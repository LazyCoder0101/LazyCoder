package com.lazycoder.uiutils.mycomponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyIconLabel extends JPanel {

    protected Color friendContainerBackColor = new Color(224, 242, 251);//new Color(252, 240, 193);

    protected Font font = new Font("微软雅黑", Font.PLAIN, 14);

    private boolean hover = false;

    protected JLabel picLabel, textLabel;

    protected void init(String text, ImageIcon icon) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 7, 3));
//        setLayout(new BorderLayout());
//        setLayout(null);
        setFont(font);

        picLabel = new JLabel(icon);
//        picLabel.setBounds(0, 5, icon.getIconWidth(), icon.getIconHeight());
        picLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        add(picLabel);

        textLabel = new JLabel(text);
//        textLabel.setBounds(picLabel.getLocation().x, 5, 200, 40);
        add(textLabel);

        setOpaque(false);
        setBackground(friendContainerBackColor);
        addMouseListener(adapter);
    }

    protected MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            hover = true;
            repaint();
            super.mouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            hover = false;
            repaint();
            super.mouseExited(e);
        }

    };

    @Override
    protected void paintComponent(Graphics g) {
        if (hover) {
            g.setColor(friendContainerBackColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }

}
