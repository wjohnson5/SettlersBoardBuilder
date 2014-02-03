package board;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tiles.Tile;
import tiles.Tile.Type;
import algorithm.Generator.FailedMapException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;


public class NumberBag {
	ListMultimap<Type, Integer> sortedNumbers = ArrayListMultimap.create();
	
	public NumberBag(final Multiset<Type> resourceTileCounts) {
		Multiset<Integer> bag = HashMultiset.create();
		int numTiles = resourceTileCounts.size();
		
		/* Add the same number of each tile to the bag */
		Random r = new Random();
		for (int i = 2; i <= 12; i++) {
			if (i != 7)
				bag.add(i, numTiles/10);
		}
		
		/* Add a few more tiles so we have enough */
		List<Integer> nums = Lists.newArrayList(new Integer[] {2,3,4,5,6,8,9,10,11,12});
		Collections.shuffle(nums);
		for (int i = 0; i < numTiles%10; i++) {
			bag.add(nums.remove(0));
		}
		
		/* Make the gold tiles have random numbers */
		for (int i = 0; i < resourceTileCounts.count(Type.GOLD); i++) {
			int n = Iterators.get(bag.iterator(), r.nextInt(bag.size()));
			bag.remove(n);
			sortedNumbers.put(Type.GOLD, n);
		}
		
		/* Sort the types of resources by the number of tiles they have on the board.
		 * This way, if a resource has less tiles, it will be more likely to get higher numbers */
		List<Type> typelist = Lists.newArrayList(Tile.resourceTypes);
		Collections.sort(typelist, new Comparator<Type>() {
			@Override
			public int compare(Type o1, Type o2) {
				return resourceTileCounts.count(o1) - resourceTileCounts.count(o2);
			}
		});
		
		/* Shuffle then sort the numbers by dots */
		LinkedList<Integer> bagList = Lists.newLinkedList(bag);
		Collections.shuffle(bagList);
		Collections.sort(bagList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return (6 - Math.abs(o2 - 7)) - (6 - Math.abs(o1 - 7));
			}
		});
		
		/* Iterate through the numbers, giving them to each resource type one at a time */
		int k = 0;
		while (!bagList.isEmpty()) {
			Type t = typelist.get(k%typelist.size());
			if (sortedNumbers.get(t).size() < resourceTileCounts.count(t)) {
				sortedNumbers.put(t, bagList.removeFirst());
			}
			k++;
		}
		
		for (Type t : Type.values()) {
			int sum = 0;
			for (Integer i : sortedNumbers.get(t)) {
				sum += 6 - Math.abs(i - 7);
			}
			System.out.println(t + ": " + sum + " dots, " + resourceTileCounts.count(t) + " tiles");
		}
		System.out.println("");
	}
	
	public Integer grabNumber(Type t) {
		int s = sortedNumbers.get(t).size();
		return sortedNumbers.get(t).remove(new Random().nextInt(s));
	}
	
	public Integer grabUniqueNumber(Type t, ImmutableSet<Integer> neighborNumbers) throws NoSuchNumberException {
		List<Integer> numbersForT = sortedNumbers.get(t);
		int s = numbersForT.size();
		if (s == 0) {
			throw new NoSuchNumberException();
		}
		int r = new Random().nextInt(s);
		int n = numbersForT.get(r);
		int i = 0;
		while (neighborNumbers.contains(n)) {
			i++;
			if (i >= s) {
				throw new NoSuchNumberException();
			}
			n = numbersForT.get((r+i)%s);
		}
		return numbersForT.remove((r+i)%s);
	}
	
	public static class NoSuchNumberException extends FailedMapException {
		private static final long serialVersionUID = -4443776062653024244L;
	}
}
