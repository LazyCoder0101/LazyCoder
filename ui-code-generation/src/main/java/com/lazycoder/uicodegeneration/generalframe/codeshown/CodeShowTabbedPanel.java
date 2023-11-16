package com.lazycoder.uicodegeneration.generalframe.codeshown;

import com.formdev.flatlaf.ui.FlatTabbedPaneUI;
import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.CodeShowPaneModel;
import com.lazycoder.uiutils.mycomponent.CodeShowTextArea;
import com.lazycoder.uiutils.mycomponent.CodeTabbedPane;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class CodeShowTabbedPanel extends CodeTabbedPane {

    /**
     *
     */
    private static final long serialVersionUID = 9222047035854139130L;

    /**
     * Create the panel.
     */
    public CodeShowTabbedPanel() {
        FlatTabbedPaneUI theUI = new FlatTabbedPaneUI(){
            @Override
            protected void installDefaults() {
                super.installDefaults();
                selectedBackground= Color.white;
                showTabSeparators=true;
            }
        };
        setUI(theUI);
        // addCodeShowPane();
    }

    /**
     * 根据信息生成代码面板
     *
     * @param codeFormatFlagParam
     */
    public void restoreCodeShowPane(CodeFormatFlagParam codeFormatFlagParam, CodeShowPaneModel codeShowPaneModel) {
        CodeShowTextArea codeTextArea = new CodeShowTextArea();
        CodeShowPane codePane = new CodeShowPane(codeFormatFlagParam, codeShowPaneModel, codeTextArea);
        superAddTab(codeFormatFlagParam.getFileName(), codePane);
    }

    /**
     * 添加格式面板并返回对应面板
     *
     * @param fileFormat
     */
    private void addCodePane(GeneralFileFormat fileFormat) {
        if (fileFormat != null) {
            CodeShowTextArea codeTextArea = new CodeShowTextArea();
            CodeShowPane codePane = new CodeShowPane(fileFormat, codeTextArea);
            superAddTab(fileFormat.getFileName(), codePane);
        }
    }

    /**
     * 添加必填模板
     *
     * @param fileName
     */
    public void addMain(String fileName) {
        List<GeneralFileFormat> codeFileList = SysService.MAIN_FORMAT_CODE_FILE_SERVICE.getMainFormatCodeFileList();// 获取所有附带代码格式文件
        if (codeFileList != null) {
            for (GeneralFileFormat mainCodeFormat : codeFileList) {
                if (mainCodeFormat.getFileType() == GeneralFileFormat.TYPE_ADDITIONAL_FORMAT_FILE) {
                    addCodePane(mainCodeFormat);

                } else if (mainCodeFormat.getFileType() == GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE) {
                    mainCodeFormat.setFileName(fileName);
                    addCodePane(mainCodeFormat);
                }
            }
        }
    }

    /**
     * 获取对应这个可选模板所添加的默认代码文件名
     *
     * @param additionalSerialNumber
     * @return
     */
    public ArrayList<String> getCorrespondingAdditionalDefaultFileName(int additionalSerialNumber) {
        ArrayList<String> list = new ArrayList<>();
        CodeShowPane codePane = null;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getTabCount(); i++) {
            codePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                if (additionalSerialNumber == codeFormatFlagParam.getAdditionalSerialNumber()) {
                    if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                        list.add(codeFormatFlagParam.getFileName());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取必填模板默认代码文件
     *
     * @return
     */
    public CodeShowPane getMainDefaultCodeShowPane() {
        CodeShowPane mainDefaultCodeShowPane = null, codePane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()) {
                if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                    mainDefaultCodeShowPane = codePane;
                    break;
                }
            }
        }
        return mainDefaultCodeShowPane;
    }

    /**
     * 获取必填模板的子代码面板列表
     *
     * @return
     */
    public ArrayList<CodeShowPane> getMainSubCodeShowPaneList() {
        ArrayList<CodeShowPane> mainSubCodeShowPaneList = new ArrayList<>();
        CodeShowPane codePane = null;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getTabCount(); i++) {
            codePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam.getFormatType()) {
                if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                    mainSubCodeShowPaneList.add(codePane);
                }
            }
        }
        return mainSubCodeShowPaneList;
    }

    /**
     * 获取模块代码面板
     *
     * @param moduleId
     * @return
     */
    public ArrayList<CodeShowPane> getModulePaneList(String moduleId) {
        ArrayList<CodeShowPane> codeShowPaneList = new ArrayList<>();
        CodeShowPane codePane = null;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getTabCount(); i++) {
            codePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getModuleId().equals(moduleId)) {
                    codeShowPaneList.add(codePane);
                }
            }
        }
        return codeShowPaneList;

    }

    public void addAdditionalDefaultFormatCodeFile(int additionalSerialNumber, String fileName) {
        GeneralFileFormat defaultCodeFormat = SysService.ADDITIONAL_FORMAT_FILE_SERVICE
                .getAdditionalDeafaultFormatCodeFile(additionalSerialNumber);
        if (defaultCodeFormat != null) {
            defaultCodeFormat.setFileName(fileName);
            addCodePane(defaultCodeFormat);
        }
    }

    public void addAdditionalSubCodeFormatList(int additionalSerialNumber) {
        List<GeneralFileFormat> formatList = SysService.ADDITIONAL_FORMAT_FILE_SERVICE
                .additionalSubFormatCodeFileList(additionalSerialNumber);// 获取所有附带代码格式文件
        if (formatList != null) {
            for (GeneralFileFormat temp : formatList) {
                addCodePane(temp);
            }
        }
    }

    /**
     * 获取可选模板的代码面板
     *
     * @param additionalSerialNumber
     * @return
     */
    public ArrayList<CodeShowPane> getAdditionalSubCodePaneList(int additionalSerialNumber) {
        ArrayList<CodeShowPane> codeShowPaneList = new ArrayList<>();
        CodeShowPane codePane = null;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getTabCount(); i++) {
            codePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                    if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                        codeShowPaneList.add(codePane);
                    }
                }
            }
        }
        return codeShowPaneList;
    }

    /**
     * 获取可选模板的默认代码面板
     *
     * @param additionalSerialNumber
     * @param fileName
     * @return
     */
    public CodeShowPane getAdditionalDeafaultCodePane(int additionalSerialNumber, String fileName) {
        CodeShowPane codePane = null, temp;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getTabCount(); i++) {
            temp = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = temp.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam.getFileType()) {
                if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                    if (codeFormatFlagParam.getFileName().equals(fileName)) {
                        codePane = temp;
                        break;
                    }
                }
            }
        }
        return codePane;
    }

    /**
     * 添加模块
     *
     * @param moduleId
     */
    public void addModule(String moduleId) {
        if (checkHaveAddTheModuleOrNot(moduleId) == false) {
            List<GeneralFileFormat> list = SysService.MODULE_FILE_FORMAT_SERVICE.getModuleFileFormatList(moduleId);
            if (list != null) {
                for (GeneralFileFormat formatFile : list) {
                    addCodePane(formatFile);
                }
            }
        }
    }

    /**
     * 获取代码文件格式参数列表
     */
    public ArrayList<CodeFormatFlagParam> getCodeFormatFlagParamList() {
        ArrayList<CodeFormatFlagParam> list = new ArrayList<>();
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            list.add(codeFormatFlagParam);
        }
        return list;
    }

    /**
     * 查看当前有没有添加过这种其他文件
     *
     * @param additionalSerialNumber
     * @return
     */
    private boolean checkHaveAddTheAdditionalOrNot(int additionalSerialNumber) {
        boolean flag = false;
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 删除对应模块的代码文件
     *
     * @param moduleId
     */
    public void delTheModuleCodeFiles(String moduleId) {
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getModuleId().equals(moduleId)) {
                    codeShowPane.delThisCodeFile();
                    removeTabAt(i);
                    delTheModuleCodeFiles(moduleId);
                    break;
                }
            }
        }
    }

    /**
     * 刷新所有代码内容
     */
    public void updateAllCodeContent() {
        CodeShowPane codeShowPane;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeShowPane.showCodeContent(null);
        }
    }

    /**
     * 删除对应可选模板的默认文件
     *
     * @param additionalSerialNumber
     * @param fileName
     */
    public void delTheAdditionalDeafaultCodeFile(int additionalSerialNumber, String fileName) {
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                    if (codeShowPane.getFileName().equals(fileName)) {
                        if (codeFormatFlagParam
                                .getFileType() == CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE) {
                            codeShowPane.delThisCodeFile();
                            removeTabAt(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void delTheAdditionalCodeFiles(int additionalSerialNumber) {
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getAdditionalSerialNumber() == additionalSerialNumber) {
                    codeShowPane.delThisCodeFile();
                    removeTabAt(i);
                    delTheAdditionalCodeFiles(additionalSerialNumber);
                    break;
                }
            }
        }
    }

    /**
     * 获取对应的代码面板
     *
     * @return
     */
    public CodeShowPane getCorrespondingCodePane(CodeFormatFlagParam codeFormatFlagParam) {
        CodeShowPane codeShowPane = null, tempCodePane;
        CodeFormatFlagParam codeFormatFlagParamTemp;
        for (int i = 0; i < getComponentCount(); i++) {
            tempCodePane = (CodeShowPane) getComponent(i);
            codeFormatFlagParamTemp = tempCodePane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.compare(codeFormatFlagParam, codeFormatFlagParamTemp) == true) {
                codeShowPane = tempCodePane;
                break;
            }
        }
        return codeShowPane;
    }

    /**
     * 查看当前有没有添加过这种模块
     *
     * @param moduleId
     * @return
     */
    private boolean checkHaveAddTheModuleOrNot(String moduleId) {
        boolean flag = false;
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getModuleId().equals(moduleId)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 查看有没有这个名称的源文件
     *
     * @param fileName
     * @return
     */
    public boolean checkHaveAddTheFileNameOrNot(String fileName) {
        boolean flag = false;
        CodeShowPane codeShowPane;
        CodeFormatFlagParam codeFormatFlagParam;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeFormatFlagParam = codeShowPane.getCodeFormatFlagParam();
            if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam.getFormatType()) {
                if (codeFormatFlagParam.getFileName().equals(fileName)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 保存项目文件
     */
    public void saveProjectFile() {
        CodeShowPane codeShowPane;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeShowPane.saveProjectFile();
        }
    }

    /**
     * 生成代码
     */
    public void generateCode() {
        CodeShowPane codeShowPane;
        for (int i = 0; i < getComponentCount(); i++) {
            codeShowPane = (CodeShowPane) getComponent(i);
            codeShowPane.generateCode();
        }
    }

    /**
     * 选中对应面板
     *
     * @param codeShowPane
     */
    public void setSelectedCodePane(CodeShowPane codeShowPane) {
        if (codeShowPane != getSelectedComponent()) {
            Component theComponent;
            for (int i = 0; i < getComponentCount(); i++) {
                theComponent = getComponent(i);
                if (theComponent == codeShowPane) {
                    setSelectedIndex(i);
                    break;
                }
            }
        }
    }

}
