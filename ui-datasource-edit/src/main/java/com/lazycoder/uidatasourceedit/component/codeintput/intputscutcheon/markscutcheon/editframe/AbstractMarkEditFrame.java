package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe;

import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractMarkEditFrame extends LazyCoderCommonDialog {

    public AbstractMarkEditFrame(){
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AbstractMarkEditFrame.this.dispose();
            }
        });
    }

}
