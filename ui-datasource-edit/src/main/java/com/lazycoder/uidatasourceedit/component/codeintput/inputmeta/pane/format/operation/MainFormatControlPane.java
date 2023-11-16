package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.NotNamed;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.AbstractFormatControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import com.lazycoder.uidatasourceedit.formatedit.main.MainFormatEditPane;
import com.lazycoder.uidatasourceedit.formatedit.main.MainPaneInterface;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import java.io.File;
import java.util.List;

public class MainFormatControlPane extends AbstractFormatControlInputPane
		implements CheckInterface, MainPaneInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 8932950927901912993L;

	private transient List<OptionDataModel> listTemp;

	public MainFormatControlPane() {
		super();
		ControlCondition condition = new ControlCondition(true, true, true, false);
		menuInit(condition);
		//       addMenuListener(condition);
	}

	/**
	 * 获取必填模板操作模型
	 *
	 * @return
	 */
	public MainOperating getMainOpratingModel() {
		MainOperating mainOperating = new MainOperating();
		setOperatingModel(mainOperating);
		return mainOperating;
	}

	@Override
	protected List<OptionDataModel> getCorrespondingChooseMenuList() {
		listTemp = SysService.OPTION_SERVICE.getMainOptionNameList();
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
		File file = DatabaseFileStructure.getMainPictureFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
		return file;
	}

	@Override
	public File getFileSelectorRootPath() {
		File file = DatabaseFileStructure.getMainNeedFileFolder(SysFileStructure.getDataFileFolder(),
				GeneralHolder.currentUserDataSourceLabel.getDataSourceName());
		return file;
	}

	@Override
	protected void addFunctionAddOpratingLabel() {
		String name = GeneralControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.main.getClassName());
		functionAddParam.setModuleName(NotNamed.main.getModuleName());
		functionAddParam.setPaneType(MarkElementName.MAIN_FORMAT_MARK);

		FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
		temp.setPassingComponentParams(passingComponentParams);
		addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
		LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());

	}

	@Override
	protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
		FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
		functionAddParam.setClassName(NotNamed.main.getClassName());
		functionAddParam.setModuleName(NotNamed.main.getModuleName());
		functionAddParam.setPaneType(MarkElementName.MAIN_FORMAT_MARK);

		FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
		return functionAddControlLabel;
	}

	@Override
	protected void addInfrequentlyUsedSettingOpratingLabel() {
		InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
		infrequentlyUsedSettingParam.setClassName(NotNamed.main.getClassName());
		infrequentlyUsedSettingParam.setModuleName(NotNamed.main.getModuleName());
		infrequentlyUsedSettingParam.setPaneType(MarkElementName.MAIN_FORMAT_MARK);

		InfrequentlyUsedSettingControlLabel temp = new InfrequentlyUsedSettingControlLabel(model,
				infrequentlyUsedSettingParam);
		super.addInfrequentlyUsedSettingOpratingLabel(temp);
	}

	@Override
	protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
		InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
		infrequentlyUsedSettingParam.setPaneType(MarkElementName.MAIN_FORMAT_MARK);
		infrequentlyUsedSettingParam.setClassName(NotNamed.main.getClassName());
		infrequentlyUsedSettingParam.setModuleName(NotNamed.main.getModuleName());

		InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
				controlElement, model, infrequentlyUsedSettingParam);
		return infrequentlyUsedSettingControlLabel;
	}

	@Override
	protected void clickAddContentChooseMenuItem() {
		// TODO Auto-generated method stub
		new ContentChooseFrameAddForBaseControlTextFrame(this);
	}

	@Override
	public void displayMainContent(MainInfo mainInfo) {
		// TODO Auto-generated method stub
		MainOperating mainOperating = SysService.MAIN_FORMAT_CODE_FILE_SERVICE.getMainOperating();// 获取必填模板的操作模型
		LazyCoderFormatControl.buildingOriginalOperatingPane(FormatEditPaneHolder.mainFormatControlPane,
				MainFormatEditPane.formatModel, mainOperating);// 把操作模型参数写进格式模型

	}

	/**
	 * 获取必填模板格式录入数据操作层的模型
	 * @return
	 */
	public MainOperating getMainOperating(){
		MainOperating mainOperating = getMainOpratingModel();
		LazyCoderFormatControl.setOperating(mainOperating, MainFormatEditPane.formatModel);
		return mainOperating;
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

}
