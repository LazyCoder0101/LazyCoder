package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.operation;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
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
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import java.io.File;
import java.util.List;


/**
 * 模块设置方法默认控制面板
 *
 * @author Administrator
 */
public class ModuleSetFunctionControlPanel extends AbstractFunctionControlInputPane {

    /**
     *
     */
    private static final long serialVersionUID = 8758659573345729729L;

    private transient List<OptionDataModel> listTemp;

    public ModuleSetFunctionControlPanel() {
        super();
        ControlCondition condition = new ControlCondition(true, true, true, false);
        menuInit(condition);
    }

    @Override
    protected void showAddCorrespondingChooseMenuItems() {
        addModuleEditCorrespondingChooseMenuItems();
    }


    @Override
    protected List<OptionDataModel> getCorrespondingChooseMenuList() {
        listTemp = DataSourceEditHolder.currentModule == null ?
                null : SysService.OPTION_SERVICE.getCorrespondingOptionNameList(DataSourceEditHolder.currentModule.getModuleId());
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
    protected void addFunctionAddOpratingLabel() {
        if (DataSourceEditHolder.currentModule != null) {
            String name = CommandCodeControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

            FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
            functionAddParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            functionAddParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            functionAddParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            functionAddParam.setPaneType(MarkElementName.SET_MARK);

            FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
            CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());
        }
    }

    @Override
    protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
        FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
        if (DataSourceEditHolder.currentModule != null) {
            functionAddParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            functionAddParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            functionAddParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            functionAddParam.setPaneType(MarkElementName.SET_MARK);
        }

        FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
        return functionAddControlLabel;
    }

    @Override
    protected void addInfrequentlyUsedSettingOpratingLabel() {
        if (DataSourceEditHolder.currentModule != null) {
            InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
            infrequentlyUsedSettingParam.setPaneType(MarkElementName.SET_MARK);
            infrequentlyUsedSettingParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            infrequentlyUsedSettingParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            infrequentlyUsedSettingParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());

            InfrequentlyUsedSettingControlLabel temp = new InfrequentlyUsedSettingControlLabel(model,
                    infrequentlyUsedSettingParam);
            super.addInfrequentlyUsedSettingOpratingLabel(temp);
        }
    }

    @Override
    protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
        InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
        if (DataSourceEditHolder.currentModule != null) {
            infrequentlyUsedSettingParam.setPaneType(MarkElementName.SET_MARK);
            infrequentlyUsedSettingParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            infrequentlyUsedSettingParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            infrequentlyUsedSettingParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        }

        InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
                controlElement, model, infrequentlyUsedSettingParam);
        return infrequentlyUsedSettingControlLabel;
    }

    @Override
    public File getImageRootPath() {
        File file = DataSourceEditHolder.currentModule == null ? null : DatabaseFileStructure.getModulePictureFolder(SysFileStructure.getDataFileFolder(),
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), DataSourceEditHolder.currentModule.getModuleId());
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File file = DataSourceEditHolder.currentModule == null ? null : DatabaseFileStructure.getModuleNeedFileFolder(SysFileStructure.getDataFileFolder(),
                GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), DataSourceEditHolder.currentModule.getModuleId());
        return file;
    }

    @Override
    protected void clickAddContentChooseMenuItem() {
        // TODO Auto-generated method stub
        if (DataSourceEditHolder.currentModule != null) {
            new ContentChooseFrameAddForBaseControlTextFrame(DataSourceEditHolder.currentModule.getModuleId(), this);
        }
    }

}
