package com.lazycoder.database.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class VariableData implements DataFormatType, BaseModel {

    /**
     * 变量类型
     */
    private int variableProperty;

    private int additionalSerialNumber = 0;

    private String moduleId = "";

    /**
     * 序号
     */
    private int serialNumber;

    /**
     * 变量名
     */
    private String variableName;

    /**
     * 变量作用名
     */
    private String roleOfVariableName;

    /**
     * 变量使用范围
     */
    private int theAvaliableRange = MODULE_TYPE;

    /**
     * 数据类型参数
     */
    private String dataTypeParam = "[]";

    /**
     * 不需要用户进行选择
     */
    private int dontNeedTheUserToSelect = FALSE_;

    /**
     * 标签类型参数
     */
    private String labelTypeParam = "[]";

    /**
     * 判断是否是自动选择的
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean isNoUserSelectionIsRequired() {
        return dontNeedTheUserToSelect == FALSE_ ? false : true;
    }

    /**
     * 获取数据类型列表
     *
     * @param variableData
     * @return
     */
    public static ArrayList<String> getDataTypeList(VariableData variableData) {
        ArrayList<String> dataTypeList = JSON.parseObject(variableData.dataTypeParam,
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
     * @param variableData
     */
    public static void setDataTypeList(ArrayList<String> dataTypeParam, VariableData variableData) {
        String str = JsonUtil.getJsonStr(dataTypeParam);
        variableData.setDataTypeParam(str);
    }

    /**
     * 获取标签类型
     *
     * @param variableData
     * @return
     */
    public static ArrayList<String> getLabelTypeParam(VariableData variableData) {
        ArrayList<String> list = JSON.parseObject(variableData.labelTypeParam, new TypeReference<ArrayList<String>>() {
        });
        return list;
    }

    /**
     * 获取数据类型列表
     *
     * @param labelTypeParam
     * @return
     */
    public static ArrayList<String> getLabelTypeParam(String labelTypeParam) {
        ArrayList<String> dataTypeList = JSON.parseObject(labelTypeParam, new TypeReference<ArrayList<String>>() {
        });
        return dataTypeList;
    }

    /**
     * 设置标签类型
     *
     * @param labelTypeList
     * @param variableData
     */
    public static void setLabelTypeParam(ArrayList<String> labelTypeList, VariableData variableData) {
        String str = JsonUtil.getJsonStr(labelTypeList);
        variableData.setLabelTypeParam(str);
    }


}
