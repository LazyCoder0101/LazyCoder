package com.lazycoder.uidatasourceedit.moduleedit.codepane;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FormatCodeFileServiceImpl;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uidatasourceedit.ModuleEditPaneHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.FormatCodeScrollPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.ModuleFileFormatCodePane;
import com.lazycoder.uidatasourceedit.component.component.BaseUseCodeFileEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class NeedUseCodeFileEditPane extends BaseUseCodeFileEditPane
        implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -3678458982400432172L;

    /**
     * 没添加过这类型的文件
     */
    private static final int THIS_FILE_HAS_NOT_BEEN_ADDED = -6;

    private static Color noNeedImportModuleCodePaneBackground = Color.WHITE,
            noNeedImportModuleCodePaneForeground = new Color(64, 158, 255);

    private int currentFileNum = 0;

    public NeedUseCodeFileEditPane() {
        super("引入源码");
        // TODO Auto-generated constructor stub
    }

    /**
     * 根据对应信息移除代码面板
     *
     * @param codeFormatFlagParam
     */
    public void removeCodeFilePane(CodeFormatFlagParam codeFormatFlagParam) {
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (CodeFormatFlagParam.compare(codeFormatFlagParam, tempCodeFormatFlagParam) == true) {
                        getTabbedPane().remove(i);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 移除某些模块的代码面板文件
     *
     * @param moduleList
     */
    public void removeModuleFilePane(ArrayList<Module> moduleList) {
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (Module temp : moduleList) {
            for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
                if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                    scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                    formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                    if (formatCodeInputPane != null) {
                        tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                        if (CodeFormatFlagParam.MODULE_TYPE == tempCodeFormatFlagParam.getFormatType()) {
                            if (tempCodeFormatFlagParam.getModuleId().equals(temp.getModuleId())) {
                                getTabbedPane().remove(i);
                                i = -1;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 移除必填模板代码
     *
     * @return
     */
    public void removeAllMainCodePane() {
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (tempCodeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MAIN_TYPE) {
                        getTabbedPane().remove(i);
                        i = -1;
                    }
                }
            }
        }
    }

    /**
     * 移除可选模板代码
     *
     * @return
     */
    public void removeAllAdditionalCodePane(int additionalSerialNumber) {
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (tempCodeFormatFlagParam.getFormatType() == CodeFormatFlagParam.ADDITIONAL_TYPE) {
                        if (tempCodeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                            getTabbedPane().remove(i);
                            i = -1;
                        }
                    }
                }
            }
        }
    }

    /**
     * 查看有没有添加过这个代码格式文件
     *
     * @return
     */
    private boolean checkTheCodeFormatFileWasAddedOrNot(CodeFormatFlagParam CodeFormatFlagParam) {
        boolean flag = false;
        AbstractFormatCodeInputPane formatCodeInputPane;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    if (formatCodeInputPane.getCodeFileId().equals(CodeFormatFlagParam.getId())) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 查看有没有添加过这种类型的代码格式文，如果有，返回位置
     *
     * @param CodeFormatFlagParam
     * @return
     */
    private int checkTheCodeFormatTypeWasAddedOrNot(CodeFormatFlagParam CodeFormatFlagParam) {
        int location = THIS_FILE_HAS_NOT_BEEN_ADDED;
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (tempCodeFormatFlagParam.getFormatType() == CodeFormatFlagParam.getFormatType()) {// 先判断类型是否相同

                        if (DataFormatType.MAIN_TYPE == CodeFormatFlagParam.getFormatType()) {// 必填模板类型情况下
                            location = i + 1;

                        } else if (DataFormatType.ADDITIONAL_TYPE == CodeFormatFlagParam.getFormatType()) {
                            if (CodeFormatFlagParam.getAdditionalSerialNumber() == tempCodeFormatFlagParam
                                    .getAdditionalSerialNumber()) {// Ordinal相同
                                location = i + 1;
                            }

                        } else if (DataFormatType.MODULE_TYPE == CodeFormatFlagParam.getFormatType()) {// 模块类型
                            if (CodeFormatFlagParam.getModuleId().equals(tempCodeFormatFlagParam.getModuleId())) {// 判断模块名是否相同
                                location = i + 1;
                            }
                        }
                    }
                }
            }
        }
        return location;
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        List<CodeFormatFlagParam> needUseCodeForamtList = ModuleInfoStaticMethod
                .getAdditionalCodeFilesParamsThatNeedToBeUsed(moduleInfo);//查看该模块需要什么代码文件
        if (needUseCodeForamtList != null) {
            addNeedUseCodeFormatPane(needUseCodeForamtList);
        }
        setModuleCodeFilePaneStyte();
    }

    /**
     * 根据模块加载对应代码
     *
     * @param moduleList
     */
    public void addNeedUseCodeFormatPaneByModuleList(List<Module> moduleList) {
        if (moduleList != null) {//获取各个模块对应的代码文件数据
            List<GeneralFileFormat> list = new ArrayList<>();
            for (Module moduleTemp : moduleList) {
                List<GeneralFileFormat> moduleCodeFormatList = SysService.MODULE_FILE_FORMAT_SERVICE
                        .getModuleFileFormatList(moduleTemp.getModuleId());
                if (moduleCodeFormatList != null) {
                    if (moduleCodeFormatList != null) {
                        for (GeneralFileFormat formatFile : moduleCodeFormatList) {
                            list.add(formatFile);
                        }
                    }
                }
            }
            FormatCodeScrollPane scrollPane;
            ModuleFileFormatCodePane teModuleFileFormatCodePane;
            CodeFormatFlagParam codeFormatFlagParam;
            ArrayList<BaseElementInterface> codeElementList;
            for (GeneralFileFormat formatCodeFile : list) {//按一个个代码文件生成对应的模块的面板
                boolean flag = checkTheCodeFormatFileWasAddedOrNot(formatCodeFile);
                // 查看有没有添加过该文件
                if (flag == false) {
                    int getLocation = checkTheCodeFormatTypeWasAddedOrNot(formatCodeFile);

                    teModuleFileFormatCodePane = new ModuleFileFormatCodePane(formatCodeFile.getCodeOrdinal());
                    teModuleFileFormatCodePane.setName(formatCodeFile.getFileName());
                    teModuleFileFormatCodePane.setAttribute(formatCodeFile);

                    teModuleFileFormatCodePane.setCodeFileId(formatCodeFile.getId());

                    codeFormatFlagParam = FormatCodeFileServiceImpl.getCodeFormatFlagParam(formatCodeFile);
                    teModuleFileFormatCodePane.setFileParam(codeFormatFlagParam);

                    codeElementList = DeserializeElementMethods.getCodeFilePaneElementList(formatCodeFile.getFormatContent());
                    teModuleFileFormatCodePane.reductionContent(codeElementList);

                    scrollPane = new FormatCodeScrollPane(teModuleFileFormatCodePane);
                    teModuleFileFormatCodePane.setUpdateScrollpane(scrollPane);
                    addTabCodeFormatPane(formatCodeFile.getFileName(), scrollPane, codeFormatFlagParam, getLocation);
                }
            }
        }
        setModuleCodeFilePaneStyte();
    }

    /**
     * 设置其他代码文件使用到的相关参数
     *
     * @param moduleInfo
     */
    public void setAdditionalRelatedParam(ModuleInfo moduleInfo) {
        setAdditionalCodeFilesParamsThatNeedToBeUsed(moduleInfo);
        setTheNumOfAdditionalCodeFilesParamsThatNeedToBeUsed(moduleInfo);
    }

    /**
     * 记录需要使用的代码文件参数
     *
     * @param moduleInfo
     */
    private void setAdditionalCodeFilesParamsThatNeedToBeUsed(ModuleInfo moduleInfo) {
        ArrayList<CodeFormatFlagParam> needUseCodeForamtList = new ArrayList<>();
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    needUseCodeForamtList.add(tempCodeFormatFlagParam);
                }
            }
        }
        moduleInfo.setAdditionalCodeFilesParamsThatNeedToBeUsed(JsonUtil.getJsonStr(needUseCodeForamtList));
    }

    /**
     * 设置使用的其他的文件的数量
     *
     * @param moduleInfo
     */
    private void setTheNumOfAdditionalCodeFilesParamsThatNeedToBeUsed(ModuleInfo moduleInfo) {
        moduleInfo.setTheNumOfAdditionalCodeFilesParamsThatNeedToBeUsed(getTabbedPane().getComponentCount());
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
        getTabbedPane().removeAll();
    }

    /**
     * 获取需要录入的必填模板代码文件数据
     *
     * @return
     */
    public ArrayList<GeneralFileFormat> getMainCodeFileList() {
        ArrayList<GeneralFileFormat> codeFileList = null;
        if (ModuleEditPaneHolder.usingRange.chekMainUsingRangeThatHaveBeenSelected() == true) {// 查看有没有必填模板对应的代码文件，有的话，保存
            codeFileList = FormatEditPaneHolder.mainCodePane.getFormatCodeList();
        }
        return codeFileList;
    }

    /**
     * 获取需要录入的可选模板的代码文件数据
     *
     * @return
     */
    public List<GeneralFileFormat> getAdditionalCodeFileList() {
        List<GeneralFileFormat> codeFileList = null;
        ArrayList<Integer> oList = ModuleEditPaneHolder.usingRange.getAdditionalSerialNumberThatHaveBeenSelected();// 查看有没有可选模板对应的代码文件，有的话，保存
        if (oList.size() > 0) {
            codeFileList = FormatEditPaneHolder.additionalEditPane.getAdditionalCodeFormatList();
        }
        return codeFileList;
    }

    /**
     * 重新加载格式代码面板
     */
    public void reloadFormatCodeInputPane() {
        Component component;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            component = getTabbedPane().getComponent(i);
            if (component instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) component;
                scrollPane.reloadFormatCodeInputPane();
            }
        }
    }

    /**
     * 把代码面板添加到TabbedPane上
     *
     * @param fileName
     * @param component
     * @param codeFormatFlagParam
     * @param index
     */
    private void addTabCodeFormatPane(String fileName, Component component, CodeFormatFlagParam codeFormatFlagParam,
                                      int index) {
        if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()
                || CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
            currentFileNum++;
            if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                String theTitle = HTMLText.createHtmlContent(fileName, HtmlPar.BLUE, false);
                if (index == THIS_FILE_HAS_NOT_BEEN_ADDED) {
                    getTabbedPane().addTab(theTitle, component);
                } else {
                    getTabbedPane().insertCloseTab(theTitle, component, index);
                }
            } else if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                if (index == THIS_FILE_HAS_NOT_BEEN_ADDED) {
                    getTabbedPane().addTab(fileName, component);
                } else {
                    getTabbedPane().insertCloseTab(fileName, component, index);
                }
            }
        } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
            currentFileNum++;
            if (index == THIS_FILE_HAS_NOT_BEEN_ADDED) {
                getTabbedPane().addTab(fileName, component);
            } else {
                getTabbedPane().insertCloseTab(fileName, component, index);
            }
        }
    }

    private void setModuleCodeFilePaneStyte() {
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (tempCodeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {
                        getTabbedPane().setBackgroundAt(i, noNeedImportModuleCodePaneBackground);
                        getTabbedPane().setForegroundAt(i, noNeedImportModuleCodePaneForeground);
                    }
                }
            }
        }
    }

    /**
     * 根据list 里面的参数一个个查，如果原来有添加过，不做处理，如果没添加过，加载出来
     *
     * @param list
     */
    private void addNeedUseCodeFormatPane(List<CodeFormatFlagParam> list) {
        ModuleFileFormatCodePane teModuleFileFormatCodePane;
        ArrayList<BaseElementInterface> codeElementList;
        for (CodeFormatFlagParam codeFormatFlagParam : list) {
            boolean flag = checkTheCodeFormatFileWasAddedOrNot(codeFormatFlagParam);
            // 查看有没有添加过该文件
            if (flag == false) {
                int getLocation = checkTheCodeFormatTypeWasAddedOrNot(codeFormatFlagParam);
                // 查看当前有没有添加过该类型的文件，有的话，返回位置
                FormatCodeScrollPane scrollPane;
                if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                    GeneralFileFormat tempCodeFormat = SysService.MODULE_FILE_FORMAT_SERVICE.getFormatCodeFileById(codeFormatFlagParam.getId());

                    if (tempCodeFormat != null) {
                        teModuleFileFormatCodePane = new ModuleFileFormatCodePane(codeFormatFlagParam.getCodeOrdinal());
                        teModuleFileFormatCodePane.setName(tempCodeFormat.getFileName());
                        teModuleFileFormatCodePane.setAttribute(tempCodeFormat);

                        teModuleFileFormatCodePane.setCodeFileId(codeFormatFlagParam.getId());
                        teModuleFileFormatCodePane.setFileParam(codeFormatFlagParam);

                        codeElementList = DeserializeElementMethods.getCodeFilePaneElementList(tempCodeFormat.getFormatContent());
                        teModuleFileFormatCodePane.reductionContent(codeElementList);

                        scrollPane = new FormatCodeScrollPane(teModuleFileFormatCodePane);
                        teModuleFileFormatCodePane.setUpdateScrollpane(scrollPane);
                        addTabCodeFormatPane(codeFormatFlagParam.getFileName(), scrollPane, codeFormatFlagParam, getLocation);
                    }
                } else if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()
                        || CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                    AbstractFormatCodeInputPane formatCodeInputPane = DataSourceEditHolder.formatEditPane.getFormatCodeInputPane(codeFormatFlagParam);
                    if (formatCodeInputPane != null) {
                        scrollPane = new FormatCodeScrollPane(formatCodeInputPane);
                        formatCodeInputPane.setUpdateScrollpane(scrollPane);
                        addTabCodeFormatPane(codeFormatFlagParam.getFileName(), scrollPane, codeFormatFlagParam, getLocation);
                    }
                }
            }
        }
    }

    /**
     * 获取需要更改的其他文件的代码文件数据
     *
     * @return
     */
    public ArrayList<GeneralFileFormat> getNeedUpdateModuleCodeFileListDataForAdditional() {
        boolean flag = false;
        ArrayList<GeneralFileFormat> codeFileList = null;
        // 找出里面各个模块的代码面板，一个个更新
        AbstractFormatCodeInputPane formatCodeInputPane;
        CodeFormatFlagParam tempCodeFormatFlagParam;
        FormatCodeScrollPane scrollPane;
        GeneralFileFormat formatFile;
        for (int i = 0; i < getTabbedPane().getComponentCount(); i++) {
            if (getTabbedPane().getComponent(i) instanceof FormatCodeScrollPane) {
                scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(i);
                formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                if (formatCodeInputPane != null) {
                    tempCodeFormatFlagParam = formatCodeInputPane.getFileParam();
                    if (tempCodeFormatFlagParam.getFormatType() == CodeFormatFlagParam.MODULE_TYPE) {

                        if (flag == false) {//如果有用到其他模块的代码文件，第一次扫到时先构造 codeFileList
                            codeFileList = new ArrayList<>();
                            flag = true;
                        }
                        formatFile = GeneralFileFormat.createModuleFormatFile();
                        formatCodeInputPane.setCodeModel(formatFile, tempCodeFormatFlagParam.getCodeOrdinal());
                        codeFileList.add(formatFile);
                    }
                }
            }
        }
        return codeFileList;
    }

    /**
     * 根据使用范围加载对应代码
     *
     * @param usingObjectList
     */
    public void addNeedUseCodeFormatPaneByUsing(List<UsingObject> usingObjectList) {
        ArrayList<AbstractFormatCodeInputPane> list = new ArrayList<>(), tempList;
        for (UsingObject usingObject : usingObjectList) {
            if (UsingObject.MAIN_TYPE == usingObject.getType()) {
                tempList = FormatEditPaneHolder.mainCodePane.getAllormatCodeInputPane();
                list.addAll(tempList);

            } else if (UsingObject.ADDITIONAL_TYPE == usingObject.getType()) {
                int additionalSerialNumber = usingObject.getSerial();
                tempList = FormatEditPaneHolder.additionalEditPane.getAllormatCodeInputPane(additionalSerialNumber);
                list.addAll(tempList);
            }
        }
        FormatCodeScrollPane scrollPane;
        for (AbstractFormatCodeInputPane formatCodeInputPane : list) {
            boolean flag = checkTheCodeFormatFileWasAddedOrNot(formatCodeInputPane.getFileParam());
            // 查看有没有添加过该文件
            if (flag == false) {
                int getLocation = checkTheCodeFormatTypeWasAddedOrNot(formatCodeInputPane.getFileParam());
                scrollPane = new FormatCodeScrollPane(formatCodeInputPane);
                addTabCodeFormatPane(formatCodeInputPane.getFileParam().getFileName(), scrollPane, formatCodeInputPane.getFileParam(), getLocation);
            }
        }
    }

    @Override
    protected CodeTabbedPane getCodeTabbedPane() {
        return new NeedUseCodeFileDelOptionPaneCodeTabbedPane();
    }


    class NeedUseCodeFileDelOptionPaneCodeTabbedPane extends CodeTabbedPane {

        /**
         *
         */
        private static final long serialVersionUID = 2150112491292810160L;


        public NeedUseCodeFileDelOptionPaneCodeTabbedPane() {
            // TODO Auto-generated constructor stub
            super();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            if (pressTheCloseButton(e) == true) {
                int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
                if (this.getComponent(tabNumber) instanceof FormatCodeScrollPane) {
                    FormatCodeScrollPane scrollPane = (FormatCodeScrollPane) getTabbedPane().getComponent(tabNumber);
                    AbstractFormatCodeInputPane formatCodeInputPane = scrollPane.getTheFormatCodeInputPane();
                    String fileName = formatCodeInputPane.getName();
                    int n = LazyCoderOptionPane.showConfirmDialog(null,
                            "确定这个模块不需要使用\"" + fileName + "\"这个代码文件吗？", "系统消息", JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION) {
                        super.mouseClicked(e);
                    }
                }
            }
        }

    }


}
