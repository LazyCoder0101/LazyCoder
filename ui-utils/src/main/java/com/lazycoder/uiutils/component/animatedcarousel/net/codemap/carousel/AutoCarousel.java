package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.CarouselPanel;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.javadev.effects.Animation;
import org.springframework.lang.Nullable;

/**
 *
 * 包 com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel部分源码 摘自 https://github.com/anthony-benavente/jswing-animated-carousel
 *
 * The <code>AutoCarousel</code> class is an extension of the
 * <code>AnimatedCarousel</code> but is now equipped with a swing timer that
 * automatically switches to another slide after a given interval.
 *
 * @author Anthony Benavente
 * @version 5/4/15
 */
public class AutoCarousel extends AnimatedCarousel {

    /**
     * This is the timer in charge of making slide transitions automatically
     */
    private Timer timer;

    /**
     * Creates an AutoCarousel object with a set timer delay
     *
     * @param container  the containing JPanel
     * @param animation  the transition animation
     * @param timerDelay the amount of time it takes to transition to
     *                   another slide
     */
    public AutoCarousel(JPanel container,
                        Animation animation,
                        int timerDelay) {
        this(container.getWidth(), container.getHeight(), animation,
                null, timerDelay);
    }

    /**
     * Creates an AutoCarousel object with a set timer delay
     *
     * @param width      the width of the slide show
     * @param height     the height of the slide show
     * @param animation  the transition animation
     * @param panels     the displayed in the slide show
     * @param timerDelay the amount of time it takes for the slide show to
     *                   transition
     */
    public AutoCarousel(int width,
                        int height,
                        Animation animation,
                        @Nullable CarouselPanel[] panels,
                        int timerDelay) {
        super(width, height, animation, panels);
        this.timer = new Timer(timerDelay, e -> goToNextCard());
    }

    /**
     * Starts the auto carousel
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops the auto carousel
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Overrides the mouse click function to restart the timer whenever the
     * slide show is clicked.
     *
     * @param e the mouse event fired
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        timer.restart();
    }

    /**
     * Transitions to the next slide but restarts the timer when it does.
     */
    @Override
    public void goToNextCard() {
        super.goToNextCard();
        timer.restart();
    }

    /**
     * Transitions to the previous slide but restarts the timer when it does.
     */
    @Override
    public void goToPrevCard() {
        super.goToPrevCard();
        timer.restart();
    }
}
