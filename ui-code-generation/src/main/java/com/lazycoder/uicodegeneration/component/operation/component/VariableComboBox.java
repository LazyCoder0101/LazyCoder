package com.lazycoder.uicodegeneration.component.operation.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleFormatVariableHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractCommandOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.CodeControlTabbedPane;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractFormatVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.ModuleFormatVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.VariableMeta;
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

/**
 * 变量选择
 *
 * @author admin
 */
public class VariableComboBox extends JMenuBar
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -1029570728312032645L;

    private JMenu menu;

    private VariableMenuItem selectedMenuItem = null;

    private VariableControl controlElement = new VariableControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private VariableMeta variableMeta = null;

    public VariableComboBox() {
        this.menu = new JMenu();
        this.add(menu);
        menu.setBackground(Color.white);
        menu.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        setToolTipText("(*^▽^*) 亲，记得要把需要填写的内容都填好喔");
        menu.addMenuListener(menuListener);
    }

    public VariableComboBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                            VariableControl controlElement) {
        // TODO Auto-generated constructor stub
        this();
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        setTheSize();
    }

    @SuppressWarnings("unchecked")
    public VariableComboBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                            VariableMeta variableMeta) {
        // TODO Auto-generated constructor stub
        this();
        this.controlElement = variableMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = variableMeta.getPathFind();

        this.variableMeta = variableMeta;

//        addItemListener(itemListener);
        setTheSize();
    }

    /**
     * 打开项目文件的时候才用，选择之前选好的值
     */
    public void showSelectedValue() {
        if (this.variableMeta != null) {
            Integer selectVariableId = this.variableMeta.getSelectVariableId();
            menu.removeAll();
            ArrayList<String> variableNeedDataTypeList = VariableControl.getDataTypeList(controlElement);
            if (variableNeedDataTypeList != null) {
                if (0 == variableNeedDataTypeList.size()) {// 没设置数据类型
                    uploadNullVariable();
                } else {// 有设置数据类型
                    uploadNeedVariable(variableNeedDataTypeList);
                }
                if (selectVariableId != null) {
                    setSelectedMenuItem(selectVariableId);
                }
            }
        }
    }

    /**
     * 选择指定值
     *
     * @param selectVariableId
     */
    protected void setSelectedMenuItem(Integer selectVariableId) {
        if (selectVariableId != null) {
            int menuItemNum = menu.getMenuComponentCount();
            VariableMenuItem item;
            JMenuItem itemTemp;
            for (int i = 0; i < menuItemNum; i++) {
                itemTemp = menu.getItem(i);
                if (itemTemp instanceof VariableMenuItem) {
                    item = (VariableMenuItem) itemTemp;
                    if (item.getVariable().getVariableId() == selectVariableId) {
                        selectedMenuItem = item;
                        setMenuText(selectedMenuItem.variable);
                        break;
                    }
                }
            }
        }
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


    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    /**
     * 根据组件自身设置的属性，加载相应的变量
     *
     * @return
     */
    private ArrayList<AbstractVariable> getTheCorrespondingVariableList() {
        ArrayList<AbstractVariable> variableTempList = new ArrayList<AbstractVariable>();
        AttributeUsageRange valiableRange = AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange());//本组件的使用范围

        if (AttributeUsageRange.ApplyOnlyToAddedTemplates == valiableRange) {// 仅应用于所添加的模板
            addTemplatesVariableList(variableTempList);

        } else if (AttributeUsageRange.VariableThatIncludesTheEntireOuterFunction == valiableRange) {//包括整个外部功能的自带变量
            variableTempList.addAll(addVariableThatIncludesTheEntireOuterFunction(null));

        } else if (AttributeUsageRange.VariableThatFirstLayerAndAllTheFunctionsItContains == valiableRange) {//第1层功能及其包含功能的自带变量
            variableTempList.addAll(addVariableThatFirstLayerAndAllTheFunctionsItContains());

        } else if (AttributeUsageRange.ApplyOnlyToItself == valiableRange) {// 仅在 本方法 / 仅在本格式面板使用
            addThisVariableList(variableTempList);

//			if (PathFind.commandType == pathFind.getMetaType()) {
//				GeneralCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
//						.getFirstCommandOpratingContainer();
//				if (firstCommandOpratingContainer != null) {
//					variableTempList.addAll(firstCommandOpratingContainer.getThisCustomVariableHolder().getVariableList());
//				}
//			} else if (PathFind.formatType == pathFind.getMetaType()) {
//				GeneralFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
//				if (formatContainer != null) {
//					variableTempList.addAll(formatContainer.getThisCustomVariableHolder().getVariableList());
//				}
//			}

        } else if (AttributeUsageRange.ApplyOnlyToThisModule == valiableRange) {// 仅在本模块使用（模块使用）
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();//首个功能容器不一定就是这模块
            addModuleVariableList(variableTempList, moduleId);

        } else if (AttributeUsageRange.ModulesRequiredForThisModule == valiableRange) {//本模块需要使用的模块
            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
                ArrayList<String> needModuleIdList = JSON.parseObject(
                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
                        new TypeReference<ArrayList<String>>() {
                        });
                for (String needModuleTemp : needModuleIdList) {
                    addModuleVariableList(variableTempList, needModuleTemp);
                }
            }

        } else if (AttributeUsageRange.ThisModuleAndRequiredModules == valiableRange) {//本模块以及需要使用的模块
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();
            addModuleVariableList(variableTempList, moduleId);
            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
                ArrayList<String> needModuleIdList = JSON.parseObject(
                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
                        new TypeReference<ArrayList<String>>() {
                        });
                for (String needModuleTemp : needModuleIdList) {
                    addModuleVariableList(variableTempList, needModuleTemp);
                }
            }

        } else if (AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate == valiableRange) {// 仅在该类型模板使用（可选模板使用）

            AbstractFormatVariableHolder formatVariableHolder = codeGenerationalOpratingContainerParam.getFormatControlPane()
                    .getFormatVariableHolder();
            if (formatVariableHolder != null) {
                variableTempList.addAll(formatVariableHolder.getVariableList());
            }

            CustomVariableHolder customVariableHolderTemp;
            ArrayList<AdditionalFormatControlPane> additionalFormatControlPaneList = CodeControlTabbedPane
                    .getAdditionalFormatControlPaneList(
                            codeGenerationalOpratingContainerParam.getFormatControlPane().getAdditionalSerialNumber());
            for (AdditionalFormatControlPane formatControlPane : additionalFormatControlPaneList) {
                customVariableHolderTemp = formatControlPane.getThisCustomVariableHolder();
                if (customVariableHolderTemp != null) {
                    variableTempList.addAll(customVariableHolderTemp.getVariableList());
                }
            }

        } else if (AttributeUsageRange.ApplyToAll == valiableRange) {// 应用于所有

            ArrayList<AbstractVariableHolder> allVariableHolderList = CodeGenerationFrameHolder
                    .getAllVariableHolderList(codeGenerationalOpratingContainerParam.getFormatControlPane());
            for (AbstractVariableHolder variableHolderTemp : allVariableHolderList) {
                if (variableHolderTemp.getVariableList().size() > 0) {
                    variableTempList.addAll(variableHolderTemp.getVariableList());
                }
            }
        }
        return variableTempList;
    }

    /**
     * 加载所需变量
     */
    private void uploadNeedVariable(ArrayList<String> variableNeedDataTypeList) {
        setToolTipText(null);
        boolean flag = false;
        VariableMenuItem variableMenuItem;
        ArrayList<AbstractVariable> theCorrespondingVariableList = getTheCorrespondingVariableList();
        ArrayList<AbstractVariable> needDataTypeVariableList = getNeedDataTypeVariables(
                theCorrespondingVariableList, variableNeedDataTypeList);
        ArrayList<AbstractVariable> needLabelTypeList = getNeedLabelTypeVariables(needDataTypeVariableList);
        for (AbstractVariable variableTemp : needLabelTypeList) {
            if ((controlElement.isNoUserSelectionIsRequired() == true && variableTemp.isNoUserSelectionIsRequired() == controlElement.isNoUserSelectionIsRequired()) ||
                    (controlElement.isNoUserSelectionIsRequired() == false)) {//如果选了自动选择(true)，那就只加载符合的变量为自动选择的，如果为false，则加载所有符合的
                if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
                    if (flag == false) {
                        setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
                        flag = true;
                    }
                }
                variableMenuItem = new VariableMenuItem(variableTemp);
                menu.add(variableMenuItem);
            }
        }
    }
    //如果为true:自动选择，那就加载所有相等的的
    //如果为false：所有符合的都要添加


    /**
     * 变量id为variableId的变量有改动，查看当前组件有没有选中该变量，有的话，同步更改
     */
    public void synchronousChange(int variableId) {
        if (selectedMenuItem != null) {
            AbstractVariable selectedVariable = selectedMenuItem.getVariable();
            if (selectedVariable != null) {
                if (selectedVariable.compare(variableId)) {
                    updateValue();
                }
            }
        }
        if (selectedMenuItem != null) {
            setMenuText(selectedMenuItem.variable);
        }
    }

    /**
     * 变量id为variableId的变量被删了，查看当前组件有没有选中该变量，有的话，删了
     *
     * @param variableId
     */
    public void synchronousDelete(int variableId) {
        if (selectedMenuItem != null) {
            AbstractVariable selectedVariable = selectedMenuItem.getVariable();
            if (selectedVariable != null) {
                if (selectedVariable.compare(variableId)) {// 现在选中的刚好就是要删的这个变量
                    selectedMenuItem = null;
                    updateValue();
                }
            }
        }
        if (selectedMenuItem != null) {
            setMenuText(selectedMenuItem.variable);
        }
    }

    /**
     * 筛选出要显示的变量
     *
     * @param theCorrespondingVariableList
     * @param variableNeedDataTypeList
     * @return
     */
    private ArrayList<AbstractVariable> getNeedDataTypeVariables(
            ArrayList<AbstractVariable> theCorrespondingVariableList,
            ArrayList<String> variableNeedDataTypeList) {

        ArrayList<AbstractVariable> conformDataTypeList = new ArrayList<AbstractVariable>();

        boolean flag = false;
        if (variableNeedDataTypeList.size() > 0) {
            for (AbstractVariable variableTemp : theCorrespondingVariableList) {
                flag = false;
                for (String variableDataTypeTemp : variableTemp.getDataTypeList()) {
                    for (String variableNeedDateTypeTemp : variableNeedDataTypeList) {
                        if (variableNeedDateTypeTemp.equals(variableDataTypeTemp)) {
                            conformDataTypeList.add(variableTemp);
                            flag = true;
                            break;
                        }
                    }
                    if (flag == true) {
                        break;
                    }
                }
            }
        } else {
            return theCorrespondingVariableList;
        }
        return conformDataTypeList;
    }

    /**
     * 获取需要的标签类型
     *
     * @param theCorrespondingVariableList
     * @return
     */
    private ArrayList<AbstractVariable> getNeedLabelTypeVariables(ArrayList<AbstractVariable> theCorrespondingVariableList) {
        ArrayList<AbstractVariable> conformLabelTypeList = new ArrayList<>();
        ArrayList<String> labelTypeList = VariableControl.getLabelTypeList(controlElement);
        if (labelTypeList != null && labelTypeList.size() > 0) {
            boolean flag = false;
            for (AbstractVariable variableTemp : theCorrespondingVariableList) {
                flag = false;
                for (String variableLabelTypeTemp : variableTemp.getLabelTypeList()) {
                    for (String labelTypeTemp : labelTypeList) {
                        if (labelTypeTemp.equals(variableLabelTypeTemp)) {
                            conformLabelTypeList.add(variableTemp);
                            flag = true;
                            break;
                        }
                    }
                    if (flag == true) {
                        break;
                    }
                }
            }
        } else {
            return theCorrespondingVariableList;
        }
        return conformLabelTypeList;
    }

    /**
     * 加载所有没设置的变量
     */
    private void uploadNullVariable() {
        setToolTipText(null);
        boolean flag = false;
        VariableMenuItem variableMenuItem;
        ArrayList<AbstractVariable> theCorrespondingVariableList = getTheCorrespondingVariableList();
        ArrayList<AbstractVariable> needLabelTypeVariableList = getNeedLabelTypeVariables(theCorrespondingVariableList);

        for (AbstractVariable variableTemp : needLabelTypeVariableList) {
            if ((controlElement.isNoUserSelectionIsRequired() == true && variableTemp.isNoUserSelectionIsRequired() == controlElement.isNoUserSelectionIsRequired()) ||
                    (controlElement.isNoUserSelectionIsRequired() == false)) {//如果选了自动选择(true)，那就只加载符合的变量为自动选择的，如果为false，则加载所有符合的
                if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
                    if (flag == false) {
                        setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
                        flag = true;
                    }
                }
                variableMenuItem = new VariableMenuItem(variableTemp);
                menu.add(variableMenuItem);
            }
        }
    }

    /**
     * 根据设置的数据类型加载符合的变量
     */
    private void uploadConformingVariable() {
//        AbstractVariable selectItem = (AbstractVariable) getModel()
//                .getSelectedItem();
        AbstractVariable selectItem = selectedMenuItem == null ? null : selectedMenuItem.getVariable();
        menu.removeAll();
        ArrayList<String> variableNeedDataTypeList = VariableControl.getDataTypeList(controlElement);
        if (variableNeedDataTypeList == null) {
            uploadNullVariable();
        } else {
            if (0 == variableNeedDataTypeList.size()) {// 没设置数据类型
                uploadNullVariable();
            } else {// 有设置数据类型
                uploadNeedVariable(variableNeedDataTypeList);
            }
        }
        setMenuText(selectItem);
    }

    /**
     * 根据方法名设置菜单显示的文字
     *
     * @param variable
     */
    private void setMenuText(AbstractVariable variable) {
        if (variable == null) {
            menu.setText("");
            menu.setToolTipText(null);
        } else {
            if (variable instanceof CustomVariable) {
                String variableValue = variable.getVariableValue();
                menu.setText(variableValue);
                menu.setForeground(new Color(65, 171, 252));
                if (variableValue.length() > 8) {
                    menu.setToolTipText(variableValue);
                }
            } else if (variable instanceof FormatVariable) {
                String roleOfValue = ((FormatVariable) variable).getRoleOfVariableName();
                menu.setText(roleOfValue);
                menu.setForeground(new Color(26, 115, 232));
                if (roleOfValue.length() > 8) {
                    menu.setToolTipText(roleOfValue);
                }
            }
        }
    }

    /**
     * 设置变量组件里面自动选择的值（仅在第一次创建代码生成界面的时候使用）
     */
    public void setNoUserSelectionIsRequiredValue() {
        if (controlElement.isNoUserSelectionIsRequired() == true && selectedMenuItem == null) {

            ArrayList<String> variableNeedDataTypeList = VariableControl.getDataTypeList(controlElement);
            if (variableNeedDataTypeList != null) {
                if (0 == variableNeedDataTypeList.size()) {// 没设置数据类型
                    uploadNullVariable();
                } else {// 有设置数据类型
                    uploadNeedVariable(variableNeedDataTypeList);
                }
            }
            if (menu.getMenuComponentCount() > 0) {
                JMenuItem menuItemTemp = menu.getItem(0);
                if (menuItemTemp != null && menuItemTemp instanceof VariableMenuItem) {
                    selectedMenuItem = (VariableMenuItem) menuItemTemp;
                }
                updateValue();
            }
        }
    }

    private void setTheSize() {
        Dimension dd = new Dimension(100, 30);
        setPreferredSize(dd);
        setMinimumSize(dd);
        setMaximumSize(dd);
        menu.setPreferredSize(dd);
        menu.setMinimumSize(dd);
        menu.setMaximumSize(dd);
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        if (selectedMenuItem == null) {
            CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), "");
            setMenuText(null);
        } else {
            AbstractVariable selectItem = selectedMenuItem.getVariable();
            if (selectItem != null) {
                CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), selectItem.getVariableValue());
                setMenuText(selectedMenuItem.variable);
            } else {
                CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), "");
                setMenuText(null);
            }
        }
    }

    @Override
    public void delThis() {
    }

    @Override
    public VariableControl getControlElement() {
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
        VariableMeta theModel = (VariableMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        if (selectedMenuItem != null) {
            AbstractVariable theVariable = (AbstractVariable) selectedMenuItem.getVariable();
            theModel.setSelectVariableId(theVariable.getVariableId());
        }
    }

    @Override
    public VariableMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        VariableMeta model = new VariableMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return 100;
    }

    @Override
    public void collapseThis() {
    }

    /**
     * 添加本模板的变量名
     *
     * @param theVariableList
     */
    private void addTemplatesVariableList(ArrayList<AbstractVariable> theVariableList) {
        AbstractFormatVariableHolder formatVariableHolder = codeGenerationalOpratingContainerParam.getFormatControlPane()
                .getFormatVariableHolder();
        if (formatVariableHolder != null) {//添加这个模板的格式变量
            theVariableList.addAll(formatVariableHolder.getVariableList());
        }

        CustomVariableHolder customVariableHolderTemp = codeGenerationalOpratingContainerParam
                .getFormatControlPane().getThisCustomVariableHolder();
        if (customVariableHolderTemp != null) {//添加这个模板的自定义变量
            theVariableList.addAll(customVariableHolderTemp.getVariableList());
        }

        AbstractFormatControlPane formatControlPane = this.codeGenerationalOpratingContainerParam.getFormatControlPane();
        if (formatControlPane != null) {
            ArrayList<ModuleRelatedParam> formatControlPaneUseModuleRelatedParamList = formatControlPane.getUseModuleRelatedParamList();
            ArrayList<ModuleFormatVariableHolder> fvlist = CodeGenerationModuleFormatVariableHolder.getModuleFormatVariableList(formatControlPaneUseModuleRelatedParamList);
            for (ModuleFormatVariableHolder moduleFormatVariableHolder : fvlist) {//添加这个模板所有模块的格式变量
                theVariableList.addAll(moduleFormatVariableHolder.getVariableList());
            }
            ArrayList<ModuleCustomVariableHolder> cvlist = CodeGenerationModuleCustomVariableHolder.getModuleCustomVariableList(formatControlPaneUseModuleRelatedParamList);
            for (ModuleCustomVariableHolder moduleCustomVariableHolder : cvlist) {//添加这个模板所有模块的自定义变量
                theVariableList.addAll(moduleCustomVariableHolder.getVariableList());
            }

            ArrayList<OpratingContainerInterface> opratingContainerList = formatControlPane.getAllOpratingContainer();
            if (opratingContainerList != null) {
                for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                    theVariableList.addAll(opratingContainer.getThisCustomVariableHolder().getVariableList());
                }
            }
        }
    }


    /**
     * 添加本功能的方法名
     *
     * @param theVariableList
     */
    private void addThisVariableList(ArrayList<AbstractVariable> theVariableList) {//添加这个功能自己的自定义变量
        theVariableList.addAll(codeGenerationalOpratingContainerParam.getThisCustomVariableHolder().getVariableList());
    }

    /**
     * 外层全部
     *
     * @param opratingContainer 调用时直接传null
     * @return
     */
    private ArrayList<AbstractVariable> addVariableThatIncludesTheEntireOuterFunction(OpratingContainerInterface opratingContainer) {
        ArrayList<AbstractVariable> list = new ArrayList<>();
        if (opratingContainer == null) {
            list.addAll(codeGenerationalOpratingContainerParam.getThisCustomVariableHolder().getVariableList());
            OpratingContainerInterface parentOpratingContainer = codeGenerationalOpratingContainerParam.getParentOpratingContainer();
            if (parentOpratingContainer != null) {
                ArrayList<AbstractVariable> nextList = addVariableThatIncludesTheEntireOuterFunction(parentOpratingContainer);
                list.addAll(nextList);
            }
        } else {
            if (opratingContainer instanceof AbstractCommandOpratingContainer) {
                AbstractCommandOpratingContainer commandOpratingContainer = (AbstractCommandOpratingContainer) opratingContainer;
                CustomVariableHolder thisCustomVariableHolder = opratingContainer.getThisCustomVariableHolder();
                if (thisCustomVariableHolder != null) {
                    list.addAll(thisCustomVariableHolder.getVariableList());
                }
                ArrayList<AbstractVariable> nextList = addVariableThatIncludesTheEntireOuterFunction(commandOpratingContainer.getParentOpratingContainer());
                list.addAll(nextList);

            } else if (opratingContainer instanceof AbstractFormatContainer) {
                AbstractFormatContainer formatContainer = (AbstractFormatContainer) opratingContainer;
                CustomVariableHolder thisCustomVariableHolder = formatContainer.getThisCustomVariableHolder();
                if (thisCustomVariableHolder != null) {
                    list.addAll(thisCustomVariableHolder.getVariableList());
                }
            }
        }
        return list;
    }

    /**
     * 获取首层以及其包含的所有功能的自带变量
     *
     * @return
     */
    private ArrayList<AbstractVariable> addVariableThatFirstLayerAndAllTheFunctionsItContains() {
        ArrayList<AbstractVariable> list = new ArrayList<>();
        if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
            AbstractCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam.getFirstCommandOpratingContainer();
            if (firstCommandOpratingContainer != null) {
                ArrayList<OpratingContainerInterface> opratingContainerList = firstCommandOpratingContainer.getAllOpratingContainer();
                CustomVariableHolder customVariableHolder;
                for (OpratingContainerInterface containerInterface : opratingContainerList) {
                    customVariableHolder = containerInterface.getThisCustomVariableHolder();
                    if (customVariableHolder != null) {
                        list.addAll(customVariableHolder.getVariableList());
                    }
                }
            }
        }

        if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
            AbstractFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
            if (formatContainer != null) {
                ArrayList<OpratingContainerInterface> opratingContainerList = formatContainer.getAllOpratingContainer();
                CustomVariableHolder customVariableHolder;
                for (OpratingContainerInterface containerInterface : opratingContainerList) {
                    customVariableHolder = containerInterface.getThisCustomVariableHolder();
                    if (customVariableHolder != null) {
                        list.addAll(customVariableHolder.getVariableList());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 添加本模板的方法名
     *
     * @param theVariableList
     */
    private void addModuleVariableList(
            ArrayList<AbstractVariable> theVariableList,
            String moduleId) {
        ModuleFormatVariableHolder formatVariableHolder = CodeGenerationModuleFormatVariableHolder
                .getModuleFormatVariable(moduleId);
        if (formatVariableHolder != null) {
            theVariableList.addAll(formatVariableHolder.getVariableList());
        }
        CustomVariableHolder customVariableHolderTemp = CodeGenerationModuleCustomVariableHolder
                .getModuleCustomVariableHolder(moduleId);
        if (customVariableHolderTemp != null) {
            theVariableList.addAll(customVariableHolderTemp.getVariableList());
        }
    }

    class VariableMenuItem extends JMenuItem {

        @Getter
        private AbstractVariable variable;

        public VariableMenuItem(AbstractVariable variable) {
            this.variable = variable;
            if (variable != null) {
                if (variable instanceof CustomVariable) {
                    setForeground(new Color(65, 171, 252));
                    this.setText(variable.getVariableValue());

                } else if (variable instanceof FormatVariable) {
                    setForeground(new Color(26, 115, 232));
                    this.setText(((FormatVariable) variable).getRoleOfVariableName());
                }
            }
            if (null == variable.getVariableValue() || "".equals(variable.getVariableValue())) {
                this.setToolTipText(variable.getToolTipTextOfHaveNotValue());
//            setBackground(new Color(255, 206, 105));
            } else {
                this.setToolTipText(variable.getToolTipTextOfHaveNotValue());
//                this.setBackground(defaultColor);
            }
            this.addActionListener(actionListener);
        }


        private ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedMenuItem = VariableComboBox.VariableMenuItem.this;
                updateValue();
            }
        };

//        class TheMenuUI extends FlatMenuItemUI {
//            public TheMenuUI() {
////                selectionBackground = new Color(255, 255, 222);
//                selectionBackground = new Color(255, 206, 105);
//            }
//        }

    }

}

