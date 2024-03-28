package com.lazycoder.uicodegeneration.component.operation.container.component;

import com.lazycoder.service.fileStructure.SysFileStructure;

import javax.swing.*;
import java.io.File;

public class AdditionalSetCodeHiddenControlButton extends CodeHiddenControlButton{

    private static ImageIcon
            setOpenIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() +
                    File.separator + "CodeGeneration" + File.separator + "set" + File.separator + "设置_open.png"),
            setCloseIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() +
                            File.separator + "CodeGeneration" + File.separator + "set" + File.separator + "设置_close.png");

    public AdditionalSetCodeHiddenControlButton(boolean expanded) {
        super(expanded);
        setIcon(setOpenIcon);
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (expanded == true) {
            setIcon(setOpenIcon);
        } else {
            setIcon(setCloseIcon);
        }
    }

}

