package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class ModuleCombobox extends JComboBox<String> {

    /**
     *
     */
    private static final long serialVersionUID = -7288471830130016686L;

    private Object[] options = {"对，先给我保存当前的内容！", "没错\n，赶紧的！ (ง •_•)ง", "点错而已 ┓(￣ェ￣;)┏"};

    public ModuleCombobox() {
        // TODO Auto-generated constructor stub
        super();
        accordingModuleEditPaneContentAtTheBeginning();
        // addPopupMenuListener(listener2);
        // addItemListener(listener);
        addActionListener(actionListener);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            showModuleContent();
        }
    };

    /**
     * 在起始的时候显示内容
     */
    public void accordingModuleEditPaneContentAtTheBeginning() {
        removeAllItems();
//        String className = (String) ModuleEditPaneHolder.classificationCombobox.getSelectedItem();
//        if (className != null) {
//            if ("".equals(className) == false) {
//                List<Module> list = SysService.moduleService.getModuleList(className);
//                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//                if (list != null) {
//                    for (Module moduleTemp: list) {
//                        model.addElement(moduleTemp.getModuleName());
//                    }
//                }
//                setModel(model);
//            }
//        }
    }

    /**
     * 显示模块内容
     */
    private void showModuleContent() {
//        if (ModuleEditPaneHolder.classificationCombobox != null) {
//
//            String className = (String) ModuleEditPaneHolder.classificationCombobox.getSelectedItem();
//            String moduleName = (String) getSelectedItem();
//            Module module = SysService.moduleService.getModule(className, moduleName);
//
//            if (moduleName != null && className != null && module != null) {
//                if ("".equals(DataSourceEditHolder.className) || "".equals(DataSourceEditHolder.moduleName)) {// 如果分类名和变量名为空（当前没有显示其他模块的内容），直接显示
//
//                    DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentDataBaseName,
//                            module.getModuleId());//检查该模块里面必要的文件夹有没有问题，有的话，修复
//                    ModuleEditPaneHolder.showModuleData(className, moduleName);
//                    if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
//                        ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
//                    }
//
//                } else {// 当前显示有其他模块的内容
//
//                    int temp = MyOptionPane.showOptionDialog(null,
//                            "(^_^)  亲，是想编辑\"" + className + "\"分类  \"" + moduleName + "\"模块的内容吗？", "系统提示",
//                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
//                            options[0]);
//                    if (temp == 0) {// 是，并且保存当前内容
//                        DataSourceEditHolder.moduleEditPane.save();
//                        DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentDataBaseName,
//                                module.getModuleId());
//                        ModuleEditPaneHolder.showModuleData(className, moduleName);
//                        if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
//                            ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
//                        }
//
//                    } else if (temp == 1) {// 是，但不保存当前的内容
//                        DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentDataBaseName,
//                                module.getModuleId());
//                        ModuleEditPaneHolder.showModuleData(className, moduleName);
//                        if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
//                            ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
//                        }
//                    } else if (temp == 2) {
//                        DataSourceEditHolder.className = className;
//                        DataSourceEditHolder.moduleName = moduleName;
//                    }
//                }
//            }
//        }
    }

    public void updateItems() {
//        Object selectedModuleName = ModuleEditPaneHolder.moduleCombobox.getSelectedItem();
//        removeActionListener(actionListener);
//        removeAllItems();
//        List<Module> list = SysService.moduleService.getModuleList(DataSourceEditHolder.className);
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//        if (list != null) {
//            for (int i = 0; i < list.size(); i++) {
//                model.addElement(list.get(i).getModuleName());
//            }
//        }
//        setModel(model);
//        ModuleEditPaneHolder.moduleCombobox.setSelectedItem(selectedModuleName);
//        addActionListener(actionListener);
    }


}
