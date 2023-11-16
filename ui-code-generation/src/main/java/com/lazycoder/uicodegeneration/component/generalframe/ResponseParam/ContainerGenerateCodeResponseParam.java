package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能容器生成代码时，对应的代码所在位置
 */
@Data
@NoArgsConstructor
public class ContainerGenerateCodeResponseParam {

    /**
     * 该代码面板中所有的CodeList的定位信息
     */
    @JSONField(deserializeUsing = CodePaneModelGenerateCodeResponseParamListDeserializer.class)
    private ArrayList<CodePaneModelGenerateCodeResponseParam> codeListLocationInfoList = new ArrayList<>();


    @JSONField(deserialize = false, serialize = false)
    public void clearAllInfo() {
        codeListLocationInfoList.clear();
    }


    /**
     * 记录该添加代码返回的信息
     *
     * @param codePaneModelGenerateCodeResponseParam
     * @return
     */
    public void addCodePaneForCodeFormatFlagParam(CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam) {
        codeListLocationInfoList.add(codePaneModelGenerateCodeResponseParam);
//        boolean flag = false;
//        for (CodePaneModelGenerateCodeResponseParam temp : codeListLocationInfoList) {
//            if (CodeFormatFlagParam.compare(temp.getCodeFormatFlagParam(), codePaneModelGenerateCodeResponseParam.getCodeFormatFlagParam())) {
//                //如果本实例中已经包含了codePaneModelGenerateCodeResponseParam对应代码面板的实例，把codePaneModelGenerateCodeResponseParam里面独有的数据都添加到该实例中
//                flag = true;
//                ArrayList<CodeListLocation> sourceCodeListLocationInfoList = codePaneModelGenerateCodeResponseParam.getCodeListLocationInfoList();
//                for (CodeListLocation sourceCodeListLocation : sourceCodeListLocationInfoList) {
//                    if (temp.checkContain(sourceCodeListLocation) == false) {
//                        temp.addCodeListLocationInfoList(sourceCodeListLocation);
//                    }
//                }
//                break;
//            }
//        }
//        if (flag == false) {
//            codeListLocationInfoList.add(codePaneModelGenerateCodeResponseParam);
//        }
    }

    public static ContainerGenerateCodeResponseParam generateContainerGenerateCodeResponseParam
            (ContainerGenerateCodeResponseParam sourceContainerGenerateCodeResponseParam, PathFindCell pathFindCell) {

        ContainerGenerateCodeResponseParam returnContainerGenerateCodeResponseParam = new ContainerGenerateCodeResponseParam();
        for (CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam : sourceContainerGenerateCodeResponseParam.codeListLocationInfoList) {
            returnContainerGenerateCodeResponseParam.codeListLocationInfoList.add(
                    CodePaneModelGenerateCodeResponseParam.generateCodePaneModelGenerateCodeResponseParam(
                            codePaneModelGenerateCodeResponseParam, pathFindCell)
            );
        }
        return returnContainerGenerateCodeResponseParam;
    }

}
