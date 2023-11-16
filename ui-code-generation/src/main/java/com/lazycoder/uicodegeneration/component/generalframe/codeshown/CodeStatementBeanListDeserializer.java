package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.StringBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.CodeInputBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.ConstantBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.ContentChooseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.CorrespondingAdditionalDefaultFileBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.CustomMethodNameBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.CustomVariableBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FileSelectorBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FunctionAddBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.MethodChooseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.TextInputBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.ThisFileNameBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.VariableBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.AdditionalSetMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.FunctionMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.ImportMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.InitMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.MainSetMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.SetMarkBean;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CodeStatementBeanListDeserializer implements ObjectDeserializer {

    private static void restoreLabelBean(JSONObject beanObject, ArrayList<BaseBean> beanList) {
        ContentChooseBean contentChooseBean;
        OptionDataModel optionDataModel;
        BaseBean bean;
        String labelType = BaseLableElement.getLabelType(beanObject);
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, TextInputBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, ContentChooseBean.class);
            beanList.add(bean);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, FunctionAddBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, CustomVariableBean.class);
            beanList.add(bean);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, VariableBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, ConstantBean.class);
            beanList.add(bean);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, FileSelectorBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, CodeInputBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, CustomMethodNameBean.class);
            beanList.add(bean);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, MethodChooseBean.class);
            beanList.add(bean);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, CorrespondingAdditionalDefaultFileBean.class);
            beanList.add(bean);

        } else if (LabelElementName.THIS_FILE_NAME.equals(labelType)) {
            bean = JSON.parseObject(JSON.toJSONString(beanObject), ThisFileNameBean.class);
//            bean = JsonUtil.restoreByJSONObject(beanObject, ThisFileNameBean.class);
            beanList.add(bean);
        }
    }

    private static void restoreMarkBean(JSONObject beanObject, ArrayList<BaseBean> beanList) {
        BaseBean bean;
        String markType = BaseMarkElement.getMarkType(beanObject);
        if (MarkElementName.FUNCTION_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, FunctionMarkBean.class);
            beanList.add(bean);

        } else if (MarkElementName.SET_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, SetMarkBean.class);
            beanList.add(bean);

        } else if (MarkElementName.INIT_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, InitMarkBean.class);
            beanList.add(bean);

        } else if (MarkElementName.MAIN_SET_TYPE_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, MainSetMarkBean.class);
            beanList.add(bean);

        } else if (MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, AdditionalSetMarkBean.class);
            beanList.add(bean);

        } else if (MarkElementName.IMPORT_MARK.equals(markType)) {
            bean = JsonUtil.restoreByJSONObject(beanObject, ImportMarkBean.class);
            beanList.add(bean);
        }
    }

    @Override
    public ArrayList<BaseBean> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<BaseBean> beanList = new ArrayList<>();

        BaseBean beanTemp;
        String beanType;
        List<JSONObject> jsonArray = parser.parseArray(JSONObject.class);
        for (JSONObject base : jsonArray) {
            beanType = BaseElement.getElementType(base);
            if (ElementName.STRING_ELEMENT.equals(beanType)) {
                beanTemp = JsonUtil.restoreByJSONObject(base, StringBean.class);
                beanList.add(beanTemp);

            } else if (ElementName.LABEL_ELEMENT.equals(beanType)) {
                restoreLabelBean(base, beanList);

            } else if (ElementName.MARK_ELEMENT.equals(beanType)) {
                restoreMarkBean(base, beanList);
            }
        }
        return beanList;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }


}
