package com.lazycoder.uiutils.folder;

import javax.swing.JComponent;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * 折叠面板，其他带有折叠功能的面板都要继承该类
 *
 * @author Administrator
 */
public class Folder extends JComponent {

    /**
     * 默认宽度比例
     */
    public final static double DEFAULT_WIDTH_PROPORTION = 0.26;
    /**
     *
     */
    private static final long serialVersionUID = -5042223586935981079L;
    // 缺省caption的高度
    private static int CAPTION_HEIGHT = 25;
    /**
     * 放应用程序的抽屉
     */
    private Drawer drawerComponent = null;

    private FoldButton hiddenBt = null;

    public Drawer getDrawer() {
        return drawerComponent;
    }

    public void setDrawer(Drawer drawer) {
        this.drawerComponent = drawer;
    }

    /**
     * 设置为默认布局
     */
    public void setDefaultLayout() {
        setLayout(new UseCodeFolderTabLayout());
    }

    public Dimension getRequiredDimension() {
        int w = drawerComponent.getContentWidth();
        // 高度是抽屉的高度加上标题的高度，抽屉的高度是目前抽出的长度
        int h = (int) (drawerComponent.getContentHeight() * drawerComponent.getRatio()) + CAPTION_HEIGHT;
        return new Dimension(w, h);
    }

    /**
     * 继承该类的时候该方法都要重写
     *
     * @return
     */
    public int getRequiredHeight() {
        return (int) (drawerComponent.getContentHeight() * drawerComponent.getRatio()) + CAPTION_HEIGHT;
    }

    /**
     * 收起面板
     */
    public void packUpHiddenPanel() {
        if (getHiddenButton() != null) {
            if (getHiddenButton().isExpanded() == true) {
                getHiddenButton().doClick();
            }
        }
    }

    /**
     * 展开面板
     */
    public void expandHiddenPanel() {
        if (getHiddenButton() != null) {
            if (getHiddenButton().isExpanded() == false) {
                getHiddenButton().doClick();
            }
        }
    }

    /**
     * 获取抽屉组件必要的隐藏按钮
     *
     * @return
     */
    public FoldButton getHiddenButton() {
        return hiddenBt;
    }

    /**
     * 设置抽屉组件必要的隐藏按钮
     *
     * @param hiddenButton
     */
    public void setHiddenButton(FoldButton hiddenButton) {
        this.hiddenBt = hiddenButton;
    }

    /**
     * 负责布局面板组件
     */
    class UseCodeFolderTabLayout implements LayoutManager {
        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            // TODO Auto-generated method stub
            return parent.getPreferredSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            // TODO Auto-generated method stub
            return parent.getMinimumSize();
        }

        @Override
        public void layoutContainer(Container parent) {
            // TODO Auto-generated method stub
            int w = parent.getWidth();
            int h = parent.getHeight();
            // 标题栏总是固定高度
            hiddenBt.setBounds(0, 0, w, CAPTION_HEIGHT);
            // 抽屉只显示抽出的比例
            drawerComponent.setBounds(0, CAPTION_HEIGHT, w, h - CAPTION_HEIGHT);
        }
    }

}
