package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FunctionAddBean;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import java.util.ArrayList;

/**
 * 该类填写生成代码所需的静态方法
 *
 * @author admin
 */
public class CodeGenerationStaticFunction {

    /**
     *
     */
    private static final long serialVersionUID = -5995701523843612886L;

    /**
     * 把最后一个组件都拿到（添加、更改、删除代码时拿到）
     *
     * @param codeList
     * @param pathFind
     * @param ii       命令类型调用该方法这里一律传0，格式类型调用该方法传1
     * @param indent   一般直接传""即可，添加代码的时候调用此方法才传缩进符
     * @return
     */
    public static UpdateCodeTemporaryVariable getLastLabelBeanList(CodeList codeList, PathFind pathFind,
                                                                   int ii, String indent) {
        int cursorPositionTemp = 0;
        UpdateCodeTemporaryVariable lastLabelBeanList = new UpdateCodeTemporaryVariable();
        PathFindCell pathFindCell = pathFind.getPathList().get(ii);

        if (ii == pathFind.getPathList().size() - 1) {// 如果到了最后一个，直接获取需要的标签bean
            lastLabelBeanList = codeList.getLabelBean(pathFindCell);

            if (LabelElementName.FUNCTION_ADD.equals(pathFindCell.getOpratingLabel().getLabelType())) {
                FunctionAddBean functionAddBeanTemp;
                String indentStr;
                for (LabelBean labelBean : lastLabelBeanList.getList()) {
                    if (labelBean instanceof FunctionAddBean) {
                        functionAddBeanTemp = (FunctionAddBean) labelBean;
                        if (indent != null && (!indent.equals("")) ||
                                functionAddBeanTemp.getIndent() != null && (!functionAddBeanTemp.getIndent().equals(""))
                        ) {
                            if (indent == null) {
                                indentStr = functionAddBeanTemp.getIndent();

                            } else if (functionAddBeanTemp.getIndent() == null) {
                                indentStr = indent;

                            } else {
                                indentStr = indent + functionAddBeanTemp.getIndent();
                            }
                            lastLabelBeanList.putIndent(functionAddBeanTemp.getFaId(), indentStr);
                        }
                    }
                }
            }
//            lastLabelBeanList.setCodePathFind(pathFind);
        } else {//只要ii不到最后一个，内部一直执行本方法
            int iiNext = ii + 1;

            CodeList nextCodeList;
            FunctionAddBean functionAddBeanTemp;
            String indentStr = "";
            UpdateCodeTemporaryVariable functionAddBeanlist = codeList.getLabelBean(pathFindCell),
                    nextLastLabelBeanList;
            cursorPositionTemp = functionAddBeanlist.getCursorDisplacement();
            boolean flag = true;
            for (LabelBean labelBeanTemp : functionAddBeanlist.getList()) {//获取第ii个功能拓展组件的对应在代码模型中的标签
                if (LabelElementName.FUNCTION_ADD.equals(labelBeanTemp.getLabelType())) {
                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;

                    if (indent != null && (!indent.equals("")) ||
                            functionAddBeanTemp.getIndent() != null && (!functionAddBeanTemp.getIndent().equals(""))
                    ) {
                        if (indent == null) {
                            indentStr = functionAddBeanTemp.getIndent();

                        } else if (functionAddBeanTemp.getIndent() == null) {
                            indentStr = indent;

                        } else {
                            indentStr = indent + functionAddBeanTemp.getIndent();
                        }
                    }

                    nextCodeList = functionAddBeanTemp.getCodeList();
                    if (flag == true) {
                        nextLastLabelBeanList = getLastLabelBeanList(nextCodeList, pathFind, iiNext, indentStr);
                        cursorPositionTemp = cursorPositionTemp + nextLastLabelBeanList.getCursorDisplacement();
                        lastLabelBeanList
                                .setCursorDisplacement(lastLabelBeanList.getCursorDisplacement() + cursorPositionTemp);
                        flag = false;
                    } else {
                        nextLastLabelBeanList = getLastLabelBeanList(nextCodeList, pathFind, iiNext, indentStr);
                    }
                    lastLabelBeanList.addAll(nextLastLabelBeanList);
                    if (nextLastLabelBeanList.isEditOrNot()) {
                        lastLabelBeanList.setEditOrNot(true);
                    }
                }
            }
        }
        return lastLabelBeanList;
    }

//    /**
//     * 把最后一个组件都拿到（更改、删除代码时拿到）
//     *
//     * @param codeList
//     * @param pathFind
//     * @param ii                         命令类型调用该方法这里一律传0，命令类型调用该方法传1
//     * @param lastFunctionAddCodeLabelId 更改或者删除时使用 填入最后一个功能拓展组件的CodeLabelId，不需要填写null
//     * @param codeLabelIdIndex           填0则检测最后一个（一般删除时填0），填1则检测倒数第二个(一般更改时填1)
//     * @return
//     */
//    public static UpdateCodeTemporaryVariable getDesignatedLastLabelBeanList(CodeList codeList, PathFind pathFind,
//                                                                             int ii, String lastFunctionAddCodeLabelId, int codeLabelIdIndex) {
//        int cursorPositionTemp = 0;
//        UpdateCodeTemporaryVariable lastLabelBeanList = new UpdateCodeTemporaryVariable();
//        PathFindCell pathFindCell = pathFind.getPathList().get(ii);
//
//        if (ii == pathFind.getPathList().size() - 1) {// 如果到了最后一个，直接获取需要的标签bean
////            lastLabelBeanList = codeList.getLabelBean(pathFindCell);
//
//            if (lastFunctionAddCodeLabelId == null){
//                lastLabelBeanList = codeList.getLabelBean(pathFindCell);
//
//            }else if(lastFunctionAddCodeLabelId != null
//                    && codeLabelIdIndex == 0 &&
//                    ii == pathFind.getPathList().size() - 1){
//                FunctionAddBean functionAddBeanTemp;
//                for (LabelBean labelBeanTemp : lastLabelBeanList.getList()) {//获取第ii个功能拓展组件的对应在代码模型中的标签
//                    if (LabelElementName.FUNCTION_ADD.equals(labelBeanTemp.getLabelType())) {
//                        functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                        if (lastFunctionAddCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId())) {
//                            lastLabelBeanList = codeList.getLabelBean(pathFindCell);
//                        }
//                    }
//                }
//            }
//        } else {//只要ii不到最后一个，内部一直执行本方法
//            int iiNext = ii + 1;
//
//            CodeList nextCodeList;
//            FunctionAddBean functionAddBeanTemp;
//            UpdateCodeTemporaryVariable functionAddBeanlist = codeList.getLabelBean(pathFindCell),
//                    nextLastLabelBeanList;
//            cursorPositionTemp = functionAddBeanlist.getCursorDisplacement();
//            boolean flag = true;
//            for (LabelBean labelBeanTemp : functionAddBeanlist.getList()) {//获取第ii个功能拓展组件的对应在代码模型中的标签
//                if (LabelElementName.FUNCTION_ADD.equals(labelBeanTemp.getLabelType())) {
//                    functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//
//                    if (lastFunctionAddCodeLabelId == null || (lastFunctionAddCodeLabelId != null &&
//                            codeLabelIdIndex == 1 &&
//                            ii == pathFind.getPathList().size() - 2&&
//                            lastFunctionAddCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId()))) {//倒数第二个
//                            nextCodeList = functionAddBeanTemp.getCodeList();
//                            if (flag == true) {
//                                nextLastLabelBeanList = getDesignatedLastLabelBeanList(nextCodeList, pathFind,
//                                        iiNext, lastFunctionAddCodeLabelId, codeLabelIdIndex);
//                                cursorPositionTemp = cursorPositionTemp + nextLastLabelBeanList.getCursorDisplacement();
//                                lastLabelBeanList
//                                        .setCursorDisplacement(lastLabelBeanList.getCursorDisplacement() + cursorPositionTemp);
//                                flag = false;
//                            } else {
//                                nextLastLabelBeanList = getDesignatedLastLabelBeanList(nextCodeList, pathFind,
//                                        iiNext, lastFunctionAddCodeLabelId, codeLabelIdIndex);
//                            }
//                            lastLabelBeanList.addAll(nextLastLabelBeanList);
//                            if (nextLastLabelBeanList.isEditOrNot()) {
//                                lastLabelBeanList.setEditOrNot(true);
//                            }
//                    }
//                }
//            }
//        }
//        return lastLabelBeanList;
//    }

//    /**
//     * 把最后一个组件都拿到（添加代码时拿到）(有代码移位的需要才用这个方法，另外使用新方法，和无需代码移位的对应获取步骤分开，避免思路混淆)
//     * （该方法只有在codeMarkShift不为空，且是逐步移位才能生效，且本方法只在添加代码的时候才用）
//     *
//     * @param codeList
//     * @param pathFind
//     * @param commandAddRelatedAttribute 代码移位参数
//     * @param ii                         命令类型调用该方法这里一律传0，命令类型调用该方法传1
//     * @return
//     */
//    public static UpdateCodeTemporaryVariable getLastLabelBeanListforCodeMarkShift_StepByStep(CodeList codeList, PathFind pathFind,
//                                                                                              CommandAddRelatedAttribute commandAddRelatedAttribute, int ii) {
//        UpdateCodeTemporaryVariable lastLabelBeanList = new UpdateCodeTemporaryVariable();
//        if (commandAddRelatedAttribute != null &&
//                CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE == commandAddRelatedAttribute.getOtherAttribute()) {//再判断一次，确定是需要进行移位操作，且是要逐步移位才进行
//            PathFindCell pathFindCell = pathFind.getPathList().get(ii);
//
//            int cursorPositionTemp = 0;
//            String codeMarkShiftCodeLabelId = commandAddRelatedAttribute.getCodeLabelId();
//
//            if (ii == pathFind.getPathList().size() - 1) {// 在最后一个功能拓展标签所做的处理
//                UpdateCodeTemporaryVariable tempLastLabelBeanList = codeList.getLabelBean(pathFindCell);
//                lastLabelBeanList.setCursorDisplacement(tempLastLabelBeanList.getCursorDisplacement());
//
//                FunctionAddBean functionAddBeanTemp;
//                for (LabelBean labelBeanTemp : tempLastLabelBeanList.getList()) {
//                    //拿到最后一个pathFindCell对应的所有功能拓展组件时，只载入和codeMarkShift.getCodeLabelId()一样的组件
//                    if (labelBeanTemp instanceof FunctionAddBean) {
//                        functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                        if (codeMarkShiftCodeLabelId == null) {
//                            if (functionAddBeanTemp.getCodeLabelId() == null) {
//                                lastLabelBeanList.add(functionAddBeanTemp);
//                                lastLabelBeanList.setCodePathFind(pathFind);
//                                lastLabelBeanList.setEditOrNot(true);
//                            }
//                        } else {
//                            if (codeMarkShiftCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId())) {
//                                lastLabelBeanList.add(functionAddBeanTemp);
//                                lastLabelBeanList.setCodePathFind(pathFind);
//                                lastLabelBeanList.setEditOrNot(true);
//                            }
//                        }
//                    }
//                }
//            } else {//只要ii不到最后一个，内部一直执行本方法
//                int iiNext = ii + 1;
//
//                CodeList nextCodeList;
//                FunctionAddBean functionAddBeanTemp;
//
//                //拿到对应的pathFindCell中所有的功能拓展组件
//                UpdateCodeTemporaryVariable functionAddBeanlist = codeList.getLabelBean(pathFindCell),
//                        nextLastLabelBeanList;
//                cursorPositionTemp = functionAddBeanlist.getCursorDisplacement();
//                boolean flag = true;
//                for (LabelBean labelBeanTemp : functionAddBeanlist.getList()) {//获取第ii个功能拓展组件的对应在代码模型中的标签
//                    if (labelBeanTemp instanceof FunctionAddBean) {
//                        functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                        nextCodeList = functionAddBeanTemp.getCodeList();
//                        if (flag == true) {
//                            nextLastLabelBeanList = getLastLabelBeanListforCodeMarkShift_StepByStep(nextCodeList, pathFind, commandAddRelatedAttribute, iiNext);
//
//                            cursorPositionTemp = cursorPositionTemp + nextLastLabelBeanList.getCursorDisplacement();
//                            lastLabelBeanList
//                                    .setCursorDisplacement(lastLabelBeanList.getCursorDisplacement() + cursorPositionTemp);
//                            flag = false;
//                        } else {
//                            nextLastLabelBeanList = getLastLabelBeanListforCodeMarkShift_StepByStep(nextCodeList, pathFind, commandAddRelatedAttribute, iiNext);
//                        }
//                        lastLabelBeanList.addAll(nextLastLabelBeanList);
//                        if (nextLastLabelBeanList.isEditOrNot()) {
//                            lastLabelBeanList.setEditOrNot(true);
//                            lastLabelBeanList.setCodePathFind(nextLastLabelBeanList.getCodePathFind());
//                        }
//                    }
//                }
//
//
//                if (lastLabelBeanList.getList().size() == 0) {//在上一级没有拿到对应对应符合条件的功能拓展标签
//
//                    for (LabelBean labelBeanTemp : functionAddBeanlist.getList()) {//在这一层查找对应的功能拓展组件有没有合适的标记，有的话，插入到这里
//                        if (labelBeanTemp instanceof FunctionAddBean) {
//                            functionAddBeanTemp = (FunctionAddBean) labelBeanTemp;
//                            if (codeMarkShiftCodeLabelId == null) {
//                                if (functionAddBeanTemp.getCodeLabelId() == null) {
//                                    lastLabelBeanList.add(functionAddBeanTemp);
//                                }
//                            } else {
//                                if (codeMarkShiftCodeLabelId.equals(functionAddBeanTemp.getCodeLabelId())) {
//                                    lastLabelBeanList.add(functionAddBeanTemp);
//                                }
//                            }
//                        }
//                    }
//                    //如果在这一层有找到合适的功能拓展组件，记下这一层的PathFind
//                    if (lastLabelBeanList.getList().size() > 0) {
//                        PathFind codePathFind = PathFind.getOnALayerOfPathFind(pathFind, ii);
//                        lastLabelBeanList.setCodePathFind(codePathFind);
//                        lastLabelBeanList.setEditOrNot(true);
//                    } else if (lastLabelBeanList.getList().size() == 0) {
//                        lastLabelBeanList.setEditOrNot(false);
//                    }
//                }
//
//            }
//        }
//        return lastLabelBeanList;
//    }

//    public static void setValue(CodeGenerationComponentInterface codeGenerationComponent, Object updateParam) {
//
//        if (PathFind.COMMAND_TYPE == codeGenerationComponent.getPathFind().getMetaType()) {
//
//            UpdateCodeEditParamForMark updateCodeEditParam = new UpdateCodeEditParamForMark();
//            updateCodeEditParam.setTrulyPathFind(codeGenerationComponent.getPathFind());
//            updateCodeEditParam.setCodeSerialNumber(codeGenerationComponent.getCodeSerialNumber());
//            updateCodeEditParam.setOpratingLabel(codeGenerationComponent.getControlElement());
//            updateCodeEditParam.setUpdateParam(updateParam);
//            OpratingContainerInterface opratingContainer = codeGenerationComponent.getOperatingComponentPlacePane()
//                    .getOpratingContainer();
//
//            opratingContainer.updateValue(updateCodeEditParam);
//
//        } else if (PathFind.FORMAT_TYPE == codeGenerationComponent.getPathFind().getMetaType()) {
//
//            UpdateCodeEditParamForFormat updateCodeEditParam = new UpdateCodeEditParamForFormat();
//            updateCodeEditParam.setCodeSerialNumber(0);
//            updateCodeEditParam.setTrulyPathFind(codeGenerationComponent.getPathFind());
//            updateCodeEditParam.setOpratingLabel(codeGenerationComponent.getControlElement());
//            updateCodeEditParam.setUpdateParam(updateParam);
//
//            AbstractOperatingPane operatingComponentPlacePane = codeGenerationComponent.getOperatingComponentPlacePane();
//            OpratingContainerInterface opratingContainer = operatingComponentPlacePane.getOpratingContainer();
//            opratingContainer.updateValue(updateCodeEditParam);
//        }
//    }

    public static void setValue(CodeGenerationComponentInterface codeGenerationComponent, int paneType, Object updateParam) {
        OpratingContainerInterface opratingContainer = codeGenerationComponent.getOperatingComponentPlacePane()
                .getOpratingContainer();
        if (opratingContainer != null) {
            opratingContainer.updateValue(codeGenerationComponent, paneType, updateParam);
        }
    }


    /**
     * 检查有没有该标记
     *
     * @param list
     * @param theMark
     * @return
     */
    public static boolean checkHaveTheMarkInList(ArrayList<HarryingMark> list, HarryingMark theMark) {
        boolean flag = false;
        for (HarryingMark harryingMarkTemp : list) {
            if (harryingMarkTemp == theMark) {
                flag = true;
            }
        }
        return flag;
    }

}



