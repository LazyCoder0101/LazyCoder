package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.MarkElementName;
import lombok.Getter;
import lombok.Setter;

/**
 * 引入标记
 *
 * @author admin
 */
public class ImportMarkElement extends BaseMarkElement {

    /**
     * 模块匹配权值
     */
    public static final int MODULE_MATCHING_WEIGHT = 1;

    /**
     * 引入序号匹配权值
     */
    public static final int IMPPRT_SERIAL_MATCHING_WEIGHT = 2;

    @Setter
    @Getter
    private String moduleId = "";

    @Setter
    @Getter
    private int ordinal = MarkElementName.MARK_NULL;

    public ImportMarkElement() {
        // TODO Auto-generated constructor stub
        setMarkType(MarkElementName.IMPORT_MARK);
        codeLabelMatchingWeight = 3;
    }

    public static ImportMarkElement jsonParsing(JSONObject elementJSONObject) {
        return JSON.toJavaObject(elementJSONObject, ImportMarkElement.class);
    }

    /**
     * 是否为通用
     */
    @JSONField(deserialize = false, serialize = false)
    @Override
    public boolean isGeneral() {
        boolean flag = false;
        if ("".equals(moduleId) && ordinal == MarkElementName.MARK_NULL &&
                ifCodeLabelIdIsNull()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 匹配比对
     *
     * @param statementInformation
     * @return
     */
    @Override
    public boolean matchThan(BaseMarkElement statementInformation) {
        ImportMarkElement importMarkElementTemp = (ImportMarkElement) statementInformation;
        boolean flag = true;
        if (ifCodeLabelIdIsNull()) {//这个标记没限制一定要写哪个标签
            if (statementInformation.ifCodeLabelIdIsNull()) {//比对的这个标记时没规定是哪个标签的
                flag = true;
            } else {
                flag = false;
            }
        } else {//这个标记限制要写某个标签
            if (statementInformation.ifCodeLabelIdIsNull()) {//比对的这个标记时没规定是哪个标签的
                flag = false;
            } else if (statementInformation.codeLabelId.equals(codeLabelId)) {
                flag = true;
            } else {
                flag = false;
            }
        }

        if (importMarkElementTemp.moduleId != null && "".equals(importMarkElementTemp.moduleId.trim()) == false) {
            if (importMarkElementTemp.moduleId.equals(moduleId) == false) {
                flag = false;
            }
        }
        if (importMarkElementTemp.ordinal != MarkElementName.MARK_NULL) {
            if (importMarkElementTemp.ordinal != ordinal) {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * 获取匹配特征数量
     */
    @Override
    public int getNumOfMatchingFeatures(BaseMarkElement statementInformation) {
        int numOfMatchingFeatures = 0;
        if (ifCodeLabelIdIsNull()==false &&
                statementInformation.ifCodeLabelIdIsNull()==false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                numOfMatchingFeatures++;
            }
        }
        ImportMarkElement initMarkElement = (ImportMarkElement) statementInformation;
        if (initMarkElement.moduleId.equals(moduleId)) {
            numOfMatchingFeatures++;
        }
        if (initMarkElement.ordinal == ordinal) {
            numOfMatchingFeatures++;
        }
        return numOfMatchingFeatures;
    }

    /**
     * 获取匹配值
     */
    @Override
    public int getMatchValue(BaseMarkElement statementInformation) {
        int matchValue = 0;
        if (ifCodeLabelIdIsNull()==false &&
                statementInformation.ifCodeLabelIdIsNull()==false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                matchValue = matchValue + codeLabelMatchingWeight;
            }
        }
        ImportMarkElement initMarkElement = (ImportMarkElement) statementInformation;
        if (initMarkElement.moduleId.equals(moduleId)) {
            matchValue = matchValue + MODULE_MATCHING_WEIGHT;
        }
        if (initMarkElement.ordinal == ordinal) {
            matchValue = matchValue + IMPPRT_SERIAL_MATCHING_WEIGHT;
        }
        return matchValue;
    }

    @Override
    public ImportMarkElement clone() {
        ImportMarkElement markElement = (ImportMarkElement) super.clone();
        return markElement;
    }

}
