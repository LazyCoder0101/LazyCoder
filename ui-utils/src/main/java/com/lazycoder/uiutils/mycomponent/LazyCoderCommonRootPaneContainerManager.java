package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class LazyCoderCommonRootPaneContainerManager {

    public static final ImageIcon LOGO_IMAGE = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "logo.png");

    public static void setCommonUI(JDialog dialog){
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setIconImage(LOGO_IMAGE.getImage());
    }

    public static void setCommonUI(JFrame frame){
        frame.setResizable(false);
        frame.setIconImage(LOGO_IMAGE.getImage());
    }

}
