package algorithm;

import tiles.NumberedTile;
import tiles.Tile;
import board.Hex;
import board.HexGrid;
import board.NumberBag;
import board.Settlement;
import board.TileBag;
import board.TileBag.NoSuchTileException;

import com.google.common.collect.ImmutableSet;
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
			try {
				map = randomize();
			} catch (FailedMapException e) {
				map = null;
			}
		} while (!approve(map));
		for (int i = 0; i < 13; i++) {
			System.out.println(i + ": " + calcSettlementScores(map).count(i));
		}
		return map;
	}
	
	public boolean approve(HexGrid map) {
		return map != null && calcSettlementScores(map).lastEntry().getElement() < 13;
	}
	
	private HexGrid randomize() throws FailedMapException {
		map = new HexGrid(width, height);
		int mapSize = Iterables.size(map);
		TileBag tiles = new TileBag(mapSize, players);
		NumberBag numbers = new NumberBag(tiles.getResourceTileCounts());		
		for (Hex h : map) {
			ImmutableSet.Builder<Tile> neighborTiles = ImmutableSet.builder();
			for (Hex n : map.getNeighbors(h)) {
				if (n.getTile() != null)
					neighborTiles.add(n.getTile());
			}
			Tile t;
			try {
				t = tiles.grabTileThatsNotIn(neighborTiles.build());
			} catch (NoSuchTileException e) {
				throw new FailedMapException();
			}
			h.setTile(t);
			if (t.isNumberedTile()) {
				NumberedTile nt = (NumberedTile) t;
				h.setNumber(numbers.grabNumber(nt.getType()));
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
	
	public class FailedMapException extends Exception {
		private static final long serialVersionUID = 934054340095220755L;
	}
}
