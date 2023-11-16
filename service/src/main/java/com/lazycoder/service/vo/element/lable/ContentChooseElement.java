package com.lazycoder.service.vo.element.lable;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.OptionDataModel;
import lombok.Data;

/**
 * 选择框元素
 *
 * @author Administrator
 */
@Data
public class ContentChooseElement extends BaseLableElement {

    @JSONField(ordinal = 4)
    private String optionId = null;

    /**
     * 使用编号（在功能里面是第几个该选项）
     */
    @JSONField(ordinal = 5)
    private int useNumbered = 0;

    /**
     * 选项名
     */
    @JSONField(ordinal = 6)
    private String optionName = null;

    public ContentChooseElement() {
        // TODO Auto-generated constructor stub
        super();
        this.setLabelType(LabelElementName.CONTENT_CHOOSE);
    }

    public ContentChooseElement(String optionId,String optionName) {
        this();
        this.optionId = optionId;
        this.optionName = optionName;
    }

    /**
     * 获取thisName （操作层）
     *
     * @param contentChooseElement
     * @return
     */
    public static String getThisNameForOprating(ContentChooseElement contentChooseElement) {
        String thisName = contentChooseElement.getOptionId() + "-" + contentChooseElement.getUseNumbered();
        return thisName;
    }

    /**
     * 获取显示名称 （操作层）
     * @param contentChooseElement
     * @param optionDataModel
     * @return
     */
    public static String getShowNameForOprating(ContentChooseElement contentChooseElement,OptionDataModel optionDataModel) {
        String thisName = optionDataModel.getOptionName() + "【" + contentChooseElement.getUseNumbered() + "】";
        return thisName;
    }

    /**
     * 获取thisName （代码层）
     *
     * @param contentChooseElement
     * @param groupId
     * @return
     */
    public static String getThisNameForCode(ContentChooseElement contentChooseElement, int groupId) {
        String thisName = getThisNameForOprating(contentChooseElement) + "-" + groupId;
        return thisName;
    }

    /**
     * 获取显示名称 （代码层）
     * @param contentChooseElement
     * @param groupId
     * @param optionDataModel
     * @return
     */
    public static String getShowNameForCode(ContentChooseElement contentChooseElement, int groupId,OptionDataModel optionDataModel) {
        String thisName = getShowNameForOprating(contentChooseElement,optionDataModel) + "{" + groupId + "}";
        return thisName;
    }

    @Override
    public boolean match(BaseLableElement contentChooseLabel) {
        // TODO Auto-generated method stub
        boolean flag = false;
        if (contentChooseLabel instanceof ContentChooseElement) {
            ContentChooseElement label = (ContentChooseElement) contentChooseLabel;
            if (label.getOptionId().equals(this.getOptionId())) {
                if (label.getUseNumbered() == this.getUseNumbered()) {
                    if (label.getLabelType().equals(this.getLabelType())) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public static ContentChooseElement restoreByJSONObject(JSONObject jSONObject) {
        ContentChooseElement contentChooseElement = JSON.toJavaObject(jSONObject, ContentChooseElement.class);
        return contentChooseElement;
    }

}
