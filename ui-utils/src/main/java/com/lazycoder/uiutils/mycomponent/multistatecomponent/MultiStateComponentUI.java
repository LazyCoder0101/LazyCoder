package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import java.io.File;
import javax.swing.ImageIcon;

public interface MultiStateComponentUI {

    public final ImageIcon MARK_NULL_ENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markNullEnable.png");

    public final ImageIcon MARK_NULL_DISENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markNullDisenable.png");

    public final ImageIcon MARK_SELECTED_ENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markSelectedEnable.png");

    public final ImageIcon MARK_SELECTED_DISENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markSelectedDisenable.png");

    public final ImageIcon MARK_NO_ENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markNoEnable.png");

    public final ImageIcon MARK_NO_DISENABLE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markNoDisenable.png");

    public final ImageIcon MARK_PRE_NULL_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markPreNull.png");

    public final ImageIcon MARK_PRE_SELECTED_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markPreSelected.png");

    public final ImageIcon MARK_PRE_NO_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
            + "myMenuItem" + File.separator + "markPreNo.png");

}
