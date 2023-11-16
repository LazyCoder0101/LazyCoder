package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.service.vo.element.mark.MarkElementDeserializer;
import com.lazycoder.uicodegeneration.CodeListCarrierInterface;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.AddCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeListDeserializer;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.UpdateCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FunctionAddBean;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForMark;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractMarkBean extends BaseElement implements BaseBean, HarryingMark, CodeGenerateBeanInterface, CodeListCarrierInterface {

    @JSONField(deserializeUsing = MarkElementDeserializer.class)
    @Setter
    protected BaseMarkElement markElement;

    @JSONField(deserializeUsing = CodeListDeserializer.class)
    @Setter
    private CodeList codeList = new CodeList();

    @Setter
    @Getter
    private String markType;

    public AbstractMarkBean() {
        // TODO Auto-generated constructor stub
        this.type = ElementName.MARK_ELEMENT;
    }

    /**
     * 获取markType这个字段的字段名
     *
     * @param jsonObject
     * @return
     */
    public static String getMarkTypeFieldName(JSONObject jsonObject) {
        return jsonObject.getString("markType");
    }

    /**
     * 根据 JSONObject 得到 codeList 的 JSONObject
     *
     * @param markBeanJSONObject
     * @return
     */
    public static JSONObject getCodeListJSONObject(JSONObject markBeanJSONObject) {
        JSONObject jsonObject = markBeanJSONObject.getJSONObject("codeList");
        return jsonObject;
    }

    /**
     * 根据 JSONObject 得到 markElement 的 JSONObject
     *
     * @param markBeanJSONObject
     * @return
     */
    public static JSONObject getMarkElementJSONObject(JSONObject markBeanJSONObject) {
        JSONObject jsonObject = markBeanJSONObject.getJSONObject("markElement");
        return jsonObject;
    }


    @Override
    public BaseMarkElement getMarkElement() {
        return markElement;
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public String getTheBeanValue() {
        // TODO Auto-generated method stub
        return codeList.getThisCodeText();
    }

    @Override
    public void updateBeanValue(Object updateParam) {
    }

    /**
     * 根据目前存放的代码返回代码文本
     */
    @JSONField(deserialize = false, serialize = false)
    public String getThisCodeText() {
        return codeList.getThisCodeText();
    }

    public String getMarkType() {
        return markElement.getMarkType();
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        return this.type;
    }

    /**
     * 添加代码 init init 1:f1 init: 1:f1；2：f1;3：f1
     *
     * @param addCodeEditParamForMark
     */
    public AddCodeTemporaryVariable addCode(AddCodeEditParamForMark addCodeEditParamForMark) {
        AddCodeTemporaryVariable addCodeTemporaryVariable = null;
        if (addCodeEditParamForMark.getTrulyPathFind().whetherToOperateAddDelOrNotForMark() == true) {// 可直接操作
            addCodeTemporaryVariable = addCodeToOperateAddDelOrNotForMark(addCodeEditParamForMark);

        } else {// 逐层定位添加
//            if (null == addCodeEditParamForMark.getCommandAddRelatedAttribute() ||
//                    CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute()
//            ) {//无需进行移位 以及 直接添加到对应标记
            addCodeTemporaryVariable = addCodeToCodeMarkShift_None(addCodeEditParamForMark);

//            } else if (null != addCodeEditParamForMark.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                //逐步向上搜索，找出合适的标记
//                addCodeTemporaryVariable = addCodeToCodeMarkToStepByStepToFindCorrespondingMark(addCodeEditParamForMark);
//
//            } else if ((null != addCodeEditParamForMark.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute())) {
//                //直接添加到对应的标记
//                addCodeTemporaryVariable = addCodeToCodeMarkShift_addToTheFirstCorrespondingMark(addCodeEditParamForMark);
//            }
        }
        return addCodeTemporaryVariable;
    }

    /**
     * 直接插入某句代码到这个标记
     *
     * @param codeSerialNumber     要插入的代码的编号
     * @param codeOrdinal
     * @param codeStatement        生成代码的字符串
     * @param addToLast            是否插入到最后面，是的话传true
     * @param nextCodeSerialNumber 下一个编号，如果要插入到某个编号的代码前面，传这个参数，没有直接填null
     * @param inserNewLineOrNot     插入代码以后是否需要换行
     * @return
     */
    public int addCode(int codeSerialNumber,
                       int codeOrdinal,
                       String codeStatement,
                       boolean addToLast, Integer nextCodeSerialNumber,
                       boolean inserNewLineOrNot) {
        int incrementalTemp = 0;
        if (addToLast && nextCodeSerialNumber != null) {//直接添加到最后
            incrementalTemp = this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, "",inserNewLineOrNot);
        } else {//插入到某个值的前面
            incrementalTemp = this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, nextCodeSerialNumber, "",inserNewLineOrNot);
        }
        return incrementalTemp;
    }

    public int addCode(PathFind pathFind, int codeSerialNumber,
                       int codeOrdinal, String codeLabelId,
                       String codeStatement,
                       boolean addToLast, Integer nextCodeSerialNumber,
                       boolean inserNewLineOrNot) {
        int incrementalTemp = 0;
        if (pathFind.whetherToOperateAddDelOrNotForMark()) {
            if ((codeLabelId == null && markElement.ifCodeLabelIdIsNull()) ||
                    (codeLabelId != null && codeLabelId.equals(markElement.getCodeLabelId()))) {
                if (addToLast && nextCodeSerialNumber != null) {//直接添加到最后
                    incrementalTemp = this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, nextCodeSerialNumber, "", inserNewLineOrNot);
                } else {//插入到某个值的前面
                    incrementalTemp = this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, "", inserNewLineOrNot);
                }
            }
        } else {
            UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
                    .getLastLabelBeanList(this.codeList, pathFind, 0, "");
            LabelBean labelBeanTemp;
            FunctionAddBean functionAddBeanTemp;
            boolean firFlag = true;
            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
                labelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if ((codeLabelId == null && functionAddBeanTemp.ifCodeLabelIdIsNull()) ||
                            (codeLabelId != null && codeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                        if (firFlag) {
                            if (addToLast && nextCodeSerialNumber != null) {//直接添加到最后
                                incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement() +
                                        functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, nextCodeSerialNumber, inserNewLineOrNot);
                            } else {//插入到某个值的前面
                                incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement() +
                                        functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                            }
                            firFlag = false;
                        } else {
                            if (addToLast && nextCodeSerialNumber != null) {//直接添加到最后
                                functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, nextCodeSerialNumber, inserNewLineOrNot);
                            } else {//插入到某个值的前面
                                functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                            }
                        }
                    }
                }
            }
        }
        return incrementalTemp;
    }

    /**
     * 添加代码（直接添加到标记）
     *
     * @param addCodeEditParamForMark
     * @return
     */
    private AddCodeTemporaryVariable addCodeToOperateAddDelOrNotForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        int incrementalTemp = 0;

        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();

        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
        if ((faCodeLabelId == null && addCodeEditParamForMark.getThanMarkElement().ifCodeLabelIdIsNull()) ||
                (faCodeLabelId != null && faCodeLabelId.equals(addCodeEditParamForMark.getThanMarkElement().getCodeLabelId()))) {
            if (addCodeEditParamForMark.isAddToLast() == true) {//查看这句初始化是不是要添加到最后的
                incrementalTemp = this.codeList.addCode(
                        addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                        addCodeEditParamForMark.getCodeStatementParam(), "", addCodeEditParamForMark.isInserNewLineOrNot());
            } else {//不是添加到最后的，把下一个模块的id传过来，方法会加到下一个id前
                incrementalTemp = this.codeList.addCode(
                        addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                        addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber()
                        , "", addCodeEditParamForMark.isInserNewLineOrNot());
            }
            addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
            addCodeTemporaryVariable.setCodePathFind(addCodeEditParamForMark.getTrulyPathFind());//标记上，将addCodeEditParamForMark.getPathFind()直接作为代码的寻址字符串
            addCodeTemporaryVariable.setEditOrNot(true);
            addCodeTemporaryVariable.add(this);
        } else {
            addCodeTemporaryVariable.setEditOrNot(false);
        }
        return addCodeTemporaryVariable;
    }

    /**
     * 添加代码（添加到标记里面的功能拓展组件，且无需进行代码移位）
     *
     * @param addCodeEditParamForMark
     * @return
     */
    private AddCodeTemporaryVariable addCodeToCodeMarkShift_None(AddCodeEditParamForMark addCodeEditParamForMark) {
        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();

        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
                .getLastLabelBeanList(this.codeList, addCodeEditParamForMark.getTrulyPathFind(), 0, "");
        updateCodeTemporaryVariable.setCodePathFind(addCodeEditParamForMark.getTrulyPathFind());

        FunctionAddBean functionAddBeanTemp;

        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute() == null ?
                null : addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();

        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
            LabelBean labelBeanTemp;
            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
                labelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (null != functionAddBeanTemp.getCodeLabelId()) {
                        updateCodeTemporaryVariable.getList().remove(i);
                        i = -1;
                    }
                }
            }
        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签功能拓展组件都去掉
            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
                if (updateCodeTemporaryVariable.getList().get(i) instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) updateCodeTemporaryVariable.getList().get(i);
                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                        updateCodeTemporaryVariable.getList().remove(i);
                        i = -1;
                    }
                }
            }
        }
        if (updateCodeTemporaryVariable.getList().size() > 0) {
            updateCodeTemporaryVariable.setEditOrNot(true);
        } else {
            updateCodeTemporaryVariable.setEditOrNot(false);
        }

        int incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement();
        String indentStr;
        boolean flag = true;
        for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
            if (labelBeanTemp instanceof FunctionAddBean) {
                functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                indentStr = updateCodeTemporaryVariable.getIndent(functionAddBeanTemp.getFaId());
                addCodeTemporaryVariable.add(functionAddBeanTemp);

                if (flag == true) {//添加第一句代码的时候，记下相对游标位置

                    if (addCodeEditParamForMark.isAddToLast() == true) {//直接插入到最后
                        incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                                addCodeEditParamForMark.getCodeStatementParam(), indentStr, addCodeEditParamForMark.isInserNewLineOrNot());
                    } else {//插入到指定NextCodeSerialNumber前面
                        incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                                addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber(),
                                indentStr, addCodeEditParamForMark.isInserNewLineOrNot());
                    }
                    flag = false;
                } else {
                    if (addCodeEditParamForMark.isAddToLast() == true) {
                        functionAddBeanTemp.getCodeList().addCode(
                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                                addCodeEditParamForMark.getCodeStatementParam(), indentStr, addCodeEditParamForMark.isInserNewLineOrNot());
                    } else {
                        functionAddBeanTemp.getCodeList().addCode(
                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
                                addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber(),
                                indentStr, addCodeEditParamForMark.isInserNewLineOrNot());
                    }
                }
            }
        }
        updateCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
        addCodeTemporaryVariable.setEditOrNot(updateCodeTemporaryVariable.isEditOrNot());
        addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
        addCodeTemporaryVariable.setCodePathFind(updateCodeTemporaryVariable.getCodePathFind());
        return addCodeTemporaryVariable;
    }

    /**
     * 添加代码（添加到标记里面的功能拓展组件，且需逐步向上级查找，找到合适的功能拓展标签，进行代码移位）
     *
     * @param addCodeEditParamForMark
     * @return
     */
