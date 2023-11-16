package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.CustomFunctionName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AbstractFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionNameIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomMethodMeta;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * 自定义方法
 *
 * @author admin
 */
public class CustomMethodNameTextField extends JTextField
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7987132771551974134L;

    private CustomMethodNameControl controlElement = new CustomMethodNameControl();

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private PathFind pathFind;

    private CustomFunctionName customMethodName;

    public CustomMethodNameTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                     CustomMethodNameControl controlElement) {
        // TODO Auto-generated constructor stub
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        getDocument().addDocumentListener(documentListener);
        addFocusListener(focusListener);

        generateThisMethodName();

        setTheSize();
    }

    public CustomMethodNameTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                     CustomMethodMeta customMethodMeta) {
        this.controlElement = customMethodMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        this.pathFind = customMethodMeta.getPathFind();

        getDocument().addDocumentListener(documentListener);
        addFocusListener(focusListener);

        restoreCustomVariable(customMethodMeta);
        setTheSize();
    }

    private FocusListener focusListener = new FocusListener() {

        @Override
        public void focusLost(FocusEvent e) {
            // TODO Auto-generated method stub
            check();
        }

        @Override
        public void focusGained(FocusEvent e) {
            // TODO Auto-generated method stub

        }
    };

    private DocumentListener documentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }
    };

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    /**
     * 生成该组件的方法名
     */
    private void generateThisMethodName() {
        int methodNameId = FunctionNameIDGenerator.generateCodeSerialNumber();

        customMethodName = new CustomFunctionName();
        customMethodName.setFunctionNameId(methodNameId);
        customMethodName.setFunctionNameRange(controlElement.getTheAvaliableRange());
        ArrayList<String> variableTypeList = customMethodName.getFunctionNameType(),
                dataVariableTypeList = CustomMethodNameControl.getLabelTypeParam(controlElement.getLabelTypeParam());
        if (dataVariableTypeList != null) {
            variableTypeList.addAll(dataVariableTypeList);
        }
        String defaultNameTemp = controlElement.getMethodName();
        if (defaultNameTemp != null && "".equals(defaultNameTemp.trim()) == false) {
            ArrayList<AbstractFunctionNameHolder> allFunctionNameHolderList = CodeGenerationFrameHolder.getAllFunctionNameHolderList();
            String defaultName = CodeGenerationFrameHolder.getAvailableFunctionName(defaultNameTemp, 0, allFunctionNameHolderList);
            customMethodName.setFunctionNameValue(defaultName);
            setText(defaultName);
        } else {
            customMethodName.setFunctionNameValue(defaultNameTemp);
            setText(defaultNameTemp);
        }

        CustomFunctionNameHolder customFunctionNameHolder = getTheCustomMethodNameHolder();
        customFunctionNameHolder.addFunctionName(customMethodName);
    }

    private CustomFunctionNameHolder getTheCustomMethodNameHolder() {
//		后面查看这里，在必填模板格式那里的功能拓展组件，选本模块的居然在这报错

        CustomFunctionNameHolder customMethodNameHolderTemp = null;

        AttributeUsageRange range = AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange());

        if (AttributeUsageRange.ApplyOnlyToItself == range) {//仅用户本方法，本格式
            customMethodNameHolderTemp = this.codeGenerationalOpratingContainerParam.getThisCustomFunctionNameHolder();
//			if (PathFind.commandType == this.pathFind.getMetaType()) {
//				GeneralCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
//						.getFirstCommandOpratingContainer();
//				if (firstCommandOpratingContainer != null) {
//					customMethodNameHolderTemp = firstCommandOpratingContainer.getThisCustomMethodNameHolder();
//				}
//
//			} else if (PathFind.formatType == this.pathFind.getMetaType()) {
//				GeneralFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
//				if (formatContainer != null) {
//					customMethodNameHolderTemp = formatContainer.getThisCustomMethodNameHolder();
//				}
//			}

        } else if (AttributeUsageRange.ApplyOnlyToThisModule == range) {//仅用于本模块
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();

            customMethodNameHolderTemp = CodeGenerationModuleCustomFunctionNameHolder
                    .getModuleCustomFunctionNameHolder(moduleId);

        } else if (AttributeUsageRange.ApplyToAll == range
                || AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate == range
                || AttributeUsageRange.ApplyOnlyToAddedTemplates == range) {
            //应用于所有
            //应用于该类型
            //应用于所添加的代码控制面板
            customMethodNameHolderTemp = codeGenerationalOpratingContainerParam.getFormatControlPane()
                    .getThisCustomFunctionNameHolder();
        }
        return customMethodNameHolderTemp;
    }

    private void restoreCustomVariable(CustomMethodMeta customMethodMeta) {
        this.customMethodName = customMethodMeta.getCustomFunctionName();
        setText(this.customMethodName.getFunctionNameValue());
        getTheCustomMethodNameHolder().addFunctionName(customMethodName);
    }

    private void setTheSize() {
        Dimension dd = new Dimension(120, 30);
        setPreferredSize(dd);
        setMaximumSize(dd);
        setMinimumSize(dd);
    }

    @Override
    public CustomMethodNameControl getControlElement() {
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
        CustomMethodMeta theModel = (CustomMethodMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setCustomFunctionName(customMethodName);
        theModel.setPathFind(pathFind);

    }

    @Override
    public CustomMethodMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        CustomMethodMeta model = new CustomMethodMeta();
        setParam(model);
        return model;
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        customMethodName.setFunctionNameValue(getText());
        CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), getText());
        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            CodeGenerationFrameHolder.codeControlTabbedPane
                    .functionNameSynchronousChange(customMethodName.getFunctionNameId());
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        getTheCustomMethodNameHolder().delCustomFunctionName(customMethodName.getFunctionNameId());
        if (customMethodName != null) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
                CodeGenerationFrameHolder.codeControlTabbedPane.functionNameSynchronousDelete(customMethodName.getFunctionNameId());
            }
        }
    }

    private boolean check() {
        boolean flag = true;
        String value = getText();
        if ("".equals(value.trim()) == false) {
            if (StringUtil.checkVariableName(value) == false) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	换个一般的名字吧，只要英文或数字就好，不过开头别写数字");
            } else {
                //查看现在有的方法名有没有这个名字的
                ArrayList<AbstractFunctionNameHolder> allFunctionNameHolderList = CodeGenerationFrameHolder.getAllFunctionNameHolderList();
                for (AbstractFunctionNameHolder functionNameListTemp : allFunctionNameHolderList) {
                    for (AbstractMethodName temp : functionNameListTemp.getFunctionNameList()) {
                        if (temp.getFunctionNameId() != customMethodName.getFunctionNameId()) {
                            if (temp.getFunctionNameValue().equals(getText())) {
                                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	换个名字吧，之前起过这名字了");
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag == false) {
                        break;
                    }
                }
            }
        }
        return flag;
    }


    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return 110;
    }

    @Override
    public void collapseThis() {

    }

}
