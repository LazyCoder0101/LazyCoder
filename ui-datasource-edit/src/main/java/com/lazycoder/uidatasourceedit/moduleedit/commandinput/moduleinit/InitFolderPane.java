package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.control.ModuleControlFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.functionname.ModuleFunctionNameFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode.ImportCodeFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.init.InitCodeFolder;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.variable.ModuleVariableFolder;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.folder.FolderPane;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.SwingUtilities;


public class InitFolderPane extends FolderPane implements CheckInterface {

    /**
     * 默认宽度比例
     */
    public final static double WIDTH_PROPORTION = 0.29;
    /**
     *
     */
    private static final long serialVersionUID = -4698025365957549439L;
    private static final int BUTTON_X = 10, PADDING = 10;
    private MyContainerPane importCodePane, initCodeButtonPane, moduleControlPane, moduleVariablePane,
            moduleFunctionNamePane;
    /**
     * 引入代码抽屉组件
     */
    private ImportCodeFolder importCodeFolder;
    /**
     * 初始化代码抽屉组件
     */
    private InitCodeFolder initCodeFolder;

    private MyButton addInitCodeButton, delInitCodeButton;

    private OperatingTipButton importTips, initTips, moduleControlTips, moduleVariableTips, moduleFunctionTips;
    /**
     * 模块控制抽屉组件
     */
    private ModuleControlFolder moduleControlFolder;
    /**
     * 模块变量抽屉组件
     */
    private ModuleVariableFolder moduleVariableFolder;
    private ModuleFunctionNameFolder moduleFunctionNameFolder;
    private MyButton addImportButton, delImporButton, addModuleVariableButtion, delModuleVariableButton,
            reductionModuleControlButton, clearModuleControlButton, addModuleFunctionNameButtion,
            delModuleFunctionNameButton, restoreButton;

    public InitFolderPane() {
        super(40);

        // TODO Auto-generated constructor stub
        importCodeFolderInit();
        initCodeFolderInit();
        moduleControlInit();
        moduleVariableFolderInit();
        moduleFunctionNameFolderInit();

        registerInitPaneHiddenButtons();

    }

    private void importCodeFolderInit() {
        importCodePane = new MyContainerPane();
        addImportButton = new MyButton("添加");
        addImportButton.addActionListener(importCodeListener);
        addImportButton.setBounds(BUTTON_X, 0, 80, 30);
        importCodePane.add(addImportButton);
        delImporButton = new MyButton("删除");
        delImporButton.addActionListener(importCodeListener);
        delImporButton.setBounds(BUTTON_X + addImportButton.getWidth() + PADDING, 0, 80, 30);
        importCodePane.add(delImporButton);
        importTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置" + File.separator + "初始化编辑"  + File.separator + "引入代码")
                .getAbsolutePath());
        importTips.setBounds(BUTTON_X + addImportButton.getWidth() + delImporButton.getWidth() + PADDING + 10, 0, 30,
                30);
        importCodePane.add(importTips);
        this.add(importCodePane);
        tabs.add(importCodePane);

        importCodeFolder = new ImportCodeFolder(true);
        addContainer(importCodeFolder);
        ModuleEditPaneHolder.importCodeFolder = importCodeFolder;
    }

    private void initCodeFolderInit() {
        initCodeButtonPane = new MyContainerPane();
        addInitCodeButton = new MyButton("添加");
        addInitCodeButton.addActionListener(initCodeListener);
        addInitCodeButton.setBounds(BUTTON_X, 0, 80, 30);

        initCodeButtonPane.add(addInitCodeButton);
        delInitCodeButton = new MyButton("删除");
        delInitCodeButton.addActionListener(initCodeListener);
        delInitCodeButton.setBounds(BUTTON_X + addInitCodeButton.getWidth() + PADDING, 0, 80, 30);
        initCodeButtonPane.add(delInitCodeButton);

        restoreButton = new MyButton("还原");
        initCodeButtonPane.add(restoreButton);
        restoreButton.addActionListener(initCodeListener);
        restoreButton.setBounds(
                BUTTON_X + addInitCodeButton.getWidth() + PADDING + delInitCodeButton.getWidth() + PADDING, 0, 80, 30);
        initCodeButtonPane.add(restoreButton);

        initTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置" + File.separator + "初始化编辑"  + File.separator + "初始化代码")
                .getAbsolutePath());
