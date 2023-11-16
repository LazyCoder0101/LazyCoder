package com.lazycoder.uidatasourceedit.component;

import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class ProgramingLanguageCombobox extends JComboBox<ProgramingLanguage> {

	/**
	 *
	 */
	private static final long serialVersionUID = -3129267598644722358L;

	@SuppressWarnings("unchecked")
	public ProgramingLanguageCombobox() {
		// TODO Auto-generated constructor stub
		setRenderer(new ProgramingLanguageCellRenderer());
		DefaultComboBoxModel<ProgramingLanguage> comboBoxModel = new DefaultComboBoxModel<ProgramingLanguage>();
		comboBoxModel.addElement(ProgramingLanguage.C);
		comboBoxModel.addElement(ProgramingLanguage.JAVA);
		comboBoxModel.addElement(ProgramingLanguage.ASM);
		comboBoxModel.addElement(ProgramingLanguage.CSHARP);
		comboBoxModel.addElement(ProgramingLanguage.PHP);
		comboBoxModel.addElement(ProgramingLanguage.PYTHON);
		comboBoxModel.addElement(ProgramingLanguage.JAVASCRIPT);
		comboBoxModel.addElement(ProgramingLanguage.JSP);
		comboBoxModel.addElement(ProgramingLanguage.HTML);
		comboBoxModel.addElement(ProgramingLanguage.CSS);
		comboBoxModel.addElement(ProgramingLanguage.PERL);
		comboBoxModel.addElement(ProgramingLanguage.RUBY);
		comboBoxModel.addElement(ProgramingLanguage.VB);
		comboBoxModel.addElement(ProgramingLanguage.VERILOG);

		setModel(comboBoxModel);
		setSelectedIndex(0);

	}

	@Override
	public ProgramingLanguage getSelectedItem() {
		// TODO Auto-generated method stub
		return (ProgramingLanguage) super.getSelectedItem();
	}

	public void setSelectedItem(ProgramingLanguage programingLanguage) {
		// TODO Auto-generated method stub
		super.setSelectedItem(programingLanguage);
	}

}

@SuppressWarnings("rawtypes")
class ProgramingLanguageCellRenderer implements ListCellRenderer {

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	JLabel renderer;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
												  boolean cellHasFocus) {

		renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		String theText = null;
		if (value instanceof ProgramingLanguage) {
			ProgramingLanguage valueTemp = (ProgramingLanguage) value;
			theText = valueTemp.getLanguageName();

		}
		renderer.setText(theText);
		return renderer;
	}

}
