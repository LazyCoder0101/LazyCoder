package com.lazycoder.uidatasourceedit.component.component.table.variable;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.VariableData;
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
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractVariableTable extends JTable implements BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 3508686328646608650L;

    protected static String[] columnNames = {"变量作用名", "变量名", "数据类型", "使用范围", "标签类型", "自动选择"};

    /**
     * 变量作用名
     */
    protected static int roleOfVariableName = 0,

    /**
     * 模块名
     */
    variableName = 1,

    /**
     * 数据类型
     */
    dataType = 2,

    /**
     * 使用范围
     */
    usingRange = 3,

    /**
     * 标签类型
     */
    labelType = 4,

    /**
     * 是否需要自动选择
     */
    dontNeedTheUserToSelect = 5;

    @Getter
    private DefaultTableModel tableModel;

    /**
     * 数据类型存放列表
     */
    private ArrayList<String> dataTypeList = new ArrayList<>();

    /**
     * 标签类型存放列表
     */
    private ArrayList<String> labelTypeList = new ArrayList<>();

    /**
     * 使用范围存放列表
     */
    private ArrayList<Integer> usingRangeList = new ArrayList<Integer>();

    /**
     * 是否设置自动选择
     */
    private ArrayList<Integer> dontNeedTheUserToSelectList = new ArrayList<>();

    public AbstractVariableTable() {
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


        new VariableTableDataTypeEditButtonColumn(this, dataType);
        new VariableAvaliableRangeColumn(this, usingRange);
        new VariableTableLabelTypeEditButtonColumn(this, labelType);
        AutoSelectComboboxColumn.createAutoSelectComboboxColumn(this, AbstractVariableTable.dontNeedTheUserToSelect);
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
     * 获取标签类型参数
     *
     * @param row
     * @return
     */
    public String getLabelTypeParam(int row) {
        return labelTypeList.get(row);
    }

    /**
     * 添加变量
     */
    public void addVariable() {
        Object[] str = {"", "", "", AttributeUsageRange.ApplyToAll.getShowText(), "", AutoSelectComboboxColumn.optionShowValue[1]};
        dataTypeList.add("[]");
        labelTypeList.add("[]");
        usingRangeList.add(AttributeUsageRange.ApplyToAll.getDictionaryValue());
        dontNeedTheUserToSelectList.add(FALSE_);
        tableModel.addRow(str);
        this.updateUI();
    }

    /**
     * 删除最后一个变量
     */
    public void delVariable() {
        if (getRowCount() > 0) {
            dataTypeList.remove(dataTypeList.size() - 1);
            labelTypeList.remove(labelTypeList.size() - 1);
            usingRangeList.remove(usingRangeList.size() - 1);
            dontNeedTheUserToSelectList.remove(dontNeedTheUserToSelectList.size() - 1);
            tableModel.removeRow(getRowCount() - 1);
            updateUI();
        }
    }

    protected ArrayList<VariableData> getVariableDataList(int property) {
        int row = tableModel.getRowCount();
        ArrayList<VariableData> list = new ArrayList<>();
        VariableData variableData;
        for (int a = 0; a < row; a++) {
            variableData = new VariableData();
            variableData.setVariableProperty(property);
            variableData.setRoleOfVariableName((String) tableModel.getValueAt(a, roleOfVariableName));
            variableData.setVariableName((String) tableModel.getValueAt(a, variableName));
            variableData.setDataTypeParam(dataTypeList.get(a));
            variableData.setTheAvaliableRange(
                    AttributeUsageRange.convertShowTextIntoDictionaryValue((String) getValueAt(a, usingRange)));
            variableData.setLabelTypeParam(labelTypeList.get(a));
            variableData.setDontNeedTheUserToSelect(dontNeedTheUserToSelectList.get(a));

            // 设置变量范围
            variableData.setSerialNumber(a + 1);
            list.add(variableData);
        }
        // "变量作用名",
        // "变量名",
        // "数据类型",
        // "使用范围"
        // "标签类型"
        // "自动选择"
        return list;
    }

    /**
     * 获取变量总数
     *
     * @return
     */
    public int getVariableNum() {
        return getRowCount();
    }

    /**
     * 检查变量
     */
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        int row = tableModel.getRowCount();
        String roleTemp1, roleTemp2, vNameTemp1, vNameTemp2;
        for (int a = 0; a < row; a++) {
            roleTemp1 = (String) tableModel.getValueAt(a, roleOfVariableName);
            if (roleTemp1 == null
                    || "".equals((roleTemp1).trim())) {// 变量作用名为空
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o  亲，麻烦检查一下，第" + (a + 1) + "个“变量作用名”还没写喔！", "系统消息",
                        JOptionPane.PLAIN_MESSAGE);
                break;
            }

            vNameTemp1 = (String) tableModel.getValueAt(a, variableName);
            if (vNameTemp1 == null
                    || "".equals(vNameTemp1.trim())) {// 变量名没写
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o  亲，我看了一下，第" + (a + 1) + "个“变量名”还没写喔！", "系统消息",
                        JOptionPane.PLAIN_MESSAGE);
                break;
            }

            for (int k = a + 1; k < row; k++) {
                roleTemp2 = (String) tableModel.getValueAt(k, roleOfVariableName);
                if (roleTemp1.trim().equals(roleTemp2.trim())) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，麻烦检查一下，你写的第" + (a + 1) + "个变量，\n还有第" + (k + 1) + "个变量，他们的变量作用名是一样的，这样我没法录入", "系统消息",
                            JOptionPane.PLAIN_MESSAGE);
                    break;
                }

                vNameTemp2 = (String) tableModel.getValueAt(k, variableName);
                if (vNameTemp1.trim().equals(vNameTemp2.trim())) {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "(｡•́︿•̀｡)  亲，麻烦检查一下，你写的第" + (a + 1) + "个变量，\n还有第" + (k + 1) + "个变量，他们的变量名是一样的，这样我没法录入", "系统消息",
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
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName), text,
                        tableModel.getValueAt(r, usingRange), tableModel.getValueAt(r, labelType), tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);

            } else {
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName),
                        tableModel.getValueAt(r, dataType),
                        tableModel.getValueAt(r, usingRange), tableModel.getValueAt(r, labelType), tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);
            }
        }
        setModel(newTableModel);

        new VariableTableDataTypeEditButtonColumn(this, dataType);
        new VariableAvaliableRangeColumn(this, usingRange);
        new VariableTableLabelTypeEditButtonColumn(this, labelType);
        AutoSelectComboboxColumn.createAutoSelectComboboxColumn(this, AbstractVariableTable.dontNeedTheUserToSelect);

        tableModel = newTableModel;
    }

    /**
     * 更改某一行的标签类型参数
     *
     * @param row
     * @param labelTypeParam
     */
    public void updateLabelTypeParam(int row, String labelTypeParam) {
        labelTypeList.set(row, labelTypeParam);
        String text = getShowLabelTypeStr(labelTypeParam);

        Object[] str;
        Object[][] data = {};
        DefaultTableModel newTableModel = new DefaultTableModel(data, columnNames);
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            if (row == r) {
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName),
                        tableModel.getValueAt(r, dataType),
                        tableModel.getValueAt(r, usingRange),text, tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);

            } else {
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName),
                        tableModel.getValueAt(r, dataType),
                        tableModel.getValueAt(r, usingRange), tableModel.getValueAt(r, labelType), tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);
            }
        }
        setModel(newTableModel);

        new VariableTableDataTypeEditButtonColumn(this, dataType);
        new VariableAvaliableRangeColumn(this, usingRange);
        new VariableTableLabelTypeEditButtonColumn(this, labelType);
        AutoSelectComboboxColumn.createAutoSelectComboboxColumn(this, AbstractVariableTable.dontNeedTheUserToSelect);

        tableModel = newTableModel;
    }

    /**
     * 更改是否自动选择的值
     *
     * @param row
     * @param dontNeedTheUserToSelect
     */
    public void updateWetherAutoSelectParam(int row, int dontNeedTheUserToSelect) {
        dontNeedTheUserToSelectList.set(row, dontNeedTheUserToSelect);
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
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName),
                        tableModel.getValueAt(r, dataType),
                        text,
                        tableModel.getValueAt(r, labelType), tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);

            } else {
                str = new Object[]{tableModel.getValueAt(r, roleOfVariableName), tableModel.getValueAt(r, variableName),
                        tableModel.getValueAt(r, dataType),
                        tableModel.getValueAt(r, usingRange),
                        tableModel.getValueAt(r, labelType), tableModel.getValueAt(r, dontNeedTheUserToSelect)
                };
                newTableModel.addRow(str);
            }
        }
        setModel(newTableModel);

        new VariableTableDataTypeEditButtonColumn(this, dataType);
        new VariableAvaliableRangeColumn(this, usingRange);
        new VariableTableLabelTypeEditButtonColumn(this, labelType);
        AutoSelectComboboxColumn.createAutoSelectComboboxColumn(this, AbstractVariableTable.dontNeedTheUserToSelect);

        tableModel = newTableModel;
    }

    /**
     * 根据变量显示在表格里
     *
     * @param variableList
     */
    protected void displayContents(List<VariableData> variableList) {
        // TODO Auto-generated method stub
        if (variableList != null) {
            Object[] str;
            for (VariableData temp : variableList) {
                str = new Object[]{
                        temp.getRoleOfVariableName(),
                        temp.getVariableName(),
                        getShowDataTypeStr(temp.getDataTypeParam()),
                        AttributeUsageRange.dictionaryValueToVariableUsageRange(temp.getTheAvaliableRange()).getShowText(),
                        getShowLabelTypeStr(temp.getLabelTypeParam()),
                        AutoSelectComboboxColumn.getShowAutoSelectText(temp.getDontNeedTheUserToSelect())
                };
                dataTypeList.add(temp.getDataTypeParam());
                labelTypeList.add(temp.getLabelTypeParam());
                usingRangeList.add(temp.getTheAvaliableRange());

                dontNeedTheUserToSelectList.add(temp.getDontNeedTheUserToSelect());
                // "变量作用名",
                // "变量名",
                // "数据类型",
                // "使用范围"
                // "标签类型"
                // "自动选择"
                tableModel.addRow(str);
            }
            AbstractVariableTable.this.updateUI();
        }
    }

    /**
     * 获取显示数据类型的字符
     *
     * @param dataTypeParam
     * @return
     */
    private String getShowDataTypeStr(String dataTypeParam) {
        ArrayList<String> list = VariableData.getDataTypeList(dataTypeParam);
        StringBuilder out = new StringBuilder();
        if (list != null) {
            if (list.size() > 0) {
                out.append(StringUtils.join(list, "、"));
            }
        }
        return out.toString();
    }

    /**
     * 获取显示数据类型的字符
     *
     * @param labelTypeParam
     * @return
     */
    private String getShowLabelTypeStr(String labelTypeParam) {
        ArrayList<String> list = VariableData.getLabelTypeParam(labelTypeParam);
        StringBuilder out = new StringBuilder();
        if (list != null) {
            if (list.size() > 0) {
                out.append(StringUtils.join(list, "、"));
            }
        }
        return out.toString();
    }

    protected void clear() {
        dataTypeList.clear();
        labelTypeList.clear();
        usingRangeList.clear();
        dontNeedTheUserToSelectList.clear();
        init(columnNames);
    }

    /**
     * 对应该表格的使用范围有哪个，构造对应的下拉框添加进去
     *
     * @return
     */
    public abstract VariableUsageRangeCombobox getVariableUsageRangeCombobox();

}
