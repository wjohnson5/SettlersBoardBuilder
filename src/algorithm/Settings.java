package algorithm;

import gui.Drawable;

import java.awt.Graphics;
import java.awt.Point;

import board.Hex;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

public class Settings {
	
	public enum HexSetting {
		DISABLED,
		WATER,
		LAND
	}

	private int width = 8;
	private int height = 8;
	private int players = 6;
	private boolean addWater = true;
	private boolean fogOfWar = false;
	private Table<Integer, Integer, HexSetting> map = HashBasedTable.create();
	
	private static Settings _this;
	
	private Settings() {
		for (int c = 0; c < width; c++) {
			for (int r = 0 - c/2; r < height - c/2; r++) {
				map.put(r, c, HexSetting.DISABLED);
			}
		}
	}
	
	public static Settings getInstance() {
		if (_this == null) {
			_this = new Settings();
		}
		return _this;
	}
	
	public void toggle(Point p) {
		HexSetting s = map.get(p.x, p.y);
		if (s == null) {
			return;
		}
		if (s.equals(HexSetting.DISABLED)) {
			map.put(p.x, p.y, HexSetting.LAND);
		} else if (s.equals(HexSetting.LAND)) {
			map.put(p.x, p.y, HexSetting.WATER);
		} else {
			map.put(p.x, p.y, HexSetting.DISABLED);
		}
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public boolean hasAddWater() {
		return addWater;
	}

	public Settings setAddWater(boolean addWater) {
		this.addWater = addWater;
		return this;
	}

	public boolean hasFogOfWar() {
		return fogOfWar;
	}

	public Settings setFogOfWar(boolean fogOfWar) {
		this.fogOfWar = fogOfWar;
		return this;
	}
	
	public Boundary getBoundary() {
		return new Boundary(ImmutableTable.copyOf(map));
	}
	
	public static class Boundary implements Drawable {
		
		private ImmutableTable<Integer, Integer, HexSetting> map;
		
		private Boundary(ImmutableTable<Integer, Integer, HexSetting> map) {
			this.map = map;
		}
		
		public ImmutableTable<Integer, Integer, HexSetting> getMap() {
			return map;
		}
		
		@Override
		public Drawer on(Graphics g) {
			return new Drawer(g) {
				@Override
				public void draw() {
					for (Cell<Integer, Integer, HexSetting> c : map.cellSet()) {
						if (c.getValue().equals(HexSetting.LAND)) {
							Hex.createLandHex(c.getRowKey(), c.getColumnKey()).on(g()).draw();
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
}
