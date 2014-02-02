package board;
import gui.Drawable;
import gui.Hexagon;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import algorithm.Convert;
import tiles.GoldTile;
import tiles.Tile;


public class Hex implements Drawable {

	private Tile t;
	private int number = 1;
	int x;
	int y;
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
	
	@Override
	public Drawer on(Graphics g) {		
		return new Drawer(g) {
			@Override
			public void draw() {
				g().setColor(t.getColor());
				Point p = Convert.fromGameToScreen(x(), y());
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
					FontMetrics fm = g().getFontMetrics();
		            
					g().drawString(s, p.x - fm.stringWidth(s)/2, p.y+5);
					
				}
			}
		};
	}
	
	

}
