package com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.code.FunctionAddCodeElement;
import com.lazycoder.uicodegeneration.CodeListCarrierInterface;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.AddCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeList;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeListDeserializer;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.UpdateCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.CodeGenerateBeanInterface;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParam;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParam;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParam;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

public class FunctionAddBean extends FunctionAddCodeElement implements LabelBean, CodeGenerateBeanInterface, CodeListCarrierInterface {

    @Setter
    @Getter
    private String faId = "0";

    @JSONField(deserializeUsing = CodeListDeserializer.class)
    @Setter
    private CodeList codeList = new CodeList();

    public FunctionAddBean() {
        // TODO Auto-generated constructor stub
        super();
    }

    /**
     * 根据 JSONObject还原
     *
     * @param elementJSONObject
     * @return
     */
    public static FunctionAddBean restoreByJSONObject(JSONObject elementJSONObject) {
        FunctionAddBean functionAddBean = JsonUtil.restoreByJSONObject(elementJSONObject, FunctionAddBean.class);
        JSONObject codeListJSONObject = getCodeList(elementJSONObject);
        if (codeListJSONObject != null) {
            CodeList theCodeList = CodeList.restoreByJSONObject(codeListJSONObject);
            functionAddBean.codeList = theCodeList;
        }
        return functionAddBean;
    }

    /**
     * 获取代码列表的 JSONObject
     *
     * @param functionAddBeanJSONObject
     * @return
     */
    private static JSONObject getCodeList(JSONObject functionAddBeanJSONObject) {
        JSONObject jsonObject = functionAddBeanJSONObject.getJSONObject("codeList");
        return jsonObject;
    }

//    /**
//     * 把所有的最后一个组件拿到（添加、更改、删除代码时拿到）
//     *
//     * @param codeList 0：f1 1：f2 2：f1
//     * @param pathFind
//     * @param ii       命令写0，格式写1
//     * @return
//     */
//    private static CodeGenerationTemporaryVariable getLastLabelBeanList(CodeList codeList, PathFind pathFind, int ii) {
//        CodeGenerationTemporaryVariable lastLabelBeanList = new CodeGenerationTemporaryVariable();
//        PathFindCell pathFindCell = pathFind.getPathList().get(ii);
//        if (ii == pathFind.getPathList().size() - 1) {// 如果到了最后一个，直接获取需要的标签bean
//            lastLabelBeanList = codeList.getLabelBean(pathFindCell);
//        } else {
//            CodeList nextCodeList;
//            LabelBean labelBeanTemp;
//            FunctionAddBean functionAddBeanTemp;
//
//            int iiNext = ii + 1;
//            CodeGenerationTemporaryVariable functionAddBeanlist = codeList.getLabelBean(pathFindCell),
//                    nextLastLabelBeanList = new CodeGenerationTemporaryVariable();
//            for (int i = 0; i < functionAddBeanlist.getList().size(); i++) {
//                labelBeanTemp = functionAddBeanlist.getList().get(i);
//                if (LabelElementName.FUNCTION_ADD.equals(labelBeanTemp.getLabelType())) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    nextCodeList = functionAddBeanTemp.getCodeList();
//                    if (i == 0) {
//                        nextLastLabelBeanList = getLastLabelBeanList(nextCodeList, pathFind, iiNext);
//                        lastLabelBeanList.setCursorDisplacement(lastLabelBeanList.getCursorDisplacement()
//                                + nextLastLabelBeanList.getCursorDisplacement());
//                    } else {
//                        nextLastLabelBeanList = getLastLabelBeanList(nextCodeList, pathFind, iiNext);
//                    }
//                    lastLabelBeanList.addAll(nextLastLabelBeanList);
//                }
//            }
//        }
//        return lastLabelBeanList;
//    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public String getTheBeanValue() {
        // TODO Auto-generated method stub
        return codeList.getThisCodeText();
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public void updateBeanValue(Object updateParam) {
        // TODO Auto-generated method stub
    }

