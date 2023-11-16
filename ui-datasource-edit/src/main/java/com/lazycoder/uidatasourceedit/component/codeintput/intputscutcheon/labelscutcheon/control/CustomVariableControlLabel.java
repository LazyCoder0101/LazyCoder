package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.CustomVariableLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.CustomVariableEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Setter;

public class CustomVariableControlLabel extends CustomVariableLabel implements ControlLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = -2273994967554046295L;

    @Setter
    private CustomVariableControl controlElement = new CustomVariableControl();

    /**
     * 新建
     *
     * @param name
     */
    public CustomVariableControlLabel(String name) {
        // TODO Auto-generated constructor stub
        init(name);
    }

    /**
     * 还原
     *
     * @param controlElement
     */
    public CustomVariableControlLabel(CustomVariableControl controlElement) {
        // TODO Auto-generated constructor stub
        this.controlElement = controlElement;
        init(controlElement.getThisName());
    }

    public void init(String name) {
        setName(name);
        setText(name);
        setUI(new ControlLabelButtonUI());
        addActionListener(listener);
    }

    @Override
    public void deleteFromPanel() {
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new CustomVariableEditFrame(CustomVariableControlLabel.this);
        }
    };


    public boolean isNoUserSelectionIsRequired() {
        return controlElement.isNoUserSelectionIsRequired();
    }

    public void setNoUserSelectionIsRequired(boolean noUserSelectionIsRequired) {
        this.controlElement.setNoUserSelectionIsRequired(noUserSelectionIsRequired);
    }

    public boolean isAllowDuplicateNames() {
        return controlElement.isAllowDuplicateNames();
    }

    public void setAllowDuplicateNames(boolean allowDuplicateNames) {
        this.controlElement.setAllowDuplicateNames(allowDuplicateNames);
    }

    public String getDataTypeParam() {
        return controlElement.getDataTypeParam();
    }

    public void setDataTypeParam(String dataTypeParam) {
        this.controlElement.setDataTypeParam(dataTypeParam);
    }

    public String getLabelTypeParam() {
        return controlElement.getLabelTypeParam();
    }

    public void setLabelTypeParam(String labelTypeParam) {
        this.controlElement.setLabelTypeParam(labelTypeParam);
    }

    public String getLeftStr() {
        return controlElement.getLeftStr();
    }

    public void setLeftStr(String leftStr) {
        this.controlElement.setLeftStr(leftStr);
    }

    public String getRightStr() {
        return controlElement.getRightStr();
    }

    public void setRightStr(String rightStr) {
        this.controlElement.setRightStr(rightStr);
    }

    public String getSeparatorStr() {
        return controlElement.getSeparatorStr();
    }

    public void setSeparatorStr(String separatorStr) {
        this.controlElement.setSeparatorStr(separatorStr);
    }

    public int getTheAvaliableRange() {
        return controlElement.getTheAvaliableRange();
    }

    public void setTheAvaliableRange(int theAvaliableRange) {
        this.controlElement.setTheAvaliableRange(theAvaliableRange);
    }

    public int isOnlyAddOne() {
        return controlElement.getOnlyAddOne();
    }

    public void setOnlyAddOne(int onlyAddOne) {
        this.controlElement.setOnlyAddOne(onlyAddOne);
    }

    public String getDefaultNameList() {
        return controlElement.getDefaultNameList();
    }

    public void setDefaultNameList(String defaultNameList) {
        this.controlElement.setDefaultNameList(defaultNameList);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        controlElement.setThisName(name);
    }

    @Override
    public CustomVariableControl property() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public CustomVariableControl getControl() {
        return controlElement;
    }


}