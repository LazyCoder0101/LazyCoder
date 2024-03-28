package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.AdditionalFunctionCodePane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeUsePropetyMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CommandContainerCodesImportCodeShowButton;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import java.awt.*;

public class AdditionalFunctionCodeTier extends AbstractCodeTier {

	/**
	 *
	 */
	private static final long serialVersionUID = -6801599532639031043L;

	private CodeLabelCombobox codeLabelCombobox;

	private JPanel panel;

	private JScrollPane scrollPane;

	private CodeUsePropetyMenu codeUsePropetyMenu;

	private int additionalSerialNumber = 0;

	private CommandContainerCodesImportCodeShowButton commandContainerCodesImportCodeShowButton = null;

	public AdditionalFunctionCodeTier(int additionalSerialNumber, ContainerModel model, int operatingOrdinal, int codeOrdinal) {
		this.additionalSerialNumber = additionalSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinal;
		this.codeOrdinal = codeOrdinal;

		panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		codePane = new AdditionalFunctionCodePane(codeOrdinal, model);
		model.getFunctionCodes().put(codeOrdinal, this);
		scrollPane = new JScrollPane(codePane);
		codePane.setUpdateScrollpane(scrollPane);
		panel.add(scrollPane, BorderLayout.CENTER);

		commandContainerCodesImportCodeShowButton = new CommandContainerCodesImportCodeShowButton();

		JLabel clLabel = new JLabel("代码标签：");
		clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codeLabelCombobox = new CodeLabelCombobox();
//		codeLabelCombobox.setToolTipText("该代码会随着用户选择，添加到对应模板符合条件的\"功能\"标签位置或者功能组件中符合条件的位置，\n（没有符合的将无法写入）");

		codeUsePropetyMenu = new CodeUsePropetyMenu();

		Box box = Box.createHorizontalBox();
		box.add(commandContainerCodesImportCodeShowButton);
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


	@Override
	public void reductionContent(GeneralCodeModel codeModel) {
		super.reductionContent(codeModel);
		AdditionalFunctionCodeModel additionalFunctionCodeModel = (AdditionalFunctionCodeModel) codeModel;
		codeLabelCombobox.setSelectedCodeLabel(additionalFunctionCodeModel.getCodeLabelId());
		codeUsePropetyMenu.setCodeUsePropertyParam(additionalFunctionCodeModel.getCodeUsePropertyParam());
		commandContainerCodesImportCodeShowButton.setThisImport(additionalFunctionCodeModel.getImportCodeParam());
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (super.check() == false) {
			flag = false;
			String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber);
			LazyCoderOptionPane.showMessageDialog(null,
					"o(´^｀)o   其他\"" + typeName + "\"分类中，第" + operatingOrdinalNumber + "个可选模板功能没写代码内容", "系统信息",
					JOptionPane.PLAIN_MESSAGE);
		}
		return flag;
	}

	@Override
	public AdditionalFunctionCodeModel getCodeModel() {
		// TODO Auto-generated method stub
		AdditionalFunctionCodeModel codeModel = new AdditionalFunctionCodeModel();
		codeModel.setCodeFormatParam(getCodeParam());
		codeModel.setCodeOrdinal(codeOrdinal);
		codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
		codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
		codeModel.setImportCodeParam(commandContainerCodesImportCodeShowButton.getImportCodeParam());
		return codeModel;
	}

	@Override
	public String getPathParam() {
		return "";
	}

	@Override
	public void packUpCorrespondingImportCodePane() {
		if (commandContainerCodesImportCodeShowButton != null) {
			commandContainerCodesImportCodeShowButton.packUpPanel();
		}
	}

}