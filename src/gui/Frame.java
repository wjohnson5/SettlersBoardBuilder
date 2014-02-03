package gui;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1203725186300525380L;

	public Frame() {
		setTitle("Catan Map Generator");
		setSize(800, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new MainPanel());
	}

}
