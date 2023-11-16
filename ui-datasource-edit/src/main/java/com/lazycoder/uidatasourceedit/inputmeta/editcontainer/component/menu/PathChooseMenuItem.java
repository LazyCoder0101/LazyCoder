package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component.menu;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import lombok.Getter;

import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class PathChooseMenuItem extends JCheckBoxMenuItem {

    /**
     *
     */
    private static final long serialVersionUID = 4568372538904445370L;

    private BasePathChooseMenu pathChooseMenu = null;

    @Getter
    private CodeFormatFlagParam codeFormatFlagParam = null;

    public PathChooseMenuItem(CodeFormatFlagParam codeFormatFlagParam, BasePathChooseMenu pathChooseMenu) {
        // TODO Auto-generated constructor stub
        super();
        this.codeFormatFlagParam = codeFormatFlagParam;
        this.pathChooseMenu = pathChooseMenu;
        setText(codeFormatFlagParam.getFileName());
        setTheToolTipText();
        addItemListener(listener);
    }

    private void setTheToolTipText() {
        int type = this.codeFormatFlagParam.getFormatType();
        if (type == CodeFormatFlagParam.MAIN_TYPE) {
            setToolTipText("必填模板");

        } else if (type == CodeFormatFlagParam.ADDITIONAL_TYPE) {
            setToolTipText("其他文件" + this.codeFormatFlagParam.getAdditionalSerialNumber());

        } else if (type == CodeFormatFlagParam.MODULE_TYPE) {
            Module module = SysService.MODULE_SERVICE.getModuleById(this.codeFormatFlagParam.getModuleId());
            if (module != null) {
                setToolTipText("模块文件\t分类：" + module.getClassName() + "\t模块名："
                        + module.getModuleName());
            }
        }
    }


    private ItemListener listener = new ItemListener() {

        @Override
        public void itemStateChanged(ItemEvent e) {
            // TODO Auto-generated method stub
            if (pathChooseMenu != null) {
                pathChooseMenu.processing(PathChooseMenuItem.this);

                ArrayList<CodeFormatFlagParam> list = pathChooseMenu.getSelectedCodeFormatFlagParams();
                pathChooseMenu.showWritePath(list);
                pathChooseMenu.doClick();
            }
        }
    };

}
