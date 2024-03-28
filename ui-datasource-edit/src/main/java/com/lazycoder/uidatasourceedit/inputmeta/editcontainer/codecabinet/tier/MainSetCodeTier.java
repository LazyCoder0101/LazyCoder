package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.MainSetCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeUsePropetyMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CommandContainerCodesImportCodeShowButton;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu.MainSetPathChooseMenu;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public class MainSetCodeTier extends AbstractCodeTier {

	/**
	 *
	 */
	private static final long serialVersionUID = 7894568994742559124L;

	private MainSetPathChooseMenu pathChooseMenu;
	private JMenuBar menuBar;

	private JPanel panel;
	private JScrollPane scrollPane;

	private int typeSerialNumber;

	private CodeLabelCombobox codeLabelCombobox;

	private CodeUsePropetyMenu codeUsePropetyMenu;

	private CommandContainerCodesImportCodeShowButton commandContainerCodesImportCodeShowButton = null;

	public MainSetCodeTier(int typeSerialNumber, int operatingOrdinal, int codeOrdinal, ContainerModel model) {
		panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		this.codeOrdinal = codeOrdinal;
		this.typeSerialNumber = typeSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinal;

		codePane = new MainSetCodePane(codeOrdinal, model);

		model.getFunctionCodes().put(codeOrdinal, this);
		scrollPane = new JScrollPane(codePane);
		codePane.setUpdateScrollpane(scrollPane);
		panel.add(scrollPane, BorderLayout.CENTER);

		commandContainerCodesImportCodeShowButton = new CommandContainerCodesImportCodeShowButton();

		JLabel mLabel = new JLabel("*");
		mLabel.setForeground(Color.RED);
		JLabel wriLoclabel = new JLabel("填写位置：");
		wriLoclabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		menuBar = new JMenuBar();

		JLabel clLabel = new JLabel("代码标签：");
		clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codeLabelCombobox = new CodeLabelCombobox();
		codeLabelCombobox.addPopupMenuListener(popupMenuListener);

		pathChooseMenu = new MainSetPathChooseMenu(typeSerialNumber, operatingOrdinal, codeOrdinal);
		menuBar.add(pathChooseMenu);

		codeUsePropetyMenu = new CodeUsePropetyMenu();

		Box box = Box.createHorizontalBox();
		box.add(commandContainerCodesImportCodeShowButton);
		box.add(Box.createHorizontalStrut(10));
		box.add(mLabel);
		box.add(wriLoclabel);
		box.add(menuBar);
		box.add(Box.createHorizontalStrut(10));
		box.add(clLabel);
		box.add(codeLabelCombobox);
		box.add(Box.createHorizontalStrut(10));
		box.add(codeUsePropetyMenu);

		Box vbox = Box.createVerticalBox();
		vbox.add(box);
		vbox.add(Box.createVerticalStrut(10));

		panel.add(vbox, BorderLayout.SOUTH);
	}

	private PopupMenuListener popupMenuListener = new PopupMenuListener() {
		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			pathChooseMenu.getMarkElement().setCodeLabelId(codeLabelCombobox.getCodeLabelId());
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {}
	};

	@Override
	public void reductionContent(GeneralCodeModel codeModel) {
		// TODO Auto-generated method stub
		FormatTypeCodeModel theCodeModel = (FormatTypeCodeModel) codeModel;
		super.reductionContent(codeModel);
		pathChooseMenu.setSelectedPath(theCodeModel.getPathParam());
		codeLabelCombobox.setSelectedCodeLabel(((FormatTypeCodeModel) codeModel).getCodeLabelId());
		codeUsePropetyMenu.setCodeUsePropertyParam(theCodeModel.getCodeUsePropertyParam());
		commandContainerCodesImportCodeShowButton.setThisImport(theCodeModel.getImportCodeParam());
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (super.check() == false) {
			flag = false;
			LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   必填模板的\"设置" + typeSerialNumber + "\"里的第" + operatingOrdinalNumber
					+ "个方法里面的第" + codeOrdinal + "条代码没写内容，\n如果不需要请先删掉", "系统信息", JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (pathChooseMenu.check() == true) {
				if (pathChooseMenu.checkHaveAddTheMarkScutcheonOrNot() == false) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   必填模板的\"设置" + typeSerialNumber + "\"里的第" + operatingOrdinalNumber
							+ "个方法里面的第" + codeOrdinal + "条代码，\n你想设置写在对应代码文件哪里，请在对应地方添加标记", "系统信息", JOptionPane.PLAIN_MESSAGE);

				}
			} else {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   必填模板的\"设置" + typeSerialNumber + "\"里的第" + operatingOrdinalNumber
						+ "个方法里面的第" + codeOrdinal + "条代码还没选要写在哪里", "系统信息", JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
	}

	public MainSetPathChooseMenu getPathChoose() {
		return pathChooseMenu;
	}

	/**
	 * 获取路径参数
	 */
	@Override
	public String getPathParam() {
		// TODO Auto-generated method stub
		return pathChooseMenu.getPathParam();
	}

	@Override
	public FormatTypeCodeModel getCodeModel() {
		// TODO Auto-generated method stub
		FormatTypeCodeModel codeModel = FormatTypeCodeModel.creatMainSetCodeModel();
		codeModel.setCodeFormatParam(getCodeParam());
		codeModel.setPathParam(getPathParam());
		codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
		codeModel.setCodeOrdinal(codeOrdinal);
		codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
		codeModel.setImportCodeParam(commandContainerCodesImportCodeShowButton.getImportCodeParam());
		return codeModel;
	}

	@Override
	public void packUpCorrespondingImportCodePane() {
		if (commandContainerCodesImportCodeShowButton != null) {
			commandContainerCodesImportCodeShowButton.packUpPanel();
		}
	}

}
