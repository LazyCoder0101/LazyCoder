package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.uiutils.popup.AfPopupPanel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import lombok.Getter;
import lombok.Setter;


public class MyPopupButton extends MyToggleButton {//implements  ComponentListener {


    /**
     *
     */
    private static final long serialVersionUID = 2802016804693513781L;

    @Setter
    @Getter
    protected int paneWidth = 50, paneHeight = 200, buttonWidth = 80, buttonHeight = 30;
//    protected int paneWidth = 550, paneHeight = 200, buttonWidth = 80, buttonHeight = 30;

    private MyPopupPanel popupPanel = null;

    private JComponent theComponentPane;

    public MyPopupButton() {
        super();
        init();
    }

    public MyPopupButton(Icon icon) {
        super(icon);
        init();
    }

    public MyPopupButton(Icon icon, boolean selected) {
        super(icon, selected);
        init();
    }

    public MyPopupButton(String text) {
        super(text);
        init();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        MyPopupButton button = new MyPopupButton();
        button.setInternalComponent(scrollPane);
        button.setBounds(50, 50, 80, 30);
        frame.add(button);

        frame.setBounds(300, 300, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void init() {
//		addChangeListener(this);
//		addComponentListener(this);
        setFocusPainted(false);
        addActionListener(actionListener);
        addHierarchyBoundsListener(hierarchyBoundsListener);
        setSelected(false);
    }

    private HierarchyBoundsListener hierarchyBoundsListener = new HierarchyBoundsAdapter() {
        @Override
        public void ancestorMoved(HierarchyEvent hierarchyEvent) {
            super.ancestorMoved(hierarchyEvent);
            packUpPanel();
        }
    };

//	public void setPopupPanel(MyPopupPanel popupPanel) {
//		if (this.popupPanel != null) {
//			this.popupPanel.hidePopup();
//			this.popupPanel = null;
//		}
//		this.popupPanel = popupPanel;
//	}

    /**
     * 设置内部组件
     *
     * @param component
     */
    public void setInternalComponent(JComponent component) {
        this.theComponentPane = component;
    }

    @Override
    public void doClick() {
        super.doClick();
        showPopupPane();
    }

    protected void showPopupPane() {
        if (isSelected() == true) {
            if (popupPanel == null) {
                if (theComponentPane != null) {
                    Point point = MyPopupButton.this.getLocationOnScreen();
                    int paneWidthTemp = getPaneWidth(),
                            buttonWidthTemp = getButtonWidth(),
                            paneHeightTemp = getPaneHeight(),
                            buttonHeightTemp = getButtonHeight(),
                            xMoveTemp = paneWidthTemp;
//                    paneWidth = paneWidthTemp;
                    popupPanel = getPopupPanel(theComponentPane);

                    if (paneWidth < buttonWidthTemp) {
                        xMoveTemp = buttonWidthTemp;
                    }
                    if (point.y + buttonHeightTemp + paneHeightTemp < SysUtil.SCREEN_SIZE.height - SysUtil.taskbarInsets.bottom) {// 高度在屏幕高度范围之内 SysUtil.taskbarInsets.bottom
                        if (point.x + xMoveTemp < SysUtil.SCREEN_SIZE.width) {// 宽度在屏幕宽度之内
                            popupPanel.showPopup(MyPopupButton.this, 0, buttonHeight);
                        } else {// 宽度超出屏幕宽度
                            popupPanel.showPopup(MyPopupButton.this, 0 - paneWidthTemp, 0);
                        }
                    } else {// 高度超出屏幕高度

                        int theHeight = 0 - paneHeightTemp + buttonHeightTemp,
                                screeny = getLocationOnScreen().y;
                        if (screeny + theHeight < 0) {
                            int temp = screeny + theHeight;
                            theHeight = theHeight - temp;
                        }
                        if (point.x + xMoveTemp < SysUtil.SCREEN_SIZE.width) {// 宽度在屏幕宽度之内
                            popupPanel.showPopup(MyPopupButton.this, buttonWidthTemp, theHeight);
                        } else {// 宽度超出屏幕宽度
                            popupPanel.showPopup(MyPopupButton.this, 0 - paneWidth, theHeight);
                        }
                    }
                    doSomeThingWhenExpandPanel();
                }
            }
        } else {
            if (popupPanel != null) {
                doSomeThingWhenPackUpPanel();
                popupPanel.hidePopup();
                popupPanel = null;
            }
        }
    }

    /**
     * 展开面板的时候需要做操作
     */
    protected void doSomeThingWhenExpandPanel() {
    }

    /**
     * 收起面板的时候需要做操作
     */
    protected void doSomeThingWhenPackUpPanel() {
    }

    public void hidePopupPanel() {
        // TODO Auto-generated method stub
        if (popupPanel != null) {
            popupPanel.hidePopup();
            popupPanel = null;
        }
    }

    /**
     * 收起面板
     */
    public final void packUpPanel() {
        if (isSelected() == true) {
            doClick();
        }
    }

    /**
     * 展开面板
     */
    public final void expandPanel() {
        if (isSelected() == false) {
            doClick();
        }
    }

    public final boolean isExpanded() {
        return isSelected();
    }


    protected MyPopupPanel getPopupPanel(JComponent componentPane) {
        return new MyPopupPanel(componentPane, this);
    }


    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            showPopupPane();
        }
    };

    class MyPopupPanel extends AfPopupPanel {

        /**
         *
         */
        private static final long serialVersionUID = 8883917611734573302L;

        protected MyPopupButton popupButton;

        public MyPopupPanel(JComponent component, MyPopupButton popupButton) {
            setLayout(new BorderLayout());
            add(component, BorderLayout.CENTER);
            this.popupButton = popupButton;
            setBorder(BorderFactory.createRaisedBevelBorder());
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(popupButton.paneWidth, popupButton.paneHeight);
        }

        // 隐藏 Popup
//        @Override
//        public void hidePopup() {
//            super.hidePopup();
//        }

    }

}