package com.lazycoder.service.vo.element.mark;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.vo.base.BaseElement;
import lombok.Data;

@Data
public class BaseMarkElement extends BaseElement implements Cloneable {

    /**
     * 代码标签匹配的权值
     */
    @JSONField(deserialize = false, serialize = false)
    protected int codeLabelMatchingWeight = 4;

    private String markType;

    protected String codeLabelId = CodeLabel.NO_CODE_LABEL.getCodeLabelId();

    public BaseMarkElement() {
        // TODO Auto-generated constructor stub
        type = ElementName.MARK_ELEMENT;
    }

    /**
     * 判断codeLabelId有没有设置标记
     *
     * @return
     */
    public boolean ifCodeLabelIdIsNull() {
        boolean flag = false;
        if (this.codeLabelId == null) {
            flag = true;
        }
        return flag;
    }

    /**
     * 返回标记类型
     *
     * @param elementJSONObject
     * @return
     */
    public static String getMarkType(JSONObject elementJSONObject) {
        return elementJSONObject.getString("markType");
    }

    /**
     * 是否为通用(需要重写)
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean isGeneral() {
        return ifCodeLabelIdIsNull() ? true : false;
    }

    /**
     * 匹配比对(需要重写)
     *
     * @param statementInformation
     * @return
     */
    public boolean matchThan(BaseMarkElement statementInformation) {
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
        return flag;
    }

    /**
     * 获取匹配特征数量（需要重写）
     */
    public int getNumOfMatchingFeatures(BaseMarkElement statementInformation) {
        int numOfMatchingFeatures = 0;
        if (ifCodeLabelIdIsNull() == false && statementInformation.ifCodeLabelIdIsNull() == false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                numOfMatchingFeatures++;
            }
        }
        return numOfMatchingFeatures;
    }

    /**
     * 获取匹配值（需要重写）
     */
    @JSONField(deserialize = false, serialize = false)
    public int getMatchValue(BaseMarkElement statementInformation) {
        int matchValue = 0;
        if (ifCodeLabelIdIsNull() == false && statementInformation.ifCodeLabelIdIsNull() == false) {
            if (statementInformation.codeLabelId.equals(codeLabelId)) {
                matchValue = matchValue + codeLabelMatchingWeight;
            }
        }
        return matchValue;
    }

    @Override
    public BaseMarkElement clone() {
        BaseMarkElement markElement = null;
        try {
            markElement = (BaseMarkElement) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return markElement;
    }

}
