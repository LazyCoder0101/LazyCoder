package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.database.model.ImportCode;
import com.lazycoder.uidatasourceedit.moduleedit.GeneralImportCodeInputPane;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class ImportCodeInputPane extends GeneralImportCodeInputPane {

    protected ImportLable importLable;

    /**
     * 新建
     *
     * @param ordinal
     */
    public ImportCodeInputPane(int ordinal) {
        super(ordinal);
        importLable.setLabelText(ordinal);
    }

    /**
     * 还原
     *
     * @param importCode
     */
    public ImportCodeInputPane(ImportCode importCode) {
        super(importCode);
        importLable.setLabelText(importCode.getOrdinal());
    }

    @Override
    protected void init() {
        importLable = new ImportLable();
        hbox1.add(importLable);
        super.init();
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);
    }

    /**
     * 获取引入代码
     *
     * @return
     */
    public ImportCode getImportCode() {
        ImportCode importCode = new ImportCode();
        // importCode.setMarkName(markButton.getText().trim());
        importCode.setCode(textArea.getText());
        importCode.setOrdinal(ordinal);
        importCode.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        return importCode;
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            importLable.getMarkElement().setCodeLabelId(codeLabelCombobox.getCodeLabelId());
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    };


}
