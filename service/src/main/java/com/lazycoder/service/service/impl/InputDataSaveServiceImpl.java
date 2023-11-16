package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.AttachedFile;
import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleControl;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.MainMetaModel;
import com.lazycoder.service.vo.save.AdditionalFunctionCodeInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionNameInputData;
import com.lazycoder.service.vo.save.AdditionalFunctionOperatingInputData;
import com.lazycoder.service.vo.save.AdditionalVariableInputData;
import com.lazycoder.service.vo.save.InitInputData;
import com.lazycoder.service.vo.save.ModuleFunctionInputData;
import com.lazycoder.service.vo.save.ModuleSetInputData;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 数据保存服务
 */
@Service
@Component(value = "InputDataSaveServiceImpl")
public class InputDataSaveServiceImpl {

    /**
     * 保存必填模板的录入数据
     *
     * @param mainInfo                  必填模板信息
     * @param mainMetaModel             必填模板格式和相关代码的元模型
     * @param mainVariableDataList      录入的所有必填模板变量
     * @param mainFunctionNameDataList  录入的所有必填模板方法名
     * @param mainSetOperatingModelList 录入的必填模板设置的所有操作层数据模型相关数据
     * @param mainSetCodeModelList      录入的必填模板设置的所有代码层数据模型相关数据
     * @return
     */
    @TransactionMulti
    public boolean saveMainInputData(MainInfo mainInfo, MainMetaModel mainMetaModel,
                                     List<VariableData> mainVariableDataList, List<FunctionNameData> mainFunctionNameDataList,
                                     List<FormatTypeOperatingModel> mainSetOperatingModelList, List<FormatTypeCodeModel> mainSetCodeModelList) {
        try {
            SysService.FORMAT_INFO_SERVICE.updateMainSetInfo(mainInfo);
            SysService.VARIABLE_DATA_SERVICE.saveMainVariableList(mainVariableDataList);
            SysService.FUNCTION_NAME_DATA_SERVICE.saveMainFunctionNameList(mainFunctionNameDataList);
            SysService.MAIN_FORMAT_CODE_FILE_SERVICE.updateMainOperating(mainMetaModel.getOperatingModel());
            List<GeneralFileFormat> list = new ArrayList<>();
            list.addAll(mainMetaModel.getCodeModelList());
            SysService.MAIN_FORMAT_CODE_FILE_SERVICE.save(list);
            SysService.MAIN_SET_SERVICE.saveOperatingModel(mainSetOperatingModelList);
            SysService.MAIN_SET_SERVICE.saveCodeModel(mainSetCodeModelList);

            SysService.SYS_PARAM_SERVICE.setEnabledState(true);//目前设定只要保存了必填模板相关内容，那这用户数据源就可以使用（数据源一开始默认无法使用，这句只在第一次保存必填模板时起到实际作用，开始标记这个数据源可以使用）

            SysService.SYS_PARAM_SERVICE.updateDataSourceClientVersion();//保存数据到数据源的时候，立刻写入当前这个客户端的版本
        } catch (Exception e) {
            SysService.SYS_SERVICE_SERVICE.log_error(InputDataSaveServiceImpl.class + " 保存必填模板录入数据出错：\n" + e.getMessage() + "\n\n\n");
            return false;
        }
        return true;
    }

