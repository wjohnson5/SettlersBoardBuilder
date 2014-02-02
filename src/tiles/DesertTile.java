package tiles;

import java.awt.Color;

public class DesertTile extends Tile {
	private static Color DESERT_COLOR = new Color(194,178,128);

	@Override
	public Color getColor() {
		return DESERT_COLOR;
	}
}
