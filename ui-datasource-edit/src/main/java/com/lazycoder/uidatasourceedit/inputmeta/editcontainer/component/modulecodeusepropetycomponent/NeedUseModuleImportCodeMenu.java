package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent;

import com.lazycoder.database.model.Module;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NeedUseModuleImportCode;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.ModuleCodeUsePropetyMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenu;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class NeedUseModuleImportCodeMenu extends JMenu {

    private ModuleCodeUsePropetyMenu moduleCodeUsePropetyMenu = null;

    @Getter
    private NeedUseModuleImportCode needUseModuleImportCode = new NeedUseModuleImportCode();

    public NeedUseModuleImportCodeMenu(ModuleCodeUsePropetyMenu moduleCodeUsePropetyMenu) {
        setText(needUseModuleImportCode.getShowText());
        this.moduleCodeUsePropetyMenu = moduleCodeUsePropetyMenu;
        addAllMenuItems();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    updateItems();
                }
//                doClick();
            }
        });
    }

    public String getMyText() {
        return needUseModuleImportCode.getShowText() + "（" + StringUtils.join(getSelectedModuleName(),"、") + "）";
    }

    /**
     * 查看有没有选中其中某个选项
     *
     * @return
     */
    public boolean isSelectedOrNot() {
        boolean flag = false;
        int menuItemNum = getMenuComponentCount();
        NeedUseModuleImportCodeMenuItem menuItem;
        for (int i = 0; i < menuItemNum; i++) {
            if (getItem(i) instanceof NeedUseModuleImportCodeMenuItem) {
                menuItem = (NeedUseModuleImportCodeMenuItem) getItem(i);
                if (menuItem.isSelected()) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 根据参数设置值
     *
     * @param needUseModuleImportCode
     */
    public void setNeedUseModuleImportCode(NeedUseModuleImportCode needUseModuleImportCode) {
        this.needUseModuleImportCode = needUseModuleImportCode;
        removeAll();
        addAllMenuItems();
        if (needUseModuleImportCode.getNeedUseModuleList().size() > 0) {
            setSelectedMenuItems(needUseModuleImportCode.getNeedUseModuleList());
        }
        if (this.moduleCodeUsePropetyMenu != null) {
            this.moduleCodeUsePropetyMenu.showText();
        }
    }

    /**
     * 更新选项值
     */
    private void updateItems() {
        ArrayList<String> selectedList = getSelectedModules();
        this.removeAll();
        addAllMenuItems();
        setSelectedMenuItems(selectedList);
    }

    public void addAllMenuItems() {
        if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
            ArrayList<Module> moduleList = ModuleEditPaneHolder.relatedModuleInfoMenu.getSelectedNeedUseModuleList();
            if (moduleList != null) {
                NeedUseModuleImportCodeMenuItem menuItem;
                for (Module moduleTemp : moduleList) {
                    menuItem = new NeedUseModuleImportCodeMenuItem(moduleTemp, this, this.moduleCodeUsePropetyMenu);
                    add(menuItem);
                }
            }
        }
    }

    /**
     * 获取已选中的，需要的模块的模块id
     *
     * @return
     */
    public ArrayList<String> getSelectedModuleName() {
        ArrayList<String> selectedModules = new ArrayList<>();
        NeedUseModuleImportCodeMenuItem menuItem;
        int menuItemNum = getMenuComponentCount();
        for (int i = 0; i < menuItemNum; i++) {
            if (getItem(i) instanceof NeedUseModuleImportCodeMenuItem) {
                menuItem = (NeedUseModuleImportCodeMenuItem) getItem(i);
                if (menuItem.isSelected()) {
                    selectedModules.add(menuItem.getModuleName());
                }
            }
        }
        return selectedModules;
    }

    /**
     * 获取已选中的，需要的模块的模块id
     *
     * @return
     */
    public ArrayList<String> getSelectedModules() {
        ArrayList<String> selectedModules = new ArrayList<>();
        NeedUseModuleImportCodeMenuItem menuItem;
        int menuItemNum = getMenuComponentCount();
        for (int i = 0; i < menuItemNum; i++) {
            if (getItem(i) instanceof NeedUseModuleImportCodeMenuItem) {
                menuItem = (NeedUseModuleImportCodeMenuItem) getItem(i);
                if (menuItem.isSelected()) {
                    selectedModules.add(menuItem.getModuleId());
                }
            }
        }
        return selectedModules;
    }

    public void setSelectedMenuItems(ArrayList<String> selectedList) {
        NeedUseModuleImportCodeMenuItem menuItem;
        int menuItemNum = getMenuComponentCount();
        for (String moduleIDTemp : selectedList) {
            for (int i = 0; i < menuItemNum; i++) {
                if (getItem(i) instanceof NeedUseModuleImportCodeMenuItem) {
                    menuItem = (NeedUseModuleImportCodeMenuItem) getItem(i);
                    if (menuItem.getModuleId().equals(moduleIDTemp)) {
                        menuItem.setSelected(true);
                        break;
                    }
                }
            }
        }
    }


}

