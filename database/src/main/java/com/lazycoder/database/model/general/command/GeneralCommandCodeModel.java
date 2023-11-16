package com.lazycoder.database.model.general.command;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.general.GeneralCodeModel;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.AbstractCodeUseProperty;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NeedUseModuleImportCode;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NoNeedInserNewLine;
import com.lazycoder.lazycodercommon.vo.CodeUseProperty.NoNeedToInsertImportCode;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能代码储存模型类
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class GeneralCommandCodeModel extends GeneralCodeModel {

    private String codeLabelId = null;

    private String codeUsePropertyParam = "[]";

    /**
     * 根据参数转成对应属性
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public static ArrayList<AbstractCodeUseProperty> getCodeUsePropertyList(String codeUsePropertyParam) {
        ArrayList<AbstractCodeUseProperty> codeUsePropertyList = new ArrayList<>();
        if (codeUsePropertyParam != null) {
            JSONArray codeUsePropertyJSONArray = JSONArray.parseArray(codeUsePropertyParam);
            JSONObject codeUsePropertyJsonObject;
            int size = codeUsePropertyJSONArray.size();
            for (int i = 0; i < size; i++) {
                codeUsePropertyJsonObject = codeUsePropertyJSONArray.getJSONObject(i);
                int type = codeUsePropertyJsonObject.getIntValue(AbstractCodeUseProperty.getCodeUsePropertyTypeName());
                if (type == AbstractCodeUseProperty.NoNeedToInsertImportCodeType) {
                    codeUsePropertyList.add(JsonUtil.restoreByJSONObject(codeUsePropertyJsonObject, NoNeedToInsertImportCode.class));

                } else if (type == AbstractCodeUseProperty.NoNeedInserNewLine) {
                    codeUsePropertyList.add(JsonUtil.restoreByJSONObject(codeUsePropertyJsonObject, NoNeedInserNewLine.class));

                } else if (type == AbstractCodeUseProperty.NeedUseModuleImportCodeType) {
                    codeUsePropertyList.add(JsonUtil.restoreByJSONObject(codeUsePropertyJsonObject, NeedUseModuleImportCode.class));
                }
            }
        }
        return codeUsePropertyList;
    }

    /**
     * 查看属性里面是否包含有某属性
     *
     * @param codeUsePropertyList
     * @param codeUsePropertyType
     * @return
     */
    public static boolean contant(ArrayList<AbstractCodeUseProperty> codeUsePropertyList, int codeUsePropertyType) {
        boolean flag = false;
        for (AbstractCodeUseProperty codeUseProperty : codeUsePropertyList) {
            if (codeUseProperty.getCodeUsePropertyType() == codeUsePropertyType) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取对应需要模块的引入代码的相关属性
     * @param codeUsePropertyList
     * @return
     */
    public static NeedUseModuleImportCode getNeedUseModuleImportCodeProperty(ArrayList<AbstractCodeUseProperty> codeUsePropertyList) {
        NeedUseModuleImportCode needUseModuleImportCode = null;
        for (AbstractCodeUseProperty codeUseProperty : codeUsePropertyList) {
            if (codeUseProperty.getCodeUsePropertyType() == AbstractCodeUseProperty.NeedUseModuleImportCodeType) {
                needUseModuleImportCode = (NeedUseModuleImportCode) codeUseProperty;
                break;
            }
        }
        return needUseModuleImportCode;
    }

}
