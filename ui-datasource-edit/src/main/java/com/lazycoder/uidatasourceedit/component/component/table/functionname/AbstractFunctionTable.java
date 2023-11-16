package com.lazycoder.uidatasourceedit.component.component.table.functionname;

import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.service.vo.AttributeUsageRange;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public abstract class AbstractFunctionTable extends JTable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7202805705348515519L;

	protected static String[] columnNames = {"方法作用名", "方法名", "数据类型", "使用范围"};
	/**
	 * 方法作用名
	 */
	protected static int roleOfFunctionName = 0,

	/**
	 * 模块名
	 */
	functionName = 1,

	/**
	 * 数据类型
	 */
	dataType = 2,

	/**
	 * 使用范围
	 */
	usingRange = 3;

	private DefaultTableModel tableModel;
	/**
	 * 数据类型存放列表
	 */
	private ArrayList<String> dataTypeList = new ArrayList<>();
	/**
	 * 使用范围存放列表
	 */
	private ArrayList<Integer> usingRangeList = new ArrayList<Integer>();

	public AbstractFunctionTable() {
		// TODO Auto-generated constructor stub
		init(columnNames);
	}

	protected void init(String[] theColumnNames) {
		JTableHeader tableHeader = this.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(tableHeader.getWidth(), 25));
		setRowHeight(30);
		setShowHorizontalLines(true);
		setShowVerticalLines(true);
		getTableHeader().setReorderingAllowed(false);

		Object[][] data = {};
		tableModel = new DefaultTableModel(data, theColumnNames);
		setModel(tableModel);
