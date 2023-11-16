package com.lazycoder.uiutils.component;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 * 带提示的输入框
 * 摘自 https://blog.csdn.net/weixin_46389691/article/details/116945095
 */
public class JRichTextField extends JTextField implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = -1985791340012812141L;

    private String tipText = null;

    protected static Color textColor = Color.BLACK,
            tipColor = Color.GRAY;

    public JRichTextField(String tipText) {
        this.tipText = tipText;

        setForeground(tipColor);
        addFocusListener(focusListener);
    }

    private FocusListener focusListener = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            //得到焦点时，当前文本框的提示文字和创建该对象时的提示文字一样，说明用户正要键入内容
            if (tipText.equals(getText())) {
                setText("");     //将提示文字清空
                setForeground(textColor);  //设置用户输入的字体颜色为黑色
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            //失去焦点时，用户尚未在文本框内输入任何内容，所以依旧显示提示文字
            if ("".equals(getText())) {
                setForeground(tipColor); //将提示文字设置为灰色
                setText(tipText);     //显示提示文字
            }
        }
    };

    /**
     * 新建输入框时，没有显示任何内容的时候使用，刚开始没有任何信息的时候显示提示文本
     */
    @Override
    public void run() {
        if ("".equals(getText())) {
            setForeground(tipColor); //将提示文字设置为灰色
            setText(tipText);     //显示提示文字
        }
    }

}