    /**
     * 添加代码
     *
     * @param addCodeEditParam
     */
    public AddCodeTemporaryVariable addCodeToFormat(AddCodeEditParam addCodeEditParam) {
        AddCodeTemporaryVariable addCodeTemporaryVariable = null;
        if (addCodeEditParam.getTrulyPathFind().whetherToOperateForFormat() == true) {// 可直接操作
            addCodeTemporaryVariable = addCodeToFormatforOperateOrNot(addCodeEditParam);
        } else {// 逐层定位添加
//            if (null == addCodeEditParam.getCommandAddRelatedAttribute() ||
//                    CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE == addCodeEditParam.getCommandAddRelatedAttribute().getOtherAttribute()
//            ) {//无需进行移位 以及 直接添加到对应标记
            addCodeTemporaryVariable = addCodeToFormatShift_None(addCodeEditParam);

//            } else if (null != addCodeEditParam.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParam.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                //逐步向上搜索，找出合适的标记
//                addCodeTemporaryVariable = addCodeToFormatToStepByStepToFindCorrespondingMark(addCodeEditParam);
//
//            } else if ((null != addCodeEditParam.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParam.getCommandAddRelatedAttribute().getOtherAttribute())) {
//                //直接添加到对应的标记
//                addCodeTemporaryVariable = addCodeToFormatShift_addToTheFirstCorrespondingMark(addCodeEditParam);//在格式类型的功能拓展组件里面，直接当添加到对应标记的功能无效
//            }
        }
        return addCodeTemporaryVariable;
    }

    public int addCode(int codeSerialNumber, int codeOrdinal, String codeStatement, boolean inserNewLineOrNot) {
        return this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, this.getIndent(), inserNewLineOrNot);
    }

    public int addCode(int codeSerialNumber, int codeOrdinal, String codeStatement, Integer nextCodeSerialNumber, boolean inserNewLineOrNot) {
        return this.codeList.addCode(codeSerialNumber, codeOrdinal, codeStatement, nextCodeSerialNumber, this.getIndent(), inserNewLineOrNot);
    }

    private AddCodeTemporaryVariable addCodeToFormatforOperateOrNot(AddCodeEditParam addCodeEditParam) {
        AddCodeTemporaryVariable addCodeTemporaryVariable = null;
        int incrementalCursorPositionTemp = 0;
        String codeLabelId = addCodeEditParam.getCommandAddRelatedAttribute() == null ?
                null : addCodeEditParam.getCommandAddRelatedAttribute().getCodeLabelId();
        if ((codeLabelId == null && this.ifCodeLabelIdIsNull()) ||
                codeLabelId != null && codeLabelId.equals(this.getCodeLabelId())) {
            incrementalCursorPositionTemp = this.codeList.addCode(addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
                    addCodeEditParam.getCodeStatementParam(), this.getIndent(), addCodeEditParam.isInserNewLineOrNot());

            addCodeTemporaryVariable = new AddCodeTemporaryVariable();
            addCodeTemporaryVariable.setCodePathFind(addCodeEditParam.getTrulyPathFind());
            addCodeTemporaryVariable.setEditOrNot(true);
            addCodeTemporaryVariable.setCursorDisplacement(incrementalCursorPositionTemp);
            addCodeTemporaryVariable.add(this);
        }
        return addCodeTemporaryVariable;
    }

    /**
     * @param addCodeEditParam
     */
    private AddCodeTemporaryVariable addCodeToFormatShift_None(AddCodeEditParam addCodeEditParam) {
        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();

        UpdateCodeTemporaryVariable labelBeanList = CodeGenerationStaticFunction.getLastLabelBeanList(this.codeList,
                addCodeEditParam.getTrulyPathFind(), 1, this.getIndent());// 拿最后一个功能拓展组件
        labelBeanList.setCodePathFind(addCodeEditParam.getTrulyPathFind());

        FunctionAddBean functionAddBeanTemp;
        LabelBean labelBeanTemp;
        String codeLabelId = addCodeEditParam.getCommandAddRelatedAttribute().getCodeLabelId();
        if (codeLabelId != null) {//把不是这个codeLabelId的都去掉
            for (int a = 0; a < labelBeanList.getList().size(); a++) {
                labelBeanTemp = labelBeanList.getList().get(a);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (!codeLabelId.equals(functionAddBeanTemp.getCodeLabelId())) {
                        labelBeanList.getList().remove(a);
                        a = -1;
                    }
                }
            }
        } else {//codeLabelId为空
            for (int a = 0; a < labelBeanList.getList().size(); a++) {
                labelBeanTemp = labelBeanList.getList().get(a);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (null != functionAddBeanTemp.getCodeLabelId()) {
                        labelBeanList.getList().remove(a);
                        a = -1;
                    }
                }
            }
        }

        if (labelBeanList.getList().size() == 0) {
            labelBeanList.setEditOrNot(false);
        } else if (labelBeanList.getList().size() > 0) {
            labelBeanList.setEditOrNot(true);
        }

        int incrementalCursorPositionTemp = labelBeanList.getCursorDisplacement();
        //拿到组件以后进行添加
        boolean flag = true;
        for (int i = 0; i < labelBeanList.getList().size(); i++) {
            labelBeanTemp = labelBeanList.getList().get(i);
            if (labelBeanTemp instanceof FunctionAddBean) {
                functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                addCodeTemporaryVariable.getList().add(functionAddBeanTemp);
                if (flag == true) {
                    incrementalCursorPositionTemp = incrementalCursorPositionTemp
                            + functionAddBeanTemp.getCodeList().addCode(addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
                            addCodeEditParam.getCodeStatementParam(), this.getIndent(), addCodeEditParam.isInserNewLineOrNot());
                    flag = false;
                } else {
                    functionAddBeanTemp.getCodeList().addCode(addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
                            addCodeEditParam.getCodeStatementParam(), this.getIndent(), addCodeEditParam.isInserNewLineOrNot());
                }
            }
        }
        labelBeanList.setCursorDisplacement(incrementalCursorPositionTemp);

        addCodeTemporaryVariable.setCodePathFind(labelBeanList.getCodePathFind());
        addCodeTemporaryVariable.setEditOrNot(labelBeanList.isEditOrNot());
        addCodeTemporaryVariable.setCursorDisplacement(incrementalCursorPositionTemp);
        return addCodeTemporaryVariable;
    }

    /**
     * @param addCodeEditParam
     */