    /**
     * 保存可选模板的相关录入数据
     *
     * @param additionalInfoList                       所有录入的可选模板的信息数据
     * @param additionalOperatingList                  所有录入的可选模板的格式操作层的对应数据
     * @param additionalCodeFormatList                 所有录入的可选模板的代码文件的对应数据
     * @param additionalVariableInputDataList          所有录入的可选模板的变量的相关数据
     * @param additionalFunctionNameInputDataList      所有录入的可选模板的方法名的相关数据
     * @param additionalSetOperatingList               所有录入的可选模板的设置功能操作层的对应数据
     * @param additionalSetCodeList                    所有录入的可选模板的设置功能代码层的对应数据
     * @param additionalFunctionOperatingInputDataList 所有可选模板的可选模板的功能操作层的相关数据
     * @param additionalFunctionCodeInputDataList      所有可选模板的可选模板的功能代码层的相关数据
     * @return
     */
    @TransactionMulti
    public boolean saveAdditionalInputData(List<AdditionalInfo> additionalInfoList,
                                           ArrayList<AdditionalOperating> additionalOperatingList, List<GeneralFileFormat> additionalCodeFormatList,
                                           ArrayList<AdditionalVariableInputData> additionalVariableInputDataList, ArrayList<AdditionalFunctionNameInputData> additionalFunctionNameInputDataList,
                                           List<FormatTypeOperatingModel> additionalSetOperatingList, List<FormatTypeCodeModel> additionalSetCodeList,
                                           ArrayList<AdditionalFunctionOperatingInputData> additionalFunctionOperatingInputDataList, ArrayList<AdditionalFunctionCodeInputData> additionalFunctionCodeInputDataList) {
        boolean flag = true;
        try {
            SysService.FORMAT_INFO_SERVICE.saveAdditionalSetInfoList(additionalInfoList);//保存可选模板的格式信息
            SysService.ADDITIONAL_FORMAT_FILE_SERVICE.saveAdditionalOperatingList(additionalOperatingList);//保存录入的可选模板的格式控制层模型代码
            SysService.ADDITIONAL_FORMAT_FILE_SERVICE.saveFormatCodeFileList(additionalCodeFormatList);//保存录入的可选模板的所有代码文件数据
            if (additionalVariableInputDataList.size() > 0) {//一个个可选模板保存录入的对应的变量数据
                for (AdditionalVariableInputData additionalVariableInputData : additionalVariableInputDataList) {
                    SysService.VARIABLE_DATA_SERVICE.saveAdditionalVariableList(additionalVariableInputData.getVariableDataList(), additionalVariableInputData.getAdditionalSerialNumber());
                }
            }
            if (additionalFunctionNameInputDataList.size() > 0) {//一个个可选模板保存录入的对应的方法名数据
                for (AdditionalFunctionNameInputData additionalFunctionNameInputData : additionalFunctionNameInputDataList) {
                    SysService.FUNCTION_NAME_DATA_SERVICE.saveAdditionalFunctionNameList(additionalFunctionNameInputData.getFunctionNameDataList(), additionalFunctionNameInputData.getAdditionalSerialNumber());
                }
            }
            SysService.ADDITIONAL_SET_SERVICE.saveOperatingModel(additionalSetOperatingList);//保存录入的可选模板的设置功能的操作层的数据
            SysService.ADDITIONAL_SET_SERVICE.saveCodeModel(additionalSetCodeList);//保存录入的可选模板的设置功能的代码层的数据

            if (additionalFunctionOperatingInputDataList.size() > 0) {//一个个可选模板保存录入的对应的可选模板的功能的操作层数据
                for (AdditionalFunctionOperatingInputData additionalFunctionOperatingInputData : additionalFunctionOperatingInputDataList) {
                    SysService.ADDITIONAL_FUNCTION_SERVICE.saveOperatingModel(additionalFunctionOperatingInputData.getAdditionalFunctionOperatingModelList(), additionalFunctionOperatingInputData.getAdditionalSerialNumber());
                }
            }
            if (additionalFunctionCodeInputDataList.size() > 0) {//一个个可选模板保存录入的对应的可选模板的功能的代码层数据
                for (AdditionalFunctionCodeInputData additionalFunctionCodeInputData : additionalFunctionCodeInputDataList) {
                    SysService.ADDITIONAL_FUNCTION_SERVICE.saveCodeModel(additionalFunctionCodeInputData.getAdditionalFunctionCodeModelList(), additionalFunctionCodeInputData.getAdditionalSerialNumber());
                }
            }

            SysService.SYS_PARAM_SERVICE.updateDataSourceClientVersion();//保存数据到数据源的时候，立刻写入当前这个客户端的版本
        } catch (Exception e) {
//            flag = false;
            SysService.SYS_SERVICE_SERVICE.log_error(InputDataSaveServiceImpl.class + " 保存可选模板录入数据出错：\n" + e.getMessage() + "\n\n\n");
//            return false;
        }
        return flag;
    }

