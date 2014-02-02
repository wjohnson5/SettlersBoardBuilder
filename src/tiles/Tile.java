package tiles;

import java.awt.Color;

public abstract class Tile {
	public abstract Color getColor();
	public boolean isNumberedTile() {
		return false;
	}
}
