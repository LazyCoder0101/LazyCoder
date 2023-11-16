package com.lazycoder.uicodegeneration.generalframe.palette.additional;

import com.lazycoder.database.model.featureSelectionModel.AdditionalFeatureSelection;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import lombok.Getter;

public class AdditionalSelectionLabel extends MyIconLabel {

    /**
     *
     */
    private static final long serialVersionUID = 7290012636739714736L;

    /**
     * 可选模板对应的图标
     */
    private final static ImageIcon ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "其他模块.png");

    @Getter
    private AdditionalFeatureSelection additionalFeatureSelection;

    public AdditionalSelectionLabel(AdditionalFeatureSelection additionalFeatureSelection) {
        this.additionalFeatureSelection = additionalFeatureSelection;
        init(additionalFeatureSelection.getTypeName(), ICON);
        setPreferredSize(new Dimension((int) (0.109 * SysUtil.SCREEN_SIZE.width), 40));
    }

}
