package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.component.CodeHiddenControlButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.FormatOpratinglHiddenButton;
import com.lazycoder.uicodegeneration.component.operation.container.pane.FormatOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FormatOpratingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractFormatOperatingContainerModel;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public abstract class AbstractFormatContainer extends Folder implements OpratingContainerInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7317869033070578256L;

    public static final int  MaxSetPaneHight = (int) (0.5 * SysUtil.SCREEN_SIZE.height);

//	protected int paneWidth=(int) (0.365 * SysUtil.screenSize.getWidth());

    protected static final int HIDDEN_HEIGHT = 30, MODULE_CONTROL_JSP_HEIGHT = 200;
    protected FormatOperatingPane formatOperatingPane;
    protected FormatOpratingContainerParam formatOpratingContainerParam;

    @Getter
    protected AbstractFormatControlPane formatControlPane;

    @Getter
    protected PathFind pathFind = null;
    /**
     * 对应代码文件名称
     */
    protected String currentCodeFileName = "";

    /**
     * 该格式容器组件，有没有对应的设置功能的状态
     */
    protected boolean setState = false;
    protected JScrollPane formatOperatingJSP = null;
    private FormatOpratinglHiddenButton formatOpratinglHiddenButton = null;
    private CustomVariableHolder customVariableHolder = null;
    private CustomFunctionNameHolder customFunctionNameHolder = null;

    /**
     * 设置类面板的伸展收缩按钮
     */
    @Getter
    private CodeHiddenControlButton setCodeHiddenControlButton = null;

    /**
     * 放应用程序的抽屉
     */
    @Getter
    private Drawer setDrawer = null;

    protected JScrollPane setScrollPane = null;

    public AbstractFormatContainer() {
        // TODO Auto-generated constructor stub
        super();
        customVariableHolder = new CustomVariableHolder();
        customFunctionNameHolder = new CustomFunctionNameHolder();
        // init(true, true, formatOpratingTitle, propotion);
    }

    /**
     * 生成控件内容
     *
     * @param formatOpratingContainerParam
     */
    protected void generateOperationalContent(FormatOpratingContainerParam formatOpratingContainerParam) {
        this.formatOpratingContainerParam = formatOpratingContainerParam;
        formatControlPane = formatOpratingContainerParam.getFormatControlPane();
        pathFind = formatOpratingContainerParam.getParentPathFind();

        this.formatOpratingContainerParam.setThisCustomFunctionNameHolder(customFunctionNameHolder);
        this.formatOpratingContainerParam.setThisCustomVariableHolder(customVariableHolder);

        this.formatOpratingContainerParam.setThisOpratingContainer(this);
        this.formatOpratingContainerParam.setFormatContainer(this);
        formatOperatingPane.generateOperationalContent(this.formatOpratingContainerParam);
    }


    /**
     * 还原控件内容
     *
     * @param model
     * @param formatOpratingContainerParam
     */
    protected void restoreContent(AbstractFormatOperatingContainerModel model,
                                  FormatOpratingContainerParam formatOpratingContainerParam) {
        formatControlPane = formatOpratingContainerParam.getFormatControlPane();

        this.currentCodeFileName = model.getCurrentCodeFileName();
        pathFind = formatOpratingContainerParam.getParentPathFind();

        this.setState = model.isFormatState();

        this.formatOpratingContainerParam = formatOpratingContainerParam;

        this.formatOpratingContainerParam.setThisCustomFunctionNameHolder(customFunctionNameHolder);
        this.formatOpratingContainerParam.setThisCustomVariableHolder(customVariableHolder);

        this.formatOpratingContainerParam.setThisOpratingContainer(this);
        this.formatOpratingContainerParam.setFormatContainer(this);
        formatOperatingPane.restoreContent(model.getDeafaultPaneElementList(), this.formatOpratingContainerParam);
    }

    protected void init(boolean expanded, double propotion) {
        int paneWidth = (int) (propotion * SysUtil.SCREEN_SIZE.getWidth());
        setUILayout();

        formatOpratinglHiddenButton = formatOpratinglHiddenButtonInit();
//        new FormatOpratinglHiddenButton(expanded, formatOpratingTitle) {
//            @Override
//            public void doSomethingWhenMousePressed(boolean expanded) {
//                super.doSomethingWhenMousePressed(expanded);
//                if (formatTypePane != null) {
//                    formatTypePane.packUpPanel();
//                }
//                if (expanded == false) {//收起面板时，收起面板的所有组件
//                    formatOperatingPane.collapseThis();
//                }
//
//            }
//
////            @Override
////            public void doClick() {
////                super.doClick();
////                if (formatOpratinglHiddenButton.isExpanded() == true) {
////                    formatOperatingPane.collapseThis();
////                }
////            }
//        };
        formatOpratinglHiddenButton.setSize(paneWidth, HIDDEN_HEIGHT);
        setHiddenButton(formatOpratinglHiddenButton);
        add(formatOpratinglHiddenButton);

        formatOperatingPane = new FormatOperatingPane(this);
        formatOperatingJSP = new JScrollPane(formatOperatingPane);
//        SysUtil.scrollToTop(formatOperatingJSP);

        Drawer drawer = new Drawer(expanded ? 1 : 0, formatOperatingJSP);
        this.setDrawer(drawer);
        add(drawer);

//        if (formatState == true) {
//            formatTypePane = generateSettingsButton();
//            add(formatTypePane);
//        }
    }


    protected void setInit(boolean setState, double propotion) {
        this.setState = setState;
        int paneWidth = (int) (propotion * SysUtil.SCREEN_SIZE.getWidth());

        setCodeHiddenControlButton = setCodeHiddenControlButtonInit();
//                    (true) {
//
//                @Override
//                public void doSomethingWhenMousePressed(boolean expanded) {
//                    super.doSomethingWhenMousePressed(expanded);
//                    if (mainSetCodeHiddenControlButton.isExpanded() == false && mainSetTypeFolder != null) {//收起面板时，收起隐藏面板的所有组件
//                        mainSetTypeFolder.collapseThis();
//                    }
//                    if (formatState == true) {
//                        if (mainSetCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
//                            formatControlPane.collapseThis();
//                        }
//                    }
//                }
//
//                @Override
//                public void doClick() {
//                    super.doClick();
//                    if (mainSetCodeHiddenControlButton.isExpanded() == true && mainSetTypeFolder != null) {
//                        mainSetTypeFolder.collapseThis();
//                    }
//                    if (formatState == true) {
//                        if (mainSetCodeHiddenControlButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
//                            formatControlPane.collapseThis();
//                        }
//                    }
//                }
//            };
        setCodeHiddenControlButton.setSize(paneWidth, HIDDEN_HEIGHT);
        add(setCodeHiddenControlButton);

        setScrollPane = new JScrollPane();
        setScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setDrawer = new Drawer(1, setScrollPane);
        add(setDrawer);
    }

    /**
     * 格式容器生成隐藏按钮的方法
     *
     * @return
     */
    protected FormatOpratinglHiddenButton formatOpratinglHiddenButtonInit() {
        return new FormatOpratinglHiddenButton(true,"格式");
    }

    /**
     * 生成按钮的方法
     *
     * @return
     */
    protected CodeHiddenControlButton setCodeHiddenControlButtonInit() {
        return new CodeHiddenControlButton(true);
    }


    protected void setUILayout() {
        setLayout(new FormatContainerOpratingLayout());
    }


    protected void setAppropriateSize() {
        int hiddenHeght = 20 + formatOperatingPane.getLineNum() * 30;
        if (hiddenHeght > MODULE_CONTROL_JSP_HEIGHT) {
            formatOperatingJSP.setSize(formatOpratinglHiddenButton.getWidth(), MODULE_CONTROL_JSP_HEIGHT);
        } else {
            formatOperatingJSP.setSize(formatOpratinglHiddenButton.getWidth(), hiddenHeght);
        }
        if (setState){//有设置功能
            setScrollPane.setSize(formatOpratinglHiddenButton.getWidth(), MaxSetPaneHight);
        }
    }

    /**
     * 初始化构造时调用的生成格式按钮的方法
     *
     * @return
     */
