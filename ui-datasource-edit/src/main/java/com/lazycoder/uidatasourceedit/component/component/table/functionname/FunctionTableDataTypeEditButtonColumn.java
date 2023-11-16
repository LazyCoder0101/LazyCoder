package com.lazycoder.uidatasourceedit.component.component.table.functionname;

import com.lazycoder.uiutils.component.ButtonColumn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FunctionTableDataTypeEditButtonColumn extends ButtonColumn {

	/**
	 *
	 */
	private static final long serialVersionUID = 236365212046585998L;

	private AbstractFunctionTable table;

	public FunctionTableDataTypeEditButtonColumn(AbstractFunctionTable table, int column) {
		super(table, column);
		this.table = table;
		// TODO Auto-generated constructor stub
		getEditButton().addActionListener(actionListener);
	}

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			FunctionDataTypeEditFrame frame = new FunctionDataTypeEditFrame(table, getTable().getSelectedRow());
			fireEditingStopped();
		}
	};

}
