package tiles;

import java.awt.Color;

public abstract class NumberedTile extends Tile {
	protected Type type;
	
	public NumberedTile(Type type) {
		this.type = type;
	}
	
	public static enum Type { 		
		WHEAT (new Color(255,241,41)),
		ORE (new Color(150,150,150)),
		BRICK (new Color(219,104,33)),
		WOOD (new Color(10,115,0)),
		SHEEP (new Color(105,250,52)),
		GOLD (new Color(207,181,59));
		
		private Color c;
		Type(Color c) {
			this.c = c;
		}
		Color getColor() {
			return c;
		}
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public boolean isNumberedTile() {
		return true;
	}

	@Override
	public Color getColor() {
		return type.getColor();
	}
}
