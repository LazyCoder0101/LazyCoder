package com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.customvariable;

import com.lazycoder.uicodegeneration.component.operation.component.CustomVariableMutipleInputBox;
import com.lazycoder.uicodegeneration.generalframe.variable.CustomVariable;
import com.lazycoder.uiutils.mycomponent.GeneralOptionTextFieldEditPane;
import java.util.ArrayList;
import javax.swing.JTextField;

public class CustomVariableMutiEditPane extends GeneralOptionTextFieldEditPane {

    /**
     *
     */
    private static final long serialVersionUID = -778060081437467897L;

    public CustomVariableMutiEditPane() {
        // TODO Auto-generated constructor stub
        super();
    }

    public CustomVariableTextFieldForMuti addOptionTextField(CustomVariableParam customVariableParam,
                                                             CustomVariable customVariable) {
        CustomVariableTextFieldForMuti textField = new CustomVariableTextFieldForMuti(customVariableParam,
                customVariable);
        add(textField);
        return textField;
    }

    /**
     * 删除最后一个变量输入框
     */
    public void delOptionTextField(CustomVariableMutipleInputBox customVariableMutipleInputBox) {
        int num = getComponentCount();
        CustomVariableTextFieldForMuti customVariableTextField;
        if (num > 0) {
            customVariableTextField = (CustomVariableTextFieldForMuti) getComponent(num - 1);
            customVariableTextField.delThisVariable(customVariableMutipleInputBox);
            remove(num - 1);
        }
    }

    /**
     * 删除所有变量，在删除对应语句的时候用到
     */
    public void delAllCustomVariable(CustomVariableMutipleInputBox customVariableMutipleInputBox) {
        CustomVariableTextFieldForMuti customVariableTextField;
        for (int i = 0; i < this.getComponentCount(); i++) {
            customVariableTextField = (CustomVariableTextFieldForMuti) getComponent(i);
            customVariableTextField.delThisVariable(customVariableMutipleInputBox);
        }
    }

    public ArrayList<CustomVariable> getCustomVariableList() {
        ArrayList<CustomVariable> customVaribleList = new ArrayList<CustomVariable>();
        CustomVariableTextFieldForMuti customVariableTextField;
        for (int i = 0; i < this.getComponentCount(); i++) {
            customVariableTextField = (CustomVariableTextFieldForMuti) getComponent(i);
            customVaribleList.add(customVariableTextField.getTheCustomVarible());
        }
        return customVaribleList;
    }

    /**
     * 这句要重写
     */
    @Override
    public void restore(ArrayList<String> textList) {
        JTextField textField;
        for (String temp: textList) {
            textField = new JTextField();
            textField.setText(temp);
            add(textField);
        }
    }

}
