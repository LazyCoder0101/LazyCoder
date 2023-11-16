package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;


import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.utils.JsonUtil;

/**
 * 选项数据编辑窗口，该类只写通用业务处理
 */
public abstract class AbstractOptionDataModelEditFrame extends ContentChooseFrame implements DataFormatType {

    /**
     *
     */
    private static final long serialVersionUID = -8748623182951345230L;

    protected String moduleId = null;

    protected int additionalSerialNumber = 0;

    protected int usingRange = MODULE_TYPE;

    protected AbstractOptionDataModelEditFrame() {
        // TODO Auto-generated constructor stub
        super();
    }

    protected AbstractOptionDataModelEditFrame(int usingRange) {
        // TODO Auto-generated constructor stub
        this();
        this.usingRange = usingRange;
    }

    /**
     * 生成选项的基本内容
     *
     * @return
     */
    protected OptionDataModel getBaseOptionDatamodel() {
        OptionDataModel optionDataModel = new OptionDataModel();
        optionDataModel.setOptionName(optionNameTextField.getText().trim());
        optionDataModel.setLeftStr(leftTextField.getText());
        optionDataModel.setRightStr(rightTextField.getText());
        optionDataModel.setSeparatorStr(separatorTextField.getText());

        if (nOr1Combobx.getSelectedItem() == "多选") {
            optionDataModel.setOptionType(OptionDataModel.MULTIPLE);
        } else if (nOr1Combobx.getSelectedItem() == "单选") {
            optionDataModel.setOptionType(OptionDataModel.EXECLUSIVE);
        }

        if (generalCheckBox.isSelected() == true) {
            optionDataModel.setUsingRange(OptionDataModel.GENERAL_TYPE);
        } else {
            optionDataModel.setUsingRange(usingRange);
        }
        if (generalCheckBox.isSelected() == false) {
            optionDataModel.setModuleId(moduleId);
        }
        if (this.usingRange == ADDITIONAL_TYPE) {
            optionDataModel.setAdditionalSerialNumber(additionalSerialNumber);
        }
        optionDataModel.setValueNum(optionEditPane.getValueNum());
        optionDataModel.setOptionNameListParam(optionEditPane.getOptionNameListParam());
        optionDataModel.setOptionValueListParam(optionEditPane.getOptionValueListParam());
        optionDataModel.setValueListGroupNum(optionEditPane.getValueGroupNum());

        if (noteRowHeaderView != null) {
            optionDataModel.setRowNoteParam(JsonUtil.getJsonStr(noteRowHeaderView.getRowNoteList()));
        }

        return optionDataModel;
    }

    protected boolean check() {
        return optionEditPane.check();
    }


}
