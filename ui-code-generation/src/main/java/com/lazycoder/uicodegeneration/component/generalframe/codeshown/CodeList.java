package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.format.CodeListModel;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.format.TheCodeStatementModel;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能拓展面板、业务逻辑面板等对应代码模型继承的类
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public class CodeList {

    @JSONField(deserializeUsing = CodeListArrayCodeListDeserializer.class)
    private ArrayList<TheCodeStatement> codeList = new ArrayList<>();

    /**
     * 根据 JSONObject还原
     *
     * @param codeListJSONObject
     * @return
     */
    public static CodeList restoreByJSONObject(JSONObject codeListJSONObject) {
        CodeList list = new CodeList();
        JSONArray codeListJSONArray = getCodeList(codeListJSONObject);
        JSONObject codeStatementJSONObject;
        TheCodeStatement theCodeStatement;
        for (int i = 0; i < codeListJSONArray.size(); i++) {
            codeStatementJSONObject = codeListJSONArray.getJSONObject(i);
            theCodeStatement = TheCodeStatement.restoreByJSONObject(codeStatementJSONObject);
            list.codeList.add(theCodeStatement);
        }
        return list;

    }

    /**
     * 获取代码列表的 JSONArray
     *
     * @param codeListJSONObject
     * @return
     */
    private static JSONArray getCodeList(JSONObject codeListJSONObject) {
        JSONArray jsonArray = codeListJSONObject.getJSONArray("codeList");
        return jsonArray;
    }

    /**
     * 根据模型初始化
     *
     * @param codeListModel
     * @return
     */
    public static CodeList creatBy(CodeListModel codeListModel) {
        TheCodeStatement theCodeStatementTemp;
        CodeList codeList = new CodeList();
        for (TheCodeStatementModel temp : codeListModel.getCodeModelList()) {
            theCodeStatementTemp = TheCodeStatement.creatBy(temp);
            codeList.codeList.add(theCodeStatementTemp);
        }
        return codeList;
    }

    /**
     * 获取对应的标签元素
     *
     * @param pathFindCell
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public UpdateCodeTemporaryVariable getLabelBean(PathFindCell pathFindCell) {
        UpdateCodeTemporaryVariable temporaryVariable = new UpdateCodeTemporaryVariable(), listTemp;
        boolean flag = true;
        for (TheCodeStatement baseCodeTemp : this.codeList) {

            if (baseCodeTemp.getCodeSerialNumber() == pathFindCell.getCodeSerialNumber()) {
                listTemp = baseCodeTemp.getLabelBean(pathFindCell);
                temporaryVariable.addAll(listTemp);
                if (flag == true) {
                    temporaryVariable.setEditOrNot(listTemp.isEditOrNot());

                    temporaryVariable.setCursorDisplacement(
                            temporaryVariable.getCursorDisplacement() + listTemp.getCursorDisplacement());
                }

                flag = false;
            } else {
                if (flag == true) {
                    temporaryVariable.setCursorDisplacement(
                            temporaryVariable.getCursorDisplacement() + baseCodeTemp.getCodeLength());
                }
            }
        }
        return temporaryVariable;
    }

    /**
     * 添加代码 对应寻址标记、生成代码的集合
     */
    public int addCode(int codeSerialNumber, int codeOrdinal, String codeStatement, String indent, boolean inserNewLineOrNot) {
        int incrementalCursorPositionTemp = calculatedCodeLength();
        if (!checkHaveTheCodeOrNot(codeSerialNumber, codeOrdinal)) {
            TheCodeStatement baseCode = new TheCodeStatement(codeSerialNumber, codeOrdinal, codeStatement, indent, inserNewLineOrNot);
            this.codeList.add(baseCode);
        }
        return incrementalCursorPositionTemp;
    }

    /**
     * 添加代码，目前业务上仅提供给初始化代码更换原来代码使用（如果发现当前代码没有为nextCodeSerialNumber的代码，生成代码会写在最后）
     *
     * @param codeSerialNumber     本次添加的代码的代码id
     * @param codeOrdinal
     * @param codeStatement        本次添加的代码的生成语句
     * @param nextCodeSerialNumber 要添加在哪一行代码的前面，传该代码的代码id
     * @param indent               缩进符
     * @param inserNewLineOrNot     插入代码以后是否需要换行
     * @return
     */
    public int addCode(int codeSerialNumber, int codeOrdinal, String codeStatement, Integer nextCodeSerialNumber, String indent, boolean inserNewLineOrNot) {
        int incrementalCursorPositionTemp = calculatedCodeLength();
        if (!checkHaveTheCodeOrNot(codeSerialNumber, codeOrdinal)) {
            TheCodeStatement baseCode = new TheCodeStatement(codeSerialNumber, codeOrdinal, codeStatement, indent, inserNewLineOrNot);
            int position = 0;
            boolean flag = false;//当前这里有没有id为nextCodeSerialNumber的标记
            if (nextCodeSerialNumber == null) {
                flag = true;
            } else {
                for (int i = 0; i < codeList.size(); i++) {
                    if (codeList.get(i).getCodeSerialNumber() == nextCodeSerialNumber) {
                        position = i;
                        flag = true;
                        break;
                    }
                }
            }
            if (flag == true) {
                this.codeList.add(position, baseCode);
            } else {
                this.codeList.add(baseCode);
            }
        }
        return incrementalCursorPositionTemp;
    }

    /**
     * 检查是否有添加过这组编号的代码（在这里设定每个codelist同一个codeSerialNumber和codeOrdinal只能插入一次）
     *
     * @param codeSerialNumber
     * @param codeOrdinal
     * @return
     */
    private boolean checkHaveTheCodeOrNot(int codeSerialNumber, int codeOrdinal) {
        boolean flag = false;
        for (TheCodeStatement codeStatement : this.codeList) {
            if (codeSerialNumber == codeStatement.getCodeSerialNumber() &&
                    codeOrdinal == codeStatement.getCodeOrdinal()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

//    public int deleteCode(int codeSerialNumber) {
//        int incrementalCursorPositionTemp = 0;
//        TheCodeStatement baseCodeTemp;
//        boolean flag = true;
//        for (int i = 0; i < this.codeList.size(); i++) {
//            baseCodeTemp = this.codeList.get(i);
//            if (baseCodeTemp.getCodeSerialNumber() == codeSerialNumber) {
//                this.codeList.remove(i);
//                i = -1;
//                flag = false;
//            } else {
//                if (flag == true) {
//                    incrementalCursorPositionTemp = incrementalCursorPositionTemp + baseCodeTemp.getCodeLength();
//                }
//            }
//        }
//        return incrementalCursorPositionTemp;
//    }

    /**
     * 删除代码 对应寻址标记、代码编号
     */
    public UpdateCodeTemporaryVariable deleteCode(int codeSerialNumber) {
        UpdateCodeTemporaryVariable temporaryVariable = new UpdateCodeTemporaryVariable();
        int incrementalCursorPositionTemp = 0;
        TheCodeStatement baseCodeTemp;
        boolean flag = true;

        for (int i = 0; i < this.codeList.size(); i++) {
            baseCodeTemp = this.codeList.get(i);
            if (baseCodeTemp.getCodeSerialNumber() == codeSerialNumber) {
                this.codeList.remove(i);
                i = -1;
                temporaryVariable.setEditOrNot(true);
                flag = false;
            } else {
                if (flag == true) {
                    incrementalCursorPositionTemp = incrementalCursorPositionTemp + baseCodeTemp.getCodeLength();
                }
            }
        }

        temporaryVariable.setCursorDisplacement(incrementalCursorPositionTemp);
        return temporaryVariable;
    }

    /**
     * 根据目前存放的代码返回代码文本
     */
    @JSONField(deserialize = false, serialize = false)
    public String getThisCodeText() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < this.codeList.size(); i++) {
            out.append(codeList.get(i).getCodeContent());
        }
        return out.toString();
    }

    /**
     * 获取代码列表模型
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public CodeListModel getCodeListModel() {
        CodeListModel model = new CodeListModel();
        for (TheCodeStatement temp : this.codeList) {
            model.getCodeModelList().add(temp.getTheCodeBeanModel());
        }
        return model;
    }

    /**
     * 获取这部分代码的字数
     *
     * @return
     */
    public int calculatedCodeLength() {
        int codeListLength = 0;
        for (TheCodeStatement temp : this.codeList) {
            codeListLength = codeListLength + temp.calculatedCodeLength();
        }
        return codeListLength;
    }

}
