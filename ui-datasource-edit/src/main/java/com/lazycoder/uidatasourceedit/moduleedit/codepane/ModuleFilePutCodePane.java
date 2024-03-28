package com.lazycoder.uidatasourceedit.moduleedit.codepane;

import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.FormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.ModuleFileFormatCodePane;
import com.lazycoder.uidatasourceedit.component.component.ImportCodeFileEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleFilePutCodePane extends ImportCodeFileEditPane
        implements ModuleEditComponentInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = -1501697445847534338L;

    private final Color noNeedInserImportCodePaneColor = new Color(236,245,255);

    public ModuleFilePutCodePane() {
        super("模块对应代码文件");
        // TODO Auto-generated constructor stub
    }

    /**
     * 添加空白源文件
     */
    public void createNewBlankSourceFile() {
        String fileName = FileUtil.addCodeFile();
        boolean flag = checkAddFile(fileName);
        if (flag == true && fileName != null) {
            fileName = fileName.trim();
            if ("".equals(fileName) == false) {
                currentCodeFileNum++;

                ModuleFileFormatCodePane moduleformatCodePane = new ModuleFileFormatCodePane(currentCodeFileNum);
                moduleformatCodePane.createNewBlankSourceFile(fileName);

                LazyCoderFormatControl.buildingNewSubCodePane(moduleformatCodePane,
                        ModuleEditPaneHolder.moduleFormatModel, fileName);
                setModuleCodeFile(moduleformatCodePane, fileName, currentCodeFileNum, DataSourceEditHolder.currentModule.getModuleId());
                FormatCodeScrollPane scrollPane = new FormatCodeScrollPane(moduleformatCodePane);
                moduleformatCodePane.setUpdateScrollpane(scrollPane);
                getTabbedPane().addTab(fileName, scrollPane);

                setSelectedLast();
            }
        }
        setModuleCodeFilePaneStyte();
    }

    /**
     * 打开源文件
     */
    public void openSourceFile() {
//        File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL,"导入源码文件");
        File file = selectFile();
        if (file != null) {
            boolean flag = checkForThisFile(file.getName());
            if (flag == true) {
                LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
            } else {
                currentCodeFileNum++;
                ModuleFileFormatCodePane moduleFileFormatCodePane = new ModuleFileFormatCodePane(currentCodeFileNum);
                moduleFileFormatCodePane.openSourceFile(file);

                LazyCoderFormatControl.buildingNewSubCodePane(moduleFileFormatCodePane,
                        ModuleEditPaneHolder.moduleFormatModel, file.getName());
                setModuleCodeFile(moduleFileFormatCodePane, file.getName(), currentCodeFileNum, DataSourceEditHolder.currentModule.getModuleId());

                FormatCodeScrollPane scrollPane = new FormatCodeScrollPane(moduleFileFormatCodePane);
                moduleFileFormatCodePane.setUpdateScrollpane(scrollPane);
                getTabbedPane().addTab(file.getName(), scrollPane);

                setSelectedLast();
                setModuleCodeFilePaneStyte();
            }
        }
    }

    // 待改
    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
        currentCodeFileNum = 0;
        if (moduleInfo.getNumOfAddFile() > 0) {// 如果有添加文件
            List<GeneralFileFormat> list = SysService.MODULE_FILE_FORMAT_SERVICE
                    .getModuleFileFormatList(DataSourceEditHolder.currentModule.getModuleId());
            GeneralFileFormat temp;
            ModuleFileFormatCodePane teModuleFileFormatCodePane;
            if (list != null) {
                FormatCodeScrollPane scrollPane;
                for (int i = 0; i < list.size(); i++) {
                    currentCodeFileNum++;
                    temp = list.get(i);
                    teModuleFileFormatCodePane = new ModuleFileFormatCodePane(temp.getCodeOrdinal());
                    teModuleFileFormatCodePane.setName(temp.getFileName());
                    teModuleFileFormatCodePane.setAttribute(temp);
                    LazyCoderFormatControl.buildingOriginalSubCodePane(teModuleFileFormatCodePane,
                            ModuleEditPaneHolder.moduleFormatModel, temp.getFileName(), temp);
                    setModuleCodeFile(teModuleFileFormatCodePane, temp.getFileName(),
                            temp.getCodeOrdinal(), DataSourceEditHolder.currentModule.getModuleId());

                    scrollPane = new FormatCodeScrollPane(teModuleFileFormatCodePane);
                    teModuleFileFormatCodePane.setUpdateScrollpane(scrollPane);
                    getTabbedPane().addTab(temp.getFileName(), scrollPane);

                }
                setSelectedLast();
                showModuleCodeFilePath();
            }
        }
        LazyCoderFormatControl.updateCodePaneMenu(ModuleEditPaneHolder.moduleFormatModel);
        setModuleCodeFilePaneStyte();
    }

    /**
     * 模块代码文件没设置路径的，填上
     */
    public void showModuleCodeFilePath() {
        AbstractFormatCodeInputPane teModuleFileFormatCodePane = null;
        FormatCodeScrollPane tempJsp;
        ModuleFileFormatCodePane moduleFileFormatCodePane;
        String thePath = SysService.SYS_PARAM_SERVICE.getParamValue(SysParam.MODULE_CODE_FILE_PATH);
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                tempJsp = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                teModuleFileFormatCodePane = tempJsp.getTheFormatCodeInputPane();
                if (teModuleFileFormatCodePane != null) {
                    if (teModuleFileFormatCodePane instanceof ModuleFileFormatCodePane) {
                        moduleFileFormatCodePane = (ModuleFileFormatCodePane) teModuleFileFormatCodePane;
                        if (moduleFileFormatCodePane.getPath() == null) {
                            moduleFileFormatCodePane.setPath(thePath);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        getTabbedPane().removeAll();
        fileChooserInit();
//        updateUI();
//        repaint();
    }

    /**
     * 获取录入的模块代码文件数据
     *
     * @return
     */
    public ArrayList<GeneralFileFormat> getModuleCodeFileData() {
        GeneralFileFormat formatFile;
        AbstractFormatCodeInputPane teModuleFileFormatCodePane = null;
        FormatCodeScrollPane tempJsp;
        ArrayList<GeneralFileFormat> list = new ArrayList<>();
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                tempJsp = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                teModuleFileFormatCodePane = tempJsp.getTheFormatCodeInputPane();
                if (teModuleFileFormatCodePane != null) {
                    formatFile = GeneralFileFormat.createModuleFormatFile();
                    formatFile.setModuleId(DataSourceEditHolder.currentModule.getModuleId());
                    teModuleFileFormatCodePane.setCodeModel(formatFile, (i + 1));
                    list.add(formatFile);
                }
            }
        }
        return list;
    }

    /**
     * 清空模块格式代码
     */
    public void clearAllModuleFileFormat() {
        getTabbedPane().removeAll();
    }

    /**
     * 获取模块格式文件的名称
     *
     * @return
     */
    public ArrayList<String> getModuleFormatFileNameList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            list.add(getTabbedPane().getTitleAt(i));
        }
        return list;
    }

    /**
     * 设置添加的模块格式文件的相关参数
     *
     * @param moduleInfo
     */
    public void setModuleFormatFileRelatedParam(ModuleInfo moduleInfo) {
        moduleInfo.setNumOfAddFile(ModuleEditPaneHolder.moduleFilePutCodePane.getTabbedPane().getComponentCount());// 设置添加模块格式文件的数量
        ModuleInfoStaticMethod.setAddFileParam(moduleInfo,
                ModuleEditPaneHolder.moduleFilePutCodePane.getModuleFormatFileNameList());// 记录添加模块格式文件的名称
    }

    /**
     * 获取当前代码文件面板
     *
     * @return
     */
