package algorithm;

import board.Hex;
import board.HexGrid;
import board.NumberBag;
import board.Settlement;
import board.TileBag;

import com.google.common.collect.Iterables;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

public class Generator {
	int width = 7;
	int height = 7;
	int players = 6;
	
	private static Generator _this;
	private HexGrid map;	
	
	private Generator() {
		
	}
	
	public static Generator getInstance() {
		if (_this == null) {
			_this = new Generator();
		}
		return _this;
	}
	
	public HexGrid getMap() {
		if (map == null) {
			map = generate();
		}
		return map;
	}
	
	public HexGrid generate() {
		HexGrid map;
		do {
			map = randomize();
		} while (!approve(map));
		for (int i = 0; i < 13; i++) {
			System.out.println(i + ": " + calcSettlementScores(map).count(i));
		}
		return map;
	}
	
	public boolean approve(HexGrid map) {
		return (calcSettlementScores(map).lastEntry().getElement() < 13) ;
	}
	
	public HexGrid randomize() {
		map = new HexGrid(width, height);
		int mapSize = Iterables.size(map);
		TileBag tiles = new TileBag(mapSize, players);
		NumberBag numbers = new NumberBag(mapSize);		
		for (Hex h : map) {
			h.setTile(tiles.grabTile());
			if (h.getTile().isNumberedTile()) {
				h.setNumber(numbers.grabNumber());
			}
		}		
		return map;
	}
	
	public SortedMultiset<Integer> calcSettlementScores(HexGrid map) {
		SortedMultiset<Integer> scores = TreeMultiset.create();
		for (Settlement s : map.getAllSettlements()) {
			scores.add(s.getScore());
		}
		return scores;
	}
}
