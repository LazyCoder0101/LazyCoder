package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.MarkElementName;
import lombok.Getter;
import lombok.Setter;

/**
 * 必填模板设置标记
 *
 * @author admin
 */
public class MainSetMarkElement extends BaseMarkElement {

    /**
     * 模块设置的分类序号的匹配权值
     */
    public static final int NUMBER_OF_MODULE_SET_CLASSIFICATION_MATCHING_WEIGHT = 1;

    /**
     * 命令序号匹配权值
     */
    public static final int COMMAND_SERIAL_MATCHING_WEIGHT = 2;

    /**
     * 代码序号匹配权值
     */
    public static final int CODE_SERIAL_MATCHING_WEIGHT = 3;

    /**
     * 设置分类编号 (第几个分类的设置源码写在这标记的，可不写)
     */
    @Setter
    @Getter
    private int classificationSerial = MarkElementName.MARK_NULL;

    /**
     * 初始化序号 （第几个设置源码写在这标记的，可不写）
     */
    @Setter
    @Getter
    private int operatingSerialNumber = MarkElementName.MARK_NULL;

    /**
     * 代码编号 （第几条设置代码写在这里，可不写）
     */
    @Setter
    @Getter
    private int codeNumber = MarkElementName.MARK_NULL;

    public MainSetMarkElement() {
        // TODO Auto-generated constructor stub
        super();
        setMarkType(MarkElementName.MAIN_SET_TYPE_MARK);
        codeLabelMatchingWeight = 4;
    }

    public static MainSetMarkElement jsonParsing(JSONObject elementJSONObject) {
        return JSON.toJavaObject(elementJSONObject, MainSetMarkElement.class);
    }

    /**
     * 是否为通用
     */
    @JSONField(deserialize = false, serialize = false)
    @Override
    public boolean isGeneral() {
        boolean flag = false;
        if (classificationSerial == MarkElementName.MARK_NULL && operatingSerialNumber == MarkElementName.MARK_NULL
                && codeNumber == MarkElementName.MARK_NULL &&
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
        MainSetMarkElement setStatementInformation = (MainSetMarkElement) statementInformation;
        boolean flag = true;
        if (ifCodeLabelIdIsNull()) {//这个标记没限制一定要写哪个标签
            if (setStatementInformation.ifCodeLabelIdIsNull()) {//比对的这个标记时没规定是哪个标签的
                flag = true;
            } else {
                flag = false;
            }
        } else {//这个标记限制要写某个标签
            if (setStatementInformation.ifCodeLabelIdIsNull()) {//比对的这个标记时没规定是哪个标签的
                flag = false;
            } else if (setStatementInformation.codeLabelId.equals(codeLabelId)) {
                flag = true;
            } else {
                flag = false;
            }
        }

        if (setStatementInformation.classificationSerial != MarkElementName.MARK_NULL) {
            if (setStatementInformation.classificationSerial != classificationSerial) {
                flag = false;
            }
        }
        if (setStatementInformation.operatingSerialNumber != MarkElementName.MARK_NULL) {
            if (setStatementInformation.operatingSerialNumber != operatingSerialNumber) {
                flag = false;
            }
        }
        if (setStatementInformation.codeNumber != MarkElementName.MARK_NULL) {
            if (setStatementInformation.codeNumber != codeNumber) {
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
        MainSetMarkElement setStatementInformation = (MainSetMarkElement) statementInformation;
        int numOfMatchingFeatures = 0;
        if (ifCodeLabelIdIsNull()==false &&
                statementInformation.ifCodeLabelIdIsNull()==false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                numOfMatchingFeatures++;
            }
        }
        if (setStatementInformation.classificationSerial == classificationSerial) {
            numOfMatchingFeatures++;
        }
        if (setStatementInformation.operatingSerialNumber == operatingSerialNumber) {
            numOfMatchingFeatures++;
        }
        if (setStatementInformation.codeNumber == codeNumber) {
            numOfMatchingFeatures++;
        }
        return numOfMatchingFeatures;
    }

    /**
     * 获取匹配值
     */
    @Override
    public int getMatchValue(BaseMarkElement statementInformation) {
        MainSetMarkElement setStatementInformation = (MainSetMarkElement) statementInformation;
        int matchValue = 0;
        if (ifCodeLabelIdIsNull()==false &&
                statementInformation.ifCodeLabelIdIsNull()==false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                matchValue = matchValue + codeLabelMatchingWeight;
            }
        }
        if (setStatementInformation.classificationSerial == classificationSerial) {
            matchValue = matchValue + NUMBER_OF_MODULE_SET_CLASSIFICATION_MATCHING_WEIGHT;
        }
        if (setStatementInformation.operatingSerialNumber == operatingSerialNumber) {
            matchValue = matchValue + COMMAND_SERIAL_MATCHING_WEIGHT;
        }
        if (setStatementInformation.codeNumber == codeNumber) {
            matchValue = matchValue + CODE_SERIAL_MATCHING_WEIGHT;
        }
        return matchValue;
    }

    @Override
    public MainSetMarkElement clone() {
        MainSetMarkElement markElement = (MainSetMarkElement) super.clone();
        return markElement;
    }

}
