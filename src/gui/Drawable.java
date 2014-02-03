package gui;

import java.awt.Graphics;

public interface Drawable {
	
	Drawer on(Graphics g);
	
	public abstract class Drawer {
		private Graphics g;
		
		public Drawer(Graphics g) {
			this.g = g;
		}
		
		public abstract void draw();
		
		public Graphics g() {
			return g;
		}
	}

}
