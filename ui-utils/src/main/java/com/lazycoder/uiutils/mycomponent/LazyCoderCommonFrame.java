package com.lazycoder.uiutils.mycomponent;

import javax.swing.JFrame;

public class LazyCoderCommonFrame extends JFrame {

    public LazyCoderCommonFrame(){
        LazyCoderCommonRootPaneContainerManager.setCommonUI(this);
    }

}
