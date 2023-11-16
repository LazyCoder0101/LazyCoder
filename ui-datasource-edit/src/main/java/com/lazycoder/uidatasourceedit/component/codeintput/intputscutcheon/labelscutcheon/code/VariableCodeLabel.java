package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.VariableCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.VariableLabel;

public class VariableCodeLabel extends VariableLabel implements CodeLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 99020052989877120L;

    private VariableCodeElement codeElement = new VariableCodeElement();

    /**
     * 新建
     */
    public VariableCodeLabel(String name) {
        // TODO Auto-generated constructor stub
        init(name);
    }

    /**
     * 还原
     *
     * @param codeElement
     */
    public VariableCodeLabel(VariableCodeElement codeElement) {
        // TODO Auto-generated constructor stub
        this.codeElement = codeElement;
        init(codeElement.getThisName());
    }

    private void init(String name) {
        setText(name);
        setName(name);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        codeElement.setThisName(name);
    }

    @Override
    public VariableCodeElement property() {
        // TODO Auto-generated method stub
        return codeElement;
    }

}