package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel;


//import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.animation.Animation;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.ScalableImage;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import org.javadev.effects.Animation;
import org.javadev.layout.AnimatingCardLayout;
import org.springframework.lang.Nullable;

/**
 * 根据原来相应部分代码稍作修改
 */
public class OperatingTipAnimatedCarousel extends JPanel {

    /**
     * This is the list of transitions used when demoing the carousel
     */
    private static final String[] TRANSITIONS = new String[]{
            "Cube Animation", "Dashboard Animation", "Fade Animation", "Iris Animation", "Radial Animation", "Slide Animation"
    };

    /**
     * Animation that shows when changing to another slide
     */
    private Animation animation;

    /**
     * Slides in the slide show
     */
    private List<OperatingTipCarouselPanel> panels = null;

    /**
     * Current panel being displayed
     */
    private OperatingTipCarouselPanel currentPanel;

    /**
     * Index of the current panel in the list
     */
    private int currentPanelIndex = 0;

    private AnimatingCardLayout cardLayout;

    /**
     * Creates a new AnimatedCarousel off a JPanel container
     *
     * @param container JPanel containing this slide show
     * @param animation the animation to use for transitions
     */
    public OperatingTipAnimatedCarousel(JPanel container,
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
    public OperatingTipAnimatedCarousel(int width,
                                        int height,
                                        Animation animation,
                                        @Nullable OperatingTipCarouselPanel[] panels) {
        setSize(new Dimension(width, height));
        this.panels = new ArrayList<>();
        this.animation = animation;
        this.currentPanelIndex = -1;

        setLayout(cardLayout = new AnimatingCardLayout(animation));
        if (panels != null) {
            initPanels(panels);
        }
    }

    /**
     * Initializes the panels of the carousel through panels passed in.
     *
     * @param panels array of panels to add to the slide show
     */
    private void initPanels(OperatingTipCarouselPanel[] panels) {
        for (OperatingTipCarouselPanel panel : panels) {
            addPanel(panel);
        }
    }

    /**
     * Adds a panel to the slide show
     *
     * @param panel a CarouselPanel that is added to the carousel
     */
    public void addPanel(OperatingTipCarouselPanel panel) {
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
//            try {
            setAnimation(getRandomAnimation());
            currentPanelIndex = (++currentPanelIndex) % panels.size();
            currentPanel = panels.get(currentPanelIndex);
            cardLayout.next(this);
//            } catch (IllegalStateException e) {
//
//            }
        }
    }

    /**
     * 获取当前的图片总数
     *
     * @return
     */
    public int getPictureNum() {
        return panels == null ? 0 : panels.size();
    }

    /**
     * 获取当前的图片的序号
     * @return
     */
    public int getCurrentPictureIndex() {
        return currentPanelIndex;
    }

    /**
     * Goes to the previous slide if there are slides to go to
     */
    public void goToPrevCard() {
        if (panels.size() > 1) {
//            try {
            setAnimation(getRandomAnimation());
            currentPanelIndex -= 1;
            if (currentPanelIndex < 0) {
                currentPanelIndex = panels.size() - 1;
            }
            currentPanel = panels.get(currentPanelIndex);
            cardLayout.previous(this);
//            } catch (IllegalStateException e) {
//
//            }
        }
    }

    /**
     * Gets the animation from the selected item in the combo box
     *
     * @return the animation selected in the combo box
     */
    public static Animation getRandomAnimation() {
        Animation result = null;
        try {
            Random random = new Random();
            int r = random.nextInt(5) + 1;
            String selectedClass = TRANSITIONS[r]
                    .replace(" ", "");
            Class loadedClass = Class.forName("org.javadev.effects." +
                    selectedClass);
            result = (Animation) loadedClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Adds a panel to the slide show
     *
     * @param image the image to be displayed on the slide
     */
    public void addPanel(ScalableImage image) {
        addPanel(new OperatingTipCarouselPanel(getWidth(), getHeight(), image));
    }

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