//        initTips.setToolTipText("当本模块的代码需要写到红色的代码面板时，不会写入引入代码，其他代码面板都会写入引入代码");
        initTips.setBounds(restoreButton.getLocation().x + restoreButton.getWidth() + PADDING, 0, 30,
                30);
        initCodeButtonPane.add(initTips);

        this.add(initCodeButtonPane);
        tabs.add(initCodeButtonPane);

        initCodeFolder = new InitCodeFolder(true);
        addContainer(initCodeFolder);
        ModuleEditPaneHolder.initCodeFolder = initCodeFolder;
    }

    private void moduleControlInit() {
        moduleControlPane = new MyContainerPane();
        reductionModuleControlButton = new MyButton("还原");
        reductionModuleControlButton.addActionListener(moduleControlListener);
        reductionModuleControlButton.setBounds(BUTTON_X, 0, 80, 30);
        moduleControlPane.add(reductionModuleControlButton);

        clearModuleControlButton = new MyButton("清空");
        clearModuleControlButton.addActionListener(moduleControlListener);
        clearModuleControlButton.setBounds(BUTTON_X + reductionModuleControlButton.getWidth() + PADDING, 0, 80, 30);
        moduleControlPane.add(clearModuleControlButton);

        moduleControlTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "模块设置" + File.separator + "初始化编辑"  + File.separator + "模块控制")
                .getAbsolutePath());
//        moduleControlTips.setToolTipText("当本模块的代码需要写到红色的代码面板时，不会写入引入代码，其他代码面板都会写入引入代码");
        moduleControlTips.setBounds(clearModuleControlButton.getLocation().x + clearModuleControlButton.getWidth() + PADDING, 0, 30,
                30);
        moduleControlPane.add(moduleControlTips);

        this.add(moduleControlPane);
        tabs.add(moduleControlPane);

        moduleControlFolder = new ModuleControlFolder(true);
        addContainer(moduleControlFolder);
        ModuleEditPaneHolder.moduleControlFolder = moduleControlFolder;
    }

    public ArrayList<AbstractEditContainerModel> getAllEditContainerModel() {
        ArrayList<AbstractEditContainerModel> list = new ArrayList<AbstractEditContainerModel>();
        list.add(ModuleEditPaneHolder.moduleFormatModel);
        list.addAll(initCodeFolder.getAllEditContainerModel());
        return list;
    }

    private ActionListener importCodeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addImportButton) {
                importCodeFolder.addImportCode();

            } else if (e.getSource() == delImporButton) {
                importCodeFolder.delImportCode();
            }
//            updateUI();
//            repaint();
//            importCodeFolder.updateUI();
//            importCodeFolder.repaint();
            Window window = SwingUtilities.getWindowAncestor(InitFolderPane.this);
            if (window != null) {
                window.validate();
            }
        }
    };

    private ActionListener initCodeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addInitCodeButton) {
                initCodeFolder.addInitFunction();
            } else if (e.getSource() == delInitCodeButton) {
                initCodeFolder.delInitFunction();
            } else if (e.getSource() == restoreButton) {
                initCodeFolder.restore();
            }
            SysUtil.updateFrameUI(importCodeFolder);
        }
    };

    private ActionListener moduleControlListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == reductionModuleControlButton) {
                restore();
            } else if (e.getSource() == clearModuleControlButton) {
                boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
                if (flag == true) {
                    LazyCoderFormatControl.clear(ModuleEditPaneHolder.moduleFormatModel);
                    ModuleEditPaneHolder.moduleFilePutCodePane.clearAllModuleFileFormat();
                }
            }
            SysUtil.updateFrameUI(moduleControlFolder);
        }
    };

    private ActionListener moduleVariableListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addModuleVariableButtion) {
                moduleVariableFolder.addModuleVariable();
            } else if (e.getSource() == delModuleVariableButton) {
                moduleVariableFolder.delModuleVariavle();
            }
            SysUtil.updateFrameUI(moduleVariableFolder);
        }
    };

    private ActionListener moduleFunctionNameListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addModuleFunctionNameButtion) {
                moduleFunctionNameFolder.addFunctionName();
            } else if (e.getSource() == delModuleFunctionNameButton) {
                moduleFunctionNameFolder.delFunctionName();
            }
            SysUtil.updateFrameUI(moduleFunctionNameFolder);
        }
    };

    private void restore() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            ModuleInfo moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(DataSourceEditHolder.currentModule.getModuleId());
            Module module = SysService.MODULE_SERVICE.getModuleById(DataSourceEditHolder.currentModule.getModuleId());
            if (moduleInfo != null && module != null) {
//                if (moduleInfo.getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_) {// 此前添加过内容，显示之前编辑过的内容
                ModuleEditPaneHolder.moduleFilePutCodePane.clearModuleContents();
                moduleControlFolder.clearModuleContents();
                ModuleEditPaneHolder.moduleFormatModel = new FormatModel();// 重新初始化模块格式模型
                ModuleEditPaneHolder.moduleFilePutCodePane.displayModuleContents(moduleInfo, module);
                moduleControlFolder.displayModuleContents(moduleInfo, module);
//                } else {
//                    LazyCoderOptionPane.showMessageDialog(null, "ヽ(ー_ー)ノ  我查了一下，这里之前没写有什么内容，没什么可以还原的。");
//                }
            }
        }
    }

    private void moduleVariableFolderInit() {
        moduleVariablePane = new MyContainerPane();
        addModuleVariableButtion = new MyButton("添加");
        addModuleVariableButtion.setBounds(BUTTON_X, 0, 80, 30);
        addModuleVariableButtion.addActionListener(moduleVariableListener);
        moduleVariablePane.add(addModuleVariableButtion);

        delModuleVariableButton = new MyButton("删除");
        delModuleVariableButton.setBounds(BUTTON_X + addModuleVariableButtion.getWidth() + PADDING, 0, 80, 30);
        delModuleVariableButton.addActionListener(moduleVariableListener);
        moduleVariablePane.add(delModuleVariableButton);

        this.add(moduleVariablePane);
        tabs.add(moduleVariablePane);

        moduleVariableFolder = new ModuleVariableFolder(true);
        addContainer(moduleVariableFolder);
        ModuleEditPaneHolder.moduleVariableFolder = moduleVariableFolder;

        moduleVariableTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有变量")
                .getAbsolutePath());
