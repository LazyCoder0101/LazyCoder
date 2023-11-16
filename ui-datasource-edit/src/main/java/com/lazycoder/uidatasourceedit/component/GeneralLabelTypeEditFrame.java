package com.lazycoder.uidatasourceedit.component;

import com.lazycoder.uiutils.mycomponent.GeneralOptionTextFieldEditPane;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class GeneralLabelTypeEditFrame extends LazyCoderCommonDialog {

	protected GeneralOptionTextFieldEditPane labelTypeEditPane;
	protected MyButton addLabelTyprBt, delLabelTyprBt, okButton, cancelButton;
	private JPanel contentPane;
	private JScrollPane scrollPane;


	/**
	 * Create the frame.
	 */
	public GeneralLabelTypeEditFrame() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		labelTypeEditPane = new GeneralOptionTextFieldEditPane();
		scrollPane = new JScrollPane(labelTypeEditPane);
		scrollPane.setBounds(220, 25, 140, 150);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("标签类型：");
		lblNewLabel.setBounds(35, 25, 100, 30);
		contentPane.add(lblNewLabel);

		addLabelTyprBt = new MyButton("添加标签类型");
		addLabelTyprBt.addActionListener(listener);
		addLabelTyprBt.setBounds(35, 70, 150, 30);
		contentPane.add(addLabelTyprBt);

		delLabelTyprBt = new MyButton("删除标签类型");
		delLabelTyprBt.addActionListener(listener);
		delLabelTyprBt.setBounds(35, 130, 150, 30);
		contentPane.add(delLabelTyprBt);

		okButton = new MyButton("确认");
		okButton.setBounds(60, 200, 80, 30);
		contentPane.add(okButton);

		cancelButton = new MyButton("取消");
		cancelButton.addActionListener(listener);
		cancelButton.setBounds(210, 200, 80, 30);
		contentPane.add(cancelButton);

		setBounds(SysUtil.SCREEN_SIZE.width / 2 - 220, SysUtil.SCREEN_SIZE.height / 2 - 150, 450, 300);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GeneralLabelTypeEditFrame.this.dispose();
			}
		});
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addLabelTyprBt) {
				labelTypeEditPane.addOptionTextField();
				scrollPane.updateUI();
				scrollPane.repaint();

			} else if (e.getSource() == delLabelTyprBt) {
				labelTypeEditPane.delOptionTextField();
				scrollPane.updateUI();
				scrollPane.repaint();

			} else if (e.getSource() == okButton) {

			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		}
	};

}
