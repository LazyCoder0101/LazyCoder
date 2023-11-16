package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * The <code>ScalableImage</code> class is an AWT component that can display
 * images at a set width and height.
 *
 * @author Anthony Benavente
 * @version 4/22/15
 */
public class ScalableImage extends Canvas {

    /**
     *  The original image passed to the class. This is used when changing the
     *  size of the image.
     */
    private Image originalImage;

    /**
     * This is the scaled image that is actually drawn
     */
    private Image image;

    /**
     * Whether the image is centered horizontally
     */
    private boolean centerH;

    /**
     * Whether the image is centered vertically
     */
    private boolean centerV;

    /**
     * The border to be drawn around this image
     */
    private Border border;

    /**
     * Creates an instance of a ScalableImage with the desired attributes
     *
     * @param imagePath path to the image file
     * @param width desired width of the new image
     * @param height desired height of the new image
     */
    public ScalableImage(String imagePath, int width, int height) {
        this.centerH = false;
        this.centerV = false;
        this.border = new EmptyBorder(0, 0, 0, 0);
        setSize(width, height);
        setImagePath(imagePath, width, height);
        repaint();
    }

    /**
     * Determines if the image is centered horizontally and/or vertically and
     * draws the image with
     * those dimensions.
     *
     * @param g graphics used to draw image
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        border.paintBorder(this, g, 0, 0, getWidth(), getHeight());

        int drawX = centerH ? getParent().getWidth() / 2 - getWidth() / 2
                            : 0;
        int drawY = centerV ? getParent().getHeight() / 2 - getHeight() / 2
                            : 0;
        g.drawImage(image, drawX, drawY, null);
    }

    /**
     * Changes the path of the original image
     *
     * @param path the path to the new image
     */
    public void setImagePath(String path) {
        setImagePath(path, getWidth(), getHeight());
    }

    /**
     * Changes the path of the original image and the desired width and height
     * values
     *
     * @param path the path to the new image
     * @param width the desired width
     * @param height the desired height
     */
    public void setImagePath(String path, int width, int height) {
        try {
            originalImage = ImageIO.read(new File(path));
            image = originalImage.getScaledInstance(width,
                    height, Image.SCALE_FAST);
            setSize(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        repaint();
    }

    /**
     * Gets if the image is centered horizontally
     *
     * @return if the image is centered horizontally
     */
    public boolean isCenterH() {
        return centerH;
    }

    /**
     * Sets if image is centered horizontally
     *
     * @param centerH if the image should be centered horizontally
     */
    public void setCenterH(boolean centerH) {
        this.centerH = centerH;
    }

    public boolean isCenterV() {
        return centerV;
    }

    /**
     * Sets if image is centered vertically
     *
     * @param centerV if the image should be centered vertically
     */
    public void setCenterV(boolean centerV) {
        this.centerV = centerV;
    }

    /**
     * Sets the width of the image being displayed
     *
     * @param width the new width for the image
     */
    public void setWidth(int width) {
        super.setSize(width, getHeight());
        image = originalImage.getScaledInstance(width,
                getHeight(), Image.SCALE_FAST);
        repaint();
    }

    /**
     * Sets the height the image being displayed
     *
     * @param height the new height for the image
     */
    public void setHeight(int height) {
        super.setSize(getWidth(), height);
        image = originalImage.getScaledInstance(getWidth(),
                height, Image.SCALE_FAST);
        repaint();
    }

    /**
     * Gets the border around this component
     *
     * @return the border around this component
     */
    public Border getBorder() {
        return border;
    }

    /**
     * Sets the border around this component
     *
     * @param border the new border to draw around this component
     */
    public void setBorder(Border border) {
        this.border = border;
    }
}
