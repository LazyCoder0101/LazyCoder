package com.lazycoder.uidatasourceedit.formatedit.main;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.FormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LineNumFormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.AdditionalDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.MainDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.MainSubFormatCodePane;
import com.lazycoder.uidatasourceedit.component.component.ImportCodeFileEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.CheckInterface;
import com.lazycoder.uidatasourceedit.moduleedit.codepane.showDelOptionPaneCodeTabbedPane;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MainFormatPutPane extends ImportCodeFileEditPane
        implements MainPaneInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 6873588928317227594L;

    public MainFormatPutPane() {
        super("必填模板对应代码文件");
        // TODO Auto-generated constructor stub
    }

    /**
     * 设置默认代码文件的文件名
     */
    public void setMainDeafaultCodeFileName() {
        String fileName = LazyCoderOptionPane.showInputDialog(null, "后面用户更改文件名时，会采用设置的文件名的后缀\n请输入文件名：\n", "设置必填模板默认源文件的文件名",
                JOptionPane.PLAIN_MESSAGE);
        if ("".equals(fileName)) {
            LazyCoderOptionPane.showMessageDialog(null, "源文件名一定要写！");
        } else {
            if (FileUtil.isValidFileName(fileName) == true) {
                boolean flag = checkForThisFile(fileName);
                if (flag == true) {
                    LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
                } else {
                    if (FileUtil.haveExOrNot(fileName) == true) {
                        LineNumFormatCodeScrollPane scrollPane;
                        AbstractFormatCodeInputPane formatCodeInputPane;
                        MainDeafaultFormatCodePane mainDeafaultFormatCodePane;
                        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
                            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                                if (formatCodeInputPane != null) {
                                    if (formatCodeInputPane instanceof MainDeafaultFormatCodePane) {
                                        getTabbedPane().setTitleAt(i, HTMLText.createHtmlContent(fileName, HtmlPar.BLUE, false));
                                        mainDeafaultFormatCodePane = (MainDeafaultFormatCodePane) formatCodeInputPane;
                                        mainDeafaultFormatCodePane.setName(fileName);
                                        mainDeafaultFormatCodePane.getFileParam().setFileName(fileName);
                                        mainDeafaultFormatCodePane
                                                .setDefaultFilenameSetting(MainDeafaultFormatCodePane.TRUE_);
                                        scrollPane.setName(fileName);
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        LazyCoderOptionPane.showMessageDialog(null, "(〃'▽'〃)  源文件名一定要写后缀名，而且后缀名要正常点的！");
                    }
                }
            } else {
                LazyCoderOptionPane.showMessageDialog(null, "(ಥ_ಥ)  这文件名。。。好像不太对吧，换一个吧");
            }
        }
    }

    @Override
    public void displayMainContent(MainInfo mainInfo) {
        // TODO Auto-generated method stub
        displayDefaultFormatCodeFile();
        displaySubFormatCodeFileList(mainInfo);
    }

    /**
     * 获取子代码列表的数量
     *
     * @return
     */
    private int getNumOfSubCodeFile() {
        return getTabbedPane().getComponentCount() - 1;
    }

    /**
     * 还原默认代码面板的内容
     */
    private void displayDefaultFormatCodeFile() {
        GeneralFileFormat defaultCodeFormat = SysService.MAIN_FORMAT_CODE_FILE_SERVICE
                .getMainDefaultFormatFile();// 获取默认主格式代码文件
        if (defaultCodeFormat != null) {
            currentCodeFileNum++;
            MainFormatEditPane.mainFormatDeafaultPane = new MainDeafaultFormatCodePane(
                    defaultCodeFormat.getCodeOrdinal()) {
                @Override
                public void setDeafaultCodeFileName() {
                    setMainDeafaultCodeFileName();
                }
            };// 生成主格式代码面板
            MainFormatEditPane.mainFormatDeafaultPane.setAttribute(defaultCodeFormat);
            MainFormatEditPane.mainFormatDeafaultPane.setName(defaultCodeFormat.getFileName());
            MainFormatEditPane.mainFormatDeafaultPane
                    .setDefaultFilenameSetting(defaultCodeFormat.getDefaultFilenameSetting());
            LazyCoderFormatControl.buildingOriginalDefaultCodePane(
                    MainFormatEditPane.mainFormatDeafaultPane, MainFormatEditPane.formatModel,
                    defaultCodeFormat.getFileName(), defaultCodeFormat);// 把默认主格式代码文件的内容回写到面板中
            setMainDefaultCodeFile(MainFormatEditPane.mainFormatDeafaultPane,
                    defaultCodeFormat.getFileName());
            // 默认不能删的那个必填模板格式面板
            FormatEditPaneHolder.mainFormatFunctionPane = MainFormatEditPane.mainFormatDeafaultPane;
            LineNumFormatCodeScrollPane scrollPane2 = new LineNumFormatCodeScrollPane(
                    MainFormatEditPane.mainFormatDeafaultPane);
            MainFormatEditPane.mainFormatDeafaultPane.setUpdateScrollpane(scrollPane2);
            scrollPane2.setName(defaultCodeFormat.getFileName());
            getTabbedPane().superAddTab(
                    HTMLText.createHtmlContent(defaultCodeFormat.getFileName(), HtmlPar.BLUE, false),
                    scrollPane2, "默认代码文件");// 添加到tab面板
        }

    }

    /**
     * 还原所有子代码面板的内容
     *
     * @param mainInfo
     */
    private void displaySubFormatCodeFileList(MainInfo mainInfo) {
        if (mainInfo.getNumOfSubCodeFile() > 0) {
            MainSubFormatCodePane subformatCodePane;
            LineNumFormatCodeScrollPane scrollPane3;
            // 附带格式列表
            List<GeneralFileFormat> formatList = SysService.MAIN_FORMAT_CODE_FILE_SERVICE.getMainSubFormatCodeFileList();// 获取所有附带代码格式文件
            if (formatList != null) {
                for (GeneralFileFormat temp : formatList) {
                    currentCodeFileNum++;
                    subformatCodePane = new MainSubFormatCodePane(temp.getCodeOrdinal());
                    subformatCodePane.setName(temp.getFileName());
                    subformatCodePane.setAttribute(temp);

                    LazyCoderFormatControl.buildingOriginalSubCodePane(subformatCodePane, MainFormatEditPane.formatModel,
                            temp.getFileName(), temp);// 把附带主格式代码文件的内容回写到面板中
                    setMainSubCodeFile(subformatCodePane, temp.getFileName(), temp.getCodeOrdinal());

                    scrollPane3 = new LineNumFormatCodeScrollPane(subformatCodePane);
                    subformatCodePane.setUpdateScrollpane(scrollPane3);
                    scrollPane3.setName(temp.getFileName());
                    getTabbedPane().addTab(temp.getFileName(), scrollPane3);
                }
            }
        }
    }

    /**
     * 根据codeFormatFlagParam的信息获取对应面板
     *
     * @param codeFileId
     * @return
     */
    public AbstractFormatCodeInputPane getFormatCodeInputPane(String codeFileId) {
        Component scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane = null, formatCodeInputPaneTemp;
        CodeFormatFlagParam codeFormatFlagParamTemp;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            scrollPane = getTabbedPane().getComponent(i);
            if (scrollPane instanceof LineNumFormatCodeScrollPane) {
                formatCodeInputPaneTemp = ((LineNumFormatCodeScrollPane) scrollPane).getTheFormatCodeInputPane();
                if (formatCodeInputPaneTemp != null) {
                    codeFormatFlagParamTemp = formatCodeInputPaneTemp.getFileParam();
                    if (codeFormatFlagParamTemp.getId().equals(codeFileId)) {
                        formatCodeInputPane = formatCodeInputPaneTemp;
                        break;
                    }
                }
            }
        }
        return formatCodeInputPane;
    }

    /**
     * 设置模块设置的相关参数
     *
     * @param mainInfo
     */
    public void setMainInfoRelatedParam(MainInfo mainInfo) {
        mainInfo.setNumOfSubCodeFile(getNumOfSubCodeFile());// 记录必填模板设置代码的分类数量
    }

    /**
     * 添加空白源文件
     */
    public void createNewBlankSourceFile() {
        String fileName = FileUtil.addCodeFile();
        boolean flag = checkAddFile(fileName);
        if (flag == true && fileName != null) {
            fileName = fileName.trim();
            currentCodeFileNum++;
            MainSubFormatCodePane mainSubformatCodePane = new MainSubFormatCodePane(currentCodeFileNum);
            mainSubformatCodePane.createNewBlankSourceFile(fileName);
            LazyCoderFormatControl.buildingNewSubCodePane(mainSubformatCodePane, MainFormatEditPane.formatModel, fileName);
            setMainSubCodeFile(mainSubformatCodePane, fileName, currentCodeFileNum);

            LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(mainSubformatCodePane);
            scrollPane.setName(fileName);
            mainSubformatCodePane.setUpdateScrollpane(scrollPane);
            getTabbedPane().addTab(fileName, scrollPane);
            setSelectedLast();
            if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
            }
        }
    }

    /**
     * 打开源文件
     */
    public void openSourceFile() {
        //File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL, "请选择需要导入的懒农数据源文件");
        File file = selectFile();
        if (file != null) {
            boolean flag = checkForThisFile(file.getName());
            if (flag == true) {
                LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
            } else {
                currentCodeFileNum++;
                MainSubFormatCodePane mainSubformatCodePane = new MainSubFormatCodePane(currentCodeFileNum);
                mainSubformatCodePane.openSourceFile(file);
                LazyCoderFormatControl.buildingNewSubCodePane(mainSubformatCodePane, MainFormatEditPane.formatModel, file.getName());
                setMainSubCodeFile(mainSubformatCodePane, file.getName(), currentCodeFileNum);

                LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(mainSubformatCodePane);
                scrollPane.setName(file.getName());
                mainSubformatCodePane.setUpdateScrollpane(scrollPane);
                getTabbedPane().addTab(file.getName(), scrollPane);
                setSelectedLast();
                if (ModuleEditPaneHolder.moduleFilePutCodePane != null) {
                    ModuleEditPaneHolder.moduleFilePutCodePane.showModuleCodeFilePath();
                }
            }
        }
    }

    /**
     * 获取录入的格式代码的模型
     *
     * @return
     */
    public ArrayList<GeneralFileFormat> getFormatCodeList() {
        ArrayList<GeneralFileFormat> list = new ArrayList<GeneralFileFormat>();
        GeneralFileFormat mainCodeFormat = getDeafaultCodeFormat();
        list.add(mainCodeFormat);
        List<GeneralFileFormat> codeModelList = getMainSubCodeFormatList();
        list.addAll(codeModelList);
        return list;
    }


    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        LineNumFormatCodeScrollPane scrollPane;
        MainDeafaultFormatCodePane deafaultFormatCodePane;
        AbstractFormatCodeInputPane componentTemp;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                componentTemp = scrollPane.getTheFormatCodeInputPane();
                if (componentTemp != null) {
                    if (componentTemp instanceof MainDeafaultFormatCodePane) {
                        deafaultFormatCodePane = (MainDeafaultFormatCodePane) componentTemp;
                        if (deafaultFormatCodePane.getDefaultFilenameSetting() == AdditionalDeafaultFormatCodePane.FALSE_) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null, "╮(╯3╰)╭  必填模板还没设置默认代码文件的文件名，再检查一下，写完了再保存把");
                            break;
                        }
                    }
                    if (componentTemp.getUseState() == false) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null,
                                "╮(╯3╰)╭  那个名为\"" + componentTemp.getName() + "\"的代码文件什么都还没写，写完了再保存吧");
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 获取必填模板的附带源码模型列表
     */
    public List<GeneralFileFormat> getMainSubCodeFormatList() {
        List<GeneralFileFormat> codeModelList = new ArrayList<GeneralFileFormat>();
        MainSubFormatCodePane mainSubformatCodePane;
        GeneralFileFormat codeFormat;
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    if (formatCodeInputPane instanceof MainSubFormatCodePane) {
                        mainSubformatCodePane = (MainSubFormatCodePane) formatCodeInputPane;
                        codeFormat = mainSubformatCodePane.getMainCodeFormat(i + 1);
                        codeModelList.add(codeFormat);
                    }
                }
            }
        }
        return codeModelList;
    }

    public ArrayList<AbstractFormatCodeInputPane> getAllormatCodeInputPane() {
        ArrayList<AbstractFormatCodeInputPane> list = new ArrayList<>();
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                list.add(formatCodeInputPane);
            }
        }
        return list;
    }

    /**
     * 获取默认代码模型
     *
     * @return
     */
    public GeneralFileFormat getDeafaultCodeFormat() {
        MainDeafaultFormatCodePane mainFormatDeafaultCodePane;
        GeneralFileFormat mainCodeFormat = null;
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    if (formatCodeInputPane instanceof MainDeafaultFormatCodePane) {
                        mainFormatDeafaultCodePane = (MainDeafaultFormatCodePane) formatCodeInputPane;
                        mainCodeFormat = mainFormatDeafaultCodePane.getMainCodeFormat(i + 1);
                        break;
                    }
                }
            }
        }
        return mainCodeFormat;
    }

    /**
     * 获取当前代码文件面板
     *
     * @return
     */
    public AbstractFormatCodeInputPane getSelectedCodeFormatPane() {
        AbstractFormatCodeInputPane formatCodeInputPane = null;
        Component component = tabbedPane.getSelectedComponent();
        if (component != null) {
            if (component instanceof FormatCodeScrollPane) {
                FormatCodeScrollPane formatCodeScrollPane = (FormatCodeScrollPane) component;
                formatCodeInputPane = formatCodeScrollPane.getTheFormatCodeInputPane();
            }
        }
        return formatCodeInputPane;
    }

    /**
     * 重新加载格式代码面板
     */
    public void reloadFormatCodeInputPane() {
        LineNumFormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                scrollPane.reloadFormatCodeInputPane();
            }
        }
    }

    @Override
    protected CodeTabbedPane getCodeTabbedPane() {
        // TODO Auto-generated method stub
        return new showDelOptionPaneCodeTabbedPane(this);
    }

    @Override
    public void deletePane() {
        // TODO Auto-generated method stub
        if (getTabbedPane().getSelectedComponent() instanceof FormatCodeScrollPane) {
            FormatCodeScrollPane formatCodeScrollPane = (FormatCodeScrollPane) getTabbedPane().getSelectedComponent();
            AbstractFormatCodeInputPane formatCodeInputPane = formatCodeScrollPane.getTheFormatCodeInputPane();
            if (formatCodeInputPane != null) {
                CodeFormatFlagParam codeFormatFlagParam = formatCodeInputPane.getFileParam();
                ModuleEditPaneHolder.needUseCodeFileEditPane.removeCodeFilePane(codeFormatFlagParam);
            }
        }
    }

    public void clearAllSubCodeInputPane() {
        currentCodeFileNum = 1;
//		tabbedPane.removeAll();
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    if (formatCodeInputPane instanceof MainSubFormatCodePane) {
                        tabbedPane.remove(i);
                        i = 0;
                    }
                }
            }
        }
    }

    public void clearAllCodePane() {
        currentCodeFileNum = 0;
        tabbedPane.removeAll();
    }

}
