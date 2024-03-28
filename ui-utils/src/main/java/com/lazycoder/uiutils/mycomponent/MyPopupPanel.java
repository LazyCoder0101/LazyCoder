package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.uiutils.popup.AfPopupPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class MyPopupPanel extends AfPopupPanel {

    /**
     *
     */
    private static final long serialVersionUID = 8883917611734573302L;

    protected MyPopupButton popupButton;

    private JComponent contentComponent;

    public MyPopupPanel(JComponent component, MyPopupButton popupButton) {
        setLayout(new BorderLayout());
        add(component, BorderLayout.CENTER);
        this.popupButton = popupButton;
        this.contentComponent = component;
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
