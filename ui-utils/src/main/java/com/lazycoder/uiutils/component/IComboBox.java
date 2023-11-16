package com.lazycoder.uiutils.component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

import com.zoo.base.Resource;


@SuppressWarnings("rawtypes")
public class IComboBox extends JComboBox
{  
	   
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3640617144017409790L;
	private Color graceBlue=new Color(60,195,245);
	private Color graceGray=new Color(238,238,238);
	
	private int waneWidth=15;
	private int waneHeight=15;
	
	public void setWane(boolean isWane){
		if (!isWane) {
			waneHeight=0;
			waneWidth=0;
		}
	}
	
	public IComboBox()
	 {  
	  super();
	  init();  
	 }
	@SuppressWarnings("unchecked")
	public IComboBox(ComboBoxModel model)
	 {  
	  super(model);  
	  init();  
	 }  
	 @SuppressWarnings("unchecked")
	public IComboBox(Object[] items)
	 {  
	  super(items);  
	  init();  
	 }  
	 @SuppressWarnings("unchecked")
	public IComboBox(Vector items)
	 {  
	  super(items);  
	  init();  
	 }
	 
	 //初始化
	@SuppressWarnings("unchecked")
	private void init()
	 {
		 setUI(new IComboBoxUI());  
		 setForeground(Color.gray);//下拉框展开时，非下拉部分的前景色
		 setBackground(graceGray);
		 setFont(new Font("新宋体", Font.PLAIN, 14));
		 setRenderer(new IComboBoxRenderer());
		 
	 }  
	 @Override
	 public Dimension getPreferredSize()
	 {  
		 return super.getPreferredSize();  
	 }
	 //定制ComboBox的下拉面板(下拉部分)
	 public class IComboBoxRenderer extends JLabel implements ListCellRenderer
	 {  
		   
		 /**
		 * 
		 */
		private static final long serialVersionUID = -5076926518043985432L;
		private DefaultListCellRenderer defaultCellRenderer = new DefaultListCellRenderer();  
		 
		 public IComboBoxRenderer()
		 {  
			 super();
		 }  
		 
		 //下拉框部分
		 @Override
		 public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		 {  
			  JLabel renderer = (JLabel)defaultCellRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			  if(isSelected)//选中下拉列表项
			  {  
				   renderer.setBackground(graceBlue);  //选中项的背景色
				   renderer.setForeground(Color.WHITE);//选中项的前景色
			  }else
			  {  
				  renderer.setBackground(Color.WHITE);//未选中项的背景色
				  renderer.setForeground(Color.DARK_GRAY);//未选中项前景色
			  } 
			  //ComboBox面板(非下拉部分)
			  list.setSelectionForeground(Color.DARK_GRAY);//下拉框未展开时非下拉框显示的前景色
			  list.setSelectionBackground(graceGray);//下拉框未展开时非下拉框显示的背景色
			  list.setBorder(null);  
			  return renderer;  
		 }  
	 }
	 
	 //非下拉部分
	 public class IComboBoxUI extends BasicComboBoxUI 
	 {  
		 
		 private JButton arrow;//下拉框的箭头
		 private boolean boundsLight = false; 
		 
		 public IComboBoxUI()
		 {  
		  super();  
		 }
		 @Override
		 protected JButton createArrowButton()
		 {  
			  arrow = new JButton();  
			  arrow.setIcon(Resource.getIcon("res","iComboBox_arrow.png"));
			  arrow.setRolloverEnabled(true);
			  arrow.setRolloverIcon(Resource.getIcon("res","iComboBox_arrow_up.png"));
			  arrow.setPressedIcon(Resource.getIcon("res","iComboBox_arrow_press.png"));
			  arrow.setBorder(null);
			  arrow.setOpaque(false);
			  arrow.setContentAreaFilled(false);
			  return arrow;  
		 }  
		 
		 @Override
		 public void paint(Graphics g, JComponent c)
		 {  
			  hasFocus = comboBox.hasFocus();
			  Graphics2D g2 = (Graphics2D)g;  
			  if (!comboBox.isEditable())
			  {  
				   Rectangle r = rectangleForCurrentValue();
				   //重点:JComboBox的textfield 的绘制 并不是靠Renderer来控制.
				   //它会通过paintCurrentValueBackground来绘制背景  
				   //然后通过paintCurrentValue();去绘制JComboBox里显示的值  
				   paintCurrentValueBackground(g2, r, hasFocus);  
				   paintCurrentValue(g2, r, hasFocus);  
			  }  
		    
			  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			  int width = c.getWidth()-1;  //边框线跨宽度
			  int height = 0;  
			  int heightOffset = 0;
			  if (comboBox.isPopupVisible())//弹出下拉框时
			  {
				   heightOffset = 10;
				   height = c.getHeight()-1;  
				   g2.setColor(graceBlue);
				   arrow.setIcon(Resource.getIcon("res","iComboBox_arrow_press.png"));
			  }else
			  {
				  heightOffset = 0;
				  height = c.getHeight()-1;
				  g2.setColor(graceBlue);
				  arrow.setIcon(Resource.getIcon("res","iComboBox_arrow.png"));
			  }
			  if (comboBox.isFocusable())
			  {  
				  g2.setColor(graceBlue);
//			  }else
//			  {
			  }
			  g2.drawRoundRect(0, 0, width, height + heightOffset,waneWidth,waneHeight);
		 }
		 
