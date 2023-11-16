package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalCodeFormatPutPane;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.AdditionalSetCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.AdditionalSetControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class AdditionalSetEditContainer extends AbstractEditContainer {

	/**
	 *
	 */
	private static final long serialVersionUID = 2039845659386800733L;

	/**
	 * 组件长度所在屏幕比例
	 */
	private static final double PROPORTION = 0.32;
	/**
	 * 组件放置面板
	 */
	private MyContainerPane componentPane;

	private MyButton addBt, delBt;

	private OperatingPropertyCombobox setPropertyCombobox;

	private AdditionalCodeFormatPutPane additionalCodeFormatPutPane;

	/**
	 * 设置序号
	 */
	private int typeSerialNumber = 0;

	private int additionalSerialNumber = 0;

	public AdditionalSetEditContainer() {
		super(15);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 新建
	 *
	 * @param additionalSerialNumber
	 * @param additionalCodeFormatPutPane
	 * @param typeSerialNumber
	 * @param operatingOrdinalNumber
	 */
	public AdditionalSetEditContainer(int additionalSerialNumber, AdditionalCodeFormatPutPane additionalCodeFormatPutPane, int typeSerialNumber,
									  int operatingOrdinalNumber) {
		this();
		this.additionalSerialNumber = additionalSerialNumber;
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
		this.typeSerialNumber = typeSerialNumber;
		this.operatingOrdinalNumber = operatingOrdinalNumber;
		controlPaneModuleSet();
		controlCabinet.paneStyteInit(true);
		codePaneModuleSet();
		addNewCodeCabinet();
	}

	/**
	 * 还原
	 *
	 * @param additionalSetMetaModel
	 * @param additionalCodeFormatPutPane
	 */
	public AdditionalSetEditContainer(AdditionalSetMetaModel additionalSetMetaModel, AdditionalCodeFormatPutPane additionalCodeFormatPutPane) {
		this();
		this.additionalCodeFormatPutPane = additionalCodeFormatPutPane;
		this.additionalSerialNumber = additionalSetMetaModel.getOperatingModel().getAdditionalSerialNumber();
		this.operatingOrdinalNumber = additionalSetMetaModel.getOperatingModel().getOrdinal();
		this.typeSerialNumber = additionalSetMetaModel.getOperatingModel().getTypeSerialNumber();

		controlPaneModuleSet();
		controlCabinet.paneStyteInit(false);
		codePaneModuleSet();
		theModel.reductionContent(additionalSetMetaModel.getOperatingModel());
		ArrayList<FormatTypeCodeModel> codeList;
		if (additionalSetMetaModel.getCodeModelList() != null) {
			codeList = additionalSetMetaModel.getCodeModelList();
			((AdditionalSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, this.operatingOrdinalNumber);
		}
		controlCabinet.reductionContent(additionalSetMetaModel.getOperatingModel());
		CommandCodeControl.updateCodePaneMenu(theModel);
		setPropertyCombobox.setSelectedItem(
				FunctionUseProperty.getFunctionUsePropertyBy(additionalSetMetaModel.getOperatingModel().getSetProperty()));
	}

	private void controlPaneModuleSet() {
		controlCabinet = new AdditionalSetControlCabinet(additionalSerialNumber, theModel, typeSerialNumber, this.operatingOrdinalNumber, true,
				PROPORTION){
			@Override
			public void clearContainer() {
				AdditionalSetEditContainer.this.clearAndBlankCodePane();
			}

			@Override
			public void restoreContainer() {
				FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getAdditionalFormatTypeFeatureSelectionModel();
				featureSelectionModel.setTypeSerialNumber(typeSerialNumber);
				featureSelectionModel.setOrdinal(operatingOrdinalNumber);
				AdditionalSetMetaModel theMetaModel = SysService.ADDITIONAL_SET_SERVICE.getAdditionalSetMetaModel(featureSelectionModel);
				if (theMetaModel != null) {
					AdditionalSetEditContainer.this.clear();
					theModel.reductionContent(theMetaModel.getOperatingModel());

					ArrayList<FormatTypeCodeModel> codeList;
					if (theMetaModel.getCodeModelList() != null) {
						codeList = theMetaModel.getCodeModelList();
						((AdditionalSetCodeCabinet) codeCabinet).reductionContent(codeList, theModel, operatingOrdinalNumber);
					}
					controlCabinet.reductionContent(theMetaModel.getOperatingModel());
					setPropertyCombobox.setSelectedItem(
							FunctionUseProperty.getFunctionUsePropertyBy(theMetaModel.getOperatingModel().getSetProperty()));
					CommandCodeControl.updateCodePaneMenu(theModel);

//					Container parent = AdditionalSetEditContainer.this.getParent();
//					if (parent!=null){
//						parent.validate();
//					}
				}
			}
		};
		addContainer(controlCabinet);
	}

	private void codePaneModuleSet() {
		componentPane = new MyContainerPane();

		JLabel label = new JLabel("使用限制：");
		label.setBounds(190, 0, 80, 30);
		componentPane.add(label);
		setPropertyCombobox = OperatingPropertyCombobox.creatSetPropertyCombobox();
		setPropertyCombobox.setBounds(260, 0, 200, 30);
		componentPane.add(setPropertyCombobox);

		addBt = new MyButton("添加");
		addBt.addActionListener(listener);
		addBt.setBounds(10, 0, 80, 30);
		componentPane.add(addBt);
		delBt = new MyButton("删除");
		delBt.addActionListener(listener);
		delBt.setBounds(100, 0, 80, 30);
		componentPane.add(delBt);

		tabs.add(componentPane);
		this.add(componentPane);

		codeCabinet = new AdditionalSetCodeCabinet(additionalSerialNumber, additionalCodeFormatPutPane, this.typeSerialNumber,
				this.operatingOrdinalNumber, true, PROPORTION);
		addContainer(codeCabinet);
	}

	@Override
	public void clearAndBlankCodePane() {
		super.clearAndBlankCodePane();
		setPropertyCombobox.clearInit();
	}

	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addBt) {
				addCodePane();
			} else if (e.getSource() == delBt) {
				delCodePane();
			}
		}
	};

	public void addNewCodeCabinet() {
		codeCabinet.addCodePane(theModel);
		CommandCodeControl.updateCodePaneMenu(theModel);
	}

	/**
	 * 获取操作模型
	 *
	 * @return
	 */
	public FormatTypeOperatingModel getOperatingModel() {
		FormatTypeOperatingModel operatingModel = FormatTypeOperatingModel.getAdditionalFormatTypeOperatingModel();
		operatingModel.setAdditionalSerialNumber(additionalSerialNumber);
		operatingModel.setTypeSerialNumber(this.typeSerialNumber);
		operatingModel.setOrdinal(operatingOrdinalNumber);
		operatingModel.setShowText(controlCabinet.getShowText());
		operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
		operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
		operatingModel.setHiddenState(controlCabinet.getHiddenState());
		operatingModel.setSetProperty(setPropertyCombobox.getSelectedItem().getSysDictionaryValue());
		operatingModel.setControlComponentCorrespondingInformation(
				AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
		operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
		operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
		return operatingModel;
	}

	/**
	 * 获取代码模型列表
	 *
	 * @return
	 */
	public ArrayList<FormatTypeCodeModel> getCodeModelList() {
		return ((AdditionalSetCodeCabinet) codeCabinet).getCodeModelList();
	}

	/**
	 * 获取代码总数
	 */
	public int getAdditionalSetCodeNum() {
		return ((AdditionalSetCodeCabinet) codeCabinet).getAdditionalSetCodeNum();
	}

}
