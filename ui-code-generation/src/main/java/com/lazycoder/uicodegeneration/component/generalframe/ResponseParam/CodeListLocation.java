package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.MarkElementDeserializer;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.PathFindListDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定位CodeList类在代码模型中的参数
 */
@Data
@NoArgsConstructor
public class CodeListLocation {

    @JSONField(ordinal = 1)
    private int metaType = PathFind.COMMAND_TYPE;

    /**
     * 用于定位符合特征的标记的标记元素
     */
    @JSONField(deserializeUsing = MarkElementDeserializer.class, ordinal = 2)
    private BaseMarkElement thanMarkElement = null;

    /**
     * 符合特征的标记中，所有的CodeList的寻址字符串的集合
     */
    @JSONField(deserializeUsing = PathFindListDeserializer.class, ordinal = 3)
    private ArrayList<PathFind> codePathFindList = new ArrayList<>();


    /**
     * @param markElement
     * @param codePathFind
     */
    public void addCodePathFindForMark(BaseMarkElement markElement, PathFind codePathFind) {
        this.metaType = PathFind.COMMAND_TYPE;
        if (this.thanMarkElement == null) {
            this.thanMarkElement = markElement;
            boolean flag = false;
            for (PathFind pathFindTemp : codePathFindList) {
                if (PathFind.same(pathFindTemp, codePathFind)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                codePathFindList.add(codePathFind);
            }
        } else if (this.thanMarkElement.matchThan(markElement)) {
            boolean flag = false;
            for (PathFind pathFindTemp : codePathFindList) {
                if (PathFind.same(pathFindTemp, codePathFind)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                codePathFindList.add(codePathFind);
            }
        }
    }

    /**
     * @param codePathFind
     */
    public void addCodePathFindForFormat(PathFind codePathFind) {
        this.metaType = PathFind.FORMAT_TYPE;
        boolean flag = false;
        for (PathFind pathFindTemp : codePathFindList) {
            if (PathFind.same(pathFindTemp, codePathFind)) {
                flag = true;
                break;
            }
        }
        if (flag == false) {
            codePathFindList.add(codePathFind);
        }
    }

    public static boolean compare(CodeListLocation codeListLocation1, CodeListLocation codeListLocation2) {
        boolean flag = false;
        if (codeListLocation1.metaType == codeListLocation2.getMetaType()) {
            if (codeListLocation1.metaType == PathFind.COMMAND_TYPE) {
                if (codeListLocation1.thanMarkElement != null &&
                        codeListLocation1.thanMarkElement.matchThan(codeListLocation2.thanMarkElement) &&
                        codeListLocation1.codePathFindList.size() == codeListLocation2.codePathFindList.size()) {
                    boolean pathFindCompareFlag = true;
                    for (PathFind pathFind1 : codeListLocation1.codePathFindList) {
                        if (PathFind.checkContain(codeListLocation2.codePathFindList, pathFind1) == false) {
                            pathFindCompareFlag = false;
                            break;
                        }
                    }
                    if (pathFindCompareFlag == true) {
                        flag = true;
                    }
                }
            } else if (codeListLocation1.metaType == PathFind.FORMAT_TYPE) {
                if (codeListLocation1.codePathFindList.size() == codeListLocation2.codePathFindList.size()) {
                    boolean pathFindCompareFlag = true;
                    for (PathFind pathFind1 : codeListLocation1.codePathFindList) {
                        if (PathFind.checkContain(codeListLocation2.codePathFindList, pathFind1) == false) {
                            pathFindCompareFlag = false;
                            break;
                        }
                    }
                    if (pathFindCompareFlag == true) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public static CodeListLocation generateCodeListLocation(CodeListLocation sourceCodeListLocation, PathFindCell pathFindCell) {
        CodeListLocation returnCodeListLocation = new CodeListLocation();
        returnCodeListLocation.metaType = sourceCodeListLocation.metaType;
        returnCodeListLocation.thanMarkElement = sourceCodeListLocation.thanMarkElement;
        PathFind pathFindTemp;
        for (PathFind sourcePathFind : sourceCodeListLocation.codePathFindList) {
            pathFindTemp = new PathFind(sourcePathFind);
            pathFindTemp.getPathList().add(pathFindCell);
            returnCodeListLocation.codePathFindList.add(pathFindTemp);
        }
        return returnCodeListLocation;
    }

}
