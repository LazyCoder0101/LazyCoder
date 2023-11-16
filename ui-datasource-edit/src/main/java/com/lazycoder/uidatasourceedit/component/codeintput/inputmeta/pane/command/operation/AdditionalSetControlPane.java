package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import java.io.File;
import java.util.List;

public class AdditionalSetControlPane extends AbstractFunctionControlInputPane {

	/**
	 *
	 */
	private static final long serialVersionUID = -6492163981357374254L;

	private transient List<OptionDataModel> listTemp;

	/**
	 * 可选模板业务方法的标识编号
	 */
	private int additionalSerialNumber = 0;

	public AdditionalSetControlPane() {
		super();
		ControlCondition condition = new ControlCondition(true, true, true, false);
		menuInit(condition);
	}

	@Override
	protected List<OptionDataModel> getCorrespondingChooseMenuList() {
		listTemp = SysService.OPTION_SERVICE.getAdditionalOptionNameList(additionalSerialNumber);
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
		String name = CommandCodeControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.additionalSet.getClassName());
		functionAddParam.setModuleName(NotNamed.additionalSet.getModuleName());
		functionAddParam.setPaneType(MarkElementName.ADDITIONAL_SET_TYPE_MARK);
		functionAddParam.setAdditionalSerialNumber(additionalSerialNumber);

		FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
		temp.setPassingComponentParams(passingComponentParams);
		addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
		CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());
	}

	@Override
	protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.additionalSet.getClassName());
		functionAddParam.setModuleName(NotNamed.additionalSet.getModuleName());
		functionAddParam.setPaneType(MarkElementName.ADDITIONAL_SET_TYPE_MARK);
		functionAddParam.setAdditionalSerialNumber(additionalSerialNumber);

		FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
		return functionAddControlLabel;
	}

	@Override
	protected void addInfrequentlyUsedSettingOpratingLabel() {
		InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
		infrequentlyUsedSettingParam.setClassName(NotNamed.additionalSet.getClassName());
		infrequentlyUsedSettingParam.setModuleName(NotNamed.additionalSet.getModuleName());
		infrequentlyUsedSettingParam.setPaneType(MarkElementName.ADDITIONAL_SET_TYPE_MARK);
		infrequentlyUsedSettingParam.setAdditionalSerialNumber(additionalSerialNumber);

		InfrequentlyUsedSettingControlLabel temp = new InfrequentlyUsedSettingControlLabel(model,
				infrequentlyUsedSettingParam);
		super.addInfrequentlyUsedSettingOpratingLabel(temp);
	}

	@Override
	protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
		InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
		infrequentlyUsedSettingParam.setClassName(NotNamed.additionalSet.getClassName());
		infrequentlyUsedSettingParam.setModuleName(NotNamed.additionalSet.getModuleName());
		infrequentlyUsedSettingParam.setPaneType(MarkElementName.ADDITIONAL_SET_TYPE_MARK);
		infrequentlyUsedSettingParam.setAdditionalSerialNumber(additionalSerialNumber);

		InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
				controlElement, model, infrequentlyUsedSettingParam);
		return infrequentlyUsedSettingControlLabel;
	}

	@Override
	protected void clickAddContentChooseMenuItem() {
		// TODO Auto-generated method stub
		new ContentChooseFrameAddForBaseControlTextFrame(additionalSerialNumber, this);
	}

}
