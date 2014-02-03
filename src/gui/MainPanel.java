package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithm.Convert;
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
	
	boolean editMode = true;
	
	MainPanel() {
		
		final JCheckBox waterbox = new JCheckBox("Add Water");
		waterbox.setSelected(true);
		final JCheckBox fogbox = new JCheckBox("Fog Of War");
		fogbox.setSelected(false);
		

		JButton regenButton = new JButton("Generate");
		
		regenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.getInstance()
					.setAddWater(waterbox.isSelected())
					.setFogOfWar(fogbox.isSelected());
				Generator.getInstance().generate();
				editMode = false;
				repaint();
			}
		});		

		JButton editButton = new JButton("Edit Map");
		
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editMode = true;
				repaint();
			}
		});
		
		JLabel playersLabel = new JLabel("Number of Players:");
		JComboBox<Integer> playersSelect = new JComboBox<>(new Integer[]{3,4,5,6});
		playersSelect.setSelectedItem(6);
		playersSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<Integer> cb = (JComboBox<Integer>)e.getSource();
				Settings.getInstance().setPlayers((Integer)cb.getSelectedItem());
				repaint();
			}
		});
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,6));
		buttonPanel.setSize(100, 100);
		buttonPanel.add(regenButton);
		buttonPanel.add(editButton);
		buttonPanel.add(playersLabel);
		buttonPanel.add(playersSelect);
		buttonPanel.add(waterbox);
		buttonPanel.add(fogbox);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(buttonPanel);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {
				if (editMode) {
					Settings.getInstance().toggle(Convert.fromScreenToGame(e.getPoint()));
					repaint();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}			
		});
	}
	
	private static final long serialVersionUID = 5945192644470506206L;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (editMode) {
			Settings.getInstance().getBoundary().on(g).draw();
		} else {
			Generator.getInstance().getMap().on(g).draw();
		}
	}
}
