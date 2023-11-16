package com.lazycoder.uidatasourceedit.moduleedit.toolbar.needmodule;

import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


/**
 * 使用模块前，需要调用的模块的设置下拉框 该下拉框列出所有模块
 *
 * @param <Module>
 * @author admin
 */
public class NeedModuleCombobox<Module> extends MultiSelectComboBox<Module> implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -1584741096952249466L;

    public NeedModuleCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setRenderer(new NeedModuleComboboxRenderer());
        // setModel((ComboBoxModel<Module>)
        // NeedModuleComboboxRenderer.getTestModel());
        // addPopupMenuListener(listener);
    }

    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

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
     * 添加需要用到的模块列表
     */
    @SuppressWarnings("unchecked")
    public void addNeedUseModuleList() {
        List<com.lazycoder.database.model.Module> moduleList = (List<com.lazycoder.database.model.Module>) getSelectedItems();
        if (moduleList != null) {
            ModuleEditPaneHolder.needUseCodeFileEditPane.addNeedUseCodeFormatPaneByModuleList(moduleList);
        }
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

        if (module.getNeedModuleParam().equals(com.lazycoder.database.model.Module.NEW_STATE)) {// 此前未被编辑过

        } else {// 此前有编辑过内容
            List<com.lazycoder.database.model.Module> list = SysService.MODULE_SERVICE.getNeedModuleList(module);
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
     * 去除一个选中的选项
     *
     * @param module
     */
    @SuppressWarnings("unchecked")
    public void removeSelectedModule(com.lazycoder.database.model.Module module) {
        com.lazycoder.database.model.Module temp;
        List<com.lazycoder.database.model.Module> list = (List<com.lazycoder.database.model.Module>) getSelectedItems();
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if (temp.getModuleName().equals(module.getModuleName())) {
                if (temp.getClassName().equals(module.getModuleName())) {
                    removeSelectedIndex(i);
                }
            }
        }
    }

    /**
     * 获取所有的模块，添加到下拉列表
     */
    @SuppressWarnings("unchecked")
    private void addItems() {
        DefaultComboBoxModel<com.lazycoder.database.model.Module> model = new DefaultComboBoxModel<>();

        List<com.lazycoder.database.model.Module> list = SysService.MODULE_SERVICE
                .getModuleListExceptNonModuleAnd(DataSourceEditHolder.currentModule.getModuleId());
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                model.addElement(list.get(i));
            }
        }
        setModel((ComboBoxModel<Module>) model);
    }

    /**
     * 给module写入需要使用的模块
     *
     * @param module
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void setNeedModuleParam(com.lazycoder.database.model.Module module) {
        List<com.lazycoder.database.model.Module> list = (List<com.lazycoder.database.model.Module>) getSelectedItems();
        ModuleStaticMethod.setNeedModuleParam(module, list);
    }

}