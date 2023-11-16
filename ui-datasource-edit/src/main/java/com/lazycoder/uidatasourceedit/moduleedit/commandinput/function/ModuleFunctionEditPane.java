package com.lazycoder.uidatasourceedit.moduleedit.commandinput.function;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.save.ModuleFunctionInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;


public class ModuleFunctionEditPane extends JPanel
        implements ModuleEditComponentInterface, CheckInterface , EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = -6421008592129949172L;

    private Border defaultborder;

    /**
     * Create the panel.
     */
    private MyButton addButton, delButton, restoreButton;

    private OperatingTipButton functionButton;

    private JTabbedPane tabbedPane;

    private int currentNum = 0;

    public ModuleFunctionEditPane() {
        setLayout(new BorderLayout(0, 0));

        toolbarInit();
        tabbedInit();
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        currentNum = 0;
        if (Module.TRUE_ == module.getEnabledState()) {
            if (moduleInfo.getNumOfFunctionCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
                ArrayList<String> list = ModuleInfoStaticMethod.getFunctionTypeListParam(moduleInfo);
                ModuleFunctionCategoryPane moduleSetCodeEditPane;
                for (String typeName : list) {
                    currentNum = currentNum + 1;
                    moduleSetCodeEditPane = new ModuleFunctionCategoryPane(currentNum);
                    moduleSetCodeEditPane.displayModuleContent(typeName);
                    tabbedPane.addTab("功能", moduleSetCodeEditPane);
                }
            }
        }
    }

    private MouseAdapter navigateToFunctionMarkMouseAdapter = new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            super.mouseEntered(mouseEvent);
            ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToFunctionMark(true);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            super.mouseExited(mouseEvent);
            ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToFunctionMark(false);
        }
    };

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                addCategory();

            } else if (e.getSource() == delButton) {
                delCategory();

            } else if (e.getSource() == restoreButton) {
                restore();
            }
        }
    };

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        currentNum = 0;
        tabbedPane.removeAll();
        updateUI();
        repaint();
    }

    private void toolbarInit() {
        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        addButton = new MyButton("添加");
        toolBar.add(addButton);
        addButton.addActionListener(listener);
        addButton.addMouseListener(navigateToFunctionMarkMouseAdapter);

        toolBar.add(Box.createHorizontalStrut(30));
        delButton = new MyButton("删除");
        toolBar.add(delButton);
        delButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));
        restoreButton = new MyButton("还原");
        toolBar.add(restoreButton);
        restoreButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));
        functionButton  = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置" + File.separator + "功能编辑")
                .getAbsolutePath());
        toolBar.add(functionButton);
    }

    private void tabbedInit() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        defaultborder = tabbedPane.getBorder();
    }

    private void addCategory() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            currentNum = currentNum + 1;
            ModuleFunctionCategoryPane moduleSetCodeEditPane = new ModuleFunctionCategoryPane(currentNum);
            tabbedPane.addTab("功能", moduleSetCodeEditPane);
        }
    }

    private void delCategory() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            if (tabbedPane.getComponentCount() > 0) {
                currentNum = currentNum - 1;
                tabbedPane.remove(tabbedPane.getTabCount() - 1);
            }
        }
    }

    private void restore() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(DataSourceEditHolder.currentModule.getModuleId());
            Module module = SysService.MODULE_SERVICE.getModuleById(DataSourceEditHolder.currentModule.getModuleId());
            if (moduleInfo != null && module != null) {
                if (moduleInfo.getNumOfFunctionCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
                    clearModuleContents();
                    displayModuleContents(moduleInfo, module);
                } else {
                    LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
                }
            }
        }
    }

    public String getTypeName(int typeSerialNumber) {
        String typeName = "";
        ModuleFunctionCategoryPane paneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            paneTemp = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);
            if (paneTemp.getTypeSerialNumber() == typeSerialNumber) {
                typeName = paneTemp.getTypeName();
            }
        }
        return typeName;
    }

    public ModuleFunctionInputData getModuleFunctionInputData() {
        ModuleFunctionInputData moduleFunctionInputData = new ModuleFunctionInputData();

        ArrayList<FunctionOperatingModel> operatingList = new ArrayList<>(), operatingTempList;
        ArrayList<FunctionCodeModel> codeList = new ArrayList<>(), codeTempList;
        ModuleFunctionCategoryPane paneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            paneTemp = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);

            operatingTempList = paneTemp.getOperationParamList();
            operatingList.addAll(operatingTempList);

            codeTempList = paneTemp.getCodeParamList();
            codeList.addAll(codeTempList);
        }
        moduleFunctionInputData.setOperatingList(operatingList);
        moduleFunctionInputData.setCodeList(codeList);
        return moduleFunctionInputData;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (tabbedPane.getComponentCount() > 0) {
            if (checkModuleFunctionCategoryPane() == true) {
                ModuleFunctionCategoryPane moduleFunctionCategoryPaneTemp;
                if (tabbedPane.getComponentCount() > 0) {
                    for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                        moduleFunctionCategoryPaneTemp = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);
                        if (moduleFunctionCategoryPaneTemp.check() == false) {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkModuleFunctionCategoryPane() {
        boolean flag = true;
        String typeName1, typeName2;
        ModuleFunctionCategoryPane moduleFunctionCategoryPaneTemp1, moduleFunctionCategoryPaneTemp2;
        if (tabbedPane.getComponentCount() > 0) {
            //           if (ModuleEditPaneHolder.needUseCodeFileEditPane.navigateToFunctionMark(false) == true) {
            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {//检查有没有设置的功能分类名
                moduleFunctionCategoryPaneTemp1 = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);
                typeName1 = moduleFunctionCategoryPaneTemp1.getTypeName();
                for (int a = i + 1; a < tabbedPane.getComponentCount(); a++) {
                    moduleFunctionCategoryPaneTemp2 = (ModuleFunctionCategoryPane) tabbedPane.getComponent(a);
                    typeName2 = moduleFunctionCategoryPaneTemp2.getTypeName();
                    if (typeName1.trim() == typeName2.trim()) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "模块功能那里第" + (i + 1) + "个面板和第" + (a + 1) + "个面板分类名一样，\n请检查清楚录入数据无误后再保存", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }
                if (flag == false) {
                    break;
                }
            }
//            } else {
//                flag = false;
//                MyOptionPane.showMessageDialog(null,
//                        "(^_−)☆  录入功能一般要写在默认代码文件哪里，你到是添加个标记告诉我啊", "系统信息",
//                        JOptionPane.PLAIN_MESSAGE);
//                ModuleEditPaneHolder.useModuleFunctionEditPane.setSelectedIndex(2);//跳到功能编辑面板
//            }
        }
        return flag;
    }

    /**
     * 获取功能种类数量
     *
     * @return
     */
    public int getFunctionTypeNum() {
        return tabbedPane.getComponentCount();
    }

    /**
     * 获取功能类型
     *
     * @return
     */
    private ArrayList<String> getFunctionTypeParams() {
        ArrayList<String> list = new ArrayList<>();
        ModuleFunctionCategoryPane tempPane;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            tempPane = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);
            list.add(tempPane.getTypeName());
        }
        return list;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        ModuleFunctionCategoryPane tempPane;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            tempPane = (ModuleFunctionCategoryPane) tabbedPane.getComponent(i);
            list.addAll(tempPane.getAllEditContainerModel());
        }
        return list;
    }

    /**
     * 设置在模块信息里面的相关参数
     *
     * @param moduleInfo
     */
    public void setFunctionRelatedParam(ModuleInfo moduleInfo) {
        ModuleInfoStaticMethod.setFunctionTypeListParam(moduleInfo, getFunctionTypeParams());// 记录功能代码的分类名称
        moduleInfo.setNumOfFunctionCodeType(getFunctionTypeNum());// 记录功能代码的分类数量
    }

    @Override
    public void setUIResponse(boolean status) {
        if (status) {
            tabbedPane.setBorder(DataSourceEditHolder.RESPONSE_TRUE_BORDER);
            setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
        } else {
            tabbedPane.setBorder(defaultborder);
            setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
        }
    }

}
