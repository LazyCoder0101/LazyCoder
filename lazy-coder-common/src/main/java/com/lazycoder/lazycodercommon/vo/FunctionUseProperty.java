package com.lazycoder.lazycodercommon.vo;

import lombok.Getter;

/**
 * 功能使用属性
 */
public enum FunctionUseProperty {

    /**
     * 自动生成一次：每次添加这个模块，这个代码都要自动生成一次
     * 仅自动生成一次：每次添加这个模块，这个代码都要自动生成一次，且每次右键菜单，遍历查询，只要发现该功能有被添加，这个功能对应的菜单直接失能，不允许再点击（自动生成该功能后，允许用户自己删除该功能）
     * 只能使用一次：每次右键菜单，遍历查询，只要发现该功能有被添加，这个功能对应的菜单直接失能
     * 仅自动生成一次且不能删：每次添加这个模块，这个代码都要自动生成一次，且每次右键菜单，遍历查询，只要发现该功能有被添加，这个功能对应的菜单直接失能，不允许再点击，且该功能的关闭按钮失能
     */

    no(0,"无"),
    autoGenerateOnce(1,"自动生成一次"),
    autoGenerateOnceCanOnlyBeUsedOnce(2,"自动生成一次，且只能使用一次"),
    onlyBeUsedOnce(3,"只能使用一次"),
    autoGenerateOnceAndCanNotDel(4,"仅自动生成一次且不能删"),

    onlyBeAddedToBusinessSchtcheonOfATemplate(5,"只能添加到模板默认代码文件的\"方法\"中"),
    onlyAddedItOnceToBusinessSchtcheonOfATemplate(6,"只能在模板默认代码文件的\"方法\"中添加一次"),
    onlyBeAddedToFunctionAddLabel (7,"只能添加到方法组件中");

    @Getter
    private final int sysDictionaryValue;

    @Getter
    private final String showText;

    private FunctionUseProperty(int sysDictionaryValue, String showText){
        this.sysDictionaryValue=sysDictionaryValue;
        this.showText=showText;
    }

    /**
     * 根据字典值返回对应使用属性
     *
     * @param sysDictionaryValue
     * @return
     */
    public static FunctionUseProperty getFunctionUsePropertyBy(int sysDictionaryValue) {
        FunctionUseProperty functionUseProperty = null;
        for (FunctionUseProperty temp : FunctionUseProperty.values()) {
            if (temp.getSysDictionaryValue() == sysDictionaryValue) {
                functionUseProperty = temp;
                break;
            }
        }
        return functionUseProperty;
    }

}
