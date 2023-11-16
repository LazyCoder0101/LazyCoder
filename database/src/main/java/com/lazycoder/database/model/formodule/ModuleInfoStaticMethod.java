package com.lazycoder.database.model.formodule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;

public class ModuleInfoStaticMethod {

    /**
     * 记录设置类型参数
     *
     * @param moduleInfo
     * @param ModuleSetTypeList
     */
    public static void setModuleSetTypeListParam(ModuleInfo moduleInfo, ArrayList<String> ModuleSetTypeList) {
        moduleInfo.setSetTheTypeOfSetCodeParam(JsonUtil.getJsonStr(ModuleSetTypeList));
    }

    /**
     * 获取所有设置类型
     *
     * @return
     */
    public static ArrayList<String> getModuleSetTypeListParam(ModuleInfo moduleInfo) {
        ArrayList<String> list = JSON.parseObject(moduleInfo.getSetTheTypeOfSetCodeParam(),
                new TypeReference<ArrayList<String>>() {
                });
        return list;
    }

    /**
     * 设置方法类型参数
     *
     * @param moduleInfo
     * @param FunctionTypeList
     */
    public static void setFunctionTypeListParam(ModuleInfo moduleInfo, ArrayList<String> FunctionTypeList) {
        moduleInfo.setFunctionTheTypeOfSourceCodeParam(JsonUtil.getJsonStr(FunctionTypeList));
    }

    /**
     * 获取方法类型参数
     *
     * @param moduleInfo
     * @return
     */
    public static ArrayList<String> getFunctionTypeListParam(ModuleInfo moduleInfo) {
        ArrayList<String> list = JSON.parseObject(moduleInfo.getFunctionTheTypeOfSourceCodeParam(),
                new TypeReference<ArrayList<String>>() {
                });
        return list;
    }


    /**
     * 设置添加文件的参数
     *
     * @param moduleInfo
     * @param AddFileParamList
     */
    public static void setAddFileParam(ModuleInfo moduleInfo, ArrayList<String> AddFileParamList) {
        moduleInfo.setAddFileParam(JsonUtil.getJsonStr(AddFileParamList));
    }


    /**
     * 获取需要使用的代码文件参数
     *
     * @return
     */
    public static ArrayList<CodeFormatFlagParam> getAdditionalCodeFilesParamsThatNeedToBeUsed(
            ModuleInfo moduleInfo) {
        ArrayList<CodeFormatFlagParam> list = JSON.parseObject(
                moduleInfo.getAdditionalCodeFilesParamsThatNeedToBeUsed(),
                new TypeReference<ArrayList<CodeFormatFlagParam>>() {
                });
        return list;
    }



}