//    private AddCodeTemporaryVariable addCodeToCodeMarkToStepByStepToFindCorrespondingMark(AddCodeEditParamForMark addCodeEditParamForMark) {
//        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();
//
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanListforCodeMarkShift_StepByStep(this.codeList, addCodeEditParamForMark.getTrulyPathFind(),
//                        addCodeEditParamForMark.getCommandAddRelatedAttribute(), 0);
//        FunctionAddBean functionAddBeanTemp;
//
//        int incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement();
//        if (updateCodeTemporaryVariable.isEditOrNot()) {//在功能拓展组件，里面的组件就已经找到合适的位置
//            boolean flag = true;
//            for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    addCodeTemporaryVariable.add(functionAddBeanTemp);
//
//                    if (flag == true) {//添加第一句代码的时候，记下相对游标位置
//
//                        if (addCodeEditParamForMark.isAddToLast() == true) {//直接插入到最后
//                            incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam());
//                        } else {//插入到指定NextCodeSerialNumber前面
//                            incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                        }
//                        flag = false;
//                    } else {
//                        if (addCodeEditParamForMark.isAddToLast() == true) {
//                            functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam());
//                        } else {
//                            functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                        }
//                    }
//                }
//            }
//            updateCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//
//            addCodeTemporaryVariable.setCodePathFind(updateCodeTemporaryVariable.getCodePathFind());
//            addCodeTemporaryVariable.setEditOrNot(updateCodeTemporaryVariable.isEditOrNot());
//            addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//        } else {//在功能拓展组件里面找不到符合的位置，看看本标记是否符合
//            String codeMarkShiftCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getMarkElement().getCodeLabelId() == null) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                            addCodeEditParamForMark.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParamForMark.getTrulyPathFind(), 0));
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getMarkElement().getCodeLabelId())) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                            addCodeEditParamForMark.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParamForMark.getTrulyPathFind(), 0));
//                }
//            }
//        }
//        return addCodeTemporaryVariable;
//    }

    /**
     * 添加代码（添加到标记里面的功能拓展组件，如果对应的功能拓展组件没有符合的位置，直接添加到对应标记）
     *
     * @param addCodeEditParamForMark
     * @return
     */
