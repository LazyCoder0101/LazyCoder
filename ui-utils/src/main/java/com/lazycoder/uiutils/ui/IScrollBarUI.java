package com.lazycoder.uiutils.ui;//package com.lannong.Client.UI.BaseCommonComponent;
//
//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.RenderingHints;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.plaf.basic.BasicArrowButton;
//import javax.swing.plaf.basic.BasicScrollBarUI;
//
//public class IScrollBarUI extends BasicScrollBarUI {
//	public IScrollBarUI() {
//		super();
//	}
//
//	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
//		int width = thumbBounds.width;
//		int height = thumbBounds.height;
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//		g2.translate(thumbBounds.x, thumbBounds.y);
//		g2.setColor(XUtil.defaultComboBoxBoundsColor);
//		g2.drawRoundRect(1, 1, width - 2, height - 2, 5, 5);
//
//		g2.setColor(Color.ORANGE);
//		g2.drawLine(3, height / 2, width - 4, height / 2);
//		g2.drawLine(3, height / 2 + 3, width - 4, height / 2 + 3);
//		g2.translate(-thumbBounds.x, -thumbBounds.y);
//	}
//
//	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
//		g.setColor(XUtil.defaultComboBoxColor);
//		int x = trackBounds.x;
//		int y = trackBounds.y;
//		int width = trackBounds.width;
//		int height = trackBounds.height;
//		Graphics2D g2 = (Graphics2D) g;
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
//
//		g2.fill3DRect(x, y, width, height, true);
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		g2.setColor(XUtil.defaultComboBoxBoundsColor.brighter());
//		g2.fill3DRect(x, y, 1, height + 1, true);
//		if (trackHighlight == DECREASE_HIGHLIGHT) {
//			paintDecreaseHighlight(g);
//		} else if (trackHighlight == INCREASE_HIGHLIGHT) {
//			paintIncreaseHighlight(g);
//		}
//	}
//
//	protected JButton createIncreaseButton(int orientation) {
//		JButton button = new BasicArrowButton(orientation) {
//			public void paint(Graphics g) {
//				Graphics2D g2 = (Graphics2D) g;
//				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//				g2.setColor(XUtil.defaultComboBoxBoundsColor);
//				g2.drawLine(0, 0, 0, getHeight());
//				g2.drawLine(0, 0, getWidth(), 0 - 1);
//				g2.drawImage(((ImageIcon) XUtil.defaultComboBoxArrowIcon_Into).getImage(), -1, 0, null);
//			}
//		};
//		button.setOpaque(false);
//		return button;
//	}
//
//	protected JButton createDecreaseButton(int orientation) {
//
//		JButton button = new BasicArrowButton(orientation) {
//			public void paint(Graphics g) {
//				Graphics2D g2 = (Graphics2D) g;
//				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//				g2.setColor(XUtil.defaultComboBoxBoundsColor);
//				g2.drawLine(0, 0, 0, getHeight());
//				g2.drawLine(0, getHeight() - 1, getWidth(), getHeight());
//				g2.drawImage(((ImageIcon) XUtil.defaultComboBoxArrowIcon_Into).getImage(), -1, 0, null);
//			}
//		};
//		button.setOpaque(false);
//		return button;
//	}
//}