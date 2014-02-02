package tiles;

import java.awt.Color;

public class WaterTile extends Tile {

	private static Color WATER_COLOR = new Color(43,170,255);
	
	@Override
	public Color getColor() {
		return WATER_COLOR;
	}
}
