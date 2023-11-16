package com.lazycoder.uidatasourceedit.component.component.table.functionname;

import com.lazycoder.database.model.VariableData;
import com.lazycoder.uidatasourceedit.component.GeneralDataTypeEditFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class FunctionDataTypeEditFrame extends GeneralDataTypeEditFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -8562768842854778280L;

	private AbstractFunctionTable functionTable;

	private String dataTypeParam;

	private int row;


	public FunctionDataTypeEditFrame(AbstractFunctionTable functionTable, int row) {
		// TODO Auto-generated constructor stub
		super();
		setTitle("设置方法数据类型");

		this.functionTable = functionTable;
		this.dataTypeParam = functionTable.getDataTypeParam(row);
		this.row = row;

		ArrayList<String> dataTypeList = VariableData.getDataTypeList(dataTypeParam);
		dataTypeEditPane.restore(dataTypeList);

		okButton.addActionListener(okListener);
		setVisible(true);

	}

	private ActionListener okListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == okButton) {
				String param = dataTypeEditPane.getTextJson();
				functionTable.updateDataTypeParam(row, param);
				dispose();
			}
		}
	};

}
