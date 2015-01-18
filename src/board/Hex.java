package board;
import gui.Drawable;
import gui.Hexagon;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import algorithm.Convert;
import algorithm.Settings;
import tiles.DesertTile;
import tiles.DisabledTile;
import tiles.GoldTile;
import tiles.Tile;
import tiles.WaterTile;


public class Hex implements Drawable {
	
	public static Hex createLockedWaterHex(int x, int y) {
		Hex h = new Hex(x, y);
		h.setTile(new WaterTile());
		h.setVisible();
		return h;
	}
	
	public static Hex createDisabledHex(int x, int y) {
		Hex h = new Hex(x, y);
		h.setTile(new DisabledTile());
		h.setVisible();
		return h;
	}
	
	public static Hex createLandHex(int x, int y) {
		Hex h = new Hex(x, y);
		h.setTile(new DesertTile());
		h.setVisible();
		return h;
	}

	private Tile t;
	private int number = 1;
	int x;
	int y;
	private boolean visible = false;
	
	public Hex(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile getTile() {
		return t;
	}

	public void setTile(Tile t) {
		this.t = t;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	int getDots() {
		if (t instanceof GoldTile) {
			return 5*(6 - Math.abs(number - 7))/3;
		}
		return 6 - Math.abs(number - 7);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible() {
		visible = true;
	}
	
	@Override
	public Drawer on(Graphics g) {		
		return new Drawer(g) {
			@Override
			public void draw() {
				Point p = Convert.fromGameToScreen(x, y);
				if (visible || !Settings.getInstance().hasFogOfWar()) {
					g().setColor(t.getColor());
					g().fillPolygon(Hexagon.create(p.x, p.y));
					if (t.isNumberedTile()) {
						g().setColor(Color.WHITE);
						g().fillOval(p.x - Hexagon.SIZE*3/8, p.y - Hexagon.SIZE*3/8, Hexagon.SIZE*3/4, Hexagon.SIZE*3/4);
						if (number == 6 || number == 8) {
							g().setColor(Color.RED);
						} else {
							g().setColor(Color.BLACK);
						}
						String s = String.valueOf(number);
			            
						g().setFont(new Font(g().getFont().getFontName(), Font.PLAIN, 20));
						FontMetrics fm = g().getFontMetrics();
						g().drawString(s, p.x - fm.stringWidth(s)/2, p.y + fm.getAscent()/2 - 1);
						
					}
				} else {
					g().setColor(Color.DARK_GRAY);
					g().fillPolygon(Hexagon.create(p.x,p.y));
				}
			}
		};
	}
	
	

}
