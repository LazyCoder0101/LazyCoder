package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.CodeGenerateBeanInterface;
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
import com.lazycoder.uicodegeneration.generalframe.tool.FunctionAddBeanIDGenerator;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.format.BaseCodeGenerateStatementModel;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeGenerateStatement {

    @JSONField(deserializeUsing = CodeStatementBeanListDeserializer.class)
    protected ArrayList<BaseBean> codeStatementBeanList = new ArrayList<>();

//    public CodeGenerateStatement(String indent, String statementFormat, String fileName) {
//        // TODO Auto-generated constructor stub
//        generateContent(indent, statementFormat, fileName);
//    }

    /**
     * 获取代码语句的内部元素列表
     *
     * @param codeGenerateStatementJSONObject
     * @return
     */
    protected static JSONArray getCodeStatementBeanList(JSONObject codeGenerateStatementJSONObject) {
        JSONArray jsonArray = codeGenerateStatementJSONObject.getJSONArray("codeStatementBeanList");
        return jsonArray;
    }

    protected void generateContent(List<JSONObject> list, String fileName) {
        //List<JSONObject> list = JsonUtil.restoreArrayByStr(statementFormat, JSONObject.class);
        StringBean stringBean;
        String elementType, labelType;
        if (list != null) {
            for (JSONObject temp : list) {
                elementType = BaseElement.getElementType(temp);

                if (ElementName.STRING_ELEMENT.equals(elementType)) {
                    stringBean = new StringBean(temp);
                    codeStatementBeanList.add(stringBean);

                } else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    labelType = BaseLableElement.getLabelType(temp);
                    generateLabelBean(labelType, temp, fileName);
                }
            }
        }
    }

    /**
     * 还原内部格式语句
     *
     * @param codeGenerateBeanModel
     */
    public void restoreStatementMode(BaseCodeGenerateStatementModel codeGenerateBeanModel) {
        StringBean stringBean;
        String type;
        List<JSONObject> list = JSON.parseArray(codeGenerateBeanModel.getCodeParam(), JSONObject.class);
        for (JSONObject temp : list) {
            type = temp.getString(BaseBean.THIS_TYPE);
            if (ElementName.STRING_ELEMENT.equals(type)) {
                stringBean = JSON.toJavaObject(temp, StringBean.class);
                codeStatementBeanList.add(stringBean);

            } else if (ElementName.LABEL_ELEMENT.equals(type)) {
                restoreLabelBean(temp);
            }
        }
    }

    /**
     * 根据存储文件对应的数据进行还原
     *
     * @param temp
     */
    protected void restoreLabelBean(JSONObject temp) {
        String labelType = BaseLableElement.getLabelType(temp);
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            TextInputBean bean = TextInputBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            ContentChooseBean bean = ContentChooseBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            FunctionAddBean bean = FunctionAddBean.restoreByJSONObject(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            CustomVariableBean bean = CustomVariableBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            VariableBean bean = VariableBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            ConstantBean bean = ConstantBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            FileSelectorBean bean = FileSelectorBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            CodeInputBean bean = CodeInputBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            CustomMethodNameBean bean = CustomMethodNameBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            MethodChooseBean bean = MethodChooseBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.THIS_FILE_NAME.equals(labelType)) {
            ThisFileNameBean bean = ThisFileNameBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            CorrespondingAdditionalDefaultFileBean bean = CorrespondingAdditionalDefaultFileBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);
        }
    }

    /**
     * 获取代码内容
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public String getCodeContent() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BaseBean bean: this.codeStatementBeanList) {
            stringBuilder.append(bean.getTheBeanValue());
        }
        return stringBuilder.toString();
    }

    /**
     * 根据标签元素生成标签组件
     *
     * @param labelType
     * @param temp
     * @param fileName  代码格式才需要
     */
    @JSONField(deserialize = false, serialize = false)
    protected void generateLabelBean(String labelType, JSONObject temp, String fileName) {
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            TextInputBean bean = TextInputBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            ContentChooseBean bean = ContentChooseBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            FunctionAddBean bean = FunctionAddBean.restoreByJSONObject(temp);
            bean.setFaId(FunctionAddBeanIDGenerator.generateCodeSerialNumber()+"");
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            CustomVariableBean bean = CustomVariableBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            VariableBean bean = VariableBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            ConstantBean bean = ConstantBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            FileSelectorBean bean = FileSelectorBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            CodeInputBean bean = CodeInputBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            CustomMethodNameBean bean = CustomMethodNameBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            MethodChooseBean bean = MethodChooseBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            CorrespondingAdditionalDefaultFileBean bean = CorrespondingAdditionalDefaultFileBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);

        } else if (LabelElementName.THIS_FILE_NAME.equals(labelType)) {
            ThisFileNameBean bean = ThisFileNameBean.restoreByJson(temp);
            codeStatementBeanList.add(bean);
            if (fileName != null) {
                bean.updateBeanValue(fileName);
            }
        }
    }

    /**
     * 设置参数
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public void setParam(BaseCodeGenerateStatementModel codeGenerateBeanModel) {
        ArrayList<CodeGenerateBeanInterface> list = new ArrayList<>();
        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
            list.add(baseBeanTemp);
        }
        codeGenerateBeanModel.setCodeParam(JsonUtil.getJsonStr(list));
    }

    /**
     * 即时计算出代码长度
     *
     * @return
     */
    public int calculatedCodeLength() {
        int temp = 0;
        for (BaseBean baseBean : codeStatementBeanList) {
            temp = temp + baseBean.getValueLength();
        }
        return temp;
    }

}
