package gui;

import java.awt.Polygon;

public class Hexagon extends Polygon {
	
	private static final long serialVersionUID = -4498095884113089169L;
	
	public static final int SIZE = 45;
	public static final int WIDTH_SPACE = Double.valueOf(Math.sqrt(3) * SIZE).intValue() + 2;
	public static final int HEIGHT_SPACE = 3 * SIZE / 2 + 2;
	public static final int LEFT_PADDING = SIZE*2;
	public static final int TOP_PADDING = SIZE*2;
	
	public Hexagon(int x,  int y) {
		for (int i = 0; i < 6; i++) {
			Double angle = 2.0d * Math.PI / 6 * (i + 0.5);
			Double xi = x + SIZE * Math.cos(angle);
			Double yi = y + SIZE * Math.sin(angle);
			addPoint(xi.intValue(), yi.intValue());
		}
	}
	
	public static Hexagon create(int x, int y) {
		return new Hexagon(x,y);
	}
	
}
