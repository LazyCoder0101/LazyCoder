package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.database.model.CommandContainerImportCode;
import com.lazycoder.uidatasourceedit.moduleedit.GeneralImportCodeInputPane;


public class CommandContainerImportCodeInputPane extends GeneralImportCodeInputPane {

    protected CommandContainerImportLable importLable;

    /**
     * 新建
     *
     * @param ordinal
     */
    public CommandContainerImportCodeInputPane(int ordinal) {
        super();
        this.ordinal = ordinal;
        init();
        importLable.setLabelText(ordinal);
    }

    /**
     * 还原
     *
     * @param importCode
     */
    public CommandContainerImportCodeInputPane(CommandContainerImportCode importCode) {
        super();
        this.ordinal = importCode.getOrdinal();
        init();
        importLable.setLabelText(importCode.getOrdinal());
        textArea.setText(importCode.getCode());
        codeLabelCombobox.setSelectedCodeLabel(importCode.getCodeLabelId());
    }

    @Override
    protected void init() {
        importLable = new CommandContainerImportLable();
        hbox1.add(importLable);
        super.init();
    }

    /**
     * 获取引入代码
     *
     * @return
     */
    public CommandContainerImportCode getImportCode() {
        CommandContainerImportCode importCode = new CommandContainerImportCode();
        // importCode.setMarkName(markButton.getText().trim());
        importCode.setCode(textArea.getText());
        importCode.setOrdinal(ordinal);
        importCode.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        return importCode;
    }


}
