package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * 该源码从网上摘取，时间过于久远，已找不到当初出处
 * The AnimatedPanel is used during animations to draw the actual animation
 * to the screen.
 *
 * @author Anthony Benavente
 * @version 5/5/15
 */
public abstract class AnimatedPanel extends JPanel {

    /**
     * This is the duration the animation should last for
     */
    private int animationDuration;

    /**
     * This is the animation that is using this panel
     */
    private Animation parent;

    /**
     * This is the component that will be replaced
     */
    private Component start;

    /**
     * This is the component replacing start
     */
    private Component end;

    /**
     * Notifies the component that it should start its animating
     */
    protected boolean shouldStart;

    /**
     * Amount of time spent drawing the animation
     */
    protected float totalDrawTime;

    /**
     * Amount of time between updates
     */
    protected float deltaTime;

    /**
     * Progress tracking the animation process
     */
    protected float progress;

    /**
     * Value at which the progress must reach to stop the value
     */
    protected float endProgress;

    /**
     * Amount to progress after each update
     */
    protected float deltaProgress;

    /**
     * Image of first component
     */
    protected BufferedImage firstImage;

    /**
     * Image of second component
     */
    protected BufferedImage secondImage;

    /**
     * Creates an AnimatedPanel with the given parameters.
     *
     * @param parent            the animation that is using this panel
     * @param start             the component to show first
     * @param end               the component that is shown by the end
     * @param animationDuration the amount of time the animation lasts
     */
    public AnimatedPanel(Animation parent, Component start, Component end,
                         int animationDuration) {
        this.parent = parent;
        this.start = start;
        this.end = end;
        this.animationDuration = animationDuration;
        this.shouldStart = true;
        this.deltaProgress = 0.5f;
        this.progress = 0f;
        this.endProgress = 100f;
        this.firstImage = createImageFromComponent(getStart());
        this.secondImage = createImageFromComponent(getEnd());
        this.deltaTime = Math.round(animationDuration *
                deltaProgress / endProgress);
        setPreferredSize(getMaxComponentSize(start, end));
    }

    /**
     * Gets the largest dimensions of two components
     *
     * @param start the starting component
     * @param end   the ending component
     * @return the dimension that is the largest of the two
     */
    protected Dimension getMaxComponentSize(Component start,
                                            Component end) {
        Dimension max = start.getSize();
        if (max.getWidth() < end.getWidth() ||
                max.getHeight() < end.getHeight()) {
            max = end.getSize();
        }
        return max;
    }

    /**
     * Overridden to call the paint method as well
     *
     * @param g graphics object to draw to the window
     */
    @Override
    public void update(Graphics g) {
        super.update(g);
        paint(g);
    }

    /**
     * Override this method to use custom animation
     *
     * @param g graphics object used to draw to the screen
     */
    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * Creates an image off of an AWT component
     *
     * @param c component to get the image of
     * @return the image displaying the component passed in
     */
    protected BufferedImage createImageFromComponent(Component c) {
        BufferedImage result = null;
        if (c != null) {
            result = generateBlankImage(c);
            if (result != null) {
                Graphics g = result.getGraphics();
                c.paint(g);
                g.dispose();
            }
        }
        return result;
    }

    /**
     * Generates a blank image to the size of the component
     *
     * @param c the component to use to make the image
     * @return the newly made blank image
     */
    private BufferedImage generateBlankImage(Component c) {
        BufferedImage result;
        GraphicsEnvironment env = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        boolean hasAlpha = config.getColorModel().hasAlpha();
        if (hasAlpha) {
            result = config.createCompatibleImage(c.getWidth(), c.getHeight());
        } else {
            result = new BufferedImage(c.getWidth(), c.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
        }
        return result;
    }

    /**
     * Gets the amount of time the animation lasts
     *
     * @return the amount of time the animation lasts
     */
    public int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * Sets the amount of time the animation lasts
     *
     * @param animationDuration the amount of time the animation lasts
     */
    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        deltaTime = Math.round(animationDuration *
                deltaProgress / endProgress);
    }

    /**
     * Gets the animation that is using this panel
     *
     * @return the animation that is using this panel
     */
    public Animation getAnimationParent() {
        return parent;
    }

    /**
     * Sets the animation that is using this panel
     *
     * @param parent the animation that is using this panel
     */
    public void setAnimationParent(Animation parent) {
        this.parent = parent;
    }

    /**
     * Gets the component that will be replacedd
     *
     * @return the component that will be replaced
     */
    public Component getStart() {
        return start;
    }

    /**
     * Sets the component that will be replaced
     *
     * @param start the component that will be replaced
     */
    public void setStart(Component start) {
        this.start = start;
    }

    /**
     * Gets the component replacing the start
     *
     * @return the component replacing the start
     */
    public Component getEnd() {
        return end;
    }

    /**
     * Sets the component replacing the start
     *
     * @param end the component replacing the start
     */
    public void setEnd(Component end) {
        this.end = end;
    }

    /**
     * Starts the thread in charge of handling updates
     */
    protected void startThread(ActionListener actionPerformed) {
        shouldStart = false;
        new Thread(() -> {
            progress = 0;
            while (progress < endProgress) {
                actionPerformed.actionPerformed(null);
                progress += deltaProgress;
                try {
                    Thread.sleep((long) deltaTime);
                    repaint();
                    getToolkit().sync();
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }
}
