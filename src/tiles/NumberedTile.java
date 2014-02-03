package tiles;

public abstract class NumberedTile extends Tile {
	
	public NumberedTile(Type type) {
		super(type);
	}

	@Override
	public boolean isNumberedTile() {
		return true;
	}
}
