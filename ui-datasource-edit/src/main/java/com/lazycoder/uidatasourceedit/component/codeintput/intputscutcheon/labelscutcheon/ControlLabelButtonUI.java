package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.formdev.flatlaf.ui.FlatButtonUI;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.lazycoder.uiutils.utils.ShineEffect;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public class ControlLabelButtonUI extends FlatButtonUI {

    public ControlLabelButtonUI() {
        super();
    }

//    public static ComponentUI createUI(JComponent c) {
//        return new ControlLabelButtonUI();
//    }

    @Override
    public void paint(Graphics g, JComponent c) {
        ShineEffect.createShineEffect(g, c, ShineEffect.defaultComponentColor);
        super.paint(g, c);
    }


    public static ComponentUI createUI(JComponent c) {
        return FlatUIUtils.createSharedUI(ControlLabelButtonUI.class, ControlLabelButtonUI::new);
    }

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
    }

}
