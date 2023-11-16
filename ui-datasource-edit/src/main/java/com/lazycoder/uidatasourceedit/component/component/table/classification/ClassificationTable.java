package com.lazycoder.uidatasourceedit.component.component.table.classification;

import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import java.util.List;


public class ClassificationTable extends JTable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7928686330789652671L;

	public ClassificationTable() {
		// TODO Auto-generated constructor stub
		JTableHeader tableHeader = this.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 25));
		setRowHeight(40);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
//		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		Object[][] data = {};
		ClassificationTableModel classificationTableModel = new ClassificationTableModel(data);
		getTableHeader().setReorderingAllowed(false);
		setModel(classificationTableModel);
		getColumnModel().getColumn(ClassificationColumn.EDIT).setMaxWidth(80);
		getColumnModel().getColumn(ClassificationColumn.DELETE).setMaxWidth(80);
		new ClassificationTableEditButtonColumn(this, ClassificationColumn.EDIT);
		new ClassificationTableDeleteButtonColumn(this, ClassificationColumn.DELETE);

		showClassList();
	}

	public void showClassList() {

		List<TheClassification> list = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
		Object[][] data = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			data[i] = new Object[]{list.get(i).getClassName(), "修改", "删除"};
		}
		ClassificationTableModel classificationTableModel = new ClassificationTableModel(data);
		setModel(classificationTableModel);
		getColumnModel().getColumn(ClassificationColumn.EDIT).setMaxWidth(80);
		getColumnModel().getColumn(ClassificationColumn.DELETE).setMaxWidth(80);
		new ClassificationTableEditButtonColumn(this, ClassificationColumn.EDIT);
		new ClassificationTableDeleteButtonColumn(this, ClassificationColumn.DELETE);

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		boolean flag = true;
		if (column == ClassificationColumn.CLASS_NAME || row == 0) {
			flag = false;
		}
		return flag;
	}

	class ClassificationTableModel extends DefaultTableModel {

		/**
		 *
		 */
		private static final long serialVersionUID = 2957552183940504340L;

		public ClassificationTableModel(Object[][] data) {
			// TODO Auto-generated constructor stub
			super(data, new String[]{"分类", "", ""});
		}

	}

}
