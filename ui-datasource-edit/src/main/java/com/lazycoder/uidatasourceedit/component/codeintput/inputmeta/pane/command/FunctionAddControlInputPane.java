package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command;

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
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition.ControlCondition;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import java.io.File;
import java.util.List;

/**
 * 功能拓展组件的内部功能，其控制输入面板
 *
 * @author Administrator
 */
public class FunctionAddControlInputPane extends AbstractFunctionControlInputPane {

    /**
     *
     */
    private static final long serialVersionUID = 1028792673844354698L;

    private transient List<OptionDataModel> listTemp;

    private FunctionAddControl controlElement;

    private FunctionAddParam functionAddParam;

    public FunctionAddControlInputPane() {
        super();
        ControlCondition condition = new ControlCondition(false, false, false, false);
        menuInit(condition);
    }

    public void setParam(FunctionAddControl controlElement, FunctionAddParam functionAddParam) {
        this.controlElement = controlElement;
        this.functionAddParam = functionAddParam;
    }

    public String getPaneType() {
        return functionAddParam.getPaneType();
    }

    /**
     * 删除内容选择组件（）
     */
    @Override
    protected void delContentChoose(String optionName, int thisOptionId) {
        /**
         * 让模块通知其他代码面板删除组件
         */
        CommandCodeControl.delContentChoose(model, optionName, thisOptionId);
    }

    @Override
    protected void showAddCorrespondingChooseMenuItems() {
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {
            addModuleEditCorrespondingChooseMenuItems();
        } else {
            super.showAddCorrespondingChooseMenuItems();
        }
    }


    /**
     * 获取对应选择菜单列表（溯源）
     */
    @Override
    protected List<OptionDataModel> getCorrespondingChooseMenuList() {
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {

            listTemp = DataSourceEditHolder.currentModule == null ?
                    null : SysService.OPTION_SERVICE.getCorrespondingOptionNameList(DataSourceEditHolder.currentModule.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            listTemp = SysService.OPTION_SERVICE.getMainOptionNameList();

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())) {
            listTemp = SysService.OPTION_SERVICE.getAdditionalOptionNameList(functionAddParam.getAdditionalSerialNumber());
        }
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

    /**
     * 获取图片路径（溯源）
     */
    @Override
    public File getImageRootPath() {
        File file = null, sysDataFolder = SysFileStructure.getDataFileFolder();
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {

            file = DatabaseFileStructure.getModulePictureFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    functionAddParam.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getMainPictureFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName());

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getAdditionalPictureFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    functionAddParam.getAdditionalSerialNumber());
        }
        return file;
    }

    /**
     * 获取文件路径（溯源）
     */
    @Override
    public File getFileSelectorRootPath() {
        File file = null, sysDataFolder = SysFileStructure.getDataFileFolder();
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {

            file = DatabaseFileStructure.getModuleNeedFileFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    functionAddParam.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getMainNeedFileFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName());

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getAdditionalNeedFileFolder(sysDataFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    functionAddParam.getAdditionalSerialNumber());
        }
        return file;
    }

    @Override
    protected void clickAddContentChooseMenuItem() {
        // TODO Auto-generated method stub
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {
            if (DataSourceEditHolder.currentModule != null) {
                new ContentChooseFrameAddForBaseControlTextFrame(DataSourceEditHolder.currentModule.getModuleId(), this);
            }
        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            new ContentChooseFrameAddForBaseControlTextFrame(this);

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())) {
            new ContentChooseFrameAddForBaseControlTextFrame(functionAddParam.getAdditionalSerialNumber(), this);

        }
    }

    /**
     * 添加功能拓展组件（直接CommandCodeControl）
     */
    @Override
    protected void addFunctionAddOpratingLabel() {
        // TODO Auto-generated method stub
        String name = GeneralControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

        FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
        temp.setPassingComponentParams(passingComponentParams);
        addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);
        CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl().controlComponentInformation());
    }

    @Override
    protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
        FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
        return functionAddControlLabel;
    }

    /**
     * 生成不常用组件（直接CommandCodeControl）
     */
    @Override
    protected void addInfrequentlyUsedSettingOpratingLabel() {
        // TODO Auto-generated method stub
        InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
        infrequentlyUsedSettingParam.setPaneType(getPaneType());
        infrequentlyUsedSettingParam.setClassName(functionAddParam.getClassName());
        infrequentlyUsedSettingParam.setModuleName(functionAddParam.getModuleName());
        infrequentlyUsedSettingParam.setModuleId(functionAddParam.getModuleId());
        infrequentlyUsedSettingParam.setAdditionalSerialNumber(functionAddParam.getAdditionalSerialNumber());

        InfrequentlyUsedSettingControlLabel temp = new InfrequentlyUsedSettingControlLabel(model,
                infrequentlyUsedSettingParam);
        temp.setPassingComponentParams(passingComponentParams);
        addCorrespondingComponentMethod(temp, temp.getName(), LabelElementName.INFREQUENTLY_USED_SETTING,
                delInfrequentlyUsedSettingMenu);
        CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());
    }

    @Override
    protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
        InfrequentlyUsedSettingParam infrequentlyUsedSettingParam = new InfrequentlyUsedSettingParam();
        infrequentlyUsedSettingParam.setPaneType(getPaneType());
        infrequentlyUsedSettingParam.setClassName(functionAddParam.getClassName());
        infrequentlyUsedSettingParam.setModuleName(functionAddParam.getModuleName());
        infrequentlyUsedSettingParam.setModuleId(functionAddParam.getModuleId());
        infrequentlyUsedSettingParam.setAdditionalSerialNumber(functionAddParam.getAdditionalSerialNumber());

        InfrequentlyUsedSettingControlLabel infrequentlyUsedSettingControlLabel = new InfrequentlyUsedSettingControlLabel(
                controlElement, model, infrequentlyUsedSettingParam);
        return infrequentlyUsedSettingControlLabel;
    }

}
