package com.lazycoder.uidatasourceedit.component.component.table.functionname;

import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uiutils.component.ButtonColumn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionAvaliableRangeColumn extends ButtonColumn {

	/**
	 *
	 */
	private static final long serialVersionUID = -1059168857249432756L;

	private AbstractFunctionTable table;

	public FunctionAvaliableRangeColumn(AbstractFunctionTable table, int column) {
		super(table, column);
		this.table = table;
		// TODO Auto-generated constructor stub
		getEditButton().addActionListener(actionListener);
	}

	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String usingRangeTextString = (String) table.getValueAt(table.getSelectedRow(), AbstractFunctionTable.usingRange);
			AttributeUsageRange temp = AttributeUsageRange.convertShowTextIntoVariableUsageRange(usingRangeTextString);

			VariableUsageRangeCombobox comboBox = table.getFunctionNameUsageRangeCombobox();
			comboBox.setSelectedItem(temp);

			FunctionUsingRangeFrame frame = new FunctionUsingRangeFrame(comboBox) {

				/**
				 *
				 */
				private static final long serialVersionUID = 2916343396627846889L;

				@Override
				public void ok() {
					table.updateUsingRange(table.getSelectedRow(),
							(AttributeUsageRange) comboBox.getModel().getSelectedItem());
					super.ok();
				}

				;
			};
			frame.setLocation(550, 400);
			fireEditingStopped();
		}
	};

}