		 //
		 @Override
		 public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus)
		 {  
			  Font oldFont = comboBox.getFont();
			  super.paintCurrentValue(g, bounds, hasFocus);
			  comboBox.setFont(oldFont); 
		 }  
		 @Override
		 public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus)
		 {
			 comboBox.setBackground(getBackground());//非下拉部分按钮的背景色
			 super.paintCurrentValueBackground(g, bounds, hasFocus);
		 }
		 @Override
		 public Dimension getPreferredSize(JComponent c)
		 {  
			 return super.getPreferredSize(c);  
		 }  
		 
		 public boolean isBoundsLight()
		 {  
			 return boundsLight;  
		 }  
		 
		 public void setBoundsLight(boolean boundsLight)
		 {  
			 this.boundsLight = boundsLight;  
		 }  
		 
		 @Override
		 protected ComboPopup createPopup()
		 {  
			  ComboPopup popup = new BasicComboPopup(comboBox)
			  {  
				   /**
				 * 
				 */
				private static final long serialVersionUID = -8658238984136596803L;
				@Override
				protected JScrollPane createScroller()
				   {  
					    JScrollPane sp = new JScrollPane(list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);  
					    sp.setHorizontalScrollBar(null);//去掉水平滚动栏
					    ScrollBarUI sbUI=new IScrollBarUI();
					    sp.getVerticalScrollBar().setUI(sbUI);
					    return sp;  
				   }
				   //重载paintBorder方法 来画出我们想要的边框..  
				   @Override
				   public void paintBorder(Graphics g)
				   {  
					    Graphics2D g2 = (Graphics2D) g;  
					    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
					      RenderingHints.VALUE_ANTIALIAS_ON);  
					    g2.setColor(graceBlue);
					    g2.drawRoundRect(0,-arrow.getHeight(),getWidth()-1,getHeight()+arrow.getHeight()-1,0,0);  
				   }  
			  };  
			  return popup;  
		 }  
	 }
	 //下拉面板滚动条部分
	 public class IScrollBarUI extends BasicScrollBarUI
	 {  
		 public IScrollBarUI()
		 {  
			 super();  
		 }  
		 //定制滑块
		 @Override
		 protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
		 {  
			  int width = thumbBounds.width;  
			  int height = thumbBounds.height;
			  Graphics2D g2 = (Graphics2D)g;  
			  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
			          RenderingHints.VALUE_ANTIALIAS_ON);  
			    
			  g2.translate(thumbBounds.x, thumbBounds.y);  
			  g2.setColor(graceBlue);  
			  g2.drawRoundRect(1,0,width-2,height-2,5,5);  
			    
			  g2.setColor(Color.ORANGE);
			  g2.drawLine(3,height/2,width-4,height/2);  
			  g2.drawLine(3,height/2+3,width-4,height/2+3);  
			  g2.translate(-thumbBounds.x, -thumbBounds.y);
		 }  
		//定制滑块的轨迹
		 @Override
		 protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		 {  
			  g.setColor(Color.gray);
			  int x = trackBounds.x;  
			  int y = trackBounds.y;  
			  int width = trackBounds.width;  
			  int height = trackBounds.height;  
			  Graphics2D g2 = (Graphics2D)g;  
			  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
			          RenderingHints.VALUE_ANTIALIAS_ON);  
			  g2.setComposite(AlphaComposite  
			    .getInstance(AlphaComposite.SRC_OVER, 0.1f));  
			    
			  g2.fill3DRect(x, y, width, height, true);
			  g2.setComposite(AlphaComposite  
			    .getInstance(AlphaComposite.SRC_OVER, 1f));  
			  g2.setColor(graceBlue);  
			  g2.fill3DRect(x, y, 1, height+10, true);  
			  if(trackHighlight == DECREASE_HIGHLIGHT)
			  {  
			      paintDecreaseHighlight(g);  
			  }   
			  else if(trackHighlight == INCREASE_HIGHLIGHT)
			  {  
			      paintIncreaseHighlight(g);  
			  }  
		 }  
		 
		 //向上的按钮
		 @Override
		 protected JButton createIncreaseButton(int orientation)
		 {  
			  JButton button = new BasicArrowButton(orientation)
			  {  
				   /**
				 * 
				 */
				private static final long serialVersionUID = -3401610346605892077L;

				@Override
				public void paint(Graphics g)
				   {  
					    Graphics2D g2 = (Graphics2D)g;  
					    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
					    g2.setColor(graceBlue);   
					    g2.drawLine(0,0,0,getHeight());  
					    g2.drawLine(0,0,getWidth(),0-1);  
					    g2.drawImage(Resource.getIcon("res","iComboBox_scrollBar_down.png").getImage(),-1,0,null); 
				   }  
			  };  
			  button.setOpaque(false);//设置透明
			  return button;  
		 }  
		 
		 //向下的按钮
		 @Override
		 protected JButton createDecreaseButton(int orientation)
		 {  
		 
		    
			  JButton button = new BasicArrowButton(orientation)
			  {  
				   /**
				 * 
				 */
				private static final long serialVersionUID = -7557106106897587112L;

				@Override
				public void paint(Graphics g)
				   {  
					    Graphics2D g2 = (Graphics2D)g;  
					    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  
					            RenderingHints.VALUE_ANTIALIAS_ON);  
					    g2.setColor(graceBlue);  
					    g2.drawLine(0,0,0,getHeight());  
					    g2.drawLine(0,getHeight()-1,getWidth(),getHeight());  
					    g2.drawImage(Resource.getIcon("res","iComboBox_scrollBar_up.png").getImage(),-1,0,null);  
				   }  
			  };  
			  button.setOpaque(false);//设置按钮透明
			  return button;  
		 }  
	} 
} 

