package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.functionadd.MethodAddStorageFormat;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.FunctionAddLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.InternalMethodAddEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FunctionAddControlLabel extends FunctionAddLabel implements ControlLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1216556803867219345L;

    private FunctionAddControl controlElement = new FunctionAddControl();

    private FunctionAddParam functionAddParam;

    /**
     * 新建
     *
     * @param name
     * @param functionAddParam
     */
    public FunctionAddControlLabel(String name, FunctionAddParam functionAddParam) {
        super();
        this.functionAddParam = functionAddParam;
        init(name);
    }

    /**
     * 还原
     *
     * @param controlElement
     * @param functionAddParam
     */
    public FunctionAddControlLabel(FunctionAddControl controlElement, FunctionAddParam functionAddParam) {
        // TODO Auto-generated constructor stub
        super();
        this.controlElement = controlElement;
        this.functionAddParam = functionAddParam;
        init(controlElement.getThisName());
    }

    private void init(String name) {
        setName(name);
        setText(name);
        setUI(new ControlLabelButtonUI());
        addActionListener(listener);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new InternalMethodAddEditFrame(FunctionAddControlLabel.this, functionAddParam);
        }
    };


    public boolean isOnlInternalCodeIsAllowed() {
        return controlElement.isOnlInternalCodeIsAllowed();
    }

    public void setOnlInternalCodeIsAllowed(boolean onlInternalCodeIsAllowed) {
        this.controlElement.setOnlInternalCodeIsAllowed(onlInternalCodeIsAllowed);
    }

    public ArrayList<MethodAddStorageFormat> getFunctionList() {
        return controlElement.getFunctionList();
    }

    public void setFunctionList(ArrayList<MethodAddStorageFormat> functionList) {
        controlElement.setFunctionList(functionList);
    }

    public int getOtherAttribute() {
        return controlElement.getOtherAttribute();
    }

    public void setOtherAttribute(int otherAttribute) {
        controlElement.setOtherAttribute(otherAttribute);
    }

    @Override
    public void deleteFromPanel() {}

    @Override
    public void setName(String name) {
        super.setName(name);
        controlElement.setThisName(name);
    }

    @Override
    public FunctionAddControl property() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public FunctionAddControl getControl() {
        // TODO Auto-generated method stub
        return controlElement;
    }

}