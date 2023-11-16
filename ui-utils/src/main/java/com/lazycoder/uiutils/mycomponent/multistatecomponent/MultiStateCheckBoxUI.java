package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MultiStateCheckBoxUI extends BasicCheckBoxUI implements MultiStateComponentUI {

    private MultiStateCheckBox checkBox = null;

    public static ComponentUI createUI(JComponent c) {
        return new MultiStateCheckBoxUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        icon = MARK_NULL_ENABLE_ICON;
        if (c instanceof MultiStateCheckBox) {
            checkBox = (MultiStateCheckBox) c;
            checkBox.setToolTipText(checkBox.getEnableTips()[MultiStateCheckBox.MARK_NULL]);
        }
    }

//    private Icon setCheckIcon() {
//        if (this.checkBox != null) {
//            MultiStateModel multiStateMenuItemModel = (MultiStateModel) this.checkBox.getModel();
//            if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NULL) {
//                if (this.checkBox.isEnabled()) {
//                    icon = MARK_NULL_ENABLE_ICON;
//                } else {
//                    icon = MARK_NULL_DISENABLE_ICON;
//                }
//
//            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_SELECTED) {
//                if (this.checkBox.isEnabled()) {
//                    icon = MARK_SELECTED_ENABLE_ICON;
//                } else {
//                    icon = MARK_SELECTED_DISENABLE_ICON;
//                }
//
//            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NO) {
//                if (this.checkBox.isEnabled()) {
//                    icon = MARK_NO_ENABLE_ICON;
//                } else {
//                    icon = MARK_NO_DISENABLE_ICON;
//                }
//
//            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_NULL) {
//                icon = MARK_PRE_NULL_ICON;
//
//            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED) {
//                icon = MARK_PRE_SELECTED_ICON;
//
//            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED_NO) {
//                icon = MARK_PRE_NO_ICON;
//
//            }
//            if (checkBox.isEnabled()) {
//                checkBox.setToolTipText(checkBox.getEnableTips()[multiStateMenuItemModel.getCurrentState()]);
//            } else {
//                checkBox.setToolTipText(checkBox.getDisenableTips()[multiStateMenuItemModel.getCurrentState()]);
//            }
//        }
//        return icon;
//    }

//    @Override
//    protected void installDefaults(AbstractButton b) {
//        super.installDefaults(b);
//        setCheckIcon();
//    }

//    @Override
//    protected void paintIcon(Graphics g, AbstractButton b, Rectangle iconRect) {
//        Icon theIcon = setCheckIcon();
//        theIcon.paintIcon(b, g, iconRect.x, iconRect.y);
//    }

//    @Override
//    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
//        if (checkBox != null) {
//            Icon theIcon = setCheckIcon();
//            ButtonModel model = checkBox.getModel();
//            if (model != null) {
//                if (model.isPressed() && model.isArmed()) {
//                    theIcon.paintIcon(c, g, iconRect.x + getTextShiftOffset(),
//                            iconRect.y + getTextShiftOffset());
//                } else {
//                    theIcon.paintIcon(c, g, iconRect.x, iconRect.y);
//                }
//            }
//        }
//    }

}
