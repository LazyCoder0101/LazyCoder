package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.modulecodeusepropetycomponent;

import com.lazycoder.database.model.Module;
import com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.ModuleCodeUsePropetyMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;

public class NeedUseModuleImportCodeMenuItem extends JRadioButtonMenuItem {

    private ModuleCodeUsePropetyMenu moduleCodeUsePropetyMenu = null;

    private NeedUseModuleImportCodeMenu needUseModuleImportCodeMenu = null;

    private Module module;

    public NeedUseModuleImportCodeMenuItem(Module module, NeedUseModuleImportCodeMenu needUseModuleImportCodeMenu, ModuleCodeUsePropetyMenu moduleCodeUsePropetyMenu) {
        super(module.getModuleName());
        this.moduleCodeUsePropetyMenu = moduleCodeUsePropetyMenu;
        this.needUseModuleImportCodeMenu = needUseModuleImportCodeMenu;
        this.module = module;
        addActionListener(listener);
    }

    public String getModuleId() {
        return this.module.getModuleId();
    }

    public String getModuleName(){return this.module.getModuleName();}

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isSelected()) {
                if (needUseModuleImportCodeMenu != null && (!needUseModuleImportCodeMenu.getNeedUseModuleImportCode().getNeedUseModuleList().contains(module.getModuleId()))) {
                    needUseModuleImportCodeMenu.getNeedUseModuleImportCode().getNeedUseModuleList().add(module.getModuleId());
                }
            } else {
                if (needUseModuleImportCodeMenu != null) {
                    needUseModuleImportCodeMenu.getNeedUseModuleImportCode().getNeedUseModuleList().remove(module.getModuleId());
                }
            }

            if (moduleCodeUsePropetyMenu != null) {
                moduleCodeUsePropetyMenu.showText();
            }
        }
    };

}
