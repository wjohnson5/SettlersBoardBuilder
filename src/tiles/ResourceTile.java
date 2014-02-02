package tiles;

import java.awt.Color;

public class ResourceTile extends NumberedTile {
	
	public static enum Type { 
		
		WHEAT (new Color(255,241,41)),
		ORE (new Color(150,150,150)),
		BRICK (new Color(219,104,33)),
		WOOD (new Color(10,115,0)),
		SHEEP (new Color(105,250,52));
		
		private Color c;
		Type(Color c) {
			this.c = c;
		}
		Color getColor() {
			return c;
		}
		
	}

	Type type;
	
	public ResourceTile(Type type) {
		this.type = type;
	}

	@Override
	public Color getColor() {
		return type.getColor();
	}
}
