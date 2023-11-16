package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.JToggleButton;
import lombok.Getter;
import lombok.Setter;

public class MultiStateModel extends JToggleButton.ToggleButtonModel implements MultiStateModelInterface {//DefaultButtonModel

    @Getter
    @Setter
    protected int currentState = MARK_NULL;

    public MultiStateModel() {
        super();
    }

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
            if (currentState == MARK_NO) {
                currentState = MARK_NULL;

            } else if (currentState < MARK_NO) {
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
