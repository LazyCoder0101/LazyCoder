package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.uicodegeneration.component.operation.container.InitOpratingContainer;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uiutils.folder.FoldPaneUI;
import com.lazycoder.uiutils.folder.Folder;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComponent;
import javax.swing.Timer;

public class InitCodeControlPaneUI extends FoldPaneUI {

    private static final double INCDEC = 0.085;
    private static final double DECRATIO = 0.925;
    private static final int ANIMATION_INTERVAL = 15;

    private AbstractCodeControlPane initCodeControlPane;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        initCodeControlPane = (AbstractCodeControlPane) c;
    }

    // 为该面板添加折叠处理事件
    @Override
    public void addTab(Folder tab) {
        super.addTab(tab);
        if (tab instanceof InitOpratingContainer) {
            InitOpratingContainer initOpratingContainer = (InitOpratingContainer) tab;
            ModuleSetCodeHiddenControlButton moduleSetCodeHiddenControlButton = ((InitOpratingContainer) tab).getModuleSetCodeHiddenControlButton();
            if (moduleSetCodeHiddenControlButton != null) {
                moduleSetCodeHiddenControlButton.addItemListener(new InitCodeControlPaneUI.InitFolderModuleSetTabItemListener(initOpratingContainer));
            }
        }
    }

    // 折叠事件处理器
    class InitFolderModuleSetTabItemListener implements ItemListener {
        private InitOpratingContainer initOpratingContainer = null;
        private Timer initTimer;

        public InitFolderModuleSetTabItemListener(InitOpratingContainer initOpratingContainer) {
            this.initOpratingContainer = initOpratingContainer;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (this.initOpratingContainer.getModuleSetCodeHiddenControlButton() != null) {
                if (this.initOpratingContainer.getModuleSetCodeHiddenControlButton().isEnabled() == true) {
                    if (initCodeControlPane.isAnimated()) {
                        // 需要动画效果，先关闭可能的timer
                        if (initTimer != null) {
                            if (initTimer.isRunning()) {
                                initTimer.stop();
                            }
                        }
                        // 生成新的timer
                        initTimer = new Timer(ANIMATION_INTERVAL, null);
                        // 根据展开或者是关闭选择不同的动画处理动作
                        ActionListener action;
                        if (e.getStateChange() == ItemEvent.DESELECTED) {
                            action = new InitCodeControlPaneUI.InitFolderingAction(this.initOpratingContainer, initTimer);
                        } else {
                            action = new InitCodeControlPaneUI.InitExpandingAction(this.initOpratingContainer, initTimer);
                        }
                        initTimer.addActionListener(action);
                        // 启动timer
                        initTimer.start();
                    } else {
                        // 无动画效this.initOpratingContainer.getModuleSetDrawer()果，直接展开或者关闭
                        if (this.initOpratingContainer.getModuleSetDrawer() != null) {
                            this.initOpratingContainer.getModuleSetDrawer().setRatio(e.getStateChange() == ItemEvent.DESELECTED ? 0 : 1.0);
                        }
                        initCodeControlPane.doLayout();
                    }
                    initCodeControlPane.repaint();
                }
            }
        }

    }

    // 折叠事件基类
    abstract class InitFolderAction implements ActionListener {
        // 当前折叠的面板
        private InitOpratingContainer initOpratingContainer;
        // 相关的时钟
        private Timer initTimer;
        // 下面两个参数计算抽屉抽出或者关闭的实时速度
        private double initRratio;
        private double initExponent;

        public InitFolderAction(InitOpratingContainer initOpratingContainer, Timer timer) {
            this.initOpratingContainer = initOpratingContainer;
            this.initTimer = timer;
            initRratio = INCDEC;
            initExponent = DECRATIO;
            // 将抽屉置于动画效果状态
            if (this.initOpratingContainer.getModuleSetDrawer() != null) {
                this.initOpratingContainer.getModuleSetDrawer().setAnimating(true);
            }
        }

        // 计算速度变化，可以允许非匀速运动效果的抽屉
        protected double getDelta() {
            double r = initRratio;
            initRratio = initRratio * initExponent;
            return r;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 计算新的值
            double r = this.initOpratingContainer.getModuleSetDrawer() == null ?
                    0 : delta(this.initOpratingContainer.getModuleSetDrawer().getRatio(), getDelta());
            if (overflow(r)) {
                // 溢出则置为界限值
                r = bound();
                if (this.initOpratingContainer.getModuleSetDrawer() != null) {
                    // 设置抽屉状态，停止动画效果，重新布局折叠面板，停止时钟，最好释放时钟
                    this.initOpratingContainer.getModuleSetDrawer().setAnimating(false);
                }
                doLayout(r);
                initTimer.stop();
                initTimer = null;
            } else {
                // 普通动画的一帧，设置抽屉状态，重新布局折叠面板
                doLayout(r);
            }
        }

        private void doLayout(final double r) {
            if (this.initOpratingContainer.getModuleSetDrawer() != null) {
                this.initOpratingContainer.getModuleSetDrawer().setRatio(r);
            }
            // 相比以前添加了动画时，动态调整父容器布局，这对于父容器是JScrolPane
            // 的base_fuunction_input_pane来说很重要，它需要实时调整ScrollBar
            Container parent = initCodeControlPane.getParent();
            if (parent != null) {
                parent.doLayout();
            }
            // 调整自己的尺寸布局
            initCodeControlPane.doLayout();
        }

        // 计算新的位置和透明度
        protected abstract double delta(double r, double d);

        // 当前值是否溢出，比如超过1.0或者小于了0
        protected abstract boolean overflow(double r);

        // 当前运动的界限值
        protected abstract double bound();
    }

    // 展开用的动画处理事件
    class InitExpandingAction extends InitCodeControlPaneUI.InitFolderAction {
        public InitExpandingAction(InitOpratingContainer initOpratingContainer, Timer timer) {
            super(initOpratingContainer, timer);
        }

        @Override
        protected double delta(double r, double d) {
            return r + d;
        }// 递增

        @Override
        protected boolean overflow(double r) {
            return r > 1;
        }// 上限为1

        @Override
        protected double bound() {
            return 1;
        }// 上限值位1
    }

    // 折叠时用的动画处理事件
    class InitFolderingAction extends InitCodeControlPaneUI.InitFolderAction {
        public InitFolderingAction(InitOpratingContainer initOpratingContainer, Timer timer) {
            super(initOpratingContainer, timer);
        }

        @Override
        protected double delta(double r, double d) {
            return r - d;
        }// 递减

        @Override
        protected boolean overflow(double r) {
            return r < 0;
        }// 下限为0

        @Override
        protected double bound() {
            return 0;
        }// 下限值为0
    }


}
