package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.FunctionCodePaneInterface;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionCodeInputPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;

public abstract class AbstractCodeTier extends JScrollPane implements FunctionCodePaneInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 6124291069275671824L;

    @Getter
    protected AbstractFunctionCodeInputPane codePane = null;

    protected int codeOrdinal = 0;

    protected int operatingOrdinalNumber = 0;

    @Override
    public void updateMenu() {
        // TODO Auto-generated method stub
        if (codePane != null) {
            codePane.updateMenu();
        }
    }

    @Override
    public void delLabel(String labelType, String componentName) {
        // TODO Auto-generated method stub
        if (codePane != null) {
            codePane.delLabel(labelType, componentName);
        }
    }

    @Override
    public int getSerialNumber() {
        // TODO Auto-generated method stub
        return codePane.getSerialNumber();
    }

    public String getCodeParam() {
        // TODO Auto-generated method stub
        String out = "";
        if (codePane != null) {
            out = codePane.getCodeParam();
        }
        return out;
    }

    public abstract GeneralCodeModel getCodeModel();

    public abstract String getPathParam();

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        codePane.clear();
    }

    @Override
    public void delContentChooseLable(String optionId, int useNumbered) {
        // TODO Auto-generated method stub
        codePane.delContentChooseLable(optionId, useNumbered);
    }

    @Override
    public void delContentChooseLable(String optionId) {
        // TODO Auto-generated method stub
        if (codePane != null) {
            codePane.delContentChooseLable(optionId);
        }
    }

    @Override
    public void updateRadioComboboxShowValue(ContentChooseControl contentChooseControl, int selectIndex) {
        // TODO Auto-generated method stub
        codePane.updateRadioComboboxShowValue(contentChooseControl, selectIndex);
    }

    @Override
    public void updateMutiComboboxShowValue(ContentChooseControl chooseControl, ArrayList<Integer> selectList) {
        // TODO Auto-generated method stub
        codePane.updateMutiComboboxShowValue(chooseControl, selectList);
    }


    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return codePane.getUseState();
    }

    public void reductionContent(GeneralCodeModel codeModel) {
        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.getCodePaneElementList(codeModel.getCodeFormatParam());
        codePane.reductionContent(elementList);
    }

    @Override
    public void updateComboboxItems(OptionDataModel option) {
        codePane.updateComboboxItems(option);
    }

    @Override
    public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border) {
        codePane.makeCorrespondingLabelScutcheonRespond(lableElement, border);
    }

    /**
     * 收起这个代码面板对应的记录引入代码内容的面板
     */
    public abstract void packUpCorrespondingImportCodePane();

}