//    private AddCodeTemporaryVariable addCodeToCodeMarkShift_addToTheFirstCorrespondingMark(AddCodeEditParamForMark addCodeEditParamForMark) {
//        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();
//
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanList(this.codeList, addCodeEditParamForMark.getTrulyPathFind(), 0);
//
//        FunctionAddBean functionAddBeanTemp;
//
//        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
//        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute() == null ?
//                null : addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
//
//        LabelBean codeGenerationTemporaryVariableLabelBeanTemp;
//        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
//            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
//                codeGenerationTemporaryVariableLabelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
//                if (codeGenerationTemporaryVariableLabelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) codeGenerationTemporaryVariableLabelBeanTemp;
//                    if (null != functionAddBeanTemp.getCodeLabelId()) {
//                        updateCodeTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
//            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
//                codeGenerationTemporaryVariableLabelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
//                if (codeGenerationTemporaryVariableLabelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) codeGenerationTemporaryVariableLabelBeanTemp;
//                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
//                        updateCodeTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        }
//
//        //如果在上一步搜索到合适的标签，记下当前的寻址字符串
//        if (updateCodeTemporaryVariable.getList().size() > 0) {
//            updateCodeTemporaryVariable.setCodePathFind(addCodeEditParamForMark.getTrulyPathFind());
//            updateCodeTemporaryVariable.setEditOrNot(true);
//        } else if (updateCodeTemporaryVariable.getList().size() == 0) {
//            updateCodeTemporaryVariable.setEditOrNot(false);
//        }
//        int incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement();
//
//        if (updateCodeTemporaryVariable.isEditOrNot()) {
//            boolean flag = true;
//            for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    addCodeTemporaryVariable.add(functionAddBeanTemp);
//
//                    if (flag == true) {//添加第一句代码的时候，记下相对游标位置
//
//                        if (addCodeEditParamForMark.isAddToLast() == true) {//直接插入到最后
//                            incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam());
//                        } else {//插入到指定NextCodeSerialNumber前面
//                            incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                        }
//                        flag = false;
//                    } else {
//                        if (addCodeEditParamForMark.isAddToLast() == true) {
//                            functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam());
//                        } else {
//                            functionAddBeanTemp.getCodeList().addCode(
//                                    addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                    addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                        }
//                    }
//                }
//            }
//            updateCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//
//            addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//            addCodeTemporaryVariable.setCodePathFind(updateCodeTemporaryVariable.getCodePathFind());
//            addCodeTemporaryVariable.setEditOrNot(true);
//        } else {
//            if (faCodeLabelId == null) {
//                if (null == this.getMarkElement().getCodeLabelId()) {
//                    if (addCodeEditParamForMark.isAddToLast() == true) {//直接插入到最后
//                        incrementalTemp = incrementalTemp + this.codeList.addCode(
//                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                addCodeEditParamForMark.getCodeStatementParam());
//                    } else {//插入到指定NextCodeSerialNumber前面
//                        incrementalTemp = incrementalTemp + this.codeList.addCode(
//                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                    }
//                    updateCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParamForMark.getTrulyPathFind(), 0));
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                } else {
//                    updateCodeTemporaryVariable.setEditOrNot(false);
//                    addCodeTemporaryVariable.setEditOrNot(false);
//                }
//            } else {
//                if (faCodeLabelId.equals(this.getMarkElement().getCodeLabelId())) {
//                    if (addCodeEditParamForMark.isAddToLast() == true) {//直接插入到最后
//                        incrementalTemp = incrementalTemp + this.codeList.addCode(
//                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                addCodeEditParamForMark.getCodeStatementParam());
//                    } else {//插入到指定NextCodeSerialNumber前面
//                        incrementalTemp = incrementalTemp + this.codeList.addCode(
//                                addCodeEditParamForMark.getCodeSerialNumber(), addCodeEditParamForMark.getCodeOrdinal(),
//                                addCodeEditParamForMark.getCodeStatementParam(), addCodeEditParamForMark.getNextCodeSerialNumber());
//                    }
//                    updateCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParamForMark.getTrulyPathFind(), 0));
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                } else {
//                    updateCodeTemporaryVariable.setEditOrNot(false);
//                    addCodeTemporaryVariable.setEditOrNot(false);
//                }
//            }
//        }
//        return addCodeTemporaryVariable;
//    }

    /**
     * 检查要添加的代码是否可以添加到这里
     *
     * @param addCodeEditParamForMark
     * @return
     */
    public boolean checkWhetherItMatchesForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        boolean returnFlag = false;
        if (addCodeEditParamForMark.getTrulyPathFind().whetherToOperateAddDelOrNotForMark() == true) {// 可直接操作
            returnFlag = checkWhetherItMatchesForMarkToOperateAddDelOrNotForMark(addCodeEditParamForMark);
        } else {// 逐层定位添加
//            if (null == addCodeEditParamForMark.getCommandAddRelatedAttribute() ||
//                    CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute()
//            ) {//无需进行移位 以及 直接添加到对应标记
            returnFlag = checkWhetherItMatchesForMarkToCodeMarkShift_None(addCodeEditParamForMark);
//            } else if (null != addCodeEditParamForMark.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                returnFlag = checkWhetherItMatchesForMarkToSTEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(addCodeEditParamForMark);
//
//            } else if ((null != addCodeEditParamForMark.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForMark.getCommandAddRelatedAttribute().getOtherAttribute())) {
//                //直接添加到对应的标记
//                returnFlag = checkWhetherItMatchesForMarkToCodeMarkShift_addToTheFirstCorrespondingMark(addCodeEditParamForMark);
//            }
        }
        return returnFlag;
    }

    /**
     * 检查是否可以直接添加到标记
     *
     * @param addCodeEditParamForMark
     * @return
     */
    private boolean checkWhetherItMatchesForMarkToOperateAddDelOrNotForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        boolean returnFlag = false;
        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
        if ((faCodeLabelId == null && addCodeEditParamForMark.getThanMarkElement().ifCodeLabelIdIsNull()) ||
                (faCodeLabelId != null && faCodeLabelId.equals(addCodeEditParamForMark.getThanMarkElement().getCodeLabelId()))) {
            returnFlag = true;
        }
        return returnFlag;
    }

    /**
     * 检测是否可以添加代码（检测添加到标记里面的功能拓展组件，且无需进行代码移位的情况）
     *
     * @param addCodeEditParamForMark
     * @return
     */
    private boolean checkWhetherItMatchesForMarkToCodeMarkShift_None(AddCodeEditParamForMark addCodeEditParamForMark) {
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
                .getLastLabelBeanList(this.codeList, addCodeEditParamForMark.getTrulyPathFind(), 0, "");
        updateCodeTemporaryVariable.setCodePathFind(addCodeEditParamForMark.getTrulyPathFind());

        FunctionAddBean functionAddBeanTemp;

        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute() == null ?
                null : addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();

        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
            LabelBean labelBeanTemp;
            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
                labelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (null != functionAddBeanTemp.getCodeLabelId()) {
                        updateCodeTemporaryVariable.getList().remove(i);
                        i = -1;
                    }
                }
            }
        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
            LabelBean labelBeanTemp;
            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
                labelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                        updateCodeTemporaryVariable.getList().remove(i);
                        i = -1;
                    }
                }
            }
        }
        if (updateCodeTemporaryVariable.getList().size() > 0) {
            updateCodeTemporaryVariable.setEditOrNot(true);
        } else {
            updateCodeTemporaryVariable.setEditOrNot(false);
        }
        return updateCodeTemporaryVariable.isEditOrNot();
    }

    /**
     * 检测是否可以添加代码（检测添加到标记里面的功能拓展组件，且逐级往上的情况）
     *
     * @param addCodeEditParamForMark
     * @return
     */