    /**
     * 保存录入的模块数据
     *
     * @param moduleInfo                                    模块信息
     * @param module                                        模块
     * @param importCodeList                                录入的引入代码数据
     * @param moduleControl                                 录入的模块控制数据
     * @param variableDataList                              录入的变量数据
     * @param functionNameDataList                          录入的方法名数据
     * @param initInputData                                 录入的初始化数据
     * @param moduleSetInputData                            录入的模块设置数据
     * @param moduleFunctionInputData                       录入的模块方法数据
     * @param moduleCodeFileList                            录入的模块代码文件数据
     * @param mainCodeFileList                              需要更改的必填模板的代码文件数据（没有填 null）
     * @param additionalCodeFileList                        需要更改的可选模板代码文件数据（没有填 null）
     * @param needUpdateModuleCodeFileListDataForAdditional 保存本次模块数据的同时需要更改的其他的模块的代码文件数据
     * @return
     */
    @TransactionMulti
    public boolean saveModuleInputData(ModuleInfo moduleInfo, Module module,
                                       ArrayList<ImportCode> importCodeList, ModuleControl moduleControl,
                                       ArrayList<VariableData> variableDataList, ArrayList<FunctionNameData> functionNameDataList,
                                       InitInputData initInputData, ModuleSetInputData moduleSetInputData, ModuleFunctionInputData moduleFunctionInputData,
                                       ArrayList<GeneralFileFormat> moduleCodeFileList,
                                       ArrayList<GeneralFileFormat> mainCodeFileList, List<GeneralFileFormat> additionalCodeFileList, ArrayList<GeneralFileFormat> needUpdateModuleCodeFileListDataForAdditional) {
        try {
            SysService.MODULE_INFO_SERVICE.updateModuleInfo(moduleInfo);// 写入数据库，更新模块信息
            SysService.MODULE_SERVICE.setModuleInfo(module);//更新对应模块信息数据
            SysService.IMPORT_CODE_SERVICE.save(importCodeList, module.getModuleId());//保存录入的引入代码相关数据
            SysService.MODULE_CONTROL_SERVICE.updateModuleControl(moduleControl);//保存录入的模块控制相关数据
            SysService.VARIABLE_DATA_SERVICE.saveModuleVariableList(variableDataList, module.getModuleId());//保存录入的模块变量相关数据
            SysService.FUNCTION_NAME_DATA_SERVICE.saveModuleFunctionNameList(functionNameDataList, module.getModuleId());//保存录入的模块功能名相关数据

            //保存录入的初始化的功能相关数据
            SysService.INIT_SERVICE.saveOperatingModel(initInputData.getOperatingList(), module.getModuleId());
            SysService.INIT_SERVICE.saveCodeList(initInputData.getCodeList(), module.getModuleId());

            //保存录入的模块设置的功能相关数据
            SysService.MODULE_SET_SERVICE.saveOperatingModel(moduleSetInputData.getOperatingList(), module.getModuleId());
            SysService.MODULE_SET_SERVICE.saveCodeList(moduleSetInputData.getCodeList(), module.getModuleId());

            //保存录入的模块的功能相关数据
            SysService.FUNCTION_SERVICE.saveOperatingModel(moduleFunctionInputData.getOperatingList(), module.getModuleId());
            SysService.FUNCTION_SERVICE.saveCodeModel(moduleFunctionInputData.getCodeList(), module.getModuleId());

            //保存录入的模块代码文件相关数据
            SysService.MODULE_FILE_FORMAT_SERVICE.save(moduleCodeFileList, module.getModuleId());

            //如果保存本次数据需要更改必填模板代码文件的相关数据，保存当前必填模板相关数据
            if (mainCodeFileList != null && mainCodeFileList.size() > 0) {
                SysService.MAIN_FORMAT_CODE_FILE_SERVICE.save(mainCodeFileList);
            }

            //如果保存本次数据需要更改可选模板代码文件的相关数据，保存当前可选模板相关数据
            if (additionalCodeFileList != null && additionalCodeFileList.size() > 0) {
                SysService.ADDITIONAL_FORMAT_FILE_SERVICE.saveFormatCodeFileList(additionalCodeFileList);
            }

            //如果保存本次数据需要更改另外的模块代码文件的相关数据，保存这些模块代码文件相关数据
            if (needUpdateModuleCodeFileListDataForAdditional != null && needUpdateModuleCodeFileListDataForAdditional.size() > 0) {
                SysService.MODULE_FILE_FORMAT_SERVICE.updateModuleFileFormat(needUpdateModuleCodeFileListDataForAdditional);
            }
//        } catch (UncategorizedSQLException e) {
//            log.error("保存模块录入sql数据出错"+ e.getMessage());
//            return false;

            SysService.SYS_PARAM_SERVICE.updateDataSourceClientVersion();//保存数据到数据源的时候，立刻写入当前这个客户端的版本
        } catch (Exception e) {
            SysService.SYS_SERVICE_SERVICE.log_error(InputDataSaveServiceImpl.class + " 保存模块录入数据出错：\n" + e.getMessage() + "\n\n\n");
            return false;
        }
        return true;
    }

