package com.lazycoder.uiutils.ribbonmenu;

import com.lazycoder.service.fileStructure.SysFileStructure;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

//代码摘自 https://github.com/csekme/JRibbonMenu 略有调整
// Copyright 2020 Csekme Krisztián
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WAARRNTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

public class RibbonMenuItem extends JMenuItem {

 	private static final long serialVersionUID = 1L;
 	
 	public static final double SCALING_FACTOR =((double)java.awt.Toolkit.getDefaultToolkit().getScreenResolution()) / 96 ;

	final JMenuItem ref = new JMenuItem();
    private final static ImageIcon CHECKED_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "RibbonMenu"+ File.separator  + "checked.png");
    private final static ImageIcon UNCHECKED_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "RibbonMenu"+ File.separator  + "unchecked.png");
	
    boolean hover;
    boolean pressed;
    private ImageIcon icon;

    private boolean checkMenu = false;
    private static Color colorHover = new Color(232, 239, 247);
    private static Color colorPressed = new Color(201, 224, 247);
    private static Color colorBackground = new Color(255, 255, 255);
 
    
    public void setIcon(ImageIcon icon) {
    	this.icon = icon;
    }

    public RibbonMenuItem(String title, boolean defaultSelection) {
        super(title);
        this.checkMenu = true;
        this.setSelected(defaultSelection);
        addMouseListener(MA);
    }

    public RibbonMenuItem(String title) {
        super(title);
        addMouseListener(MA);
    }

    public RibbonMenuItem(String title, ImageIcon icon) {
        super(title);

        this.icon = icon;
        addMouseListener(MA);
    }

    public boolean isCheckMenu() {
        return checkMenu;
    }

    public void setCheckMenu(boolean checkMenu) {
        this.checkMenu = checkMenu;
    }

    public static void setHoverColor(Color color) {
        colorHover = color;
    }

    public static void setPressedColor(Color color) {
        colorPressed = color;
    }

    public static void setBackgroundColor(Color color) {
        colorBackground = color;
    }

    @Override
    public void paint(Graphics gl) {
        Graphics2D g = (Graphics2D) gl;

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.setFont(ref.getFont().deriveFont(Font.PLAIN));
        g.setColor(colorBackground);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (hover) {
            g.setColor(colorHover);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (pressed) {
            g.setColor(colorPressed);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        g.setColor(ref.getForeground());
        g.drawString(getText(), getIconTextGap() + 26, (int)(16 * SCALING_FACTOR));
        
        if (!isCheckMenu() && icon != null) {
            g.drawImage(icon.getImage(), 4, 3, (int)(16 * SCALING_FACTOR), (int)(16 * SCALING_FACTOR), this);
        }

        if (isCheckMenu()) {
            if (isSelected()) {
            	if (CHECKED_ICON!=null) {
                    g.drawImage(CHECKED_ICON.getImage(), 4, 3, (int)(16 * SCALING_FACTOR), (int)(16 * SCALING_FACTOR), this);
                }
            } else {
            	if (UNCHECKED_ICON!=null) {
                    g.drawImage(UNCHECKED_ICON.getImage(), 4, 3, (int)(16 * SCALING_FACTOR), (int)(16 * SCALING_FACTOR), this);
                }
            }
        }

        // super.paint(g);
    }

    MouseAdapter MA = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {

            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setSelected(!isSelected());
            pressed = true;
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            hover = false;
            pressed = false;
            com.lazycoder.uiutils.ribbonmenu.RibbonBar.fired();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            hover = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            hover = true;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            hover = true;
        }

    };

}
