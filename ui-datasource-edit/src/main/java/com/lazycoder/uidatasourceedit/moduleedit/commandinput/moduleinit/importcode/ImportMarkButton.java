package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class ImportMarkButton extends MyButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -7421137548470769118L;

	// private importMarkElement markElement;

	// private int ordinal;
	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			setMarkName();
		}
	};

	// private void markElementInit() {
	// markElement = new importMarkElement();
	// markElement.setClassName(DataSourceEditHolder.className);
	// markElement.setModuleName(DataSourceEditHolder.moduleName);
	// markElement.setOrdinal(ordinal);
	// }

	public ImportMarkButton() {
		// TODO Auto-generated constructor stub
		super();

		// this.ordinal = ordinal;
		// markElementInit();

		setText("");
		Dimension dimension = new Dimension(100, 30);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.addActionListener(listener);
	}

	private void setMarkName() {
		// String[] temp = { this.markButton.getText() };
		// String theMarkName = (String) MyOptionPane.showInputDialog(null,
		// "请设置该引入代码的标记!", "引入标记",
		// JOptionPane.OK_CANCEL_OPTION, null, temp, temp[0]);
		String theMarkName = LazyCoderOptionPane.showInputDialog(null, "请设置该代码的标记!", "引入标记", JOptionPane.PLAIN_MESSAGE);
		if (theMarkName != null) {
			this.setText(theMarkName);
		}
	}
}
