package com.lazycoder.lazycodercommon.vo.CodeUseProperty;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码使用属性
 */
public abstract class AbstractCodeUseProperty {

    /**
     * 无需插入本模块引入代码
     */
    public static final int NoNeedToInsertImportCodeType = 0;

    /**
     * 插入需要使用模块的引入代码
     */
    public static final int NeedUseModuleImportCodeType = 1;

    /**
     * 无需插入换行
     */
    public static final int NoNeedInserNewLine = 2;

    /**
     * 代码使用属性类型
     *
     * @return
     */
    @Setter
    @Getter
    protected int codeUsePropertyType;

    @JSONField(deserialize = false, serialize = false)
    public abstract String getShowText();

    /**
     * 获取codeUsePropertyType这字段的名称
     * @return
     */
    public static String getCodeUsePropertyTypeName(){
        return "codeUsePropertyType";
    }

//    noNeedToInsertImportCode(0,"无需插入引入代码");
//
//    @Getter
//    private final int sysDictionaryValue;
//
//    @Getter
//    private final String showText;
//
//    private CodeUseProperty(int sysDictionaryValue, String showText){
//        this.sysDictionaryValue=sysDictionaryValue;
//        this.showText=showText;
//    }
//
//    /**
//     * 根据字典值返回对应使用属性
//     *
//     * @param sysDictionaryValue
//     * @return
//     */
//    public static CodeUseProperty getCodeUsePropertyBy(int sysDictionaryValue) {
//        CodeUseProperty codeUseProperty = null;
//        for (CodeUseProperty temp : CodeUseProperty.values()) {
//            if (temp.getSysDictionaryValue() == sysDictionaryValue) {
//                codeUseProperty = temp;
//                break;
//            }
//        }
//        return codeUseProperty;
//    }

}

