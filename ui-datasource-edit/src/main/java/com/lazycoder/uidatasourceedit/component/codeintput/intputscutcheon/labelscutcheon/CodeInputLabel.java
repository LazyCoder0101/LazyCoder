package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;

public class CodeInputLabel extends MyButton implements LabelComponentIntetface {

    public static final ImageIcon CODE_INPUT_ICON = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent" + File.separator
                    + "CodeInput" + File.separator + "code.png");
    /**
     *
     */
    private static final long serialVersionUID = 2555468782938337856L;

    private PassingComponentParams passingComponentParams = null;

    public CodeInputLabel() {
        super(CODE_INPUT_ICON);
        // this.setBorder(border);
        Dimension dd = new Dimension(100, 30);

        this.setMaximumSize(dd);
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
    }

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLabelType() {
        // TODO Auto-generated method stub
        return LabelElementName.CODE_INPUT;
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
        LabelComponentIntetface.addCorrespondingComponentResponseListener(this);
    }

    @Override
    public void setNavigate(boolean flag) {

    }

    @Override
    public BaseLableElement property() {
        return null;
    }

}