//@SuppressWarnings("hiding")
//public class VariableComboBox extends VariableComboBoxForCodeGeneration<com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable>
//        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = -1029570728312032645L;
//
//    private VariableControl controlElement = new VariableControl();
//
//    private PathFind pathFind;
//
//    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;
//
//    private VariableMeta variableMeta = null;
//
//    @SuppressWarnings("unchecked")
//    public VariableComboBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
//                            VariableControl controlElement) {
//        // TODO Auto-generated constructor stub
//        super();
//        this.setRenderer(new VariableCellRenderer());
//        this.setEditor(new VariableComboEditor());
//        setUI(new VariableComboBoxUI());
//
//        this.controlElement = controlElement;
//        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
//
//        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
//                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
//        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
//        pathFindTemp.getPathList().add(pathFindCell);
//        this.pathFind = pathFindTemp;
//
//        setTheSize();
//        addPopupMenuListener(popupMenuListener);
//        setToolTipText("(*^▽^*) 亲，记得要把需要填写的内容都填好喔");
//    }
//
//    @SuppressWarnings("unchecked")
//    public VariableComboBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
//                            VariableMeta variableMeta) {
//        // TODO Auto-generated constructor stub
//        super();
//        this.setRenderer(new VariableCellRenderer());
//        this.setEditor(new VariableComboEditor());
//        setUI(new VariableComboBoxUI());
//        this.controlElement = variableMeta.getControlElement();
//        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
//        this.pathFind = variableMeta.getPathFind();
//
//        this.variableMeta = variableMeta;
//
////        addItemListener(itemListener);
//        setTheSize();
//        addPopupMenuListener(popupMenuListener);
//
//    }
//
//
//    private PopupMenuListener popupMenuListener = new PopupMenuListener() {
//
//        @Override
//        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
//            // TODO Auto-generated method stub
//            removePopupMenuListener(popupMenuListener);
//            uploadConformingVariable();
//            addPopupMenuListener(popupMenuListener);
//        }
//
//        @Override
//        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//            // TODO Auto-generated method stub
//            updateValue();
//        }
//
//        @Override
//        public void popupMenuCanceled(PopupMenuEvent e) {
//            // TODO Auto-generated method stub
//        }
//    };
//
////    private ItemListener itemListener = new ItemListener() {
////        @Override
////        public void itemStateChanged(ItemEvent e) {
////            if (e.getStateChange() == ItemEvent.SELECTED) {
////                updateValue();
////            }
////        }
////    };
//
//    @Override
//    public OpratingContainerInterface getThisOpratingContainer() {
//        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
//    }
//
//    /**
//     * 根据组件自身设置的属性，加载相应的变量
//     *
//     * @return
//     */
//    private ArrayList<AbstractVariable> getTheCorrespondingVariableList() {
//        ArrayList<AbstractVariable> variableTempList = new ArrayList<AbstractVariable>(),
//                variableList = new ArrayList<>();
//        AttributeUsageRange valiableRange = AttributeUsageRange.dictionaryValueToVariableUsageRange(
//                controlElement.getTheAvaliableRange());//本组件的使用范围
//
//        if (AttributeUsageRange.ApplyOnlyToAddedTemplates == valiableRange) {// 仅应用于所添加的模板
//            addTemplatesVariableList(variableTempList);
//
//        } else if (AttributeUsageRange.ApplyOnlyToItself == valiableRange) {// 仅在 本方法 / 仅在本格式面板使用
//            addThisVariableList(variableTempList);
//
////			if (PathFind.commandType == pathFind.getMetaType()) {
////				GeneralCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
////						.getFirstCommandOpratingContainer();
////				if (firstCommandOpratingContainer != null) {
////					variableTempList.addAll(firstCommandOpratingContainer.getThisCustomVariableHolder().getVariableList());
////				}
////			} else if (PathFind.formatType == pathFind.getMetaType()) {
////				GeneralFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
////				if (formatContainer != null) {
////					variableTempList.addAll(formatContainer.getThisCustomVariableHolder().getVariableList());
////				}
////			}
//
//        } else if (AttributeUsageRange.ApplyOnlyToThisModule == valiableRange) {// 仅在本模块使用（模块使用）
//            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();//首个功能容器不一定就是这模块
//            addModuleVariableList(variableTempList, moduleId);
//
//        } else if (AttributeUsageRange.ModulesRequiredForThisModule == valiableRange) {
//            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
//                ArrayList<String> needModuleIdList = JSON.parseObject(
//                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
//                        new TypeReference<ArrayList<String>>() {
//                        });
//                for (String needModuleTemp : needModuleIdList) {
//                    addModuleVariableList(variableTempList, needModuleTemp);
//                }
//            }
//
//        } else if (AttributeUsageRange.ThisModuleAndRequiredModules == valiableRange) {
//            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();
//            addModuleVariableList(variableTempList, moduleId);
//            if (Module.NEW_STATE.equals(codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam()) == false) {
//                ArrayList<String> needModuleIdList = JSON.parseObject(
//                        codeGenerationalOpratingContainerParam.getModule().getNeedModuleParam(),
//                        new TypeReference<ArrayList<String>>() {
//                        });
//                for (String needModuleTemp : needModuleIdList) {
//                    addModuleVariableList(variableTempList, needModuleTemp);
//                }
//            }
//
//        } else if (AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate == valiableRange) {// 仅在该类型模板使用（可选模板使用）
//
//            AbstractFormatVariableHolder formatVariableHolder = codeGenerationalOpratingContainerParam.getFormatControlPane()
//                    .getFormatVariableHolder();
//            if (formatVariableHolder != null) {
//                variableTempList.addAll(formatVariableHolder.getVariableList());
//            }
//
//            CustomVariableHolder customVariableHolderTemp;
//            ArrayList<AdditionalFormatControlPane> additionalFormatControlPaneList = CodeControlTabbedPane
//                    .getAdditionalFormatControlPaneList(
//                            codeGenerationalOpratingContainerParam.getFormatControlPane().getAdditionalSerialNumber());
//            for (AdditionalFormatControlPane formatControlPane : additionalFormatControlPaneList) {
//                customVariableHolderTemp = formatControlPane.getThisCustomVariableHolder();
//                if (customVariableHolderTemp != null) {
//                    variableTempList.addAll(customVariableHolderTemp.getVariableList());
//                }
//            }
//
//        } else if (AttributeUsageRange.ApplyToAll == valiableRange) {// 应用于所有
//
//            ArrayList<AbstractVariableHolder> allVariableHolderList = CodeGenerationFrameHolder
//                    .getAllVariableHolderList(codeGenerationalOpratingContainerParam.getFormatControlPane());
//            for (AbstractVariableHolder variableHolderTemp : allVariableHolderList) {
//                variableTempList.addAll(variableHolderTemp.getVariableList());
//            }
//        }
//        variableList = variableTempList
//        return variableList;
//    }
//
//    /**
//     * 加载所需变量
//     */
//    @SuppressWarnings("unchecked")
//    private void uploadNeedVariable(ArrayList<String> variableNeedDataTypeList) {
//        setToolTipText(null);
//        boolean flag = false;
//        VariableComboBoxModel<com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable> model = new VariableComboBoxModel<>();
//        ArrayList<AbstractVariable> theCorrespondingVariableList = getTheCorrespondingVariableList();
//        ArrayList<AbstractVariable> needVariableList = getNeedVariables(
//                theCorrespondingVariableList, variableNeedDataTypeList);
//        for (AbstractVariable variableTemp : needVariableList) {
//            if (variableTemp instanceof CustomVariable) {
//                if (((CustomVariable) variableTemp).isNoUserSelectionIsRequired() == controlElement.isNoUserSelectionIsRequired()) {
//                    if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
//                        if (flag == false) {
//                            setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
//                            flag = true;
//                        }
//                    }
//                    model.addElement(variableTemp);
//                }
//            } else if (variableTemp instanceof FormatVariable) {
//                if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
//                    if (flag == false) {
//                        setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
//                        flag = true;
//                    }
//                }
//                model.addElement(variableTemp);
//            }
//        }
//        setModel(model);
//    }
//
//    /**
//     * 根据selectedVariableId选中对应的选项
//     *
//     * @param selectedVariableId
//     */
//    protected void selectedTheVariable(int selectedVariableId) {
//        AbstractVariable temp;
//        ComboBoxModel<AbstractVariable> model = getModel();
//        for (int i = 0; i < model.getSize(); i++) {
//            temp = model.getElementAt(i);
//            if (temp.compare(selectedVariableId)) {
//                setSelectedIndex(i);
//                break;
//            }
//        }
//    }
//
////    AbstractVariable temp = null;
////    ComboBoxModel<AbstractVariable> model = getModel();
////    AbstractVariable selectItem = (AbstractVariable) anObject;//= variableComboBox.getModel().getElementAt(list.getSelectedIndex());
////    int variableId = selectItem == null ? -1 : selectItem.getVariableId();
////            if (variableId != -1) {
////        for (int i = 0; i < model.getSize(); i++) {
////            temp = model.getElementAt(i);
////            if (temp.compare(selectItem.getVariableId())) {
////                setSelectedIndex(i);
////                break;
////            }
////        }
////    }
//////            variableComboBox.setSelectedItem(o);
//
//
//    @Override
//    public void setSelectedItem(Object anObject) {
//        AbstractVariable element, inputParam, oldSelection, objectToSelect = null;
//        if (anObject instanceof AbstractVariable) {
//            inputParam = (AbstractVariable) anObject;
//            oldSelection = (AbstractVariable) selectedItemReminder;
//            if (oldSelection == null || oldSelection.getVariableId() != inputParam.getVariableId()) {
//
//                if (anObject != null && !isEditable()) {
//                    // For non editable combo boxes, an invalid selection
//                    // will be rejected.
//                    boolean found = false;
//                    for (int i = 0; i < getModel().getSize(); i++) {
//                        element = getModel().getElementAt(i);
//                        if (inputParam.getVariableId() == element.getVariableId()) {
//                            found = true;
//                            objectToSelect = element;
//                            break;
//                        }
//                    }
//                    if (found) {
//                        getModel().setSelectedItem(objectToSelect);
//                        getEditor().setItem(anObject);
//                        selectedItemChanged();
//                        fireActionEvent();
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public Object getSelectedItem() {
//        return getModel().getSelectedItem();
//    }
//
//    /**
//     * 变量id为variableId的变量有改动，查看当前组件有没有选中该变量，有的话，同步更改
//     */
//    public void synchronousChange(int variableId) {
//        AbstractVariable selectedVariable = (AbstractVariable) getSelectedItem();
//        if (selectedVariable != null) {
//            if (selectedVariable.compare(variableId)) {
//                updateValue();
//            }
//        }
//    }
//
//    /**
//     * 变量id为variableId的变量被删了，查看当前组件有没有选中该变量，有的话，删了
//     *
//     * @param variableId
//     */
//    public void synchronousDelete(int variableId) {
//        AbstractVariable selectedVariable = (AbstractVariable) getSelectedItem();
//        if (selectedVariable != null) {
//            if (selectedVariable.compare(variableId)) {// 现在选中的刚好就是要删的这个变量
//                uploadConformingVariable();
//                if (getModel().getSize() > 0) {
//                    setSelectedIndex(0);
//                }
//                updateValue();
//            }
//        }
//    }
//
//    /**
//     * 筛选出要显示的变量
//     *
//     * @param theCorrespondingVariableList
//     * @param variableNeedDataTypeList
//     * @return
//     */
//    private ArrayList<AbstractVariable> getNeedVariables(
//            ArrayList<AbstractVariable> theCorrespondingVariableList,
//            ArrayList<String> variableNeedDataTypeList) {
//
//        ArrayList<AbstractVariable> theVariables = new ArrayList<AbstractVariable>();
//
//        boolean flag = false;
//        for (AbstractVariable variableTemp : theCorrespondingVariableList) {
//            for (String variableDataTypeTemp : variableTemp.getVariableType()) {
//                for (String variableNeedDateTypeTemp : variableNeedDataTypeList) {
//                    if (variableNeedDateTypeTemp.equals(variableDataTypeTemp)) {
//                        theVariables.add(variableTemp);
//                        flag = true;
//                        break;
//                    }
//                }
//                if (flag == true) {
//                    break;
//                }
//            }
//        }
//        return theVariables;
//    }
//
//    /**
//     * 加载所有没设置的变量
//     */
//    @SuppressWarnings("unchecked")
//    private void uploadNullVariable() {
//        setToolTipText(null);
//        boolean flag = false;
//        VariableComboBoxModel<AbstractVariable> model = new VariableComboBoxModel<>();
//        ArrayList<AbstractVariable> theCorrespondingVariableList = getTheCorrespondingVariableList();
//        for (AbstractVariable variableTemp : theCorrespondingVariableList) {
//
//            if (variableTemp instanceof CustomVariable) {
//                if (((CustomVariable) variableTemp).isNoUserSelectionIsRequired() == controlElement.isNoUserSelectionIsRequired()) {
//                    if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
//                        if (flag == false) {
//                            setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
//                            flag = true;
//                        }
//                    }
//                    model.addElement(variableTemp);
//                }
//            } else if (variableTemp instanceof FormatVariable) {
//                if (variableTemp.getVariableValue() == null || "".equals(variableTemp.getVariableValue())) {
//                    if (flag == false) {
//                        setToolTipText("(*^▽^*) 亲，再找找看，还有内容没填喔");
//                        flag = true;
//                    }
//                }
//                model.addElement(variableTemp);
//            }
//        }
//        setModel(model);
//    }
//
//    /**
//     * 根据设置的数据类型加载符合的变量
//     */
//    private void uploadConformingVariable() {
////        AbstractVariable selectItem = (AbstractVariable) getModel()
////                .getSelectedItem();
//        AbstractVariable selectItem = (AbstractVariable) getSelectedItem();
//        int variableId = selectItem == null ? -1 : selectItem.getVariableId();
////        removeAllItems();
//        ArrayList<String> variableNeedLabelTypeList = VariableControl.getDataTypeList(controlElement);
//        if (variableNeedLabelTypeList != null) {
//            if (0 == variableNeedLabelTypeList.size()) {// 没设置数据类型
//                uploadNullVariable();
//            } else {// 有设置数据类型
//                uploadNeedVariable(variableNeedLabelTypeList);
//            }
//        }
//        if (variableId != -1) {
//            selectedTheVariable(variableId);
//        }
//    }
//
//    /**
//     * 设置变量组件里面不需要用户选择的值（仅在第一次创建代码生成界面的时候使用）
//     */
//    public void setNoUserSelectionIsRequiredValue() {
//        if (controlElement.isNoUserSelectionIsRequired() == true && getItemCount() == 0) {
//
//            ArrayList<String> variableNeedLabelTypeList = VariableControl.getDataTypeList(controlElement);
//            if (variableNeedLabelTypeList != null) {
//                if (0 == variableNeedLabelTypeList.size()) {// 没设置数据类型
//                    uploadNullVariable();
//                } else {// 有设置数据类型
//                    uploadNeedVariable(variableNeedLabelTypeList);
//                }
//            }
//            if (getModel().getSize() > 0) {
//                setSelectedIndex(0);
//                updateValue();
//            }
//        }
//    }
//
//    private void setTheSize() {
//        Dimension dd = new Dimension(160, 30);
//        setPreferredSize(dd);
//        setMinimumSize(dd);
//        setMaximumSize(dd);
//    }
//
//    @Override
//    public void updateValue() {
//        // TODO Auto-generated method stub
////        AbstractVariable selectItem = (AbstractVariable) getModel()
////                .getSelectedItem();
//        AbstractVariable selectItem = (AbstractVariable) getSelectedItem();
//        if (selectItem != null && getModel().getSize() > 0) {
//            CodeGenerationStaticFunction.setValue(this, selectItem.getVariableValue());
//        } else {
//            CodeGenerationStaticFunction.setValue(this, "");
//        }
//    }
//
//    @Override
//    public void delThis() {
//    }
//
//    @Override
//    public VariableControl getControlElement() {
//        return controlElement;
//    }
//
//    @Override
//    public PathFind getPathFind() {
//        return pathFind;
//    }
//
//    @Override
//    public int getCodeSerialNumber() {
//        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
//    }
//
//    @Override
//    public AbstractOperatingPane getOperatingComponentPlacePane() {
//        // TODO Auto-generated method stub
//        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
//    }
//
//    @Override
//    public void setParam(FormatStructureModelInterface model) {
//        // TODO Auto-generated method stub
//        VariableMeta theModel = (VariableMeta) model;
//        theModel.setControlElement(controlElement);
//        theModel.setPathFind(pathFind);
//        if (getSelectedIndex() > 0) {
//            AbstractVariable theVariable = (AbstractVariable) getSelectedItem();
//            theModel.setSelectVariableId(theVariable.getVariableId());
//        }
//    }
//
//    @Override
//    public VariableMeta getFormatStructureModel() {
//        // TODO Auto-generated method stub
//        VariableMeta model = new VariableMeta();
//        setParam(model);
//        return model;
//    }
//
//    @Override
//    public int getComponentWidth() {
//        // TODO Auto-generated method stub
//        return 140;
//    }
//
//    @Override
//    public void collapseThis() {
//    }
//
//    /**
//     * 添加本模板的方法名
//     *
//     * @param theVariableList
//     */
//    private void addTemplatesVariableList(ArrayList<AbstractVariable> theVariableList) {
//        AbstractFormatVariableHolder formatVariableHolder = codeGenerationalOpratingContainerParam.getFormatControlPane()
//                .getFormatVariableHolder();
//        if (formatVariableHolder != null) {
//            theVariableList.addAll(formatVariableHolder.getVariableList());
//        }
//
//        CustomVariableHolder customVariableHolderTemp = codeGenerationalOpratingContainerParam
//                .getFormatControlPane().getThisCustomVariableHolder();
//        if (customVariableHolderTemp != null) {
//            theVariableList.addAll(customVariableHolderTemp.getVariableList());
//        }
//    }
//
//    /**
//     * 添加本模板的方法名
//     *
//     * @param theVariableList
//     */
//    private void addThisVariableList(ArrayList<AbstractVariable> theVariableList) {
//        theVariableList.addAll(codeGenerationalOpratingContainerParam.getThisCustomVariableHolder().getVariableList());
//    }
//
//    /**
//     * 添加本模板的方法名
//     *
//     * @param theVariableList
//     */
//    private void addModuleVariableList(
//            ArrayList<AbstractVariable> theVariableList,
//            String moduleId) {
//        String markType = getPathFind().getMarkType();
//
//        if (MarkElementName.FUNCTION_MARK.equals(markType) || MarkElementName.SET_MARK.equals(markType)
//                || MarkElementName.INIT_MARK.equals(markType)) {//本模块使用这个范围，目前在命令容器只有这三个才能选
//            ModuleFormatVariableHolder formatVariableHolder = CodeGenerationModuleFormatVariableHolder
//                    .getModuleFormatVariable(moduleId);
//            if (formatVariableHolder != null) {
//                theVariableList.addAll(formatVariableHolder.getVariableList());
//                ArrayList<com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable> vl = formatVariableHolder.getVariableList();
//            }
//            CustomVariableHolder customVariableHolderTemp = CodeGenerationModuleCustomVariableHolder
//                    .getModuleCustomVariableHolder(moduleId);
//            if (customVariableHolderTemp != null) {
//                theVariableList.addAll(customVariableHolderTemp.getVariableList());
//            }
//
//        } else if (MarkElementName.MODULE_CONTROL.equals(markType)) {//本模块使用这个范围，目前在格式容器只有模块控制才能选
//
//            AbstractFormatVariableHolder formatVariableHolder = CodeGenerationModuleFormatVariableHolder
//                    .getModuleFormatVariable(moduleId);
//            if (formatVariableHolder != null) {
//                theVariableList.addAll(formatVariableHolder.getVariableList());
//            }
//            CustomVariableHolder customVariableHolderTemp = CodeGenerationModuleCustomVariableHolder
//                    .getModuleCustomVariableHolder(moduleId);
//            if (customVariableHolderTemp != null) {
//                theVariableList.addAll(customVariableHolderTemp.getVariableList());
//            }
//        }
//    }
//
//}

// 此类用于显示提示信息
//class VariableCellRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object>, Serializable {
//
////    private static String nullStr = HTMLText.createHtmlContent("这里还没写内容", HtmlPar.red,true);
//
////    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
//
////    JLabel renderer;
//
//    public VariableCellRenderer() {
//        super();
//    }
//
//    @Override
//    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
//                                                  boolean cellHasFocus) {
//        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//
////        // 下拉列表每个选项显示的就是这个
////        renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//
//        String theText = null; // 显示内容
//
//        AbstractVariable variable = (AbstractVariable) value;
//        if (value instanceof CustomVariable) {
//            theText = ((CustomVariable) value).getVariableValue();
//
//        } else if (value instanceof FormatVariable) {
//            theText = ((FormatVariable) value).getRoleOfVariableName();
//        }
//        if (value != null) {
////            if (theText==null&&"".equals(theText)){
////                renderer.setText(nullStr);
////            }else {
////                renderer.setText(theText);
////            }
//            setText(theText);
//            if (variable.getVariableValue() == null || "".equals(variable.getVariableValue())) {
//                setToolTipText(variable.getToolTipTextOfHaveNotValue());//原本设计空值也显示出来，提示用户去填，结果下拉框组件默认不显示空值，没有重写对应方法
//            } else {
//                setToolTipText(variable.getToolTipTextOfHaveValue());
////                renderer.setBackground(defaultColor);
//            }
//        }
//
//        return this;
//    }
//
//}
//
//
//class VariableComboEditor extends BasicComboBoxEditor {
//
//    //    private JLabel labelItem = new JLabel();
////    private JPanel panel = new JPanel();
//    private AbstractVariable selectedItem;
//
//    public VariableComboEditor() {
//        super();
////        labelItem.setOpaque(false);
////        labelItem.setHorizontalAlignment(JLabel.CENTER);
////        labelItem.setForeground(Color.WHITE);
////
////        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
////        panel.setBackground(Color.BLUE);
////        panel.add(labelItem);
//    }
//
//    @Override
//    public AbstractVariable getItem() {
//        return this.selectedItem;
//    }
//
//    @Override
//    public void setItem(Object item) {
//        if (item == null || !(item instanceof AbstractVariable)) {
//            return;
//        }
//        this.selectedItem = (AbstractVariable) item;
//        if (item instanceof CustomVariable) {
//            editor.setText(this.selectedItem.getVariableValue());
//            editor.setForeground(new Color(65, 171, 252));
//
//        } else if (item instanceof FormatVariable) {
//            editor.setText(((FormatVariable) this.selectedItem).getRoleOfVariableName());
//            editor.setForeground(new Color(26, 115, 232));
//        }
//    }
//}
//
//class VariableComboBoxUI extends FlatComboBoxUI {
//
//    @Override
//    protected ComboPopup createPopup() {
//        BasicComboPopup comboPopup = (BasicComboPopup) super.createPopup();
//        VariableMouseAdapter variableMouseAdapter = new VariableMouseAdapter(comboPopup.getList(), comboBox);
//        if (variableMouseAdapter != null) {
//            comboPopup.getList().addMouseListener(variableMouseAdapter);
////
////            if (propertyChangeListener != null) {
////                comboBox.removePropertyChangeListener( propertyChangeListener );
////            }
////            if (itemListener != null) {
////                comboBox.removeItemListener( itemListener );
////            }
////            comboPopup.uninstallComboBoxModelListeners(comboBox.getModel());
////            comboPopup.uninstallKeyboardActions();
////            comboPopup.uninstallListListeners();
////            comboPopup.uninstallScrollerListeners();
//        }
//        return comboPopup;
//    }
//
//    class VariableMouseAdapter extends java.awt.event.MouseAdapter {
//        protected javax.swing.JList list;
//        protected VariableComboBox variableComboBox;
//
//        private VariableMouseAdapter(javax.swing.JList list, javax.swing.JComboBox comboBox) {
//            this.list = list;
//            this.variableComboBox = (VariableComboBox) comboBox;
//        }
//
//        @Override
//        public void mousePressed(java.awt.event.MouseEvent anEvent) {
//            //           int index = list.getSelectedIndex();
//            AbstractVariable selectItem = variableComboBox.getModel().getElementAt(list.getSelectedIndex());
////            int variableId = selectItem == null ? -1 : selectItem.getVariableId();
////            if (variableId != -1) {
////                variableComboBox.selectedTheVariable(variableId);
////            }
//////            variableComboBox.setSelectedItem(o);
//            variableComboBox.setSelectedItem(selectItem);
//
//        }
//
//    }
//}
//
//class VariableComboBoxModel<E> extends DefaultComboBoxModel<E> {
//
//    Vector<E> objects;
//    Object selectedObject;
//
//    public VariableComboBoxModel() {
//        objects = new Vector<E>();
//    }
//
//    public VariableComboBoxModel(final E[] items) {
//        objects = new Vector<E>(items.length);
//
//        int i, c;
//        for (i = 0, c = items.length; i < c; i++) {
//            objects.addElement(items[i]);
//        }
//
//        if (getSize() > 0) {
//            selectedObject = getElementAt(0);
//        }
//    }
//
//    public VariableComboBoxModel(Vector<E> v) {
//        objects = v;
//
//        if (getSize() > 0) {
//            selectedObject = getElementAt(0);
//        }
//    }
//
//    @Override
//    public void setSelectedItem(Object anObject) {
//        if (anObject instanceof AbstractVariable && selectedObject instanceof AbstractVariable) {
//            AbstractVariable variable = (AbstractVariable) anObject,
//                    selectedVariable = (AbstractVariable) selectedObject;
//
//            if ((selectedObject != null && selectedVariable.getVariableId() != variable.getVariableId()) ||
//                    selectedObject == null && anObject != null) {
//                selectedObject = anObject;
//                fireContentsChanged(this, -1, -1);
//            }
//        } else {
//
//            if ((selectedObject != null && !selectedObject.equals(anObject)) ||
//                    selectedObject == null && anObject != null) {
//                selectedObject = anObject;
//                fireContentsChanged(this, -1, -1);
//            }
//        }
//    }
//
//    @Override
//    public Object getSelectedItem() {
//        return selectedObject;
//    }
//
//    @Override
//    public int getSize() {
//        return objects.size();
//    }
//
//    @Override
//    public E getElementAt(int index) {
//        if (index >= 0 && index < objects.size()) {
//            return objects.elementAt(index);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public int getIndexOf(Object anObject) {
//        return objects.indexOf(anObject);
//    }
//
//    @Override
//    public void addElement(E anObject) {
//        objects.addElement(anObject);
//        fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
//        if (objects.size() == 1 && selectedObject == null && anObject != null) {
//            setSelectedItem(anObject);
//        }
//    }
//
//    @Override
//    public void insertElementAt(E anObject, int index) {
//        objects.insertElementAt(anObject, index);
//        fireIntervalAdded(this, index, index);
//    }
//
//    @Override
//    public void removeElementAt(int index) {
//        if (getElementAt(index) == selectedObject) {
//            if (index == 0) {
//                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
//            } else {
//                setSelectedItem(getElementAt(index - 1));
//            }
//        }
//
//        objects.removeElementAt(index);
//
//        fireIntervalRemoved(this, index, index);
//    }
//
//    @Override
//    public void removeElement(Object anObject) {
//        int index = objects.indexOf(anObject);
//        if (index != -1) {
//            removeElementAt(index);
//        }
//    }
//
//    @Override
//    public void removeAllElements() {
//        if (objects.size() > 0) {
//            int firstIndex = 0;
//            int lastIndex = objects.size() - 1;
//            objects.removeAllElements();
//            selectedObject = null;
//            fireIntervalRemoved(this, firstIndex, lastIndex);
//        } else {
//            selectedObject = null;
//        }
//    }
//
//    @Override
//    public void addAll(Collection<? extends E> c) {
//        if (c.isEmpty()) {
//            return;
//        }
//        int startIndex = getSize();
//        objects.addAll(c);
//        fireIntervalAdded(this, startIndex, getSize() - 1);
//    }
//
//    @Override
//    public void addAll(int index, Collection<? extends E> c) {
//        if (index < 0 || index > getSize()) {
//            throw new ArrayIndexOutOfBoundsException("index out of range: " +
//                    index);
//        }
//
//        if (c.isEmpty()) {
//            return;
//        }
//
//        objects.addAll(index, c);
//        fireIntervalAdded(this, index, index + c.size() - 1);
//    }
//
//}

