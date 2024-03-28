package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.generalframe.operation.component.BusinessLogicCodeControlPane;
import com.lazycoder.uiutils.folder.FoldPaneUI;
import com.lazycoder.uiutils.folder.Folder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class BusinessLogicCodeControlPaneUI extends FoldPaneUI {

    private static final double INCDEC = 0.085;
    private static final double DECRATIO = 0.925;
    private static final int ANIMATION_INTERVAL = 15;

    private BusinessLogicCodeControlPane businessLogicCodeControlPane;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        businessLogicCodeControlPane = (BusinessLogicCodeControlPane) c;
    }

    // 为该面板添加折叠处理事件
    @Override
    public void addTab(Folder tab) {
        super.addTab(tab);
        if (tab instanceof AbstractFormatContainer) {
            AbstractFormatContainer formatContainer = (AbstractFormatContainer) tab;
            CodeHiddenControlButton setCodeHiddenControlButton = ((AbstractFormatContainer) tab).getSetCodeHiddenControlButton();
            if (setCodeHiddenControlButton != null) {
                setCodeHiddenControlButton.addItemListener(new FormatContainerTabItemListener(formatContainer));
            }

        }
    }

    // 折叠事件处理器
    class FormatContainerTabItemListener implements ItemListener {
        private AbstractFormatContainer formatContainer = null;
        private Timer timer;

        public FormatContainerTabItemListener(AbstractFormatContainer formatContainer) {
            this.formatContainer = formatContainer;
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (this.formatContainer.getSetCodeHiddenControlButton() != null) {
                if (this.formatContainer.getSetCodeHiddenControlButton().isEnabled() == true) {
                    if (businessLogicCodeControlPane.isAnimated()) {
                        // 需要动画效果，先关闭可能的timer
                        if (timer != null) {
                            if (timer.isRunning()) {
                                timer.stop();
                            }
                        }
                        // 生成新的timer
                        timer = new Timer(ANIMATION_INTERVAL, null);
                        // 根据展开或者是关闭选择不同的动画处理动作
                        ActionListener action;
                        if (e.getStateChange() == ItemEvent.DESELECTED) {
                            action = new FormatContainerFolderingAction(this.formatContainer, timer);
                        } else {
                            action = new FormatContainerExpandingAction(this.formatContainer, timer);
                        }
                        timer.addActionListener(action);
                        // 启动timer
                        timer.start();
                    } else {
                        // 无动画效this.formatOpratingContainer.getSetDrawer()果，直接展开或者关闭
                        if (this.formatContainer.getSetDrawer() != null) {
                            this.formatContainer.getSetDrawer().setRatio(e.getStateChange() == ItemEvent.DESELECTED ? 0 : 1.0);
                        }
                        businessLogicCodeControlPane.doLayout();
                    }
                    businessLogicCodeControlPane.repaint();
                }
            }
        }

    }

    // 折叠事件基类
    abstract class FormatContainerFolderAction implements ActionListener {
        // 当前折叠的面板
        private AbstractFormatContainer formatContainer;
        // 相关的时钟
        private Timer timer;
        // 下面两个参数计算抽屉抽出或者关闭的实时速度
        private double ratio;
        private double exponent;

        public FormatContainerFolderAction(AbstractFormatContainer formatContainerr, Timer timer) {
            this.formatContainer = formatContainerr;
            this.timer = timer;
            ratio = INCDEC;
            exponent = DECRATIO;
            // 将抽屉置于动画效果状态
            if (this.formatContainer.getSetDrawer() != null) {
                this.formatContainer.getSetDrawer().setAnimating(true);
            }
        }

        // 计算速度变化，可以允许非匀速运动效果的抽屉
        protected double getDelta() {
            double r = ratio;
            ratio = ratio * exponent;
            return r;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 计算新的值
            double r = this.formatContainer.getSetDrawer() == null ?
                    0 : delta(this.formatContainer.getSetDrawer().getRatio(), getDelta());
            if (overflow(r)) {
                // 溢出则置为界限值
                r = bound();
                if (this.formatContainer.getSetDrawer() != null) {
                    // 设置抽屉状态，停止动画效果，重新布局折叠面板，停止时钟，最好释放时钟
                    this.formatContainer.getSetDrawer().setAnimating(false);
                }
                doLayout(r);
                timer.stop();
                timer = null;
            } else {
                // 普通动画的一帧，设置抽屉状态，重新布局折叠面板
                doLayout(r);
            }
        }

        private void doLayout(final double r) {
            if (this.formatContainer.getSetDrawer() != null) {
                this.formatContainer.getSetDrawer().setRatio(r);
            }
            // 相比以前添加了动画时，动态调整父容器布局，这对于父容器是JScrolPane
            // 的base_fuunction_input_pane来说很重要，它需要实时调整ScrollBar
            Container parent = businessLogicCodeControlPane.getParent();
            if (parent != null) {
                parent.doLayout();
            }
            // 调整自己的尺寸布局
            businessLogicCodeControlPane.doLayout();
        }

        // 计算新的位置和透明度
        protected abstract double delta(double r, double d);

        // 当前值是否溢出，比如超过1.0或者小于了0
        protected abstract boolean overflow(double r);

        // 当前运动的界限值
        protected abstract double bound();
    }

    // 展开用的动画处理事件
    class FormatContainerExpandingAction extends FormatContainerFolderingAction {
        public FormatContainerExpandingAction(AbstractFormatContainer formatContainer, Timer timer) {
            super(formatContainer, timer);
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
    class FormatContainerFolderingAction extends FormatContainerFolderAction {
        public FormatContainerFolderingAction(AbstractFormatContainer formatContainer, Timer timer) {
            super(formatContainer, timer);
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
