package com.lazycoder.uidatasourceedit.moduleedit.toolbar.nousemodule;

import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.component.MultiSelectComboBox;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用该模块不能使用的模块 列出所有不是每次填写程序都要填写的模块列表
 *
 * @param <Module>
 * @author admin
 */
public class NoUseModuleCombobox<Module> extends MultiSelectComboBox<Module> implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 4973838232442803984L;

    public NoUseModuleCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setRenderer(new NoUseModuleComboboxRenderer());
        // setModel((ComboBoxModel<Module>)
    }

    /**
     * 给moduel写入不需要使用的模块
     */
    @SuppressWarnings("unchecked")
    public void setNoUseModuleList(com.lazycoder.database.model.Module module) {
        List<com.lazycoder.database.model.Module> list = (List<com.lazycoder.database.model.Module>) getSelectedItems();
        ModuleStaticMethod.setNoUseModuleParam(module, list);
    }

    /**
     * 返回选择的值，用','拼接的字符串
     *
     * @return
     */
    @Override
    public String getSelectedItemsString() {
        List<String> list = new ArrayList<>();
        for (Integer index : getSelectedSortedIndexs()) {
            Module elementAt = getModel().getElementAt(index);
            list.add(elementAt == null ? "" : ((com.lazycoder.database.model.Module) elementAt).getModuleName());
        }
        return spliceCollectionValue(list, "、");
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, com.lazycoder.database.model.Module module) {
        // TODO Auto-generated method stub
        removeAllItems();
        removePopupMenuListener(listener);
        addItems();
        if (module.getNoUseModuleParam().equals(com.lazycoder.database.model.Module.NEW_STATE)) {// 此前从没被编辑过

        } else {// 此前有编辑过内容
            ArrayList<com.lazycoder.database.model.Module> list = SysService.MODULE_SERVICE.getNeedModuleList(module);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    addSelectedModule(list.get(i));
                }
            }
        }
        addPopupMenuListener(listener);

    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
    }

    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

        @SuppressWarnings("unchecked")
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }
    };

    /**
     * 去除一个选中的选项
     *
     * @param list
     */
    public void removeSelectedModuleList(List<com.lazycoder.database.model.Module> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean flag = checkHaveOrNot(list.get(i));// 查看有没有这个
            if (flag == true) {// 有这选项
                removeSelectedIndex(i);
            }
        }
    }

    /**
     * 检查目前的选中的选项有没有这个
     *
     * @param module
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean checkHaveOrNot(com.lazycoder.database.model.Module module) {
        boolean flag = false;
        List<com.lazycoder.database.model.Module> list = (List<com.lazycoder.database.model.Module>) getSelectedItems();
        com.lazycoder.database.model.Module temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if (temp.getModuleName().equals(module.getModuleName())) {
                if (temp.getClassName().equals(module.getClassName())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 添加一个选中的选项
     *
     * @param module
     */
    private void addSelectedModule(com.lazycoder.database.model.Module module) {
        if (module != null) {
            for (int i = 0; i < dataModel.getSize(); i++) {
                com.lazycoder.database.model.Module element = (com.lazycoder.database.model.Module) dataModel
                        .getElementAt(i);
                if (element.getModuleName().equals(module.getModuleName())) {
                    if (element.getClassName().equals(module.getClassName())) {
                        addSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 添加下拉框的选项
     */
    @SuppressWarnings("unchecked")
    private void addItems() {
        DefaultComboBoxModel<com.lazycoder.database.model.Module> model = new DefaultComboBoxModel<>();

        List<com.lazycoder.database.model.Module> list = SysService.MODULE_SERVICE.getModulesListThatAreNotRequired();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                model.addElement(list.get(i));
            }
        }
        setModel((ComboBoxModel<Module>) model);
    }

}