package tiles;

public class ResourceTile extends NumberedTile {
	
	public ResourceTile(Type type) {
		super(type);
		if (type.equals(Type.GOLD)) {
			throw new IllegalArgumentException();
		}
	}
}
