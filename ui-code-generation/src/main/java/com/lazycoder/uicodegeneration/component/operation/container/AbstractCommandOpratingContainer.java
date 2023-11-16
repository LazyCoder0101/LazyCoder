package com.lazycoder.uicodegeneration.component.operation.container;


import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.ContainerGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.component.CloseButton;
import com.lazycoder.uicodegeneration.component.operation.container.component.HiddenAndInfoButton;
import com.lazycoder.uicodegeneration.component.operation.container.pane.DeafaultCommandOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.pane.HiddenCommandOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.CommandContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.container.AbstractCommandOperatingContainerModel;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import lombok.Getter;

public abstract class AbstractCommandOpratingContainer extends Folder implements OpratingContainerInterface {

    //
    protected static final int DEFAULT_SHOW_HEIGHT = 102, DEFAULT_SHOW_MIN_HEIGHT = 62, HIDDEN_HEIGHT = 20;

//    private static final BEScrollPaneUI scrollPaneUI = new BEScrollPaneUI();

    /**
     * 宽度比例
     */
    protected static final double PROPOTION = 0.35;
    /**
     *
     */
    private static final long serialVersionUID = 8439956274638427361L;

    protected JScrollPane deafaultOperatingJSP = null, hiddenOperatingJSP;

    protected DeafaultCommandOperatingPane deafaultOperatingPane = null;

    protected HiddenCommandOperatingPane hiddenOperatingPane = null;

    protected CloseButton closeButton = null;

    protected HiddenAndInfoButton hiddenAndInfoButton = null;

    /**
     * 放置该命令容器的代码面板
     */
    protected AbstractCodeControlPane codeControlPane = null;
    /**
     * 隐藏使用状态
     */
    protected boolean hiddenState = true;
    /**
     * 代码序号
     */
    protected int codeSerialNumber;
    /**
     * 父面板路径参数
     */
    protected PathFind pathFind;
    protected CustomVariableHolder customVariableHolder;
    protected CustomFunctionNameHolder customFunctionNameHolder;

    /**
     * 该类用于存储本实例对应的代码所在的位置信息
     */
    @Getter
    protected ContainerGenerateCodeResponseParam thisCodeLocationInformation = new ContainerGenerateCodeResponseParam();

//    private RoundBorder deafaultOperatingPaneBorder=new RoundBorder(),
//            hiddenOperatingPaneBorder=new RoundBorder();

