package com.lazycoder.uidatasourceedit.component;

import com.lazycoder.database.common.EncodingTypeEnum;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class EncodingCombobox extends JComboBox<EncodingTypeEnum> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2279537533226770588L;

	@SuppressWarnings("unchecked")
	public EncodingCombobox() {
		// TODO Auto-generated constructor stub
		setRenderer(new EncodingCellRenderer());
		DefaultComboBoxModel<EncodingTypeEnum> model = new DefaultComboBoxModel<EncodingTypeEnum>();
		for (EncodingTypeEnum temp : EncodingTypeEnum.values()) {
			model.addElement(temp);
		}
		setModel(model);
		setSelectedIndex(0);
	}

	@Override
	public EncodingTypeEnum getSelectedItem() {
		// TODO Auto-generated method stub
		return (EncodingTypeEnum) super.getSelectedItem();
	}

	public void setSelectedItem(EncodingTypeEnum programingLanguage) {
		// TODO Auto-generated method stub
		super.setSelectedItem(programingLanguage);
	}

}

@SuppressWarnings("rawtypes")
class EncodingCellRenderer implements ListCellRenderer {

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	JLabel renderer;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
												  boolean cellHasFocus) {

		renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String theText = null;
		if (value instanceof EncodingTypeEnum) {
			EncodingTypeEnum valueTemp = (EncodingTypeEnum) value;
			theText = valueTemp.getEncodingDetectParam();
		}
		renderer.setText(theText);
		return renderer;
	}

}
