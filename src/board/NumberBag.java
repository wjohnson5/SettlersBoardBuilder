package board;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;


public class NumberBag {
	static Multiset<Integer> FULL; 
	{
		FULL = HashMultiset.create();
		for (int i = 3; i < 12; i++) {
			if (i != 7)
				FULL.add(i, 6);
		}
		FULL.add(2, 5);
		FULL.add(12, 5);
	}
	
	List<Integer> bag;
	
	public NumberBag(int numTiles) {
		Multiset<Integer> bagPrep = HashMultiset.create();
		Multiset<Integer> remaining = HashMultiset.create(FULL);
		
		for (int i = 2; i <= 12; i++) {
			if (i != 7) {
				bagPrep.add(i, numTiles/10);
				remaining.remove(i, numTiles/10);
			}
		}
		List<Integer> rest = new LinkedList<>(remaining);
		Collections.shuffle(rest);
		for (int i = 0; i < numTiles%10; i++) {
			bagPrep.add(rest.remove(0));
		}
		
		this.bag = new LinkedList<>(bagPrep);
		Collections.shuffle(bag);
	}
	
	public Integer grabNumber() {
		return bag.remove(0);
	}
}
