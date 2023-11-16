package com.lazycoder.uidatasourceedit.component.component.table.module;

import com.github.pagehelper.PageInfo;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.Module;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import lombok.Getter;

public class ModuleTable extends JTable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7928686330789652671L;

	@Getter
	private PageInfo<Module> pageInfoList;

	@Getter
	private List<Module> moduleList;

	public ModuleTable() {
		// TODO Auto-generated constructor stub
		JTableHeader tableHeader = this.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 25));
		setRowHeight(40);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
//		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		Object[][] data = {};
		ModuleTableModel moduleTableModel = new ModuleTableModel(data);
		setModel(moduleTableModel);
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setMaxWidth(140);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setMinWidth(100);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setPreferredWidth(100);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setMaxWidth(140);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setMinWidth(100);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setPreferredWidth(100);

		getColumnModel().getColumn(ModuleColumn.EDIT).setMaxWidth(80);
		getColumnModel().getColumn(ModuleColumn.DELETE).setMaxWidth(80);
		new ModuleTableEditButtonColumn(this, ModuleColumn.EDIT);
		new ModuleTableDeleteButtonColumn(this, ModuleColumn.DELETE);

//		showModuleList();
	}

	/**
	 * 显示所有模块
	 */
	public void showModuleList(PageInfo<Module> pageInfoList, int pageNum, int pageSize) {
		this.pageInfoList = pageInfoList;
		moduleList = pageInfoList.getList();
		Module temp;
		Object[][] data = new Object[moduleList.size()][];
		for (int i = 0; i < moduleList.size(); i++) {
			temp = moduleList.get(i);
			data[i] = new Object[]{temp.getModuleName(), temp.getClassName(), temp.getNote(), "修改", "删除"};
		}
		ModuleTableModel moduleTableModel = new ModuleTableModel(data);
		setModel(moduleTableModel);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setMaxWidth(140);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setMinWidth(100);
		getColumnModel().getColumn(ModuleColumn.MODULE_NAME).setPreferredWidth(100);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setMaxWidth(140);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setMinWidth(100);
		getColumnModel().getColumn(ModuleColumn.CLASS_NAME).setPreferredWidth(100);

		getColumnModel().getColumn(ModuleColumn.EDIT).setMaxWidth(80);
		getColumnModel().getColumn(ModuleColumn.DELETE).setMaxWidth(80);
		new ModuleTableEditButtonColumn(this, ModuleColumn.EDIT);
		new ModuleTableDeleteButtonColumn(this, ModuleColumn.DELETE);

		ModuleTableCellRenderer moduleTableCellRenderer = new ModuleTableCellRenderer();
		TableColumn col;
		for (int i = 0; i < ModuleColumn.EDIT; i++) {
			col = getColumn(getColumnName(i));
			col.setCellRenderer(moduleTableCellRenderer);
		}
	}


	@Override
	public boolean isCellEditable(int row, int column) {
		boolean flag = false;
		if (column == ModuleColumn.EDIT || column == ModuleColumn.DELETE) {
			flag = true;
		}
		return flag;
	}

	class ModuleTableCellRenderer extends DefaultTableCellRenderer implements BaseModel {

		/**
		 *
		 */
		private static final long serialVersionUID = -9082085553256250484L;

		private final Color enableColor = Color.black,
				enableFalseColor = Color.GRAY;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
													   int row, int column) {
			// TODO Auto-generated method stub
			if (moduleList.get(row).getEnabledState() == TRUE_) {
				setForeground(enableColor);
//				setBackground(enableColor);
				setToolTipText(null);

			} else if (moduleList.get(row).getEnabledState() == FALSE_) {
				setForeground(enableFalseColor);
//				setBackground(enableFalseColor);
				setToolTipText("该模块还没写过任何内容，无法使用");
			}
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}

	}

	class ModuleTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 2957552183940504340L;

		public ModuleTableModel(Object[][] data) {
			// TODO Auto-generated constructor stub
			super(data, new String[]{"模块", "分类", "备注", "", ""});
		}
	}


}
