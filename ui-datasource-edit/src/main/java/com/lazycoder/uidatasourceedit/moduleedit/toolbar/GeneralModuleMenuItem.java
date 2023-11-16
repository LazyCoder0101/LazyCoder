package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import com.lazycoder.database.model.Module;
import javax.swing.JCheckBoxMenuItem;
import lombok.Getter;

public class GeneralModuleMenuItem extends JCheckBoxMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = -7196132777217172257L;

    @Getter
    private Module module;

    public GeneralModuleMenuItem(Module module) {
        // TODO Auto-generated constructor stub
        super(module.getModuleName());
        setToolTipText("\"" + module.getClassName() + "\"模块");
        this.module = module;

    }

    /**
     * 获取模块名
     */
    public void getModuleName() {
        this.module.getModuleName();
    }

    /**
     * 是否是该模块
     *
     * @param module
     * @return
     */
    public boolean isModule(Module module) {
        boolean flag = false;
        if (this.module.getModuleId().equals(module.getModuleId())) {
//        if ((this.module.getModuleName().equals(module.getModuleName())) && (this.module.getClassName().equals(module.getClassName()))) {
            flag = true;
        }
        return flag;
    }


}
