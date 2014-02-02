package board;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;


public class NumberBag {
	List<Integer> bag;
	
	public NumberBag(int numTiles) {
		Multiset<Integer> bagPrep = HashMultiset.create();
		
		for (int i = 2; i <= 12; i++) {
			if (i != 7) {
				bagPrep.add(i, numTiles/10 + 1);
			}
		}
		
		this.bag = new LinkedList<>(bagPrep);
		Collections.shuffle(bag);
	}
	
	public Integer grabNumber() {
		return bag.remove(0);
	}
}
