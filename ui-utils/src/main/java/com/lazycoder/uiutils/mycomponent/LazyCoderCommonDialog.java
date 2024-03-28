package com.lazycoder.uiutils.mycomponent;

import javax.swing.JDialog;

public class LazyCoderCommonDialog extends JDialog {

    public LazyCoderCommonDialog(){
        super();
        LazyCoderCommonRootPaneContainerManager.setCommonUI(this);
    }

}