//    public FormatTypePane generateSettingsButton() {
//        return new FormatTypePane();
//    }
    @Override
    public Dimension getRequiredDimension() {
        int w = getDrawer().getContentWidth(),
                h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + HIDDEN_HEIGHT;
        return new Dimension(w, h);
    }

    @Override
    public int getRequiredHeight() {
        return (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + HIDDEN_HEIGHT;
    }

    @Override
    public void updateValue(CodeGenerationComponentInterface codeGenerationComponent, int paneType, Object updateParam) {
//        PathFindCell pathFindCell = new PathFindCell(getCodeSerialNumber(),
//                codeGenerationComponent.getControlElement(), paneType);
        PathFind pathFindTemp = new PathFind(codeGenerationComponent.getPathFind());

        UpdateCodeEditParamForFormat updateCodeEditParamForFormat = new UpdateCodeEditParamForFormat();
        updateCodeEditParamForFormat.setCodeSerialNumber(getCodeSerialNumber());
        updateCodeEditParamForFormat.setTrulyPathFind(pathFindTemp);
        updateCodeEditParamForFormat.setOpratingLabel(codeGenerationComponent.getControlElement());
        updateCodeEditParamForFormat.setUpdateParam(updateParam);

        ArrayList<CodeShowPane> codePaneList = getCodePaneList();
        boolean flag;
        for (CodeShowPane codeShowPane : codePaneList) {
            flag = codeShowPane.updateValueInFormat(updateCodeEditParamForFormat);
            if (flag) {
                CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
            }
        }
    }

    /**
     * 检查要添加的功能是否符合添加到格式组件，是否可以添加到这里
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    public boolean checkWhetherItMatches(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        boolean flag = false;
        ArrayList<CodeShowPane> list = getCodePaneList();
        for (CodeShowPane codeShowPane : list) {
            if (codeShowPane.checkWhetherItMatchesForFormat(addCodeEditParamForFormat)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取需要添加的代码面板列表(继承该类都要重写)
     *
     * @return
     */
    public abstract ArrayList<CodeShowPane> getCodePaneList();

    public abstract ArrayList<CodeFormatFlagParam> getCodePaneRelatedInfoList();

    public String getCurrentCodeFileName() {
        return currentCodeFileName;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        AbstractFormatOperatingContainerModel theModel = (AbstractFormatOperatingContainerModel) model;
        theModel.setCurrentCodeFileName(currentCodeFileName);
        theModel.setDeafaultPaneElementList(formatOperatingPane.getComponentList());
        theModel.setFormatState(setState);
        theModel.setCustomVariableHolder(customVariableHolder);
        theModel.setCustomFunctionNameHolder(customFunctionNameHolder);
    }

    @Override
    public abstract File getImageRootPath();

    @Override
    public abstract File getFileSelectorRootPath();

    @Override
    public abstract AbstractFormatOperatingContainerModel getFormatStructureModel();

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        opratingContainerList.addAll(formatOperatingPane.getAllOpratingContainer());
        return opratingContainerList;
    }

    @Override
    public int getCodeSerialNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        formatOperatingPane.collapseThis();
    }


    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        // TODO Auto-generated method stub
        if (formatOpratinglHiddenButton != null) {
            if (isItTheSameLevel(currentOpratingContainer) == false) {
                formatOperatingPane.collapseThis();
                packUpHiddenPanel();
            }
        }
    }

    @Override
    public boolean isItTheSameLevel(OpratingContainerInterface opratingContainer) {
        boolean flag = false;
        if (opratingContainer != null) {
            flag = PathFind.isTheComparisonHierarchical(pathFind,
                    opratingContainer.getPathFind());
            if (flag == true) {
                if (pathFind.getPathList().size() == opratingContainer.getPathFind().getPathList().size()) {
                    if (opratingContainer.getCodeSerialNumber() != getCodeSerialNumber()) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        formatOperatingPane.delModuleOpratingContainerFromComponent(moduleId);
    }

    @Override
    public void delThis() {
        formatOperatingPane.delThis();
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = getAllOpratingContainerListInThis();
        opratingContainerList.add(this);
        return opratingContainerList;
    }

    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        formatOperatingPane.functionNameSynchronousChange(functionNameId);
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {
        formatOperatingPane.functionNameSynchronousDelete(functionNameId);
    }

    @Override
    public void setNoUserSelectionIsRequiredValue() {
        formatOperatingPane.setNoUserSelectionIsRequiredValue();
    }

    @Override
    public void showSelectedValue() {
        formatOperatingPane.showSelectedValue();
    }

    @Override
    public void variableSynchronousChange(int variableId) {
        formatOperatingPane.variableSynchronousChange(variableId);
    }

    @Override
    public void variableSynchronousDelete(int variableId) {
        formatOperatingPane.variableSynchronousDelete(variableId);
    }

    @Override
    public CustomFunctionNameHolder getThisCustomMethodNameHolder() {
        return customFunctionNameHolder;
    }

    @Override
    public CustomVariableHolder getThisCustomVariableHolder() {
        return customVariableHolder;
    }

    /**
     * 负责布局面板组件
     */
    class FormatContainerOpratingLayout implements LayoutManager {

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
            int w = parent.getWidth(),
                    h = parent.getHeight(),
                    setCodeHiddenControlButton_y = 0,
                    dHeight = HIDDEN_HEIGHT;

            //查一下当前这个格式容器有没有对应的设置功能可以添加，如果有，获取对应放置设置功能的面板的高度，没有就记为0
            int setPaneHightTemp = setState && getSetDrawer() != null ? getSetDrawer().getContentHeight() : 0;
            setPaneHightTemp = setCodeHiddenControlButton != null && setCodeHiddenControlButton.isExpanded() ? setPaneHightTemp : 0;

            if (getHiddenButton() != null) {//设置格式容器的隐藏按钮的位置，顺便把隐藏按钮的高度记在 设置面板的隐藏控制按钮的高里
                getHiddenButton().setBounds(xCoordinates, 0, w, HIDDEN_HEIGHT);
                setCodeHiddenControlButton_y = setCodeHiddenControlButton_y + HIDDEN_HEIGHT;
            }

            //计算动态的默认面板的高度，用总的减去隐藏隐藏按钮高度，如果有设置功能，再减去对应隐藏隐藏按钮高度和上面计算得出的设置功能的面板的高度
            int dynamicDeafaultDrawerHight = h - HIDDEN_HEIGHT;
            if (setState) {//有模块设置
                dynamicDeafaultDrawerHight = dynamicDeafaultDrawerHight - HIDDEN_HEIGHT - setPaneHightTemp;
            }
            //把这个动态的默认面板的高度 也加在 设置面板的隐藏控制按钮的高 里面
            setCodeHiddenControlButton_y = setCodeHiddenControlButton_y + dynamicDeafaultDrawerHight;

            // 抽屉只显示抽出的比例
            getDrawer().setBounds(0, dHeight, w, dynamicDeafaultDrawerHight);

            if (setState) {//有模块设置
                if (setCodeHiddenControlButton != null) {
                    setCodeHiddenControlButton.setBounds(xCoordinates, setCodeHiddenControlButton_y, w, HIDDEN_HEIGHT);
                }

                //计算设置面板高度时，默认面板相对固定，先计算默认面板的高
                int deafaultPaneHightTemp = getDrawer() != null ? getDrawer().getContentHeight() : 0;
                deafaultPaneHightTemp = getHiddenButton() != null && getHiddenButton().isExpanded() ? deafaultPaneHightTemp : 0;

                //计算动态设置面板的高，总高 - 隐藏按钮 - 默认面板的高 - 隐藏按钮（设置）
                int dynamiSetDrawerHight = h - HIDDEN_HEIGHT;
                dynamiSetDrawerHight = dynamiSetDrawerHight - deafaultPaneHightTemp - HIDDEN_HEIGHT;

                setDrawer.setBounds(0,
                        setCodeHiddenControlButton_y + HIDDEN_HEIGHT,
                        w, dynamiSetDrawerHight);
            }
        }
    }

}
