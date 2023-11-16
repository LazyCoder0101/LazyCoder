package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LazyCoderCommonFrame extends JFrame {

    public static final ImageIcon LOGO_IMAGE = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "logo.png");

    public LazyCoderCommonFrame(){
        this.setResizable(false);
        setIconImage(LOGO_IMAGE.getImage());
    }

}
