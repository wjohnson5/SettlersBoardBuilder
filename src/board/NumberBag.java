package board;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import tiles.NumberedTile.Type;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;


public class NumberBag {
	ListMultimap<Type, Integer> sortedNumbers = ArrayListMultimap.create();
	
	public NumberBag(Multiset<Type> resourceTileCounts) {
		Multiset<Integer> bagPrep = HashMultiset.create();
		int numTiles = resourceTileCounts.size();
		
		Random r = new Random();
		for (int i = 2; i <= 12; i++) {
			if (i != 7)
				bagPrep.add(i, numTiles/10);
		}
		List<Integer> nums = Lists.newArrayList(new Integer[] {2,3,4,5,6,8,9,10,11,12});
		Collections.shuffle(nums);
		for (int i = 0; i < numTiles%10; i++) {
			bagPrep.add(nums.remove(0));
		}
		
		List<Type> typelist = Lists.newArrayList(Type.values());
		Collections.shuffle(typelist);
		
		List<Integer> numberlist = Lists.newArrayList(new Integer[] {1,2,3,4,5});
		Collections.shuffle(numberlist);
		
		for (int i = 0; i < 5; i++) {
			int h = numberlist.get(i);
			int j = 7 + (r.nextInt(1)*2-1)*(6-h); // get number from dots
			int k = 0;
			while (bagPrep.contains(j)) {
				Type t = typelist.get(k%6);
				if (sortedNumbers.get(t).size() < resourceTileCounts.count(t)) {
					sortedNumbers.put(t, j);
					bagPrep.remove(j);
				}
				k++;
			} 
			while (bagPrep.contains(14-j)) {
				Type t = typelist.get(k%6);
				if (sortedNumbers.get(t).size() < resourceTileCounts.count(t)) {
					sortedNumbers.put(t, 14-j);
					bagPrep.remove(14-j);
				}
				k++;				
			}
		}
		
//		for (Type t : Type.values()) {
//			int sum = 0;
//			for (Integer i : sortedNumbers.get(t)) {
//				sum += 6 - Math.abs(i - 7);
//			}
//			System.out.println(t + ": " + sum);
//		}
	}
	
	public Integer grabNumber(Type t) {
		return sortedNumbers.get(t).remove(0);
	}
}
