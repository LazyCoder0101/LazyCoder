package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.ModuleInfo;
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
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.AbstractFormatControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import java.io.File;
import java.util.List;

public class ModuleControlPane extends AbstractFormatControlInputPane {

    /**
     *
     */
    private static final long serialVersionUID = 3524354097118468808L;

    private transient List<OptionDataModel> listTemp;

    public ModuleControlPane() {
        super();
        ControlCondition condition = new ControlCondition(true, true, true, false);
        menuInit(condition);
    }

    @Override
    protected void showAddCorrespondingChooseMenuItems() {
        addModuleEditCorrespondingChooseMenuItems();
    }


    /**
     * 查看是否有模块控制
     *
     * @return
     */
    public int getWhetherModuleControlIsRequired() {
        int returnResult = ModuleInfo.TRUE_;
        boolean flag = this.getUseState();
        if (flag == false) {
            returnResult = ModuleInfo.FALSE_;
        }
        return returnResult;
    }

    @Override
    protected List<OptionDataModel> getCorrespondingChooseMenuList() {
        listTemp = DataSourceEditHolder.currentModule == null ? null : SysService.OPTION_SERVICE.getCorrespondingOptionNameList(DataSourceEditHolder.currentModule.getModuleId());
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
            String name = GeneralControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

            FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
            functionAddParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            functionAddParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            functionAddParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            functionAddParam.setPaneType(MarkElementName.MODULE_CONTROL);

            FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
            temp.setPassingComponentParams(passingComponentParams);
            addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
            LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());
        }
    }


    @Override
    protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
        FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
        if (DataSourceEditHolder.currentModule != null) {
            functionAddParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            functionAddParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            functionAddParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            functionAddParam.setPaneType(MarkElementName.MODULE_CONTROL);
        }

        FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
        return functionAddControlLabel;
    }

    @Override
    protected void addInfrequentlyUsedSettingOpratingLabel() {
        if (DataSourceEditHolder.currentModule != null) {
            InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
            infrequentlyUsedSettingParam.setPaneType(MarkElementName.MODULE_CONTROL);
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
        infrequentlyUsedSettingParam.setPaneType(MarkElementName.MODULE_CONTROL);
        if (DataSourceEditHolder.currentModule != null) {
            infrequentlyUsedSettingParam.setClassName(DataSourceEditHolder.currentModule.getClassName());
            infrequentlyUsedSettingParam.setModuleName(DataSourceEditHolder.currentModule.getModuleName());
            infrequentlyUsedSettingParam.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        }

        InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
                controlElement, model, infrequentlyUsedSettingParam);
        return infrequentlyUsedSettingControlLabel;
    }

    @Override
    protected void clickAddContentChooseMenuItem() {
        // TODO Auto-generated method stub
//		super.clickAddContentChooseMenuItem();
        if (DataSourceEditHolder.currentModule != null) {
            new ContentChooseFrameAddForBaseControlTextFrame(DataSourceEditHolder.currentModule.getModuleId(), this);
        }
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

}
