package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.ScalableImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * 根据原来相应部分代码稍作修改
 */
public class OperatingTipCarouselPanel extends JPanel {

    /**
     * Color used to draw the info panel
     */
    private Color infoPanelColor;

    /**
     * Image displayed in this panel
     */
    private ScalableImage image;


    /**
     * Creates a new CarouselPanel with the given image, title, and description
     *
     * @param image       image to be displayed for this panel
     */
    public OperatingTipCarouselPanel(int width,
                                     int height,
                                     ScalableImage image) {
        setSize(width, height);
        this.image = image;
        this.infoPanelColor = new Color(0, 0, 0, 128);
    }

    /**
     * Gets the image displayed in this panel
     *
     * @return the image displayed in this panel
     */
    public ScalableImage getImage() {
        return image;
    }

    /**
     * Sets the image displayed in this panel
     *
     * @param image the new image to be displayed in this panel
     */
    public void setImage(ScalableImage image) {
        this.image = image;
    }

    /**
     * Draws the image and info panel to the screen
     *
     * @param g graphics object used to draw the panel
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        image.paint(g2d);
        drawInfoPanel(g2d);
    }

    /**
     * Draws the info panel to the screen. This is the box with the title
     * and the description
     *
     * @param g2d the graphics object used to draw to the screen
     */
    private void drawInfoPanel(Graphics2D g2d) {
//        g2d.setColor(infoPanelColor);
//
//        int panelY = (2*getParent().getHeight()) / 3;
//        int panelWidth = getParent().getWidth();
//        int panelHeight = getParent().getHeight();

//        g2d.fillRect(0, panelY, panelWidth, panelHeight);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setFont(titleFont);
//        g2d.setColor(titleColor);
//        g2d.drawString(title, 10, panelY + 35);
//        g2d.setFont(descriptionFont);
//        g2d.setColor(descriptionColor);
//        g2d.drawString(description, 10, panelY + 60);
    }
}
