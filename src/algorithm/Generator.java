package algorithm;

import tiles.NumberedTile;
import tiles.Tile;
import board.Hex;
import board.HexGrid;
import board.NumberBag;
import board.Settlement;
import board.TileBag;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

public class Generator {
	
	private static Settings settings = Settings.getInstance();
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
			map = new HexGrid();
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
		map = new HexGrid();
		int mapSize = Iterables.size(map);
		TileBag tiles = new TileBag(mapSize, settings.getPlayers());
		NumberBag numbers = new NumberBag(tiles.getResourceTileCounts());		
		for (Hex h : map) {
			ImmutableSet.Builder<Tile> neighborTiles = ImmutableSet.builder();
			ImmutableSet.Builder<Integer> neighborNumbers = ImmutableSet.builder();
			for (Hex n : map.getNeighbors(h)) {
				if (n.getTile() != null) {
					neighborTiles.add(n.getTile());
					neighborNumbers.add(n.getNumber());
				}
			}
			Tile t;
			t = tiles.grabUniqueTile(neighborTiles.build());
			h.setTile(t);
			if (t.isNumberedTile()) {
				NumberedTile nt = (NumberedTile) t;
				h.setNumber(numbers.grabUniqueNumber(nt.getType(), neighborNumbers.build()));
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
	
	public static class FailedMapException extends Exception {
		private static final long serialVersionUID = 934054340095220755L;
	}
}