//    private boolean checkWhetherItMatchesForMarkToSTEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE(AddCodeEditParamForMark addCodeEditParamForMark) {
//        boolean returnFlag = false;
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanListforCodeMarkShift_StepByStep(this.codeList, addCodeEditParamForMark.getTrulyPathFind(),
//                        addCodeEditParamForMark.getCommandAddRelatedAttribute(), 0);
//
//        if (updateCodeTemporaryVariable.isEditOrNot()) {//在功能拓展组件，里面的组件就已经找到合适的位置
//            returnFlag = true;
//        } else {//在功能拓展组件里面找不到符合的位置，看看本标记是否符合
//            String codeMarkShiftCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getMarkElement().getCodeLabelId() == null) {
//                    returnFlag = true;
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getMarkElement().getCodeLabelId())) {
//                    returnFlag = true;
//                }
//            }
//        }
//        return returnFlag;
//    }

    /**
     * 检查是否可以添加代码（检查 添加到标记里面的功能拓展组件，如果对应的功能拓展组件没有符合的位置，直接添加到对应标记的情况）
     *
     * @param addCodeEditParamForMark
     * @return
     */
//    private boolean checkWhetherItMatchesForMarkToCodeMarkShift_addToTheFirstCorrespondingMark(AddCodeEditParamForMark addCodeEditParamForMark) {
//        boolean returnFlag = false;
//
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanList(this.codeList, addCodeEditParamForMark.getTrulyPathFind(), 0);
//        updateCodeTemporaryVariable.setCodePathFind(addCodeEditParamForMark.getTrulyPathFind());
//
//        FunctionAddBean functionAddBeanTemp;
//
//        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
//        String faCodeLabelId = addCodeEditParamForMark.getCommandAddRelatedAttribute() == null ?
//                null : addCodeEditParamForMark.getCommandAddRelatedAttribute().getCodeLabelId();
//
//        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
//            LabelBean labelBeanTemp;
//            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
//                labelBeanTemp = updateCodeTemporaryVariable.getList().get(i);
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    if (null != functionAddBeanTemp.getCodeLabelId()) {
//                        updateCodeTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
//            for (int i = 0; i < updateCodeTemporaryVariable.getList().size(); i++) {
//                if (updateCodeTemporaryVariable.getList().get(i) instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) updateCodeTemporaryVariable.getList().get(i);
//                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
//                        updateCodeTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        }
//        if (updateCodeTemporaryVariable.getList().size() > 0) {
//            updateCodeTemporaryVariable.setEditOrNot(true);
//        } else if (updateCodeTemporaryVariable.getList().size() == 0) {
//            updateCodeTemporaryVariable.setEditOrNot(false);
//
//        }
//        if (updateCodeTemporaryVariable.isEditOrNot()) {
//            returnFlag = true;
//        } else {
//            if (faCodeLabelId == null) {
//                if (null == this.getMarkElement().getCodeLabelId()) {
//                    returnFlag = true;
//                } else {
//                    returnFlag = false;
//                }
//            } else {
//                if (faCodeLabelId.equals(this.getMarkElement().getCodeLabelId())) {
//                    returnFlag = true;
//                } else {
//                    returnFlag = false;
//                }
//            }
//        }
//        return returnFlag;
//    }

    /**
     * 更改值 init 1:t1 init: 1:f1；2：f1;3：t1
     *
     * @param updateCodeEditParamForMark
     */
    public UpdateCodeTemporaryVariable updateValue(UpdateCodeEditParamForMark updateCodeEditParamForMark) {
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction.getLastLabelBeanList(this.codeList,
                updateCodeEditParamForMark.getTrulyPathFind(), 0, "");
        for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {

            labelBeanTemp.updateBeanValue(updateCodeEditParamForMark.getOpratingLabel(),
                    updateCodeEditParamForMark.getUpdateParam());
        }
        return updateCodeTemporaryVariable;
    }

    /**
     * 删除值 init 4 init 2:f1 4:f2 5:f1 4
     *
     * @param delCodeEditParamForMark
     */
    public UpdateCodeTemporaryVariable delCode(DelCodeEditParamForMark delCodeEditParamForMark) {
        UpdateCodeTemporaryVariable temporaryVariable = new UpdateCodeTemporaryVariable();
        int incrementalTemp = 0;// 增量
        if (delCodeEditParamForMark.getTrulyPathFind().whetherToOperateAddDelOrNotForMark() == true) {// 可直接操作
            temporaryVariable = this.codeList.deleteCode(delCodeEditParamForMark.getCodeSerialNumber());
//			incrementalTemp = temporaryVariable.getCursorDisplacement();//外边调用改方法时才重新计算游标值，所以这里不需要

        } else {// 逐层定位添加
            UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
                    .getLastLabelBeanList(this.codeList, delCodeEditParamForMark.getTrulyPathFind(), 0,"");
            incrementalTemp = updateCodeTemporaryVariable.getCursorDisplacement();
            FunctionAddBean functionAddBeanTemp;
            boolean flag = true;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (flag == true) {
                        temporaryVariable = functionAddBeanTemp.getCodeList().deleteCode(delCodeEditParamForMark.getCodeSerialNumber());
                        incrementalTemp = incrementalTemp + temporaryVariable.getCursorDisplacement();
                        temporaryVariable.setCursorDisplacement(incrementalTemp);
                        flag = false;
                    } else {
                        functionAddBeanTemp.getCodeList().deleteCode(delCodeEditParamForMark.getCodeSerialNumber());
                    }
                }
            }
        }
        return temporaryVariable;
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public int getValueLength() {
        // TODO Auto-generated method stub
        return codeList.calculatedCodeLength();
    }

    @Override
    public CodeList getCodeList() {
        return codeList;
    }

}
