package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.tier;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.code.AdditionalSetCodePane;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeLabelCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.CodeUsePropetyMenu;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu.AdditionalSetPathChooseMenu;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class AdditionalSetCodeTier extends AbstractCodeTier {

	/**
	 *
	 */
	private static final long serialVersionUID = -84602448743970070L;

	private AdditionalSetPathChooseMenu pathChooseMenu;
	private JMenuBar menuBar;

	private JPanel panel;

	private JScrollPane scrollPane;

	private CodeUsePropetyMenu codeUsePropetyMenu;

	private int additionalSerialNumber = 0;

	private int typeSerialNumber;

	private CodeLabelCombobox codeLabelCombobox;

	public AdditionalSetCodeTier(int additionalSerialNumber, AdditionalCodeFormatPutPane additionalCodeFormatPutPane, int typeSerialNumber,
								 int operatingOrdinalNumber, int codeOrdinal, ContainerModel model) {
		panel = new JPanel();
		setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		this.additionalSerialNumber = additionalSerialNumber;
		this.typeSerialNumber = typeSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinalNumber;
		this.codeOrdinal = codeOrdinal;

		codePane = new AdditionalSetCodePane(codeOrdinal, model);

		model.getFunctionCodes().put(codeOrdinal, this);
		scrollPane = new JScrollPane(codePane);
		codePane.setUpdateScrollpane(scrollPane);
		panel.add(scrollPane, BorderLayout.CENTER);

		JLabel mLabel = new JLabel("*");
		mLabel.setForeground(Color.RED);
		JLabel wriLoclabel = new JLabel("填写位置：");
		wriLoclabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		menuBar = new JMenuBar();

		JLabel clLabel = new JLabel("代码标签：");
		clLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		codeLabelCombobox = new CodeLabelCombobox();
        codeLabelCombobox.addPopupMenuListener(popupMenuListener);

		AdditionalSetMarkElement markElement = new AdditionalSetMarkElement();
		markElement.setClassificationSerial(typeSerialNumber);
		markElement.setOperatingSerialNumber(operatingOrdinalNumber);
		markElement.setCodeNumber(codeOrdinal);
		pathChooseMenu = new AdditionalSetPathChooseMenu(additionalCodeFormatPutPane, markElement);
		menuBar.add(pathChooseMenu);

		codeUsePropetyMenu = new CodeUsePropetyMenu();

		Box box = Box.createHorizontalBox();
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
		codeLabelCombobox.setSelectedCodeLabel(theCodeModel.getCodeLabelId());
		codeUsePropetyMenu.setCodeUsePropertyParam(theCodeModel.getCodeUsePropertyParam());
	}

	public AdditionalSetPathChooseMenu getPathChoose() {
		return pathChooseMenu;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		boolean flag = true;
		String typeName = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeName(additionalSerialNumber),
				theSetTypeName = FormatEditPaneHolder.additionalEditPane.getTheAdditionalSetTypeName(additionalSerialNumber,
						typeSerialNumber);
		if (super.check() == false) {
			flag = false;
			JOptionPane
					.showMessageDialog(null,
							"o(´^｀)o   其他\"" + typeName + "\"分类中，\"" + theSetTypeName + "\"面板里第" + operatingOrdinalNumber
									+ "个方法里面的第" + codeOrdinal + "条代码没写内容，\n如果不需要请先删掉",
							"系统信息", JOptionPane.PLAIN_MESSAGE);
		}
		if (flag == true) {
			if (pathChooseMenu.check() == true) {
				if (pathChooseMenu.checkHaveAddTheMarkScutcheonOrNot() == false) {
					flag = false;
					LazyCoderOptionPane.showMessageDialog(
							null, "o(´^｀)o   其他\"" + typeName + "\"分类中，\"" + theSetTypeName + "\"面板里第"
									+ operatingOrdinalNumber + "个方法里面的第" + codeOrdinal + "条代码，\n你想设置写在对应代码文件哪里，请在对应地方添加标记",
							"系统信息", JOptionPane.PLAIN_MESSAGE);
				}
			} else {
				flag = false;
				LazyCoderOptionPane.showMessageDialog(
						null, "o(´^｀)o   其他\"" + typeName + "\"分类中，\"" + theSetTypeName + "\"面板里第"
								+ operatingOrdinalNumber + "个方法里面的第" + codeOrdinal + "条代码还没选要写在哪里",
						"系统信息", JOptionPane.PLAIN_MESSAGE);
			}
		}
		return flag;
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
		FormatTypeCodeModel codeModel = FormatTypeCodeModel.creatAdditionalSetCodeModel();
		codeModel.setCodeFormatParam(getCodeParam());
		codeModel.setPathParam(getPathParam());
		codeModel.setCodeOrdinal(codeOrdinal);
		codeModel.setCodeLabelId(codeLabelCombobox.getCodeLabelId());
		codeModel.setCodeUsePropertyParam(codeUsePropetyMenu.getCodeUsePropetyParam());
		return codeModel;
	}

}
