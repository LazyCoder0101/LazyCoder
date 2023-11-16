package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomVariableMeta;
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
 * 自定义变量（文本框）
 *
 * @author admin
 */
public class CustomVariableRadioTextField extends JTextField
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 6546651755352961979L;

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private CustomVariableControl controlElement = null;

    private CustomVariable customVariable;

    public CustomVariableRadioTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                        CustomVariableControl controlElement) {
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

        generateThisVariable();

        setTheSize();

    }

    public CustomVariableRadioTextField(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                        CustomVariableMeta customVariableMeta) {
        // TODO Auto-generated constructor stub

        this.controlElement = customVariableMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        this.pathFind = customVariableMeta.getPathFind();

        restoreCustomVariable(customVariableMeta);
        setTheSize();
        getDocument().addDocumentListener(documentListener);
        addFocusListener(focusListener);
    }

    private void restoreCustomVariable(CustomVariableMeta customVariableMeta) {
        ArrayList<CustomVariable> list = customVariableMeta.getInputCustomVariableList();
        for (CustomVariable temp : list) {
            this.customVariable = temp;
            setText(temp.getVariableValue());
            getTheCustomVariableHolder().getVariableList().add(temp);
        }
    }

    private void setTheSize() {
        Dimension dd = new Dimension(120, 30);
        setPreferredSize(dd);
        setMaximumSize(dd);
        setMinimumSize(dd);
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

    private boolean check() {
        boolean flag = true;
        if(getText()==null){
            flag = false;
            LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	不给它起个名字吗，这样生成代码的时候，可能会出错的喔");
        }else {
            String value = getText().trim();
            if ("".equals(value) == false) {
                if (StringUtil.checkVariableName(value) == false) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	换个一般的名字吧，只要英文或数字就好，不过开头别写数字-");
                } else {
                    if (this.controlElement.isAllowDuplicateNames() == false) {//属性设置不允许变量名重复，检查当前起的名字是否有重复
                        //查看现在有的变量有没有这个名字的
                        ArrayList<AbstractVariableHolder> allVariableHolderList = CodeGenerationFrameHolder.getAllVariableHolderList();
                        for (AbstractVariableHolder variableListTemp : allVariableHolderList) {
                            for (AbstractVariable temp : variableListTemp.getVariableList()) {
                                if (temp.getVariableId() != customVariable.getVariableId()) {
                                    if (temp.getVariableValue().equals(getText())) {
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
            } else {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	不给它起个名字吗，这样生成代码的时候，可能会出错的喔");
            }
        }
        return flag;
    }


    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        customVariable.setVariableValue(getText());
        CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), getText());
        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            CodeGenerationFrameHolder.codeControlTabbedPane.variableSynchronousChange(customVariable.getVariableId());
        }
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        getTheCustomVariableHolder().delCustomVariable(customVariable.getVariableId());
        if (customVariable != null) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
                CodeGenerationFrameHolder.codeControlTabbedPane.variableSynchronousDelete(customVariable.getVariableId());
            }
        }
    }

    private CustomVariableHolder getTheCustomVariableHolder() {
        CustomVariableHolder customVariableHolderTemp = null;
        if (AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange()) == AttributeUsageRange.ApplyOnlyToItself) {//仅用于本方法 / 本格式面板
            customVariableHolderTemp = codeGenerationalOpratingContainerParam.getThisCustomVariableHolder();
//			if (PathFind.commandType == this.pathFind.getMetaType()) {
//				GeneralCommandOpratingContainer firstCommandOpratingContainer = codeGenerationalOpratingContainerParam
//						.getFirstCommandOpratingContainer();
//				if (firstCommandOpratingContainer != null) {
//					customVariableHolderTemp = firstCommandOpratingContainer.getThisCustomVariableHolder();
//				}
//			} else if (PathFind.formatType == this.pathFind.getMetaType()) {
//				GeneralFormatContainer formatContainer = codeGenerationalOpratingContainerParam.getFormatContainer();
//				if (formatContainer != null) {
//					customVariableHolderTemp = formatContainer.getThisCustomVariableHolder();
//				}
//			}
        } else if (AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange()) == AttributeUsageRange.ApplyOnlyToThisModule) {//仅用于本模块
            String moduleId = codeGenerationalOpratingContainerParam.getModule().getModuleId();
            customVariableHolderTemp = CodeGenerationModuleCustomVariableHolder
                    .getModuleCustomVariableHolder(moduleId);

        } else if (AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange()) == AttributeUsageRange.ApplyToAll
                || AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange()) == AttributeUsageRange.ApplyOnlyToThisTypeOfTemplate
                || AttributeUsageRange.dictionaryValueToVariableUsageRange(
                controlElement.getTheAvaliableRange()) == AttributeUsageRange.ApplyOnlyToAddedTemplates) {//应用于所有、仅应用于该类型面板、仅应用于所添加的面板
            customVariableHolderTemp = codeGenerationalOpratingContainerParam.getFormatControlPane()
                    .getThisCustomVariableHolder();
        }
        return customVariableHolderTemp;
    }

    @Override
    public CustomVariableControl getControlElement() {
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

    /**
     * 生成该组件的变量
     */
    private void generateThisVariable() {
        int variableId = VariableIDGenerator.generateCodeSerialNumber();

        customVariable = new CustomVariable();
        customVariable.setVariableId(variableId);
        customVariable.setNoUserSelectionIsRequired(controlElement.isNoUserSelectionIsRequired());
        customVariable.setAllowDuplicateNames(controlElement.isAllowDuplicateNames());
        customVariable.setVariableRange(controlElement.getTheAvaliableRange());
        ArrayList<String> dataTypeList = customVariable.getDataTypeList(),
                dataVariableTypeList = CustomVariableControl.getDataTypeParam(controlElement.getDataTypeParam());
        if (dataVariableTypeList != null) {
            dataTypeList.addAll(dataVariableTypeList);
        }
        ArrayList<String> labelTypeList = customVariable.getLabelTypeList(),
                labelVariableTypeList = CustomVariableControl.getLabelTypeParam(controlElement.getLabelTypeParam());
        if (labelVariableTypeList != null) {
            labelTypeList.addAll(labelVariableTypeList);
        }
        //根据属性设置默认变量名
        ArrayList<String> defaultNameList = CustomVariableControl.getDefaultNameList(controlElement);
        if (defaultNameList != null) {
            for (String defaultNameTemp : defaultNameList) {
                if (defaultNameTemp != null && "".equals(defaultNameTemp.trim()) == false) {
                    if (this.controlElement.isAllowDuplicateNames()) {//允许变量名重复
                        String defaultName = defaultNameTemp;
                        setText(defaultName);
                    } else {//变量名不能重复
                        String defaultName = CodeGenerationComponentInterface.getDeafaultVariableName(this, defaultNameTemp);
                        setText(defaultName);
                    }
                } else {
                    setText("");
                }
            }
        }
        CustomVariableHolder customVaribleHolder = getTheCustomVariableHolder();
        customVaribleHolder.getVariableList().add(customVariable);
        updateValue();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        CustomVariableMeta theModel = (CustomVariableMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);

        ArrayList<CustomVariable> list = new ArrayList<>();
        list.add(customVariable);
        theModel.setInputCustomVariableList(list);
    }

    @Override
    public CustomVariableMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        CustomVariableMeta model = new CustomVariableMeta();
        setParam(model);
        return model;
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return 80;
    }

    @Override
    public void collapseThis() {
    }


}