    public AbstractCommandOpratingContainer() {
        super();
        customVariableHolder = new CustomVariableHolder();
        customFunctionNameHolder = new CustomFunctionNameHolder();

        // TODO Auto-generated constructor stub
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
        }
    };

    protected ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (closeButton.isEnabled() == true) {
                delThis();
            }
        }
    };

    protected void init(boolean expanded, boolean canBeDelOrNot, AbstractCodeControlPane codeControlPane) {
        setLayout(new opratingLayout());
        this.codeControlPane = codeControlPane;

        deafaultOperatingPane = new DeafaultCommandOperatingPane(this);
        deafaultOperatingJSP = new JScrollPane(deafaultOperatingPane);
        add(deafaultOperatingJSP);

        closeButton = new CloseButton();
        add(closeButton);
        closeButton.setEnabled(canBeDelOrNot);
        closeButton.addActionListener(listener);
        closeButton.addMouseListener(mouseAdapter);

        if (hiddenState == true) {
            hiddenAndInfoButton = new HiddenAndInfoButton(this.hiddenState, expanded) {

                @Override
                public void doSomethingWhenMousePressed(boolean expanded) {
                    super.doSomethingWhenMousePressed(expanded);
                    if (hiddenState == true) {
                        if (hiddenAndInfoButton.isExpanded() == false) {//收起面板时，收起隐藏面板的所有组件
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                }

                @Override
                public void doClick() {
                    super.doClick();
                    if (hiddenState == true) {
                        if (hiddenAndInfoButton.isExpanded() == true) {
                            hiddenOperatingPane.collapseThis();
                        }
                    }
                }
            };
            add(hiddenAndInfoButton);
            setHiddenButton(hiddenAndInfoButton);
        } else {
            expanded = false;
            hiddenAndInfoButton = new HiddenAndInfoButton(this.hiddenState, expanded);
            add(hiddenAndInfoButton);
//            moreButton = new MoreButton();
//            add(moreButton);
        }

        hiddenOperatingPane = new HiddenCommandOperatingPane(this);
        hiddenOperatingJSP = new JScrollPane(hiddenOperatingPane);

        Drawer drawer = new Drawer(expanded ? 1 : 0, hiddenOperatingJSP);
        this.setDrawer(drawer);
        if (hiddenState == true) {
            add(drawer);
        }
    }

    protected void restoreInit(AbstractCommandOperatingContainerModel commandOperatingContainerModel) {
        this.thisCodeLocationInformation = commandOperatingContainerModel.getThisCodeLocationInformation();
        this.pathFind = commandOperatingContainerModel.getPathFind();
        this.codeSerialNumber = commandOperatingContainerModel.getCodeSerialNumber();
        this.hiddenState = commandOperatingContainerModel.isHiddenState();
    }

    protected void restorePaneContent(AbstractCommandOperatingContainerModel commandOperatingContainerModel,
                                      GeneralContainerComponentParam codeGenerationalOpratingContainerParam) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.restoreContent(commandOperatingContainerModel.getDeafaultPaneElementList(),
                    codeGenerationalOpratingContainerParam);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.restoreContent(commandOperatingContainerModel.getHiddenPaneElementList(),
                        codeGenerationalOpratingContainerParam);
            }
        }
    }

    protected void setThisToolTipText(String functionShowText) {
        if (hiddenAndInfoButton != null) {
            HTMLText htmlText = new HTMLText();
            HtmlPar par1 = new HtmlPar();
            par1.addText(functionShowText, true);
            htmlText.addPar(par1);
            HtmlPar par2 = new HtmlPar();
            par2.addText("序号：", false);
            par2.addText(codeSerialNumber + "", true);
            htmlText.addPar(par2);
            hiddenAndInfoButton.setToolTipText(htmlText.getHtmlContent());
        }
    }

    @Override
    public Dimension getRequiredDimension() {
        int w = deafaultOperatingJSP.getPreferredSize().width, //h = DEFAULT_SHOW_HEIGHT;
//        if (hiddenState == true) {
//            h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + defaultShowHeight + hiddenHeight;
//        } else {
//            h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + defaultShowHeight;
//        }
                h = (int) (getDrawer().getContentHeight() * getDrawer().getRatio()) + deafaultOperatingJSP.getHeight();
        return new Dimension(w, h);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    /**
     * 设置合适的大小
     *
     * @param maxPropotion 允许最大尺寸对应的屏幕比例
     */
    protected void setAppropriateSize(double maxPropotion) {
        int containerMaxWidth = (int) (maxPropotion * SysUtil.SCREEN_SIZE.getWidth());
        Dimension deafaultOperatingPaneDimension = deafaultOperatingPane.getPreferredSize();
        if (deafaultOperatingPaneDimension.getWidth() > containerMaxWidth) {
            deafaultOperatingPaneDimension.width = containerMaxWidth;
        }
        int width = deafaultOperatingPaneDimension.width;

        if (hiddenState) {
            Dimension hiddenOperatingPaneDimension = hiddenOperatingPane.getPreferredSize();
            if (hiddenOperatingPaneDimension.getWidth() > containerMaxWidth) {
                hiddenOperatingPaneDimension.width = containerMaxWidth;
            }
            if (hiddenOperatingPaneDimension.width > deafaultOperatingPaneDimension.width) {
                width = hiddenOperatingPaneDimension.width;
            }

            hiddenOperatingPaneDimension.width = width;
            if (hiddenOperatingPaneDimension.getHeight() > 100) {
                hiddenOperatingPaneDimension.height = 100;
            }
            hiddenOperatingJSP.setPreferredSize(hiddenOperatingPaneDimension);
            hiddenOperatingJSP.setMaximumSize(hiddenOperatingPaneDimension);
            hiddenOperatingJSP.setMinimumSize(hiddenOperatingPaneDimension);
            hiddenOperatingJSP.setSize(hiddenOperatingPaneDimension);
        }
        deafaultOperatingPaneDimension.width = width;
        if (deafaultOperatingPaneDimension.getHeight() > DEFAULT_SHOW_HEIGHT) {
            deafaultOperatingPaneDimension.height = DEFAULT_SHOW_HEIGHT;
        }
        if (deafaultOperatingPaneDimension.getHeight() < DEFAULT_SHOW_MIN_HEIGHT) {
            deafaultOperatingPaneDimension.height = DEFAULT_SHOW_MIN_HEIGHT;
        }
        deafaultOperatingJSP.setPreferredSize(deafaultOperatingPaneDimension);
        deafaultOperatingJSP.setMaximumSize(deafaultOperatingPaneDimension);
        deafaultOperatingJSP.setMinimumSize(deafaultOperatingPaneDimension);
        deafaultOperatingJSP.setSize(deafaultOperatingPaneDimension);

        if (hiddenState == false) {
            hiddenAndInfoButton.setSize((int) hiddenAndInfoButton.getPreferredSize().getWidth(), (int) hiddenAndInfoButton.getPreferredSize().getHeight());
        }
    }

//    protected void setAppropriateSize(double maxPropotion) {
//        int containerMaxWidth = (int) (maxPropotion * SysUtil.SCREEN_SIZE.getWidth());
//        int defaultPaneWidth = deafaultOperatingPane.getMaxLineWidth(DeafaultCommandOperatingPane.PX), hiddenPaneWidth = 0, width = 0;
////                defaultPaneHeight = deafaultOperatingPane.getShowLineNum(containerMaxWidth, 16) * 30 + 10;
////                defaultPaneHeight = deafaultOperatingPane.getLineNum() * 30 + 10;
//        if (hiddenState == true) {
//            hiddenPaneWidth = hiddenOperatingPane.getMaxLineWidth(HiddenCommandOperatingPane.PX);
//            if (defaultPaneWidth > hiddenPaneWidth) {
//                width = defaultPaneWidth + 20;
//            } else {
//                width = hiddenPaneWidth + 20;
//            }
//        } else {
//            width = defaultPaneWidth + 20;
//        }
//
//        if (width > containerMaxWidth) {
//            width = containerMaxWidth;
//        }
//
////        if (defaultPaneHeight >= DEFAULT_SHOW_HEIGHT) {
////            defaultPaneHeight = DEFAULT_SHOW_HEIGHT;
////            deafaultOperatingPane.setToolTipText("默认值高度：" + defaultPaneHeight);
////        }
//
//        int finalDeafaultHeight = 0;//deafaultOperatingPane.getShowLineNum(width-20, 16)* 30 + 10;
//        if (width < containerMaxWidth) {
//            finalDeafaultHeight = deafaultOperatingPane.getShowLineNum(defaultPaneWidth, DeafaultCommandOperatingPane.PX) * 30 + 10;
//        } else {
//            finalDeafaultHeight = deafaultOperatingPane.getShowLineNum(width, DeafaultCommandOperatingPane.PX) * 30 + 10;
//        }
//        deafaultOperatingPane.setToolTipText("计算行数：" + deafaultOperatingPane.getShowLineNum(width, DeafaultCommandOperatingPane.PX) +
//                "\t+当前值：" + width + "\t最长一行的宽度：" + defaultPaneWidth + "\t计算高度：" + finalDeafaultHeight);
//        if (finalDeafaultHeight > DEFAULT_SHOW_HEIGHT) {
//            finalDeafaultHeight = DEFAULT_SHOW_HEIGHT;
//        }
//        if (finalDeafaultHeight < DEFAULT_SHOW_MIN_HEIGHT) {
//            finalDeafaultHeight = DEFAULT_SHOW_MIN_HEIGHT;
//        }
//        Dimension dd1 = new Dimension(width, finalDeafaultHeight);
//        deafaultOperatingJSP.setPreferredSize(dd1);
//        deafaultOperatingJSP.setMaximumSize(dd1);
//        deafaultOperatingJSP.setMinimumSize(dd1);
//        deafaultOperatingJSP.setSize(dd1);
//
//        if (hiddenState == true) {
////            int hiddenPaneHeight =hiddenOperatingPane.getLineNum() * 30;
//////            int hiddenPaneHeight = hiddenOperatingPane.getShowLineNum(containerMaxWidth, 14) * 30;//hiddenOperatingPane.getLineNum() * 30;
////            if (hiddenPaneHeight > 100) {
////                hiddenPaneHeight = 100;
////            }
////            codeHiddenControlButton.setSize(width, hiddenHeight);
//            int finalHiddenHeight = 0;
//            if (width < containerMaxWidth) {
//                finalHiddenHeight = hiddenOperatingPane.getShowLineNum(hiddenPaneWidth, HiddenCommandOperatingPane.PX) * 30 + 10;
//            } else {
//                finalHiddenHeight = hiddenOperatingPane.getShowLineNum(width, HiddenCommandOperatingPane.PX) * 30 + 10;
//            }
//            hiddenOperatingPane.setToolTipText("计算行数：" + hiddenOperatingPane.getShowLineNum(width, HiddenCommandOperatingPane.PX) +
//                    "\t+当前值：" + width + "\t最长一行的宽度：" + hiddenPaneWidth + "\t计算高度：" + finalHiddenHeight);
//
//            if (finalHiddenHeight > 100) {
//                finalHiddenHeight = 100;
//            }
//
//            Dimension dd2 = new Dimension(width, finalHiddenHeight);
//            hiddenPaneJSP.setPreferredSize(dd2);
//            hiddenPaneJSP.setMaximumSize(dd2);
//            hiddenPaneJSP.setMinimumSize(dd2);
//            hiddenPaneJSP.setSize(dd2);
//
//        } else {
//            hiddenAndInfoButton.setSize((int) hiddenAndInfoButton.getPreferredSize().getWidth(), (int) hiddenAndInfoButton.getPreferredSize().getHeight());
//        }
//    }

    /**
     * 该命令组件在录入用户数据源的序号
     *
     * @return
     */
    public abstract int getOrdinalInUserDB();

    /**
     * 获取默认代码面板
     *
     * @return
     */
    protected abstract CodeShowPane getDeafaultCodePane();

    /**
     * 设置组件参数
     *
     * @param controlStatementFormat 生成面板组件和文字的参数
     * @return
     */
    protected CommandContainerComponentParam getContainerComponentParam(String controlStatementFormat) {
        CommandContainerComponentParam codeGenerationalOpratingContainerParam = new CommandContainerComponentParam();
        codeGenerationalOpratingContainerParam.setThisCustomFunctionNameHolder(customFunctionNameHolder);
        codeGenerationalOpratingContainerParam.setThisCustomVariableHolder(customVariableHolder);
        return codeGenerationalOpratingContainerParam;
    }

    @Override
    public abstract File getImageRootPath();

    @Override
    public abstract File getFileSelectorRootPath();

    @Override
    public CustomVariableHolder getThisCustomVariableHolder() {
        return customVariableHolder;
    }

    @Override
    public void setParam(AbstractOperatingContainerModel model) {
        // TODO Auto-generated method stub
        AbstractCommandOperatingContainerModel theModel = (AbstractCommandOperatingContainerModel) model;
        theModel.setCodeSerialNumber(codeSerialNumber);
        theModel.setHiddenState(hiddenState);
        theModel.setPathFind(pathFind);
        theModel.setThisCodeLocationInformation(this.thisCodeLocationInformation);
        if (closeButton != null) {
            theModel.setCanBeDelOrNot(closeButton.isEnabled());
        }
        theModel.setDeafaultPaneElementList(deafaultOperatingPane.getComponentList());
        theModel.setHiddenPaneElementList(hiddenOperatingPane.getComponentList());
    }

    @Override
    public abstract AbstractCommandOperatingContainerModel getFormatStructureModel();

    @Override
    public int getCodeSerialNumber() {
        return codeSerialNumber;
    }


    /**
     * 从内部组件里删掉对应的模块的命令组件
     *
     * @param moduleId
     */
    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.delModuleOpratingContainerFromComponent(moduleId);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.delModuleOpratingContainerFromComponent(moduleId);
            }
        }
    }

    @Override
    public CustomFunctionNameHolder getThisCustomMethodNameHolder() {
        return customFunctionNameHolder;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThis() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = new ArrayList<OpratingContainerInterface>();
        if (deafaultOperatingPane != null) {
            opratingContainerList.addAll(deafaultOperatingPane.getAllOpratingContainer());
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                opratingContainerList.addAll(hiddenOperatingPane.getAllOpratingContainer());
            }
        }
        return opratingContainerList;
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        // TODO Auto-generated method stub
        ArrayList<OpratingContainerInterface> opratingContainerList = getAllOpratingContainerListInThis();
        opratingContainerList.add(this);
        return opratingContainerList;
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(UIManager.getColor("Panel.background"));//设置画图的颜色
        g2d.fillRect(0, 0, getWidth(), DEFAULT_SHOW_HEIGHT);//填充一个矩形
        super.paint(g);
    }


    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    public boolean isHiddenState() {
        return hiddenState;
    }

    @Override
    public void functionNameSynchronousChange(int functionNameId) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.functionNameSynchronousChange(functionNameId);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.functionNameSynchronousChange(functionNameId);
            }
        }
    }

    @Override
    public void functionNameSynchronousDelete(int functionNameId) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.functionNameSynchronousDelete(functionNameId);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.functionNameSynchronousDelete(functionNameId);
            }
        }
    }

    @Override
    public void variableSynchronousChange(int variableId) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.variableSynchronousChange(variableId);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.variableSynchronousChange(variableId);
            }
        }
    }

    @Override
    public void variableSynchronousDelete(int variableId) {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.variableSynchronousDelete(variableId);
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.variableSynchronousDelete(variableId);
            }
        }
    }

    @Override
    public void autoCollapse(OpratingContainerInterface currentOpratingContainer) {
        // TODO Auto-generated method stub
        if (currentOpratingContainer == null) {
            if (hiddenState == true) {
                if (deafaultOperatingPane != null) {
                    deafaultOperatingPane.collapseThis();
                }
                if (hiddenOperatingPane != null) {
                    hiddenOperatingPane.collapseThis();
                }
                packUpHiddenPanel();
            } else {
                if (deafaultOperatingPane != null) {
                    deafaultOperatingPane.collapseThis();
                }
            }
        } else {
            if (this != currentOpratingContainer) {
                if (isItTheSameLevel(currentOpratingContainer) == false) {
                    if (hiddenState == true) {
                        if (deafaultOperatingPane != null) {
                            deafaultOperatingPane.collapseThis();
                        }
                        if (hiddenOperatingPane != null) {
                            hiddenOperatingPane.collapseThis();
                        }
                        packUpHiddenPanel();

                    } else {
                        if (deafaultOperatingPane != null) {
                            deafaultOperatingPane.collapseThis();
                        }
                    }
                }
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
                    if (codeSerialNumber != opratingContainer.getCodeSerialNumber()) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.collapseThis();
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.collapseThis();
            }
        }
    }

    @Override
    public void setNoUserSelectionIsRequiredValue() {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.setNoUserSelectionIsRequiredValue();
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.setNoUserSelectionIsRequiredValue();
            }
        }
    }

    @Override
    public void showSelectedValue() {
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.showSelectedValue();
        }
        if (hiddenState == true) {
            if (hiddenOperatingPane != null) {
                hiddenOperatingPane.showSelectedValue();
            }
        }
    }

    @Override
    public void updateValue(CodeGenerationComponentInterface codeGenerationComponent, int paneType, Object updateParam) {
        CodeFormatFlagParam codeFormatFlagParam;
        CodeShowPane codeShowPane;
        BaseMarkElement markElement;
        UpdateCodeEditParamForFormat updateCodeEditParamForFormat;
        UpdateCodeEditParamForMark updateCodeEditParamForMark;
        PathFindCell pathFindCell;
        PathFind pathFindTemp;
        boolean flag, seFlag;
        for (CodePaneModelGenerateCodeResponseParam codeResponseParam : thisCodeLocationInformation.getCodeListLocationInfoList()) {
            codeFormatFlagParam = codeResponseParam.getCodeFormatFlagParam();
            if (codeFormatFlagParam != null) {
                codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                seFlag = false;
                if (codeShowPane != null) {
                    for (CodeListLocation codeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                            markElement = codeListLocation.getThanMarkElement();
                            if (markElement != null) {
                                for (PathFind addPathFindTemp : codeListLocation.getCodePathFindList()) {
                                    pathFindCell = new PathFindCell(codeSerialNumber,
                                            codeGenerationComponent.getControlElement(), paneType);
                                    pathFindTemp = new PathFind(addPathFindTemp);
                                    pathFindTemp.getPathList().add(pathFindCell);

                                    updateCodeEditParamForMark = new UpdateCodeEditParamForMark();
                                    updateCodeEditParamForMark.setTrulyPathFind(pathFindTemp);
                                    updateCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
                                    updateCodeEditParamForMark.setOpratingLabel(codeGenerationComponent.getControlElement());
                                    updateCodeEditParamForMark.setUpdateParam(updateParam);
//                                    updateCodeEditParamForMark.setCodeLabelId(codeResponseParam.getCodeLabelId());
                                    updateCodeEditParamForMark.setThanMarkElement(markElement);

                                    flag = codeShowPane.updateCodeFromMark(updateCodeEditParamForMark);
                                    if (flag && seFlag == false) {
                                        CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                                        seFlag = true;
                                    }
                                }
                            }
                        } else if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                            for (PathFind addPathFindTemp : codeListLocation.getCodePathFindList()) {
                                pathFindCell = new PathFindCell(codeSerialNumber,
                                        codeGenerationComponent.getControlElement(), paneType);
                                pathFindTemp = new PathFind(addPathFindTemp);
                                pathFindTemp.getPathList().add(pathFindCell);

                                updateCodeEditParamForFormat = new UpdateCodeEditParamForFormat();
                                updateCodeEditParamForFormat.setCodeSerialNumber(codeSerialNumber);
                                updateCodeEditParamForFormat.setTrulyPathFind(pathFindTemp);
                                updateCodeEditParamForFormat.setOpratingLabel(codeGenerationComponent.getControlElement());
                                updateCodeEditParamForFormat.setUpdateParam(updateParam);

//                                updateCodeEditParamForFormat.setCodeLabelId(codeResponseParam.getCodeLabelId());
                                flag = codeShowPane.updateValueInFormat(updateCodeEditParamForFormat);
                                if (flag && seFlag == false) {
                                    CodeGenerationFrameHolder.codeShowPanel.setSelectedCodePane(codeShowPane);
                                    seFlag = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void delCode() {
        CodeFormatFlagParam codeFormatFlagParam;
        CodeShowPane codeShowPane;
        BaseMarkElement markElement;
        DelCodeEditParamForMark delCodeEditParamForMark;
        DelCodeEditParamForFormat delCodeEditParamForFormat;
        for (CodePaneModelGenerateCodeResponseParam codeResponseParam : thisCodeLocationInformation.getCodeListLocationInfoList()) {
            codeFormatFlagParam = codeResponseParam.getCodeFormatFlagParam();
            if (codeFormatFlagParam != null) {
                codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeResponseParam.getCodeFormatFlagParam());
                if (codeShowPane != null) {
                    for (CodeListLocation codeListLocation : codeResponseParam.getCodeListLocationInfoList()) {
                        if (PathFind.COMMAND_TYPE == codeListLocation.getMetaType()) {
                            markElement = codeListLocation.getThanMarkElement();
                            if (markElement != null) {
                                for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                    delCodeEditParamForMark = new DelCodeEditParamForMark();
                                    delCodeEditParamForMark.setThanMarkElement(markElement);
                                    delCodeEditParamForMark.setCodeSerialNumber(codeSerialNumber);
                                    delCodeEditParamForMark.setTrulyPathFind(trulyPathFind);
                                    codeShowPane.delCodeToMark(delCodeEditParamForMark);
                                }
                            }
                        } else if (PathFind.FORMAT_TYPE == codeListLocation.getMetaType()) {
                            for (PathFind trulyPathFind : codeListLocation.getCodePathFindList()) {
                                delCodeEditParamForFormat = new DelCodeEditParamForFormat();
                                delCodeEditParamForFormat.setCodeSerialNumber(codeSerialNumber);
                                delCodeEditParamForFormat.setTrulyPathFind(trulyPathFind);
                                codeShowPane.delCodeFromFormat(delCodeEditParamForFormat);
                            }
                        }
                    }
                }
            }
        }
        thisCodeLocationInformation.clearAllInfo();
    }

    /**
     * 删除控制面板中的组件和相关内容
     */
    public void delControlComponentsAndContent() {
        //先删除原来的
        if (deafaultOperatingPane != null) {
            deafaultOperatingPane.delThis();
            deafaultOperatingPane.removeAll();
            deafaultOperatingPane.setText("");
        }
        if (hiddenState == true && hiddenOperatingPane != null) {
            hiddenOperatingPane.delThis();
            hiddenOperatingPane.removeAll();
            hiddenOperatingPane.setText("");
        }
    }


    @Override
    public void delThis() {
        if (codeControlPane != null) {
            delCode();
            delControlComponentsAndContent();
            codeControlPane.delCodeControlCommand(this);
        }
    }

    /**
     * 获取放置该容器的面板
     *
     * @return
     */
    public abstract AbstractCodeControlPane getCodeControlPane();

    /**
     * 获取这个功能的上一个功能
     *
     * @return
     */
    public abstract OpratingContainerInterface getParentOpratingContainer();

    public abstract BaseMarkElement getCorrespondingMarkElement();

    protected ArrayList<CodeShowPane> getCodePaneList() {
        ArrayList<CodeShowPane> list = new ArrayList<>();
        if (thisCodeLocationInformation != null) {
            CodeFormatFlagParam codeFormatFlagParam;
            CodeShowPane codeShowPane;
            ArrayList<CodePaneModelGenerateCodeResponseParam> codeListLocationInfoList = thisCodeLocationInformation.getCodeListLocationInfoList();
            for (CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam : codeListLocationInfoList) {
                codeFormatFlagParam = codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam();
                codeShowPane = CodeGenerationFrameHolder.codeShowPanel.getCorrespondingCodePane(codeFormatFlagParam);
                if (codeShowPane != null) {
                    boolean flag = false;
                    for (CodeShowPane codeShowPaneTemp : list) {
                        if (CodeFormatFlagParam.compare(codeShowPaneTemp.getCodeFormatFlagParam(),
                                codeShowPane.getCodeFormatFlagParam())) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag == false) {
                        list.add(codeShowPane);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 负责布局面板组件
     */
    class opratingLayout implements LayoutManager {

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
            int w = parent.getWidth(), h = parent.getHeight(), dHeight = deafaultOperatingJSP.getHeight();
            deafaultOperatingJSP.setBounds(xCoordinates, 0, (int) (w - closeButton.getPreferredSize().getWidth()),
                    dHeight);

            closeButton.setBounds((int) (w - closeButton.getPreferredSize().getWidth()), 0,
                    (int) closeButton.getPreferredSize().getWidth(), (int) closeButton.getPreferredSize().getHeight());

            hiddenAndInfoButton.setBounds((int) (w - hiddenAndInfoButton.getPreferredSize().getWidth()),
                    (int) closeButton.getPreferredSize().getHeight(),
                    hiddenAndInfoButton.getWidth(), hiddenAndInfoButton.getHeight());
            if (hiddenState == true) {
                getDrawer().setBounds(xCoordinates, dHeight, w, h - dHeight);
            }
        }
    }

}
