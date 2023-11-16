package com.lazycoder.database.model.general;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.utils.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
public class GeneralOperatingModel implements BaseModel {

    /**
     * 默认控制语句格式
     */
    private String defaultControlStatementFormat = "";

    /**
     * 各组件数量情况
     */
    private String numberOfComponents;

    /**
     * 控制组件对应信息
     */
    private String controlComponentCorrespondingInformation = "";

    /**
     * 所有备注的参数
     */
    private String noteListParam = "[]";

    /**
     * 生成使用组件数量数据
     */
    public static void generateUseComponentNumData(LinkedHashMap<String, Integer> useComponentNum) {
        useComponentNum.put(LabelElementName.TEXT_INPUT, 0);
//		useComponentNum.put(LabelElementName.contentChoose, 0);
        useComponentNum.put(LabelElementName.FUNCTION_ADD, 0);
        useComponentNum.put(LabelElementName.CUSTOM_VARIABLE, 0);
        useComponentNum.put(LabelElementName.VARIABLE, 0);
        useComponentNum.put(LabelElementName.CONSTANT, 0);
        useComponentNum.put(LabelElementName.FILE_SELECTOR, 0);
        useComponentNum.put(LabelElementName.NOTE, 0);
        useComponentNum.put(LabelElementName.PICTURE, 0);
        useComponentNum.put(LabelElementName.CODE_INPUT, 0);
        useComponentNum.put(LabelElementName.INFREQUENTLY_USED_SETTING, 0);
        useComponentNum.put(LabelElementName.CUSTOM_METHOD_NAME, 0);
        useComponentNum.put(LabelElementName.METHOD_CHOOSE, 0);
        useComponentNum.put(LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE, 0);
    }

    /**
     * 把use_component_num转为json字符串
     *
     * @param useComponentNum
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public static String getUseComponentNumParam(LinkedHashMap<String, Integer> useComponentNum) {
        return JsonUtil.getJsonStr(useComponentNum);
    }


}
