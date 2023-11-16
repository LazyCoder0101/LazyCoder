package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.customvariable;

import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.component.CustomVariableMutipleInputBox;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 内部的变量输入框
 *
 * @author admin
 */
public class CustomVariableTextFieldForMuti extends JTextField {

    /**
     *
     */
    private static final long serialVersionUID = 7821943593668414212L;

    private CustomVariableParam customVariableParam;

    private CustomVariable customVariable;

    private CustomVariableMutipleInputBox CustomVariableMutipleInputBox = null;

    public CustomVariableTextFieldForMuti(CustomVariableParam customVariableParam, CustomVariable customVariable) {
        super();
        this.CustomVariableMutipleInputBox = customVariableParam.getCustomVariableMutipleInoutBox();
        // TODO Auto-generated constructor stub
        this.customVariableParam = customVariableParam;
        this.customVariable = customVariable;

        String defaultNameTemp = customVariableParam.getShowVariableName();
        if (defaultNameTemp != null && "".equals(defaultNameTemp.trim()) == false) {
            if (this.customVariable.isAllowDuplicateNames()) {//允许变量名重复
                String defaultName = defaultNameTemp;
                customVariable.setVariableValue(defaultName);
                setText(defaultName);
            } else {//变量名不能重复
                String defaultName = CodeGenerationComponentInterface.getDeafaultVariableName(this.CustomVariableMutipleInputBox, defaultNameTemp);
                customVariable.setVariableValue(defaultName);
                setText(defaultName);
            }
        } else {
            customVariable.setVariableValue("");
            setText("");
        }

        getDocument().addDocumentListener(documentListener);
        addFocusListener(focusListener);
    }

    private DocumentListener documentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateThisVariableValue();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateThisVariableValue();

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateThisVariableValue();
        }
    };

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

    public void delThisVariable(CustomVariableMutipleInputBox customVariableMutipleInputBox) {
        if (customVariable != null) {
            CustomVariableHolder customVaribleHolder = customVariableMutipleInputBox.getTheCustomVariableHolder();
            if (customVaribleHolder != null) {
                customVaribleHolder.delCustomVariable(customVariable.getVariableId());
            }
            if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
                CodeGenerationFrameHolder.codeControlTabbedPane.variableSynchronousDelete(customVariable.getVariableId());
            }
        }
    }

    public CustomVariable getTheCustomVarible() {
        return customVariable;
    }

    private void updateThisVariableValue() {
        customVariable.setVariableValue(getText());
        customVariableParam.getCustomVariableMutipleInoutBox().updateValue();
        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            CodeGenerationFrameHolder.codeControlTabbedPane.variableSynchronousChange(customVariable.getVariableId());
        }
    }

    private boolean check() {
        boolean flag = true;
        String value = getText();
        if ("".equals(value.trim()) == false) {
            if (StringUtil.checkVariableName(value) == false) {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	换个一般的名字吧，只要英文或数字就好，不过开头别写数字-");
            } else {
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
        return flag;
    }

}
