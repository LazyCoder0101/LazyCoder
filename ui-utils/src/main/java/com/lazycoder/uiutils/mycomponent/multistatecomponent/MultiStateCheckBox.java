package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import lombok.Getter;

public class MultiStateCheckBox extends JCheckBox implements MultiStateModelInterface, MultiStateComponentUI {

    @Getter
    private String[] enableTips = new String[]{null, null, null, null, null, null};//这里 null 的数量需要和上面设置的状态的数量一致

    @Getter
    private String[] disenableTips = new String[]{null, null, null, null, null, null};//这里 null 的数量需要和上面设置的状态的数量一致

    public MultiStateCheckBox(String text) {
        init(text, null);
        setModel(new MultiStateCheckBoxModel());
//        setFocusable(false);
        MultiStateCheckBoxUI checkBoxUI = new MultiStateCheckBoxUI();
        setUI(checkBoxUI);
    }

    public int getCurrentState() {
        return ((MultiStateModel) getModel()).getCurrentState();
    }

    public void setCurrentState(int state) {
        ((MultiStateModel) getModel()).setCurrentState(state);
    }

    /**
     * 设置某个状态使能的时候对应的提示
     *
     * @param currentState
     * @param text
     */
    public void setEnableToolTipText(int currentState, String text) {
        if (currentState < enableTips.length) {
            enableTips[currentState] = text;
        }
    }

    /**
     * 设置某个状态失能的时候对应的提示
     *
     * @param currentState
     * @param text
     */
    public void setDisenableToolTipText(int currentState, String text) {
        if (currentState < disenableTips.length) {
            disenableTips[currentState] = text;
        }
    }

    @Override
    public Icon getIcon() {
        Icon icon = null;
        if (getModel() == null) {
            icon = super.getIcon();
        } else if (getModel() instanceof MultiStateModel) {
            MultiStateModel multiStateMenuItemModel = (MultiStateModel) getModel();
            if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NULL) {
                if (isEnabled()) {
                    icon = MARK_NULL_ENABLE_ICON;
                } else {
                    icon = MARK_NULL_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_SELECTED) {
                if (isEnabled()) {
                    icon = MARK_SELECTED_ENABLE_ICON;
                } else {
                    icon = MARK_SELECTED_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_NO) {
                if (isEnabled()) {
                    icon = MARK_NO_ENABLE_ICON;
                } else {
                    icon = MARK_NO_DISENABLE_ICON;
                }

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_NULL) {
                icon = MARK_PRE_NULL_ICON;

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED) {
                icon = MARK_PRE_SELECTED_ICON;

            } else if (multiStateMenuItemModel.getCurrentState() == MultiStateModel.MARK_PRE_SELECTED_NO) {
                icon = MARK_PRE_NO_ICON;

            }
            if (isEnabled()) {
                setToolTipText(getEnableTips()[multiStateMenuItemModel.getCurrentState()]);
            } else {
                setToolTipText(getDisenableTips()[multiStateMenuItemModel.getCurrentState()]);
            }
        }
        return icon;
    }

    /**
     * 查看是不是某个状态
     *
     * @param state
     * @return
     */
    public boolean isState(int state) {
        return state == getCurrentState() ? true : false;
    }

    class MultiStateCheckBoxModel extends MultiStateModel {

        @Override
        public void setPressed(boolean b) {
            if ((isPressed() == b) || !isEnabled()) {
                return;
            }

            if (b == false && isArmed()) {
                setSelected(!this.isSelected());
            }

            if (b) {
                stateMask |= PRESSED;
            } else {
                stateMask &= ~PRESSED;
            }

            fireStateChanged();
            if (!isPressed() && isArmed()) {
                if (currentState == MARK_SELECTED) {
                    currentState = MARK_NULL;

                } else if (currentState < MARK_SELECTED) {
                    currentState++;
                }
                int modifiers = 0;
                AWTEvent currentEvent = EventQueue.getCurrentEvent();
                if (currentEvent instanceof InputEvent) {
                    modifiers = ((InputEvent) currentEvent).getModifiersEx();
                } else if (currentEvent instanceof ActionEvent) {
                    modifiers = ((ActionEvent) currentEvent).getModifiers();
                }
                fireActionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                getActionCommand(),
                                EventQueue.getMostRecentEventTime(),
                                modifiers));
            }
        }
    }

}
