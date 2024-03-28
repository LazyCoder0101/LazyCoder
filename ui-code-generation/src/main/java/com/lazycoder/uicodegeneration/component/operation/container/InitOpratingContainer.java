package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.service.vo.meta.InitMetaModel;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.operation.component.typeset.module.SettingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.component.CodeHiddenControlButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.InitChoiceMenu;
import com.lazycoder.uicodegeneration.component.operation.container.component.ModuleSetCodeHiddenControlButton;
import com.lazycoder.uicodegeneration.component.operation.container.pane.DeafaultCommandOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.pane.HiddenCommandOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.InitOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.ModuleTypeOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.InitOpratingContainerModel;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.ui.MyScrollBarUI;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InitOpratingContainer extends AbstractCommandOpratingContainer {

    /**
     *
     */
    private static final long serialVersionUID = 6959402228841643714L;

    private static final int HIDDEN_BUTTON_HEIGHT = 30, maxHiddenPaneHight = 60,
            maxModuleSetPaneHight = (int) (0.53 * SysUtil.SCREEN_SIZE.height);

//    private SetButton setButton;

//	private InitChoiceCombobox initChoiceCombobox;

    private InitChoiceMenu initChoiceMenu;

    /**
     * 设置按钮使用状态
     */
    private boolean initContainerState = true,
            setButtonEnableState = false;

    /**
     * 初始化列表信息
     */
    private List<InitFeatureSelectonModel> initList = new ArrayList<>();

    /**
     * 当前的初始化模型
     */
    private InitMetaModel currentInitModel = null;

    private InitOperatingContainerParam initOperatingContainerParam;

    private Module module = null;

    private ModuleInfo moduleInfo = null;

    private CodeHiddenControlButton initCodeHiddenControlButton = null;

    @Getter
    private ModuleSetCodeHiddenControlButton moduleSetCodeHiddenControlButton = null;

    private SettingContainer settingContainer = null;

    /**
     * 放应用程序的抽屉
     */
    @Getter
    private Drawer moduleSetDrawer = null;

    private JScrollPane moSetScrollPane = null;

    /**
     * 新建
     *
     * @param initOperatingContainerParam
     * @param expanded
     */
    public InitOpratingContainer(InitMetaModel metaModel, InitOperatingContainerParam initOperatingContainerParam, boolean expanded) {
        super();
        this.module = initOperatingContainerParam.getModule();
        this.moduleInfo = initOperatingContainerParam.getModuleInfo();

        initOperatingContainerParam.setFirstCommandOpratingContainer(this);
        this.initOperatingContainerParam = initOperatingContainerParam;

        if (metaModel == null) {
            this.initContainerState = false;
        } else {
            this.initContainerState = true;
            this.currentInitModel = metaModel;
        }

        if (this.initContainerState == true) {//有初始化功能
            int hiddenStateFlag = currentInitModel.getOperatingModel().getHiddenState();// 获取当前选择语句的隐藏使用状态
            if (hiddenStateFlag == GeneralCommandOperatingModel.TRUE_) {
                this.hiddenState = true;

            } else if (hiddenStateFlag == GeneralCommandOperatingModel.FALSE_) {
                this.hiddenState = false;
            }
            if (initOperatingContainerParam.getModuleInfo().getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//没有模块控制相关内容，也没有设置相关内容
                setButtonEnableState = false;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//没有模块控制相关内容，但有设置相关内容
                setButtonEnableState = true;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//有模块控制相关内容，但没有设置相关内容
                setButtonEnableState = true;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//有模块控制相关内容，但没有设置相关内容
                setButtonEnableState = true;
            }
            initList = SysService.INIT_SERVICE.getFeatureList(moduleInfo.getModuleId());

        } else {//没有初始化功能
            this.hiddenState = false;
            if (initOperatingContainerParam.getModuleInfo().getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//没有模块控制相关内容，也没有设置相关内容
                setButtonEnableState = false;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//没有模块控制相关内容，但有设置相关内容
                setButtonEnableState = true;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//有模块控制相关内容，但没有设置相关内容
                setButtonEnableState = true;

            } else if (initOperatingContainerParam.getModuleInfo()
                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//有模块控制相关内容，但没有设置相关内容
                setButtonEnableState = true;
            }
        }

        init(expanded, false, initOperatingContainerParam.getCodeControlPane());

        if (this.initContainerState == true) {//有初始化功能
            if (initList.size() > 1) {
                initChoiceMenu.showSelectInit(currentInitModel);
            }
        }
//        setButton = new SetButton(initOperatingContainerParam);
//        setButton.setEnabled(setButtonEnableState);
//        add(setButton);
        createSettingContainer();

        generateCodeAndOpratingContainer();
        setAppropriateSize(PROPOTION);
    }

    /**
     * 还原
     *
     * @param model
     * @param initOperatingContainerParam
     * @param expanded
     */
    public InitOpratingContainer(InitOpratingContainerModel model,
                                 InitOperatingContainerParam initOperatingContainerParam, boolean expanded) {
        super();
        this.module = initOperatingContainerParam.getModule();
        this.moduleInfo = initOperatingContainerParam.getModuleInfo();

        this.initContainerState = model.isInitContainerState();
        this.setButtonEnableState = model.isSetButtonEnableState();
        this.initList = model.getInitList();
        this.currentInitModel = model.getCurrentInitModel();
        initOperatingContainerParam.setFirstCommandOpratingContainer(this);
        this.initOperatingContainerParam = initOperatingContainerParam;

//        setButton = new SetButton(model, this.initOperatingContainerParam);
//        setButton.setEnabled(setButtonEnableState);
//        add(setButton);

        restoreInit(model);
        init(expanded, model.isCanBeDelOrNot(), initOperatingContainerParam.getCodeControlPane());
        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
        restorePaneContent(model, commandContainerComponentParam);
        setAppropriateSize(PROPOTION);
    }


    @Override
    protected void setThisToolTipText(String functionShowText) {
        HTMLText htmlText = new HTMLText();
        if (functionShowText != null) {
            HtmlPar par1 = new HtmlPar();
            par1.addText("模块名：" + functionShowText, true);
            htmlText.addPar(par1);
        }
        HtmlPar par2 = new HtmlPar();
        par2.addText("序号：", false);
        par2.addText(codeSerialNumber + "", true);
        htmlText.addPar(par2);
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.setToolTipText(htmlText.getHtmlContent());
        }
        if (moduleSetCodeHiddenControlButton != null) {
            moduleSetCodeHiddenControlButton.setToolTipText(htmlText.getHtmlContent());
        }
    }

    @Override
    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        if (this.initContainerState == true) {
            super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
            if (initList.size() > 1) {
                initChoiceMenu.showSelectInit(currentInitModel);
            }
        }
        if (setButtonEnableState) {
            ModuleTypeOperatingContainerParam moduleTypeContainerParam = new ModuleTypeOperatingContainerParam();
            setModuleTypeOperatingContainerParam(moduleTypeContainerParam);
            settingContainer = new SettingContainer(moduleTypeContainerParam,
                    ((InitOpratingContainerModel) commandOperatingContainerModel).getSettingContainerModel());
            moSetScrollPane.setViewportView(settingContainer);
        }
        setThisToolTipText(module.getModuleName());
    }

    private void createSettingContainer(){
        if (setButtonEnableState){
            ModuleTypeOperatingContainerParam moduleTypeContainerParam = new ModuleTypeOperatingContainerParam();
            setModuleTypeOperatingContainerParam(moduleTypeContainerParam);

            settingContainer = new SettingContainer(moduleTypeContainerParam);
            moSetScrollPane.setViewportView(settingContainer);
        }
        setThisToolTipText(module.getModuleName());
    }
      /**
     * 测试用
     *
     * @param expanded
     */
    private InitOpratingContainer(boolean expanded) {
        // TODO Auto-generated constructor stub
        super();

        this.setButtonEnableState = false;
        this.hiddenState = true;

        initList = new ArrayList<>();
        InitFeatureSelectonModel selectionOption1 = new InitFeatureSelectonModel();
        selectionOption1.setOrdinal(1);
        selectionOption1.setShowText("测试1");
        initList.add(selectionOption1);

        // InitSelectionOption selectionOption2 = new InitSelectionOption();
        // selectionOption2.setOrdinal(2);
        // selectionOption2.setShowText("测试2");
        // initList.add(selectionOption2);

        init(expanded, false, null);
    }

    /**
     * 生成代码和组件
     */
    private void generateCodeAndOpratingContainer() {
        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
        pathFind = initOperatingContainerParam.getCodeControlPane().getPathFind();

        // 生成需要的代码
        if (this.initContainerState == true && currentInitModel != null) {
            setThisToolTipText(module.getModuleName());
            generateCode();

            // 生成默认代码面板需要的参数和内容
            CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                    currentInitModel.getOperatingModel().getDefaultControlStatementFormat());
            deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

            if (hiddenState == true) {
                // 生成隐藏代码面板需要的参数和内容
                CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                        currentInitModel.getOperatingModel().getHiddenControlStatementFormat());
                hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
            }
        } else {
            setThisToolTipText(module.getModuleName());
        }
    }

    private void generateCode() {
        if (this.initContainerState && pathFind != null) {
            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            CodeListLocation codeListLocation;
            ArrayList<CodeListLocation> codeListLocationArrayList;
            InitMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                    for (InitCodeModel codeModel : this.currentInitModel.getCodeModelList()) {
                        boolean inserNewLineOrNot = true;
                        if (null != codeModel.getCodeUsePropertyParam()) {
                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                            if (GeneralCommandCodeModel.contant(
                                    setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                inserNewLineOrNot = false;
                            }
                        }

                        codeListLocationArrayList = new ArrayList<>();

                        commandAddRelatedAttribute = new CommandAddRelatedAttribute();
                        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
                        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                        codeListLocation = new CodeListLocation();
                        markElement = new InitMarkElement();
                        markElement.setModuleId(currentInitModel.getOperatingModel().getModuleId());
                        markElement.setInitSerialNumber(codeModel.getOrdinal());
                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
                        codeListLocation.addCodePathFindForMark(markElement, pathFind);
                        codeListLocationArrayList.add(codeListLocation);

                        pathParam = codeModel.getPathParam();
                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.initOperatingContainerParam.getFormatControlPane());
                        if (codeShowPane != null) {
                            flag = OpratingContainerStaticMethod.generateCode(this,
                                    codeShowPane, commandAddRelatedAttribute,
                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                    inserNewLineOrNot, codeListLocationArrayList);
                            if (flag) {

                                if (null != codeModel.getCodeUsePropertyParam()) {
                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                            setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
                                    );
                                }

                                OpratingContainerStaticMethod.addCommandContainerImportCodes(codeShowPane, codeModel.getImportCodeParam());

                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 查看这里是不是可以添加这个代码的本功能容器
     *
     * @param initOperatingContainerParam
     * @param metaModel
     * @return
     */
    public static boolean checkWhetherItMatches(InitOperatingContainerParam initOperatingContainerParam,
                                                InitMetaModel metaModel) {
        boolean returnFlag = false;
        PathFind pathFind = initOperatingContainerParam.getCodeControlPane().getPathFind();
        if (pathFind != null) {
            CommandAddRelatedAttribute commandAddRelatedAttribute;
            CodeListLocation codeListLocation;
            InitMarkElement markElement;
            String pathParam;
            CodeShowPane codeShowPane;
            boolean flag;
            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                    returnFlag = true;
                    for (InitCodeModel codeModel : metaModel.getCodeModelList()) {

                        commandAddRelatedAttribute = new CommandAddRelatedAttribute();
                        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
                        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());

                        codeListLocation = new CodeListLocation();
                        markElement = new InitMarkElement();
                        markElement.setModuleId(metaModel.getOperatingModel().getModuleId());
                        markElement.setInitSerialNumber(codeModel.getOrdinal());
                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
                        codeListLocation.addCodePathFindForMark(markElement, pathFind);

                        pathParam = codeModel.getPathParam();
                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, initOperatingContainerParam.getFormatControlPane());
                        if (codeShowPane != null) {
                            flag = OpratingContainerStaticMethod.checkGenerateCode(
                                    pathFind, codeShowPane, commandAddRelatedAttribute,
                                    codeListLocation);
                            if (!flag) {
                                returnFlag = false;//只要有一句代码无法插入，都要标记为false
                                break;
                            }
                        }
                    }
                }
            }
        }
        return returnFlag;
    }

    /**
     * 设置组件参数
     *
     * @param controlStatementFormat
     * @return
     */
    @Override
    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {
        CommandContainerComponentParam codeGenerationalOpratingContainerParam = super.getContainerComponentParam(controlStatementFormat);
        codeGenerationalOpratingContainerParam.setFormatControlPane(initOperatingContainerParam.getFormatControlPane());
        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);
        codeGenerationalOpratingContainerParam.setCodeControlPane(initOperatingContainerParam.getCodeControlPane());
        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
        codeGenerationalOpratingContainerParam.setModule(initOperatingContainerParam.getModule());
        codeGenerationalOpratingContainerParam.setModuleInfo(initOperatingContainerParam.getModuleInfo());
        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);
        codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
        return codeGenerationalOpratingContainerParam;
    }

    @Override
    protected void setAppropriateSize(double maxPropotion) {
        // TODO Auto-generated method stub
        int width = (int) (maxPropotion * SysUtil.SCREEN_SIZE.getWidth());
        if (this.initContainerState) {
            Dimension dd1 = new Dimension(width, DEFAULT_SHOW_MIN_HEIGHT);
            deafaultOperatingJSP.setPreferredSize(dd1);
            deafaultOperatingJSP.setMaximumSize(dd1);
            deafaultOperatingJSP.setMinimumSize(dd1);
            int hiddenHeght = hiddenOperatingPane.getPreferredSize().height;
            if (hiddenHeght > maxHiddenPaneHight) {
                hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
            } else {
                hiddenOperatingJSP.setSize(width, hiddenHeght);
            }
        } else {
            int hiddenHeght = hiddenOperatingPane.getPreferredSize().height;
            if (hiddenHeght > maxHiddenPaneHight) {
                hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
            } else {
                hiddenOperatingJSP.setSize(width, hiddenHeght);
            }
        }
        if (setButtonEnableState && moSetScrollPane != null) {
            moSetScrollPane.setSize(width, maxModuleSetPaneHight);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(UIManager.getColor("Panel.background"));//设置画图的颜色
        g2d.fillRect(0, 0, getWidth(), DEFAULT_SHOW_MIN_HEIGHT);//填充一个矩形
        super.paint(g);
    }

    /**
     * 负责布局面板组件
     */
    class InitOpratingLayout implements LayoutManager {

        private boolean initContainerState = true;

        public InitOpratingLayout(boolean initContainerState) {
            this.initContainerState = initContainerState;
        }

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
            int w = parent.getWidth(),
                    h = parent.getHeight(),
                    moduleSetCodeHiddenControlButton_y = 0;

//            int dynamicInitDrawerHight = h -
//                    (dtemp + HIDDEN_BUTTON_HEIGHT + HIDDEN_BUTTON_HEIGHT + maxModuleSetPaneHight),
//                    dynamicModuleSetDrawerHight = h -
//                            (dtemp + HIDDEN_BUTTON_HEIGHT + HIDDEN_BUTTON_HEIGHT + maxHiddenPaneHight),
//            int stateInitDrawerHight = initContainerState == true && getHiddenButton().isExpanded() ? maxHiddenPaneHight : 0,
//              int moduleSetCodeHiddenControlButton_y = 0;
//                    stateModuleSetDrawerHight = setButtonEnableState == true && moduleSetCodeHiddenControlButton.isExpanded() ?
//                            maxModuleSetPaneHight : 0,


            if (this.initContainerState) {
                int moduleSetPaneHightTemp = setButtonEnableState && moduleSetDrawer != null ? moduleSetDrawer.getContentHeight() : 0;
                moduleSetPaneHightTemp = moduleSetCodeHiddenControlButton != null && moduleSetCodeHiddenControlButton.isExpanded() ? moduleSetPaneHightTemp : 0;

                int dHeight = deafaultOperatingJSP.getHeight() + HIDDEN_BUTTON_HEIGHT;
                deafaultOperatingJSP.setBounds(0, 0,
                        w, DEFAULT_SHOW_MIN_HEIGHT);
                moduleSetCodeHiddenControlButton_y = moduleSetCodeHiddenControlButton_y + deafaultOperatingJSP.getHeight();

                if (getHiddenButton() != null) {
                    getHiddenButton().setBounds(0, deafaultOperatingJSP.getHeight(), getHiddenButton().getWidth(), HIDDEN_BUTTON_HEIGHT);
                    moduleSetCodeHiddenControlButton_y = moduleSetCodeHiddenControlButton_y + HIDDEN_BUTTON_HEIGHT;
                }

                int dynamicInitDrawerHight = h -
                        (deafaultOperatingJSP.getHeight() + HIDDEN_BUTTON_HEIGHT);
                if (setButtonEnableState) {//有模块设置
                    dynamicInitDrawerHight = dynamicInitDrawerHight - HIDDEN_BUTTON_HEIGHT - moduleSetPaneHightTemp;
                }
                if (initList.size() == 1) {// 只有一个初始化方法
                    // 抽屉只显示抽出的比例
                    getDrawer().setBounds(0, dHeight, w, dynamicInitDrawerHight);
                    moduleSetCodeHiddenControlButton_y = moduleSetCodeHiddenControlButton_y + dynamicInitDrawerHight;

                } else if (initList.size() > 1) {// 有多个初始化方法
                    initChoiceMenu.setBounds(getHiddenButton().getWidth(), deafaultOperatingJSP.getHeight(),
                            w - getHiddenButton().getWidth(), HIDDEN_BUTTON_HEIGHT);
                    // 抽屉只显示抽出的比例
                    getDrawer().setBounds(0, dHeight, w, dynamicInitDrawerHight);
                    moduleSetCodeHiddenControlButton_y = moduleSetCodeHiddenControlButton_y + dynamicInitDrawerHight;
                }
            }
            if (setButtonEnableState) {
                int initPaneHightTemp = this.initContainerState && hiddenState == true && getDrawer() != null ? getDrawer().getContentHeight() : 0;
                initPaneHightTemp = getHiddenButton() != null && getHiddenButton().isExpanded() ? initPaneHightTemp : 0;
                int deafaultOperatingJSPHeight = deafaultOperatingJSP == null ? 0 : deafaultOperatingJSP.getHeight();
                int dynamicModuleSetDrawerHight = h -
                        (deafaultOperatingJSPHeight + HIDDEN_BUTTON_HEIGHT + HIDDEN_BUTTON_HEIGHT);
                if (hiddenState) {//初始化有隐藏面板。要减去隐藏面板的高
                    dynamicModuleSetDrawerHight = dynamicModuleSetDrawerHight - initPaneHightTemp;
                }

                if (moduleSetCodeHiddenControlButton != null) {
                    moduleSetCodeHiddenControlButton.setBounds(0,
                            moduleSetCodeHiddenControlButton_y,
                            w, HIDDEN_BUTTON_HEIGHT);
                }
                moduleSetDrawer.setBounds(0,
                        moduleSetCodeHiddenControlButton_y + HIDDEN_BUTTON_HEIGHT,
                        w, dynamicModuleSetDrawerHight);
            }
        }
    }

    public void updateInitCode(InitFeatureSelectonModel featureSelectonModel) {
        if (this.initContainerState == true && featureSelectonModel.getOrdinal() != currentInitModel.getOperatingModel().getOrdinal()) {
            ArrayList<CodeListLocation> codeListLocationArrayList;

            InitMetaModel newMetaModel = SysService.INIT_SERVICE.getModuleSetMetaModel(featureSelectonModel);
            if (newMetaModel != null) {
                boolean checkFlag = checkWhetherItMatches(this.initOperatingContainerParam, newMetaModel);
                if (checkFlag) {
                    delCode();
                    delControlComponentsAndContent();

                    currentInitModel = newMetaModel;

                    Integer nextModuleInitCodeSerialNumber = null;
                    AbstractFormatControlPane formatControlPane = initOperatingContainerParam.getFormatControlPane();
                    if (formatControlPane != null) {
                        ModuleInfo nextModuleInfo = formatControlPane.getNextModuleInfo(getModuleId());
                        if (nextModuleInfo != null) {
                            nextModuleInitCodeSerialNumber = formatControlPane.getModuleInitCodeSerialNumber(nextModuleInfo.getModuleId());
                        }
                    }

                    ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
                    CommandAddRelatedAttribute commandAddRelatedAttribute;
                    CodeListLocation codeListLocation;
                    InitMarkElement markElement;
                    String pathParam;
                    CodeShowPane codeShowPane;
                    boolean flag;
                    if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
                        if (pathFind.whetherToOperateAddDelOrNotForMark()) {
                            for (InitCodeModel codeModel : this.currentInitModel.getCodeModelList()) {
                                boolean inserNewLineOrNot = true;
                                if (null != codeModel.getCodeUsePropertyParam()) {
                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                                    if (GeneralCommandCodeModel.contant(
                                            setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
                                        inserNewLineOrNot = false;
                                    }
                                }

                                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
                                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
                                commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
                                commandAddRelatedAttribute.setAddToLast(false);
                                commandAddRelatedAttribute.setNextCodeSerialNumber(nextModuleInitCodeSerialNumber);

                                codeListLocationArrayList = new ArrayList<>();
                                codeListLocation = new CodeListLocation();
                                markElement = new InitMarkElement();
                                markElement.setModuleId(currentInitModel.getOperatingModel().getModuleId());
                                markElement.setInitSerialNumber(codeModel.getOrdinal());
                                markElement.setCodeNumber(codeModel.getCodeOrdinal());
                                markElement.setCodeLabelId(codeModel.getCodeLabelId());
                                codeListLocation.addCodePathFindForMark(markElement, pathFind);
                                codeListLocationArrayList.add(codeListLocation);

                                pathParam = codeModel.getPathParam();
                                codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.initOperatingContainerParam.getFormatControlPane());
                                if (codeShowPane != null) {
                                    flag = OpratingContainerStaticMethod.generateCode(this,
                                            codeShowPane, commandAddRelatedAttribute,
                                            codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
                                            inserNewLineOrNot, codeListLocationArrayList);
                                    if (flag) {
                                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);

                                        if (null != codeModel.getCodeUsePropertyParam()) {
                                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
                                            OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
                                                    setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
                                            );
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 生成默认代码面板需要的参数和内容
                    CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                            currentInitModel.getOperatingModel().getDefaultControlStatementFormat());
                    deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);

                    int hiddenStateFlag = currentInitModel.getOperatingModel().getHiddenState();// 获取当前选择语句的隐藏使用状态
                    if (hiddenStateFlag == GeneralCommandOperatingModel.TRUE_) {
                        this.hiddenState = true;

                    } else if (hiddenStateFlag == GeneralCommandOperatingModel.FALSE_) {
                        this.hiddenState = false;
                    }

                    if (hiddenState == true) {
                        // 生成隐藏代码面板需要的参数和内容
                        CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
                                currentInitModel.getOperatingModel().getHiddenControlStatementFormat());
                        hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);

                        //重新设置隐藏面板的高度
                        int width = (int) (PROPOTION * SysUtil.SCREEN_SIZE.getWidth());
                        int hiddenHeght = hiddenOperatingPane.getLineNum() * 30;
                        if (hiddenHeght > maxHiddenPaneHight) {
                            hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
                        } else {
                            hiddenOperatingJSP.setSize(width, hiddenHeght);
                        }

                        initCodeHiddenControlButton.setEnabled(true);
                        if (initCodeHiddenControlButton.isExpanded() == false) {
                            expandHiddenPanel();
                        }
                    } else {
                        if (initCodeHiddenControlButton.isExpanded() == true) {
                            packUpHiddenPanel();
                        }
                        initCodeHiddenControlButton.setEnabled(false);
                    }
                    initChoiceMenu.showSelectInit(currentInitModel);
                    //该功能的组件自动选择
                    //setNoUserSelectionIsRequiredValue();

                    //所有功能自动选择
                    ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                    if (opratingContainerList != null) {
                        for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                            opratingContainer.setNoUserSelectionIsRequiredValue();
                        }
                    }

                } else {//要更换的初始化功能无法插入到对应位置，给出提示
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

    @Override
    protected CodeShowPane getDeafaultCodePane() {
        // TODO Auto-generated method stub
        return this.initOperatingContainerParam.getFormatControlPane().getDefaultPane();
    }

    @Override
    public Dimension getRequiredDimension() {
        //int h = (int) (drawerComponent.getContentHeight() * drawerComponent.getRatio()) + CAPTION_HEIGHT;
        int w = getDrawer().getContentWidth(),
                h = 0;
        if (this.initContainerState) {
            h = deafaultOperatingJSP == null ?
                    0 :
                    deafaultOperatingJSP.getHeight();

            h = h + HIDDEN_BUTTON_HEIGHT;
            if (hiddenState) {
//                if (initCodeHiddenControlButton != null && initCodeHiddenControlButton.isExpanded() && getDrawer() != null) {
                h = h + (int) (getDrawer().getContentHeight() * getDrawer().getRatio());
//                }
            }
        }
        if (this.setButtonEnableState) {//加载模块设置相关组件
            h = h + HIDDEN_BUTTON_HEIGHT;
//            if (moduleSetCodeHiddenControlButton != null && moduleSetCodeHiddenControlButton.isExpanded() && moduleSetDrawer != null) {
            h = h + (int) (moduleSetDrawer.getContentHeight() * moduleSetDrawer.getRatio());
//            }
        }
        return new Dimension(w, h);
    }

    @Override
    public int getRequiredHeight() {
        int h = 0;
        if (this.initContainerState) {
            h = deafaultOperatingJSP == null ?
                    0 :
                    deafaultOperatingJSP.getHeight();

            h = h + HIDDEN_BUTTON_HEIGHT;
            if (hiddenState) {
//                if (initCodeHiddenControlButton != null && initCodeHiddenControlButton.isExpanded() && getDrawer() != null) {
                h = h + (int) (getDrawer().getContentHeight() * getDrawer().getRatio());
//                }
            }
        }
        if (this.setButtonEnableState) {//加载模块设置相关组件
            h = h + HIDDEN_BUTTON_HEIGHT;
//            if (moduleSetCodeHiddenControlButton != null && moduleSetCodeHiddenControlButton.isExpanded() && moduleSetDrawer != null) {
            h = h + (int) (moduleSetDrawer.getContentHeight() * moduleSetDrawer.getRatio());
//            }
        }
        return h;
    }

    @Override
    public File getImageRootPath() {
        File file = SourceGenerateFileStructure.getModulePictureFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleInfo.getModuleId());
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = SourceGenerateFileStructure.getModuleNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleInfo.getModuleId());
        return file;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        InitOpratingContainerModel theModel = (InitOpratingContainerModel) model;
        if (this.initContainerState) {
            super.setParam(theModel);
            theModel.setInitList(initList);
            theModel.setCurrentInitModel(currentInitModel);
        } else {
            theModel.setCodeSerialNumber(codeSerialNumber);
            theModel.setPathFind(pathFind);
            theModel.setHiddenState(false);
            theModel.setCanBeDelOrNot(false);
            theModel.setCurrentInitModel(null);
        }
        theModel.setThisCodeLocationInformation(this.thisCodeLocationInformation);
        theModel.setInitContainerState(this.initContainerState);
        theModel.setModuleId(getModuleId());
        theModel.setSetButtonEnableState(setButtonEnableState);
        if (setButtonEnableState && settingContainer != null) {
            theModel.setSettingContainerModel(settingContainer.getFormatStructureModel());
        }
    }

    @Override
    public InitOpratingContainerModel getFormatStructureModel() {
        // TODO Auto-generated method stub
        InitOpratingContainerModel model = new InitOpratingContainerModel();
        setParam(model);
        return model;
    }

    @Override
    public String getPaneType() {
        // TODO Auto-generated method stub
        return MarkElementName.INIT_MARK;
    }

    @Override
    public String getClassName() {
        // TODO Auto-generated method stub
        return moduleInfo.getClassName();
    }

    @Override
    public String getModuleName() {
        // TODO Auto-generated method stub
        return moduleInfo.getModuleName();
    }

    @Override
    public String getModuleId() {
        return moduleInfo.getModuleId();
    }

    @Override
    public int getAdditionalSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getOrdinalInUserDB() {
        return currentInitModel.getOperatingModel().getOrdinal();
    }

    public void delModuleOpratingContainer(String moduleId) {
        // TODO Auto-generated method stub
        if (this.moduleInfo.getModuleId().equals(moduleId)) {
            delThis();
        } else {
            delModuleOpratingContainerFromComponent(moduleId);
        }
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        if (this.initContainerState) {
            super.delModuleOpratingContainerFromComponent(moduleId);
        }
        if (setButtonEnableState) {
            if (settingContainer != null) {
                settingContainer.delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (this.initContainerState) {
            opratingContainerList.addAll(super.getAllOpratingContainerListInThis());
        }
        if (setButtonEnableState) {
            if (settingContainer != null) {
                opratingContainerList.addAll(settingContainer.getAllOpratingContainer());
            }
        }
        return opratingContainerList;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (this.initContainerState) {
            opratingContainerList.addAll(super.getAllOpratingContainer());
        }
        return opratingContainerList;
    }

    @Override
    public AbstractCodeControlPane getCodeControlPane() {
        return this.initOperatingContainerParam.getCodeControlPane();
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (this.initContainerState) {
            super.collapseThis();
        }
        if (setButtonEnableState) {
            if (settingContainer != null) {
                settingContainer.collapseThis();
            }
        }
    }


    /**
     * 设置面板：非必要 默认面板/默认滑动条：必要 隐藏控制按钮/抽屉：非必要 选择显示下拉框:非必要
     */
    @Override
    protected void init(boolean expanded, boolean canBeDelOrNot, AbstractCodeControlPane codeControlPane) {
        this.codeControlPane = codeControlPane;
        setLayout(new InitOpratingContainer.InitOpratingLayout(this.initContainerState));
        int width = (int) (PROPOTION * SysUtil.SCREEN_SIZE.getWidth());
        if (this.initContainerState == true) {//有初始化功能
            double hiddenButtonWidth = 0.7;

            if (hiddenState == false) {
                expanded = false;
            }
            deafaultOperatingPane = new DeafaultCommandOperatingPane(this);
            deafaultOperatingJSP = new JScrollPane(deafaultOperatingPane);
            deafaultOperatingJSP.getVerticalScrollBar().setUI(new MyScrollBarUI());
            add(deafaultOperatingJSP);

            initCodeHiddenControlButton = new CodeHiddenControlButton(expanded) {

                @Override
                public void doSomethingWhenMousePressed(boolean expanded) {
                    super.doSomethingWhenMousePressed(expanded);
                    if (hiddenState == true) {
                        if (initCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                    if (setButtonEnableState) {
                        if (settingContainer != null) {
                            settingContainer.collapseThis();
                        }
                    }
                }

                @Override
                public void doClick() {
                    super.doClick();
                    if (hiddenState == true) {
                        if (initCodeHiddenControlButton.isExpanded() == true) {
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                    if (setButtonEnableState) {
                        if (settingContainer != null) {
                            settingContainer.collapseThis();
                        }
                    }
                }
            };
            if (initList.size() == 1) {
                initCodeHiddenControlButton.setSize(width, HIDDEN_BUTTON_HEIGHT);

            } else if (initList.size() > 1) {
                initCodeHiddenControlButton.setSize((int) (hiddenButtonWidth * width), HIDDEN_BUTTON_HEIGHT);
            }
            setHiddenButton(initCodeHiddenControlButton);
            add(initCodeHiddenControlButton);

            if (initList.size() > 1) {// 有还几条初始化方法可以选择
//				initChoiceCombobox = new InitChoiceCombobox(initList, this);
                initChoiceMenu = new InitChoiceMenu(initList, this);
                Dimension dd2 = new Dimension((int) ((1 - hiddenButtonWidth) * width), HIDDEN_BUTTON_HEIGHT);
                initChoiceMenu.setPreferredSize(dd2);
                initChoiceMenu.setMinimumSize(dd2);
                initChoiceMenu.setMaximumSize(dd2);
                add(initChoiceMenu);
            }

            hiddenOperatingPane = new HiddenCommandOperatingPane(this);
            hiddenOperatingJSP = new JScrollPane(hiddenOperatingPane);
            hiddenOperatingJSP.getVerticalScrollBar().setUI(new MyScrollBarUI());
            Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenOperatingJSP);
            this.setDrawer(drawer);
            add(drawer);

            if (hiddenState == false) {
                initCodeHiddenControlButton.setEnabled(false);
            }
        } else {
            hiddenOperatingPane = new HiddenCommandOperatingPane(this);
            hiddenOperatingJSP = new JScrollPane(hiddenOperatingPane);

            Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenOperatingJSP);
            this.setDrawer(drawer);
        }
        if (this.setButtonEnableState) {//加载模块设置相关组件

            moduleSetCodeHiddenControlButton = new ModuleSetCodeHiddenControlButton(expanded) {

                @Override
                public void doSomethingWhenMousePressed(boolean expanded) {
                    super.doSomethingWhenMousePressed(expanded);
                    if (moduleSetCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
                        settingContainer.collapseThis();
                    }
                    if (hiddenState == true) {
                        if (initCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                }

                @Override
                public void doClick() {
                    super.doClick();
                    if (moduleSetCodeHiddenControlButton.isExpanded() == true) {
                        settingContainer.collapseThis();
                    }
                    if (hiddenState == true) {
                        if (initCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                }
            };
            moduleSetCodeHiddenControlButton.setSize(width, HIDDEN_BUTTON_HEIGHT);
            add(moduleSetCodeHiddenControlButton);

            moSetScrollPane = new JScrollPane();
            moSetScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            moduleSetDrawer = new Drawer(1, moSetScrollPane);
            add(moduleSetDrawer);
        }
//        setLayout(new InitOpratingContainer.InitOpratingLayout(this.initContainerState));
    }

    private void setModuleTypeOperatingContainerParam(ModuleTypeOperatingContainerParam moduleTypeContainerParam) {
        moduleTypeContainerParam.setFormatControlPane(this.initOperatingContainerParam.getFormatControlPane());
        moduleTypeContainerParam.setModule(this.initOperatingContainerParam.getModule());
        moduleTypeContainerParam.setModuleInfo(this.initOperatingContainerParam.getModuleInfo());
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        if (setButtonEnableState) {
            if (MarkElementName.MODULE_CONTROL.equals(currentOpratingContainer.getPathFind().getMarkType())) {
                if (getModuleId() != null) {
                    if (getModuleId().equals(currentOpratingContainer.getFormatContainer().getModuleId())) {
                        settingContainer.autoCollapse(currentOpratingContainer);
                    }
                }

            } else if (MarkElementName.SET_MARK.equals(currentOpratingContainer.getPathFind().getMarkType())) {
                if (getModuleId() != null) {
                    if (getModuleId().equals(currentOpratingContainer.getFirstCommandOpratingContainer().getModuleId())) {
                        settingContainer.autoCollapse(currentOpratingContainer);
                    }
                }

            } else {
                if (this.initContainerState) {
                    super.autoCollapse(currentOpratingContainer);
                }
            }
        }
    }

    @Override
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
        return this;
    }

    @Override
    public AbstractFormatContainer getFormatContainer() {
        return null;
    }


    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        if (this.initContainerState) {
            super.functionNameSynchronousChange(functionNameId);
        }
        if (this.setButtonEnableState && settingContainer != null) {
            settingContainer.functionNameSynchronousChange(functionNameId);
        }
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {
        if (this.initContainerState) {
            super.functionNameSynchronousDelete(functionNameId);
        }
        if (this.setButtonEnableState && settingContainer != null) {
            settingContainer.functionNameSynchronousDelete(functionNameId);
        }
    }

    @Override
    public void variableSynchronousChange(int variableId) {
        if (this.initContainerState) {
            super.variableSynchronousChange(variableId);
        }
        if (this.setButtonEnableState && settingContainer != null) {
            settingContainer.variableSynchronousChange(variableId);
        }
    }

    @Override
    public void variableSynchronousDelete(int variableId) {
        if (this.initContainerState) {
            super.variableSynchronousDelete(variableId);
        }
        if (this.setButtonEnableState && settingContainer != null) {
            settingContainer.variableSynchronousDelete(variableId);
        }
    }

    @Override
    public boolean isItTheSameLevel(OpratingContainerInterface opratingContainer) {
        boolean flag = false;
        if (this.initContainerState) {
            flag = super.isItTheSameLevel(opratingContainer);
        }
        return flag;
    }

    @Override
    public void setNoUserSelectionIsRequiredValue() {
        if (this.initContainerState) {
            super.setNoUserSelectionIsRequiredValue();
        }
        if (this.setButtonEnableState && settingContainer != null) {
            ArrayList<OpratingContainerInterface> opratingContainerList = settingContainer.getAllOpratingContainer();
            for (OpratingContainerInterface opratingContainerInterface : opratingContainerList) {
                opratingContainerInterface.setNoUserSelectionIsRequiredValue();
            }
        }
    }

    @Override
    public InitMarkElement getCorrespondingMarkElement() {
        return new InitMarkElement();
    }

    @Override
    public OpratingContainerInterface getParentOpratingContainer() {
        return null;
    }

}
//
//import com.lazycoder.database.common.MarkElementName;
//import com.lazycoder.database.model.Module;
//import com.lazycoder.database.model.ModuleInfo;
//import com.lazycoder.database.model.command.InitCodeModel;
//import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
//import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
//import com.lazycoder.database.model.general.command.GeneralCommandOperatingModel;
//import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
//import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
//import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
//import com.lazycoder.service.service.SysService;
//import com.lazycoder.service.vo.element.mark.InitMarkElement;
//import com.lazycoder.service.vo.meta.InitMetaModel;
//import com.lazycoder.uicodegeneration.PathFind;
//import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
//import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
//import com.lazycoder.uicodegeneration.component.operation.component.typeset.module.SetButton;
//import com.lazycoder.uicodegeneration.component.operation.container.component.CodeHiddenControlButton;
//import com.lazycoder.uicodegeneration.component.operation.container.component.InitChoiceMenu;
//import com.lazycoder.uicodegeneration.component.operation.container.pane.DeafaultCommandOperatingPane;
//import com.lazycoder.uicodegeneration.component.operation.container.pane.HiddenCommandOperatingPane;
//import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
//import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
//import com.lazycoder.uicodegeneration.component.operation.container.sendparam.InitOperatingContainerParam;
//import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
//import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
//import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
//import com.lazycoder.uicodegeneration.generalframe.tool.CodeIDGenerator;
//import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
//import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
//import com.lazycoder.uicodegeneration.proj.stostr.operation.container.InitOpratingContainerModel;
//import com.lazycoder.uiutils.folder.Drawer;
//import com.lazycoder.uiutils.htmlstyte.HTMLText;
//import com.lazycoder.uiutils.htmlstyte.HtmlPar;
//import com.lazycoder.uiutils.ui.MyScrollBarUI;
//import com.lazycoder.uiutils.utils.SysUtil;
//import com.lazycoder.utils.swing.LazyCoderOptionPane;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.LayoutManager;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.UIManager;
//
//public class InitOpratingContainer extends AbstractCommandOpratingContainer {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 6959402228841643714L;
//
//    private static final int HIDDEN_BUTTON_HEIGHT = 30, maxHiddenPaneHight = 60;
//
//    private SetButton setButton;
//
////	private InitChoiceCombobox initChoiceCombobox;
//
//    private InitChoiceMenu initChoiceMenu;
//
//    /**
//     * 设置按钮使用状态
//     */
//    private boolean initContainerState = true,
//            setButtonEnableState = false;
//
//    /**
//     * 初始化列表信息
//     */
//    private List<InitFeatureSelectonModel> initList = new ArrayList<>();
//
//    /**
//     * 当前的初始化模型
//     */
//    private InitMetaModel currentInitModel = null;
//
//    private InitOperatingContainerParam initOperatingContainerParam;
//
//    private Module module = null;
//
//    private ModuleInfo moduleInfo = null;
//
//    private CodeHiddenControlButton codeHiddenControlButton = null;
//
//    /**
//     * 新建
//     *
//     * @param initOperatingContainerParam
//     * @param expanded
//     */
//    public InitOpratingContainer(InitMetaModel metaModel, InitOperatingContainerParam initOperatingContainerParam, boolean expanded) {
//        super();
//        this.module = initOperatingContainerParam.getModule();
//        this.moduleInfo = initOperatingContainerParam.getModuleInfo();
//
//        initOperatingContainerParam.setFirstCommandOpratingContainer(this);
//        this.initOperatingContainerParam = initOperatingContainerParam;
//
//        if (metaModel == null) {
//            this.initContainerState = false;
//        } else {
//            this.initContainerState = true;
//            this.currentInitModel = metaModel;
//        }
//
//        if (this.initContainerState == true) {//有初始化功能
//            int hiddenStateFlag = currentInitModel.getOperatingModel().getHiddenState();// 获取当前选择语句的隐藏使用状态
//            if (hiddenStateFlag == GeneralCommandOperatingModel.TRUE_) {
//                this.hiddenState = true;
//
//            } else if (hiddenStateFlag == GeneralCommandOperatingModel.FALSE_) {
//                this.hiddenState = false;
//            }
//            if (initOperatingContainerParam.getModuleInfo().getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//没有模块控制相关内容，也没有设置相关内容
//                setButtonEnableState = false;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//没有模块控制相关内容，但有设置相关内容
//                setButtonEnableState = true;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//有模块控制相关内容，但没有设置相关内容
//                setButtonEnableState = true;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//有模块控制相关内容，但没有设置相关内容
//                setButtonEnableState = true;
//            }
//            initList = SysService.INIT_SERVICE.getFeatureList(moduleInfo.getModuleId());
//
//        } else {//没有初始化功能
//            this.hiddenState = false;
//            if (initOperatingContainerParam.getModuleInfo().getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//没有模块控制相关内容，也没有设置相关内容
//                setButtonEnableState = false;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.FALSE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//没有模块控制相关内容，但有设置相关内容
//                setButtonEnableState = true;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() == 0) {//有模块控制相关内容，但没有设置相关内容
//                setButtonEnableState = true;
//
//            } else if (initOperatingContainerParam.getModuleInfo()
//                    .getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_
//                    && initOperatingContainerParam.getModuleInfo().getNumOfSetCodeType() > 0) {//有模块控制相关内容，但没有设置相关内容
//                setButtonEnableState = true;
//            }
//        }
//
//        init(expanded, false, initOperatingContainerParam.getCodeControlPane());
//
//        if (this.initContainerState == true) {//有初始化功能
//            if (initList.size() > 1) {
//                initChoiceMenu.showSelectInit(currentInitModel);
//            }
//        }
//
//        setButton = new SetButton(initOperatingContainerParam);
//        setButton.setEnabled(setButtonEnableState);
//        add(setButton);
//
//        generateCodeAndOpratingContainer();
//        setAppropriateSize(PROPOTION);
//    }
//
//    /**
//     * 还原
//     *
//     * @param model
//     * @param initOperatingContainerParam
//     * @param expanded
//     */
//    public InitOpratingContainer(InitOpratingContainerModel model,
//                                      InitOperatingContainerParam initOperatingContainerParam, boolean expanded) {
//        super();
//        this.module = initOperatingContainerParam.getModule();
//        this.moduleInfo = initOperatingContainerParam.getModuleInfo();
//
//        this.initContainerState = model.isInitContainerState();
//        this.setButtonEnableState = model.isSetButtonEnableState();
//        this.initList = model.getInitList();
//        this.currentInitModel = model.getCurrentInitModel();
//        initOperatingContainerParam.setFirstCommandOpratingContainer(this);
//        this.initOperatingContainerParam = initOperatingContainerParam;
//
//        setButton = new SetButton(model, this.initOperatingContainerParam);
//        setButton.setEnabled(setButtonEnableState);
//        add(setButton);
//
//        restoreInit(model);
//        init(expanded, model.isCanBeDelOrNot(), initOperatingContainerParam.getCodeControlPane());
//        CommandContainerComponentParam commandContainerComponentParam = getContainerComponentParam(null);
//        restorePaneContent(model, commandContainerComponentParam);
//        setAppropriateSize(PROPOTION);
//    }
//
//    @Override
//    protected void restoreInit(AbstractCommandOperatingContainerModel commandOperatingContainerModel) {
//        super.restoreInit(commandOperatingContainerModel);
//        setThisToolTipText(module.getModuleName());
////        this.thisCodeLocationInformation = commandOperatingContainerModel.getThisCodeLocationInformation();
////        if (this.initContainerState) {
////            this.pathFind = commandOperatingContainerModel.getPathFind();
////            this.codeSerialNumber = commandOperatingContainerModel.getCodeSerialNumber();
////            this.hiddenState = commandOperatingContainerModel.isHiddenState();
////
////            setThisToolTipText(module.getModuleName());
////        } else {
////            this.pathFind = commandOperatingContainerModel.getPathFind();
////            this.codeSerialNumber = commandOperatingContainerModel.getCodeSerialNumber();
////            this.hiddenState = commandOperatingContainerModel.isHiddenState();
////            setThisToolTipText(module.getModuleName());
////        }
//    }
//
//    @Override
//    protected void setThisToolTipText(String functionShowText) {
//        HTMLText htmlText = new HTMLText();
//        if (functionShowText != null) {
//            HtmlPar par1 = new HtmlPar();
//            par1.addText(functionShowText, true);
//            htmlText.addPar(par1);
//        }
//        HtmlPar par2 = new HtmlPar();
//        par2.addText("序号：", false);
//        par2.addText(codeSerialNumber + "", true);
//        htmlText.addPar(par2);
//        setButton.setToolTipText(htmlText.getHtmlContent());
//    }
//
//    @Override
//    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel, GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
//        if (this.initContainerState == true) {
//            super.restorePaneContent(commandOperatingContainerModel, codeGenerationalOpratingContainerParam);
//            if (initList.size() > 1) {
//                initChoiceMenu.showSelectInit(currentInitModel);
//            }
//        }
//    }
//
//    /**
//     * 测试用
//     *
//     * @param expanded
//     */
//    private InitOpratingContainer(boolean expanded) {
//        // TODO Auto-generated constructor stub
//        super();
//
//        this.setButtonEnableState = false;
//        this.hiddenState = true;
//
//        initList = new ArrayList<>();
//        InitFeatureSelectonModel selectionOption1 = new InitFeatureSelectonModel();
//        selectionOption1.setOrdinal(1);
//        selectionOption1.setShowText("测试1");
//        initList.add(selectionOption1);
//
//        // InitSelectionOption selectionOption2 = new InitSelectionOption();
//        // selectionOption2.setOrdinal(2);
//        // selectionOption2.setShowText("测试2");
//        // initList.add(selectionOption2);
//
//        init(expanded, false, null);
//    }
//
//    /**
//     * 生成代码和组件
//     */
//    private void generateCodeAndOpratingContainer() {
//        codeSerialNumber = CodeIDGenerator.generateCodeSerialNumber();
//        pathFind = initOperatingContainerParam.getCodeControlPane().getPathFind();
//
//        // 生成需要的代码
//        if (this.initContainerState == true && currentInitModel != null) {
//            setThisToolTipText(module.getModuleName());
//            generateCode();
//
//            // 生成默认代码面板需要的参数和内容
//            CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
//                    currentInitModel.getOperatingModel().getDefaultControlStatementFormat());
//            deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);
//
//            if (hiddenState == true) {
//                // 生成隐藏代码面板需要的参数和内容
//                CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
//                        currentInitModel.getOperatingModel().getHiddenControlStatementFormat());
//                hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
//            }
//        } else {
//            setThisToolTipText(module.getModuleName());
//        }
//    }
//
//    private void generateCode() {
//        if (this.initContainerState && pathFind != null) {
//            ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
//            CommandAddRelatedAttribute commandAddRelatedAttribute;
//            CodeListLocation codeListLocation;
//            ArrayList<CodeListLocation> codeListLocationArrayList;
//            InitMarkElement markElement;
//            String pathParam;
//            CodeShowPane codeShowPane;
//            boolean flag;
//            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
//                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
//                    for (InitCodeModel codeModel : this.currentInitModel.getCodeModelList()) {
//                        boolean inserNewLineOrNot = true;
//                        if (null != codeModel.getCodeUsePropertyParam()) {
//                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                            if (GeneralCommandCodeModel.contant(
//                                    setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
//                                inserNewLineOrNot = false;
//                            }
//                        }
//
//                        codeListLocationArrayList=new ArrayList<>();
//
//                        commandAddRelatedAttribute = new CommandAddRelatedAttribute();
//                        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
//                        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
//                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                        codeListLocation = new CodeListLocation();
//                        markElement = new InitMarkElement();
//                        markElement.setModuleId(currentInitModel.getOperatingModel().getModuleId());
//                        markElement.setInitSerialNumber(codeModel.getOrdinal());
//                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
//                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
//                        codeListLocation.addCodePathFindForMark(markElement, pathFind);
//                        codeListLocationArrayList.add(codeListLocation);
//
//                        pathParam = codeModel.getPathParam();
//                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.initOperatingContainerParam.getFormatControlPane());
//                        if (codeShowPane != null) {
//                            flag = OpratingContainerStaticMethod.generateCode(this,
//                                    codeShowPane, commandAddRelatedAttribute,
//                                    codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
//                                    inserNewLineOrNot, codeListLocationArrayList);
//                            if (flag) {
//                                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//
//                                if (null != codeModel.getCodeUsePropertyParam()) {
//                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                                    OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
//                                            setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
//                                    );
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 查看这里是不是可以添加这个代码的本功能容器
//     *
//     * @param initOperatingContainerParam
//     * @param metaModel
//     * @return
//     */
//    public static boolean checkWhetherItMatches(InitOperatingContainerParam initOperatingContainerParam,
//                                                InitMetaModel metaModel) {
//        boolean returnFlag = false;
//        PathFind pathFind = initOperatingContainerParam.getCodeControlPane().getPathFind();
//        if (pathFind != null) {
//            CommandAddRelatedAttribute commandAddRelatedAttribute;
//            CodeListLocation codeListLocation;
//            InitMarkElement markElement;
//            String pathParam;
//            CodeShowPane codeShowPane;
//            boolean flag;
//            if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
//                if (pathFind.whetherToOperateAddDelOrNotForMark()) {
//                    returnFlag = true;
//                    for (InitCodeModel codeModel : metaModel.getCodeModelList()) {
//
//                        commandAddRelatedAttribute = new CommandAddRelatedAttribute();
//                        commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
//                        commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
//                        commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//
//                        codeListLocation = new CodeListLocation();
//                        markElement = new InitMarkElement();
//                        markElement.setModuleId(metaModel.getOperatingModel().getModuleId());
//                        markElement.setInitSerialNumber(codeModel.getOrdinal());
//                        markElement.setCodeNumber(codeModel.getCodeOrdinal());
//                        markElement.setCodeLabelId(codeModel.getCodeLabelId());
//                        codeListLocation.addCodePathFindForMark(markElement, pathFind);
//
//                        pathParam = codeModel.getPathParam();
//                        codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, initOperatingContainerParam.getFormatControlPane());
//                        if (codeShowPane != null) {
//                            flag = OpratingContainerStaticMethod.checkGenerateCode(
//                                    pathFind, codeShowPane, commandAddRelatedAttribute,
//                                    codeListLocation);
//                            if (!flag) {
//                                returnFlag = false;//只要有一句代码无法插入，都要标记为false
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return returnFlag;
//    }
//
//    /**
//     * 设置组件参数
//     *
//     * @param controlStatementFormat
//     * @return
//     */
//    @Override
//    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {
//        CommandContainerComponentParam codeGenerationalOpratingContainerParam = super.getContainerComponentParam(controlStatementFormat);
//        codeGenerationalOpratingContainerParam.setFormatControlPane(initOperatingContainerParam.getFormatControlPane());
//        codeGenerationalOpratingContainerParam.setThisOpratingContainer(this);
//        codeGenerationalOpratingContainerParam.setCodeControlPane(initOperatingContainerParam.getCodeControlPane());
//        codeGenerationalOpratingContainerParam.setParentPathFind(pathFind);
//        codeGenerationalOpratingContainerParam.setModule(initOperatingContainerParam.getModule());
//        codeGenerationalOpratingContainerParam.setModuleInfo(initOperatingContainerParam.getModuleInfo());
//        codeGenerationalOpratingContainerParam.setCodeSerialNumber(codeSerialNumber);
//        codeGenerationalOpratingContainerParam.setControlStatementFormat(controlStatementFormat);
//        codeGenerationalOpratingContainerParam.setFirstCommandOpratingContainer(this);
//        return codeGenerationalOpratingContainerParam;
//    }
//
//    @Override
//    protected void setAppropriateSize(double maxPropotion) {
//        // TODO Auto-generated method stub
//        if (this.initContainerState) {
//            int width = (int) (maxPropotion * SysUtil.SCREEN_SIZE.getWidth());
//            Dimension dd1 = new Dimension(width, DEFAULT_SHOW_MIN_HEIGHT);
//            deafaultOperatingJSP.setPreferredSize(dd1);
//            deafaultOperatingJSP.setMaximumSize(dd1);
//            deafaultOperatingJSP.setMinimumSize(dd1);
//            int hiddenHeght = hiddenOperatingPane.getPreferredSize().height;
//            if (hiddenHeght > maxHiddenPaneHight) {
//                hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
//            } else {
//                hiddenOperatingJSP.setSize(width, hiddenHeght);
//            }
//        } else {
//            int width = (int) (maxPropotion * SysUtil.SCREEN_SIZE.getWidth());
//            int hiddenHeght = hiddenOperatingPane.getPreferredSize().height;
//            if (hiddenHeght > maxHiddenPaneHight) {
//                hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
//            } else {
//                hiddenOperatingJSP.setSize(width, hiddenHeght);
//            }
//        }
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(UIManager.getColor("Panel.background"));//设置画图的颜色
//        g2d.fillRect(0, 0, getWidth(), DEFAULT_SHOW_MIN_HEIGHT);//填充一个矩形
//        super.paint(g);
//    }
//
//    /**
//     * 负责布局面板组件
//     */
//    class InitOpratingLayout implements LayoutManager {
//
//        private boolean initContainerState = true;
//
//        public InitOpratingLayout(boolean initContainerState) {
//            this.initContainerState = initContainerState;
//        }
//
//        @Override
//        public void addLayoutComponent(String name, Component comp) {
//        }
//
//        @Override
//        public void removeLayoutComponent(Component comp) {
//        }
//
//        @Override
//        public Dimension preferredLayoutSize(Container parent) {
//            return parent.getPreferredSize();
//        }
//
//        @Override
//        public Dimension minimumLayoutSize(Container parent) {
//            return parent.getMinimumSize();
//        }
//
//        @Override
//        public void layoutContainer(Container parent) {
//            setButton.setBounds(0, 0, (int) setButton.getPreferredSize().getWidth(),
//                    (int) setButton.getPreferredSize().getHeight());
//
//            if (this.initContainerState) {
//                int w = parent.getWidth(), h = parent.getHeight(),
//                        dHeight = deafaultOperatingJSP.getHeight() + HIDDEN_BUTTON_HEIGHT;
//
//                deafaultOperatingJSP.setBounds((int) setButton.getPreferredSize().getWidth(), 0,
//                        w - (int) setButton.getPreferredSize().getWidth(), DEFAULT_SHOW_MIN_HEIGHT);
//
//                if (getHiddenButton() != null) {
//                    getHiddenButton().setBounds(0, DEFAULT_SHOW_MIN_HEIGHT, getHiddenButton().getWidth(), HIDDEN_BUTTON_HEIGHT);
//                }
//
//                if (initList.size() == 1) {// 只有一个初始化方法
//                    // 抽屉只显示抽出的比例
//                    getDrawer().setBounds(0, dHeight, w, h - dHeight);
//
//                } else if (initList.size() > 1) {// 有多个初始化方法
//                    initChoiceMenu.setBounds(getHiddenButton().getWidth(), DEFAULT_SHOW_MIN_HEIGHT,
//                            w - getHiddenButton().getWidth(), HIDDEN_BUTTON_HEIGHT);
//                    // 抽屉只显示抽出的比例
//                    getDrawer().setBounds(0, dHeight, w, h - dHeight);
//                }
//            }
//        }
//    }
//
//    public void updateInitCode(InitFeatureSelectonModel featureSelectonModel) {
//        if (this.initContainerState == true && featureSelectonModel.getOrdinal() != currentInitModel.getOperatingModel().getOrdinal()) {
//            ArrayList<CodeListLocation> codeListLocationArrayList;
//
//            InitMetaModel newMetaModel = SysService.INIT_SERVICE.getModuleSetMetaModel(featureSelectonModel);
//            if (newMetaModel != null) {
//                boolean checkFlag = checkWhetherItMatches(this.initOperatingContainerParam, newMetaModel);
//                if (checkFlag) {
//                    delCode();
//                    delControlComponentsAndContent();
//
//                    currentInitModel = newMetaModel;
//
//                    Integer nextModuleInitCodeSerialNumber = null;
//                    AbstractFormatControlPane formatControlPane = initOperatingContainerParam.getFormatControlPane();
//                    if (formatControlPane != null) {
//                        ModuleInfo nextModuleInfo = formatControlPane.getNextModuleInfo(getModuleId());
//                        if (nextModuleInfo != null) {
//                            nextModuleInitCodeSerialNumber = formatControlPane.getModuleInitCodeSerialNumber(nextModuleInfo.getModuleId());
//                        }
//                    }
//
//                    ArrayList<AbstractCodeUseProperty> setPropertyTempList = null;
//                    CommandAddRelatedAttribute commandAddRelatedAttribute;
//                    CodeListLocation codeListLocation;
//                    InitMarkElement markElement;
//                    String pathParam;
//                    CodeShowPane codeShowPane;
//                    boolean flag;
//                    if (PathFind.COMMAND_TYPE == pathFind.getMetaType() && MarkElementName.INIT_MARK.equals(pathFind.getMarkType())) {
//                        if (pathFind.whetherToOperateAddDelOrNotForMark()) {
//                            for (InitCodeModel codeModel : this.currentInitModel.getCodeModelList()) {
//                                boolean inserNewLineOrNot = true;
//                                if (null != codeModel.getCodeUsePropertyParam()) {
//                                    setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                                    if (GeneralCommandCodeModel.contant(
//                                            setPropertyTempList, AbstractCodeUseProperty.NoNeedInserNewLine)) {
//                                        inserNewLineOrNot = false;
//                                    }
//                                }
//
//                                commandAddRelatedAttribute = new CommandAddRelatedAttribute();
//                                commandAddRelatedAttribute.setAddType(CommandAddRelatedAttribute.ADD_TO_MARK_ADD_TYPE);
//                                commandAddRelatedAttribute.setOtherAttribute(CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
//                                commandAddRelatedAttribute.setCodeLabelId(codeModel.getCodeLabelId());
//                                commandAddRelatedAttribute.setAddToLast(false);
//                                commandAddRelatedAttribute.setNextCodeSerialNumber(nextModuleInitCodeSerialNumber);
//
//                                codeListLocationArrayList = new ArrayList<>();
//                                codeListLocation = new CodeListLocation();
//                                markElement = new InitMarkElement();
//                                markElement.setModuleId(currentInitModel.getOperatingModel().getModuleId());
//                                markElement.setInitSerialNumber(codeModel.getOrdinal());
//                                markElement.setCodeNumber(codeModel.getCodeOrdinal());
//                                markElement.setCodeLabelId(codeModel.getCodeLabelId());
//                                codeListLocation.addCodePathFindForMark(markElement, pathFind);
//                                codeListLocationArrayList.add(codeListLocation);
//
//                                pathParam = codeModel.getPathParam();
//                                codeShowPane = OpratingContainerStaticMethod.getCurrentChowPane(pathParam, this.initOperatingContainerParam.getFormatControlPane());
//                                if (codeShowPane != null) {
//                                    flag = OpratingContainerStaticMethod.generateCode(this,
//                                            codeShowPane, commandAddRelatedAttribute,
//                                            codeModel.getCodeFormatParam(), codeModel.getCodeOrdinal(), codeModel.getCodeLabelId(),
//                                            inserNewLineOrNot, codeListLocationArrayList);
//                                    if (flag) {
//                                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
//
//                                        if (null != codeModel.getCodeUsePropertyParam()) {
//                                            setPropertyTempList = GeneralCommandCodeModel.getCodeUsePropertyList(codeModel.getCodeUsePropertyParam());
//                                            OpratingContainerStaticMethod.handlesOperationsRelatedToCodeProperties(
//                                                    setPropertyTempList, codeShowPane, moduleInfo, module, this.getModuleId()
//                                            );
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    // 生成默认代码面板需要的参数和内容
//                    CommandContainerComponentParam deafaultCodeGenerationalOpratingContainerParam = getContainerComponentParam(
//                            currentInitModel.getOperatingModel().getDefaultControlStatementFormat());
//                    deafaultOperatingPane.generateOperationalContent(deafaultCodeGenerationalOpratingContainerParam);
//
//                    int hiddenStateFlag = currentInitModel.getOperatingModel().getHiddenState();// 获取当前选择语句的隐藏使用状态
//                    if (hiddenStateFlag == GeneralCommandOperatingModel.TRUE_) {
//                        this.hiddenState = true;
//
//                    } else if (hiddenStateFlag == GeneralCommandOperatingModel.FALSE_) {
//                        this.hiddenState = false;
//                    }
//
//                    if (hiddenState == true) {
//                        // 生成隐藏代码面板需要的参数和内容
//                        CommandContainerComponentParam hiddenCodeGenerationalOpratingContainerParam = getContainerComponentParam(
//                                currentInitModel.getOperatingModel().getHiddenControlStatementFormat());
//                        hiddenOperatingPane.generateOperationalContent(hiddenCodeGenerationalOpratingContainerParam);
//
//                        //重新设置隐藏面板的高度
//                        int width = (int) (PROPOTION * SysUtil.SCREEN_SIZE.getWidth());
//                        int hiddenHeght = hiddenOperatingPane.getLineNum() * 30;
//                        if (hiddenHeght > maxHiddenPaneHight) {
//                            hiddenOperatingJSP.setSize(width, maxHiddenPaneHight);
//                        } else {
//                            hiddenOperatingJSP.setSize(width, hiddenHeght);
//                        }
//
//                        codeHiddenControlButton.setEnabled(true);
//                        if (codeHiddenControlButton.isExpanded() == false) {
//                            expandHiddenPanel();
//                        }
//                    } else {
//                        if (codeHiddenControlButton.isExpanded() == true) {
//                            packUpHiddenPanel();
//                        }
//                        codeHiddenControlButton.setEnabled(false);
//                    }
//                    initChoiceMenu.showSelectInit(currentInitModel);
//                    //该功能的组件自动选择
//                    //setNoUserSelectionIsRequiredValue();
//
//                    //所有功能自动选择
//                    ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
//                    if (opratingContainerList != null) {
//                        for (OpratingContainerInterface opratingContainer : opratingContainerList) {
//                            opratingContainer.setNoUserSelectionIsRequiredValue();
//                        }
//                    }
//
//                } else {//要更换的初始化功能无法插入到对应位置，给出提示
//                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
//                            JOptionPane.PLAIN_MESSAGE);
//                }
//            }
//        }
//    }
//
//    @Override
//    protected CodeShowPane getDeafaultCodePane() {
//        // TODO Auto-generated method stub
//        return this.initOperatingContainerParam.getFormatControlPane().getDefaultPane();
//    }
//
//    @Override
//    public Dimension getRequiredDimension() {
//        int w = getDrawer().getContentWidth(), h = setButton.getButtonHeight();
//        if (this.initContainerState) {
//            h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + DEFAULT_SHOW_MIN_HEIGHT + HIDDEN_BUTTON_HEIGHT;
//        }
//        return new Dimension(w, h);
//    }
//
//    @Override
//    public File getImageRootPath() {
//        File file = SourceGenerateFileStructure.getModulePictureFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleInfo.getModuleId());
//        return file;
//    }
//
//    @Override
//    public File getFileSelectorRootPath() {
//        File file = SourceGenerateFileStructure.getModuleNeedFilePutPath(CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName, moduleInfo.getModuleId());
//        return file;
//    }
//
//    @Override
//    public void setParam(AbstractOperatingContainerModel model) {
//        // TODO Auto-generated method stub
//        InitOpratingContainerModel theModel = (InitOpratingContainerModel) model;
//        if (this.initContainerState) {
//            super.setParam(theModel);
//            theModel.setInitList(initList);
//            theModel.setCurrentInitModel(currentInitModel);
//        } else {
//            theModel.setCodeSerialNumber(codeSerialNumber);
//            theModel.setPathFind(pathFind);
//            theModel.setHiddenState(false);
//            theModel.setCanBeDelOrNot(false);
//            theModel.setCurrentInitModel(null);
//        }
//        theModel.setThisCodeLocationInformation(this.thisCodeLocationInformation);
//        theModel.setInitContainerState(this.initContainerState);
//        theModel.setModuleId(getModuleId());
//        theModel.setSetButtonEnableState(setButtonEnableState);
//        theModel.setSettingContainerModel(setButton.getSettingContainer().getFormatStructureModel());
//    }
//
//    @Override
//    public InitOpratingContainerModel getFormatStructureModel() {
//        // TODO Auto-generated method stub
//        InitOpratingContainerModel model = new InitOpratingContainerModel();
//        setParam(model);
//        return model;
//    }
//
//    @Override
//    public String getPaneType() {
//        // TODO Auto-generated method stub
//        return MarkElementName.INIT_MARK;
//    }
//
//    @Override
//    public String getClassName() {
//        // TODO Auto-generated method stub
//        return moduleInfo.getClassName();
//    }
//
//    @Override
//    public String getModuleName() {
//        // TODO Auto-generated method stub
//        return moduleInfo.getModuleName();
//    }
//
//    @Override
//    public String getModuleId() {
//        return moduleInfo.getModuleId();
//    }
//
//    @Override
//    public int getAdditionalSerialNumber() {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public int getOrdinalInUserDB() {
//        return currentInitModel.getOperatingModel().getOrdinal();
//    }
//
//    public void delModuleOpratingContainer(String moduleId) {
//        // TODO Auto-generated method stub
//        if (this.moduleInfo.getModuleId().equals(moduleId)) {
//            delThis();
//        } else {
//            delModuleOpratingContainerFromComponent(moduleId);
//        }
//    }
//
//    @Override
//    public void delModuleOpratingContainerFromComponent(String moduleId) {
//        // TODO Auto-generated method stub
//        if (this.initContainerState) {
//            super.delModuleOpratingContainerFromComponent(moduleId);
//        }
//        if (setButtonEnableState) {
//            if (setButton != null) {
//                setButton.delModuleOpratingContainerFromComponent(moduleId);
//            }
//        }
//    }
//
//    @Override
//    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
//        // TODO Auto-generated method stub
//        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
//        if (this.initContainerState) {
//            opratingContainerList.addAll(super.getAllOpratingContainerListInThis());
//        }
//        if (setButtonEnableState) {
//            if (setButton != null) {
//                opratingContainerList.addAll(setButton.getAllOpratingContainer());
//            }
//        }
//        return opratingContainerList;
//    }
//
//    @Override
//    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
//        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
//        if (this.initContainerState) {
//            opratingContainerList.addAll(super.getAllOpratingContainer());
//        }
//        return opratingContainerList;
//    }
//
//    @Override
//    public AbstractCodeControlPane getCodeControlPane() {
//        return this.initOperatingContainerParam.getCodeControlPane();
//    }
//
//    @Override
//    public void collapseThis() {
//        // TODO Auto-generated method stub
//        if (this.initContainerState) {
//            super.collapseThis();
//        }
//        if (setButtonEnableState) {
//            if (setButton != null) {
//                setButton.collapseThis();
//            }
//        }
//    }
//
//    /**
//     * 设置面板：非必要 默认面板/默认滑动条：必要 隐藏控制按钮/抽屉：非必要 选择显示下拉框:非必要
//     */
//    @Override
//    protected void init(boolean expanded, boolean canBeDelOrNot, AbstractCodeControlPane codeControlPane) {
//        this.codeControlPane = codeControlPane;
//        setLayout(new InitOpratingLayout(this.initContainerState));
//        if (this.initContainerState == true) {//有初始化功能
//            int width = (int) (PROPOTION * SysUtil.SCREEN_SIZE.getWidth());
//            double hiddenButtonWidth = 0.7;
//
//            if (hiddenState == false) {
//                expanded = false;
//            }
//
//            deafaultOperatingPane = new DeafaultCommandOperatingPane(this);
//            deafaultOperatingJSP = new JScrollPane(deafaultOperatingPane);
//            deafaultOperatingJSP.getVerticalScrollBar().setUI(new MyScrollBarUI());
//            add(deafaultOperatingJSP);
//
//            codeHiddenControlButton = new CodeHiddenControlButton(expanded) {
//
//                @Override
//                public void doSomethingWhenMousePressed(boolean expanded) {
//                    super.doSomethingWhenMousePressed(expanded);
//                    if (hiddenState == true) {
//                        if (codeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
//                            hiddenOperatingPane.collapseThis();
//                        }
//                    }
//                }
//
//                @Override
//                public void doClick() {
//                    super.doClick();
//                    if (hiddenState == true) {
//                        if (codeHiddenControlButton.isExpanded() == true) {
//                            hiddenOperatingPane.collapseThis();
//                        }
//                    }
//                }
//            };
//            if (initList.size() == 1) {
//                codeHiddenControlButton.setSize(width, HIDDEN_BUTTON_HEIGHT);
//
//            } else if (initList.size() > 1) {
//                codeHiddenControlButton.setSize((int) (hiddenButtonWidth * width), HIDDEN_BUTTON_HEIGHT);
//            }
//            setHiddenButton(codeHiddenControlButton);
//            add(codeHiddenControlButton);
//
//            if (initList.size() > 1) {// 有还几条初始化方法可以选择
////				initChoiceCombobox = new InitChoiceCombobox(initList, this);
//                initChoiceMenu = new InitChoiceMenu(initList, this);
//                Dimension dd2 = new Dimension((int) ((1 - hiddenButtonWidth) * width), HIDDEN_BUTTON_HEIGHT);
//                initChoiceMenu.setPreferredSize(dd2);
//                initChoiceMenu.setMinimumSize(dd2);
//                initChoiceMenu.setMaximumSize(dd2);
//                add(initChoiceMenu);
//            }
//
//            hiddenOperatingPane = new HiddenCommandOperatingPane(this);
//            hiddenOperatingJSP = new JScrollPane(hiddenOperatingPane);
//            hiddenOperatingJSP.getVerticalScrollBar().setUI(new MyScrollBarUI());
//            Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenOperatingJSP);
//            this.setDrawer(drawer);
//            add(drawer);
//
//            if (hiddenState == false) {
//                codeHiddenControlButton.setEnabled(false);
//            }
//        } else {
//            hiddenOperatingPane = new HiddenCommandOperatingPane(this);
//            hiddenOperatingJSP = new JScrollPane(hiddenOperatingPane);
//
//            Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenOperatingJSP);
//            this.setDrawer(drawer);
//        }
//    }
//
//    /**
//     * 收起面板
//     */
//    public void packUpSetPanel() {
//        setButton.packUpPanel();
//    }
//
//    /**
//     * 展开面板
//     */
//    public void expandSetPanel() {
//        setButton.expandPanel();
//    }
//
//    @Override
//    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
//        if (setButtonEnableState) {
//            if (setButton != null) {
//                if (MarkElementName.MODULE_CONTROL.equals(currentOpratingContainer.getPathFind().getMarkType())) {
//                    if (getModuleId() != null) {
//                        if (getModuleId().equals(currentOpratingContainer.getFormatContainer().getModuleId())) {
//                            setButton.getSettingContainer().autoCollapse(currentOpratingContainer);
//                        } else {
//                            setButton.packUpPanel();
//                        }
//                    }
//
//                } else if (MarkElementName.SET_MARK.equals(currentOpratingContainer.getPathFind().getMarkType())) {
//                    if (getModuleId() != null) {
//                        if (getModuleId().equals(currentOpratingContainer.getFirstCommandOpratingContainer().getModuleId())) {
//                            setButton.getSettingContainer().autoCollapse(currentOpratingContainer);
//                        } else {
//                            setButton.packUpPanel();
//                        }
//                    }
//
//                } else if (MarkElementName.INIT_MARK.equals(currentOpratingContainer.getPathFind().getMarkType())) {
//                    setButton.packUpPanel();
//
//                } else {
//                    if (this.initContainerState) {
//                        super.autoCollapse(currentOpratingContainer);
//                    }
//                    setButton.packUpPanel();
//                }
//            }
//        }
//    }
//
//    @Override
//    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer() {
//        return this;
//    }
//
//    @Override
//    public AbstractFormatContainer getFormatContainer() {
//        return null;
//    }
//
//
//    @Override
//    public void functionNameSynchronousChange(int functionNameId) {
//        if (this.initContainerState) {
//            super.functionNameSynchronousChange(functionNameId);
//        }
//    }
//
//    @Override
//    public void functionNameSynchronousDelete(int functionNameId) {
//        if (this.initContainerState) {
//            super.functionNameSynchronousDelete(functionNameId);
//        }
//    }
//
//    @Override
//    public void variableSynchronousChange(int variableId) {
//        if (this.initContainerState) {
//            super.variableSynchronousChange(variableId);
//        }
//    }
//
//    @Override
//    public void variableSynchronousDelete(int variableId) {
//        if (this.initContainerState) {
//            super.variableSynchronousDelete(variableId);
//        }
//    }
//
//    @Override
//    public boolean isItTheSameLevel(OpratingContainerInterface opratingContainer) {
//        boolean flag = false;
//        if (this.initContainerState) {
//            flag = super.isItTheSameLevel(opratingContainer);
//        }
//        return flag;
//    }
//
//    @Override
//    public void setNoUserSelectionIsRequiredValue() {
//        if (this.initContainerState) {
//            super.setNoUserSelectionIsRequiredValue();
//        }
//    }
//
//    @Override
//    public InitMarkElement getCorrespondingMarkElement() {
//        return new InitMarkElement();
//    }
//
//    @Override
//    public OpratingContainerInterface getParentOpratingContainer() {
//        return null;
//    }
//
//}
