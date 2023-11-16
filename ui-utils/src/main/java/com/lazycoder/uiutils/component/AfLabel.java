package com.lazycoder.uiutils.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import javax.swing.JPanel;

/* Swing高级篇， 13.3讲 
 * 
 * 2019-02-27 : 更新
 * 摘自 https://edu.csdn.net/course/detail/20810
 * 
 */
public class AfLabel extends JPanel
{
	public int wrappingWidth = 0; // 最大换行宽度 , 0 表示不换行
	public String text ; // 要显示的文本
	public int lineGap = 4; // 行间距
	public int padding = 8; // 边距
	
	public AfLabel()
	{
	}
	public AfLabel(String text)
	{
		this.text = text;
	}
	
	@Override
	public Dimension getPreferredSize()
	{				
		// 此处不能使用 this.getGraphics()，因为当控件未被显示之前，Graphics是空的
		Font font = this.getFont();
		int maxWidth = (wrappingWidth > 0 ? wrappingWidth :9999) ;
		
		// 如果未设置文本，直接返回
		if(this.text == null || text.length() == 0)
		{
			return new Dimension(padding*2, font.getSize() + padding*2);
		}
		
		// 直接创建一个自己的  FontRenderContext
		FontRenderContext frc = new MyFontRenderContext(); // 不能使用g2d.getFontRenderContext()
		AttributedString styledText = new AttributedString(text,font.getAttributes());
		AttributedCharacterIterator textIter = styledText.getIterator();
		LineBreakMeasurer measurer = new LineBreakMeasurer(textIter, frc);
				
		// 计算它需要多宽、多高
		int width = 0;
		int height = 0;
		while (measurer.getPosition() < text.length())
		{
			TextLayout layout = measurer.nextLayout(maxWidth);

			// 宽度有可能小于 wrappingWidth
			Rectangle2D rect = layout.getBounds();
			int textWidth = (int) (rect.getX() + rect.getWidth());
			if(textWidth > width) {
				width = textWidth + 1;
			}
			
			height += layout.getLeading() + layout.getAscent() + layout.getDescent();
			height += this.lineGap;
		}

		// 加上边距
		width += padding *2;
		height += padding *2;
		return new Dimension(width, height);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
//		int width = getWidth();
//		int height = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		//g2d.setPaint(new Color(0,0,0,0));
		//g2d.clearRect(0, 0, width, height);
		//g2d.fillRect(0,0,width,height);
		
		// 平滑绘制 （ 反锯齿 )
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//g2d.setPaint(Color.BLUE);

		if(this.text== null || text.length()==0){return;}
		
		FontRenderContext frc = g2d.getFontRenderContext();
		AttributedString styledText = new AttributedString(text, g2d.getFont().getAttributes());
		AttributedCharacterIterator iter = styledText.getIterator();
		LineBreakMeasurer measurer = new LineBreakMeasurer(iter, frc);

		// 如果未设置最大换行宽度，将设置为9999(无限宽)
		int maxWidth = (wrappingWidth > 0 ? wrappingWidth :9999) ;
		int x = padding;
		int y = padding;		
		while (measurer.getPosition() < text.length())
		{
			TextLayout layout = measurer.nextLayout(maxWidth);

			y += (layout.getAscent());
			layout.draw(g2d, x, y);

			y += layout.getDescent() + layout.getLeading();			
			y += lineGap; // 行距
		}
	}

	////////// Getter / Setter /////////
	
	public int getWrappingWidth()
	{
		return wrappingWidth;
	}
	
	public void setWrappingWidth(int wrappingWidth)
	{
		this.wrappingWidth = wrappingWidth;
		this.repaint();
	}

	public String getText()
	{
		return text;		
	}
	
	public void setText(String text)
	{
		this.text = text;
		this.repaint();
	}

	public int getLineGap()
	{
		return lineGap;
	}
	
	public void setLineGap(int lineGap)
	{
		this.lineGap = lineGap;
		this.repaint();
	}
	public int getPadding()
	{
		return padding;
	}
	public void setPadding(int padding)
	{
		this.padding = padding;
	}
	
	
	// 因 FontRenderContext 是 protected的
	// 为了测量文本的高度，所以自己建了一个它的子类
	private static class MyFontRenderContext extends FontRenderContext
	{
		public MyFontRenderContext()
		{			
		}
	}


}
