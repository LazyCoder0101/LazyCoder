package com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.components.ScalableImage;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.PictureTools;
import com.lazycoder.uiutils.ui.MyFlatUI.MyFlatButtonUI;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.RealNumberUtil;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.javadev.effects.Animation;
import org.javadev.effects.DashboardAnimation;

/**
 * 根据原来相应部分代码稍作修改
 */
public class OperatingTipAnimatedCarouselPane extends JPanel {

    /**
     * The carousel shown on the screen
     */
    protected OperatingTipAnimatedCarousel carousel;

    private JLabel infoLabel;

    private MyButton previousBt, nextBt;

    /**
     * Creates a new AnimatedCarouselTest
     */
    private OperatingTipAnimatedCarouselPane(String path) {
        setLayout(new BorderLayout());
        setSize(680, 530);
        add(initCarousel(path), BorderLayout.CENTER);
        add(initControlPanel(), BorderLayout.SOUTH);
        showCurrentInfo();
    }

    /**
     * Initializes the control panel
     *
     * @return the initialized control panel
     */
    private JPanel initControlPanel() {
        JPanel panel = new JPanel();

        infoLabel = new JLabel();
        panel.add(infoLabel);
        panel.add(Box.createHorizontalStrut(80));

        MyFlatButtonUI buttonUI = new MyFlatButtonUI();
        previousBt = new MyButton("上一页");
        previousBt.setUI(buttonUI);
        panel.add(previousBt);
        previousBt.addActionListener(e -> goToPrevCard());

        panel.add(Box.createHorizontalStrut(80));

        nextBt = new MyButton("下一页");
        nextBt.setUI(buttonUI);
        panel.add(nextBt);
        nextBt.addActionListener(e -> goToNextCard());
//        transitions = new JComboBox<>();
//        for (String transitionName : TRANSITIONS) {
//            transitions.addItem(transitionName + " Animation");
//        }
//        transitions.addActionListener(e -> carousel.setAnimation(getAnimation()));
//        panel.add(transitions);
        panel.add(Box.createHorizontalStrut(150));
        return panel;
    }

    private void goToPrevCard() {
        carousel.goToPrevCard();
        mouseMoveUp();//把鼠标移开当前按钮所在位置，避免此时在切换过程中用户又点击按钮导致继续切换
    }

    private void goToNextCard() {
        carousel.goToNextCard();
        mouseMoveUp();//把鼠标移开当前按钮所在位置，避免此时在切换过程中用户又点击按钮导致继续切换
    }

    private void mouseMoveUp() {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        // Search the devices for the one that draws the specified point.
        for (GraphicsDevice device : gs) {
            GraphicsConfiguration[] configurations =
                    device.getConfigurations();
            Point p = MouseInfo.getPointerInfo().getLocation();
            try {
                Robot r = new Robot(device);
                r.mouseMove(p.x + 70, p.y - 200);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Initializes an AnimatedCarousel and returns it
     *
     * @return the newly initialized carousel
     */
    protected JPanel initCarousel(String path) {
        Animation animation = new DashboardAnimation();
        animation.setAnimationDuration(300);

        carousel = new OperatingTipAnimatedCarousel(getWidth(), getHeight(),
                animation, null) {

            @Override
            public void goToNextCard() {
                super.goToNextCard();
                showCurrentInfo();
            }

            @Override
            public void goToPrevCard() {
                super.goToPrevCard();
                showCurrentInfo();
            }
        };
        loadImagesToCarousel(path);
        return carousel;
    }

    /**
     * Loads images from the res/img folder into the carousel
     */
    protected void loadImagesToCarousel(String path) {
//        File[] imageFiles = new File("res/img").listFiles();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] imageFiles = file.listFiles(PictureTools.myFilenameFilter());
            orderByName(imageFiles);
            if (imageFiles != null) {
                for (File f : imageFiles) {
                    ScalableImage image = new ScalableImage(f.getAbsolutePath(),
                            carousel.getWidth(), carousel.getHeight());
                    carousel.addPanel(image);
                }
            }
        } else if (file.isFile()) {
            ScalableImage image = new ScalableImage(file.getAbsolutePath(),
                    carousel.getWidth(), carousel.getHeight());
            carousel.addPanel(image);
        }
    }

    private void showCurrentInfo() {
        infoLabel.setText((carousel.getCurrentPictureIndex() + 1) + "/" + carousel.getPictureNum());
    }

    /**
     * 按名字排序
     *
     * @param sourceSiles
     */
    public static void orderByName(File[] sourceSiles) {
        List fileList = Arrays.asList(sourceSiles);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }
                if (RealNumberUtil.isInteger(FileUtil.getFileNameNoEx(o1.getName())) && RealNumberUtil.isInteger(FileUtil.getFileNameNoEx(o2.getName()))) {
                    //如果文件名完全是数字的话，直接返回两个数字之间的差值，避免出现类似于10排在2前面的情况
                    return RealNumberUtil.convertedToInteger(FileUtil.getFileNameNoEx(o1.getName())) -
                            RealNumberUtil.convertedToInteger(FileUtil.getFileNameNoEx(o2.getName()));
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
    }


    /**
     * @param path
     * @return
     */
    public static JDialog creatHelpAnimatedCarouselFrame(String path) {//"I:/图片"
        LazyCoderCommonDialog frame = new LazyCoderCommonDialog();
        frame.setTitle("操作提示");
        frame.setSize(680, 600);
        frame.setContentPane(new OperatingTipAnimatedCarouselPane(path));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

}
