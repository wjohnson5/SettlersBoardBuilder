package board;
import gui.Drawable;

import java.awt.Graphics;
import java.util.Iterator;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table.Cell;


public class HexGrid implements Iterable<Hex>, Drawable {
	int width;
	int height;
	ImmutableTable<Integer, Integer, Hex> map;
	
	public HexGrid(int width, int height) {
		this.width = width;
		this.height = height;
		
		ImmutableTable.Builder<Integer, Integer, Hex> b = ImmutableTable.builder();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (isInBounds(x,y)) {
					b.put(x, y, new Hex(x, y));
				}
			}
		}
		map = b.build();
	}
	
	Optional<Hex> get(int x, int y) {
		if (isInBounds(x, y)) {
			return Optional.of(map.get(x, y));
		} else {
			return Optional.absent();
		}
	}
	
	ImmutableSet<Hex> getNeighbors(int x, int y) {
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
		for (int x = 0; x <= width; x++) {
			for (int y = 0; y <= height; y++) {
				if (isInBounds(x,y) || isInBounds(x,y-1) || isInBounds(x-1,y)) {
					b.addAll(getSettlements(x,y));
				}
			}
		}
		return b.build();
	}
	
	boolean isInBounds(int x, int y) {
		return !(x < 0 
				|| x >= width 
				|| y < 0 
				|| y >= height 
				|| x + y < 2 
				|| x + y > width + height - 4);
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
				for (Cell<Integer, Integer, Hex> c : map.cellSet()) {
					c.getValue().on(g()).at(c.getColumnKey(), c.getRowKey()).draw();
				}				
			}			
		};
	}
}
