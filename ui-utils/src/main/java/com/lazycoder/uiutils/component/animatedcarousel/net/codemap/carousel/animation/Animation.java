package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * The Animation class is an abstract class that holds the basis of an
 * animation. The animate method should be overridden to display the
 * animation itself.
 *
 * @author Anthony Benavente
 * @version 5/5/15
 */
public abstract class Animation {

    /**
     * The list of listeners notified during animations
     */
    private List<AnimationListener> listeners;

    /**
     * Direction the animation plays
     */
    private AnimationDirection direction;

    /**
     * Number of milliseconds the animation should last for
     */
    private int duration;

    /**
     * Creates a new Animation with 0 second animation period
     */
    public Animation() {
        this(0);
    }

    /**
     * Creates a new Animation with the given parameters
     *
     * @param duration amount of time for an animation to last
     */
    public Animation(int duration) {
        this.listeners = new ArrayList<>();
        this.direction = AnimationDirection.FORWARD;
        this.duration = duration;
    }

    /**
     * Call this function to start the animation of the components from the
     * first to the second
     *
     * @param first  the first component that is displayed in the animation
     * @param second the last component displayed at the end of the animation
     * @return the component animated on
     */
    public Component animate(Component first, Component second) {
        return generateAnimatedPanel(first, second);
    }

    /**
     * Generates the AnimatedPanel to be used when displaying the animation
     *
     * @param first  the first component to display
     * @param second the second component to display
     * @return the animated panel that was initialized
     */
    protected abstract AnimatedPanel generateAnimatedPanel(Component first,
                                                           Component second);

    /**
     * Gets the direction the animation should move in
     *
     * @return the direction the animation should move in
     */
    public AnimationDirection getDirection() {
        return direction;
    }

    /**
     * Sets the direction the animation should move in
     *
     * @param direction the direction the animation should move in
     */
    public void setDirection(AnimationDirection direction) {
        this.direction = direction;
    }

    /**
     * Adds a listener that listens to the animation's status
     *
     * @param listener the listener to add
     */
    public void addAnimationListener(AnimationListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes an AnimationListener from the list of listeners to be notified
     *
     * @param listener the listener to remove
     */
    public void removeAnimationListener(AnimationListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that an animation started
     */
    protected void fireAnimationStart() {
        listeners.forEach(AnimationListener::onAnimationStart);
    }

    /**
     * Notifies all listeners that an animation ended
     */
    protected void fireAnimationEnd() {
        listeners.forEach(AnimationListener::onAnimationEnd);
    }

    /**
     * Gets the duration of the animation
     *
     * @return the duration of the animation
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the animation
     *
     * @param duration the duration of the animation
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * These are the directions an animation can go. Some animations do not
     * have direction (Fade).
     */
    public enum AnimationDirection {
        FORWARD,
        BACKWARD
    }
}
