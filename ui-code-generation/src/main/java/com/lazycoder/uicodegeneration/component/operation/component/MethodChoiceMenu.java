package com.lazycoder.uicodegeneration.component.operation.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.formdev.flatlaf.ui.FlatMenuItemUI;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.CustomFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AbstractFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AbstractFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.ModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.CodeControlTabbedPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.MethodChooseMeta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import lombok.Getter;

public class MethodChoiceMenu extends JMenuBar implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    private JMenu menu;

    private MethodChoiceMenuItem selectedMenuItem = null;

    private MethodChooseControl controlElement = new MethodChooseControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private MethodChooseMeta methodChooseMeta = null;

    private MethodChoiceMenu() {
        this.menu = new JMenu();
        this.add(menu);
        menu.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        menu.addMenuListener(menuListener);
        setTheSize();
        setToolTipText("(*^▽^*) 亲，记得要把需要填写的内容都填好喔");
    }

    public MethodChoiceMenu(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                            MethodChooseControl controlElement) {
        this();
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;
    }

    public MethodChoiceMenu(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                            MethodChooseMeta methodChooseMeta) {
        this();
        this.controlElement = methodChooseMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = methodChooseMeta.getPathFind();

        this.methodChooseMeta = methodChooseMeta;
        setMenuText(methodChooseMeta.getMethodName());
    }

    private void setTheSize() {
        Dimension dd = new Dimension(140, 30);
        setPreferredSize(dd);
        setMinimumSize(dd);
        setMaximumSize(dd);
        menu.setPreferredSize(dd);
        menu.setMinimumSize(dd);
        menu.setMaximumSize(dd);
    }

    private MenuListener menuListener = new MenuListener() {
        @Override
        public void menuSelected(MenuEvent menuEvent) {
            uploadConformingVariable();
        }

        @Override
        public void menuDeselected(MenuEvent menuEvent) {
        }

        @Override
        public void menuCanceled(MenuEvent menuEvent) {
        }
    };

    /**
     * 根据设置的数据类型加载符合的变量
     */
    private void uploadConformingVariable() {
        AbstractMethodName methodName = null;
        if (selectedMenuItem != null) {
            methodName = selectedMenuItem.generalMethodName;
        }
        menu.removeAll();
        ArrayList<String> functionNameNeedDataTypeList = MethodChooseControl.getLabelTypeList(controlElement);
        if (functionNameNeedDataTypeList != null) {
            if (0 == functionNameNeedDataTypeList.size()) {// 没设置数据类型
                uploadNullFunctionName();
            } else if (functionNameNeedDataTypeList.size() > 0) {// 有设置数据类型
                uploadNeedFunctionName(functionNameNeedDataTypeList);
            }
        }
        setMenuText(methodName);
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    /**
     * 加载所有没设置的变量
     */
    private void uploadNullFunctionName() {
        ArrayList<AbstractMethodName> theCorrespondingMethodNameList = getTheCorrespondingFunctionNameList();
        MethodChoiceMenuItem methodChoiceMenuItem;
        for (AbstractMethodName methodNameTemp : theCorrespondingMethodNameList) {
            methodChoiceMenuItem = new MethodChoiceMenuItem(methodNameTemp);
            menu.add(methodChoiceMenuItem);
        }
    }

    /**
     * 加载所需变量
     */
    private void uploadNeedFunctionName(ArrayList<String> methodNameNeedDataTypeList) {
        ArrayList<AbstractMethodName> theCorrespondingFunctionNameList = getTheCorrespondingFunctionNameList();
        ArrayList<AbstractMethodName> needMethodNameList = getNeedFunctionNameList(
                theCorrespondingFunctionNameList, methodNameNeedDataTypeList);
        MethodChoiceMenuItem methodChoiceMenuItem;
        for (AbstractMethodName methodNameTemp : needMethodNameList) {
            methodChoiceMenuItem = new MethodChoiceMenuItem(methodNameTemp);
            menu.add(methodChoiceMenuItem);
        }
    }

    private ArrayList<AbstractMethodName> getTheCorrespondingFunctionNameList() {
        ArrayList<AbstractMethodName> methodNameTempList = new ArrayList<>();
        AttributeUsageRange useRange = AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange());

        if (AttributeUsageRange.ApplyOnlyToAddedTemplates == useRange) {// 仅应用于所添加的模板
            //获取所添加的模板的格式方法名、以及自定义方法名的数据
            addTemplatesMethodNameList(methodNameTempList);

        } else if (AttributeUsageRange.ApplyOnlyToItself == useRange) {// 仅在 本方法 / 仅在本格式面板使用
            //获取本方法的自定义方法名
            addThisMethodNameList(methodNameTempList);

//            if (PathFind.commandType == pathFind.getMetaType()) {
//                GeneralCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
//                        .getFirstCommandOpratingContainer();
//                if (firstCommandOpratingContainer != null) {
//                    methodNameTempList.addAll(firstCommandOpratingContainer.getThisCustomMethodNameHolder().getFunctionNameList());
//                }
//            } else if (PathFind.formatType == pathFind.getMetaType()) {
//                GeneralFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
//                if (formatContainer != null) {
//                    methodNameTempList.addAll(formatContainer.getThisCustomMethodNameHolder().getFunctionNameList());
//                }
//            }

        } else if (AttributeUsageRange.ApplyOnlyToThisModule == useRange) {// 仅在本模块使用（模块使用）
            //获取本模块的格式方法名、以及自定义方法名的数据
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();
            addModuleMethodNameList(methodNameTempList, moduleId);

        } else if (AttributeUsageRange.ModulesRequiredForThisModule == useRange) {// 本模块需要使用的模块（模块使用）
            //获取需要使用的模块的格式方法名、以及自定义方法名的数据
            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
                ArrayList<String> needModuleIdList = JSON.parseObject(
                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
                        new TypeReference<ArrayList<String>>() {
                        });
                for (String needModuleTemp : needModuleIdList) {
                    addModuleMethodNameList(methodNameTempList, needModuleTemp);
                }
            }

        } else if (AttributeUsageRange.ThisModuleAndRequiredModules == useRange) {// 本模块以及需要使用的模块（模块使用）
            //获取本模块、以及需要使用的模块的格式方法名、以及自定义方法名的数据
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();
            addModuleMethodNameList(methodNameTempList, moduleId);
            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
                ArrayList<String> needModuleIdList = JSON.parseObject(
                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
                        new TypeReference<ArrayList<String>>() {
                        });
                for (String needModuleTemp : needModuleIdList) {
                    addModuleMethodNameList(methodNameTempList, needModuleTemp);
                }
            }

        } else if (AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate == useRange) {// 仅在该类型模板使用（可选模板使用）
            AbstractFormatFunctionNameHolder formatFunctionNameHolder = codeGenerationalOpratingContainerParam.getFormatControlPane()
                    .getFormatFunctionNameHolder();
            if (formatFunctionNameHolder != null) {
                methodNameTempList.addAll(formatFunctionNameHolder.getFunctionNameList());
            }

            CustomFunctionNameHolder customFunctionNameHolderTemp;
            ArrayList<AdditionalFormatControlPane> additionalFormatControlPaneList = CodeControlTabbedPane
                    .getAdditionalFormatControlPaneList(
                            codeGenerationalOpratingContainerParam.getFormatControlPane().getAdditionalSerialNumber());
            for (AdditionalFormatControlPane formatControlPane : additionalFormatControlPaneList) {
                customFunctionNameHolderTemp = formatControlPane.getThisCustomFunctionNameHolder();
                if (customFunctionNameHolderTemp != null) {
                    methodNameTempList.addAll(customFunctionNameHolderTemp.getFunctionNameList());
                }
            }

        } else if (AttributeUsageRange.ApplyToAll == useRange) {// 应用于所有

            ArrayList<AbstractFunctionNameHolder> allMethodNameHolderList = CodeGenerationFrameHolder
                    .getAllFunctionNameHolderList(codeGenerationalOpratingContainerParam.getFormatControlPane());
            for (AbstractFunctionNameHolder methodNameHolderTemp : allMethodNameHolderList) {
                methodNameTempList.addAll(methodNameHolderTemp.getFunctionNameList());
            }
        }
        return methodNameTempList;
    }

    /**
     * 方法名id为functionNameId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     */
    public void synchronousChange(int functionNameId) {
        if (selectedMenuItem != null) {
            AbstractMethodName selectedFunctionName = selectedMenuItem.generalMethodName;
            if (selectedFunctionName != null) {
                if (selectedFunctionName.compare(functionNameId)) {
                    updateValue();
                }
            }
        }
    }

    /**
     * 方法名id为functionNameId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param functionNameId
     */
    public void synchronousDelete(int functionNameId) {
        if (selectedMenuItem != null) {
            AbstractMethodName selectedFunctionName = selectedMenuItem.generalMethodName;
            if (selectedFunctionName.compare(functionNameId)) {
                selectedMenuItem = null;
                updateValue();
            }
        }
    }

    /**
     * 筛选出要显示的方法名
     *
     * @param theCorrespondingFunctionNameList
     * @param functionNameNeedDataTypeList
     * @return
     */
    private ArrayList<AbstractMethodName> getNeedFunctionNameList(
            ArrayList<AbstractMethodName> theCorrespondingFunctionNameList,
            ArrayList<String> functionNameNeedDataTypeList) {

        ArrayList<AbstractMethodName> theFunctionNameList = new ArrayList<AbstractMethodName>();

        boolean flag = false;
        for (AbstractMethodName functionNameTemp : theCorrespondingFunctionNameList) {
            flag = false;
            for (String functionNameDataTypeTemp : functionNameTemp.getFunctionNameType()) {
                for (String functionNameNeedDateTypeTemp : functionNameNeedDataTypeList) {
                    if (functionNameNeedDateTypeTemp.equals(functionNameDataTypeTemp)) {
                        theFunctionNameList.add(functionNameTemp);
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
        }
        return theFunctionNameList;
    }


    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        if (selectedMenuItem == null) {
            CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), "");
            menu.setText("");
            menu.setToolTipText(null);
        } else {
            CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), selectedMenuItem.getGeneralMethodName().getFunctionNameValue());
            setMenuText(selectedMenuItem.getGeneralMethodName());
        }
    }

    /**
     * 根据方法名设置菜单显示的文字
     *
     * @param generalMethodName
     */
    private void setMenuText(AbstractMethodName generalMethodName) {
        if (generalMethodName == null) {
            menu.setText("");
            menu.setToolTipText(null);
        } else {
            if (generalMethodName instanceof CustomFunctionName) {
                String methodNameValue = generalMethodName.getFunctionNameValue();
                menu.setText(methodNameValue);
                if (methodNameValue.length() > 8) {
                    menu.setToolTipText(methodNameValue);
                }
            } else if (generalMethodName instanceof FormatFunctionName) {
                String roleOfValue = ((FormatFunctionName) generalMethodName).getRoleOfFunctionNameValue();
                menu.setText(roleOfValue);
                if (roleOfValue.length() > 8) {
                    menu.setToolTipText(roleOfValue);
                }
            }
        }
    }

    /**
     * 根据selectMethodNameId设置菜单显示的文字
     *
     * @param selectMethodNameId
     */
    private void setMenuText(Integer selectMethodNameId) {
        if (selectMethodNameId != null) {
            ArrayList<AbstractMethodName> theCorrespondingFunctionNameList = getTheCorrespondingFunctionNameList();
            if (theCorrespondingFunctionNameList != null) {
                for (AbstractMethodName methodName : theCorrespondingFunctionNameList) {
                    if (methodName.getFunctionNameId() == selectMethodNameId) {
                        setMenuText(methodName);
                    }
                }
            }
        }
    }


    @Override
    public void delThis() {
    }

    @Override
    public MethodChooseControl getControlElement() {
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        MethodChooseMeta theModel = (MethodChooseMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        if (selectedMenuItem != null) {
            theModel.setMethodName(selectedMenuItem.generalMethodName);
//			theModel.setSelectMethodNameId(selectedMenuItem.generalMethodName.getFunctionNameId());
        }
    }

    @Override
    public MethodChooseMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        MethodChooseMeta model = new MethodChooseMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return 140;
    }

    @Override
    public void collapseThis() {

    }

    class MethodChoiceMenuItem extends JMenuItem {


        @Getter
        private AbstractMethodName generalMethodName;

        public MethodChoiceMenuItem(AbstractMethodName generalMethodName) {
            this.generalMethodName = generalMethodName;
            if (generalMethodName != null) {
                if (generalMethodName instanceof CustomFunctionName) {
                    this.setText(generalMethodName.getFunctionNameValue());
                    setForeground(new Color(45, 140, 240));

                } else if (generalMethodName instanceof FormatFunctionName) {
                    this.setText(((FormatFunctionName) generalMethodName).getRoleOfFunctionNameValue());
                }
            }
            if (generalMethodName.getFunctionNameValue() == null || "".equals(generalMethodName.getFunctionNameValue())) {
                this.setToolTipText(generalMethodName.getToolTipTextOfHaveNotValue());//原本设计空值也显示出来，提示用户去填，结果组件默认不显示空值，没有重写对应方法
                this.setUI(new TheMenuUI());
                //setBackground(new Color(255, 206, 105));
            } else {
                this.setToolTipText(generalMethodName.getToolTipTextOfHaveValue());
//                this.setBackground(defaultColor);
            }
            this.addActionListener(actionListener);
        }


        private ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedMenuItem = MethodChoiceMenuItem.this;
                updateValue();
            }
        };

        class TheMenuUI extends FlatMenuItemUI {
            public TheMenuUI() {
//                selectionBackground = new Color(255, 255, 222);
                selectionBackground = new Color(255, 206, 105);
            }
        }
    }

    /**
     * 添加本模板的方法名
     *
     * @param theMethodNameList
     */
    private void addTemplatesMethodNameList(ArrayList<AbstractMethodName> theMethodNameList) {
        AbstractFormatFunctionNameHolder formatMethodNameHolder = codeGenerationalOpratingContainerParam
                .getFormatControlPane().getFormatFunctionNameHolder();
        if (formatMethodNameHolder != null) {
            theMethodNameList.addAll(formatMethodNameHolder.getFunctionNameList());
        }

        CustomFunctionNameHolder customMethodNameHolderTemp = codeGenerationalOpratingContainerParam
                .getFormatControlPane().getThisCustomFunctionNameHolder();
        if (customMethodNameHolderTemp != null) {
            theMethodNameList.addAll(customMethodNameHolderTemp.getFunctionNameList());
        }
    }

    /**
     * 添加本模板的方法名
     *
     * @param theMethodNameList
     */
    private void addThisMethodNameList(ArrayList<AbstractMethodName> theMethodNameList) {
        theMethodNameList.addAll(codeGenerationalOpratingContainerParam.getThisCustomFunctionNameHolder().getFunctionNameList());
    }

    /**
     * 添加本模板的方法名
     *
     * @param theMethodNameList
     */
    private void addModuleMethodNameList(ArrayList<AbstractMethodName> theMethodNameList, String moduleId) {
        String markType = getPathFind().getMarkType();

        ModuleFormatFunctionNameHolder formatVariableHolder = CodeGenerationModuleFormatFunctionNameHolder
                .getModuleFormatFunctionName(moduleId);
        if (formatVariableHolder != null) {
            theMethodNameList.addAll(formatVariableHolder.getFunctionNameList());
        }
        ModuleCustomFunctionNameHolder customVariableHolderTemp = CodeGenerationModuleCustomFunctionNameHolder
                .getModuleCustomFunctionNameHolder(moduleId);
        if (customVariableHolderTemp != null) {
            theMethodNameList.addAll(customVariableHolderTemp.getFunctionNameList());
        }
    }

}


