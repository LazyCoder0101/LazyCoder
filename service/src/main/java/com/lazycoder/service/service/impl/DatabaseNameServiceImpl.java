package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleControl;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.utils.UUIDUtil;
import org.springframework.stereotype.Service;

@Service
public class DatabaseNameServiceImpl {

    /**
     * 添加模块
     *
     * @param className
     * @param moduleName
     * @param ordinal
     * @param note
     */
    @TransactionMulti
    public void addModule(String className, String moduleName, int ordinal, String note) {
        String moduleId = UUIDUtil.getUUID();

        Module module = new Module();
        module.setModuleId(moduleId);
        module.setClassName(className);
        module.setModuleName(moduleName);
        module.setIndexParam(ordinal);
        module.setNote(note);
        SysService.MODULE_SERVICE.addModule(module);

        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setModuleId(moduleId);
        moduleInfo.setClassName(className);
        moduleInfo.setModuleName(moduleName);
        SysService.MODULE_INFO_SERVICE.addModuleInfo(moduleInfo);

        ModuleControl moduleControl = new ModuleControl();

        FormatModel formatModel = new FormatModel();
        moduleControl.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(formatModel));
        moduleControl.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(formatModel.getUseComponentNum()));

        moduleControl.setModuleId(moduleId);
        SysService.MODULE_CONTROL_SERVICE.addModuleControl(moduleControl);

        DatabaseFrameFileMethod.generateOrCheckModuleFileStrucure(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), moduleId);
    }

    /**
     * 更改（重命名）分类
     */
    @TransactionMulti
    public void updateClassification(TheClassification classification, String oldClassName) {
        String newClassName = classification.getClassName();
        SysService.CLASSIFICATION_SERVICE.updateClassification(classification, oldClassName);
        if (newClassName.equals(oldClassName) == false) {
            SysService.MODULE_SERVICE.renameClassName(newClassName, oldClassName);
            SysService.MODULE_INFO_SERVICE.renameClassName(newClassName, oldClassName);
        }
    }

    /**
     * 更改模块
     */
    @TransactionMulti
    public void updateModule(Module module, String oldModuleName, String oldClassName) {
        String className = module.getClassName(), newModuleName = module.getModuleName();
        SysService.MODULE_SERVICE.updateModule(module);
        if (newModuleName.equals(oldModuleName) == false || className.equals(oldClassName) == false) {
            SysService.MODULE_INFO_SERVICE.renameModuleName(module.getModuleId(), className, newModuleName);
        }
    }

    /**
     * 删除模块
     */
    @TransactionMulti
    public void delDataOfModule(String className, String moduleName, String moduleId) {
        SysService.MODULE_SERVICE.delModule(className, moduleName);
        SysService.MODULE_INFO_SERVICE.delDataOfModule(className, moduleName);
        SysService.OPTION_SERVICE.delDataOfModule(moduleId);
        SysService.ATTACHED_FILE_SERVICE.delDataOfModule(moduleId);
        SysService.VARIABLE_DATA_SERVICE.delDataOfModule(moduleId);
        SysService.FUNCTION_NAME_DATA_SERVICE.delDataOfModule(moduleId);
        SysService.IMPORT_CODE_SERVICE.delDataOfModule(moduleId);
        SysService.MODULE_CONTROL_SERVICE.delDataOfModule(moduleId);
        SysService.MODULE_FILE_FORMAT_SERVICE.delDataOfModule(moduleId);

        SysService.INIT_SERVICE.delDataOfModule(moduleId);
        SysService.MODULE_SET_SERVICE.delDataOfModule(moduleId);
        SysService.FUNCTION_SERVICE.delDataOfModule(moduleId);

        DatabaseFrameFileMethod.deleteModule(GeneralHolder.currentUserDataSourceLabel.getDataSourceName(), moduleId);
    }




}
