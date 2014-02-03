package tiles;

public class ResourceTile extends NumberedTile {
	
	public ResourceTile(Type type) {
		super(type);
		if (!type.isResource()) {
			throw new IllegalArgumentException();
		}
	}
}