//		setGridColor(Color.black);
//		setShowGrid(true);
//		setShowHorizontalLines(true);
//		setShowVerticalLines(true);
//		setBackground(Color.white);

		new FunctionTableDataTypeEditButtonColumn(this, dataType);
		new FunctionAvaliableRangeColumn(this, usingRange);

	}

	/**
	 * 获取数据类型参数
	 *
	 * @param row
	 * @return
	 */
	public String getDataTypeParam(int row) {
		return dataTypeList.get(row);
	}

	/**
	 * 添加方法名
	 */
	public void addFunctionName() {
		String dataTypeTemp = "[]";
		Object[] str = {"", "", "", AttributeUsageRange.ApplyToAll.getShowText()};
		dataTypeList.add(dataTypeTemp);
		usingRangeList.add(AttributeUsageRange.ApplyToAll.getDictionaryValue());
		tableModel.addRow(str);
		this.updateUI();
	}

	/**
	 * 删除最后一个方法名
	 */
	public void delFunctionName() {
		if (getRowCount() > 0) {
			dataTypeList.remove(dataTypeList.size() - 1);
			usingRangeList.remove(usingRangeList.size() - 1);
			tableModel.removeRow(getRowCount() - 1);
			updateUI();
		}
	}

	protected ArrayList<FunctionNameData> getFunctionNameDataList(int property) {
		int row = tableModel.getRowCount();
		ArrayList<FunctionNameData> list = new ArrayList<>();
		FunctionNameData functionNameData;
		for (int a = 0; a < row; a++) {
			functionNameData = new FunctionNameData();
			functionNameData.setFunctionNameProperty(property);
			functionNameData.setRoleOfFunctionName((String) tableModel.getValueAt(a, roleOfFunctionName));
			functionNameData.setFunctionName((String) tableModel.getValueAt(a, functionName));
			functionNameData.setDataTypeParam(dataTypeList.get(a));
			functionNameData.setTheAvaliableRange(
					AttributeUsageRange.convertShowTextIntoDictionaryValue((String) getValueAt(a, usingRange)));

			// 设置方法名范围
			functionNameData.setSerialNumber(a + 1);
			list.add(functionNameData);
		}
		return list;
	}

	/**
	 * 获取方法名总数
	 *
	 * @return
	 */
	public int getFunctionNameNum() {
		return getRowCount();
	}

	/**
	 * 检查方法名
	 */
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		int row = tableModel.getRowCount();
		String roleTemp1, roleTemp2, fNameTemp1, fNameTemp2;
		for (int a = 0; a < row; a++) {
			roleTemp1 = (String) tableModel.getValueAt(a, roleOfFunctionName);
			if (roleTemp1 == null
					|| "".equals(roleTemp1.trim())) {// 方法作用名为空
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，麻烦检查一下，第" + (a + 1) + "个“方法作用名”还没写喔！", "系统消息",
						JOptionPane.PLAIN_MESSAGE);
				break;
			}

			fNameTemp1 = (String) tableModel.getValueAt(a, functionName);
			if (fNameTemp1 == null
					|| "".equals(fNameTemp1.trim())) {// 方法名没写
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，我看了一下，第" + (a + 1) + "个“方法名”还没写喔！", "系统消息",
						JOptionPane.PLAIN_MESSAGE);
				break;
			}

			for (int k = a + 1; k < row; k++) {
				roleTemp2 = (String) tableModel.getValueAt(k, roleOfFunctionName);
				if (roleTemp1.trim().equals(roleTemp2.trim())) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，麻烦检查一下，你写的第" + (a + 1) + "个方法名，\n还有第" + (k + 1) + "个方法名，他们的方法作用名是一样的，这样我没法录入", "系统消息",
							JOptionPane.PLAIN_MESSAGE);
					break;
				}

				fNameTemp2 = (String) tableModel.getValueAt(k, functionName);
				if (fNameTemp1.trim().equals(fNameTemp2.trim())) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，麻烦检查一下，你写的第" + (a + 1) + "个方法名，\n还有第" + (k + 1) + "个方法名，他们的方法名是一样的，这样我没法录入", "系统消息",
							JOptionPane.PLAIN_MESSAGE);
					break;
				}
			}
			if (flag == false) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 更改某一行的数据类型参数
	 *
	 * @param row
	 * @param dataTypeParam
	 */
	public void updateDataTypeParam(int row, String dataTypeParam) {
		dataTypeList.set(row, dataTypeParam);
		String text = getShowDataTypeStr(dataTypeParam);

		Object[] str;
		Object[][] data = {};
		DefaultTableModel newTableModel = new DefaultTableModel(data, columnNames);
		for (int r = 0; r < tableModel.getRowCount(); r++) {
			if (row == r) {
				str = new Object[]{tableModel.getValueAt(r, roleOfFunctionName), tableModel.getValueAt(r, functionName), text,
						tableModel.getValueAt(r, usingRange)
				};
				newTableModel.addRow(str);

			} else {
				str = new Object[]{tableModel.getValueAt(r, roleOfFunctionName), tableModel.getValueAt(r, functionName),
						tableModel.getValueAt(r, dataType), tableModel.getValueAt(r, usingRange)
				};
				newTableModel.addRow(str);
			}
		}
		setModel(newTableModel);

		new FunctionTableDataTypeEditButtonColumn(this, dataType);
		new FunctionAvaliableRangeColumn(this, usingRange);

		tableModel = newTableModel;
	}

	/**
	 * 更改使用范围
	 */
	public void updateUsingRange(int row, AttributeUsageRange selectAttributeUsageRange) {
		usingRangeList.set(row, selectAttributeUsageRange.getDictionaryValue());
		String text = selectAttributeUsageRange.getShowText();

		Object[] str;
		Object[][] data = {};
		DefaultTableModel newTableModel = new DefaultTableModel(data, columnNames);
		for (int r = 0; r < tableModel.getRowCount(); r++) {
			if (row == r) {
				str = new Object[]{tableModel.getValueAt(r, roleOfFunctionName), tableModel.getValueAt(r, functionName),
						tableModel.getValueAt(r, dataType), text
				};
				newTableModel.addRow(str);

			} else {
				str = new Object[]{tableModel.getValueAt(r, roleOfFunctionName), tableModel.getValueAt(r, functionName),
						tableModel.getValueAt(r, dataType),  tableModel.getValueAt(r, usingRange)
				};
				newTableModel.addRow(str);
			}
		}
		setModel(newTableModel);

		new FunctionTableDataTypeEditButtonColumn(this, dataType);
		new FunctionAvaliableRangeColumn(this, usingRange);

		tableModel = newTableModel;
	}

	/**
	 * 根据方法名显示在表格里
	 *
	 * @param functionNameDatalList
	 */
	protected void displayContents(List<FunctionNameData> functionNameDatalList) {
		// TODO Auto-generated method stub
		FunctionNameData temp;
		if (functionNameDatalList != null) {
			for (int i = 0; i < functionNameDatalList.size(); i++) {
				temp = functionNameDatalList.get(i);
				Object[] str = {temp.getRoleOfFunctionName(), temp.getFunctionName(), getShowDataTypeStr(temp),
						AttributeUsageRange.dictionaryValueToVariableUsageRange(temp.getTheAvaliableRange())
								.getShowText()};
				dataTypeList.add(temp.getDataTypeParam());
				usingRangeList.add(temp.getTheAvaliableRange());

				tableModel.addRow(str);
			}
			AbstractFunctionTable.this.updateUI();
		}
	}

	/**
	 * 获取显示数据类型的字符
	 *
	 * @param functionNameData
	 * @return
	 */
	private String getShowDataTypeStr(FunctionNameData functionNameData) {
		return getShowDataTypeStr(functionNameData.getDataTypeParam());
	}

	/**
	 * 获取显示数据类型的字符
	 *
	 * @param dataTypeParam
	 * @return
	 */
	private String getShowDataTypeStr(String dataTypeParam) {
		ArrayList<String> list = FunctionNameData.getDataTypeList(dataTypeParam);
		StringBuilder out = new StringBuilder();
		if (list != null) {
			if (list.size() > 0) {
				out.append(list.get(0));
				if (list.size() > 1) {
					for (int i = 1; i < list.size(); i++) {
						out.append("、" + list.get(i));
					}
				}
			}
		}
		return out.toString();
	}

	protected void clear() {
		dataTypeList.clear();
		usingRangeList.clear();

		init(columnNames);
	}

	/**
	 * 对应该表格的使用范围有哪个，构造对应的下拉框添加进去
	 *
	 * @return
	 */
	public abstract VariableUsageRangeCombobox getFunctionNameUsageRangeCombobox();

}
