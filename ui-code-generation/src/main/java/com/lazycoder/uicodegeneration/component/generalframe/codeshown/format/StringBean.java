package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;


public class StringBean extends BaseElement implements BaseBean, CodeGenerateBeanInterface, BaseModel {

    /**
     * Generate According to Format 是否根据格式生成（一般生成字符类型的代码都是根据既有的格式生成，有时有例外，需要将此改为false_做标记）
     */
	@Getter
	@Setter
    private int gfs = TRUE_;

    @Getter
    @Setter
    private String beanValue = "";

    public StringBean() {
        // TODO Auto-generated constructor stub
        this.type = ElementName.STRING_ELEMENT;
        this.beanValue = "";
    }

    public StringBean(JSONObject elementJSONObject) {
        // TODO Auto-generated constructor stub
        StringElement element = StringElement.getStringElement(elementJSONObject);
        this.type = ElementName.STRING_ELEMENT;
        this.beanValue = element.getText();
    }

    /**
     * 根据 JSONObject 还原
     *
     * @param jsonObject
     * @return
     */
    public static StringBean restoreByJSONObject(JSONObject jsonObject) {
        StringBean stringBean = JsonUtil.restoreByJSONObject(jsonObject, StringBean.class);
        return stringBean;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String str = "字符：" + beanValue;
        return str;
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return this.type;
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public String getTheBeanValue() {
        // TODO Auto-generated method stub
        return this.beanValue;
    }

    @Override
    public void updateBeanValue(Object updateParam) {
        // TODO Auto-generated method stub
        if (updateParam instanceof String) {
            this.beanValue = (String) updateParam;
        }
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public int getValueLength() {
        // TODO Auto-generated method stub
        return beanValue.length();
    }

}
