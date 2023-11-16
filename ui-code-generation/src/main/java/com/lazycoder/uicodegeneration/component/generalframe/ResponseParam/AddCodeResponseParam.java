package com.lazycoder.uicodegeneration.component.generalframe.ResponseParam;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.CodeFormatFlagParamDeserializer;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.PathFindListDeserializer;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加代码时需要返回的参数
 */
@Data
@NoArgsConstructor
public class AddCodeResponseParam {

    /**
     * 游标的设置参数，小于0的话就是该处位置不需要添加代码
     */
    private int incrementalCursorPositionTemp = 0;

    /**
     * 是否需要编辑（该处是否需要添加对应代码）
     */
    private boolean editOrNot = false;

    /**
     * 对应生成的代码
     */
//    private ArrayList<TheCodeStatement> theCodeStatementList = new ArrayList<>();

    /**
     * 放置该段代码的数组
     */
//    private ArrayList<CodeList> codeListArrayList = new ArrayList<>();

    /**
     * 代码所在位置的对应寻址字符串
     */
    @JSONField(deserializeUsing = PathFindListDeserializer.class)
    private ArrayList<PathFind> codePathFindList = new ArrayList<>();

    /**
     * 所在的代码面板的对应信息
     */
    @JSONField(deserializeUsing = CodeFormatFlagParamDeserializer.class)
    private CodeFormatFlagParam codeFormatFlagParam = null;


}

