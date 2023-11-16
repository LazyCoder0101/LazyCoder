package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.uiutils.component.ButtonColumn;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import javax.swing.UIManager;


public class VariableTableDataTypeEditButtonColumn extends ButtonColumn {

	/**
	 *
	 */
	private static final long serialVersionUID = 199893396225037011L;

	private AbstractVariableTable table;

	public VariableTableDataTypeEditButtonColumn(AbstractVariableTable table, int column) {
		super(table, column);
		this.table = table;
		// TODO Auto-generated constructor stub
		getEditButton().addActionListener(actionListener);
	}

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new VariableDataTypeEditFrame(table, getTable().getSelectedRow());
			fireEditingStopped();
		}
	};

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
												   int row, int column) {
		if (hasFocus) {
//			renderButton.setForeground(table.getForeground());
//			renderButton.setBackground(UIManager.getColor("Button.background"));
			renderButton.setForeground(UIManager.getColor("Button.foreground"));
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}
		renderButton.setText((String) this.table.getTableModel().getValueAt(row, column));

		return renderButton;
	}

}
