package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleset;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.save.ModuleSetInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
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
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;


public class ModuleSetCodeEditPane extends JPanel
        implements ModuleEditComponentInterface, CheckInterface, EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = -7412530082047240398L;

    private Border defaultborder;

    /**
     * Create the panel.
     */
    private JTabbedPane tabbedPane;

    private MyButton addButton, delButton, restoreButton;

    private OperatingTipButton moduleSetButton;

    private int currentNum = 0;

    public ModuleSetCodeEditPane() {
        setLayout(new BorderLayout(0, 0));

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        addButton = new MyButton("添加");
        toolBar.add(addButton);
        addButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));

        delButton = new MyButton("删除");
        toolBar.add(delButton);
        delButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));
        restoreButton = new MyButton("还原");
        toolBar.add(restoreButton);
        restoreButton.addActionListener(listener);

        toolBar.add(Box.createHorizontalStrut(30));
        moduleSetButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置" + File.separator + "模块设置编辑")
                .getAbsolutePath());
        toolBar.add(moduleSetButton);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        defaultborder = tabbedPane.getBorder();
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            // TODO Auto-generated method stub
            if (arg0.getSource() == addButton) {
                addCategory();
            } else if (arg0.getSource() == delButton) {
                delCategory();
            } else if (arg0.getSource() == restoreButton) {
                restore();
            }
        }
    };


    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        if (Module.TRUE_ == module.getEnabledState()) {
            if (moduleInfo.getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
                currentNum = 0;
                ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;
                ArrayList<String> list = ModuleInfoStaticMethod.getModuleSetTypeListParam(moduleInfo);
                if (list != null) {
                    for (String typeName : list) {
                        currentNum++;
                        moduleSetCategoryEditPaneTemp = new ModuleSetCategoryEditPane(currentNum);
                        moduleSetCategoryEditPaneTemp.displayModuleContent(typeName);
                        tabbedPane.addTab("设置" + currentNum, moduleSetCategoryEditPaneTemp);
                    }
                }
            }
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        currentNum = 0;
        tabbedPane.removeAll();
    }

    public ModuleSetInputData getModuleSetInputData() {
        ModuleSetInputData moduleSetInputData = new ModuleSetInputData();

        ArrayList<ModuleSetOperatingModel> operatingList = new ArrayList<>(), operatingTempList;
        ArrayList<ModuleSetCodeModel> codeList = new ArrayList<>(), codeTempList;
        ModuleSetCategoryEditPane paneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            paneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(i);

            operatingTempList = paneTemp.getOperationParamList();
            operatingList.addAll(operatingTempList);

            codeTempList = paneTemp.getCodeParamList();
            codeList.addAll(codeTempList);
        }
        moduleSetInputData.setOperatingList(operatingList);
        moduleSetInputData.setCodeList(codeList);

        return moduleSetInputData;
    }

    private void addCategory() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            currentNum++;
            ModuleSetCategoryEditPane moduleSetCodeEditPane = new ModuleSetCategoryEditPane(currentNum);
            tabbedPane.addTab("设置" + currentNum, moduleSetCodeEditPane);
        }
    }

    private void delCategory() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            if (tabbedPane.getComponentCount() > 0) {
                currentNum--;
                tabbedPane.remove(tabbedPane.getComponent(tabbedPane.getComponentCount() - 1));
            }
        }
    }

    private void restore() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(DataSourceEditHolder.currentModule.getModuleId());
            Module module = SysService.MODULE_SERVICE.getModuleById(DataSourceEditHolder.currentModule.getModuleId());
            if (moduleInfo != null && module != null) {
                if (moduleInfo.getNumOfSetCodeType() > 0) {// 此前添加过内容，显示之前编辑过的内容
                    clearModuleContents();
                    displayModuleContents(moduleInfo, module);
                } else {
                    LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
                }
            }
        }
    }

    /**
     * 获取模块类型列表
     *
     * @return
     */
    private ArrayList<String> getModuleSetTypeList() {
        ArrayList<String> list = new ArrayList<>();
        ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            moduleSetCategoryEditPaneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(i);
            list.add(moduleSetCategoryEditPaneTemp.getTheModuleSetTypeName());
        }
        return list;
    }

    /**
     * 设置模块设置的相关参数
     *
     * @param moduleInfo
     */
    public void setModuleSetRelatedParam(ModuleInfo moduleInfo) {
        moduleInfo.setNumOfSetCodeType(getModuleSetNum());// 记录设置代码的分类数量
        ModuleInfoStaticMethod.setModuleSetTypeListParam(moduleInfo, getModuleSetTypeList()); // 记录设置代码的分类名称
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (tabbedPane.getComponentCount() > 0) {
            if (checkModuleSetCategoryEditPane() == true) {
                ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;
                for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                    moduleSetCategoryEditPaneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(i);
                    if (moduleSetCategoryEditPaneTemp.check() == false) {
                        flag = false;
                        break;
                    }
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private boolean checkModuleSetCategoryEditPane() {
        boolean flag = true;
        String typeName1, typeName2;
        ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp1, moduleSetCategoryEditPaneTemp2;
        if (tabbedPane.getComponentCount() > 0) {
            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                moduleSetCategoryEditPaneTemp1 = (ModuleSetCategoryEditPane) tabbedPane.getComponent(i);
                typeName1 = moduleSetCategoryEditPaneTemp1.getTheModuleSetTypeName();
                for (int a = i + 1; a < tabbedPane.getComponentCount(); a++) {
                    moduleSetCategoryEditPaneTemp2 = (ModuleSetCategoryEditPane) tabbedPane.getComponent(a);
                    typeName2 = moduleSetCategoryEditPaneTemp2.getTheModuleSetTypeName();
                    if (typeName1.equals(typeName2)) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "模块设置那里第" + (i + 1) + "个面板和第" + (a + 1) + "个面板分类名一样，\n请检查清楚录入数据无误后再保存", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }
                if (flag == false) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取设置总类数量
     *
     * @return
     */
    public int getModuleSetNum() {
        return tabbedPane.getComponentCount();
    }

    /**
     * 获取该分类的方法总数
     *
     * @param opreatingSerial
     */
    public int getModuleSetOpreatingNum(int opreatingSerial) {
        int moduleSetOpreatingNum = MarkElementName.MARK_NULL;
        if (opreatingSerial > 0) {
            ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;

            for (int a = 0; a < tabbedPane.getComponentCount(); a++) {
                moduleSetCategoryEditPaneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(a);
                if (moduleSetCategoryEditPaneTemp.getTypeSerialNumber() == opreatingSerial) {
                    moduleSetOpreatingNum = moduleSetCategoryEditPaneTemp.getModuleSetOpreatingNum();
                    break;
                }
            }
        }
        return moduleSetOpreatingNum;
    }

    /**
     * 获取该分类的某个方法的代码数
     *
     * @return
     */
    public int getModuleSetCodeNum(int classificationSerial, int opreatingSerial) {
        int moduleSetCodeNum = MarkElementName.MARK_NULL;
        if (opreatingSerial > 0) {
            ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;
            for (int r = 0; r < tabbedPane.getComponentCount(); r++) {
                moduleSetCategoryEditPaneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(r);
                if (moduleSetCategoryEditPaneTemp.getTypeSerialNumber() == classificationSerial) {
                    moduleSetCodeNum = moduleSetCategoryEditPaneTemp.getModuleSetCodeNum(opreatingSerial);
                    break;
                }
            }
        }
        return moduleSetCodeNum;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        ModuleSetCategoryEditPane moduleSetCategoryEditPaneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            moduleSetCategoryEditPaneTemp = (ModuleSetCategoryEditPane) tabbedPane.getComponent(i);
            list.addAll(moduleSetCategoryEditPaneTemp.getAllEditContainerModel());
        }
        return list;
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
