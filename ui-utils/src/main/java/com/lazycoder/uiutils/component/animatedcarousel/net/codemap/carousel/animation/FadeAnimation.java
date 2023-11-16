package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation;

import java.awt.*;

/**
 * This animation shows fading from one object to another
 *
 * @author Anthony Benavente
 * @version 5/5/15
 */
public class FadeAnimation extends Animation {

    /**
     * Generates the animated panel based off the one made in this class
     *
     * @param first the first component to display
     * @param second the second component to display
     * @return the FadeAnimatedPanel
     */
    @Override
    protected AnimatedPanel generateAnimatedPanel(Component first,
                                                  Component second) {
        return new FadeAnimatedPanel(this, first, second, getDuration());
    }

    /**
     * This is a custom AnimatedPanel that shows a fading animation
     */
    class FadeAnimatedPanel extends AnimatedPanel {

        /**
         * This is the current opacity
         */
        private float opacity;

        /**
         * Creates an AnimatedPanel with the given parameters.
         *
         * @param parent            the animation that is using this panel
         * @param start             the component to show first
         * @param end               the component that is shown by the end
         * @param animationDuration the amount of time the animation lasts
         */
        public FadeAnimatedPanel(Animation parent, Component start,
                                 Component end, int animationDuration) {
            super(parent, start, end, animationDuration);
        }

        /**
         * Overridden to draw the animation
         *
         * @param g graphics object used to draw to the screen
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            if (shouldStart) {
                fireAnimationStart();
                startThread(e -> {
                    opacity = progress / endProgress;
                    if (progress + deltaProgress >= endProgress) {
                        fireAnimationEnd();
                    }
                });
            }

            if (firstImage != null && secondImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                opacity = opacity < 0 ? 0 : opacity > 1 ? 1 : opacity;
                drawImages(g2d, opacity);
            }
        }

        /**
         * Draws the two images in how they should be drawn for the Fade
         * animation
         */
        private void drawImages(Graphics2D g2d, float alpha) {
            Composite oldComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite
                    .SRC_ATOP, 1 - alpha));
            g2d.drawImage(firstImage, null, 0, 0);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite
                    .SRC_OVER, alpha));
            g2d.drawImage(secondImage, null, 0, 0);
            g2d.setComposite(oldComposite);
        }
    }
}
