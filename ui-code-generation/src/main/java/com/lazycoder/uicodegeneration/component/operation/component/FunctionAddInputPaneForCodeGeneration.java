package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.CodeOpratingComponentBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.functionadd.FunctionAddCodeControlPane;
import com.lazycoder.uicodegeneration.component.operation.container.FunctionAddInternalOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractAdditiveMethodCodePane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractCodeControlPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FunctionAddMeta;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import lombok.Getter;

public class FunctionAddInputPaneForCodeGeneration extends MyPopupButton
        implements CodeGenerationFormatUIComonentInterface, CodeGenerationComponentInterface,
        CodeOpratingComponentBusinessTraverse {

    /**
     *
     */
    private static final long serialVersionUID = -972518026437048859L;

    private static final ImageIcon MORE_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
            + "FunctionOperationComponent" + File.separator + "FunctionAdd" + File.separator + "more.png"),
            MORE_PRESSED_ICON = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent"
                            + File.separator + "FunctionAdd" + File.separator + "more_pressed.png");

    private static final ImageIcon OPTION_ICON = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent" + File.separator
                    + "FunctionAdd" + File.separator + "列表.png");
    private static final int DEAFAULT_FUNCTION_PANE_WIDTH = (int) (0.30 * SysUtil.SCREEN_SIZE.width);
    private FunctionAddCodeControlPane functionAddCodeControlPane;        //	本层的添加容器面板
    private AbstractCodeControlPane parentLayerFunctionAddOperationPane = null;    //	上一层的添加容器面板（收起面板的时候用来把该面板设为当前面板）
    private FunctionAddControl controlElement;
    private PathFind pathFind;
    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;
    private FunctionAddOperationPane functionAddOperationPane = null;

    public FunctionAddInputPaneForCodeGeneration() {
        super();
        buttonHeight = MORE_ICON.getIconHeight() + 7;
        buttonWidth = MORE_ICON.getIconWidth() + 7;
        paneWidth = DEAFAULT_FUNCTION_PANE_WIDTH;
        paneHeight = 450;
        setIcon(MORE_ICON);
        setSelectedIcon(MORE_PRESSED_ICON);
        setInternalComponent(new JTextArea());
    }


    /**
     * 新建
     *
     * @param codeGenerationalOpratingContainerParam
     * @param controlElement
     */
    public FunctionAddInputPaneForCodeGeneration(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                                 FunctionAddControl controlElement) {
        super();
        this.controlElement = controlElement;
        this.parentLayerFunctionAddOperationPane = codeGenerationalOpratingContainerParam.getCodeControlPane();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;
        functionAddCodeControlPane = new FunctionAddCodeControlPane(this.pathFind,
                codeGenerationalOpratingContainerParam, this.controlElement);

        init();
        functionAddCodeControlPane.autoGenerateOpratingContainers();
    }


    /**
     * 还原
     *
     * @param codeGenerationalOpratingContainerParam
     * @param meta
     */
    public FunctionAddInputPaneForCodeGeneration(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                                 FunctionAddMeta meta) {
        super();
        this.controlElement = meta.getControlElement();
        this.parentLayerFunctionAddOperationPane = codeGenerationalOpratingContainerParam.getCodeControlPane();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = meta.getPathFind();
        functionAddCodeControlPane = new FunctionAddCodeControlPane(this.pathFind,
                codeGenerationalOpratingContainerParam, this.controlElement);
        init();

        functionAddCodeControlPane.restoreContent(meta.getFunctionAddCodeControlPaneModel());
    }

    private void init() {
        buttonHeight = MORE_ICON.getIconHeight() + 7;
        buttonWidth = MORE_ICON.getIconWidth() + 7;
        paneWidth = DEAFAULT_FUNCTION_PANE_WIDTH;
        paneHeight = 450;
        setIcon(MORE_ICON);
        setSelectedIcon(MORE_PRESSED_ICON);
        functionAddOperationPane = new FunctionAddOperationPane(functionAddCodeControlPane);
        setInternalComponent(functionAddOperationPane);

        //调试用
//        HTMLText htmlText = new HTMLText();
//        HtmlPar par1 = new HtmlPar();
//        par1.add(controlElement.getThisName());
//        par1.nextLine();
//        par1.addText(pathFind.toString(), false);
//        htmlText.addPar(par1);
//        if (this.pathFind.isFirstLayerOpratingContainer() == true) {
//            HtmlPar par2 = new HtmlPar();
//            par1.addColorText("第一层的", HtmlPar.BLUE, false);
//            htmlText.addPar(par2);
//        }
//        setToolTipText(htmlText.getHtmlContent());
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }


    @Override
    public FunctionAddMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        FunctionAddMeta model = new FunctionAddMeta();
        setParam(model);
        return model;
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        FunctionAddMeta theModel = (FunctionAddMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(functionAddCodeControlPane.getPathFind());
        theModel.setFunctionAddCodeControlPaneModel(functionAddCodeControlPane.getFormatStructureModel());
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        if (functionAddCodeControlPane == CodeGenerationFrameHolder.currentAdditiveMethodCodePane) {
            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                    CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
        }
        functionAddCodeControlPane.delThis();
        hidePopupPanel();
    }

    @Override
    public FunctionAddControl getControlElement() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        // TODO Auto-generated method stub
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        // TODO Auto-generated method stub
        functionAddCodeControlPane.delTheModuleContent(moduleId);
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        return functionAddCodeControlPane.getAllOpratingContainer();
    }

    private ArrayList<OpratingContainerInterface> getAllOpratingContainerListInThisPane() {
        return functionAddCodeControlPane.getAllOpratingContainerListInThisPane();
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return buttonWidth;
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (isExpanded()) {
            doClick();
        }
    }


    @Override
    protected void doSomeThingWhenExpandPanel() {
        if (controlElement.isOnlInternalCodeIsAllowed() == false) {// 如果可以使用外部方法，即可以在这添加方法
            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(functionAddCodeControlPane);
            functionAddCodeControlPane.requestFocus();
        }
    }

    /**
     * 收起面板时所进行的操作
     * <p>
     * 如果可以添加外部方法
     * 如果是命令类型
     * 如果就是在第一层的话
     * 如果本组件的功能拓展面板就是当前面板
     * 把当前的业务逻辑面板设置为新的当前面板
     * <p>
     * 不是第一层的，看看上一层是不是可以添加外部方法的面板
     * 是的话，把上一层设置为新的当前面板
     * <p>
     * <p>
     * <p>
     * 如果是格式类型
     */
    @Override
    protected void doSomeThingWhenPackUpPanel() {
        if (functionAddCodeControlPane == CodeGenerationFrameHolder.currentAdditiveMethodCodePane) {//当前面板就是这个组件的面板
            if (controlElement.isOnlInternalCodeIsAllowed() == false) {//如果可以添加外部方法
                if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
                    if (this.pathFind.isFirstLayerOpratingContainer() == true) {// 如果可以直接添加到方法
                        if (functionAddCodeControlPane == CodeGenerationFrameHolder.currentAdditiveMethodCodePane) {// 如果内部的功能拓展面板就是当前面板
                            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                    CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                        }
                    } else {// 不是直接添加到业务逻辑面板的
                        if (parentLayerFunctionAddOperationPane != null) {
                            if (parentLayerFunctionAddOperationPane instanceof AbstractAdditiveMethodCodePane) {
                                AbstractAdditiveMethodCodePane pane = (AbstractAdditiveMethodCodePane) parentLayerFunctionAddOperationPane;
                                if (pane.canAddExternalMethods() == true) {//如果可以添加外部方法
                                    CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(pane);
                                } else {
                                    CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                            CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                                }
                            } else {
                                CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                        CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                            }
                        } else {
                            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                    CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                        }
                    }
                } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
//                    if (this.pathFind.isFirstLayerOpratingContainer() == true) {
//					if (functionAddCodeControlPane == CodeGenerationFrameHolder.currentAdditiveMethodCodePane) {// 如果内部的功能拓展面板就是当前面板
//                        CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
//                                CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
//					}else {
//						CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
//								CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
//					}
//                    } else {
                    if (parentLayerFunctionAddOperationPane != null) {
                        if (parentLayerFunctionAddOperationPane instanceof AbstractAdditiveMethodCodePane) {
                            AbstractAdditiveMethodCodePane pane = (AbstractAdditiveMethodCodePane) parentLayerFunctionAddOperationPane;
                            if (pane.canAddExternalMethods() == true) {//如果可以添加外部方法
                                CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(pane);
                            } else {
                                CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                        CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                            }
                        } else {
                            CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                    CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                        }
                    } else {
                        CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                                CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                    }
//                    }
                }
            }
        } else {//当前面板不是这个组件的面板（但可能是这个组件里面的方法组件的面板）
            if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane != null) {
                PathFind currentPanePathFind = CodeGenerationFrameHolder.currentAdditiveMethodCodePane.getPathFind();
                if (PathFind.isTheComparisonHierarchical(currentPanePathFind, pathFind)) {
                    CodeGenerationFrameHolder.setCurrentMethodAddPaneAs(
                            CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane());
                }
            }
        }
    }

    class FunctionAddOperationPane extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = 872386809840651872L;

        private MyToolBar toolBar;

        private JToggleButton ownMethodButton;
        private JPopupMenu popupMenu;

        private FunctionAddOperationPane() {
            setLayout(new BorderLayout());

            toolBar = new MyToolBar();
            toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
            toolBar.setFloatable(false);
            add(toolBar, BorderLayout.NORTH);
        }

        public FunctionAddOperationPane(AbstractCodeControlPane codeControlPane) {
            this();
            init();
            add(codeControlPane.getParentScrollPane(), BorderLayout.CENTER);
        }

        private void init() {
            boolean isOnlInternalCodeIsAllowed=controlElement.isOnlInternalCodeIsAllowed();
            ArrayList<MethodAddStorageFormat> addFunctionList = controlElement.getFunctionList();

            if (isOnlInternalCodeIsAllowed == true) {// 不可以使用外部方法
                toolBar.setBorder(BorderFactory.createTitledBorder("本面板不允许使用外部方法"));
            }

            if (addFunctionList.size() > 0) {
                JLabel label = new JLabel("内部方法：");
                toolBar.add(label);

                popupMenu = new JPopupMenu();

                ownMethodButton = new JToggleButton(OPTION_ICON);
                Dimension btDimension = new Dimension(OPTION_ICON.getIconWidth(), OPTION_ICON.getIconHeight());
                ownMethodButton.setPreferredSize(btDimension);
                ownMethodButton.setFocusPainted(false);
                ownMethodButton.addMouseListener(mouseAdapter);
                toolBar.add(ownMethodButton);
            }
        }