//    private AddCodeTemporaryVariable addCodeToFormatToStepByStepToFindCorrespondingMark(AddCodeEditParam addCodeEditParam) {
//        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();
//
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = CodeGenerationStaticFunction
//                .getLastLabelBeanListforCodeMarkShift_StepByStep(this.codeList, addCodeEditParam.getTrulyPathFind(),
//                        addCodeEditParam.getCommandAddRelatedAttribute(), 1);
//        FunctionAddBean functionAddBeanTemp;
//        int incrementalTemp = updateCodeTemporaryVariableTemp.getCursorDisplacement();
//
//        if (updateCodeTemporaryVariableTemp.isEditOrNot()) {//在功能拓展组件，里面的组件就已经找到合适的位置
//            //如果是添加到本功能拓展组件，里面的组件，在这里进行操作
//            boolean flag = true;
//            for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    addCodeTemporaryVariable.add(functionAddBeanTemp);
//                    if (flag == true) {//添加第一句代码的时候，记下相对游标位置
//
//                        incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                                addCodeEditParam.getCodeStatementParam());
//
//                        flag = false;
//                    } else {
//                        functionAddBeanTemp.getCodeList().addCode(
//                                addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                                addCodeEditParam.getCodeStatementParam());
//                    }
//                }
//            }
//
//            addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//            addCodeTemporaryVariable.setCodePathFind(updateCodeTemporaryVariableTemp.getCodePathFind());
//            addCodeTemporaryVariable.setEditOrNot(updateCodeTemporaryVariableTemp.isEditOrNot());
//
//        } else {//在功能拓展组件里面找不到合适的组件，看看本功能拓展组件（在格式模型里面的对应本功能拓展组件）是否合适
//
//            String codeMarkShiftCodeLabelId = addCodeEditParam.getCommandAddRelatedAttribute() == null ?
//                    null : addCodeEditParam.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getCodeLabelId() == null) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                            addCodeEditParam.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParam.getTrulyPathFind(), 1));
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getCodeLabelId())) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                            addCodeEditParam.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParam.getTrulyPathFind(), 1));
//                }
//            }
//        }
//        return addCodeTemporaryVariable;
//    }

    /**
     * 在格式类型中添加代码到对应标记
     *
     * @param addCodeEditParam
     */
