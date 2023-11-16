package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.service.vo.element.mark.MarkStaticMethod;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodeListLocation;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.StringBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FunctionAddBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.AdditionalSetMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.FunctionMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.ImportMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.InitMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.MainSetMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.mark.SetMarkBean;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.AddCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.DelCodeEditParamForMark;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForFormat;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.UpdateCodeEditParamForMark;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 格式面板继承的类
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public class TheFormatStatement extends CodeGenerateStatement {

    public TheFormatStatement(String statementFormat, String fileName) {
        // TODO Auto-generated constructor stub
        List<JSONObject> list = JsonUtil.restoreArrayByStr(statementFormat, JSONObject.class);
        generateContent(list, fileName);
    }

    @Override
    protected void generateContent(List<JSONObject> list, String fileName) {
        String elementType, labelType, markType;
        StringBean stringBean;
        if (list != null) {
            for (JSONObject temp : list) {
                elementType = BaseElement.getElementType(temp);

                if (ElementName.STRING_ELEMENT.equals(elementType)) {
                    stringBean = new StringBean(temp);
                    codeStatementBeanList.add(stringBean);

                } else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    labelType = BaseLableElement.getLabelType(temp);
                    generateLabelBean(labelType, temp, fileName);

                } else if (ElementName.MARK_ELEMENT.equals(elementType)) {
                    markType = BaseMarkElement.getMarkType(temp);
                    generateMarkBean(markType, temp);
                }
            }
        }
    }

    /**
     * 还原标签组件
     *
     * @param markType
     * @param temp
     */
    private void generateMarkBean(String markType, JSONObject temp) {

        if (MarkElementName.FUNCTION_MARK.equals(markType)) {
            FunctionMarkBean bean = new FunctionMarkBean(temp);
            codeStatementBeanList.add(bean);

        } else if (MarkElementName.SET_MARK.equals(markType)) {
            SetMarkBean bean = new SetMarkBean(temp);
            codeStatementBeanList.add(bean);

        } else if (MarkElementName.INIT_MARK.equals(markType)) {
            InitMarkBean bean = new InitMarkBean(temp);
            codeStatementBeanList.add(bean);

        } else if (MarkElementName.IMPORT_MARK.equals(markType)) {
            ImportMarkBean bean = new ImportMarkBean(temp);
            codeStatementBeanList.add(bean);

        } else if (MarkElementName.MAIN_SET_TYPE_MARK.equals(markType)) {
            MainSetMarkBean bean = new MainSetMarkBean(temp);
            codeStatementBeanList.add(bean);

        } else if (MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(markType)) {
            AdditionalSetMarkBean bean = new AdditionalSetMarkBean(temp);
            codeStatementBeanList.add(bean);
        }
    }

    /**
     * 直接添加代码到对应pathFind位置
     *
     * @param thanMarkElement
     * @param pathFind
     * @param codeLabelId
     * @param codeSerialNumber
     * @param codeOrdinal
     * @param codeStatement
     * @param addToLast
     * @param nextCodeSerialNumber
     * @return
     */
    public int addCodeToMark(BaseMarkElement thanMarkElement, PathFind pathFind,
                             String codeLabelId, int codeSerialNumber,
                             int codeOrdinal, String codeStatement,
                             boolean addToLast, Integer nextCodeSerialNumber, boolean inserNewLineOrNot) {
        int thisCursorPositionTemp = 0;
        boolean firFlag = true;
        if (pathFind.getMetaType() == PathFind.COMMAND_TYPE) {
            ArrayList<HarryingMark> list = getAllCorrespondingMark(thanMarkElement);
            AbstractMarkBean temp;
            if (list.size() > 0) {
                for (HarryingMark harryingMarkTemp1 : list) {
                    temp = (AbstractMarkBean) harryingMarkTemp1;
                    if (firFlag) {
                        thisCursorPositionTemp = temp.addCode(pathFind, codeSerialNumber, codeOrdinal, codeLabelId, codeStatement, addToLast, nextCodeSerialNumber, inserNewLineOrNot);
                        firFlag = false;
                    } else {
                        temp.addCode(pathFind, codeSerialNumber, codeOrdinal, codeLabelId, codeStatement, addToLast, nextCodeSerialNumber, inserNewLineOrNot);
                    }
                }
            } else {
                thisCursorPositionTemp = -1;
            }
        } else if (pathFind.getMetaType() == PathFind.FORMAT_TYPE) {
            UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = getFormatLabelBeanList(pathFind);
            thisCursorPositionTemp = updateCodeTemporaryVariableTemp.getCursorDisplacement();

            if (updateCodeTemporaryVariableTemp.isEditOrNot() == true) {//如果有这个对应的组件
                FunctionAddBean functionAddBeanTemp;
                for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
                    if (labelBeanTemp instanceof FunctionAddBean) {
                        functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                        if ((codeLabelId == null && functionAddBeanTemp.ifCodeLabelIdIsNull()) ||
                                (codeLabelId != null && codeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                            if (firFlag) {
                                thisCursorPositionTemp = thisCursorPositionTemp + functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                                firFlag = false;
                            } else {
                                functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                            }
                        }
                    }
                }
            } else {
                thisCursorPositionTemp = -1;
            }
        }
        return thisCursorPositionTemp;
    }


    /**
     * 把最后的组件都拿到（添加使用）
     *
     * @param pathFind
     * @param ii       都写0
     * @param codeList 写null
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    private UpdateCodeTemporaryVariable getLastLabelBeanList(PathFind pathFind, int ii, CodeList codeList) {
        UpdateCodeTemporaryVariable lastLabelBeanList = new UpdateCodeTemporaryVariable();// 记录最后返回的值
        PathFindCell pathFindCell = pathFind.getPathList().get(ii);

        if (ii == 0) {
            LabelBean labelBeanTemp;
            if (pathFind.getPathList().size() == 1) {// 是格式里面的某个组件 如：ti(更改值)
                // f1（添加，删除值）
                boolean flag = true;
//                BaseBean baseBeanTemp;
                for (BaseBean baseBeanTemp : this.codeStatementBeanList) {// 在格式模型内翻出对应的组件
//                for (int i = 0; i < this.codeStatementBeanList.size(); i++) {
//                    baseBeanTemp = this.codeStatementBeanList.get(i);
                    if (baseBeanTemp instanceof LabelBean) {// BaseBean
                        labelBeanTemp = (LabelBean) baseBeanTemp;
                        if (labelBeanTemp.match(pathFindCell) == true) {
                            lastLabelBeanList.add(labelBeanTemp);
                            lastLabelBeanList.setEditOrNot(true);
                            flag = false;
                        } else {
                            if (flag == true) {
                                lastLabelBeanList.setCursorDisplacement(
                                        lastLabelBeanList.getCursorDisplacement() + baseBeanTemp.getValueLength());
                            }
                        }
                    } else {
                        if (flag == true) {
                            lastLabelBeanList.setCursorDisplacement(
                                    lastLabelBeanList.getCursorDisplacement() + baseBeanTemp.getValueLength());
                        }
                    }
                }

            } else if (pathFind.getPathList().size() > 1) {// 在格式的某个功能拓展组件的组件里面，在功能拓展组件里面的代码翻出需要的组件
                boolean flag = true;
                // 如 f1 1:t1 f1 2:f1 3:f2 4:t1
                FunctionAddBean functionAddBeanTemp; // 在此之前，首先要找出对应的功能拓展组件
                UpdateCodeTemporaryVariable labelBeanListTemp = new UpdateCodeTemporaryVariable();
                int incrementalCursorPositionTemp = 0;
                for (BaseBean baseBeanTemp : this.codeStatementBeanList) {// 在格式模型内翻出对应的组件
                    if (baseBeanTemp instanceof FunctionAddBean) {// BaseBean
                        functionAddBeanTemp = (FunctionAddBean) baseBeanTemp;
                        if (functionAddBeanTemp.match(pathFindCell) == true) {
                            labelBeanListTemp = this.getLastLabelBeanList(pathFind, 1,
                                    functionAddBeanTemp.getCodeList());// 在f1里面找到需要的最后一个组件集合
                            lastLabelBeanList.addAll(labelBeanListTemp);// 都添加到返回面板上
                            if (labelBeanListTemp.isEditOrNot()) {
                                lastLabelBeanList.setEditOrNot(true);
                            }

                            if (flag == true) {
                                incrementalCursorPositionTemp = incrementalCursorPositionTemp
                                        + labelBeanListTemp.getCursorDisplacement();

                                flag = false;
                            }
                        } else {
                            if (flag == true) {
                                incrementalCursorPositionTemp = incrementalCursorPositionTemp
                                        + baseBeanTemp.getValueLength();
                            }
                        }
                    } else {
                        if (flag == true) {
                            incrementalCursorPositionTemp = incrementalCursorPositionTemp
                                    + baseBeanTemp.getValueLength();
                        }
                    }
                }
                lastLabelBeanList.setCursorDisplacement(incrementalCursorPositionTemp);
            }

        } else if (ii == pathFind.getPathList().size() - 1) {// 如果到了最后一个，直接获取需要的标签bean
            lastLabelBeanList = codeList.getLabelBean(pathFindCell);

        } else {
            UpdateCodeTemporaryVariable functionAddBeanlist = codeList.getLabelBean(pathFindCell),
                    nextLastLabelBeanList = new UpdateCodeTemporaryVariable();// 拿到这次需要的组件
            int cursorPositionTemp = functionAddBeanlist.getCursorDisplacement(), iiNext = ii + 1;

            CodeList nextCodeList;
            FunctionAddBean functionAddBeanTemp;
            boolean flag = true;
            for (LabelBean labelBeanTemp : functionAddBeanlist.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    nextCodeList = functionAddBeanTemp.getCodeList();
                    nextLastLabelBeanList = getLastLabelBeanList(pathFind, iiNext, nextCodeList);
                    lastLabelBeanList.addAll(nextLastLabelBeanList);
                    if (nextLastLabelBeanList.isEditOrNot()) {
                        lastLabelBeanList.setEditOrNot(true);
                    }

                    if (flag == true) {
                        cursorPositionTemp = cursorPositionTemp + nextLastLabelBeanList.getCursorDisplacement();
                        flag = false;
                    }
                }
            }
            lastLabelBeanList.setCursorDisplacement(cursorPositionTemp);
        }
        return lastLabelBeanList;
    }

    /**
     * 在格式中添加代码（只调用一次，然后获取光标位置）
     *
     * @param addCodeEditParamForFormat
     * @return 返回值小于0的话，就是这个代码文件不用添加这个值
     */
    public CodePaneModelGenerateCodeResponseParam addCodeToFormat(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
        UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = getFormatLabelBeanList(addCodeEditParamForFormat.getTrulyPathFind());

        int thisCursorPositionTemp = updateCodeTemporaryVariableTemp.getCursorDisplacement();

        if (updateCodeTemporaryVariableTemp.isEditOrNot() == true) {//如果有这个对应的组件
            CodeListLocation returnCodeListLocation;
            AddCodeTemporaryVariable temp;

            FunctionAddBean functionAddBeanTemp;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    temp = functionAddBeanTemp.addCodeToFormat(addCodeEditParamForFormat);
                    if (temp != null && temp.isEditOrNot()) {//有成功添加进去这个FunctionAddBean组件
                        returnCodeListLocation = new CodeListLocation();
                        thisCursorPositionTemp = thisCursorPositionTemp + temp.getCursorDisplacement();
                        codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
                        returnCodeListLocation.addCodePathFindForFormat(temp.getCodePathFind());
                        codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(returnCodeListLocation);
                    }
                }
            }
        } else {
            thisCursorPositionTemp = -1;
        }
        codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
        return codePaneModelGenerateCodeResponseParam;
    }

    /**
     * 检查要添加的功能是否符合添加到格式组件，是否可以添加到这里
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    public boolean checkWhetherItMatchesForFormat(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        boolean returnFlag = false;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = getFormatLabelBeanList(addCodeEditParamForFormat.getTrulyPathFind());// 获取格式中对应的那个组件

        if (updateCodeTemporaryVariableTemp.isEditOrNot() == true) {//如果有这个对应的组件
            boolean temp;
            FunctionAddBean functionAddBeanTemp;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    temp = functionAddBeanTemp.checkWhetherItMatchesForFormat(addCodeEditParamForFormat);
                    if (temp) {//有成功添加进去这个FunctionAddBean组件
                        returnFlag = true;
                        break;
                    }
                }
            }
        }
        return returnFlag;
    }

    /**
     * 从格式删除代码（只调用一次，然后获取光标位置）
     *
     * @param delCodeEditParamForFormat
     */
    public int delCodeFromFormat(DelCodeEditParamForFormat delCodeEditParamForFormat) {
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = getFormatLabelBeanList(delCodeEditParamForFormat.getTrulyPathFind());// 获取格式中对应的那个组件
        int thisCursorPositionTemp = updateCodeTemporaryVariable.getCursorDisplacement();
        if (updateCodeTemporaryVariable.isEditOrNot() == true) {
            FunctionAddBean functionAddBeanTemp;
            boolean flag = true;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (flag == true) {
                        thisCursorPositionTemp = thisCursorPositionTemp
                                + functionAddBeanTemp.delCode(delCodeEditParamForFormat,
                                delCodeEditParamForFormat.getTrulyPathFind().whetherToOperateForFormat());
                        flag = false;
                    } else {
                        functionAddBeanTemp.delCode(delCodeEditParamForFormat,
                                delCodeEditParamForFormat.getTrulyPathFind().whetherToOperateForFormat());
                    }
                }
            }
        } else {
            thisCursorPositionTemp = -1;
        }
        return thisCursorPositionTemp;
    }

    /**
     * 更新代码值到格式
     *
     * @param updateCodeEditParamForFormat
     */
    public int updateValueInFormat(UpdateCodeEditParamForFormat updateCodeEditParamForFormat) {
//        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = getLastLabelBeanList(updateCodeEditParamForFormat.getTrulyPathFind(), 0,
//                null);// 根据定位信息获取最后一个组件
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable;
        if (updateCodeEditParamForFormat.getTrulyPathFind().whetherToOperateForFormat()) {//如果是格式模板的组件
            updateCodeTemporaryVariable = getFormatLabelBeanList(updateCodeEditParamForFormat.getTrulyPathFind());// 获取格式中对应的那个组件
        } else {//添加到格式的代码面板的功能拓展组件里面的功能，对应的组件
            updateCodeTemporaryVariable = getLastLabelBeanList(updateCodeEditParamForFormat.getTrulyPathFind(), 0,
                    null);// 根据定位信息获取最后一个组件
        }

        int thisCursorPositionTemp = updateCodeTemporaryVariable.getCursorDisplacement();
        if (updateCodeTemporaryVariable.isEditOrNot() == true) {
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariable.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean == false) {
                    labelBeanTemp.updateBeanValue(updateCodeEditParamForFormat.getOpratingLabel(),
                            updateCodeEditParamForFormat.getUpdateParam());
                }
            }
        } else {
            thisCursorPositionTemp = -1;
        }
        return thisCursorPositionTemp;
    }

    /**
     * 添加代码到标记（仅限初始化和设置）
     *
     * @param addCodeEditParamForMark
     */
    public CodePaneModelGenerateCodeResponseParam addCodeToMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();

        ArrayList<HarryingMark> list = getAllCorrespondingMark(addCodeEditParamForMark.getThanMarkElement());

        AbstractMarkBean temp;
        int thisCursorPositionTemp = 0;
        if (list.size() > 0) {
            boolean firFlag = true;
            AddCodeTemporaryVariable addCodeTemporaryVariable;
            CodeListLocation codeListLocation;
            for (HarryingMark harryingMarkTemp1 : list) {
                temp = (AbstractMarkBean) harryingMarkTemp1;
                if (firFlag) {
                    addCodeTemporaryVariable = temp.addCode(addCodeEditParamForMark);
                    thisCursorPositionTemp = addCodeTemporaryVariable.getCursorDisplacement() + getCursorPositionBeforeTheMark(temp);
                    firFlag = false;

                    if (addCodeTemporaryVariable.isEditOrNot()) {
                        codeListLocation = new CodeListLocation();
                        codeListLocation.addCodePathFindForMark(addCodeEditParamForMark.getThanMarkElement(), addCodeTemporaryVariable.getCodePathFind());
                        codePaneModelGenerateCodeResponseParam.addCodeListLocationInfoList(codeListLocation);
                        codePaneModelGenerateCodeResponseParam.setEditOrNot(true);
                    }
                } else {
                    temp.addCode(addCodeEditParamForMark);
                }
            }
        } else {
            thisCursorPositionTemp = -1;
            codePaneModelGenerateCodeResponseParam.setEditOrNot(false);
        }
        codePaneModelGenerateCodeResponseParam.setIncrementalCursorPositionTemp(thisCursorPositionTemp);
        return codePaneModelGenerateCodeResponseParam;
    }

    /**
     * 检查要添加的代码是否可以添加到这里
     *
     * @param addCodeEditParamForMark
     * @return
     */
    public boolean checkWhetherItMatchesForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        boolean returnFlag = false;
        ArrayList<HarryingMark> list = getAllCorrespondingMark(addCodeEditParamForMark.getThanMarkElement());

        AbstractMarkBean temp;
        if (list.size() > 0) {
            for (HarryingMark harryingMarkTemp1 : list) {
                temp = (AbstractMarkBean) harryingMarkTemp1;
                if (temp.checkWhetherItMatchesForMark(addCodeEditParamForMark)) {
                    returnFlag = true;
                    break;
                }
            }
        }
        return returnFlag;
    }

    /**
     * 从标记更改代码值（新的调整方法）
     *
     * @param updateCodeEditParamForMark
     * @param theCorrespondingMark
     * @return
     */
    public UpdateCodeTemporaryVariable updateCodeFromMark(UpdateCodeEditParamForMark updateCodeEditParamForMark, AbstractMarkBean theCorrespondingMark) {
        int thisCursorPositionTemp = 0;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = theCorrespondingMark.updateValue(updateCodeEditParamForMark);//根据传入的参数在该标记进行代码更改，看看该标记里面的的代码是不是需要改这句代码的，
        if (updateCodeTemporaryVariable.isEditOrNot() == true) {
            thisCursorPositionTemp = thisCursorPositionTemp + getCursorPositionBeforeTheMark(theCorrespondingMark);
            updateCodeTemporaryVariable.setCursorDisplacement(thisCursorPositionTemp);
        }
        return updateCodeTemporaryVariable;
    }

    /**
     * 从标记删除代码（仅限初始化和设置）
     *
     * @param delCodeEditParamForMark
     */
    public UpdateCodeTemporaryVariable delCodeToMark(DelCodeEditParamForMark delCodeEditParamForMark, AbstractMarkBean theCorrespondingMark) {
        UpdateCodeTemporaryVariable temporaryVariable = theCorrespondingMark.delCode(delCodeEditParamForMark);
        return temporaryVariable;
    }

    /**
     * 获取到该标记之前的光标位置
     *
     * @param mark
     */
    @JSONField(deserialize = false, serialize = false)
    private int getCursorPositionBeforeTheMark(AbstractMarkBean mark) {
        int cursorPosition = 0;
        for (BaseBean beanTemp : this.codeStatementBeanList) {
            if (beanTemp instanceof AbstractMarkBean) {
                if (beanTemp == mark) {
                    break;
                } else {
                    cursorPosition = cursorPosition + beanTemp.getValueLength();
                }
            } else {
                cursorPosition = cursorPosition + beanTemp.getValueLength();
            }
        }
        return cursorPosition;
    }

    /**
     * 获取所有对应的标记（增删改用这个）
     *
     * @param thanMarkElement
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public ArrayList<HarryingMark> getAllCorrespondingMark(BaseMarkElement thanMarkElement) {
        ArrayList<HarryingMark> allMarkScutcheon = getAllMarkBean();// 获取所有标记
        ArrayList<HarryingMark> list = MarkStaticMethod
                .getHarryingMarkWithCorrespondingQualifyingMarkList(thanMarkElement, allMarkScutcheon);
        return list;
    }

    /**
     * 获取所有的标记
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    private ArrayList<HarryingMark> getAllMarkBean() {
        ArrayList<HarryingMark> list = new ArrayList<>();
        for (BaseBean beanTemp : this.codeStatementBeanList) {
            if (beanTemp instanceof AbstractMarkBean) {
                list.add((AbstractMarkBean) beanTemp);
            }
        }
        return list;
    }

    /**
     * 添加业务逻辑代码
     *
     * @param addCodeEditParamForMark
     */
