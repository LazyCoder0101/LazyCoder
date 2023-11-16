package com.lazycoder.uiutils.ui.MyFlatUI;

import com.formdev.flatlaf.ui.FlatOptionPaneUI;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;

public class MyFlatOptionPaneUI extends FlatOptionPaneUI {

    public static ComponentUI createUI(JComponent c) {
        Window window = SwingUtilities.getWindowAncestor(c);
        if (window != null) {
            window.setIconImage(LazyCoderCommonFrame.LOGO_IMAGE.getImage());
        }
        return new MyFlatOptionPaneUI();
    }

    @Override
    protected Container createButtonArea() {
        Container container = super.createButtonArea();
        Component[] components = container.getComponents();
        if (components != null) {
            JButton jButton;
            for (Component component : components) {
                if (component instanceof JButton) {
                    jButton = (JButton) component;
                    if (!(jButton.getUI() instanceof MyFlatButtonUI)) {
                        jButton.setUI(new MyFlatButtonUI());
                    }
                }
            }
        }
        return container;
    }
}
