package gui;

import java.awt.Graphics;

public interface Drawable {
	
	Drawer on(Graphics g);
	
	public abstract class Drawer {
		private int x, y;
		private Graphics g;
		
		public Drawer(Graphics g) {
			this.g = g;
		}
		
		public Drawer at(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}
		
		public abstract void draw();
		
		public Graphics g() {
			return g;
		}
		
		public int x() {
			return x;
		}
		
		public int y() {
			return y;
		}
	}

}
