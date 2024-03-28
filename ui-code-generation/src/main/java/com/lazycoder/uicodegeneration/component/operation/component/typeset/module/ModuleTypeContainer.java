package com.lazycoder.uicodegeneration.component.operation.component.typeset.module;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.OperatingPaneBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractCommandOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.ModuleSetOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.proj.stostr.operation.containerput.ModuleTypeContainerModel;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleTypeContainer extends Folder implements OperatingPaneBusinessTraverse {//} implements Runnable {

    public final static Double THE_PROPORTION = 0.32;
    /**
     *
     */
    private static final long serialVersionUID = 7683312237156711166L;

    private static ImageIcon moreIcon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "更多.png");

    private static final int HIDDEN_HEIGHT = 30, MODULE_TYPE_JSP_HEIGHT = 350, MODULE_SET_BUTTON_WIDTH = 80;

//    private Thread t = null;

    private MdoduleTypeHiddenButton moduleTypeHiddenButton;

    private JScrollPane moduleTypeCodeControlsJsp;

    private ModuleTypeCodeControlPane moduleTypeCodeControlPane;

    private MyButton moduleTypeButton;

    private JPopupMenu menu;

    @Getter
    private String moduleTypeName = "";

    /**
     * 模块分类参数
     */
    private ModuleTypeOperatingContainerParam moduleTypeContainerParam;

    /**
     * 新建
     *
     * @param moduleTypeContainerParam
     * @param expanded
     */
    public ModuleTypeContainer(ModuleTypeOperatingContainerParam moduleTypeContainerParam, boolean expanded) {
        // TODO Auto-generated constructor stub
        super();
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        init(true);
        autoGenerateOpratingContainers();
    }

    /**
     * 还原
     *
     * @param moduleTypeModel
     * @param moduleTypeContainerParam
     * @param expanded
     */
    public ModuleTypeContainer(ModuleTypeContainerModel moduleTypeModel,
                               ModuleTypeOperatingContainerParam moduleTypeContainerParam, boolean expanded) {
        // TODO Auto-generated constructor stub
        super();
        this.moduleTypeContainerParam = moduleTypeContainerParam;
        init(true);
        moduleTypeCodeControlPane.restoreContent(moduleTypeModel);
    }

    private void init(boolean expanded) {
        int width = (int) (THE_PROPORTION * SysUtil.SCREEN_SIZE.getWidth());
        setLayout(new ModuleTypeLayout());

        menu = new JPopupMenu();

        moduleTypeName = this.moduleTypeContainerParam.getModuleSetType();
        moduleTypeHiddenButton = new MdoduleTypeHiddenButton(this.moduleTypeContainerParam.getModuleSetType(),
                expanded) {
            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                moduleTypeCodeControlPane.collapseThis();
                super.doSomethingWhenMousePressed(expanded);
            }
            //            @Override
//            public void doClick() {
//                moduleTypeCodeControlPane.collapseThis();
//                super.doClick();
//            }
        };
        moduleTypeHiddenButton.setSize(width, HIDDEN_HEIGHT);
        setHiddenButton(moduleTypeHiddenButton);
        add(moduleTypeHiddenButton);

        moduleTypeButton = new MyButton(moreIcon);
        moduleTypeButton.setSize(MODULE_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);
        moduleTypeButton.addMouseListener(mouseAdapter);
        add(moduleTypeButton);

        moduleTypeCodeControlPane = new ModuleTypeCodeControlPane(this.moduleTypeContainerParam);
        moduleTypeCodeControlsJsp = moduleTypeCodeControlPane.getParentScrollPane();
        moduleTypeCodeControlsJsp.setSize(width, MODULE_TYPE_JSP_HEIGHT);

        Drawer drawer = new Drawer(expanded ? 1 : 0, moduleTypeCodeControlsJsp);
        this.setDrawer(drawer);
        add(drawer);
    }

    public ArrayList<OpratingContainerInterface> getAllOpratingContainerList() {
        // TODO Auto-generated method stub
        return moduleTypeCodeControlPane.getAllOpratingContainer();
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            updateMenuItems();
            if (moduleTypeButton.isVisible() == true) {
                menu.show(moduleTypeButton, e.getX(), e.getY());//这里有可能报错，原因是有时没有显示屏幕出来，有运行了这里，要看看有什么办法能去掉线程让他显示
            }
            super.mouseEntered(e);
        }
    };

    /**
     * 从各个方法那里的组件进入，一个个删除里面的模块
     *
     * @param moduleId
     */
    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        moduleTypeCodeControlPane.delModuleOpratingContainerFromComponent(moduleId);
    }

    @Override
    public void collapseThis() {
        moduleTypeCodeControlPane.collapseThis();
    }

    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (MarkElementName.SET_MARK.equals(currentOpratingContainer.getPaneType())) {
            AbstractCommandOpratingContainer firstCommandOpratingContainer = currentOpratingContainer.getFirstCommandOpratingContainer();
            if (firstCommandOpratingContainer != null && (firstCommandOpratingContainer instanceof ModuleSetOpratingContainer)) {
                ModuleSetOpratingContainer moduleSetOpratingContainer = (ModuleSetOpratingContainer) firstCommandOpratingContainer;
//                if (moduleSetOpratingContainer.getModuleName().equals(moduleTypeContainerParam.getModuleName()) &&
//                        moduleSetOpratingContainer.getClassName().equals(moduleTypeContainerParam.getClassName())) {
                if (moduleSetOpratingContainer.getModuleId().equals(moduleTypeContainerParam.getModule().getModuleId())) {
                    if (moduleTypeName.equals(moduleSetOpratingContainer.getModuleTypeName())) {
                        expandHiddenPanel();
                        moduleTypeCodeControlPane.autoCollapse(currentOpratingContainer);
                    } else {
                        packUpHiddenPanel();
                    }
                }
            }
        }
    }

    @Override
    public void delThis() {
        moduleTypeCodeControlPane.delThis();
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        return null;
    }

    /**
     * 获取模块类型模型
     *
     * @return
     */
    public ModuleTypeContainerModel getFormatStructureModel() {
        return moduleTypeCodeControlPane.getFormatStructureModel();
    }

    private void updateMenuItems() {
        menu.removeAll();
        List<ModuleSetFeatureSelectionModel> list = SysService.MODULE_SET_SERVICE.getFeatureList(
                moduleTypeContainerParam.getModule().getModuleId(),
                moduleTypeContainerParam.getModuleSetType());
        if (list != null) {
            moduleTypeMenuItem tempItem;
            for (ModuleSetFeatureSelectionModel tempModel : list) {
                tempItem = new moduleTypeMenuItem(tempModel);
                menu.add(tempItem);

                //如果该菜单是只能添加一次的和自动生成只能添加一次的，查看当前添加的功能里面有没有和添加这个菜单对应的功能，有的话，将该菜单失能
                if (tempModel.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue() ||
                        tempModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {
                    ModuleSetOpratingContainer theOpratingContainer;
                    ArrayList<OpratingContainerInterface> opratingContainerList = moduleTypeCodeControlPane.getAllOpratingContainerListInThisPane();
                    for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                        if (opratingContainer instanceof ModuleSetOpratingContainer) {
                            theOpratingContainer = (ModuleSetOpratingContainer) opratingContainer;
                            if (tempModel.getOrdinal() == theOpratingContainer.getOrdinalInUserDB()) {
                                tempItem.setEnabled(false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

//    @Override
//    public void run() {
//        try {
//            Thread.sleep(600);
//            reExpandPanel();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    private void reExpandPanel() {
//        SetButton setButton = moduleTypeContainerParam.getSetButton();
//        if (setButton != null) {
////            if (setButton.isSelected() == true) {
//            setButton.packUpPanel();
//            setButton.expandPanel();
////            }
//        }
//    }

    /**
     * 生成事先设置的需要生成的方法
     */
    private void autoGenerateOpratingContainers() {
        List<ModuleSetFeatureSelectionModel> list = SysService.MODULE_SET_SERVICE.getFeatureList(
                moduleTypeContainerParam.getModule().getModuleId(),
                moduleTypeContainerParam.getModuleSetType());
        if (list != null) {
            ModuleSetOpratingContainer opratingContainer;
            for (ModuleSetFeatureSelectionModel selectionModel : list) {
                if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnce.getSysDictionaryValue() ||
                        selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {

                    opratingContainer = moduleTypeCodeControlPane.addOpratingPane(selectionModel, true);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + moduleTypeContainerParam.getModule().getModuleName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加\"" + moduleTypeContainerParam.getModule().getModuleName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                } else if (selectionModel.getSetProperty() == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                    opratingContainer = moduleTypeCodeControlPane.addOpratingPane(selectionModel, false);
                    if (opratingContainer == null) {
                        String text = "无法添加\"" + moduleTypeContainerParam.getModule().getModuleName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应位置！	(✪ω✪)";
                        String logtext = getClass() + "（添加功能异常）" +
                                "无法添加\"" + moduleTypeContainerParam.getModule().getModuleName() + "\"模块的\"" + selectionModel.getShowText() + "\"("
                                + selectionModel.getTypeSerialNumber() + ")(" + selectionModel.getOrdinal() + ")功能，找不到该功能的对应标记！";
                        CodeGenerationFrameHolder.errorLogging(text, logtext);
                    }
                }
            }
        }
    }

    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        moduleTypeCodeControlPane.functionNameSynchronousChange(functionNameId);
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {
        moduleTypeCodeControlPane.functionNameSynchronousDelete(functionNameId);
    }

    @Override
    public void variableSynchronousChange(int variableId) {
        moduleTypeCodeControlPane.functionNameSynchronousDelete(variableId);
    }

    @Override
    public void variableSynchronousDelete(int variableId) {
        moduleTypeCodeControlPane.functionNameSynchronousDelete(variableId);
    }

    /**
     * 负责布局面板组件
     */
    class ModuleTypeLayout implements LayoutManager {

        /**
         * x坐标
         */
        private int xCoordinates = 0;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getPreferredSize();
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getMinimumSize();
        }

        @Override
        public void layoutContainer(Container parent) {

            int w = parent.getWidth(), h = parent.getHeight(), dHeight = HIDDEN_HEIGHT;

            if (getHiddenButton() != null) {
                getHiddenButton().setBounds(xCoordinates, 0, w - MODULE_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);
            }
            moduleTypeButton.setBounds(w - MODULE_SET_BUTTON_WIDTH, 0, MODULE_SET_BUTTON_WIDTH, HIDDEN_HEIGHT);
            // 抽屉只显示抽出的比例
            getDrawer().setBounds(xCoordinates, dHeight, w, h - dHeight);
        }
    }

    class moduleTypeMenuItem extends JMenuItem {// implements Runnable {

        /**
         *
         */
        private static final long serialVersionUID = -490331634707256399L;

        private ModuleSetFeatureSelectionModel featureSelectionModel;

//		private Thread t = null;

        public moduleTypeMenuItem(ModuleSetFeatureSelectionModel featureSelectionModel) {
            // TODO Auto-generated constructor stub
            super(featureSelectionModel.getShowText());
            this.featureSelectionModel = featureSelectionModel;
            addActionListener(listener);

            if (featureSelectionModel != null && ("[]".equals(featureSelectionModel.getNoteListParam()) == false)) {
                HTMLText noteTip = CodeGenerationFrameHolder.getNoteToolTip(featureSelectionModel.getNoteListParam());
                if (noteTip != null) {
                    setToolTipText(noteTip.getHtmlContent());
                }
            }

        }

        private ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (isEnabled() == true) {
                    CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

                    ModuleSetOpratingContainer moduleSetOpratingContainer = moduleTypeCodeControlPane.addOpratingPane(featureSelectionModel, true);
                    if (moduleSetOpratingContainer == null) {
                        LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        //该功能的组件自动选择
                        //moduleSetOpratingContainer.setNoUserSelectionIsRequiredValue();

                        //所有功能自动选择
                        ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                        if (opratingContainerList != null) {
                            for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                                opratingContainer.setNoUserSelectionIsRequiredValue();
                            }
                        }

                        if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                            if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                                if (CodeGenerationFrameHolder.currentFormatControlPane != null) {
                                    CodeGenerationFrameHolder.currentFormatControlPane.autoCollapse(moduleSetOpratingContainer);

//                                t = new Thread(ModuleTypeContainer.this);
//                                t.start();//自动收起面板以后，重新展开面板，以让打开初始化设置的面板变成收缩后合适的大小
                                }
                            }
                        }
                        CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
                    }
                }
            }
        };


    }

}
