package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.MarkElementName;
import lombok.Getter;
import lombok.Setter;

/**
 * 初始化标记
 *
 * @author admin
 */
public class InitMarkElement extends BaseMarkElement {

    /**
     * 模块匹配权值
     */
    public static final int MODULE_MATCHING_WEIGHT = 1;

    /**
     * 命令序号匹配权值
     */
    public static final int COMMAND_SERIAL_MATCHING_WEIGHT = 2;

    /**
     * 代码序号匹配权值
     */
    public static final int CODE_SERIAL_MATCHING_WEIGHT = 3;

    @Setter
    @Getter
    private String moduleId = null;

    /**
     * 初始化序号 （第几个初始化源码写在这标记的，可不写）
     */
    @Setter
    @Getter
    private int initSerialNumber = MarkElementName.MARK_NULL;

    /**
     * 代码编号 （第几条初始化代码写在这里，可不写）
     */
    @Setter
    @Getter
    private int codeNumber = MarkElementName.MARK_NULL;

    public InitMarkElement() {
        // TODO Auto-generated constructor stub
        setMarkType(MarkElementName.INIT_MARK);
        codeLabelMatchingWeight = 4;
    }

    public static InitMarkElement jsonParsing(JSONObject elementJSONObject) {
        return JSON.toJavaObject(elementJSONObject, InitMarkElement.class);
    }

    /**
     * 是否为通用
     */
    @JSONField(deserialize = false, serialize = false)
    @Override
    public boolean isGeneral() {
        boolean flag = false;
        if ((moduleId == null || "".equals(moduleId)) && initSerialNumber == MarkElementName.MARK_NULL
                && codeNumber == MarkElementName.MARK_NULL&&
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
        InitMarkElement initStatementInformation = (InitMarkElement) statementInformation;
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

        if (initStatementInformation.moduleId != null && ("".equals(initStatementInformation.moduleId) == false)) {
            if (initStatementInformation.moduleId.trim().equals(moduleId) == false) {
                flag = false;
            }
        }
        if (initStatementInformation.initSerialNumber != MarkElementName.MARK_NULL) {
            if (initStatementInformation.initSerialNumber != initSerialNumber) {
                flag = false;
            }
        }
        if (initStatementInformation.codeNumber != MarkElementName.MARK_NULL) {
            if (initStatementInformation.codeNumber != codeNumber) {
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
        InitMarkElement initMarkElement = (InitMarkElement) statementInformation;
        if (initMarkElement.moduleId != null && ("".equals(initMarkElement.moduleId) == false)) {
            if (initMarkElement.moduleId.trim().equals(moduleId)) {
                numOfMatchingFeatures++;
            }
        }
        if (initMarkElement.initSerialNumber == initSerialNumber) {
            numOfMatchingFeatures++;
        }
        if (initMarkElement.codeNumber == codeNumber) {
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
        InitMarkElement initMarkElement = (InitMarkElement) statementInformation;
        if (ifCodeLabelIdIsNull()==false &&
                statementInformation.ifCodeLabelIdIsNull()==false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                matchValue = matchValue + codeLabelMatchingWeight;
            }
        }
        if (initMarkElement.moduleId != null && ("".equals(initMarkElement.moduleId) == false)) {
            if (initMarkElement.moduleId.trim().equals(moduleId)) {
                matchValue = matchValue + MODULE_MATCHING_WEIGHT;
            }
        }
        if (initMarkElement.initSerialNumber == initSerialNumber) {
            matchValue = matchValue + COMMAND_SERIAL_MATCHING_WEIGHT;
        }
        if (initMarkElement.codeNumber == codeNumber) {
            matchValue = matchValue + CODE_SERIAL_MATCHING_WEIGHT;
        }
        return matchValue;
    }

    @Override
    public InitMarkElement clone() {
        InitMarkElement markElement = (InitMarkElement) super.clone();
        return markElement;
    }

}
