package com.lazycoder.uiutils.component;


import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * 下拉框
 *
 * @param <T>
 * @author Administrator
 */
public class ComboboxColumn<T> extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {//extends DefaultCellEditor {

	/**
	 *
	 */
	private static final long serialVersionUID = 4191562297781398805L;
	/**
	 * 下拉框
	 */
	protected JComboBox<T> comboBox = null;

	private JTable table;

	public ComboboxColumn(JTable table, JComboBox<T> comboBox, int column) {
//		super(comboBox);
		super();
		this.table = table;
		this.comboBox = comboBox;

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellEditor(this);
		columnModel.getColumn(column).setCellEditor(this);
	}


	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
												   int row, int column) {
		return comboBox;
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return comboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		// TODO Auto-generated method stub
		return comboBox;
	}

	/**
	 * 获取当前值
	 */
//	public String getCellEditorValue() {
//		String text = null;
//		if (comboBox != null) {
//			text = (String) comboBox.getSelectedItem();
//		}
//		return text;
//	}
	public JComboBox<T> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<T> comboBox) {
		this.comboBox = comboBox;
	}

	public JTable getTable() {
		return table;
	}

//	class ComboboxCellRenderer implements TableCellRenderer {
//
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//													   int row, int column) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//	}

}
