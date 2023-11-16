package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.functionname;

import com.lazycoder.database.model.FunctionNameData;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.FoldButton;
import com.lazycoder.uiutils.folder.FoldButtonUI;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JScrollPane;


public class ModuleFunctionNameFolder extends Folder
        implements ModuleEditComponentInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = -7504672422756710812L;

    private Box vBox;

    private JScrollPane jsp;

    private ModuleFuncitonNameTable table;

    /**
     * @param expanded 是否展开
     */
    public ModuleFunctionNameFolder(boolean expanded) {
        // TODO Auto-generated constructor stub
        setDefaultLayout();
        FoldButton hiddenButton = new FoldButton(expanded);
        hiddenButton.setUI(new FoldButtonUI());
        hiddenButton.setText("可用方法名");
        setHiddenButton(hiddenButton);
        add(hiddenButton);

        vBox = Box.createVerticalBox();
        jsp = new JScrollPane(vBox);
        jsp.setSize((int) (InitFolderPane.WIDTH_PROPORTION * SysUtil.SCREEN_SIZE.getWidth()), 200);

        table = new ModuleFuncitonNameTable();

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
     * 添加方法名
     */
    public void addFunctionName() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            table.addFunctionName();
        }
    }

    /**
     * 删除最后一个方法名
     */
    public void delFunctionName() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            table.delFunctionName();
        }
    }

    /**
     * 获取录入模块的方法名
     *
     * @return
     */
    public ArrayList<FunctionNameData> getFunctionNameDataList() {
        return table.getFunctionNameDataList();
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return table.check();
    }

    /**
     * 获取方法名总数
     *
     * @return
     */
    public int getModuleFunctionNum() {
        return table.getRowCount();
    }

}
