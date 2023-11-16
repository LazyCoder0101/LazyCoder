package com.lazycoder.uidatasourceedit.component.component;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.FormatCodeScrollPane;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BaseUseCodeFileEditPane extends JPanel implements CodeFileEditPaneInterface {

    /**
     *
     */
    private static final long serialVersionUID = -8179790018566087388L;

    protected CodeTabbedPane tabbedPane;
    

    public BaseUseCodeFileEditPane(String labelText) {
        setLayout(new BorderLayout(0, 0));

        JLabel label = new JLabel(labelText);
        add(label, BorderLayout.NORTH);

        tabbedPane = getCodeTabbedPane();
        FlatTabbedPaneUI tabbedPaneUI = new FlatTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                showTabSeparators = true;
            }
        };
        tabbedPane.setUI(tabbedPaneUI);
        // tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * 设置为必填模板默认代码文件
     *
     * @param pane
     * @param fileName
     */
    public static void setMainDefaultCodeFile(AbstractFormatCodeInputPane pane, String fileName) {
        CodeFormatFlagParam fileParam = new CodeFormatFlagParam();
        fileParam.setFormatType(CodeFormatFlagParam.MAIN_TYPE);
        fileParam.setFileType(CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE);
        fileParam.setFileName(fileName);
        fileParam.setId(pane.getCodeFileId());
        pane.setFileParam(fileParam);
    }

    /**
     * 设置为必填模板子代码文件
     *
     * @param pane
     * @param fileName
     * @param codeOrdinal
     */
    public static void setMainSubCodeFile(AbstractFormatCodeInputPane pane, String fileName, int codeOrdinal) {
        CodeFormatFlagParam fileParam = new CodeFormatFlagParam();
        fileParam.setFormatType(CodeFormatFlagParam.MAIN_TYPE);
        fileParam.setFileType(CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE);
        fileParam.setFileName(fileName);
        fileParam.setCodeOrdinal(codeOrdinal);
        fileParam.setId(pane.getCodeFileId());
        pane.setFileParam(fileParam);
    }

    /**
     * 设置为可选模板默认代码文件
     *
     * @param pane
     * @param fileName
     * @param ordinal
     */
    public static void setAdditionalDefaultCodeFile(AbstractFormatCodeInputPane pane, String fileName, int ordinal) {
        CodeFormatFlagParam fileParam = new CodeFormatFlagParam();
        fileParam.setFormatType(CodeFormatFlagParam.ADDITIONAL_TYPE);
        fileParam.setFileType(CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE);
        fileParam.setFileName(fileName);
        fileParam.setAdditionalSerialNumber(ordinal);
        fileParam.setId(pane.getCodeFileId());
        pane.setFileParam(fileParam);
    }

    /**
     * 设置为可选模板子代码文件
     *
     * @param pane
     * @param fileName
     * @param ordinal
     * @param codeOrdinal
     */
    public static void setAdditionalSubCodeFile(AbstractFormatCodeInputPane pane, String fileName, int ordinal,
                                                int codeOrdinal) {
        CodeFormatFlagParam fileParam = new CodeFormatFlagParam();
        fileParam.setFormatType(CodeFormatFlagParam.ADDITIONAL_TYPE);
        fileParam.setFileType(CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE);
        fileParam.setFileName(fileName);
        fileParam.setAdditionalSerialNumber(ordinal);
        fileParam.setCodeOrdinal(codeOrdinal);
        fileParam.setId(pane.getCodeFileId());
        pane.setFileParam(fileParam);
    }

    /**
     * 设置为模块代码文件
     *
     * @param pane
     * @param fileName
     * @param codeOrdinal
     * @param moduleId
     */
    public static void setModuleCodeFile(AbstractFormatCodeInputPane pane, String fileName, int codeOrdinal,
                                         String moduleId) {
        CodeFormatFlagParam fileParam = new CodeFormatFlagParam();
        fileParam.setId(pane.getCodeFileId());
        fileParam.setFormatType(CodeFormatFlagParam.MODULE_TYPE);
        fileParam.setFileType(CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE);
        fileParam.setFileName(fileName);
        fileParam.setCodeOrdinal(codeOrdinal);
        fileParam.setModuleId(moduleId);
        pane.setFileParam(fileParam);
    }

    protected CodeTabbedPane getCodeTabbedPane() {
        return new CodeTabbedPane();
    }

    public CodeTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * 删除添加到里面的面板
     */
    public void deletePane() {
    }

    /**
     * 查看有没有这文件
     *
     * @param fileName
     * @return
     */
    protected boolean checkForThisFile(String fileName) {
        boolean flag = false;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            if (fileName.equals(tabbedPane.getTitleAt(i))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 检查一下添加的文件，看看有没有问题
     *
     * @return
     */
    protected boolean checkAddFile(String fileName) {
        boolean flag = true;
        if (fileName == null || "".equals(fileName.trim())) {
            LazyCoderOptionPane.showMessageDialog(null, "添加的源文件名不能为空！");
        } else {
            fileName = fileName.trim();
            if (FileUtil.haveExOrNot(fileName) == true) {
                if (FileUtil.isValidFileName(fileName) == true) {
                    boolean checkTemp = checkForThisFile(fileName);
                    if (checkTemp == true) {
                        flag = false;
                        LazyCoderOptionPane.showMessageDialog(null, "已添加过该文件");
                    }
                } else {
                    flag = false;
                    LazyCoderOptionPane.showMessageDialog(null, "(ಥ_ಥ)  这文件名。。。好像不太对吧，换一个吧");
                }
            } else {
                flag = false;
                LazyCoderOptionPane.showMessageDialog(null, "这文件的后缀名麻烦写一下。	ヽ(ー_ー)ノ！");
            }
        }
        return flag;
    }

    /**
     * 选中最后一个
     */
    public void setSelectedLast() {
        if (tabbedPane.getComponentCount() > 0) {
            tabbedPane.setSelectedIndex(tabbedPane.getComponentCount() - 1);
        }
    }

    /**
     * 选中第？个
     */
    public void setSelectedIndex(int index) {
        if (tabbedPane.getComponentCount() > 0) {
            tabbedPane.setSelectedIndex(index);
        }
    }

    /**
     * 获取当前代码文件参数
     *
     * @return
     */
    public ArrayList<CodeFormatFlagParam> getCurrentCodeFormatParam() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        FormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPaneTemp;
        for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
            if (tabbedPane.getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) tabbedPane.getComponent(i);
                formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPaneTemp != null) {
                    list.add(formatCodeInputPaneTemp.getFileParam());
                }
            }
        }
        return list;
    }

    /**
     * 把代码文件的参数排序
     *
     * @param codeFormatFlagParams
     */
    public ArrayList<CodeFormatFlagParam> sortCodeFormatParamList(ArrayList<CodeFormatFlagParam> codeFormatFlagParams) {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        for (CodeFormatFlagParam temp : codeFormatFlagParams) {// 先把必填模板的拿出来放到签名
            if (CodeFormatFlagParam.MAIN_TYPE == temp.getFormatType()) {
                list.add(temp);
            }
        }
        ArrayList<Integer> additionalSerialNumberList = ModuleEditPaneHolder.usingRange
                .getAdditionalSerialNumberThatHaveBeenSelected();
        for (Integer additionalSerialNumber : additionalSerialNumberList) {// 再放可选模板的
            for (CodeFormatFlagParam temp : codeFormatFlagParams) {
                if (CodeFormatFlagParam.ADDITIONAL_TYPE == temp.getFormatType()) {
                    if (temp.getAdditionalSerialNumber() == additionalSerialNumber) {
                        list.add(temp);
                    }
                }
            }
        }

        ArrayList<Module> moduleList = ModuleEditPaneHolder.relatedModuleInfoMenu.getSelectedNeedUseModuleList();
        for (Module module : moduleList) {
            for (CodeFormatFlagParam temp : codeFormatFlagParams) {// 最后把各个模块的归类依次放进去
                if (CodeFormatFlagParam.MODULE_TYPE == temp.getFormatType()) {
                    if (module.getModuleId().equals(temp.getModuleId())) {
                        list.add(temp);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 定位到对应的标志
     *
     * @param list            对应的路径参数
     * @param thanMarkElement 比对标签
     * @param navigateOrNot
     * @return
     */
    @Override
    public boolean navigateToTheCorrespondingMark(ArrayList<CodeFormatFlagParam> list, BaseMarkElement thanMarkElement,
                                                  boolean navigateOrNot) {
        FormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPaneTemp;
        boolean flag = true, checkFlag = checkCurrentSelectedIS(list);
        CodeFormatFlagParam codeFormatFlagParamTemp2;
        for (CodeFormatFlagParam codeFormatFlagParamTemp1 : list) {

            for (int i = 0; i < tabbedPane.getComponentCount(); i++) {
                if (tabbedPane.getComponent(i) instanceof FormatCodeScrollPane) {
                    scrollPane = (FormatCodeScrollPane) tabbedPane.getComponent(i);
                    formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
                    if (formatCodeInputPaneTemp != null) {
                        codeFormatFlagParamTemp2 = formatCodeInputPaneTemp.getFileParam();

                        if (CodeFormatFlagParam.compare(codeFormatFlagParamTemp1, codeFormatFlagParamTemp2) == true) {// 如果要找的就是这个文件
                            if (formatCodeInputPaneTemp.navigateToTheCorrespondingMark(thanMarkElement,
                                    navigateOrNot) == false) {
                                flag = false;
                            }
                            if (checkFlag == false) {
                                getTabbedPane().setSelectedIndex(i);
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 定位到方法标记
     *
     * @param navigateOrNot
     * @return
     */
    public boolean navigateToFunctionMark(boolean navigateOrNot) {
        boolean flag = true, selectedFlag = true;

        FormatCodeScrollPane scrollPane;
        AbstractFormatCodeInputPane formatCodeInputPaneTemp;
        CodeFormatFlagParam codeFormatFlagParamTemp;

        FunctionMarkElement thanMarkElement = new FunctionMarkElement();

        Component selectedComponent = tabbedPane.getSelectedComponent();
        if (selectedComponent!=null&&selectedComponent instanceof FormatCodeScrollPane){
            scrollPane = (FormatCodeScrollPane) selectedComponent;
            formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
            if (formatCodeInputPaneTemp != null) {
                codeFormatFlagParamTemp = formatCodeInputPaneTemp.getFileParam();
                if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp.getFileType()) {
                    //如果当前的代码面板就是默认面板，定位里面的方法组件
                    if (formatCodeInputPaneTemp.navigateToTheCorrespondingMark(thanMarkElement,
                            navigateOrNot) == false) {
                        flag = false;
                    }
                }else {//如果当前代码面板不是默认面板，一个个找，找到默认面板并定位里面的方法组件
                    for (int i = 0; i < tabbedPane.getComponentCount(); i++) {//定位到
                        if (tabbedPane.getComponent(i) instanceof FormatCodeScrollPane) {
                            scrollPane = (FormatCodeScrollPane) tabbedPane.getComponent(i);
                            formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
                            if (formatCodeInputPaneTemp != null) {
                                codeFormatFlagParamTemp = formatCodeInputPaneTemp.getFileParam();
                                if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParamTemp.getFileType()) {
                                    if (CodeFormatFlagParam.MODULE_TYPE != codeFormatFlagParamTemp.getFormatType()) {
                                        if (formatCodeInputPaneTemp.navigateToTheCorrespondingMark(thanMarkElement,
                                                navigateOrNot) == false) {
                                            flag = false;
                                        }
                                        if (selectedFlag == true) {
                                            getTabbedPane().setSelectedIndex(i);
                                            selectedFlag = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 定位到对应的引入标记
     *
     * @param thanMarkElement
     * @param navigateOrNot
     * @return
     */
    public boolean navigateToTheCorrespondingImportMark(BaseMarkElement thanMarkElement, boolean navigateOrNot) {
        boolean flag = true;
        if (tabbedPane.getSelectedIndex() >= 0) {
            if (tabbedPane.getComponent(tabbedPane.getSelectedIndex()) instanceof FormatCodeScrollPane) {
                FormatCodeScrollPane scrollPane = (FormatCodeScrollPane) tabbedPane.getSelectedComponent();
                AbstractFormatCodeInputPane formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPaneTemp != null) {
                    if (formatCodeInputPaneTemp.navigateToTheCorrespondingMark(thanMarkElement,
                            navigateOrNot) == false) {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 检查当前选中的是不是在要查的范围内
     *
     * @param list
     * @return
     */
    private boolean checkCurrentSelectedIS(ArrayList<CodeFormatFlagParam> list) {
        boolean flag = false;
        CodeFormatFlagParam selectedCcodeFormatFlagParamTemp = getCurrentFormatFileInfo();
        for (CodeFormatFlagParam temp : list) {
            if (selectedCcodeFormatFlagParamTemp != null) {
                if (CodeFormatFlagParam.compare(selectedCcodeFormatFlagParamTemp, temp)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 获取当前选中文件的信息
     *
     * @return
     */
    private CodeFormatFlagParam getCurrentFormatFileInfo() {
        CodeFormatFlagParam codeFormatFlagParamTemp = null;
        if (tabbedPane.getTabCount() > 0) {
            if (tabbedPane.getComponent(tabbedPane.getSelectedIndex()) instanceof FormatCodeScrollPane) {
                FormatCodeScrollPane scrollPane = (FormatCodeScrollPane) tabbedPane
                        .getComponent(tabbedPane.getSelectedIndex());
                AbstractFormatCodeInputPane formatCodeInputPaneTemp = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPaneTemp != null) {
                    codeFormatFlagParamTemp = formatCodeInputPaneTemp.getFileParam();
                }
            }
        }
        return codeFormatFlagParamTemp;
    }

}
