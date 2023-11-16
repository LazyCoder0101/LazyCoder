package com.lazycoder.uiutils.mycomponent;

import javax.swing.JDialog;

public class LazyCoderCommonDialog extends JDialog {

    public LazyCoderCommonDialog(){
        super();
        setModal(true);
        setResizable(false);
        setIconImage(LazyCoderCommonFrame.LOGO_IMAGE.getImage());
    }

}
