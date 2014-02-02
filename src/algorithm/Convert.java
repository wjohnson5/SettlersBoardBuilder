package algorithm;

import gui.Hexagon;

import java.awt.Point;

public class Convert {
	
	public static Point fromGameToScreen(int x, int y) {
		int q = x + (y - (y&1)) / 2;
		int r = y;
		q *= Hexagon.WIDTH_SPACE;
		if ((r&1) == 0) {
			q += Hexagon.WIDTH_SPACE / 2;
		} else {
			q += Hexagon.WIDTH_SPACE;
		}
		r *= Hexagon.HEIGHT_SPACE;
		r += Hexagon.HEIGHT_SPACE;
		return new Point(q,r);
	}
}
