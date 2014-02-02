package tiles;

import java.awt.Color;

public class GoldTile extends NumberedTile {
	private static Color GOLD_COLOR = new Color(207,181,59);

	@Override
	public Color getColor() {
		return GOLD_COLOR;
	}

}
