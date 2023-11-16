package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel;


//import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation.Animation;
import org.javadev.effects.Animation;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.CarouselPanel;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.ScalableImage;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.javadev.layout.AnimatingCardLayout;
import org.springframework.lang.Nullable;

/**
 * The <code>AnimatedCarousel</code> class is a utility used to store a
 * series of slides where, when transitioning to another slide, an animation
 * plays.
 *
 * @author Anthony Benavente
 * @version 5/4/15
 */
public class AnimatedCarousel extends JPanel implements MouseListener {

    /**
     * Animation that shows when changing to another slide
     */
    private Animation animation;

    /**
     * Slides in the slide show
     */
    private List<CarouselPanel> panels;

    /**
     * Current panel being displayed
     */
    private CarouselPanel currentPanel;

    /**
     * Index of the current panel in the list
     */
    private int currentPanelIndex;

    private AnimatingCardLayout cardLayout;

    /**
     * Creates a new AnimatedCarousel off a JPanel container
     *
     * @param container  JPanel containing this slide show
     * @param animation  the animation to use for transitions
     */
    public AnimatedCarousel(JPanel container,
                            Animation animation) {
        this(container.getWidth(), container.getHeight(), animation, null);
    }

    /**
     * Creates an AnimatedCarousel object
     *
     * @param width     the width of the slide show
     * @param height    the height of the slide show
     * @param animation the animation to play when transitioning to another
     *                  slide
     * @param panels    the slides to initialize the slide show to. This can be
     *                  null if you do not want slides.
     */
    public AnimatedCarousel(int width,
                            int height,
                            Animation animation,
                            @Nullable CarouselPanel[] panels) {
        setSize(new Dimension(width, height));
        this.panels = new ArrayList<>();
        this.animation = animation;
        this.currentPanelIndex = -1;

        setLayout(cardLayout = new AnimatingCardLayout(animation));
        if (panels != null) {
            initPanels(panels);
        }
        addMouseListener(this);
    }

    /**
     * Initializes the panels of the carousel through panels passed in.
     *
     * @param panels array of panels to add to the slide show
     */
    private void initPanels(CarouselPanel[] panels) {
        for (CarouselPanel panel : panels) {
            addPanel(panel);
        }
    }

    /**
     * Adds a panel to the slide show
     *
     * @param panel a CarouselPanel that is added to the carousel
     */
    public void addPanel(CarouselPanel panel) {
        if (currentPanel == null) {
            currentPanel = panel;
            currentPanelIndex = 0;
        }
        add(panel, "" + panels.size());
        panels.add(panel);
    }

    /**
     * Goes to the next slide if there are slides to go to
     */
    public void goToNextCard() {
        if (panels.size() > 1) {
            currentPanelIndex = (++currentPanelIndex) % panels.size();
            currentPanel = panels.get(currentPanelIndex);
            cardLayout.next(this);
        }
    }

    /**
     * Goes to the previous slide if there are slides to go to
     */
    public void goToPrevCard() {
        if (panels.size() > 1) {
            currentPanelIndex -= 1;
            if (currentPanelIndex < 0) {
                currentPanelIndex = panels.size() - 1;
            }
            currentPanel = panels.get(currentPanelIndex);
            cardLayout.previous(this);
        }
    }

    /**
     * Adds a panel to the slide show
     *
     * @param image       the image to be displayed on the slide
     * @param title       the title of the slide
     * @param description the description of the slide
     */
    public void addPanel(ScalableImage image,
                         String title,
                         String description) {
        addPanel(new CarouselPanel(getWidth(), getHeight(), image, title,
                description));
    }

    /**
     * Event fired when the mouse is clicked on the carousel. Override
     * this function for different functionality, but this goes to the next
     * slide.
     *
     * @param e mouse event fired
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        goToNextCard();
    }

    /**
     * Event fired when the mouse is pressed down on the carousel. Override
     * this function for functionality.
     *
     * @param e mouse event fired
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Event fired when the mouse is released in the carousel. Override
     * this function for functionality.
     *
     * @param e mouse event fired
     */
    @Override
    public void mouseReleased(MouseEvent e) { }

    /**
     * Event fired when the mouse enters the carousel. Override
     * this function for functionality.
     *
     * @param e mouse event fired
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Event fired when the mouse exits the carousel. Override
     * this function for functionality.
     *
     * @param e mouse event fired
     */
    @Override
    public void mouseExited(MouseEvent e) { }

    /**
     * Sets the animation to be used when transitioning slides
     *
     * @param animation the new animation to be displayed when transitioning
     *                  slides
     */
    public void setAnimation(Animation animation) {
        this.animation = animation;
        cardLayout.setAnimation(animation);
    }

    /**
     * Gets the animation to be used when transitioning slides
     *
     * @return the animation to be used when transitioning slides
     */
    public Animation getAnimation() {
        return animation;
    }
}
