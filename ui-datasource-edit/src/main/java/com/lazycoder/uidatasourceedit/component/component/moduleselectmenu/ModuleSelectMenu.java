package com.lazycoder.uidatasourceedit.component.component.moduleselectmenu;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import lombok.Getter;

public class ModuleSelectMenu extends JMenuBar {

    @Getter
    private Module selectedModule = null;

    private JMenu menu = null;

    private JMenuItem noModuleItem;

    public ModuleSelectMenu() {
        super();
        menu = new JMenu();
        add(menu);
        String noText = HTMLText.createHtmlContent("无", HtmlPar.BLUE, false);
        noModuleItem = new JMenuItem(noText);
        noModuleItem.addActionListener(noModuleActionListener);

        uploadModuleItems();
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.doClick();
            }
        });
    }

    private ActionListener noModuleActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setNoModule();
        }
    };

    /**
     * 添加所有选项
     */
    public void uploadModuleItems() {
        menu.removeAll();
        menu.add(noModuleItem);
        ModuleSelectMenuItem item;
        List<Module> list = SysService.MODULE_SERVICE.getAllModuleList();
        List<TheClassification> classList = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
        if (list != null) {
            JMenu classMenu;
            for (TheClassification classification : classList) {
                classMenu = new JMenu(classification.getClassName());
                menu.add(classMenu);
                for (Module temp : list) {
                    if (classification.getClassName().equals(temp.getClassName())) {
                        item = new ModuleSelectMenuItem(temp);
                        item.addActionListener(menuItemActionListener);
                        classMenu.add(item);
                    }
                }
            }
        }
    }

    private ActionListener menuItemActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof ModuleSelectMenuItem) {
                ModuleSelectMenuItem moduleSelectMenuItem = (ModuleSelectMenuItem) e.getSource();
                selectModule(moduleSelectMenuItem.getModule());
            }
        }
    };

    public void setNoModule(){
        menu.setText("");
        menu.setToolTipText(null);
        this.selectedModule = null;
    }

    public void selectModule(Module module) {
        if (module != null) {
            menu.setText(module.getModuleName());
            HTMLText htmlText = new HTMLText();
            HtmlPar par1 = new HtmlPar();
            par1.addColorText("所属分类：", HtmlPar.YELLOW, false);
            par1.addText(module.getClassName(), false);
            htmlText.addPar(par1);
            menu.setToolTipText(htmlText.getHtmlContent());

            this.selectedModule = module;
        }
    }

}
