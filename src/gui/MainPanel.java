package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import algorithm.Generator;
import algorithm.Settings;

public class MainPanel extends JPanel {
	static Color SHEEP = new Color(105,250,52);
	static Color WOOD = new Color(10,115,0);
	static Color ORE = new Color(150,150,150);
	static Color WHEAT = new Color(255,241,41);
	static Color BRICK = new Color(219,104,33);
	static Color WATER = new Color(43,170,255);
	static Color DESERT = new Color(194,178,128);
	static Color GOLD = new Color(207,181,59);
	
	MainPanel() {
		JButton regenButton = new JButton("Re-Generate");
		
		final JCheckBox waterbox = new JCheckBox("Add Water");
		waterbox.setSelected(true);
		final JCheckBox fogbox = new JCheckBox("Fog Of War");
		fogbox.setSelected(false);
		
		regenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.getInstance()
					.setAddWater(waterbox.isSelected())
					.setFogOfWar(fogbox.isSelected());
				Generator.getInstance()	.generate();
				repaint();
			}
		});
		
		JPanel buttonPanel = new JPanel(new GridLayout(3,1));
		buttonPanel.setSize(100, 100);
		buttonPanel.add(regenButton);
		buttonPanel.add(waterbox);
		buttonPanel.add(fogbox);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(buttonPanel);
	}
	private static final long serialVersionUID = 5945192644470506206L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Generator.getInstance().getMap().on(g).draw();
	}
}
