package com.lazycoder.utils.swing;

import com.lazycoder.utils.FileUtil;
import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * 直接复制JOptionPane的静态方法得来，仅是把对话框的标题栏logo改为懒农logo,稍作改动
 */
public class LazyCoderOptionPane {


    public static void showMessageDialog(Component parentComponent,
                                         Object message) throws HeadlessException {
        showMessageDialog(parentComponent, message, "",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMessageDialog(Component parentComponent,
                                         Object message, String title, int messageType)
            throws HeadlessException {
        showMessageDialog(parentComponent, message, title, messageType, null);
    }


    public static void showMessageDialog(Component parentComponent,
                                         Object message, String title, int messageType, Icon icon)
            throws HeadlessException {
        showOptionDialog(parentComponent, message, title, JOptionPane.DEFAULT_OPTION,
                messageType, icon, null, null);
    }

    public static int showConfirmDialog(Component parentComponent,
                                        Object message) throws HeadlessException {
        return showConfirmDialog(parentComponent, message,
                UIManager.getString("OptionPane.titleText"),
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    public static int showConfirmDialog(Component parentComponent,
                                        Object message, String title, int optionType)
            throws HeadlessException {
        return showConfirmDialog(parentComponent, message, title, optionType,
                JOptionPane.QUESTION_MESSAGE);
    }

    public static int showConfirmDialog(Component parentComponent,
                                        Object message, String title, int optionType, int messageType)
            throws HeadlessException {
        return showConfirmDialog(parentComponent, message, title, optionType,
                messageType, null);
    }

    public static int showConfirmDialog(Component parentComponent,
                                        Object message, String title, int optionType,
                                        int messageType, Icon icon) throws HeadlessException {
        return showOptionDialog(parentComponent, message, title, optionType,
                messageType, icon, null, null);
    }

    public static String showInputDialog(Object message)
            throws HeadlessException {
        return showInputDialog(null, message);
    }

    public static String showInputDialog(Object message, Object initialSelectionValue) {
        return showInputDialog(null, message, initialSelectionValue);
    }

    public static String showInputDialog(Component parentComponent,
                                         Object message) throws HeadlessException {
        return showInputDialog(parentComponent, message, "", JOptionPane.QUESTION_MESSAGE);
    }

    public static String showInputDialog(Component parentComponent, Object message,
                                         Object initialSelectionValue) {
        return (String) showInputDialog(parentComponent, message,
                "", JOptionPane.QUESTION_MESSAGE, null, null,
                initialSelectionValue);
    }

    public static String showInputDialog(Component parentComponent,
                                         Object message, String title, int messageType)
            throws HeadlessException {
        return (String) showInputDialog(parentComponent, message, title,
                messageType, null, null, null);
    }


    @SuppressWarnings("deprecation")
    public static Object showInputDialog(Component parentComponent,
                                         Object message, String title, int messageType, Icon icon,
                                         Object[] selectionValues, Object initialSelectionValue)
            throws HeadlessException {
        JOptionPane pane = new JOptionPane(message, messageType,
                JOptionPane.OK_CANCEL_OPTION, icon,
                null, null);

        pane.setWantsInput(true);
        pane.setSelectionValues(selectionValues);
        pane.setInitialSelectionValue(initialSelectionValue);
        pane.setComponentOrientation(((parentComponent == null) ?
                JOptionPane.getRootFrame() : parentComponent).getComponentOrientation());
        pane.setMessageType(messageType);

        JDialog dialog = pane.createDialog(parentComponent, title);
        dialog.setIconImage(FileUtil.LOGO_IMAGE.getImage());

        pane.selectInitialValue();
        dialog.show();
        dialog.dispose();

        Object value = pane.getInputValue();

        if (value == JOptionPane.UNINITIALIZED_VALUE) {
            return null;
        }
        return value;
    }

    @SuppressWarnings("deprecation")
    public static int showOptionDialog(Component parentComponent,
                                       Object message, String title, int optionType, int messageType,
                                       Icon icon, Object[] options, Object initialValue)
            throws HeadlessException {
        JOptionPane pane = new JOptionPane(message, messageType,
                optionType, icon,
                options, initialValue);

        pane.setInitialValue(initialValue);
        pane.setComponentOrientation(((parentComponent == null) ?
                JOptionPane.getRootFrame() : parentComponent).getComponentOrientation());
        pane.setMessageType(messageType);

        JDialog dialog = pane.createDialog(parentComponent, title);
        dialog.setIconImage(FileUtil.LOGO_IMAGE.getImage());

        pane.selectInitialValue();
        dialog.show();
        dialog.dispose();

        Object selectedValue = pane.getValue();

        if (selectedValue == null) {
            return JOptionPane.CLOSED_OPTION;
        }
        if (options == null) {
            if (selectedValue instanceof Integer) {
                return ((Integer) selectedValue).intValue();
            }
            return JOptionPane.CLOSED_OPTION;
        }
        for (int counter = 0, maxCounter = options.length;
             counter < maxCounter; counter++) {
            if (options[counter].equals(selectedValue)) {
                return counter;
            }
        }
        return JOptionPane.CLOSED_OPTION;
    }

}
