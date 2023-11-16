package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationModuleCustomVariableHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.MultipleInputForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.customvariable.CustomVariableMutiEditPane;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.customvariable.CustomVariableParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.generalframe.tool.VariableIDGenerator;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CustomVariableMeta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 * 自定义变量
 *
 * @author admin
 */
public class CustomVariableMutipleInputBox extends MultipleInputForCodeGeneration
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -7708554983716417732L;

    private static final ImageIcon ADD_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
            + "FunctionOperationComponent" + File.separator + "CustomVariable" + File.separator + "add.png"),
            MINUS_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
                    + "FunctionOperationComponent" + File.separator + "CustomVariable" + File.separator + "minus.png"),
            EXPAND_ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator
                    + "FunctionOperationComponent" + File.separator + "CustomVariable" + File.separator + "expand.png"),
            COLLAPSE_ICON = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "FunctionOperationComponent"
                            + File.separator + "CustomVariable" + File.separator + "collapse.png");

    private CustomVariableControl controlElement = new CustomVariableControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private CustomVariableMutiEditPane customVariableMutiEditPane;

    public CustomVariableMutipleInputBox() {
        // TODO Auto-generated constructor stub
        super();
        setIcon(ADD_ICON, MINUS_ICON, EXPAND_ICON, COLLAPSE_ICON);
        setBorder(BorderFactory.createRaisedSoftBevelBorder());

        customVariableMutiEditPane = new CustomVariableMutiEditPane();
        scrollPane.setViewportView(customVariableMutiEditPane);
    }

    /**
     * 新建
     * @param codeGenerationalOpratingContainerParam
     * @param controlElement
     */
    public CustomVariableMutipleInputBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                         CustomVariableControl controlElement) {
        this();
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        addButton.addActionListener(actionListener);
        delButton.addActionListener(actionListener);

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        addDefaultCustomVariables();
//        updateValue();

        textField.addMouseListener(mouseAdapter);
    }

    /**
     * 还原
     * @param codeGenerationalOpratingContainerParam
     * @param customVariableMeta
     */
    public CustomVariableMutipleInputBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                         CustomVariableMeta customVariableMeta) {
        this();
        this.controlElement = customVariableMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        addButton.addActionListener(actionListener);
        delButton.addActionListener(actionListener);
        this.pathFind = customVariableMeta.getPathFind();

        ArrayList<CustomVariable> inputCustomVariableList = customVariableMeta.getInputCustomVariableList();
        if (inputCustomVariableList != null) {
            for (CustomVariable temp : inputCustomVariableList) {
                addCustomVariable(temp.getVariableValue(), temp);
            }
            scrollPane.updateUI();
            scrollPane.repaint();
        }
        updateValue();

        textField.addMouseListener(mouseAdapter);
    }

    /**
     * 添加变量
     *
     * @param showVariableName
     */
    private void addCustomVariable(String showVariableName) {
        int variableId = VariableIDGenerator.generateCodeSerialNumber();

        CustomVariable customVariable = new CustomVariable();
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
//        customVariable.setVariableValue(showVariableName);

        addCustomVariable(showVariableName, customVariable);
    }

    private void addDefaultCustomVariables() {
        ArrayList<String> defaultNameList = CustomVariableControl.getDefaultNameList(controlElement);
        if (defaultNameList != null) {
            for (String defaultNameTemp : defaultNameList) {
                addCustomVariable(defaultNameTemp);
            }
        }
        updateValue();
    }

    /**
     * 在当前组件添加一个对应的自定义变量输入框
     *
     * @param showVariableName
     * @param customVariable
     */
    private void addCustomVariable(String showVariableName, CustomVariable customVariable) {
        //集中存放对应的自定义组件需要的参数，传到对应的自定义变量输入框
        CustomVariableParam customVariableParam = new CustomVariableParam();
        customVariableParam.setCustomVariableMutipleInoutBox(this);
        customVariableParam.setFormatControlPane(codeGenerationalOpratingContainerParam.getFormatControlPane());
        customVariableParam.setControlElement(controlElement);
        customVariableParam.setShowVariableName(showVariableName);

        customVariableMutiEditPane.addOptionTextField(customVariableParam, customVariable);
        getTheCustomVariableHolder().addVariable(customVariable);
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        CodeGenerationStaticFunction.setValue(this,codeGenerationalOpratingContainerParam.getPaneType(), this.getValue());
        textField.setText(getShowStr(customVariableMutiEditPane.getTextList()));
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        customVariableMutiEditPane.delAllCustomVariable(this);
        super.hidePopupPanel();
    }

    @Override
    public CustomVariableControl getControlElement() {
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    public void setPathFind(PathFind pathFind) {
        this.pathFind = pathFind;
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
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    public String getValue() {
        StringBuilder out = new StringBuilder("");
        ArrayList<String> customVariableNameList = customVariableMutiEditPane.getTextList();
        if (customVariableNameList != null) {
            if (customVariableNameList.size() == 1) {
                out.append(customVariableNameList.get(0));

            } else if (customVariableNameList.size() > 1) {
                out.append(controlElement.getLeftStr() + customVariableNameList.get(0) + controlElement.getRightStr());
                for (int i = 1; i < customVariableNameList.size(); i++) {
                    out.append(controlElement.getSeparatorStr() + controlElement.getLeftStr()
                            + customVariableNameList.get(i) + controlElement.getRightStr());
                }
            }
        }
        return out.toString();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        CustomVariableMeta theModel = (CustomVariableMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        theModel.setInputCustomVariableList(customVariableMutiEditPane.getCustomVariableList());
    }

    @Override
    public CustomVariableMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        CustomVariableMeta model = new CustomVariableMeta();
        setParam(model);
        return model;
    }

    public CustomVariableHolder getTheCustomVariableHolder() {
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

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
        }
    };

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                addCustomVariable("");
                updateValue();
                scrollPane.updateUI();
                scrollPane.repaint();

            } else if (e.getSource() == delButton) {
                customVariableMutiEditPane.delOptionTextField(CustomVariableMutipleInputBox.this);
                updateValue();
                scrollPane.updateUI();
                scrollPane.repaint();
            }
        }
    };


    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return TEXT_FIELD_WIDTH + ADD_ICON.getIconWidth() + MINUS_ICON.getIconWidth() + EXPAND_ICON.getIconWidth() + 10;
    }

    @Override
    public void collapseThis() {
        expandButton.packUpPanel();
    }

}
