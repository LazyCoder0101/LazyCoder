package com.lazycoder.database.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FunctionNameData implements DataFormatType {

    /**
     * 方法名类型
     */
    private int functionNameProperty;

    private int additionalSerialNumber = 0;

    private String moduleId = "";

    /**
     * 分类
     */
    private String className = "";

    /**
     * 模块
     */
    private String moduleName = "";

    /**
     * 序号
     */
    private int serialNumber;

    /**
     * 方法名
     */
    private String functionName;

    /**
     * 方法作用名
     */
    private String roleOfFunctionName;

    /**
     * 方法使用范围
     */
    private int theAvaliableRange = MODULE_TYPE;

    /**
     * 数据类型参数
     */
    private String dataTypeParam = "[]";

    /**
     * 获取数据类型列表
     *
     * @param functionNameData
     * @return
     */
    public static ArrayList<String> getDataTypeList(FunctionNameData functionNameData) {
        ArrayList<String> dataTypeList = JSON.parseObject(functionNameData.dataTypeParam,
                new TypeReference<ArrayList<String>>() {
                });
        return dataTypeList;
    }

    /**
     * 获取数据类型列表
     *
     * @param dataTypeParam
     * @return
     */
    public static ArrayList<String> getDataTypeList(String dataTypeParam) {
        ArrayList<String> dataTypeList = JSON.parseObject(dataTypeParam, new TypeReference<ArrayList<String>>() {
        });
        return dataTypeList;
    }

    /**
     * 设置数据类型
     *
     * @param dataTypeParam
     * @param functionNameData
     */
    public static void setDataTypeList(ArrayList<String> dataTypeParam, FunctionNameData functionNameData) {
        String str = JsonUtil.getJsonStr(dataTypeParam);
        functionNameData.setDataTypeParam(str);
    }

}
