package com.lazycoder.uidatasourceedit.component.component.moduleselectmenu;

import com.lazycoder.database.model.Module;
import javax.swing.JMenuItem;
import lombok.Getter;

public class ModuleSelectMenuItem extends JMenuItem {

    @Getter
    private Module module = null;

    public ModuleSelectMenuItem(Module module) {
        super(module.getModuleName());
        this.module = module;
    }

}