    /**
     * 保存模块的附带文件
     *
     * @param moduleInfo
     * @param attachedFileList
     * @return
     */
    @TransactionMulti
    public boolean saveModuleAttachedFileInputData(ModuleInfo moduleInfo, List<AttachedFile> attachedFileList) {
        try {
            SysService.ATTACHED_FILE_SERVICE.saveModuleAttachedFileList(moduleInfo.getModuleId(), attachedFileList);
            SysService.MODULE_INFO_SERVICE.updateNumOfModuleAttachedFile(moduleInfo);
        } catch (Exception e) {
            SysService.SYS_SERVICE_SERVICE.log_error(InputDataSaveServiceImpl.class + " 保存附带文件录入数据出错：\n" + e.getMessage() + "\n\n\n");
            return false;
        }
        return true;
    }

    /**
     * 保存可选模板的附带文件
     *
     * @param additionalInfo
     * @param attachedFileList
     * @return
     */
    @TransactionMulti
    public boolean saveAdditionalAttachedFileInputData(AdditionalInfo additionalInfo, List<AttachedFile> attachedFileList) {
        try {
            SysService.ATTACHED_FILE_SERVICE.saveAdditionalAttachedFileList(additionalInfo.getAdditionalSerialNumber(), attachedFileList);
            SysService.FORMAT_INFO_SERVICE.updateNumOfAdditionalAttachedFile(additionalInfo);

        } catch (Exception e) {
            SysService.SYS_SERVICE_SERVICE.log_error(InputDataSaveServiceImpl.class + " 保存附带文件录入数据出错：\n" + e.getMessage() + "\n\n\n");
            return false;
        }
        return true;
    }

    /**
     * 查看是否有使用这个代码标签
     *
     * @param codeLabelId 代码标签的对应id
     * @return
     */
    public boolean selectExistCodeLabelIdUsed(String codeLabelId) {
//        boolean flag = SysService.importCodeService.selectExistCodeLabelIdUsed(codeLabelId)&&
//                SysService.mainSetServicelmpl.selectExistCodeLabelIdUsed(codeLabelId)&&
//                SysService.initService.selectExistCodeLabelIdUsed(codeLabelId)&&
//                SysService.moduleSetServicelmpl.selectExistCodeLabelIdUsed(codeLabelId)&&
//                SysService.functionService.selectExistCodeLabelIdUsed(codeLabelId)&&
//                SysService.additionalFunctionServicelmpl.selectExistCodeLabelIdUsed(codeLabelId);
        boolean flag = true;
        if (!SysService.IMPORT_CODE_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
            if (!SysService.MAIN_SET_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
                if (!SysService.INIT_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
                    if (!SysService.MODULE_SET_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
                        if (!SysService.FUNCTION_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
                            if (!SysService.ADDITIONAL_FUNCTION_SERVICE.selectExistCodeLabelIdUsed(codeLabelId)) {
                                flag = false;
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

}
