package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.component.FormatOpratinglHiddenButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.FormatTypePane;
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
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import lombok.Getter;

public abstract class AbstractFormatContainer extends Folder implements OpratingContainerInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7317869033070578256L;

//	protected int paneWidth=(int) (0.365 * SysUtil.screenSize.getWidth());

    protected static final int HIDDEN_HEIGHT = 30, MODULE_CONTROL_JSP_HEIGHT = 200;
    protected FormatOperatingPane formatOperatingPane;
    protected FormatTypePane formatTypePane = null;
    protected FormatOpratingContainerParam formatOpratingContainerParam;

    @Getter
    protected AbstractFormatControlPane formatControlPane;

    @Getter
    protected PathFind pathFind = null;
    /**
     * 对应代码文件名称
     */
    protected String currentCodeFileName = "";
    protected boolean formatState = false;
    private JScrollPane formatOperatingJSP;
    private FormatOpratinglHiddenButton formatOpratinglHiddenButton;
    private CustomVariableHolder customVariableHolder = null;
    private CustomFunctionNameHolder customFunctionNameHolder = null;

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

        this.formatState = model.isFormatState();

        this.formatOpratingContainerParam = formatOpratingContainerParam;

        this.formatOpratingContainerParam.setThisCustomFunctionNameHolder(customFunctionNameHolder);
        this.formatOpratingContainerParam.setThisCustomVariableHolder(customVariableHolder);

        this.formatOpratingContainerParam.setThisOpratingContainer(this);
        this.formatOpratingContainerParam.setFormatContainer(this);
        formatOperatingPane.restoreContent(model.getDeafaultPaneElementList(), this.formatOpratingContainerParam);
    }

    protected void init(boolean formatState, boolean expanded, String formatOpratingTitle, double propotion) {
        this.formatState = formatState;

        int paneWidth = (int) (propotion * SysUtil.SCREEN_SIZE.getWidth());
        setLayout(new ModuleControlOpratingLayout());

        formatOpratinglHiddenButton = new FormatOpratinglHiddenButton(expanded, formatOpratingTitle) {
            @Override
            public void doSomethingWhenMousePressed(boolean expanded) {
                super.doSomethingWhenMousePressed(expanded);
                if (formatTypePane != null) {
                    formatTypePane.packUpPanel();
                }
                if (expanded == false) {//收起面板时，收起面板的所有组件
                    formatOperatingPane.collapseThis();
                }

            }

//            @Override
//            public void doClick() {
//                super.doClick();
//                if (formatOpratinglHiddenButton.isExpanded() == true) {
//                    formatOperatingPane.collapseThis();
//                }
//            }
        };
        formatOpratinglHiddenButton.setSize(paneWidth, HIDDEN_HEIGHT);
        setHiddenButton(formatOpratinglHiddenButton);
        add(formatOpratinglHiddenButton);

        formatOperatingPane = new FormatOperatingPane(this);
        formatOperatingJSP = new JScrollPane(formatOperatingPane);
//        SysUtil.scrollToTop(formatOperatingJSP);

        Drawer drawer = new Drawer(expanded ? 1 : 0, formatOperatingJSP);
        this.setDrawer(drawer);
        add(drawer);

        if (formatState == true) {
            formatTypePane = generateSettingsButton();
            add(formatTypePane);
        }
    }

    protected void setAppropriateSize() {
        int hiddenHeght = 20 + formatOperatingPane.getLineNum() * 30;
        if (hiddenHeght > MODULE_CONTROL_JSP_HEIGHT) {
            formatOperatingJSP.setSize(formatOpratinglHiddenButton.getWidth(), MODULE_CONTROL_JSP_HEIGHT);
        } else {
            formatOperatingJSP.setSize(formatOpratinglHiddenButton.getWidth(), hiddenHeght);
        }
    }

    /**
     * 重新展开设置面板
     */
    public void reExpandSetPanel() {
        if (formatState == true && formatTypePane != null) {
            if (formatTypePane.isSelected() == true) {
                formatTypePane.packUpPanel();
                formatTypePane.expandPanel();
            }
        }
    }

    /**
     * 初始化构造时调用的生成格式按钮的方法
     *
     * @return
     */
    public FormatTypePane generateSettingsButton() {
        return new FormatTypePane();
    }


    @Override
    public Dimension getRequiredDimension() {
        int w = getDrawer().getContentWidth(),
                h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + HIDDEN_HEIGHT;
        return new Dimension(w, h);
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
        boolean flag ;
        for (CodeShowPane codeShowPane : codePaneList) {
            flag = codeShowPane.updateValueInFormat(updateCodeEditParamForFormat);
            if (flag){
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
        theModel.setFormatState(formatState);
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
//		if (formatState == true) {
//			formatTypePane.packUpPanel();
//		}
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
        if (formatState == true) {
            if (formatTypePane != null) {
                formatTypePane.packUpPanel();
            }
        }
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
     * 获取打开设置的按钮
     *
     * @return
     */
    public FormatTypePane getSetTypeButton() {
        return formatTypePane;
    }

    /**
     * 负责布局面板组件
     */
    class ModuleControlOpratingLayout implements LayoutManager {

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

            int hiddenButtonWeight = parent.getWidth(), drawerWeight = parent.getWidth(), h = parent.getHeight(),
                    dHeight = HIDDEN_HEIGHT;
            if (formatState == true) {
                xCoordinates = FormatTypePane.DRAFAULT_BUTTON_WIDTH;
                hiddenButtonWeight = parent.getWidth() - FormatTypePane.DRAFAULT_BUTTON_WIDTH;
                formatTypePane.setBounds(0, 0, FormatTypePane.DRAFAULT_BUTTON_WIDTH, HIDDEN_HEIGHT);
            }
            if (getHiddenButton() != null) {
                getHiddenButton().setBounds(xCoordinates, 0, hiddenButtonWeight, HIDDEN_HEIGHT);
            }
            // 抽屉只显示抽出的比例
            getDrawer().setBounds(0, dHeight, drawerWeight, h - dHeight);
        }
    }

}
