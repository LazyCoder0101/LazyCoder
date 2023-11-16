package com.lazycoder.uiutils.mycomponent.multistatecomponent;

public interface MultiStateModelInterface {

    /**
     * 默认，不选
     */
    public final static int MARK_NULL = 0;

    /**
     * 标记为选中
     */
    public final static int MARK_SELECTED = 1;

    /**
     * 标记为禁止
     */
    public final static int MARK_NO = 2;

    /**
     * 预定不选（无法选择该项，用户无法继续操作）
     */
    public final static int MARK_PRE_NULL = 3;

    /**
     * 预定选择（必选，用户无法继续操作）
     */
    public final static int MARK_PRE_SELECTED = 4;

    /**
     * 预定禁止（无法选择，用户无法继续操作）
     */
    public final static int MARK_PRE_SELECTED_NO = 5;

}
