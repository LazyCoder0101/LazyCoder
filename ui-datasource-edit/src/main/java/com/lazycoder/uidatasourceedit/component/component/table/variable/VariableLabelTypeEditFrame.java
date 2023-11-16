package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.database.model.VariableData;
import com.lazycoder.uidatasourceedit.component.GeneralLabelTypeEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class VariableLabelTypeEditFrame extends GeneralLabelTypeEditFrame {

	private AbstractVariableTable variableTable;

	private String labelTypeParam;

	private int row;

	public VariableLabelTypeEditFrame(AbstractVariableTable variableTable, int row) {
		// TODO Auto-generated constructor stub
		super();
		setTitle("变量标签类型设置");

		this.variableTable = variableTable;
		this.labelTypeParam = variableTable.getLabelTypeParam(row);
		this.row = row;

		ArrayList<String> dataTypeList = VariableData.getLabelTypeParam(labelTypeParam);
		labelTypeEditPane.restore(dataTypeList);

		okButton.addActionListener(okListener);
		setVisible(true);

	}

	private ActionListener okListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == okButton) {
				String param = labelTypeEditPane.getTextJson();
				variableTable.updateLabelTypeParam(row, param);
				dispose();
			}
		}
	};


}
