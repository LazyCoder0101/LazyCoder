package com.lazycoder.uidatasourceedit.moduleedit;

import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleControl;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.save.InitInputData;
import com.lazycoder.service.vo.save.ModuleFunctionInputData;
import com.lazycoder.service.vo.save.ModuleSetInputData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.moduleedit.attachedfile.ModuleAttachedFileSettingFrame;
import com.lazycoder.uidatasourceedit.moduleedit.codepane.ModuleCodeEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.UseSettingMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.moduleselect.CurrentModuleSelectMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.relatedmoduleinfomenu.RelatedModuleInfoMenu;
import com.lazycoder.uidatasourceedit.moduleedit.toolbar.usingrange.UsingRangeCombobox;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ModuleEditPane extends JPanel implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 8789035997764920549L;

    private ModuleCodeEditPane moduleEditSplitPane;

    private MyButton new_bt, open_bt, save_bt, set_bt;

    private CurrentModuleSelectMenu currentModuleSelectMenu = null;

//    private ModuleEditPaneClassCombobox classificationCombobox;
//
//    private ModuleCombobox moduleCombobox;

    private RelatedModuleInfoMenu relatedModuleInfoMenu;

    private UseSettingMenu useSettingMenu;

    private OperatingTipButton moduleEditOperatingTipButton;

//	private NoUseModuleMenu noUseModuleMenu;

    private UsingRangeCombobox usingRange;

    public ModuleEditPane() {
        setLayout(new BorderLayout());
        toolBarInit();
        moduleEditInit();
    }

    private void toolBarInit() {
        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        moduleEditOperatingTipButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置")
                .getAbsolutePath());
        toolBar.add(moduleEditOperatingTipButton);

        JLabel l1 = new JLabel("当前模块：");
        toolBar.add(l1);
        currentModuleSelectMenu = new CurrentModuleSelectMenu();
        ModuleEditPaneHolder.currentModuleSelectMenu = currentModuleSelectMenu;
        toolBar.add(currentModuleSelectMenu);
//        classificationCombobox = new ModuleEditPaneClassCombobox();
//        ModuleEditPaneHolder.classificationCombobox = classificationCombobox;
//        toolBar.add(classificationCombobox);
//
//        JLabel l2 = new JLabel("模块：");
//        toolBar.add(l2);
//        moduleCombobox = new ModuleCombobox();
//        ModuleEditPaneHolder.moduleCombobox = moduleCombobox;
//        toolBar.add(moduleCombobox);

        new_bt = new MyButton("新建");
        new_bt.addActionListener(toolBarListener);
        toolBar.add(new_bt);

        open_bt = new MyButton("导入");
        open_bt.addActionListener(toolBarListener);
        toolBar.add(open_bt);

        save_bt = new MyButton("保存");
        save_bt.addActionListener(toolBarListener);
        toolBar.add(save_bt);

        set_bt = new MyButton("设置");
        set_bt.addActionListener(toolBarListener);
        toolBar.add(set_bt);

        Component horizontalStrut1 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut1);

        JLabel l3 = new JLabel("可使用范围：");
        toolBar.add(l3);
        usingRange = new UsingRangeCombobox<>();
        ModuleEditPaneHolder.usingRange = usingRange;
        toolBar.add(usingRange);

        Component horizontalStrut2 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut2);

        JLabel l4 = new JLabel("调用相关模块设置：");
        toolBar.add(l4);
        relatedModuleInfoMenu = new RelatedModuleInfoMenu();
        ModuleEditPaneHolder.relatedModuleInfoMenu = relatedModuleInfoMenu;
        toolBar.add(relatedModuleInfoMenu);

        Component horizontalStrut3 = Box.createHorizontalStrut(20);
        toolBar.add(horizontalStrut3);

        //选择需要使用的模块时，系统会自动再选择需要使用的模块中，还要使用的模块，会存在和不可调用模块冲突的情况，暂时屏蔽该功能
//		JLabel l5 = new JLabel("不可调用模块：");
//		toolBar.add(l5);
//		noUseModuleMenu = new NoUseModuleMenu();
//		ModuleEditPaneHolder.noUseModuleMenu = noUseModuleMenu;
//		toolBar.add(noUseModuleMenu);

        JLabel l5 = new JLabel("模块使用设置：");
        toolBar.add(l5);
        useSettingMenu = new UseSettingMenu();
        ModuleEditPaneHolder.useSettingMenu = useSettingMenu;
        toolBar.add(useSettingMenu);

