package board;

import com.google.common.collect.ImmutableSet;


public class Settlement {
	ImmutableSet<Hex> hexes;
	
	Settlement(ImmutableSet<Hex> hexes) {
		this.hexes = hexes;
	}
	
	public int getScore() {
		int score = 0;
		for (Hex h : hexes) {
			score += h.getDots();
		}
		return score;
	}
}