//    public int addBusinessLogicCode(AddCodeEditParamForMark addCodeEditParamForMark) {
//        int thisCursorPositionTemp = 0;
//        boolean flag = true, editOrNot = false;
//        FunctionMarkBean functionMarkBeanTemp;
//        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
//            if (baseBeanTemp instanceof FunctionMarkBean) {
//                functionMarkBeanTemp = (FunctionMarkBean) baseBeanTemp;
//                if (flag == true) {
//                    thisCursorPositionTemp = thisCursorPositionTemp
//                            + functionMarkBeanTemp.addCode(addCodeEditParamForMark);
//                    flag = false;
//                    editOrNot = true;
//                } else {
//                    functionMarkBeanTemp.addCode(addCodeEditParamForMark);
//
//                }
//            } else {
//                if (flag == true) {
//                    thisCursorPositionTemp = thisCursorPositionTemp + baseBeanTemp.getValueLength();
//                }
//            }
//        }
//        if (editOrNot == false) {
//            thisCursorPositionTemp = -1;
//        }
//        return thisCursorPositionTemp;
//    }

    /**
     * 更改业务代码的值
     *
     * @param updateCodeEditParamForMark
     */
//    public int updateValueInBusinessLogicCode(UpdateCodeEditParamForMark updateCodeEditParamForMark) {
//        int thisCursorPositionTemp = 0;
//        FunctionMarkBean functionMarkBeanTemp;
//        CodeGenerationTemporaryVariable codeGenerationTemporaryVariable;
//        boolean flag = true, editOrNot = false;
//        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
//            if (baseBeanTemp instanceof FunctionMarkBean) {
//                functionMarkBeanTemp = (FunctionMarkBean) baseBeanTemp;
//                if (flag == true) {
//                    codeGenerationTemporaryVariable = functionMarkBeanTemp.updateValue(updateCodeEditParamForMark);
//                    thisCursorPositionTemp = thisCursorPositionTemp
//                            + codeGenerationTemporaryVariable.getCursorDisplacement();
//                    flag = false;
//                    editOrNot = codeGenerationTemporaryVariable.isEditOrNot();
//                } else {
//                    functionMarkBeanTemp.updateValue(updateCodeEditParamForMark);
//
//                }
//            } else {
//                if (flag == true) {
//                    thisCursorPositionTemp = thisCursorPositionTemp + baseBeanTemp.getValueLength();
//                }
//            }
//        }
//        if (editOrNot == false) {
//            thisCursorPositionTemp = -1;
//        }
//        return thisCursorPositionTemp;
//    }

    /**
     * 删除业务代码
     *
     * @param delCodeEditParamForMark
     */
