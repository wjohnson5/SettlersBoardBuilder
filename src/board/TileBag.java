package board;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import algorithm.Generator.FailedMapException;
import algorithm.Settings;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;

import tiles.DesertTile;
import tiles.GoldTile;
import tiles.ResourceTile;
import tiles.Tile;
import tiles.WaterTile;
import tiles.Tile.Type;

public class TileBag {	
	private List<Tile> bag = new LinkedList<>();
	private Multiset<Type> numOfType = HashMultiset.create();
	
	public TileBag(int numTiles, int numPlayers) {
		Random r = new Random();	
		
		for (Type t : Tile.resourceTypes) {
				int n = r.nextInt(3) + numPlayers - 1;
				bag.addAll(Collections.nCopies(n, new ResourceTile(t)));
				numOfType.add(t, n);
		}
			
		int n = r.nextInt(numPlayers/2 + 2);
		bag.addAll(Collections.nCopies(n, new GoldTile()));
		numOfType.add(Type.GOLD, n);
		
		bag.addAll(Collections.nCopies(r.nextInt(Math.max(1, Math.min(5, numTiles - bag.size()))) + 1, new DesertTile()));
				
		if (Settings.getInstance().hasAddWater()) {
			bag.addAll(Collections.nCopies(Math.max(0, numTiles - bag.size()), new WaterTile()));
		} else {
			Iterator<Type> resourceloop = Iterators.cycle(Tile.resourceTypes);
			while (numTiles - bag.size() > 0) {
				Type t = resourceloop.next();
				bag.add(new ResourceTile(t));
				numOfType.add(t);
			}
		}
		
		Collections.shuffle(bag);
	}
	
	public Tile grabTile() {
		return bag.remove(0);
	}
	
	public Tile grabUniqueTile(ImmutableSet<Tile> s) throws NoSuchTileException {
		int i = 0;
		if (i >= bag.size()) {
			throw new NoSuchTileException();
		}
		Tile t2 = bag.get(i);
		while (t2.isNumberedTile() && s.contains(t2)) {
			i++;
			if (i >= bag.size()) {
				throw new NoSuchTileException();
			}
			t2 = bag.get(i);
		}
		return bag.remove(i);
	}
	
	public Multiset<Type> getResourceTileCounts() {
		return numOfType;
	}
	
	public static class NoSuchTileException extends FailedMapException {
		private static final long serialVersionUID = -3795743322829430816L;
	}
}
