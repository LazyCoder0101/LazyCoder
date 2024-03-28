package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NoNeedInserNewLine;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent.CodeUsePropetyMenuItem;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CodeUsePropetyMenu extends JMenu {

    protected CodeUsePropetyMenuItem noNeedInserNewLineMenuItem;

    public CodeUsePropetyMenu(){
        noNeedInserNewLineMenuItem = new CodeUsePropetyMenuItem(new NoNeedInserNewLine(),this);
        add(noNeedInserNewLineMenuItem);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doClick();
            }
        });
    }

    /**
     * 设置对应属性
     *
     * @param codeUsePropertyParam
     */
    public void setCodeUsePropertyParam(String codeUsePropertyParam){
        if (codeUsePropertyParam != null) {
            ArrayList<AbstractCodeUseProperty> codeUsePropertyList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
            if (GeneralCommandCodeModel.contant(codeUsePropertyList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                noNeedInserNewLineMenuItem.setSelected(true);
            }
            showText();
        }
    }

    /**
     * 取消选择某个代码属性值对应的菜单
     *
     * @param codeUsePropetyDictionaryValue
     * @param flag
     */
    public void setSelectedCodeUsePropety(int codeUsePropetyDictionaryValue, boolean flag){
        if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedInserNewLine) {
            noNeedInserNewLineMenuItem.setSelected(flag);
        }
        showText();
    }

    /**
     * 将某个代码属性对应的菜单项 失/使 能
     *
     * @param codeUsePropetyDictionaryValue
     * @param flag
     */
    public void setEnabled(int codeUsePropetyDictionaryValue, boolean flag){
        if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedInserNewLine) {
            //无需换行
            noNeedInserNewLineMenuItem.setEnabled(flag);
        }
    }

    /**
     * 更改显示文字
     */
    public void showText(){
        ArrayList<String> codeUsePropertyTextList = new ArrayList<>();
        if (noNeedInserNewLineMenuItem.isSelected()) {
            codeUsePropertyTextList.add(noNeedInserNewLineMenuItem.getCodeUseProperty().getShowText());
        }
        if (codeUsePropertyTextList.size() == 0) {
            setText("");
            setToolTipText(null);
        } else {
            String text = StringUtils.join(codeUsePropertyTextList, "、");
            setText(text);
            setToolTipText(HTMLText.createHtmlContent(text, false));
        }
    }

    /**
     * 获取已选的属性值参数
     * @return
     */
    public String getCodeUsePropetyParam(){
        ArrayList<AbstractCodeUseProperty> codeUsePropertyArrayList = new ArrayList<>();
        if (noNeedInserNewLineMenuItem.isSelected()) {
            codeUsePropertyArrayList.add(noNeedInserNewLineMenuItem.getCodeUseProperty());
        }
        return JsonUtil.getJsonStr(codeUsePropertyArrayList);
    }

}
