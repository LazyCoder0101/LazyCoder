package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code;


import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.vo.datasourceedit.format.CorrespondingFormatCodePaneInterface;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.CodeCondition;

public class ModuleFileFormatCodePane extends ImportCodeTextPane implements CorrespondingFormatCodePaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = -262305623495236946L;

    protected GeneralCodeModel model = null;

    public ModuleFileFormatCodePane(int sort) {
        super(sort);
        CodeCondition codeCondition = new CodeCondition(false, true);
        menuInit(codeCondition);
        setPath(null);
    }

    @Override
    public int getSerialNumber() {
        // TODO Auto-generated method stub
        return serialNumber;
    }

    /**
     * 获取代码参数
     */
    @Override
    public String getCodeParam() {
        return "";
    }

    /**
     * 更新菜单
     */
    @Override
    public void updateMenu() {
        super.updateMenu();
    }

    @Override
    public void setCodeModel(GeneralFileFormat formatCodeModel, int codeOrdinal) {
        super.setCodeModel(formatCodeModel, codeOrdinal);
        if (getFileParam() != null) {
            formatCodeModel.setModuleId(getFileParam().getModuleId());
        }
    }

}
