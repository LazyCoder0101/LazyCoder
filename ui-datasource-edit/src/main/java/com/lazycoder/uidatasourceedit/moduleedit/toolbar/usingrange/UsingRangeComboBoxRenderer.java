package com.lazycoder.uidatasourceedit.moduleedit.toolbar.usingrange;

import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;


public class UsingRangeComboBoxRenderer extends MultiSelectComboBox.MultiSelectComboBoxRenderer {

	/**
	 *
	 */
	private static final long serialVersionUID = -6252513864067539339L;

	private final static Color MAIN_COLOR = new Color(26, 115, 232),
			ADDITIONAL_COLOR = new Color(100, 176, 255);
	// module_file_color = new Color(245+1, 170+3, 125+1);

	public UsingRangeComboBoxRenderer() {
		super();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
												  boolean cellHasFocus) {
		// setComponentOrientation(list.getComponentOrientation());

		String theText = ""; // 显示内容
		String toolTipText = ""; // 提示文本
		if (value instanceof UsingObject) {
			UsingObject usingRange = (UsingObject) value; // 把值转换成数组

			theText = usingRange.getShowName(); // 获取显示内容
			if (usingRange.getType() == UsingObject.ADDITIONAL_TYPE) {
				toolTipText = "第" + usingRange.getSerial() + "个可选模板"; // 获取提示文本
				setToolTipText(toolTipText);

			}

			if (usingRange.getType() == UsingObject.MAIN_TYPE) {
				if (isSelected) {
					this.setBackground(list.getSelectionBackground());
					this.setForeground(list.getSelectionForeground());
				} else {
					this.setBackground(MAIN_COLOR);
					this.setForeground(Color.WHITE);
				}

			} else if (usingRange.getType() == UsingObject.ADDITIONAL_TYPE) {
				if (isSelected) {
					this.setBackground(list.getSelectionBackground());
					this.setForeground(list.getSelectionForeground());
				} else {
					this.setBackground(ADDITIONAL_COLOR);
					this.setForeground(Color.WHITE);
				}
			}
		}
		setEnabled(list.isEnabled());
		setSelected(isSelected);
		setFont(list.getFont());
		this.setText(theText);
		return this;
	}

}