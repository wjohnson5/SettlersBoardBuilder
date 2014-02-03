package tiles;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Objects;

public abstract class Tile {
	
	public static final	EnumSet<Type> resourceTypes = EnumSet.range(Type.WHEAT, Type.SHEEP);
	public static final	EnumSet<Type> numberedTypes = EnumSet.range(Type.WHEAT, Type.GOLD);
	
	public static enum Type { 		
		WHEAT (new Color(255,241,41), true),
		ORE (new Color(150,150,150), true),
		BRICK (new Color(219,104,33), true),
		WOOD (new Color(10,115,0), true),
		SHEEP (new Color(105,250,52), true),
		GOLD (new Color(207,181,59), false),
		WATER (new Color(43,170,255), false),
		DESERT (new Color(194,178,128), false),
		DISABLED (new Color(255,255,255), false);
		
		private Color c;
		private boolean isResource;
		
		Type(Color c, boolean isResource) {
			this.c = c;
			this.isResource = isResource;
		}
		Color getColor() {
			return c;
		}
		boolean isResource() {
			return isResource;
		}
	}
		
	protected Type type;
	
	protected Tile(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public Color getColor() {
		return type.getColor();
	}
	
	public boolean isNumberedTile() {
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Tile)) {
			return false;
		}
		Tile t = (Tile) o;
		return this.type.equals(t.type);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.type);
	}
}
