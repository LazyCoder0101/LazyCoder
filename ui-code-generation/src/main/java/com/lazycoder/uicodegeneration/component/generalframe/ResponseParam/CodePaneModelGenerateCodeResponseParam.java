package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.CodeFormatFlagParamDeserializer;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 生成代码时，代码面板以及其生成的代码模型返回的对应参数
 */
@Data
@NoArgsConstructor
public class CodePaneModelGenerateCodeResponseParam {

    private int metaType = PathFind.COMMAND_TYPE;

    /**
     * 游标的设置参数，小于0的话就是该处位置不需要添加代码
     * 每个功能再保存数据的时候，都有个CodePaneModelGenerateCodeResponseParam实例保存代码位置信息，以便后面打开项目和更改代码值的时候找到本功能所有位置，此参数与该功能没有关系，不需要保存此参数的数值
     */
    @JSONField(deserialize = false, serialize = false)
    private int incrementalCursorPositionTemp = 0;

    /**
     * 是否需要编辑（该处是否需要添加对应代码）
     * 每个功能再保存数据的时候，都有个CodePaneModelGenerateCodeResponseParam实例保存代码位置信息，以便后面打开项目和更改代码值的时候找到本功能所有位置，此参数与该功能没有关系，不需要保存此参数的数值
     */
    @JSONField(deserialize = false, serialize = false)
    private boolean editOrNot = false;

    /**
     * 对应这个代码面板的信息，后面根据该参数找到对应代码面板
     */
    @JSONField(deserializeUsing = CodeFormatFlagParamDeserializer.class)
    private CodeFormatFlagParam codeFormatFlagParam = null;

    /**
     * 该代码面板中所有的CodeList的定位信息（添加功能的时候才需要用该参数）
     */
    @JSONField(deserializeUsing = CodeListLocationArrayListDeserializer.class)
    private ArrayList<CodeListLocation> codeListLocationInfoList = new ArrayList<>();

    /**
     * 代码序号（命令形式才设置该参数）
     */
    private Integer codeOrdinal = null;

    private String codeLabelId = null;

    public static CodePaneModelGenerateCodeResponseParam generateCodePaneModelGenerateCodeResponseParam(
            CodePaneModelGenerateCodeResponseParam sourceCodePaneModelGenerateCodeResponseParam, int codeOrdinal, String codeLabelId
    ) {
        CodePaneModelGenerateCodeResponseParam toCodePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
        toCodePaneModelGenerateCodeResponseParam.codeOrdinal = codeOrdinal;
        toCodePaneModelGenerateCodeResponseParam.codeLabelId = codeLabelId;

        ArrayList<CodeListLocation> sourceCodeListLocationInfoList = sourceCodePaneModelGenerateCodeResponseParam.codeListLocationInfoList;
        for (CodeListLocation codeListLocation : sourceCodeListLocationInfoList) {
            toCodePaneModelGenerateCodeResponseParam.codeListLocationInfoList.add(codeListLocation);
        }
        toCodePaneModelGenerateCodeResponseParam.codeFormatFlagParam = sourceCodePaneModelGenerateCodeResponseParam.codeFormatFlagParam;
        return toCodePaneModelGenerateCodeResponseParam;
    }

    public static CodePaneModelGenerateCodeResponseParam generateCodePaneModelGenerateCodeResponseParam
            (CodePaneModelGenerateCodeResponseParam sourceCodePaneModelGenerateCodeResponseParam, PathFindCell pathFindCell) {
        CodePaneModelGenerateCodeResponseParam returnCodePaneModelGenerateCodeResponseParam = new CodePaneModelGenerateCodeResponseParam();
        returnCodePaneModelGenerateCodeResponseParam.editOrNot = sourceCodePaneModelGenerateCodeResponseParam.editOrNot;
        returnCodePaneModelGenerateCodeResponseParam.codeFormatFlagParam = sourceCodePaneModelGenerateCodeResponseParam.codeFormatFlagParam;
        for (CodeListLocation codeListLocation : sourceCodePaneModelGenerateCodeResponseParam.codeListLocationInfoList) {
            returnCodePaneModelGenerateCodeResponseParam.codeListLocationInfoList.add(
                    CodeListLocation.generateCodeListLocation(codeListLocation, pathFindCell));
        }
        return returnCodePaneModelGenerateCodeResponseParam;
    }


    public void addCodeListLocationInfoList(CodeListLocation codeListLocation) {
        this.codeListLocationInfoList.add(codeListLocation);
    }

    /**
     * 查看theCodeListLocationList是否包含有一个和codeListLocation一样的元素
     *
     * @param codeListLocation
     * @return
     */
    public boolean checkContain(CodeListLocation codeListLocation) {
        boolean flag = false;
        for (CodeListLocation codeListLocationTemp : this.codeListLocationInfoList) {
            if (CodeListLocation.compare(codeListLocationTemp, codeListLocation)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
