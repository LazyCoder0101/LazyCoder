package com.lazycoder.uidatasourceedit.component.component.table.functionname;

import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.VariableUsageRangeCombobox;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FunctionUsingRangeFrame extends LazyCoderCommonDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -6742770490499189989L;

	private final JPanel contentPanel = new JPanel();

	private MyButton okButton, cancelButton;

	private VariableUsageRangeCombobox variableUsageRangeCombobox;


	/**
	 * Create the dialog.
	 */
	private FunctionUsingRangeFrame() {
		setTitle("方法使用范围");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel label = new JLabel("标签");
		label.setBounds(50, 50, 70, 30);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new MyButton("确定");
				okButton.setActionCommand("OK");
				okButton.addActionListener(listener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new MyButton("取消");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(listener);
				buttonPane.add(cancelButton);
			}
		}
		setBounds(SysUtil.SCREEN_SIZE.width/2-200, SysUtil.SCREEN_SIZE.height/2-120, 450, 240);
	}

	public FunctionUsingRangeFrame(VariableUsageRangeCombobox variableUsageRangeCombobox) {
		this();
		this.variableUsageRangeCombobox = variableUsageRangeCombobox;

		this.variableUsageRangeCombobox.setBounds(100, 50, 240, 30);
		contentPanel.add(variableUsageRangeCombobox);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == okButton) {
				ok();
			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		}
	};


	public void ok() {
		dispose();
	}

}
