package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base;

import com.lazycoder.database.model.OptionDataModel;
import javax.swing.JMenuItem;
import lombok.Getter;

/**
 * 选项选择使用的菜单项
 */
public class ContentOptionMenuItem extends JMenuItem {

    @Getter
    private OptionDataModel optionDataModel = null;

    public ContentOptionMenuItem(OptionDataModel optionDataModel) {
        super(optionDataModel.getOptionName());
        this.optionDataModel = optionDataModel;
    }

}
