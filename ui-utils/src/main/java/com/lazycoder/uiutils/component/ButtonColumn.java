package com.lazycoder.uiutils.component;

import com.lazycoder.uiutils.mycomponent.MyButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.Component;

/**
 * 源码摘自 https://blog.csdn.net/yaerfeng/article/details/7255204
 *
 * @author Administrator
 */
public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    /**
     *
     */
    private static final long serialVersionUID = 4191562297781398805L;

    private JTable table;
    protected MyButton renderButton;
    protected MyButton editButton;
    protected String text;

    public ButtonColumn(JTable table, int column) {
        super();
        this.table = table;
        renderButton = new MyButton();
        editButton = new MyButton();
        editButton.setFocusPainted(false);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor("Button.background"));
            renderButton.setForeground(UIManager.getColor("Button.foreground"));
            renderButton.setBackground(UIManager.getColor("Button.background"));
        }
        renderButton.setText((value == null) ? " " : value.toString());

        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = (value == null) ? " " : value.toString();
        editButton.setText(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JTable getTable() {
        return table;
    }

}