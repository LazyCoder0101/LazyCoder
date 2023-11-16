package com.lazycoder.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能模块表
 *
 * @author Administrator
 */
@NoArgsConstructor
@Data
public class Module implements BaseModel {

    private String moduleId;

    /**
     * 新增状态，从来没被编辑过的标记
     */
    public final static String NEW_STATE = "Not edited（新增）";

    /**
     * 分类
     */
    private String className;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 备注
     */
    private String note;

    /**
     * 排序
     */
    private int indexParam = 0;

    /**
     * 可使用范围
     */
    private String usingRangeParam = NEW_STATE;

    /**
     * 需要调用模块
     */
    private String needModuleParam = NEW_STATE;

    /**
     * 不需要用的模块
     */
    private String noUseModuleParam = NEW_STATE;

    /**
     * 使用设置参数
     */
    private String useSettingParam = "[]";

    /**
     * 是否可以使用
     */
    private int enabledState = FALSE_;

}
