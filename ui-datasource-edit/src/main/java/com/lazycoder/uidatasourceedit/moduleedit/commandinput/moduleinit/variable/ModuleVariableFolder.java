package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.variable;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.VariableData;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JScrollPane;


public class ModuleVariableFolder extends Folder
        implements ModuleEditComponentInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = -951168503653103889L;

    private Box vBox;

    private JScrollPane jsp;

    private ModuleVariableTable table;

    /**
     * @param expanded 是否展开
     */
    public ModuleVariableFolder(boolean expanded) {
        // TODO Auto-generated constructor stub
        setDefaultLayout();
        ModuleVariableFolderHiddenButton hiddenButton = new ModuleVariableFolderHiddenButton(expanded);
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        vBox = Box.createVerticalBox();
        jsp = new JScrollPane(vBox);
        jsp.setSize((int) (InitFolderPane.WIDTH_PROPORTION * SysUtil.SCREEN_SIZE.getWidth()), 200);

        table = new ModuleVariableTable();

//        table.updateUI();

        JScrollPane scrollPane = new JScrollPane(table);
        vBox.add(scrollPane);

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, jsp);
        setDrawer(drawer);
        add(drawer);

    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        // 待添加表
        table.displayModuleContents(moduleInfo, module);
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        table.clearModuleContents();
        updateUI();
        repaint();
    }

    /**
     * 添加模块变量
     */
    public void addModuleVariable() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            table.addVariable();
        }
    }

    /**
     * 删除最后一个模块变量
     */
    public void delModuleVariavle() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            table.delVariable();
        }
    }

    /**
     * 获取录入的模块变量数据
     *
     * @return
     */
    public ArrayList<VariableData> getVariableDataList() {
        return table.getVariableDataList();
    }


    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return table.check();
    }

    /**
     * 获取模块变量总数
     *
     * @return
     */
    public int getModuleVariableNum() {
        return table.getRowCount();
    }

}
