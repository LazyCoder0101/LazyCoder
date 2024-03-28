package com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.importcode;

import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.EditContainerPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uidatasourceedit.moduleedit.commandinput.moduleinit.InitFolderPane;
import com.lazycoder.uiutils.folder.Drawer;
import com.lazycoder.uiutils.folder.Folder;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;
import java.util.List;

public class ImportCodeFolder extends Folder
        implements CheckInterface, ModuleEditComponentInterface, EditContainerPane {

    /**
     *
     */
    private static final long serialVersionUID = 6317759106966761150L;

    private Border defaultborder;

    private Box vBox;

    private JScrollPane jsp;

    private int curentImportNum = 0;

    /**
     * 初始化构造该控件
     *
     * @param expanded 是否展开
     */
    public ImportCodeFolder(boolean expanded) {
        // TODO Auto-generated constructor stub
        setDefaultLayout();

        ImportCodeFolderHiddenButton hiddenButton = new ImportCodeFolderHiddenButton(expanded);
        setHiddenButton(hiddenButton);
        add(hiddenButton);
        defaultborder = hiddenButton.getBorder();

        vBox = Box.createVerticalBox();
        int spHeight = (int) (0.1481 * SysUtil.SCREEN_SIZE.getHeight());
        jsp = new JScrollPane(vBox);
        jsp.setSize((int) (InitFolderPane.WIDTH_PROPORTION * SysUtil.SCREEN_SIZE.getWidth()), spHeight);//160

        // 生成并添加抽屉
        Drawer drawer = new Drawer(expanded ? 1 : 0, jsp);
        setDrawer(drawer);
        add(drawer);
    }

    /**
     * 添加引入代码
     */
    public void addImportCode() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            curentImportNum++;
            ImportCodeInputPane importCodeInputPane = new ImportCodeInputPane(curentImportNum);
            vBox.add(importCodeInputPane);
//            vBox.updateUI();
//            vBox.repaint();
//            updateUI();
//            repaint();
        }
    }

    /**
     * 删除最后一条引入代码
     */
    public void delImportCode() {
        boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
        if (flag == true) {
            if (curentImportNum > 0) {
                curentImportNum--;
                vBox.remove(vBox.getComponentCount() - 1);
                vBox.updateUI();
                vBox.repaint();
            }
        }
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        curentImportNum = 0;
        ImportCodeInputPane importCodeInputPane;
        if (module.getEnabledState() == Module.TRUE_ && moduleInfo.getNumOfImport() > 0) {//有编辑过,且此前添加过内容，显示之前编辑过的内容
            List<ImportCode> list = SysService.IMPORT_CODE_SERVICE.getImportCodeList(moduleInfo.getModuleId());
            for (ImportCode importCode: list) {
                curentImportNum++;
                importCodeInputPane = new ImportCodeInputPane(importCode);
                vBox.add(importCodeInputPane);
            }

        } else if (module.getEnabledState() == Module.FALSE_) {
            curentImportNum++;
            importCodeInputPane = new ImportCodeInputPane(curentImportNum);
            vBox.add(importCodeInputPane);
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        curentImportNum = 0;
        vBox.removeAll();
        this.updateUI();
        this.repaint();
    }

    public int getNumOfImportCode() {
        return vBox.getComponentCount();
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        ImportCodeInputPane temp1, temp2;
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            temp1 = (ImportCodeInputPane) vBox.getComponent(i);
            if ("".equals(temp1.getCode().trim())) {
                LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o    亲，第" + (i + 1) + "个引入代码那里什么都没写，\n不需要的话，请把它删了", "系统信息",
                        JOptionPane.PLAIN_MESSAGE);
                flag = false;
                break;
            }

            for (int k = i + 1; k < vBox.getComponentCount(); k++) {
                temp2 = (ImportCodeInputPane) vBox.getComponent(k);
                if (temp1.getCode().trim().equals(temp2.getCode().trim())) {
                    LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o   再检查一下吧，第" + (i + 1) + "句引入代码和第" + (k + 1) + "引入代码都是一样的，这个没必要吧", "系统信息",
                            JOptionPane.PLAIN_MESSAGE);
                    flag = false;
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
        return flag;
    }

    public ArrayList<ImportCode> getImportCode() {
        ImportCodeInputPane temp;
        ImportCode importCode;
        ArrayList<ImportCode> list = new ArrayList<>();
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            temp = (ImportCodeInputPane) vBox.getComponent(i);
            importCode = new ImportCode();
            importCode.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
            importCode.setOrdinal(temp.getOrdinal());
            importCode.setCode(temp.getCode());
            importCode.setCodeLabelId(temp.getCodeLabelId());
            // importCode.setMarkName(temp.getTheMarkName());
            list.add(importCode);
        }
        return list;
    }

    @Override
    public void setUIResponse(boolean status) {
        if (status) {
            getHiddenButton().setBorder(DataSourceEditHolder.RESPONSE_TRUE_BORDER);
            jsp.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
            jsp.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_TRUE_COLOR);
        } else {
            getHiddenButton().setBorder(defaultborder);
            jsp.getVerticalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
            jsp.getHorizontalScrollBar().setBackground(DataSourceEditHolder.RESPONSE_FALSE_COLOR);
        }
    }

}
