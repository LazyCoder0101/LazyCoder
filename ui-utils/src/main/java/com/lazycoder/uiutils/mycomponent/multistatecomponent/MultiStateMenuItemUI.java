package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class MultiStateMenuItemUI extends BasicCheckBoxMenuItemUI implements MultiStateComponentUI{

    public MultiStateMenuItem theMenuItem = null;

    public static ComponentUI createUI(JComponent c) {
        return new MultiStateMenuItemUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        checkIcon = MARK_NULL_ENABLE_ICON;
        if (c instanceof MultiStateMenuItem) {
            theMenuItem = (MultiStateMenuItem) c;
            theMenuItem.setToolTipText(theMenuItem.getEnableTips()[MultiStateMenuItem.MARK_NULL]);
        }
    }

    private Icon setCheckIcon() {
        if (this.theMenuItem != null) {
            MultiStateModel multiStateMenuItemModel = (MultiStateModel) this.theMenuItem.getModel();
            if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NULL) {
                if (this.theMenuItem.isEnabled()){
                    checkIcon = MARK_NULL_ENABLE_ICON;
                }else {
                    checkIcon = MARK_NULL_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_SELECTED) {
                if (this.theMenuItem.isEnabled()){
                    checkIcon = MARK_SELECTED_ENABLE_ICON;
                }else {
                    checkIcon = MARK_SELECTED_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NO) {
                if (this.theMenuItem.isEnabled()){
                    checkIcon = MARK_NO_ENABLE_ICON;
                }else {
                    checkIcon = MARK_NO_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_NULL) {
                checkIcon = MARK_PRE_NULL_ICON;

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED) {
                checkIcon = MARK_PRE_SELECTED_ICON;

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED_NO) {
                checkIcon = MARK_PRE_NO_ICON;

            }
            if (theMenuItem.isEnabled()) {
                theMenuItem.setToolTipText(theMenuItem.getEnableTips()[multiStateMenuItemModel.getCurrentState()]);
            } else {
                theMenuItem.setToolTipText(theMenuItem.getDisenableTips()[multiStateMenuItemModel.getCurrentState()]);
            }
        }
        return checkIcon;
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        setCheckIcon();
    }

    @Override
    protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap) {
        Icon theIcon = setCheckIcon();
        super.paintMenuItem(g, c, theIcon, arrowIcon, background, foreground, defaultTextIconGap);
    }

}