//    private AddCodeTemporaryVariable addCodeToFormatShift_addToTheFirstCorrespondingMark(AddCodeEditParam addCodeEditParam) {
//        AddCodeTemporaryVariable addCodeTemporaryVariable = new AddCodeTemporaryVariable();
//
//        UpdateCodeTemporaryVariable codeGenerationTemporaryVariableTemp = CodeGenerationStaticFunction
//                .getLastLabelBeanList(this.codeList, addCodeEditParam.getTrulyPathFind(), 1);
//
//        FunctionAddBean functionAddBeanTemp;
//
//        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
//        String faCodeLabelId = addCodeEditParam.getCommandAddRelatedAttribute().getCodeLabelId();
//
//        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
//            LabelBean labelBeanTemp;
//            for (int i = 0; i < codeGenerationTemporaryVariableTemp.getList().size(); i++) {
//                labelBeanTemp = codeGenerationTemporaryVariableTemp.getList().get(i);
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    if (null != functionAddBeanTemp.getCodeLabelId()) {
//                        codeGenerationTemporaryVariableTemp.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
//            if (addCodeEditParam.getCommandAddRelatedAttribute() != null && CommandAddRelatedAttribute.ADD_TO_FUNCTION_ADD_COMPONENT_ADD_TYPE == addCodeEditParam.getCommandAddRelatedAttribute().getAddType()) {
//                for (int i = 0; i < codeGenerationTemporaryVariableTemp.getList().size(); i++) {
//                    if (codeGenerationTemporaryVariableTemp.getList().get(i) instanceof FunctionAddBean) {
//                        functionAddBeanTemp = (FunctionAddBean) codeGenerationTemporaryVariableTemp.getList().get(i);
//                        if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
//                            codeGenerationTemporaryVariableTemp.getList().remove(i);
//                            i = -1;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (codeGenerationTemporaryVariableTemp.getList().size() == 0) {
//            codeGenerationTemporaryVariableTemp.setEditOrNot(false);
//
//        } else if (codeGenerationTemporaryVariableTemp.getList().size() > 0) {
//            codeGenerationTemporaryVariableTemp.setEditOrNot(true);
//        }
//
//        int incrementalTemp = codeGenerationTemporaryVariableTemp.getCursorDisplacement();
//        if (codeGenerationTemporaryVariableTemp.isEditOrNot()) {
//            //在上一个步骤找到合适的功能拓展组件
//
//            //如果在上一步搜索到合适的标签，记下当前的寻址字符串
//            codeGenerationTemporaryVariableTemp.setCodePathFind(addCodeEditParam.getTrulyPathFind());
//
//            incrementalTemp = codeGenerationTemporaryVariableTemp.getCursorDisplacement();
//            boolean flag = true;
//            for (LabelBean labelBeanTemp : codeGenerationTemporaryVariableTemp.getList()) {
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    addCodeTemporaryVariable.add(functionAddBeanTemp);
//                    if (flag == true) {//添加第一句代码的时候，记下相对游标位置
//
//                        incrementalTemp = incrementalTemp + functionAddBeanTemp.getCodeList().addCode(
//                                addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                                addCodeEditParam.getCodeStatementParam());
//                        codeGenerationTemporaryVariableTemp.setCursorDisplacement(incrementalTemp);
//                        flag = false;
//                    } else {
//                        functionAddBeanTemp.getCodeList().addCode(
//                                addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                                addCodeEditParam.getCodeStatementParam());
//                    }
//                }
//            }
//
//            addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//            addCodeTemporaryVariable.setCodePathFind(codeGenerationTemporaryVariableTemp.getCodePathFind());
//            addCodeTemporaryVariable.setEditOrNot(codeGenerationTemporaryVariableTemp.isEditOrNot());
//        } else {
//            String codeMarkShiftCodeLabelId = addCodeEditParam.getCommandAddRelatedAttribute() == null ?
//                    null : addCodeEditParam.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getCodeLabelId() == null) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                            addCodeEditParam.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParam.getTrulyPathFind(), 1));
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getCodeLabelId())) {
//                    incrementalTemp = getCodeList().addCode(
//                            addCodeEditParam.getCodeSerialNumber(), addCodeEditParam.getCodeOrdinal(),
//                            addCodeEditParam.getCodeStatementParam());
//
//                    addCodeTemporaryVariable.setCursorDisplacement(incrementalTemp);
//                    addCodeTemporaryVariable.add(this);
//                    addCodeTemporaryVariable.setEditOrNot(true);
//                    addCodeTemporaryVariable.setCodePathFind(PathFind.getOnALayerOfPathFind(addCodeEditParam.getTrulyPathFind(), 1));
//                }
//            }
//        }
//        return addCodeTemporaryVariable;
//    }

    /**
     * 检查要添加的功能是否符合，是否可以添加到这里
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    public boolean checkWhetherItMatchesForFormat(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        boolean returnFlag = false;
        if (addCodeEditParamForFormat.getTrulyPathFind().whetherToOperateForFormat() == true) {// 可直接操作
            returnFlag = checkWhetherItMatchesForFormatToFormatforOperateOrNot(addCodeEditParamForFormat);
        } else {// 逐层定位添加
//            if (null == addCodeEditParamForFormat.getCommandAddRelatedAttribute() ||
//                    CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE == addCodeEditParamForFormat.getCommandAddRelatedAttribute().getOtherAttribute()) {
            returnFlag = checkWhetherItMatchesForFormatToCodeMarkShift_None(addCodeEditParamForFormat);

//            } else if (null != addCodeEditParamForFormat.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForFormat.getCommandAddRelatedAttribute().getOtherAttribute()) {
//                returnFlag = checkWhetherItMatchesForFormatToStepByStepToFindCorrespondingMark(addCodeEditParamForFormat);
//
//            } else if ((null != addCodeEditParamForFormat.getCommandAddRelatedAttribute() &&
//                    CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE == addCodeEditParamForFormat.getCommandAddRelatedAttribute().getOtherAttribute())) {
//                //直接添加到对应的标记
//                returnFlag = checkWhetherItMatchesForFormatToCodeMarkShift_addToTheFirstCorrespondingMark(addCodeEditParamForFormat);//在格式类型的功能拓展组件中，添加到对应标记当其无效
//            }
        }
        return returnFlag;
    }

    private boolean checkWhetherItMatchesForFormatToFormatforOperateOrNot(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        boolean returnFlag = false;
        String faCodeLabelId = addCodeEditParamForFormat.getCommandAddRelatedAttribute() == null ?
                null : addCodeEditParamForFormat.getCommandAddRelatedAttribute().getCodeLabelId();
        if (faCodeLabelId == null) {
            if (ifCodeLabelIdIsNull()) {
                returnFlag = true;
            }
        } else {
            if (faCodeLabelId.equals(this.getCodeLabelId())) {
                returnFlag = true;
            }
        }
        return returnFlag;
    }

    /**
     * 检测是否可以添加代码（检测添加到格式类型的功能拓展组件里面的功能拓展组件，且无需进行代码移位的情况）
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    private boolean checkWhetherItMatchesForFormatToCodeMarkShift_None(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        UpdateCodeTemporaryVariable labelBeanList = CodeGenerationStaticFunction
                .getLastLabelBeanList(this.codeList, addCodeEditParamForFormat.getTrulyPathFind(), 1, "");
        labelBeanList.setCodePathFind(addCodeEditParamForFormat.getTrulyPathFind());

        FunctionAddBean functionAddBeanTemp;

        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
        String faCodeLabelId = addCodeEditParamForFormat.getCommandAddRelatedAttribute() == null ?
                null : addCodeEditParamForFormat.getCommandAddRelatedAttribute().getCodeLabelId();

        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
            LabelBean labelBeanTemp;
            for (int i = 0; i < labelBeanList.getList().size(); i++) {
                labelBeanTemp = labelBeanList.getList().get(i);
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (null != functionAddBeanTemp.getCodeLabelId()) {
                        labelBeanList.getList().remove(i);
                        i = -1;
                    }
                }
            }
        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
            for (int i = 0; i < labelBeanList.getList().size(); i++) {
                if (labelBeanList.getList().get(i) instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanList.getList().get(i);
                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                        labelBeanList.getList().remove(i);
                        i = -1;
                    }
                }
            }
        }
        if (labelBeanList.getList().size() > 0) {
            labelBeanList.setEditOrNot(true);
        } else {
            labelBeanList.setEditOrNot(false);
        }
        return labelBeanList.isEditOrNot();
    }

    /**
     * 检测是否可以添加代码（检测添加到格式类型的功能拓展组件里面的功能拓展组件，且逐级往上的情况）
     *
     * @param addCodeEditParamForFormat
     * @return
     */
//    private boolean checkWhetherItMatchesForFormatToStepByStepToFindCorrespondingMark(AddCodeEditParamForFormat addCodeEditParamForFormat) {
//        boolean returnFlag = false;
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanListforCodeMarkShift_StepByStep(this.codeList, addCodeEditParamForFormat.getTrulyPathFind(),
//                        addCodeEditParamForFormat.getCommandAddRelatedAttribute(), 1);
//
//        if (updateCodeTemporaryVariable.isEditOrNot()) {//在功能拓展组件，里面的组件就已经找到合适的位置
//            returnFlag = true;
//
//        } else {//在功能拓展组件里面找不到合适的组件，看看本功能拓展组件（在格式模型里面的对应本功能拓展组件）是否合适
//
//            String codeMarkShiftCodeLabelId = addCodeEditParamForFormat.getCommandAddRelatedAttribute() == null ?
//                    null : addCodeEditParamForFormat.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getCodeLabelId() == null) {
//                    returnFlag = true;
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getCodeLabelId())) {
//                    returnFlag = true;
//                }
//            }
//        }
//        return returnFlag;
//    }

    /**
     * 检查是否可以添加代码（检查 添加到格式类型的功能拓展组件里面的功能拓展组件，如果对应的功能拓展组件没有符合的位置，直接添加到对应标记的情况）
     *
     * @param addCodeEditParamForFormat
     * @return
     */
//    private boolean checkWhetherItMatchesForFormatToCodeMarkShift_addToTheFirstCorrespondingMark(AddCodeEditParamForFormat addCodeEditParamForFormat) {
//        boolean returnFlag = false;
//        UpdateCodeTemporaryVariable codeGenerationTemporaryVariable = CodeGenerationStaticFunction
//                .getLastLabelBeanList(this.codeList, addCodeEditParamForFormat.getTrulyPathFind(), 1);
//        codeGenerationTemporaryVariable.setCodePathFind(addCodeEditParamForFormat.getTrulyPathFind());
//
//        FunctionAddBean functionAddBeanTemp;
//
//        //如果指定要添加到含有某个代码标签的功能拓展组件，在这里先把没有这个代码标签的通通去掉
//        String faCodeLabelId = addCodeEditParamForFormat.getCommandAddRelatedAttribute() == null ?
//                null : addCodeEditParamForFormat.getCommandAddRelatedAttribute().getCodeLabelId();
//
//        if (faCodeLabelId == null) {//如果要写入的代码没有对应代码标签，在获取到的功能拓展组件里面，去掉有代码标签的
//            LabelBean labelBeanTemp;
//            for (int i = 0; i < codeGenerationTemporaryVariable.getList().size(); i++) {
//                labelBeanTemp = codeGenerationTemporaryVariable.getList().get(i);
//                if (labelBeanTemp instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                    if (null != functionAddBeanTemp.getCodeLabelId()) {
//                        codeGenerationTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        } else {//如果要写入的代码有对应代码标签，把功能拓展组件里面，不是这个代码标签的功能拓展组件都去掉
//            for (int i = 0; i < codeGenerationTemporaryVariable.getList().size(); i++) {
//                if (codeGenerationTemporaryVariable.getList().get(i) instanceof FunctionAddBean) {
//                    functionAddBeanTemp = (FunctionAddBean) codeGenerationTemporaryVariable.getList().get(i);
//                    if (!(faCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
//                        codeGenerationTemporaryVariable.getList().remove(i);
//                        i = -1;
//                    }
//                }
//            }
//        }
//        if (codeGenerationTemporaryVariable.getList().size() > 0) {
//            codeGenerationTemporaryVariable.setEditOrNot(true);
//        } else {
//            codeGenerationTemporaryVariable.setEditOrNot(false);
//        }
//        if (codeGenerationTemporaryVariable.isEditOrNot()) {
//            returnFlag = true;
//        } else {
//            String codeMarkShiftCodeLabelId = addCodeEditParamForFormat.getCommandAddRelatedAttribute() == null ?
//                    null : addCodeEditParamForFormat.getCommandAddRelatedAttribute().getCodeLabelId();
//            if (codeMarkShiftCodeLabelId == null) {
//                if (this.getCodeLabelId() == null) {
//                    returnFlag = true;
//                }
//            } else {
//                if (codeMarkShiftCodeLabelId.equals(this.getCodeLabelId())) {
//                    returnFlag = true;
//                }
//            }
//        }
//        return returnFlag;
//    }

    /**
     * 更改值 init 1:t1 init: 1:f1；2：f1;3：t1
     *
     * @param updateCodeEditParam
     */
    public int updateValue(UpdateCodeEditParam updateCodeEditParam) {
        UpdateCodeTemporaryVariable labelBeanList = CodeGenerationStaticFunction.getLastLabelBeanList(this.codeList,
                updateCodeEditParam.getTrulyPathFind(), 0, "");
        int incrementalCursorPositionTemp = labelBeanList.getCursorDisplacement();

        LabelBean labelBeanTemp;
        for (int i = 0; i < labelBeanList.getList().size(); i++) {
            labelBeanTemp = labelBeanList.getList().get(i);
            labelBeanTemp.updateBeanValue(updateCodeEditParam.getOpratingLabel(), updateCodeEditParam.getUpdateParam());
        }
        return incrementalCursorPositionTemp;
    }

    /**
     * 删除值
     *
     * @param delCodeEditParam
     * @param operateOrNot
     * @return 期间游标变化增量
     */
    public int delCode(DelCodeEditParam delCodeEditParam, boolean operateOrNot) {
        int incrementalCursorPositionTemp = 0;

        if (operateOrNot == true) {// 可直接操作
            incrementalCursorPositionTemp = this.codeList.deleteCode(delCodeEditParam.getCodeSerialNumber()).getCursorDisplacement();

        } else {// 逐层定位添加

            UpdateCodeTemporaryVariable labelBeanList = CodeGenerationStaticFunction.getLastLabelBeanList(this.codeList,
                    delCodeEditParam.getTrulyPathFind(), 1, "");
            incrementalCursorPositionTemp = labelBeanList.getCursorDisplacement();
            FunctionAddBean functionAddBeanTemp;
            boolean flag = true;
            for (LabelBean labelBeanTemp : labelBeanList.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (flag == true) {
                        incrementalCursorPositionTemp = incrementalCursorPositionTemp
                                + functionAddBeanTemp.getCodeList().deleteCode(delCodeEditParam.getCodeSerialNumber()).getCursorDisplacement();
                        flag = false;
                    } else {
                        functionAddBeanTemp.getCodeList().deleteCode(delCodeEditParam.getCodeSerialNumber());
                    }
                }
            }
        }
        return incrementalCursorPositionTemp;
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public void updateBeanValue(BaseLableElement opratingLabel, Object updateParam) {
        // TODO Auto-generated method stub
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
