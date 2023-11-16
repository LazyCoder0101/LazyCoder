package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.control;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleControl;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.ModuleControlPane;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.JsonUtil;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
//import freeseawind.lf.basic.scroll.LuckScrollPaneUI;

public class ModuleControlFolder extends Folder implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -7489230078089176929L;

    private Box vBox;
    private JScrollPane jsp;
    private ModuleControlPane moduleControlPane;

    /**
     * @param expanded 是否展开
     */
    public ModuleControlFolder(boolean expanded) {
        // TODO Auto-generated constructor stub
        setDefaultLayout();
        ModuleControlFolderHiddenButton hiddenButton = new ModuleControlFolderHiddenButton(expanded);
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        vBox = Box.createVerticalBox();
        jsp = new JScrollPane(vBox);
        int spHeight = (int) (0.27 * SysUtil.SCREEN_SIZE.getHeight());
        jsp.setSize((int) (InitFolderPane.WIDTH_PROPORTION * SysUtil.SCREEN_SIZE.getWidth()), spHeight);//300

        moduleControlPane = new ModuleControlPane();
        // 设置模型
        moduleControlPane.setModel(ModuleEditPaneHolder.moduleFormatModel);
        ModuleEditPaneHolder.moduleFormatModel.setControlPane(moduleControlPane);

        JScrollPane scrollPane = new JScrollPane(moduleControlPane);
        moduleControlPane.setUpdateScrollpane(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.setUI(new LuckScrollPaneUI());
        vBox.add(scrollPane);

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, jsp);
        setDrawer(drawer);
        add(drawer);

    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo,Module module) {
        // TODO Auto-generated method stub
        vBox.removeAll();
        moduleControlPane = new ModuleControlPane();
        JScrollPane scrollPane = new JScrollPane(moduleControlPane);
        moduleControlPane.setUpdateScrollpane(scrollPane);
//        scrollPane.setUI(new LuckScrollPaneUI());
        vBox.add(scrollPane);

        if (moduleInfo.getWhetherModuleControlIsRequired() == ModuleInfo.TRUE_) {// 此前添加过内容，显示之前编辑过的内容
            ModuleControl moduleControl = SysService.MODULE_CONTROL_SERVICE.getModuleControl(moduleInfo.getModuleId());
            LazyCoderFormatControl.buildingOriginalOperatingPane(moduleControlPane,
                    ModuleEditPaneHolder.moduleFormatModel, moduleControl);

        } else {// 此前没添加过内容
            LazyCoderFormatControl.buildingNewOperatingPane(moduleControlPane, ModuleEditPaneHolder.moduleFormatModel);
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
    }

    public ModuleControl getModuleControl() {
        String formatParam = "";
        try {
            formatParam = moduleControlPane.getControlStatementFormat();

        } catch (BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ModuleControl moduleControl = new ModuleControl();
        moduleControl.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        LazyCoderFormatControl.setOperating(moduleControl, ModuleEditPaneHolder.moduleFormatModel);
        moduleControl.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
        moduleControl.setDefaultControlStatementFormat(formatParam);
        moduleControl.setNoteListParam(JsonUtil.getJsonStr(moduleControlPane.getUsageReminderList()));
        return moduleControl;
    }

    /**
     * 查看是否有模块控制
     *
     * @return
     */
    public int getWhetherModuleControlIsRequired() {
        return moduleControlPane.getWhetherModuleControlIsRequired();
    }

}
