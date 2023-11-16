package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code.ThisFileNameCodeLabel;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileNameEditFrame extends AbstractEditFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 8521958530758602828L;
	private JComboBox<String> suffixComboBox;
	// private PassingComponentParams passingComponentParams;
	private ThisFileNameCodeLabel controlLabel;

	private JLabel label1;
	private OperatingTipButton operatingTip;

	private FileNameEditFrame() {
		super();

		getContentPane().setLayout(null);
		ok.setBounds(45, 120, 80, 30);
		cancel.setBounds(157, 120, 80, 30);
		getContentPane().add(ok);
		getContentPane().add(cancel);

		operatingTip = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder(
						"懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "本文件名")
				.getAbsolutePath());
		operatingTip.setLocation(20, 10);
		getContentPane().add(operatingTip);

		label1 = new JLabel("有无后缀：");
		label1.setBounds(40, 55, 100, 30);
		getContentPane().add(label1);

		suffixComboBox = new JComboBox<String>();
		suffixComboBox.setModel(new DefaultComboBoxModel<String>(new String[]{"无后缀", "有后缀"}));
		suffixComboBox.setBounds(140, 55, 100, 30);
		getContentPane().add(suffixComboBox);
		cancel.addActionListener(cancelListener);
		this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 150, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 250, 300, 234);
	}

	// 更改自定义变量组件内容
	public FileNameEditFrame(ThisFileNameCodeLabel controlLabel) {
		this();
		this.setTitle("更改组件属性");
		this.controlLabel = controlLabel;
		this.setData(controlLabel);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ok();
			}
		});

		this.setVisible(true);
	}

//	public static void main(String[] args) {
//		new FileNameEditFrame();
//	}

	ActionListener cancelListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			FileNameEditFrame.this.dispose();
		}
	};

	@Override
	public void ok() {
		// TODO Auto-generated method stub
		setValue();
		FileNameEditFrame.this.dispose();
	}

	public void setData(ThisFileNameCodeLabel controlLabel) {
		this.suffixComboBox.setSelectedIndex(controlLabel.getHaveSuffixOrNot());
	}

	public void setValue() {
		controlLabel.setHaveSuffixOrNot(suffixComboBox.getSelectedIndex());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

}
