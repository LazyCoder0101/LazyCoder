package com.lazycoder.uidatasourceedit.formatedit.additional;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.FormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LineNumFormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.AdditionalDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.AdditionalSubFormatCodePane;
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

public class AdditionalCodeFormatPutPane extends ImportCodeFileEditPane
        implements AdditionalPaneInterface, CheckInterface {

    /**
     *
     */
    private static final long serialVersionUID = 2710203691458915331L;

    private AdditionalDeafaultFormatCodePane additionalDeafaultFormatCodePane;

    private FormatModel additionalFormatModel;

    /**
     * 可选模板的标识编号
     */
    private int additionalSerialNumber = 0;

    public AdditionalCodeFormatPutPane(AdditionalDeafaultFormatCodePane additionalDeafaultFormatCodePane, int additionalSerialNumber) {
        // TODO Auto-generated constructor stub
        super("可选模板代码文件");
        this.additionalDeafaultFormatCodePane = additionalDeafaultFormatCodePane;
        this.additionalSerialNumber = additionalSerialNumber;
    }

    public void setAdditionalFormatModel(FormatModel additionalFormatModel) {
        this.additionalFormatModel = additionalFormatModel;
    }

    /**
     * 设置默认代码文件的文件名
     */
    public void setAdditionalDeafaultCodeFileName() {
        String fileName = LazyCoderOptionPane.showInputDialog(null, "用户选择当前可选模板生成代码时，设置的文件名会以当前输入文件名后缀为后缀",
                "设置可选模板默认源文件的文件名", JOptionPane.PLAIN_MESSAGE);
        if ("".equals(fileName)) {
            LazyCoderOptionPane.showMessageDialog(null, "添加的源文件名不能为空！");
        } else {
            if (FileUtil.isValidFileName(fileName) == true) {
                boolean flag = checkForThisFile(fileName);
                if (flag == true) {
                    LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
                } else {
                    if (FileUtil.haveExOrNot(fileName) == true) {
                        LineNumFormatCodeScrollPane scrollPane;
                        AbstractFormatCodeInputPane formatCodeInputPane;
                        AdditionalDeafaultFormatCodePane additionalDeafaultFormatCodePane = null;
                        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
                            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                                if (formatCodeInputPane != null) {
                                    if (formatCodeInputPane instanceof AdditionalDeafaultFormatCodePane) {
                                        getTabbedPane().setTitleAt(i,
                                                HTMLText.createHtmlContent(fileName, HtmlPar.BLUE, false));
                                        additionalDeafaultFormatCodePane = (AdditionalDeafaultFormatCodePane) formatCodeInputPane;
                                        additionalDeafaultFormatCodePane.setName(fileName);
                                        additionalDeafaultFormatCodePane.getFileParam().setFileName(fileName);
                                        additionalDeafaultFormatCodePane
                                                .setDefaultFilenameSetting(AdditionalDeafaultFormatCodePane.TRUE_);
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
    public void displayAdditionalContent(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel) {
        // TODO Auto-generated method stub
        displayDefaultFormatCodeFile(additionalMetaModel);
        displaySubFormatCodeFileList(additionalInfo, additionalMetaModel);
    }

    /**
     * 根据codeFormatFlagParam的信息获取对应面板
     *
     * @param codeFileId
     * @return
     */
    public AbstractFormatCodeInputPane getFormatCodeInputPane(String codeFileId) {
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane = null, formatCodeInputPaneTemp;
        CodeFormatFlagParam codeFormatFlagParamTemp;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
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
     * 构建一个名为fileName的其他代码面板
     */
    public void buildingNewAdditionalPane(String fileName) {
        // TODO Auto-generated method stub
        currentCodeFileNum++;
        additionalDeafaultFormatCodePane = new AdditionalDeafaultFormatCodePane(currentCodeFileNum, additionalSerialNumber) {
            @Override
            public void setDeafaultCodeFileName() {
                setAdditionalDeafaultCodeFileName();
            }
        };
        additionalDeafaultFormatCodePane.setName(fileName);
        LazyCoderFormatControl.buildingNewDefaultCodePane(additionalDeafaultFormatCodePane, additionalFormatModel, fileName);
        setAdditionalDefaultCodeFile(additionalDeafaultFormatCodePane, fileName, currentCodeFileNum);
        LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(additionalDeafaultFormatCodePane);
        scrollPane.setName(fileName);
        additionalDeafaultFormatCodePane.setUpdateScrollpane(scrollPane);
        getTabbedPane().superAddTab(HTMLText.createHtmlContent(fileName, HtmlPar.BLUE, false), scrollPane);
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
    private void displayDefaultFormatCodeFile(AdditionalMetaModel additionalMetaModel) {
        GeneralFileFormat defaultCodeFormat = additionalMetaModel.getDefaultCodeFormat();
        if (defaultCodeFormat != null) {
            currentCodeFileNum++;
            additionalDeafaultFormatCodePane = new AdditionalDeafaultFormatCodePane(defaultCodeFormat.getCodeOrdinal(), additionalSerialNumber) {
                @Override
                public void setDeafaultCodeFileName() {
                    setAdditionalDeafaultCodeFileName();
                }
            };
            additionalDeafaultFormatCodePane.setName(defaultCodeFormat.getFileName());
            additionalDeafaultFormatCodePane.setAttribute(defaultCodeFormat);
            additionalDeafaultFormatCodePane.setDefaultFilenameSetting(defaultCodeFormat.getDefaultFilenameSetting());
            LazyCoderFormatControl.buildingOriginalDefaultCodePane(additionalDeafaultFormatCodePane, this.additionalFormatModel,
                    defaultCodeFormat.getFileName(), defaultCodeFormat);
            setAdditionalDefaultCodeFile(additionalDeafaultFormatCodePane, defaultCodeFormat.getFileName(),
                    additionalMetaModel.getDefaultCodeFormat().getAdditionalSerialNumber());

            LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(additionalDeafaultFormatCodePane);
            scrollPane.setName(defaultCodeFormat.getFileName());
            additionalDeafaultFormatCodePane.setUpdateScrollpane(scrollPane);
            getTabbedPane().superAddTab(
                    HTMLText.createHtmlContent(defaultCodeFormat.getFileName(), HtmlPar.BLUE, false),
                    scrollPane, "默认代码文件");
        }
    }

    /**
     * 还原所有子代码面板的内容
     *
     * @param additionalInfo
     * @param additionalMetaModel
     */
    private void displaySubFormatCodeFileList(AdditionalInfo additionalInfo, AdditionalMetaModel additionalMetaModel) {
        List<GeneralFileFormat> subCodeFormatList = additionalMetaModel.getCodeModelList();
        if (subCodeFormatList != null) {
            if (additionalInfo.getNumOfSubCodeFile() > 0) {
                LineNumFormatCodeScrollPane scrollPane;
                AdditionalSubFormatCodePane formatCodePaneTemp;
                for (GeneralFileFormat subTemp : subCodeFormatList) {
                    currentCodeFileNum++;
                    formatCodePaneTemp = new AdditionalSubFormatCodePane(subTemp.getCodeOrdinal(), this.additionalSerialNumber);
                    formatCodePaneTemp.setName(subTemp.getFileName());
                    formatCodePaneTemp.setAttribute(subTemp);
                    LazyCoderFormatControl.buildingOriginalSubCodePane(formatCodePaneTemp, this.additionalFormatModel,
                            subTemp.getFileName(), subTemp);
                    setAdditionalSubCodeFile(formatCodePaneTemp, subTemp.getFileName(), subTemp.getAdditionalSerialNumber(),
                            subTemp.getCodeOrdinal());

                    scrollPane = new LineNumFormatCodeScrollPane(formatCodePaneTemp);
                    scrollPane.setName(subTemp.getFileName());
                    formatCodePaneTemp.setUpdateScrollpane(scrollPane);
                    getTabbedPane().addTab(subTemp.getFileName(), scrollPane);
                }
            }
        }
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
            AdditionalSubFormatCodePane additionalSubFormatCodePane = new AdditionalSubFormatCodePane(currentCodeFileNum, this.additionalSerialNumber);
            additionalSubFormatCodePane.createNewBlankSourceFile(fileName);
            LazyCoderFormatControl.buildingNewSubCodePane(additionalSubFormatCodePane, this.additionalFormatModel,
                    fileName);
            setAdditionalSubCodeFile(additionalSubFormatCodePane, fileName, additionalSerialNumber, currentCodeFileNum);
            LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(additionalSubFormatCodePane);
            scrollPane.setName(fileName);
            additionalSubFormatCodePane.setUpdateScrollpane(scrollPane);
            getTabbedPane().addTab(fileName, scrollPane);
            setSelectedLast();

        }
    }

    /**
     * 打开源文件
     */
    public void openSourceFile() {
//        File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL, "请选择需要导入的懒农数据源文件");
        File file = selectFile();
        if (file != null) {
            boolean flag = checkForThisFile(file.getName());
            if (flag == true) {
                LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
            } else {
                currentCodeFileNum++;
                AdditionalSubFormatCodePane additionalSubFormatCodePane = new AdditionalSubFormatCodePane(currentCodeFileNum, this.additionalSerialNumber);

                additionalSubFormatCodePane.openSourceFile(file);
                additionalSubFormatCodePane.setName(file.getName());
                LazyCoderFormatControl.buildingNewSubCodePane(additionalSubFormatCodePane, this.additionalFormatModel,
                        file.getName());
                setAdditionalSubCodeFile(additionalSubFormatCodePane, file.getName(), additionalSerialNumber, currentCodeFileNum);
                LineNumFormatCodeScrollPane scrollPane = new LineNumFormatCodeScrollPane(additionalSubFormatCodePane);
                scrollPane.setName(file.getName());
                additionalSubFormatCodePane.setUpdateScrollpane(scrollPane);
                getTabbedPane().addTab(file.getName(), scrollPane);
                setSelectedLast();
            }
        }
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        boolean flag = true;
        LineNumFormatCodeScrollPane scrollPane;
        AdditionalDeafaultFormatCodePane deafaultFormatCodePane;
        AbstractFormatCodeInputPane componentTemp;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                componentTemp = scrollPane.getTheFormatCodeInputPane();
                if (componentTemp != null) {
                    if (componentTemp instanceof AdditionalDeafaultFormatCodePane) {
                        deafaultFormatCodePane = (AdditionalDeafaultFormatCodePane) componentTemp;
                        if (deafaultFormatCodePane.getDefaultFilenameSetting() == AdditionalDeafaultFormatCodePane.FALSE_) {
                            flag = false;
                            LazyCoderOptionPane.showMessageDialog(null,
                                    "╮(╯3╰)╭  其他" + additionalSerialNumber + "还没设置默认代码文件的文件名，再检查一下，写完了再保存把");
                            break;

                        }

                    }
                    if (componentTemp.getUseState() == false) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "╮(╯3╰)╭  其他" + additionalSerialNumber + "的\""
                                + componentTemp.getName() + "\"什么都还没写，写完了再保存吧");
                        break;
                    }
                }
            }
        }
        return flag;
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

    /**
     * 获取该其他部分所有文件格式
     *
     * @return
     */
    public List<GeneralFileFormat> getCodeFormatList() {
        List<GeneralFileFormat> list = new ArrayList<>();
        LineNumFormatCodeScrollPane scrollPane;
        AdditionalDeafaultFormatCodePane deafaultFormatCodePane;
        AdditionalSubFormatCodePane subFormatCodePane;
        AbstractFormatCodeInputPane componentTemp;
        GeneralFileFormat codeFormat;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                componentTemp = scrollPane.getTheFormatCodeInputPane();
                if (componentTemp != null) {
                    if (componentTemp instanceof AdditionalDeafaultFormatCodePane) {
                        deafaultFormatCodePane = (AdditionalDeafaultFormatCodePane) componentTemp;
                        codeFormat = deafaultFormatCodePane.getAdditionalCodeFormat(i + 1);
                        codeFormat.setAdditionalSerialNumber(additionalSerialNumber);
                        codeFormat.setFormatType(GeneralFileFormat.ADDITIONAL_TYPE);
                        list.add(codeFormat);

                    } else if (componentTemp instanceof AdditionalSubFormatCodePane) {
                        subFormatCodePane = (AdditionalSubFormatCodePane) componentTemp;
                        codeFormat = subFormatCodePane.getAdditionalCodeFormat(i + 1);
                        codeFormat.setFormatType(GeneralFileFormat.ADDITIONAL_TYPE);
                        codeFormat.setAdditionalSerialNumber(additionalSerialNumber);
                        list.add(codeFormat);
                    }
                }
            }
        }
        return list;
    }

    public void clearAllSubCodeInputPane() {
        currentCodeFileNum = 0;
        LineNumFormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof LineNumFormatCodeScrollPane) {
                scrollPane = (LineNumFormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    if (formatCodeInputPane instanceof AdditionalSubFormatCodePane) {
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

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        additionalInfo.setNumOfSubCodeFile(getNumOfSubCodeFile());
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

}
