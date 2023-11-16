package com.lazycoder.uidatasourceedit.moduleedit;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;

/**
 * 显示模块信息的控件需要继承的接口
 *
 * @author admin
 */
public interface ModuleEditComponentInterface {

    /**
     * 显示对应模块内容： 当选中指定模块时，从数据库获取其模块信息，看看该模块此前有没有被编辑过内容，没有，显示新增该模块时的样式，有，把原来的内容还原出来
     *
     * @param moduleInfo
     * @param module
     */
    public void displayModuleContents(ModuleInfo moduleInfo, Module module);

    /**
     * 清空模块内容
     */
    public void clearModuleContents();

}
