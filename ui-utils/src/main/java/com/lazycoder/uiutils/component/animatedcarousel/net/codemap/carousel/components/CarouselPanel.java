package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * The <code>CarouselPanel</code> class is the placeholder used for each slide
 * in the <code>AnimatedCarousel</code>.
 *
 * @author Anthony Benavente
 * @version 5/4/15
 * @see AnimatedCarousel
 */
public class CarouselPanel extends JPanel {

    /**
     * Color used to draw the info panel
     */
    private Color infoPanelColor;

    /**
     * Image displayed in this panel
     */
    private ScalableImage image;

    /**
     * Title to be shown in the information panel
     */
    private String title;

    /**
     * Description shown in the information panel
     */
    private String description;

    /**
     * Font to use for the title
     */
    private Font titleFont;

    /**
     * The color of the title text
     */
    private Color titleColor;

    /**
     * Font to use for the description
     */
    private Font descriptionFont;

    /**
     * The color of the description text
     */
    private Color descriptionColor;

    /**
     * Creates a new CarouselPanel with the given image, title, and description
     *
     * @param image       image to be displayed for this panel
     * @param title       the title of the panel shown in the info section
     * @param description the description to be shown in the info section
     */
    public CarouselPanel(int width,
                         int height,
                         ScalableImage image,
                         String title,
                         String description) {
        setSize(width, height);
        this.image = image;
        this.title = title;
        this.description = description;
        this.infoPanelColor = new Color(0, 0, 0, 128);
        this.titleFont = new Font(null, Font.BOLD, 18);
        this.descriptionFont = new Font(null, Font.PLAIN, 10);
        this.titleColor = Color.white;
        this.descriptionColor = Color.white;
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
     * Gets the title to be shown in the information panel
     *
     * @return title to be shown in the information panel
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title to be shown in the information panel
     *
     * @param title the new title to be shown in the information panel
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description to be shown in the information panel
     *
     * @return the description to be shown in the information panel
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description to be shown in the information panel
     *
     * @param description the new description to be shown in the information
     *                    panel
     */
    public void setDescription(String description) {
        this.description = description;
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
        g2d.setColor(infoPanelColor);

        int panelY = (2*getParent().getHeight()) / 3;
        int panelWidth = getParent().getWidth();
        int panelHeight = getParent().getHeight();

        g2d.fillRect(0, panelY, panelWidth, panelHeight);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(titleFont);
        g2d.setColor(titleColor);
        g2d.drawString(title, 10, panelY + 35);
        g2d.setFont(descriptionFont);
        g2d.setColor(descriptionColor);
        g2d.drawString(description, 10, panelY + 60);
    }
}
