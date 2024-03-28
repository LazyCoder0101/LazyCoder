package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NeedUseModuleImportCode;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NoNeedToInsertImportCode;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent.CodeUsePropetyMenuItem;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent.NeedUseModuleImportCodeMenu;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.ArrayList;

public class ModuleCodeUsePropetyMenu extends CodeUsePropetyMenu {

    private CodeUsePropetyMenuItem noNeedToInsertImportCodeMenuItem;

    private NeedUseModuleImportCodeMenu needUseModuleImportCodeMenu;

    public ModuleCodeUsePropetyMenu() {
        super();
        noNeedToInsertImportCodeMenuItem = new CodeUsePropetyMenuItem(new NoNeedToInsertImportCode(), this);
        add(noNeedToInsertImportCodeMenuItem);

        needUseModuleImportCodeMenu = new NeedUseModuleImportCodeMenu(this);
        add(needUseModuleImportCodeMenu);
//        for (CodeUseProperty codeUseProperty : CodeUseProperty.values()) {
//            codeUsePropetyMenuItem = new CodeUsePropetyMenuItem(codeUseProperty);
//            add(codeUsePropetyMenuItem);
//        }
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                doClick();
//            }
//        });

        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem("sd");
        add(menuItem);

        //生成对应的查看引入代码的菜单项
        JMenuItem importCodeMenuItem = generateImportCodeMenuItem();
        if (importCodeMenuItem != null) {
            add(importCodeMenuItem);
        }
    }

    /**
     * 设置对应属性
     *
     * @param codeUsePropertyParam
     */
    @Override
    public void setCodeUsePropertyParam(String codeUsePropertyParam) {
        if (codeUsePropertyParam != null) {
            ArrayList<AbstractCodeUseProperty> codeUsePropertyList = GeneralCommandCodeModel.getCodeUsePropertyList(codeUsePropertyParam);
            if (GeneralCommandCodeModel.contant(codeUsePropertyList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                noNeedInserNewLineMenuItem.setSelected(true);
            }
            if (GeneralCommandCodeModel.contant(codeUsePropertyList, AbstractCodeUseProperty.NoNeedToInsertImportCodeType)) {
                noNeedToInsertImportCodeMenuItem.setSelected(true);
            }
            if (GeneralCommandCodeModel.contant(codeUsePropertyList, AbstractCodeUseProperty.NeedUseModuleImportCodeType)) {
                NeedUseModuleImportCode needUseModuleImportCode = GeneralCommandCodeModel.getNeedUseModuleImportCodeProperty(codeUsePropertyList);
                if (needUseModuleImportCode != null) {
                    needUseModuleImportCodeMenu.setNeedUseModuleImportCode(needUseModuleImportCode);
                }
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
    @Override
    public void setSelectedCodeUsePropety(int codeUsePropetyDictionaryValue, boolean flag) {
        //
        if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedToInsertImportCodeType) {
            //无需写入本模块引入代码
            noNeedToInsertImportCodeMenuItem.setSelected(flag);
//        } else if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NeedUseModuleImportCodeType) {
//            //需要模块的引入代码
//            Component[] menuList = getMenuComponents();
//            NeedUseModuleImportCodeMenu needUseModuleImportCodeMenu;
//            for (Component menuComponent : menuList) {
//                if (menuComponent instanceof NeedUseModuleImportCodeMenu) {
//                    needUseModuleImportCodeMenu = (NeedUseModuleImportCodeMenu) menuComponent;
//                    if (flag) {
//                        needUseModuleImportCodeMenu.setEnabled(true);
//                    } else {
//                        needUseModuleImportCodeMenu.removeAll();
//                        needUseModuleImportCodeMenu.setEnabled(false);
//                    }
//                    break;
//                }
//            }
        } else if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedInserNewLine) {
            //无需换行
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
    @Override
    public void setEnabled(int codeUsePropetyDictionaryValue, boolean flag) {
        if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedToInsertImportCodeType) {
            //无需写入本模块引入代码
            noNeedToInsertImportCodeMenuItem.setEnabled(flag);
        } else if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NeedUseModuleImportCodeType) {
            //需要模块的引入代码
            if (flag) {
                needUseModuleImportCodeMenu.setEnabled(true);
            } else {
                needUseModuleImportCodeMenu.removeAll();
                needUseModuleImportCodeMenu.setEnabled(false);
            }
        } else if (codeUsePropetyDictionaryValue == AbstractCodeUseProperty.NoNeedInserNewLine) {
            //无需换行
            noNeedInserNewLineMenuItem.setEnabled(flag);
        }
    }

    @Override
    public void showText() {
        ArrayList<String> codeUsePropertyTextList = new ArrayList<>();
        if (noNeedToInsertImportCodeMenuItem.isSelected()) {
            codeUsePropertyTextList.add(noNeedToInsertImportCodeMenuItem.getCodeUseProperty().getShowText());
        }
        if (needUseModuleImportCodeMenu.isSelectedOrNot()) {
            codeUsePropertyTextList.add(needUseModuleImportCodeMenu.getMyText());
        }
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

    @Override
    public String getCodeUsePropetyParam() {
        ArrayList<AbstractCodeUseProperty> codeUsePropertyArrayList = new ArrayList<>();
        if (noNeedInserNewLineMenuItem.isSelected()) {
            codeUsePropertyArrayList.add(noNeedInserNewLineMenuItem.getCodeUseProperty());
        }
        if (noNeedToInsertImportCodeMenuItem.isSelected()) {
            codeUsePropertyArrayList.add(noNeedToInsertImportCodeMenuItem.getCodeUseProperty());
        }
        if (needUseModuleImportCodeMenu.isSelectedOrNot()) {
            codeUsePropertyArrayList.add(needUseModuleImportCodeMenu.getNeedUseModuleImportCode());
        }
        return JsonUtil.getJsonStr(codeUsePropertyArrayList);
    }

    /**
     * 生成查看代码 对应的引入代码 的菜单项的方法（如果为 null，则不生成该菜单）
     * @return
     */
    public JMenuItem generateImportCodeMenuItem(){
        return null;
    }

}
