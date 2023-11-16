package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent;

import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeUsePropetyMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;
import lombok.Getter;

public class CodeUsePropetyMenuItem extends JRadioButtonMenuItem {

    private CodeUsePropetyMenu moduleCodeUsePropetyMenu;

    @Getter
    private AbstractCodeUseProperty codeUseProperty;

    public CodeUsePropetyMenuItem(AbstractCodeUseProperty codeUseProperty, CodeUsePropetyMenu moduleCodeUsePropetyMenu) {
        super(codeUseProperty.getShowText());
        this.codeUseProperty = codeUseProperty;
        this.moduleCodeUsePropetyMenu = moduleCodeUsePropetyMenu;
        addActionListener(menuItemActionListener);
    }

    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            moduleCodeUsePropetyMenu.showText();
        }
    };

}
