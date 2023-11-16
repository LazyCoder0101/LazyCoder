package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.awt.Dimension;
import java.util.Vector;

public class MultiComboboxLabel<E> extends MultiSelectComboBox<E> implements LabelComponentIntetface {

    /**
     *
     */
    private static final long serialVersionUID = -3557688377507435620L;

    private PassingComponentParams passingComponentParams = null;

    // public MultiComboboxLabel() {
    // super();
    // this.setMaximumSize(new Dimension(120, 30));
    // // this.setBorder(new BERoundBorder());
    //
    // }

    public MultiComboboxLabel(E[] items) {
        super(items);
    }

    public MultiComboboxLabel(Vector<E> items) {
        super(items);
    }

    public void setTheSize() {
        // TODO Auto-generated constructor stub
        Dimension dd = new Dimension(130, 30);
        String temp;
        int lenth = 0;
        for (int i = 0; i < getModel().getSize(); i++) {
            temp = (String) getModel().getElementAt(i);
            if (temp.length() > lenth) {
                lenth = temp.length();
            }
        }
        if (lenth > 5) {
            dd = new Dimension(lenth * 12 + 50, 30);
        }
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
        this.setMaximumSize(dd);
    }

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }


    @Override
    public String getLabelType() {
        // TODO Auto-generated method stub
        return LabelElementName.CONTENT_CHOOSE;
    }

    @Override
    public PassingComponentParams getPassingComponentParams() {
        // TODO Auto-generated method stub
        return passingComponentParams;
    }

    @Override
    public void setPassingComponentParams(PassingComponentParams passingComponentParams) {
        // TODO Auto-generated method stub
        this.passingComponentParams = passingComponentParams;
        LabelComponentIntetface.addCorrespondingComponentResponseListener(this);
    }

    @Override
    public void setNavigate(boolean flag) {
    }

    @Override
    public BaseLableElement property() {
        return null;
    }


}
