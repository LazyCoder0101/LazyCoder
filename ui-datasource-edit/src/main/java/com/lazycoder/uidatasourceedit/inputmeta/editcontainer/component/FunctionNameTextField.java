package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uiutils.component.JRichTextField;

/**
 * 录入方法填写的文本框
 *
 * @author Administrator
 */
public class FunctionNameTextField extends JRichTextField implements CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1754911696189408838L;

    private static String useTip = "在这里填写功能名称";

    public FunctionNameTextField() {
        // TODO Auto-generated constructor stub
        super(useTip);
    }

    /**
     * 清空
     */
    public void clear() {
        setText("");
    }


    @Override
    public void setText(String t) {
        super.setText(t);
        if (useTip.equals(t)) {
            setForeground(tipColor);
        }else {
            setForeground(textColor);
        }
    }

    @Override
    public boolean check() {
        boolean flag = true;
        String tText = super.getText().trim();
        if (tText == null ||
                "".equals(tText) ||
                (useTip.equals(tText) && getForeground() == tipColor)) {
            return false;
        }
        return flag;
    }

}
