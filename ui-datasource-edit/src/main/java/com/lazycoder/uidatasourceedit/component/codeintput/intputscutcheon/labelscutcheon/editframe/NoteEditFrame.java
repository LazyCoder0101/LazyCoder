package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.NoteControlLabel;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Font;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NoteEditFrame extends AbstractEditFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = -6140557140643489153L;

	private JScrollPane jsp;
	private JTextArea note;

	private JLabel nlabel;

	private JCheckBox asAUsageReminderCheckBox;

	private NoteControlLabel controlLabel;

	private OperatingTipButton operatingTip;

	public static void main(String[] args) {
		new NoteEditFrame().setVisible(true);
	}

	private NoteEditFrame() {
		super();
		this.setLayout(null);

		asAUsageReminderCheckBox = new JCheckBox("作为使用提醒");
		asAUsageReminderCheckBox.setBounds(20,15,140,30);
		this.add(asAUsageReminderCheckBox);

		operatingTip = new OperatingTipButton(
				SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "8.png").getAbsolutePath()
		);
		operatingTip.setLocation(asAUsageReminderCheckBox.getX() + asAUsageReminderCheckBox.getWidth() + 30, asAUsageReminderCheckBox.getY() + 3);
		getContentPane().add(operatingTip);

		nlabel = new JLabel("请输入注释：");
		nlabel.setBounds(20,50,140,30);
		this.add(nlabel);

		note = new JTextArea();
		note.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		note.setSize(300, 240);
		jsp = new JScrollPane(note);
		jsp.setBounds(20, 80, 500, 300);
		this.add(jsp);
		ok.setBounds(70, 410, 80, 30);
		cancel.setBounds(380, 410, 80, 30);
		this.add(ok);
		this.add(cancel);
		cancel.addActionListener(cancelListener);
		this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 300, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 270, 580, 500);

	}

	// 更改注释组件内容
	public NoteEditFrame(NoteControlLabel controlLabel) {
		this();
		this.controlLabel = controlLabel;
		this.setTitle("更改注释组件\""+this.controlLabel.getControl().getThisName()+"\"属性");

		this.note.setText(controlLabel.getNote());
		this.asAUsageReminderCheckBox.setSelected(controlLabel.isAsAUsageReminder());

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
//		new NoteEditFrame().setVisible(true);
//	}

	ActionListener cancelListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			NoteEditFrame.this.dispose();
		}
	};

	@Override
	public void ok() {
		// TODO Auto-generated method stub
		controlLabel.setNote(NoteEditFrame.this.note.getText());
		controlLabel.setAsAUsageReminder(asAUsageReminderCheckBox.isSelected());
		NoteEditFrame.this.dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

}
