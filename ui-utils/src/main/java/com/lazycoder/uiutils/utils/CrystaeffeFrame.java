package com.lazycoder.uiutils.utils;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

public class CrystaeffeFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public CrystaeffeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel() {

			private void JPanel() {
				// TODO Auto-generated method stub
				setPreferredSize(new Dimension(810, 500));
			}

			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				CrystalEffect.createCrystalEffect(g, new Color(255, 102, 0), this);
			}
		};
		panel.setBounds(20, 20, 810, 500);
		contentPane.add(panel);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CrystaeffeFrame frame = new CrystaeffeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
