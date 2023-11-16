package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.uiutils.popup.AfPopup;
import com.lazycoder.uiutils.popup.AfPopupMouseGrabber;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JWindow;
import java.awt.Component;


public class MyTogglePopup extends MyPopupButton {

    /**
     *
     */
    private static final long serialVersionUID = -4703256333229817826L;

    public MyTogglePopup() {
        super();
    }

    public MyTogglePopup(Icon icon) {
        super(icon);
    }

    public MyTogglePopup(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public MyTogglePopup(String text) {
        super(text);
    }

    @Override
    protected TogglePopupPanel getPopupPanel(JComponent componentPane) {
        // TODO Auto-generated method stub
        return new TogglePopupPanel(componentPane, this);
    }


    class TogglePopupPanel extends MyPopupPanel {

        /**
         *
         */
        private static final long serialVersionUID = 765860761657525069L;

        public TogglePopupPanel(JComponent component, MyTogglePopup togglePopup) {
            // TODO Auto-generated constructor stub
            super(component, togglePopup);
        }

        @Override
        protected TogglePopupMouseGrabber getMouseGrabber(Component owner, JWindow popup) {
            // TODO Auto-generated method stub
            return new TogglePopupMouseGrabber(owner, popup, popupButton);
        }

        @Override
        public void showPopup(Component owner, int x, int y) {
            AfPopup.ClosingPolicy closingPolicy = new AfPopup.ClosingPolicy();
            closingPolicy.setAutoClose(true);
            showPopup(owner, x, y, closingPolicy);
        }

        @Override
        public void hidePopup() {
            // TODO Auto-generated method stub
            super.hidePopup();

//		if (popupButton != null) {
//			if (this.popupButton.isSelected() == true) {
//				this.popupButton.setSelected(false);
//			}
//		}
        }

    }
}

class TogglePopupMouseGrabber extends AfPopupMouseGrabber {

    private MyPopupButton popupButton = null;

    public TogglePopupMouseGrabber(Component owner, JWindow popup, MyPopupButton popupButton) {
        super(owner, popup);
        // TODO Auto-generated constructor stub
        this.popupButton = popupButton;
    }

    @Override
    public void cancelPopup() {
        // TODO Auto-generated method stub
        super.cancelPopup();
//		if (popupButton != null) {
//			if (this.popupButton.isSelected() == true) {
//				this.popupButton.setSelected(false);
//			}
//		}
    }

    @Override
    public void afterCancelPopup() {
        // TODO Auto-generated method stub
        if (popupButton != null) {
            if (popupButton.isSelected() == true) {
                popupButton.doClick();
            }
        }
    }

}