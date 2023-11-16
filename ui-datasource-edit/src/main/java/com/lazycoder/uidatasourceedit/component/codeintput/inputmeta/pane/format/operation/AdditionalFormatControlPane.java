package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.AbstractFormatControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import java.io.File;
import java.util.List;
import lombok.Getter;

public class AdditionalFormatControlPane extends AbstractFormatControlInputPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -5890799639415706438L;

	private transient List<OptionDataModel> listTemp;

	/**
	 * 可选模板的标识编号
	 */
	@Getter
	private int additionalSerialNumber = 0;

	protected AdditionalFormatControlPane() {
		super();
		ControlCondition condition = new ControlCondition(true, true, true, false);
		menuInit(condition);
	}

	public AdditionalFormatControlPane(int additionalSerialNumber) {
		this();
		this.additionalSerialNumber = additionalSerialNumber;
	}

	/**
	 * 还原
	 *
	 * @param additionalMetaModel
	 * @param formatModel
	 */
	public void restore(AdditionalMetaModel additionalMetaModel, FormatModel formatModel) {
		AdditionalOperating operating = additionalMetaModel.getOperatingModel();
		LazyCoderFormatControl.buildingOriginalOperatingPane(this, formatModel, operating);// 把操作模型参数写进格式模型
	}


	@Override
	protected List<OptionDataModel> getCorrespondingChooseMenuList() {
		listTemp = SysService.OPTION_SERVICE.getAdditionalOptionNameList(this.additionalSerialNumber);
		return listTemp;
	}

	@Override
	protected List<OptionDataModel> getUpdateChooseMenuList() {
		return listTemp;
	}

	@Override
	protected List<OptionDataModel> getDelCorrespondingChooseFromDBMenuList() {
		return listTemp;
	}

	@Override
	public File getImageRootPath() {
		File file = DatabaseFileStructure.getAdditionalPictureFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalSerialNumber);
		return file;
	}

	@Override
	public File getFileSelectorRootPath() {
		File file = DatabaseFileStructure.getAdditionalNeedFileFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), additionalSerialNumber);
		return file;
	}

	@Override
	protected void addFunctionAddOpratingLabel() {
		String name = GeneralControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.additional.getClassName());
		functionAddParam.setModuleName(NotNamed.additional.getModuleName());
		functionAddParam.setPaneType(MarkElementName.ADDITIONAL_FORMAT_MARK);

		FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
		temp.setPassingComponentParams(passingComponentParams);
		addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
		LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());
	}

	@Override
	protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.additional.getClassName());
		functionAddParam.setModuleName(NotNamed.additional.getModuleName());
		functionAddParam.setPaneType(MarkElementName.ADDITIONAL_FORMAT_MARK);

		FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
		return functionAddControlLabel;
	}

	@Override
	protected void addInfrequentlyUsedSettingOpratingLabel() {
		if (("".equals(NotNamed.additional.getClassName()) || "".equals(NotNamed.additional.getModuleName())) == false) {
			InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
			infrequentlyUsedSettingParam.setClassName(NotNamed.additional.getClassName());
			infrequentlyUsedSettingParam.setModuleName(NotNamed.additional.getModuleName());
			infrequentlyUsedSettingParam.setPaneType(MarkElementName.ADDITIONAL_FORMAT_MARK);
			infrequentlyUsedSettingParam.setAdditionalSerialNumber(additionalSerialNumber);

			InfrequentlyUsedSettingControlLabel temp = new InfrequentlyUsedSettingControlLabel(model,
					infrequentlyUsedSettingParam);
			super.addInfrequentlyUsedSettingOpratingLabel(temp);
		}
	}

	@Override
	protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
		InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
		infrequentlyUsedSettingParam.setPaneType(MarkElementName.ADDITIONAL_FORMAT_MARK);
		infrequentlyUsedSettingParam.setClassName(NotNamed.additional.getClassName());
		infrequentlyUsedSettingParam.setModuleName(NotNamed.additional.getModuleName());

		InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
				controlElement, model, infrequentlyUsedSettingParam);
		return infrequentlyUsedSettingControlLabel;
	}

	@Override
	protected void clickAddContentChooseMenuItem() {
		new ContentChooseFrameAddForBaseControlTextFrame(additionalSerialNumber, this);
	}

}