//    public int delCodeFromBusinessLogicCode(DelCodeEditParamForMark delCodeEditParamForMark) {
//        int thisCursorPositionTemp = 0;
//        FunctionMarkBean functionMarkBeanTemp;
//        boolean flag = true, editOrNot = false;
//        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
//            if (baseBeanTemp instanceof FunctionMarkBean) {
//                functionMarkBeanTemp = (FunctionMarkBean) baseBeanTemp;
//                if (flag == true) {
//                    thisCursorPositionTemp = thisCursorPositionTemp
//                            + functionMarkBeanTemp.delCode(delCodeEditParamForMark).getCursorDisplacement();
//                    flag = false;
//                    editOrNot = true;
//                } else {
//                    functionMarkBeanTemp.delCode(delCodeEditParamForMark);
//
//                }
//            } else {
//                if (flag == true) {
//                    thisCursorPositionTemp = thisCursorPositionTemp + baseBeanTemp.getValueLength();
//                }
//            }
//        }
//        if (editOrNot == false) {
//            thisCursorPositionTemp = -1;
//        }
//        return thisCursorPositionTemp;
//    }

    /**
     * 检查代码面板有没有业务逻辑的标记
     *
     * @return
     */
    public boolean checkHaveBusinessLogicMark() {
        boolean flag = false;
        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
            if (baseBeanTemp instanceof FunctionMarkBean) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int addCodeToFirstLayerFormat(
            int codeSerialNumber, int codeOrdinal,
            String codeStatement,
            boolean inserNewLineOrNot,
            PathFind pathFind,
            CommandAddRelatedAttribute commandAddRelatedAttribute) {
        UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = getFormatLabelBeanList(pathFind);
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        int thisCursorPositionTemp = updateCodeTemporaryVariableTemp.getCursorDisplacement();
        if (updateCodeTemporaryVariableTemp.isEditOrNot() == true) {//如果有这个对应的组件
            FunctionAddBean functionAddBeanTemp;
            boolean flag = true;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if (flag) {
                        if ((codeLabelId == null && functionAddBeanTemp.ifCodeLabelIdIsNull()) ||
                                (codeLabelId != null && codeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                            thisCursorPositionTemp = thisCursorPositionTemp + functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                            flag = false;
                        }
                    } else {
                        if ((codeLabelId == null && functionAddBeanTemp.ifCodeLabelIdIsNull()) ||
                                (codeLabelId != null && codeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                            functionAddBeanTemp.addCode(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot);
                        }
                    }
                }
            }
        } else {
            thisCursorPositionTemp = -1;
        }
        return thisCursorPositionTemp;
    }

    public boolean checkAddCodeToFirstLayerFormat(
            PathFind pathFind,
            CommandAddRelatedAttribute commandAddRelatedAttribute) {
        boolean flag = false;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariableTemp = getFormatLabelBeanList(pathFind);
        String codeLabelId = commandAddRelatedAttribute.getCodeLabelId();
        if (updateCodeTemporaryVariableTemp.isEditOrNot() == true) {//如果有这个对应的组件
            FunctionAddBean functionAddBeanTemp;
            for (LabelBean labelBeanTemp : updateCodeTemporaryVariableTemp.getList()) {
                if (labelBeanTemp instanceof FunctionAddBean) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
                    if ((codeLabelId == null && functionAddBeanTemp.ifCodeLabelIdIsNull()) ||
                            (codeLabelId != null && codeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取在格式模型的组件（仅是第1层，格式模型里面的，不拿功能拓展标签里面对应的最后一个组件）
     *
     * @param pathFind
     */
    @JSONField(deserialize = false, serialize = false)
    public UpdateCodeTemporaryVariable getFormatLabelBeanList(PathFind pathFind) {
        boolean flag = true;

        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = new UpdateCodeTemporaryVariable();
        PathFindCell pathFindCell = pathFind.getPathList().get(0);

        LabelBean labelBeanTemp;
        for (BaseBean baseBeanTemp : this.codeStatementBeanList) {
            if (baseBeanTemp instanceof LabelBean) {
                labelBeanTemp = (LabelBean) baseBeanTemp;
                if (labelBeanTemp.match(pathFindCell) == true) {
                    updateCodeTemporaryVariable.add(labelBeanTemp);
                    updateCodeTemporaryVariable.setEditOrNot(true);
                    flag = false;

                } else {
                    if (flag == true) {
                        updateCodeTemporaryVariable
                                .setCursorDisplacement(updateCodeTemporaryVariable.getCursorDisplacement()
                                        + baseBeanTemp.getValueLength());
                    }
                }
            } else {
                if (flag == true) {
                    updateCodeTemporaryVariable.setCursorDisplacement(
                            updateCodeTemporaryVariable.getCursorDisplacement() + baseBeanTemp.getValueLength());
                }
            }
        }
        return updateCodeTemporaryVariable;
    }

    /**
     * 添加引入代码
     *
     * @param thanImportMarkElement
     * @param importCode
     */
    public void addImportCode(ImportMarkElement thanImportMarkElement, ImportCode importCode) {
        ArrayList<HarryingMark> list = getAllCorrespondingMark(thanImportMarkElement);
        if (list != null) {
            ImportMarkBean importMarkBean;
            for (HarryingMark harryingMark: list) {
                importMarkBean = (ImportMarkBean) harryingMark;
                importMarkBean.addImportCode(importCode.getCode());
            }
        }
    }

    /**
     * 删除引入代码
     *
     * @param thanImportMarkElement
     * @param importCode
     */
    public void delImportCode(ImportMarkElement thanImportMarkElement, ImportCode importCode) {
        ArrayList<HarryingMark> list = getAllCorrespondingMark(thanImportMarkElement);
        if (list != null) {
            ImportMarkBean importMarkBean;
            for (HarryingMark harryingMark: list) {
                importMarkBean = (ImportMarkBean) harryingMark;
                importMarkBean.delImportCode(importCode.getCode());
            }
        }
    }


}