//    public GeneralFormatCodeInputPane getSelectedCodeFormatPane() {
//        GeneralFormatCodeInputPane formatCodeInputPane = null;
//        Component component = tabbedPane.getSelectedComponent();
//        if (component != null) {
//            if (component instanceof FormatCodeScrollPane) {
//                FormatCodeScrollPane formatCodeScrollPane = (FormatCodeScrollPane) component;
//                formatCodeInputPane = formatCodeScrollPane.getTheFormatCodeInputPane();
//            }
//        }
//        return formatCodeInputPane;
//    }

    @Override
    public boolean check() {
        boolean flag = true;
        AbstractFormatCodeInputPane teModuleFileFormatCodePane = null;
        FormatCodeScrollPane tempJsp;
        ModuleFileFormatCodePane moduleFileFormatCodePane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                tempJsp = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                teModuleFileFormatCodePane = tempJsp.getTheFormatCodeInputPane();
                if (teModuleFileFormatCodePane != null) {
                    if (teModuleFileFormatCodePane instanceof ModuleFileFormatCodePane) {
                        moduleFileFormatCodePane = (ModuleFileFormatCodePane) teModuleFileFormatCodePane;
                        if (moduleFileFormatCodePane.getPath() == null) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "o(´^｀)o\t你还没有设置那个名字为\"" + teModuleFileFormatCodePane.getName() + "\"的代码面板的存放路径，\n要不再检查一下，看看哪里还有没写的吧");
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    private void setModuleCodeFilePaneStyte() {
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            getTabbedPane().setBackgroundAt(i, noNeedInserImportCodePaneColor);
        }
    }

    @Override
    protected CodeTabbedPane getCodeTabbedPane() {
        // TODO Auto-generated method stub
        return new showDelOptionPaneCodeTabbedPane(this);
    }


}
