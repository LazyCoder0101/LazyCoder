package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.StringBean;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.format.TheCodeStatementModel;
import com.lazycoder.utils.JsonUtil;
import java.util.List;
import lombok.Data;
import lombok.Getter;

/**
 * 一句基本语句使用的类
 *
 * @author admin
 */
@Data
public class TheCodeStatement extends CodeGenerateStatement {

    @Getter
    private int codeSerialNumber;

    private int codeOrdinal;

    /**
     * 代码字符串长度
     */
    private int codeLength = 0;

    public TheCodeStatement() {
        // TODO Auto-generated constructor stub
        generateContent("", "[]", "",false);
        this.codeLength = calculatedCodeLength();
    }

    public TheCodeStatement(int codeSerialNumber, int codeOrdinal) {
        // TODO Auto-generated constructor stub
        this();
        this.codeSerialNumber = codeSerialNumber;
        this.codeOrdinal = codeOrdinal;
    }

    public TheCodeStatement(int codeSerialNumber, int codeOrdinal, String statementFormat, String indent, boolean inserNewLineOrNot) {
        // TODO Auto-generated constructor stub
        this.codeSerialNumber = codeSerialNumber;
        this.codeOrdinal = codeOrdinal;
        generateContent(indent, statementFormat, "",inserNewLineOrNot);

        this.codeLength = calculatedCodeLength();
    }

    protected void generateContent(String indent, String statementFormat, String fileName, boolean inserNewLineOrNot) {
        List<JSONObject> list = JsonUtil.restoreArrayByStr(statementFormat, JSONObject.class);
        StringBean stringBean;
        if (statementFormat != null) {
            if (indent != null &&
                    indent.equals("") == false) {
                stringBean = new StringBean();
                stringBean.setBeanValue(indent);
                stringBean.setGfs(StringBean.FALSE_);
                codeStatementBeanList.add(stringBean);
            }

            generateContent(list, fileName);
            if (list.size() > 0 && inserNewLineOrNot == true) {
                stringBean = new StringBean();
                stringBean.updateBeanValue("\n");
                stringBean.setGfs(StringBean.FALSE_);
                codeStatementBeanList.add(stringBean);
            }
        }
    }

    /**
     * 根据 JSONObject还原
     *
     * @param theCodeStatementJSONObject
     * @return
     */
    public static TheCodeStatement restoreByJSONObject(JSONObject theCodeStatementJSONObject) {
        TheCodeStatement theCodeStatement = new TheCodeStatement();

        JSONObject jsonObject;
        StringBean stringBean;
        String type;
        theCodeStatement.codeSerialNumber = theCodeStatementJSONObject.getIntValue("codeSerialNumber");
        theCodeStatement.codeLength = theCodeStatementJSONObject.getIntValue("codeLength");
        JSONArray jsonArray = TheFormatStatement.getCodeStatementBeanList(theCodeStatementJSONObject);
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            type = jsonObject.getString(BaseBean.THIS_TYPE);
            if (ElementName.STRING_ELEMENT.equals(type)) {
                stringBean = StringBean.restoreByJSONObject(jsonObject);
                theCodeStatement.codeStatementBeanList.add(stringBean);

            } else if (ElementName.LABEL_ELEMENT.equals(type)) {
                theCodeStatement.restoreLabelBean(jsonObject);

            }
        }
        return theCodeStatement;
    }

    /**
     * 根据模型生成代码语句
     *
     * @param theCodeBeanModel
     * @return
     */
    public static TheCodeStatement creatBy(TheCodeStatementModel theCodeBeanModel) {
        TheCodeStatement theCodeStatement = new TheCodeStatement(theCodeBeanModel.getCodeSerialNumber(), theCodeBeanModel.getCodeOrdinal());
        theCodeStatement.restoreStatementMode(theCodeBeanModel);
        theCodeStatement.codeLength = theCodeStatement.calculatedCodeLength();
        return theCodeStatement;
    }

    /**
     * 获取代码对应的元素
     *
     * @param pathFindCell
     */
    @JSONField(deserialize = false, serialize = false)
    public UpdateCodeTemporaryVariable getLabelBean(PathFindCell pathFindCell) {
        UpdateCodeTemporaryVariable list = new UpdateCodeTemporaryVariable();
        LabelBean temp;
        boolean flag = true;
        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
            if (baseBeanTemp instanceof LabelBean) {
                temp = (LabelBean) baseBeanTemp;
                if (temp.match(pathFindCell) == true) {
                    list.add(temp);
                    list.setEditOrNot(true);//这句代码有pathFindCell这个对应的标签，标记这里表示该句代码这次更改内容需要响应
                    flag = false;
                } else {
                    if (flag == true) {
                        list.setCursorDisplacement(list.getCursorDisplacement() + baseBeanTemp.getValueLength());
                    }
                }
            } else {
                if (flag == true) {
                    list.setCursorDisplacement(list.getCursorDisplacement() + baseBeanTemp.getValueLength());
                }
            }
        }
        return list;
    }

    /**
     * 设值
     *
     * @param pathFindCell
     * @param updateParam
     */
    public void setValue(PathFindCell pathFindCell, Object updateParam) {
        LabelBean temp;
        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
            if (baseBeanTemp instanceof LabelBean) {
                temp = (LabelBean) baseBeanTemp;
                if (temp.match(pathFindCell) == true) {
                    temp.updateBeanValue(pathFindCell.getOpratingLabel(), updateParam);
                }
            }
        }
        this.codeLength = calculatedCodeLength();
    }

    /**
     * 获取代码存储模型
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public TheCodeStatementModel getTheCodeBeanModel() {
        TheCodeStatementModel theCodeBeanModel = new TheCodeStatementModel();
        super.setParam(theCodeBeanModel);
        theCodeBeanModel.setCodeSerialNumber(this.codeSerialNumber);
        theCodeBeanModel.setCodeOrdinal(codeOrdinal);
        theCodeBeanModel.setCodeLength(codeLength);
        return theCodeBeanModel;
    }

    public int getCodeLength() {
        return calculatedCodeLength();
    }

}
