package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleFormatFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.MethodChooseComboBoxForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractCommandOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AbstractFormatContainer;
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * 方法选择（该组件使用时有些问题，已废弃）
 *
 * @author admin
 */
@SuppressWarnings("hiding")
@Deprecated
public class MethodChooseCombobox extends MethodChooseComboBoxForCodeGeneration
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {
//public class MethodChooseCombobox extends MethodChooseComboBoxForCodeGeneration<com.lazycoder.uicodegeneration.generalframe.functionname.GeneralMethodName>
//		implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -4828855893739793636L;

    private MethodChooseControl controlElement = new MethodChooseControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private MethodChooseMeta methodChooseMeta = null;

    @SuppressWarnings("unchecked")
    public MethodChooseCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                MethodChooseControl controlElement) {
        // TODO Auto-generated constructor stub
        super();
        this.setRenderer(new FunctionNameCellRenderer());
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        addItemListener(itemListener);
        addPopupMenuListener(popupMenuListener);
        setTheSize();
    }

    @SuppressWarnings("unchecked")
    public MethodChooseCombobox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                MethodChooseMeta methodChooseMeta) {
        // TODO Auto-generated constructor stub
        super();
        this.setRenderer(new FunctionNameCellRenderer());
        this.controlElement = methodChooseMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = methodChooseMeta.getPathFind();

        this.methodChooseMeta = methodChooseMeta;

        addItemListener(itemListener);
        addPopupMenuListener(popupMenuListener);
        setTheSize();
    }

    private ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            updateValue();
        }
    };

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            uploadConformingVariable();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
//			updateValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }
    };

    /**
     * 根据原来设置的值来设值
     */
    public void setOriginauploadConformingMethodNamelItem() {
        removeAllItems();
        if (this.methodChooseMeta != null) {
            ArrayList<String> dataTypeList = MethodChooseControl.getLabelTypeList(controlElement);
            if (dataTypeList != null) {
                if (0 == dataTypeList.size()) {// 没设置数据类型
                    uploadNullFunctionName();
                } else {// 有设置数据类型
                    uploadNeedFunctionName(dataTypeList);
                }
            }
//			if (methodChooseMeta.getSelectMethodNameId() != null) {
//				selectedTheFuncionName(methodChooseMeta.getSelectMethodNameId());
//			}
        }
    }

    private ArrayList<AbstractMethodName> getTheCorrespondingFunctionNameList() {
        ArrayList<AbstractMethodName> methodNameTempList = new ArrayList<>();
        AttributeUsageRange useRange = AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange());

        if (AttributeUsageRange.ApplyOnlyToAddedTemplates == useRange) {// 仅应用于所添加的模板
            AbstractFormatFunctionNameHolder formatMethodNameHolder = codeGenerationalOpratingContainerParam
                    .getFormatControlPane().getFormatFunctionNameHolder();
            if (formatMethodNameHolder != null) {
                methodNameTempList.addAll(formatMethodNameHolder.getFunctionNameList());
            }

            CustomFunctionNameHolder customMethodNameHolderTemp = codeGenerationalOpratingContainerParam
                    .getFormatControlPane().getThisCustomFunctionNameHolder();
            if (customMethodNameHolderTemp != null) {
                methodNameTempList.addAll(customMethodNameHolderTemp.getFunctionNameList());
            }

        } else if (AttributeUsageRange.ApplyOnlyToItself == useRange) {// 仅在 本方法 / 仅在本格式面板使用

            if (PathFind.COMMAND_TYPE == pathFind.getMetaType()) {
                AbstractCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
                        .getFirstCommandOpratingContainer();
                if (firstCommandOpratingContainer != null) {
                    methodNameTempList.addAll(firstCommandOpratingContainer.getThisCustomMethodNameHolder().getFunctionNameList());
                }
            } else if (PathFind.FORMAT_TYPE == pathFind.getMetaType()) {
                AbstractFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
                if (formatContainer != null) {
                    methodNameTempList.addAll(formatContainer.getThisCustomMethodNameHolder().getFunctionNameList());
                }
            }

        } else if (AttributeUsageRange.ApplyOnlyToThisModule == useRange) {// 仅在本模块使用（模块使用）
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();

            ModuleFormatFunctionNameHolder formatVariableHolder = CodeGenerationModuleFormatFunctionNameHolder
                    .getModuleFormatFunctionName(moduleId);
            if (formatVariableHolder != null) {
                methodNameTempList.addAll(formatVariableHolder.getFunctionNameList());
            }
            ModuleCustomFunctionNameHolder customVariableHolderTemp = CodeGenerationModuleCustomFunctionNameHolder
                    .getModuleCustomFunctionNameHolder(moduleId);
            if (customVariableHolderTemp != null) {
                methodNameTempList.addAll(customVariableHolderTemp.getFunctionNameList());
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
     * 加载所需变量
     */
    @SuppressWarnings("unchecked")
    private void uploadNeedFunctionName(ArrayList<String> methodNameNeedDataTypeList) {
        DefaultComboBoxModel<AbstractMethodName> model = new DefaultComboBoxModel<>();
        ArrayList<AbstractMethodName> theCorrespondingFunctionNameList = getTheCorrespondingFunctionNameList();
        ArrayList<AbstractMethodName> needMethodNameList = getNeedFunctionNameList(
                theCorrespondingFunctionNameList, methodNameNeedDataTypeList);
        for (AbstractMethodName methodNameTemp : needMethodNameList) {
            model.addElement(methodNameTemp);
//			if (methodNameTemp instanceof CustomFunctionName){
//				CustomFunctionName selectItem= (CustomFunctionName) methodNameTemp;
//				model.addElement(selectItem);
//			}else if(methodNameTemp instanceof FormatFunctionName){
//				FormatFunctionName selectItem= (FormatFunctionName) methodNameTemp;
//				model.addElement(selectItem);
//			}
        }
        setModel(model);
    }


    private void selectedTheFuncionName(int selectedFunctionNameId) {
        AbstractMethodName temp;
        ComboBoxModel<AbstractMethodName> model = getModel();
        for (int i = 0; i < model.getSize(); i++) {
            temp = model.getElementAt(i);
            if (temp.compare(selectedFunctionNameId)) {
                setSelectedIndex(i);
                break;
            }
        }
    }


    /**
     * 方法名id为functionNameId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     */
    public void synchronousChange(int functionNameId) {
        AbstractMethodName selectedFunctionName = (AbstractMethodName) getSelectedItem();
        if (selectedFunctionName != null) {
            if (selectedFunctionName.compare(functionNameId)) {
                updateValue();
            }
        }
    }

    /**
     * 方法名id为functionNameId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param functionNameId
     */
    public void synchronousDelete(int functionNameId) {
        AbstractMethodName selectedFunctionName = (AbstractMethodName) getSelectedItem();
        if (selectedFunctionName != null) {
            if (selectedFunctionName.compare(functionNameId)) {// 现在选中的刚好就是要删的这个变量
                if (getModel().getSize() == 1) {// 当前选项列表只有一个选项，那可以确定，一定是functionNameId这个变量
                    CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), "");// 清空该组件对应的值

                } else if (getModel().getSize() > 1) {// 下拉列表有2个或以上
                    if (getSelectedIndex() == 0) {// 如果当前选中的是第0个，选第1个换掉
                        setSelectedIndex(1);
                    } else {// 当前选中的不是第0个，换成第0个
                        setSelectedIndex(0);
                    }
                    updateValue();
                }
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
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    /**
     * 加载所有没设置的变量
     */
    @SuppressWarnings("unchecked")
    private void uploadNullFunctionName() {
        DefaultComboBoxModel<AbstractMethodName> model = new DefaultComboBoxModel<>();
        ArrayList<AbstractMethodName> theCorrespondingMethodNameList = getTheCorrespondingFunctionNameList();
        for (AbstractMethodName functionNameTemp : theCorrespondingMethodNameList) {
            model.addElement(functionNameTemp);
        }
        setModel(model);
    }


    /**
     * 根据设置的数据类型加载符合的变量
     */
    private void uploadConformingVariable() {
        AbstractMethodName selectItem = (AbstractMethodName) getModel()
                .getSelectedItem();

        removeAllItems();
        ArrayList<String> functionNameNeedDataTypeList = MethodChooseControl.getLabelTypeList(controlElement);
        if (functionNameNeedDataTypeList != null) {
            if (0 == functionNameNeedDataTypeList.size()) {// 没设置数据类型
                uploadNullFunctionName();
            } else if (functionNameNeedDataTypeList.size() > 0) {// 有设置数据类型
                uploadNeedFunctionName(functionNameNeedDataTypeList);
            }
        }
        if (selectItem != null) {
            selectedTheFuncionName(selectItem.getFunctionNameId());
        }
    }

    private void setTheSize() {
        Dimension dd = new Dimension(140, 30);
        setPreferredSize(dd);
        setMinimumSize(dd);
        setMaximumSize(dd);
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        Object temp = getSelectedItem();
        if (temp != null) {
            if (temp instanceof CustomFunctionName) {
                CustomFunctionName selectItem = (CustomFunctionName) temp;
                CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), selectItem.getFunctionNameValue());
            } else if (temp instanceof FormatFunctionName) {
                FormatFunctionName selectItem = (FormatFunctionName) temp;
                CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), selectItem.getFunctionNameValue());
            }
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
//		com.lazycoder.uicodegeneration.generalframe.functionname.GeneralMethodName selectItem = (com.lazycoder.uicodegeneration.generalframe.functionname.GeneralMethodName) getModel()
//				.getSelectedItem();
//		if (selectItem != null) {
//			if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
//				CodeGenerationFrameHolder.codeControlTabbedPane
//						.functionNameSynchronousDelete(selectItem.getFunctionNameId());
//			}
//		}
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
        if (getSelectedIndex() > 0) {
            AbstractMethodName theMethodName = (AbstractMethodName) getSelectedItem();
//			theModel.setSelectMethodNameId(theMethodName.getFunctionNameId());
            theModel.setMethodName(theMethodName);
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

}

//此类用于显示提示信息
@SuppressWarnings("rawtypes")
class FunctionNameCellRenderer implements ListCellRenderer {

//	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

//	JLabel renderer;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
        // 下拉列表每个选项显示的就是这个
        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        String theText = null; // 显示内容
        String toolTipText = ""; // 提示文本

        if (value instanceof CustomFunctionName) {
            CustomFunctionName valueTemp = (CustomFunctionName) value;
            theText = valueTemp.getFunctionNameValue();
            toolTipText = valueTemp.getToolTipTextOfHaveValue() + "\t" + valueTemp.getFunctionNameValue();

        } else if (value instanceof FormatFunctionName) {
            FormatFunctionName valueTemp = (FormatFunctionName) value;
            theText = valueTemp.getRoleOfFunctionNameValue();
            toolTipText = valueTemp.getToolTipTextOfHaveValue() + "\t" + valueTemp.getFunctionNameValue();

        }
        renderer.setText(theText);
        if ("".equals(toolTipText) == false) {
            renderer.setToolTipText(toolTipText);
        }

        return renderer;
    }
}
