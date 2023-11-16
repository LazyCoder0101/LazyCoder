package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose;

import javax.swing.JTextField;
import java.awt.Dimension;
import java.util.ArrayList;

public class OptionValueTextFieldEditPane extends AbstractOptionTextFieldEditPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -1172404546962360500L;

	public static final int THIS_PANE_HIGHT = 30;

	public OptionValueTextFieldEditPane() {
		// TODO Auto-generated constructor stub
		textFieldHeight = THIS_PANE_HIGHT;
	}

	@Override
	protected void addOptionTextField() {
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, textFieldHeight));
		add(textField);
	}

	/**
	 * 添加一个选项值
	 */
	public void addOptionValue() {
		addOptionTextField();
	}

	protected void addAline(int num) {
		for (int i = 0; i < num; i++) {
			addOptionValue();
		}
	}

	/**
	 * 还原
	 *
	 * @param nameList
	 */
	public void recover(ArrayList<String> nameList) {
		JTextField textField;
		for (int i = 0; i < nameList.size(); i++) {
			textField = new JTextField();
			textField.setText(nameList.get(i));
			add(textField);
		}
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;//考虑到可能有特殊情况，有的选项不用填内容，直接返回true
//		JTextField temp;
//		for (int i = 0; i < getComponentCount(); i++) {
//			temp = (JTextField) getComponent(i);
//			if ("".equals(temp.getText().trim())) {
//				flag = false;
//				MyOptionPane.showMessageDialog(null, "亲，选项" + (i + 1) + "那里有选项还没填哦。  (*^▽^*)");
//				break;
//			}
//		}
		return flag;
	}

	/**
	 * 获取内容列表
	 *
	 * @return
	 */
	public ArrayList<String> getContentList() {
		ArrayList<String> list = new ArrayList<>();
		JTextField temp;
		for (int i = 0; i < getComponentCount(); i++) {
			temp = (JTextField) getComponent(i);
			list.add(temp.getText());
		}
		return list;
	}

}
