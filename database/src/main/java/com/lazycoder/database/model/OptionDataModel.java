package com.lazycoder.database.model;

import com.lazycoder.database.DataFormatType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OptionDataModel implements BaseModel, DataFormatType {


    /**
     * 单选（信息类型）
     */
    public static final int EXECLUSIVE = 0;

    /**
     * 多选（信息类型）
     */
    public static final int MULTIPLE = 1;

    /**
     * 选项名称列表参数
     */
    private String optionNameListParam;

    private int valueNum = 0;//选项数量（有多少个选项名称）

    /**
     * 选项唯一id标识
     */
    private String optionId = null;

    private String moduleId = null;

    /**
     * 选项值列表参数
     */
    private String optionValueListParam;

    /**
     * 选项名（和optionId一样，唯一，不能同名）
     */
    private String optionName;

    /**
     *
     */
    private int usingRange = MODULE_TYPE;

    /**
     * 选项类型（多选、单选）
     */
    private int optionType = EXECLUSIVE;

    /**
     * 左符号
     */
    private String leftStr;

    /**
     * 右符号
     */
    private String rightStr;

    /**
     * 间隔符
     */
    private String separatorStr;

    /**
     * 其它函数序号，其它函数的选项才用
     */
    private int additionalSerialNumber = 0;

    /**
     * 有多少组选项值列表可供选择
     */
    private int valueListGroupNum = 0;

    private String rowNoteParam = "[]";

}
