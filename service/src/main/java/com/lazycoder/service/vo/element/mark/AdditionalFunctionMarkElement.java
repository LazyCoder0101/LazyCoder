package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.MarkElementName;

/**
 * 可选模板业务方法标记
 *
 * @author admin
 */
public class AdditionalFunctionMarkElement extends BaseMarkElement {

    public AdditionalFunctionMarkElement() {
        // TODO Auto-generated constructor stub
        setMarkType(MarkElementName.ADDITIONAL_FUNCTION_MARK);
        codeLabelMatchingWeight = 1;
    }

    @Override
    public AdditionalFunctionMarkElement clone() {
        AdditionalFunctionMarkElement markElement = (AdditionalFunctionMarkElement) super.clone();
        return markElement;
    }

    public static AdditionalFunctionMarkElement jsonParsing(JSONObject elementJSONObject) {
        return JSON.toJavaObject(elementJSONObject, AdditionalFunctionMarkElement.class);
    }

}
