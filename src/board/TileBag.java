package board;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import tiles.DesertTile;
import tiles.GoldTile;
import tiles.ResourceTile;
import tiles.Tile;
import tiles.WaterTile;
import tiles.ResourceTile.Type;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;


public class TileBag {	
	List<Tile> bag;
	
	public TileBag(int numTiles, int numPlayers) {
		Multiset<Tile> bagPrep = HashMultiset.create();
		Random r = new Random();
		
		for (Type t : Type.values()) {
			bagPrep.add(new ResourceTile(t), r.nextInt(3) + numPlayers - 1);
		}
		bagPrep.add(new GoldTile(), r.nextInt(numPlayers/2 + 2));
		bagPrep.add(new DesertTile(), r.nextInt(Math.max(0, Math.min(5, numTiles - bagPrep.size())) + 1));
		bagPrep.add(new WaterTile(), Math.max(0, numTiles - bagPrep.size()));
		
		this.bag = new LinkedList<>(bagPrep);
		Collections.shuffle(bag);
	}
	
	public Tile grabTile() {
		return bag.remove(0);
	}
}
