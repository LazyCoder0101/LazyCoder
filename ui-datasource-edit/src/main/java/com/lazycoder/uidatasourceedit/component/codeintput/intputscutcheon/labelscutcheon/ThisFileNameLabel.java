package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class ThisFileNameLabel extends MyButton implements LabelComponentIntetface {

    /**
     *
     */
    private static final long serialVersionUID = 2166735054403587783L;

    private final static ImageIcon ICON = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
                    + "label_element" + File.separator + "fileName" + File.separator + "fileName.png");


    private PassingComponentParams passingComponentParams = null;

    public ThisFileNameLabel() {
        setIcon(ICON);
        Dimension dd = new Dimension(80, 30);
        this.setMaximumSize(dd);
        this.setMinimumSize(dd);
        this.setPreferredSize(dd);
    }

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLabelType() {
        // TODO Auto-generated method stub
        return LabelElementName.THIS_FILE_NAME;
    }

    @Override
    public PassingComponentParams getPassingComponentParams() {
        // TODO Auto-generated method stub
        return passingComponentParams;
    }

    @Override
    public void setPassingComponentParams(PassingComponentParams passingComponentParams) {
        // TODO Auto-generated method stub
        this.passingComponentParams = passingComponentParams;
    }


    @Override
    public void setNavigate(boolean flag) {}

    @Override
    public BaseLableElement property() {
        return null;
    }

}
