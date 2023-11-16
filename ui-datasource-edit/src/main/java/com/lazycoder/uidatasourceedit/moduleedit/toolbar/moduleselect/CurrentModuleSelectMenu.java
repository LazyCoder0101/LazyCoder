package com.lazycoder.uidatasourceedit.moduleedit.toolbar.moduleselect;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

public class CurrentModuleSelectMenu extends JMenu {

    public CurrentModuleSelectMenu() {
        super();
        uploadModuleItems();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doClick();
            }
        });
    }

    /**
     * 添加所有选项
     */
    public void uploadModuleItems() {
        removeAll();
        CurrentModuleSelectMenuItem item;
        List<Module> list = SysService.MODULE_SERVICE.getAllModuleList();
        List<TheClassification> classList = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
        if (list != null) {
            TheClassification currentClassification = null;
            Module currentModule = null;
            JMenu classMenu;
            for (TheClassification classification : classList) {
                classMenu = new JMenu(classification.getClassName());
                this.add(classMenu);
                for (Module temp : list) {
                    if (classification.getClassName().equals(temp.getClassName())) {
                        item = new CurrentModuleSelectMenuItem(temp, classification);
                        classMenu.add(item);
                        if (DataSourceEditHolder.currentModule != null) {
                            if (DataSourceEditHolder.currentModule.getModuleId().equals(temp.getModuleId())) {
                                currentClassification = classification;
                                currentModule = temp;
                            }
                        }
                    }
                }
            }
            if (DataSourceEditHolder.currentModule != null &&
                    currentClassification != null &&
                    currentModule != null) {//重新选上当前模块
                selectModule(currentModule, currentClassification);
            }
        }
    }

    /**
     * 更新模块数据，（给保存模块编辑的数据以后调用，刷新数据）
     *
     * @param newData
     */
    public void updateModuleData(Module newData) {
        JMenu jmenuTemp;
        CurrentModuleSelectMenuItem menuItemTemp;
        Component[] menuList = getMenuComponents(), menuItemList;
        boolean loopFlag = true;
        for (Component menuComponent : menuList) {
            if (menuComponent instanceof JMenu) {
                jmenuTemp = (JMenu) menuComponent;
                menuItemList = jmenuTemp.getMenuComponents();
                for (Component menuItemComponent : menuItemList) {
                    if (menuItemComponent instanceof CurrentModuleSelectMenuItem) {
                        menuItemTemp = (CurrentModuleSelectMenuItem) menuItemComponent;
                        boolean flag = menuItemTemp.updateModuleData(newData);
                        if (flag == true) {
                            loopFlag = false;
                            break;
                        }
                    }
                }
                if (loopFlag == false) {
                    break;
                }
            }
        }
    }

    /**
     * 向用户确认
     */
    public void confirmToUser(Module module, TheClassification classification) {
        Object[] options = {"对，先给我保存当前的内容！", "没错\n，赶紧的！ (ง •_•)ง", "点错而已 ┓(￣ェ￣;)┏"};

        String className = classification.getClassName(),
                moduleName = module.getModuleName(),
                moduleId = module.getModuleId();

        module = SysService.MODULE_SERVICE.getModuleById(moduleId);//在这里重新从数据库获取新的数据，避免之前因为菜单项对应模块信息和模块类没有及时更新而导致拿到旧数据
        if (moduleName != null && className != null && module != null) {
            if (DataSourceEditHolder.currentModule == null) {// 如果分类名和变量名为空（当前没有显示其他模块的内容），直接显示

                DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                        module.getModuleId());//检查该模块里面必要的文件夹有没有问题，有的话，修复
                ModuleEditPaneHolder.showModuleData(module, classification);
                if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                    ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
                }

            } else {// 当前显示有其他模块的内容

                int temp = LazyCoderOptionPane.showOptionDialog(null,
                        "(^_^)  亲，是想编辑\"" + className + "\"分类  \"" + moduleName + "\"模块的内容吗？", "系统提示",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
                        options[0]);
                if (temp == 0) {// 是，并且保存当前内容
                    DataSourceEditHolder.moduleEditPane.save();
                    DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                            module.getModuleId());
                    ModuleEditPaneHolder.showModuleData(module, classification);
                    if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                        ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
                    }

                } else if (temp == 1) {// 是，但不保存当前的内容
                    DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                            module.getModuleId());
                    ModuleEditPaneHolder.showModuleData(module, classification);
                    if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                        ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
                    }

//                } else if (temp == 2) {
                }
            }
        }
    }

    public void selectModule(Module module, TheClassification classification) {
        setText(module.getModuleName());
        HTMLText htmlText = new HTMLText();
        HtmlPar par1 = new HtmlPar();
        par1.addColorText("所属分类：", HtmlPar.Munsell, false);
        par1.add(classification.getClassName());
        htmlText.addPar(par1);
        setToolTipText(htmlText.getHtmlContent());

        DataSourceEditHolder.currentModule = module;
    }

}