//        (FunctionUseProperty.no);无
//        (FunctionUseProperty.autoGenerateOnce);自动生成一次
//        (FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce);自动生成一次，可删
//        (FunctionUseProperty.onlyBeUsedOnce);只能使用一次
//        (FunctionUseProperty.autoGenerateOnceAndCanNotDel);仅自动生成一次且不能删
        private void updateMenuItems() {
            popupMenu.removeAll();
            ArrayList<MethodAddStorageFormat> addFunctionList = controlElement.getFunctionList();
            FunctionAddInternalMethodMenuItem menuItem;
            FunctionAddInternalOpratingContainer functionAddInternalOpratingContainer;

            ArrayList<OpratingContainerInterface> containerList = getAllOpratingContainerListInThisPane();//获取当前面板所有的功能容器
            for (MethodAddStorageFormat methodAddStorageFormatTemp : addFunctionList) {
                menuItem = new FunctionAddInternalMethodMenuItem(methodAddStorageFormatTemp);
                popupMenu.add(menuItem);

                //如果该菜单是只能添加一次的和自动生成只能添加一次的，查看当前添加的功能里面有没有和添加这个菜单对应的功能，有的话，将该菜单失能
                if (methodAddStorageFormatTemp.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue() ||
                        methodAddStorageFormatTemp.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue()) {

                    for (OpratingContainerInterface opratingContainer : containerList) {
                        if (opratingContainer instanceof FunctionAddInternalOpratingContainer) {
                            functionAddInternalOpratingContainer = (FunctionAddInternalOpratingContainer) opratingContainer;

                            if (methodAddStorageFormatTemp.getOrdinal() == functionAddInternalOpratingContainer.getOrdinalInUserDB()) {
                                menuItem.setEnabled(false);
                                break;
//                            }else {
//                                menuItem.setEnabled(true);
                            }
                        }
                    }
                }
            }
        }

        private MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == ownMethodButton) {
                    updateMenuItems();
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    super.mouseEntered(e);
                }
            }

            ;
        };
    }


    class FunctionAddInternalMethodMenuItem extends JMenuItem {

        /**
         *
         */
        private static final long serialVersionUID = 2558244396978300446L;

        @Getter
        private MethodAddStorageFormat methodAddStorageFormat;

        public FunctionAddInternalMethodMenuItem(MethodAddStorageFormat methodAddStorageFormat) {
            // TODO Auto-generated constructor stub
            super(methodAddStorageFormat.getShowText());
            this.methodAddStorageFormat = methodAddStorageFormat;
            this.addActionListener(listener);

            HTMLText noteTip = CodeGenerationFrameHolder.getNoteToolTip(methodAddStorageFormat.getNoteListParam());
            if (noteTip != null) {
                setToolTipText(noteTip.getHtmlContent());
            }
        }

        private ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                CodeGenerationFrameHolder.temporaryErrorList.clear();//先把临时错误列表清空

                FunctionAddInternalOpratingContainer container = functionAddCodeControlPane.addInternalFunction(methodAddStorageFormat, true, true);
                if (container == null) {
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，这个功能的数据有点问题，没法写进去喔", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);

                } else {
                    //该功能的组件自动选择
                    //container.setNoUserSelectionIsRequiredValue();

                    //所有功能自动选择
                    ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                    if (opratingContainerList != null) {
                        for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                            opratingContainer.setNoUserSelectionIsRequiredValue();
                        }
                    }
                    container.requestFocus();
                    CodeGenerationFrameHolder.showErrorListIfNeed("这个功能有点异常喔   (=ω=；)");
                }
            }
        };
    }

}
