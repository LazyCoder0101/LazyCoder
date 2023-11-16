package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.infrequentlyusedsetting;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.transferparam.FunctionAddParam;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractInfrequentlyUsedSettingControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.AbstractFormatControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.choose.ContentChooseFrameAddForBaseControlTextFrame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InfrequentlyUsedSettingControlUseControlPane extends AbstractInfrequentlyUsedSettingControlInputPane {

    /**
     * 该面板下面的方法要再分清哪个方法该怎么判断
     */
    private static final long serialVersionUID = 5207772137173859243L;

    /**
     * 判断1： 溯源，找出最开始的面板是什么类型的
     */
    protected InfrequentlyUsedSettingControl controlElement;
    /**
     * 判断2，查看放置该组件的面板，看看它是什么面板
     */

    private InfrequentlyUsedSettingParam infrequentlyUsedSettingParam;

    private transient List<OptionDataModel> listTemp;

    public InfrequentlyUsedSettingControlUseControlPane(InfrequentlyUsedSettingControl controlElement,
                                                        AbstractEditContainerModel model, InfrequentlyUsedSettingParam infrequentlyUsedSettingParam) {
        // TODO Auto-generated constructor stub
        super(model, controlElement.getThisName());
        this.controlElement = controlElement;
        this.infrequentlyUsedSettingParam = infrequentlyUsedSettingParam;

        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.getControlPaneElmentList(controlElement.getControlStatementFormat());
        reductionContent(elementList);
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
            new ContentChooseFrameAddForBaseControlTextFrame(infrequentlyUsedSettingParam.getAdditionalSerialNumber(), this);
        }
    }

    /**
     * 添加功能拓展组件（查看放置组件的面板是格式面板还是方法面板，再判断是用CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model,
     * temp.getControl());或者lannongFormatControl.addControlLabelAndUpdateCodePaneMenu(model,
     * temp.getControl());）,有两种情况，一个是在功能拓展组件里面有不常用组件，一个是在格式面板里面有不常用组件
     */
    @Override
    protected void addFunctionAddOpratingLabel() {
        String name = GeneralControl.generateComponentName(model, LabelElementName.FUNCTION_ADD);

        FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
        functionAddParam.setClassName(infrequentlyUsedSettingParam.getClassName());
        functionAddParam.setModuleName(infrequentlyUsedSettingParam.getModuleName());
        functionAddParam.setModuleId(infrequentlyUsedSettingParam.getModuleId());
        functionAddParam.setPaneType(getPaneType());
        functionAddParam.setAdditionalSerialNumber(infrequentlyUsedSettingParam.getAdditionalSerialNumber());

        FunctionAddControlLabel temp = new FunctionAddControlLabel(name, functionAddParam);
        temp.setPassingComponentParams(passingComponentParams);
        addCorrespondingComponentMethod(temp, name, LabelElementName.FUNCTION_ADD, delFunctionAddMenu);

        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());

            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, temp.getControl());
            }
        }
    }

    @Override
    protected FunctionAddControlLabel getFunctionAddControlLabel(FunctionAddControl controlElement) {
        FunctionAddParam functionAddParam = new FunctionAddParam();// 在使用过程中需要的参数
        functionAddParam.setClassName(infrequentlyUsedSettingParam.getClassName());
        functionAddParam.setModuleName(infrequentlyUsedSettingParam.getModuleName());
        functionAddParam.setModuleId(infrequentlyUsedSettingParam.getModuleId());
        functionAddParam.setPaneType(getPaneType());
        functionAddParam.setAdditionalSerialNumber(infrequentlyUsedSettingParam.getAdditionalSerialNumber());

        FunctionAddControlLabel functionAddControlLabel = new FunctionAddControlLabel(controlElement, functionAddParam);
        return functionAddControlLabel;
    }

    /**
     * 该面板无需使用此方法
     */
    @Override
    protected InfrequentlyUsedSettingControlLabel getInfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model) {
        return null;
    }

    /**
     * 获取图片放置路径（溯源，查出最初的面板是哪个，根据这个来判断路径）
     */
    @Override
    public File getImageRootPath() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        File file = null;
        if (MarkElementName.INIT_MARK.equals(getPaneType()) || MarkElementName.SET_MARK.equals(getPaneType())
                || MarkElementName.FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.MODULE_CONTROL.equals(getPaneType())) {

            file = DatabaseFileStructure.getModulePictureFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    DataSourceEditHolder.currentModule.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getMainPictureFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName());

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getAdditionalPictureFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    this.infrequentlyUsedSettingParam.getAdditionalSerialNumber());
        }
        return file;
    }

    @Override
    public File getFileSelectorRootPath() {
        File sysDataFileFolder = SysFileStructure.getDataFileFolder();
        File file = null;
        String paneType = getPaneType();
        if (MarkElementName.INIT_MARK.equals(paneType) || MarkElementName.SET_MARK.equals(paneType)
                || MarkElementName.FUNCTION_MARK.equals(paneType) || MarkElementName.MODULE_CONTROL.equals(paneType)) {

            file = DatabaseFileStructure.getModuleNeedFileFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    DataSourceEditHolder.currentModule.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(paneType)
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getMainNeedFileFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName());

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(paneType)
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(getPaneType())
                || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(getPaneType())) {
            file = DatabaseFileStructure.getAdditionalNeedFileFolder(sysDataFileFolder, GeneralHolder.currentUserDataSourceLabel.getDataSourceName(),
                    this.infrequentlyUsedSettingParam.getAdditionalSerialNumber());

        }
        return file;
    }

    @Override
    protected void showAddCorrespondingChooseMenuItems() {
        String paneType = getPaneType();
        if (MarkElementName.INIT_MARK.equals(paneType) || MarkElementName.SET_MARK.equals(paneType)
                || MarkElementName.FUNCTION_MARK.equals(paneType) || MarkElementName.MODULE_CONTROL.equals(paneType)) {
            addModuleEditCorrespondingChooseMenuItems();
        } else {
            super.showAddCorrespondingChooseMenuItems();
        }
    }


    /**
     * 获取选择菜单列表
     *
     * @return
     */
    @Override
    protected List<OptionDataModel> getCorrespondingChooseMenuList() {
        String paneType = getPaneType();
        if (MarkElementName.INIT_MARK.equals(paneType) || MarkElementName.SET_MARK.equals(paneType)
                || MarkElementName.FUNCTION_MARK.equals(paneType) || MarkElementName.MODULE_CONTROL.equals(paneType)) {

            listTemp = SysService.OPTION_SERVICE.getCorrespondingOptionNameList(DataSourceEditHolder.currentModule.getModuleId());

        } else if (MarkElementName.MAIN_FORMAT_MARK.equals(paneType)
                || MarkElementName.MAIN_SET_TYPE_MARK.equals(paneType)) {
            listTemp = SysService.OPTION_SERVICE.getMainOptionNameList();

        } else if (MarkElementName.ADDITIONAL_FORMAT_MARK.equals(paneType) || MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(paneType)
                || MarkElementName.ADDITIONAL_FUNCTION_MARK.equals(paneType)) {
            listTemp = SysService.OPTION_SERVICE
                    .getAdditionalOptionNameList(this.infrequentlyUsedSettingParam.getAdditionalSerialNumber());
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

    @Override
    protected void addInfrequentlyUsedSettingOpratingLabel(InfrequentlyUsedSettingControlLabel controlLabel) {
        // TODO Auto-generated method stub
    }

    /**
     * 添加组件并通知代码面板更新（拿到放置面板，不常用组件放的那个面板，调用它的addControlLabelAndUpdateCodePaneMenu方法）
     */
    @Override
    protected void addControlLabelAndUpdateCodePaneMenu(BaseLableElement addLabelModel) {
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, addLabelModel.controlComponentInformation());

            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, addLabelModel.controlComponentInformation());
            }
        }
    }

    /**
     * 添加组件（拿到放置面板，不常用组件放的那个面板，调用它的addControlLabel方法）
     */
    @Override
    protected void addControlLabel(BaseLableElement addLabelModel) {
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.addControlLabel(model, addLabelModel);
            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.addControlLabel(model, addLabelModel);
            }
        }
    }

    /**
     * 删除选择组件（拿到放置面板，不常用组件放的那个面板，调用它的delContentChoose方法）
     */
    @Override
    protected void delContentChoose(String optionName, int thisOptionId) {
        super.delContentChoose(optionName, thisOptionId);
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.delContentChoose(model, optionName, thisOptionId);

            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.delContentChoose(model, optionName, thisOptionId);
            }
        }
    }

    /**
     * 删除选择组件（拿到放置面板，不常用组件放的那个面板，调用它的delContentChoose方法）
     */
    @Override
    public void delContentChoose(String optionName) {
        super.delContentChoose(optionName);
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.delContentChoose(model, optionName);

            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.delContentChoose(model, optionName);
            }
        }
    }

    @Override
    protected void performServiceOperationsAfterClickAddContentChooseComponent(ContentChooseControl chooseControl) {
        super.performServiceOperationsAfterClickAddContentChooseComponent(chooseControl);
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.addControlLabelAndUpdateCodePaneMenu(model, chooseControl);

            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.addControlLabelAndUpdateCodePaneMenu(model, chooseControl);
            }
        }
    }

    public String getPaneType() {
        return infrequentlyUsedSettingParam.getPaneType();
    }

    /**
     * 移除该面板
     *
     * @param paneName
     */
    public void removeThisInfrequentlyUsedSettingPane(String paneName) {
        if (getModel() != null) {
            getModel().getInfrequentlyUsedControlList().remove(paneName);
        }
    }

    @Override
    protected void delControlLabel(AbstractEditContainerModel model, String delLabelType, String delName) {
        // TODO Auto-generated method stub
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();
        if (componentPutPane != null) {
            if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                CommandCodeControl.delControlLabel(model, delLabelType, delName);
            } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                LazyCoderFormatControl.delControlLabel(model, delLabelType, delName);
            }
        }
    }

}
