package com.lazycoder.uiutils.folder;

import com.lazycoder.uiutils.utils.SysUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FolderPane extends JComponent implements Scrollable {

    /**
     *
     */
    private static final long serialVersionUID = 4566372165291342813L;

    // 所有的面板
    protected ArrayList<Folder> tabs = new ArrayList<Folder>();

    // 是否启动动画效果
    private boolean animated = true;

    /**
     * 组件之间的间隔
     */
    private int intterTabPadding = 15, minIntterTabPadding = 10;

    /**
     * @param intterTabPadding 组件之间的间隔
     */
    public FolderPane(int intterTabPadding) {
        // TODO Auto-generated constructor stub
        this.intterTabPadding = intterTabPadding;
        setFolderPaneUI();
        setFolderPaneLayout();
    }

    protected void setFolderPaneUI(){
        setUI(new FoldPaneUI());
    }

    protected void setFolderPaneLayout(){
        setLayout(new FolderPaneLayout());
    }

    /**
     * 添加功能容器
     *
     * @param container
     */
    public void addContainer(Folder container) {
        tabs.add(container);
        this.add(container);
        // 为该面板添加抽屉事件
        ((FoldPaneUI) ui).addTab(container);
        SysUtil.updateFrameUI(container);
    }

    public void setDeafaultLayout() {
        setLayout(new FolderPaneLayout());
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        // TODO Auto-generated method stub
        return getPreferredSize();
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        return 100;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        // TODO Auto-generated method stub
        return false;
    }

    public ArrayList<Folder> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<Folder> tabs) {
        this.tabs = tabs;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getIntterTabPadding() {
        return intterTabPadding;
    }

    // 获得该面板目前所需的空间大小：drawer+caption_height
    public Dimension getRequiredDimension() {
        int w = 500, h = 300;
        Dimension dimensionTemp;
        for (int i = 0; i < tabs.size(); i++) {
            if (i == 0) {
                h = 0;
            }
            dimensionTemp = tabs.get(i).getRequiredDimension();
            if (dimensionTemp.getWidth() > 500) {
                w = (int) dimensionTemp.getWidth();
            }
            h = h + (int) (dimensionTemp.getHeight() + intterTabPadding);
        }
        return new Dimension(w, h);
    }

    // 该布局类的布局管理器
    class FolderPaneLayout implements LayoutManager {
        // private static final int INTER_TAB_PADDING = 15;

        public FolderPaneLayout() {
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            // 补充实现preferredSize以便在JScrollPane调整时使用
            Insets insets = parent.getInsets();
            int w = 0;
            int h = 0;
            for (Folder tab : tabs) {
                Dimension dim = tab.getRequiredDimension();
                if (dim.width > w) {
                    w = dim.width;
                }
                h += dim.height + intterTabPadding;
            }
            w += insets.left + insets.right;
            h += insets.top + insets.bottom;
            return new Dimension(w, h);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top;
            for (Folder tab : tabs) {
                Dimension dim = tab.getRequiredDimension();
                tab.setBounds(x, y, dim.width, dim.height);
                tab.doLayout();
                if (tab instanceof MyContainerPane) {
                    y += dim.height + minIntterTabPadding;
                } else {
                    y += dim.height + intterTabPadding;
                }
            }
        }

    }

}
