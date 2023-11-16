package com.lazycoder.uiutils.component;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * 源码摘自https://blog.csdn.net/hiyohu/article/details/13512117
 *
 * @author 哟呼很多事
 */
public class TextFieldWithPicture extends JTextField {

	/**
	 *
	 */
	private static final long serialVersionUID = -5405811610820750958L;
	private ImageIcon icon;

	public TextFieldWithPicture(ImageIcon icon) {
		// 获取当前路径下的图片
		this.icon = icon;
		Insets insets = new Insets(0, 40, 0, 0);
		// 设置文本输入距左边20
		this.setMargin(insets);
	}

	public TextFieldWithPicture(ImageIcon icon, String text) {
		this(icon);
		setText(text);
		setEditable(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		Insets insets = getInsets();
		super.paintComponent(g);
		int iconWidth = icon.getIconWidth();
		int iconHeight = icon.getIconHeight();
		int Height = this.getHeight();
		// 在文本框中画上之前图片
		icon.paintIcon(this, g, (insets.left - iconWidth) / 2, (Height - iconHeight) / 2);

	}

}
