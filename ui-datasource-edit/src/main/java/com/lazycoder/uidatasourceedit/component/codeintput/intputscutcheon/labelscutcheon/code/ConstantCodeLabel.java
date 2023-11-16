package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.service.vo.element.lable.code.ConstantCodeElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ConstantLabel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConstantCodeLabel extends ConstantLabel implements CodeLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = -59110951199677487L;

    private ConstantCodeElement codeElement = new ConstantCodeElement();


    /**
     * 新建
     */
    public ConstantCodeLabel(String name) {
        // TODO Auto-generated constructor stub
        init(name);
    }

    /**
     * 还原
     *
     * @param codeElement
     */
    public ConstantCodeLabel(ConstantCodeElement codeElement) {
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
    public ConstantCodeElement property() {
        // TODO Auto-generated method stub
        return codeElement;
    }

}
