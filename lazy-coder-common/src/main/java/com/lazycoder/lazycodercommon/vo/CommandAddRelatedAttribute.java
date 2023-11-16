package com.lazycoder.lazycodercommon.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代码标记移位
 * <p>
 * 给方法添加组件使用，如果在方法添加组件中添加功能，发现添加的功能，对应代码找不到对应的符合的方法添加组件，对应产生的移位功能的情况
 */
@NoArgsConstructor
public class CommandAddRelatedAttribute implements Cloneable{

    /**
     * 直接添加到标记
     */
    public static final int ADD_TO_MARK_ADD_TYPE = 0;

    /**
     * 添加到功能添加组件
     */
    public static final int ADD_TO_FUNCTION_ADD_COMPONENT_ADD_TYPE = 1;

    @Getter
    @Setter
    private int addType = ADD_TO_MARK_ADD_TYPE;

    /**
     * 无需代码标记移位
     */
    public static final int NONE_OTHER_ATTRIBUTE = 0;

    /**
     * 添加到对应符合标记第1级组件
     */
    public static final int ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE = 2;

    /**
     * 逐级往上，寻找符合的方法添加组件，如果没有，寻找符合的标记组件
     */
    public static final int STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE = 1;

    @Getter
    @Setter
    private int otherAttribute = NONE_OTHER_ATTRIBUTE;

    @Getter
    @Setter
    private String codeLabelId = null;

    @Getter
    @Setter
    private boolean addToLast = true;    //是否添加到最后，默认一定为true，设为false的话，要设置 nextCodeSerialNumber,即要插入的位置，其下一个功能的代码的CodeSerialNumber

    @Getter
    @Setter
    private Integer nextCodeSerialNumber = null;//如果要添加在某句代码前面，设置此参数

    @Override
    public CommandAddRelatedAttribute clone() {
        CommandAddRelatedAttribute commandAddRelatedAttribute = null;
        try {
            commandAddRelatedAttribute = (CommandAddRelatedAttribute) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return commandAddRelatedAttribute;
    }

}
