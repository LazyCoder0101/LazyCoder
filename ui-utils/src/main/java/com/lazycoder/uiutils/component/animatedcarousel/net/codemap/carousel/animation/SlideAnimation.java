package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The SlideAnimation class shows a slide animation where the second
 * component slides into where the first component was.
 *
 * @author Anthony Benavente
 * @version 5/6/15
 */
public class SlideAnimation extends Animation {

    /**
     * Generates the slide animation panel
     *
     * @param first  the first component to display
     * @param second the second component to display
     * @return the panel to display
     */
    @Override
    protected AnimatedPanel generateAnimatedPanel(Component first,
                                                  Component second) {
        return new SlideAnimatedPanel(this, first, second, getDuration());
    }

    /**
     * This is a custom AnimatedPanel that shows a slide animation
     */
    class SlideAnimatedPanel extends AnimatedPanel {

        /**
         * Starting x of the second image
         */
        private int startX;

        /**
         * Ending x point of the second image
         */
        private int endX;

        /**
         * The current x of the second image
         */
        private int currentX;

        /**
         * Creates an AnimatedPanel with the given parameters.
         *
         * @param parent            the animation that is using this panel
         * @param start             the component to show first
         * @param end               the component that is shown by the end
         * @param animationDuration the amount of time the animation lasts
         */
        public SlideAnimatedPanel(Animation parent,
                                  Component start,
                                  Component end,
                                  int animationDuration) {
            super(parent, start, end, animationDuration);
            if (getAnimationParent().getDirection() ==
                    AnimationDirection.FORWARD) {
                startX = start.getWidth();
                endX = 0;
            } else {
                startX = 0;
                endX = start.getWidth();
            }
            currentX = startX;
        }

        /**
         * Paints the images onto the screen
         *
         * @param g graphics object used to draw to the screen
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            Graphics2D g2d = (Graphics2D) g;

            if (shouldStart) {
                fireAnimationStart();
                startThread(e -> {
                    if (getDirection() == AnimationDirection.FORWARD) {
                        currentX = (int) (startX * ((endProgress - progress)
                                / endProgress));
                    } else {
                        currentX = -(int) (endX * ((endProgress -
                                progress)
                                / endProgress)) + startX;
                    }
                    if (progress + deltaProgress >= endProgress) {
                        fireAnimationEnd();
                    }
                });
            }

        if (getDirection() == AnimationDirection.FORWARD) {
            g2d.drawImage(firstImage, null, 0, 0);
            g2d.drawImage(secondImage, null, currentX, 0);
        } else {
            g2d.drawImage(firstImage, null, 0, 0);
            g2d.drawImage(secondImage, null, currentX, 0);
        }
        }

    }
}
