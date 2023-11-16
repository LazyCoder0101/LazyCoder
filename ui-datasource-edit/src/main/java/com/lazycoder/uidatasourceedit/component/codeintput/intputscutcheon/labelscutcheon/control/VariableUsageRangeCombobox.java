package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.service.vo.AttributeUsageRange;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class VariableUsageRangeCombobox extends JComboBox<AttributeUsageRange> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8222903636823638891L;

	@SuppressWarnings("unchecked")
	public VariableUsageRangeCombobox() {
		// TODO Auto-generated constructor stub
		this.setRenderer(new VariableUsageRangeComboboxCellRenderer());
	}

	/**
	 * 选中对应枚举值
	 *
	 * @param selectedAttributeUsageRange
	 */
	public void setSelectedVariableUsageRange(AttributeUsageRange selectedAttributeUsageRange) {
		// TODO Auto-generated method stub
		ComboBoxModel<AttributeUsageRange> theModel = getModel();
		for (int i = 0; i < theModel.getSize(); i++) {
			if (theModel.getElementAt(i).getDictionaryValue() == selectedAttributeUsageRange.getDictionaryValue()) {
				setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * 选中对应枚举值
	 *
	 * @param selectedVariableUsageRangeDictionaryValue
	 */
	public void setSelectedVariableUsageRange(int selectedVariableUsageRangeDictionaryValue) {
		// TODO Auto-generated method stub
		ComboBoxModel<AttributeUsageRange> theModel = getModel();
		for (int i = 0; i < theModel.getSize(); i++) {
			if (theModel.getElementAt(i).getDictionaryValue() == selectedVariableUsageRangeDictionaryValue) {
				setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * 获取选中的使用范围的字典值（没有返回-1）
	 */
	public int getSelectedDictionaryValue() {
		Integer selectedDictionaryValue = -1;
		AttributeUsageRange selectedValue = (AttributeUsageRange) getModel().getSelectedItem();
		if (selectedValue != null) {
			selectedDictionaryValue = selectedValue.getDictionaryValue();
		}
		return selectedDictionaryValue;
	}

	@SuppressWarnings("rawtypes")
	class VariableUsageRangeComboboxCellRenderer implements ListCellRenderer {

		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		JLabel renderer;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
													  boolean cellHasFocus) {

			// 下拉列表每个选项显示的就是这个
			renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value instanceof AttributeUsageRange) {
				AttributeUsageRange valueTemp = (AttributeUsageRange) value;
				renderer.setText(valueTemp.getShowText());

			}
			return renderer;
		}
	}

}
