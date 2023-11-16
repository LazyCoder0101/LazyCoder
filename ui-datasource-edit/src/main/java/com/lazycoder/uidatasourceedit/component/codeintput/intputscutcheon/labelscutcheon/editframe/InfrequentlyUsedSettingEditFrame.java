package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.infrequentlyusedsetting.InfrequentlyUsedSettingControlUseControlPane;

import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InfrequentlyUsedSettingEditFrame extends AbstractEditFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -5284704199927788130L;

	private JPanel contentPane;

	private InfrequentlyUsedSettingControlLabel controlLabel;
	private InfrequentlyUsedSettingControlUseControlPane pane;
	private JScrollPane jsp;
	private OperatingTipButton operatingTip;


	/**
	 * 测试用的构造函数
	 */
	private InfrequentlyUsedSettingEditFrame() {
		super();
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		operatingTip = new OperatingTipButton(
				SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "13.png").getAbsolutePath()
		);
		operatingTip.setLocation(10, 10);
		getContentPane().add(operatingTip);

		jsp = new JScrollPane();
		jsp.setBounds(37, 28, 525, 289);
		contentPane.add(jsp);
		ok.setBounds(148, 341, 80, 30);
		cancel.setBounds(333, 341, 80, 30);
		contentPane.add(ok);
		contentPane.add(cancel);
		ok.addActionListener(okListener);
		cancel.addActionListener(cancelListener);
		this.setBounds((int) SysUtil.SCREEN_SIZE.getWidth() / 2 - 350, (int) SysUtil.SCREEN_SIZE.getHeight() / 2 - 300, 618, 430);

	}

	public InfrequentlyUsedSettingEditFrame(InfrequentlyUsedSettingControlLabel controlLabel, AbstractEditContainerModel model,
											InfrequentlyUsedSettingParam infrequentlyUsedSettingParam) {
		this();
		this.controlLabel = controlLabel;

		this.setTitle("更改不常用组件\""+this.controlLabel.getControl().getThisName()+"\"属性");

		infrequentlyUsedSettingParam.setThisPane(controlLabel.getPassingComponentParams().getThisPane());
		pane = new InfrequentlyUsedSettingControlUseControlPane(controlLabel.getControl(), model,
				infrequentlyUsedSettingParam);
		pane.setUpdateScrollpane(jsp);
		jsp.setViewportView(pane);

		setVisible(true);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	public void setValue() {
		try {
			controlLabel.setControlStatementFormat(pane.getControlStatementFormat());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除组件
	 */
	public void deleteComponent() {
		pane.deleteAllComponents();
	}

	@Override
	public void ok() {
		// TODO Auto-generated method stub
		setValue();
		InfrequentlyUsedSettingEditFrame.this.dispose();
	}

	private ActionListener okListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			ok();
		}
	};

	private ActionListener cancelListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			InfrequentlyUsedSettingEditFrame.this.dispose();
		}
	};

}
