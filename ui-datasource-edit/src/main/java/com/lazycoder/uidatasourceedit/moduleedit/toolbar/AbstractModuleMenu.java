package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import com.lazycoder.database.model.Module;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public abstract class AbstractModuleMenu extends JMenu {

    /**
     *
     */
    private static final long serialVersionUID = -5992000341825494719L;

    public AbstractModuleMenu() {
        // TODO Auto-generated constructor stub
        super();
        addMenuListener(menuListener);
    }

    private MenuListener menuListener = new MenuListener() {
        @Override
        public void menuSelected(MenuEvent menuEvent) {
            updateMenuItem();
        }

        @Override
        public void menuDeselected(MenuEvent menuEvent) {
        }

        @Override
        public void menuCanceled(MenuEvent menuEvent) {
        }
    };

    /**
     * 刷新菜单项
     */
    protected abstract void updateMenuItem();

    /**
     * 设置需要用到的模块
     *
     * @param moduleList
     */
    protected void selectedModuleListTrue(List<Module> moduleList) {
        GeneralModuleMenuItem item;
        Module moduleTemp;
        for (int a = 0; a < moduleList.size(); a++) {
            moduleTemp = moduleList.get(a);
            for (int i = 0; i < getMenuComponentCount(); i++) {
                item = (GeneralModuleMenuItem) getItem(i);
                boolean flag = item.isModule(moduleTemp);
                if (flag == true) {
                    item.setSelected(true);
                    break;
                }
            }
        }
    }

    /**
     * 获取当前选中的模块
     *
     * @return
     */
    public ArrayList<Module> getCurrentSelectedModuleList() {
        GeneralModuleMenuItem item;
        Module moduleTemp;

        ArrayList<Module> list = new ArrayList<>();
        for (int i = 0; i < getMenuComponentCount(); i++) {
            item = (GeneralModuleMenuItem) getItem(i);
            moduleTemp = item.getModule();
            if (item.isSelected() == true) {
                list.add(moduleTemp);
            }
        }
        return list;
    }

    /**
     * 获取当前选中的模块
     *
     * @return
     */
    public ArrayList<Module> getCurrentCanNotSelectedModuleList() {
        GeneralModuleMenuItem item;
        Module moduleTemp;

        ArrayList<Module> list = new ArrayList<>();
        for (int i = 0; i < getMenuComponentCount(); i++) {
            item = (GeneralModuleMenuItem) getItem(i);
            moduleTemp = item.getModule();
            if (item.isEnabled() == false) {
                list.add(moduleTemp);
            }
        }
        return list;
    }

    /**
     * 获取当前没有选中的模块
     *
     * @return
     */
    protected ArrayList<Module> getCurrentNoSelectedModuleList() {
        GeneralModuleMenuItem item;
        Module moduleTemp;

        ArrayList<Module> list = new ArrayList<>();
        for (int i = 0; i < getMenuComponentCount(); i++) {
            item = (GeneralModuleMenuItem) getItem(i);
            moduleTemp = item.getModule();
            if (item.isSelected() == false) {
                list.add(moduleTemp);
            }
        }
        return list;
    }

    /**
     * 把moduleList的模块设置为失能
     *
     * @param moduleList
     */
    public void setModuleListEnabledFalse(List<Module> moduleList) {
        GeneralModuleMenuItem item;
        Module comparisonModule;

        for (Module moduleTemp : moduleList) {
            for (int i = 0; i < getMenuComponentCount(); i++) {
                item = (GeneralModuleMenuItem) getItem(i);
                comparisonModule = item.getModule();
                if (comparisonModule.getModuleId().equals(moduleTemp.getModuleId())) {
                    item.setEnabled(false);
                    break;
                }
            }
        }
    }

    /**
     * 把moduleList的模块设置为使能
     *
     * @param moduleList
     */
    public void setModuleListEnabledTrue(ArrayList<Module> moduleList) {
        GeneralModuleMenuItem item;
        Module moduleTemp, comparisonModule;

        for (int a = 0; a < moduleList.size(); a++) {
            moduleTemp = moduleList.get(a);
            for (int i = 0; i < getMenuComponentCount(); i++) {
                item = (GeneralModuleMenuItem) getItem(i);
                comparisonModule = item.getModule();
                if (comparisonModule.getModuleId().equals(moduleTemp.getModuleId())) {
                    item.setEnabled(true);
                    break;
                }
            }
        }
    }

    public void showText() {
        HTMLText htmlText = new HTMLText();
        HtmlPar par = new HtmlPar();
        par.addColorText("无", HtmlPar.BLUE, false);
        htmlText.addPar(par);

        StringBuilder out = new StringBuilder();
        List<Module> list = getCurrentSelectedModuleList();
        Module temp;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                temp = list.get(i);
                if (i == list.size() - 1) {
                    out.append(temp.getModuleName());
                } else {
                    out.append(temp.getModuleName() + "、");
                }
            }
            htmlText = new HTMLText();
            par = new HtmlPar();
            par.addColorText(out.toString(), HtmlPar.MAROON, false);
            htmlText.addPar(par);
        }
        setText(htmlText.getHtmlContent());
    }

}
