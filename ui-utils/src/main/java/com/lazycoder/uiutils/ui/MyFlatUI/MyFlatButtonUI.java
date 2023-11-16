package com.lazycoder.uiutils.ui.MyFlatUI;

import com.formdev.flatlaf.ui.FlatButtonUI;
import com.formdev.flatlaf.ui.FlatUIUtils;
import java.awt.Color;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class MyFlatButtonUI extends FlatButtonUI {

    public static ComponentUI createUI(JComponent c ) {
        return FlatUIUtils.createSharedUI( MyFlatButtonUI.class, MyFlatButtonUI::new );
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setBorderPainted(false);
        b.setBackground(new Color(51,153,255));
        b.setForeground(Color.white);
    }
}
