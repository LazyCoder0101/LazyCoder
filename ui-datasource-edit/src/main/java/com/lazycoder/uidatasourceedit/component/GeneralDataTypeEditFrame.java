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

public class GeneralDataTypeEditFrame extends LazyCoderCommonDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -6423745215463652240L;
	protected GeneralOptionTextFieldEditPane dataTypeEditPane;
	protected MyButton addDataTyprBt, delDataTyprBt, okButton, cancelButton;
	private JPanel contentPane;
	private JScrollPane scrollPane;


	/**
	 * Create the frame.
	 */
	public GeneralDataTypeEditFrame() {
		// setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		dataTypeEditPane = new GeneralOptionTextFieldEditPane();
		scrollPane = new JScrollPane(dataTypeEditPane);
		scrollPane.setBounds(220, 25, 140, 150);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("数据类型：");
		lblNewLabel.setBounds(35, 25, 100, 30);
		contentPane.add(lblNewLabel);

		addDataTyprBt = new MyButton("添加数据类型");
		addDataTyprBt.addActionListener(listener);
		addDataTyprBt.setBounds(35, 70, 150, 30);
		contentPane.add(addDataTyprBt);

		delDataTyprBt = new MyButton("删除数据类型");
		delDataTyprBt.addActionListener(listener);
		delDataTyprBt.setBounds(35, 130, 150, 30);
		contentPane.add(delDataTyprBt);

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
				GeneralDataTypeEditFrame.this.dispose();
			}
		});
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GeneralDataTypeEditFrame frame = new GeneralDataTypeEditFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addDataTyprBt) {
				dataTypeEditPane.addOptionTextField();
				scrollPane.updateUI();
				scrollPane.repaint();

			} else if (e.getSource() == delDataTyprBt) {
				dataTypeEditPane.delOptionTextField();
				scrollPane.updateUI();
				scrollPane.repaint();

			} else if (e.getSource() == okButton) {

			} else if (e.getSource() == cancelButton) {
				dispose();
			}
		}
	};

}
