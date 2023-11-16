package com.lazycoder.uidatasourceedit.inputmeta.editcontainer;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.codecabinet.AdditionalFunctionCodeCabinet;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.OperatingPropertyCombobox;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.controlcabinet.AdditionalFunctionControlCabinet;
import com.lazycoder.uiutils.folder.MyContainerPane;
import com.lazycoder.uiutils.mycomponent.MyButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class AdditionalFunctionEditContainer extends AbstractEditContainer {

	/**
	 *
	 */
	private static final long serialVersionUID = -6075122457314700185L;

	/**
	 * 组件长度所在屏幕比例
	 */
	private static final double PROPORTION = 0.32;

	/**
	 * 组件放置面板
	 */
	private MyContainerPane componentPane;

	private MyButton addBt, delBt;

	private OperatingPropertyCombobox propertyCombobox;

	private int additionalSerialNumber = 0;

	public AdditionalFunctionEditContainer() {
		super(15);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 新建
	 *
	 * @param operatingOrdinalNumber
	 */
	public AdditionalFunctionEditContainer(int additionalSerialNumber, int operatingOrdinalNumber) {
		this();
		this.operatingOrdinalNumber = operatingOrdinalNumber;
		this.additionalSerialNumber = additionalSerialNumber;
		controlPaneInit();
		controlCabinet.paneStyteInit(true);
		codePaneInit();
		codeCabinet.addCodePane(theModel);

	}

	/**
	 * 还原
	 *
	 * @param metaModel
	 */
	public AdditionalFunctionEditContainer(AdditionalFunctionMetaModel metaModel) {
		this();
		this.operatingOrdinalNumber = metaModel.getOperatingModel().getOrdinal();
		this.additionalSerialNumber = metaModel.getOperatingModel().getAdditionalSerialNumber();
		controlPaneInit();
		controlCabinet.paneStyteInit(false);
		codePaneInit();
		theModel.reductionContent(metaModel.getOperatingModel());
		ArrayList<AdditionalFunctionCodeModel> codeList;
		if (metaModel.getCodeModelList() != null) {
			codeList = metaModel.getCodeModelList();
			((AdditionalFunctionCodeCabinet) codeCabinet).reductionContent(codeList, theModel);
		}
		propertyCombobox.setSelectedItem(FunctionUseProperty.getFunctionUsePropertyBy(metaModel.getOperatingModel().getSetProperty()));
		controlCabinet.reductionContent(metaModel.getOperatingModel());
		CommandCodeControl.updateCodePaneMenu(theModel);
	}

	/**
	 * 获取操作模型
	 *
	 * @return
	 */
	public AdditionalFunctionOperatingModel getOperatingModel() {
		AdditionalFunctionOperatingModel operatingModel = new AdditionalFunctionOperatingModel();
		operatingModel.setShowText(controlCabinet.getShowText());
		operatingModel.setDefaultControlStatementFormat(controlCabinet.getDefaultControlStatementFormat());
		operatingModel.setHiddenControlStatementFormat(controlCabinet.getHiddenControlStatementFormat());
		operatingModel.setHiddenState(controlCabinet.getHiddenState());
		operatingModel.setControlComponentCorrespondingInformation(
				AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(theModel));
		operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(theModel.getUseComponentNum()));
		operatingModel.setOrdinal(operatingOrdinalNumber);
		operatingModel.setAdditionalSerialNumber(this.additionalSerialNumber);
		operatingModel.setNoteListParam(controlCabinet.getNoteListParam());
		operatingModel.setSetProperty(propertyCombobox.getSelectedItem().getSysDictionaryValue());
		return operatingModel;
	}

	/**
	 * 获取代码模型
	 *
	 * @return
	 */
	public ArrayList<AdditionalFunctionCodeModel> getCodeModelList() {
		return ((AdditionalFunctionCodeCabinet) codeCabinet).getCodeModelList();
	}

	public void controlPaneInit() {
		controlCabinet = new AdditionalFunctionControlCabinet(
				additionalSerialNumber, theModel,
				this.operatingOrdinalNumber, true, PROPORTION){
			@Override
			public void restoreContainer() {
				AdditionalFunctionFeatureSelectionModel featureSelectionModel = new AdditionalFunctionFeatureSelectionModel();
				featureSelectionModel.setAdditionalSerialNumber(additionalSerialNumber);
				featureSelectionModel.setOrdinal(operatingOrdinalNumber);
				AdditionalFunctionMetaModel theMetaModel = SysService.ADDITIONAL_FUNCTION_SERVICE.getAdditionalMetaModel(featureSelectionModel);
				if (theMetaModel!=null){
					AdditionalFunctionEditContainer.this.clear();
					theModel.reductionContent(theMetaModel.getOperatingModel());
					ArrayList<AdditionalFunctionCodeModel> codeList;
					if (theMetaModel.getCodeModelList() != null) {
						codeList = theMetaModel.getCodeModelList();
						((AdditionalFunctionCodeCabinet) codeCabinet).reductionContent(codeList, theModel);
					}
					controlCabinet.reductionContent(theMetaModel.getOperatingModel());
					propertyCombobox.setSelectedItem(FunctionUseProperty.getFunctionUsePropertyBy(theMetaModel.getOperatingModel().getSetProperty()));
					CommandCodeControl.updateCodePaneMenu(theModel);

					Container parent = AdditionalFunctionEditContainer.this.getParent();
					if (parent!=null){
						parent.validate();
					}
				}
			}

			@Override
			public void clearContainer() {
				AdditionalFunctionEditContainer.this.clearAndBlankCodePane();
			}
		};
		addContainer(controlCabinet);
	}

	public void codePaneInit() {
		componentPane = new MyContainerPane();

		addBt = new MyButton("添加");
		addBt.addActionListener(listener);
		addBt.setBounds(10, 0, 80, 30);
		componentPane.add(addBt);
		delBt = new MyButton("删除");
		delBt.addActionListener(listener);
		delBt.setBounds(100, 0, 80, 30);
		componentPane.add(delBt);

		JLabel label = new JLabel("使用限制：");
		label.setBounds(190, 0, 70, 30);
		componentPane.add(label);
		propertyCombobox = OperatingPropertyCombobox.creatBusinessPropertyCombobox();
		propertyCombobox.setBounds(260, 0, 220, 30);
		componentPane.add(propertyCombobox);

		tabs.add(componentPane);
		this.add(componentPane);

		codeCabinet = new AdditionalFunctionCodeCabinet(additionalSerialNumber, theModel, this.operatingOrdinalNumber, true, PROPORTION);
		codeCabinet.setCodeJspHeight(PROPORTION, 130);
		addContainer(codeCabinet);
	}

	@Override
	public void clearAndBlankCodePane() {
		super.clearAndBlankCodePane();
		propertyCombobox.clearInit();
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

}
