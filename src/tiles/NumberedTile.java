package tiles;
public abstract class NumberedTile extends Tile {
	public Type type;
	
	public static enum Type { 		
		WHEAT,
		ORE,
		BRICK,
		WOOD,
		SHEEP,
		GOLD
	}

	@Override
	public boolean isNumberedTile() {
		return true;
	}

}