//        radioButton = new WhetherTheChoiceRadioButton("生成程序都要使用该模块");
//        ModuleEditPaneHolder.whetherTheChoiceRadioButton = radioButton;
//        radioButton.setFocusPainted(false);
//        toolBar.add(radioButton);
    }

    private void moduleEditInit() {
        moduleEditSplitPane = new ModuleCodeEditPane(0.65);
        add(moduleEditSplitPane, BorderLayout.CENTER);
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        return moduleEditSplitPane.getAllEditContainerModel();
    }

    private void openModuleAttachedFileSettingFrame() {
        boolean flag = checkModuleStateAndErrorTip();
        if (flag == true) {
            new ModuleAttachedFileSettingFrame();
        }
    }

    private ModuleInfo getModuleInfo() {
        ModuleInfo moduleInfo = new ModuleInfo();

        moduleInfo.setClassName(DataSourceEditHolder.currentModule.getClassName());// 设置分类名
        moduleInfo.setModuleName(DataSourceEditHolder.currentModule.getModuleName());// 设置模块名

        moduleInfo.setNumOfImport(ModuleEditPaneHolder.importCodeFolder.getNumOfImportCode()); // 设置引入代码数量
        moduleInfo.setNumOfInit(ModuleEditPaneHolder.initCodeFolder.getNumOfInitCode());// 设置初始化数量
        moduleInfo.setNumOfVariable(ModuleEditPaneHolder.moduleVariableFolder.getModuleVariableNum()); // 设置模块变量的数量
        moduleInfo.setNumOfFunctionName(ModuleEditPaneHolder.moduleFunctionNameFolder.getModuleFunctionNum());
        moduleInfo.setWhetherModuleControlIsRequired(
                ModuleEditPaneHolder.moduleControlFolder.getWhetherModuleControlIsRequired()); // 设置是否写有模块控制

        ModuleEditPaneHolder.moduleFilePutCodePane.setModuleFormatFileRelatedParam(moduleInfo);// 设置模块添加文件的相关参数

        ModuleEditPaneHolder.needUseCodeFileEditPane.setAdditionalRelatedParam(moduleInfo);// 设置该模块调用其他代码文件使用到的相关参数
        ModuleEditPaneHolder.moduleSetCodeEditPane.setModuleSetRelatedParam(moduleInfo);// 填写模块设置相关参数
        ModuleEditPaneHolder.moduleFunctionEditPane.setFunctionRelatedParam(moduleInfo);

        return moduleInfo;
    }

    private Module getModule() {
        Module module = new Module();
        module.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        module.setClassName(DataSourceEditHolder.currentModule.getClassName());
        module.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
        ModuleEditPaneHolder.usingRange.setUsingRangeParam(module);
        ModuleEditPaneHolder.relatedModuleInfoMenu.setNeedModuleParam(module);
        ModuleEditPaneHolder.relatedModuleInfoMenu.setNoUseModuleParam(module);
        module.setEnabledState(Module.TRUE_);
        ModuleEditPaneHolder.useSettingMenu.setUseSettingParam(module);

        return module;
    }

    /**
     * 保存
     */
    public void save() {
        boolean flag = checkModuleState();
        if (flag == true) {
            if (check() == true) {
                ModuleInfo moduleInfo = getModuleInfo();
                Module module = getModule();
                ArrayList<ImportCode> importCodeList = ModuleEditPaneHolder.importCodeFolder.getImportCode();
                ModuleControl moduleControl = ModuleEditPaneHolder.moduleControlFolder.getModuleControl();
                ArrayList<VariableData> variableDataList = ModuleEditPaneHolder.moduleVariableFolder.getVariableDataList();
                ArrayList<FunctionNameData> functionNameDataList = ModuleEditPaneHolder.moduleFunctionNameFolder.getFunctionNameDataList();
                InitInputData initInputData = ModuleEditPaneHolder.initCodeFolder.getInitInputData();
                ModuleSetInputData moduleSetInputData = ModuleEditPaneHolder.moduleSetCodeEditPane.getModuleSetInputData();
                ModuleFunctionInputData moduleFunctionInputData = ModuleEditPaneHolder.moduleFunctionEditPane.getModuleFunctionInputData();
                ArrayList<GeneralFileFormat> moduleCodeFileList = ModuleEditPaneHolder.moduleFilePutCodePane.getModuleCodeFileData();
                ArrayList<GeneralFileFormat> mainCodeFileList = ModuleEditPaneHolder.needUseCodeFileEditPane.getMainCodeFileList();
                List<GeneralFileFormat> additionalCodeFileList = ModuleEditPaneHolder.needUseCodeFileEditPane.getAdditionalCodeFileList();
                ArrayList<GeneralFileFormat> needUpdateModuleCodeFileListDataForAdditional = ModuleEditPaneHolder.needUseCodeFileEditPane.getNeedUpdateModuleCodeFileListDataForAdditional();

                boolean saveState = SysService.INPUT_DATA_SAVE_SERVICE.saveModuleInputData(moduleInfo, module,
                        importCodeList, moduleControl,
                        variableDataList, functionNameDataList,
                        initInputData, moduleSetInputData, moduleFunctionInputData,
                        moduleCodeFileList,
                        mainCodeFileList, additionalCodeFileList, needUpdateModuleCodeFileListDataForAdditional);//真正进行保存的操作
                if (saveState == true) {//保存过后，更新组件上面对应的模块信息
                    LazyCoderOptionPane.showMessageDialog(null, "已保存\"" + DataSourceEditHolder.currentModule.getModuleName() + "\"模块的数据！", "系统信息", JOptionPane.PLAIN_MESSAGE);
                    ModuleEditPaneHolder.useModuleFunctionEditPane.delRedundantFiles();
                    if (DataSourceEditHolder.moduleManagementPane != null) {//刷新模块显示列表
                        DataSourceEditHolder.moduleManagementPane.queryModule();
                    }
                    if (ModuleEditPaneHolder.currentModuleSelectMenu != null) {
                        ModuleEditPaneHolder.currentModuleSelectMenu.updateModuleData(module);
                    }
                    if (ModuleEditPaneHolder.relatedModuleInfoMenu != null) {
                        ModuleEditPaneHolder.relatedModuleInfoMenu.updateModuleData(module);
                    }
                } else {
                    LazyCoderOptionPane.showMessageDialog(null, "(╥╯^╰╥) 不知道为什么，保存不了了，要不清理一下内存，或者重新打开软件再试试吧", "系统信息", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    private ActionListener toolBarListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == new_bt) {
                createNewBlankSourceFile();

            } else if (e.getSource() == open_bt) {
                openSourceFile();

            } else if (e.getSource() == save_bt) {
                save();

            } else if (e.getSource() == set_bt) {
                openModuleAttachedFileSettingFrame();
            }
        }
    };

    /**
     * 添加空白源文件
     */
    public void createNewBlankSourceFile() {
        boolean flag = checkModuleStateAndErrorTip();
        if (flag == true) {
            ModuleEditPaneHolder.moduleFilePutCodePane.createNewBlankSourceFile();
        }
    }

    /**
     * 打开源文件
     */
    private void openSourceFile() {
        boolean flag = checkModuleStateAndErrorTip();
        if (flag == true) {
            ModuleEditPaneHolder.moduleFilePutCodePane.openSourceFile();
        }
    }

    public boolean checkModuleState() {
        boolean flag = true;
        if (DataSourceEditHolder.currentModule == null) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检查当前模块编辑界面是否在使用，如如果没在使用，给出提示
     *
     * @return
     */
    public boolean checkModuleStateAndErrorTip() {
        boolean flag = checkModuleState();
        if (flag == false) {
            LazyCoderOptionPane.showMessageDialog(null, "(￣ェ￣;)  亲，先告诉我你要选哪个模块行吗");
        }
        return flag;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (ModuleEditPaneHolder.moduleFilePutCodePane.check() == true) {
            if (ModuleEditPaneHolder.initFolderPane.check() == true) {
                if (ModuleEditPaneHolder.moduleSetCodeEditPane.check() == true) {
                    if (ModuleEditPaneHolder.moduleFunctionEditPane.check() == false) {
                        flag = false;
                    }
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

}
