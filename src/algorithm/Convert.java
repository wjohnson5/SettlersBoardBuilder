package algorithm;

import gui.Hexagon;

import java.awt.Point;

public class Convert {
	
	public static Point fromGameToScreen(int x, int y) {
		int q = x + (y - (y&1)) / 2;
		int r = y;
		q *= Hexagon.WIDTH_SPACE;
		if ((r&1) == 1) {
			q += Hexagon.WIDTH_SPACE / 2;
		}
		r *= Hexagon.HEIGHT_SPACE;
		q += Hexagon.LEFT_PADDING;
		r += Hexagon.TOP_PADDING;
		return new Point(q,r);
	}
	
	public static Point fromScreenToGame(Point p) {
		int x = p.x - Hexagon.LEFT_PADDING;
		int y = p.y - Hexagon.TOP_PADDING;
		double q = (1.0d/3 * Math.sqrt(3) * x - 1.0d/3 * y) / Hexagon.SIZE;
		double r = 2.0d/3 * y / Hexagon.SIZE;
		return round(q, r);
	}
	
	public static Point round(double q, double r) {
		double y = -q-r;
		double rx = Math.rint(q);
		double rz = Math.rint(r);
		double ry = Math.rint(y);
		
		double x_diff = Math.abs(rx-q);
		double y_diff = Math.abs(ry-y);
		double z_diff = Math.abs(rz-r);
		
		if (x_diff > y_diff && x_diff > z_diff) {
			rx = -ry-rz;
		} else if (y_diff > z_diff) {
			ry = -rx-rz;
		} else {
			rz = -rx-ry;
		}
		
		return new Point((int)rx, (int)rz);
	}
}
