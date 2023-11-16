package com.lazycoder.utils.compare;

/**
 * 摘自 https://blog.csdn.net/sunnyzyq/article/details/124603360
 * @author zyqok
 * @since 2022/05/05
 */
public class CompareNode {

    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段值
     */
    private Object fieldValue;

    /**
     * 字段名称
     */
    private String fieldName;

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