//setToolTipText("当本模块的代码需要写到红色的代码面板时，不会写入引入代码，其他代码面板都会写入引入代码");
        moduleVariableTips.setBounds(delModuleVariableButton.getLocation().x + delModuleVariableButton.getWidth() + PADDING, 0, 30,
                30);
        moduleVariablePane.add(moduleVariableTips);
    }

    private void moduleFunctionNameFolderInit() {
        moduleFunctionNamePane = new MyContainerPane();
        addModuleFunctionNameButtion = new MyButton("添加");
        addModuleFunctionNameButtion.setBounds(BUTTON_X, 0, 80, 30);
        addModuleFunctionNameButtion.addActionListener(moduleFunctionNameListener);
        moduleFunctionNamePane.add(addModuleFunctionNameButtion);

        delModuleFunctionNameButton = new MyButton("删除");
        delModuleFunctionNameButton.setBounds(BUTTON_X + addModuleVariableButtion.getWidth() + PADDING, 0, 80, 30);
        delModuleFunctionNameButton.addActionListener(moduleFunctionNameListener);
        moduleFunctionNamePane.add(delModuleFunctionNameButton);

        moduleFunctionTips = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "原有方法")
                .getAbsolutePath());
//        moduleFunctionTips.setToolTipText("当本模块的代码需要写到红色的代码面板时，不会写入引入代码，其他代码面板都会写入引入代码");
        moduleFunctionTips.setBounds(delModuleFunctionNameButton.getLocation().x + delModuleFunctionNameButton.getWidth() + PADDING, 0, 30,
                30);
        moduleFunctionNamePane.add(moduleFunctionTips);

        this.add(moduleFunctionNamePane);
        tabs.add(moduleFunctionNamePane);

        moduleFunctionNameFolder = new ModuleFunctionNameFolder(true);
        ModuleEditPaneHolder.moduleFunctionNameFolder = moduleFunctionNameFolder;
        addContainer(moduleFunctionNameFolder);
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        if (importCodeFolder.check() == true) {
            if (initCodeFolder.check() == true) {
                if (moduleVariableFolder.check() == true) {
                    if (moduleFunctionNameFolder.check() == false) {
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

    /**
     * 把初始化面板的隐藏按钮挂起供其他组件调用
     */
    private void registerInitPaneHiddenButtons() {
        ModuleEditPaneHolder.initPaneHiddenButtonList.add(importCodeFolder.getHiddenButton());
        ModuleEditPaneHolder.initPaneHiddenButtonList.add(initCodeFolder.getHiddenButton());
        ModuleEditPaneHolder.initPaneHiddenButtonList.add(moduleControlFolder.getHiddenButton());
        ModuleEditPaneHolder.initPaneHiddenButtonList.add(moduleVariableFolder.getHiddenButton());
        ModuleEditPaneHolder.initPaneHiddenButtonList.add(moduleFunctionNameFolder.getHiddenButton());
    }

}
