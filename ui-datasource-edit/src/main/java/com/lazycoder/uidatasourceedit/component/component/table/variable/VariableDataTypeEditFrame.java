package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.database.model.VariableData;
import com.lazycoder.uidatasourceedit.component.GeneralDataTypeEditFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VariableDataTypeEditFrame extends GeneralDataTypeEditFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -2750619563932056168L;

	private AbstractVariableTable variableTable;

	private String dataTypeParam;

	private int row;

	public VariableDataTypeEditFrame(AbstractVariableTable variableTable, int row) {
		// TODO Auto-generated constructor stub
		super();
		setTitle("设置数据类型");
		this.variableTable = variableTable;
		this.dataTypeParam = variableTable.getDataTypeParam(row);
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
				variableTable.updateDataTypeParam(row, param);
				dispose();
			}
		}
	};


}
