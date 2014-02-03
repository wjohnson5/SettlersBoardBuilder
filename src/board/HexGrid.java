package board;
import gui.Drawable;

import java.awt.Graphics;
import java.util.Iterator;

import algorithm.Settings;
import algorithm.Settings.HexSetting;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table.Cell;


public class HexGrid implements Iterable<Hex>, Drawable {
	ImmutableTable<Integer, Integer, Hex> map;
	ImmutableTable<Integer, Integer, HexSetting> boundary;
	
	public HexGrid() {
		this.boundary = Settings.getInstance().getBoundary().getMap();
		
		ImmutableTable.Builder<Integer, Integer, Hex> b = ImmutableTable.builder();
		for (Cell<Integer, Integer, HexSetting> c : boundary.cellSet()) {
			if (c.getValue().equals(HexSetting.LAND)) {
				b.put(c.getRowKey(), c.getColumnKey(), new Hex(c.getRowKey(), c.getColumnKey()));
			}
		}
		map = b.build();
	}
	
	Optional<Hex> get(int x, int y) {
		if (isInBounds(x, y)) {
			if (map.get(x, y) == null) {
				return Optional.of(Hex.createLockedWaterHex(x, y));
			} else {
				return Optional.of(map.get(x, y));
			}
		} else {
			return Optional.absent();
		}
	}
	
	public ImmutableSet<Hex> getNeighbors(Hex h) {
		int x = h.x;
		int y = h.y;
		ImmutableSet.Builder<Hex> s = ImmutableSet.builder();
		s.addAll(get(x-1,y).asSet());
		s.addAll(get(x-1,y+1).asSet());
		s.addAll(get(x,y-1).asSet());
		s.addAll(get(x,y+1).asSet());
		s.addAll(get(x+1,y-1).asSet());
		s.addAll(get(x+1,y).asSet());
		return s.build();
	}
	
	ImmutableSet<Settlement> getSettlements(int x, int y) {
		// top center
		Settlement set1 = new Settlement(new ImmutableSet.Builder<Hex>()
				.addAll(get(x,y).asSet()) // below
				.addAll(get(x,y-1).asSet()) // upper left
				.addAll(get(x+1,y-1).asSet()).build()); // upper right
		// top left
		Settlement set2 = new Settlement(new ImmutableSet.Builder<Hex>()
				.addAll(get(x,y).asSet()) // lower right
				.addAll(get(x-1,y).asSet()) // lower left
				.addAll(get(x,y-1).asSet()).build()); // above
		return ImmutableSet.of(set1, set2);
	}
	
	public ImmutableSet<Settlement> getAllSettlements() {
		ImmutableSet.Builder<Settlement> b = new ImmutableSet.Builder<>();
		for (Cell<Integer, Integer, HexSetting> c : boundary.cellSet()) {
			int x = c.getRowKey();
			int y = c.getColumnKey();
			if (isInBounds(x,y) || isInBounds(x,y-1) || isInBounds(x-1,y) || isInBounds(x+1,y-1)) {
				b.addAll(getSettlements(x,y));
			}
		}
		return b.build();
	}
	
	boolean isInBounds(int x, int y) {
		return boundary.get(x, y) != null && !boundary.get(x, y).equals(HexSetting.DISABLED);
	}

	@Override
	public Iterator<Hex> iterator() {
		return map.values().iterator();
	}

	@Override
	public Drawer on(Graphics g) {
		return new Drawer(g) {
			@Override
			public void draw() {
				for (Cell<Integer, Integer, HexSetting> c : boundary.cellSet()) {
					if (c.getValue().equals(HexSetting.LAND)) {
						Hex h = map.get(c.getRowKey(), c.getColumnKey());
						if (h == null) {
							Hex.createLandHex(c.getRowKey(), c.getColumnKey());
						} else {
							h.on(g()).draw();
						}
					} else if (c.getValue().equals(HexSetting.WATER)) {
						Hex.createLockedWaterHex(c.getRowKey(), c.getColumnKey()).on(g()).draw();
					} else {
						Hex.createDisabledHex(c.getRowKey(), c.getColumnKey()).on(g()).draw();
					}
				}				
			}			
		};
	}
}
