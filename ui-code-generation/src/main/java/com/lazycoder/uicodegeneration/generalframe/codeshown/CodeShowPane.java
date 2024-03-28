package com.lazycoder.uicodegeneration.generalframe.codeshown;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.common.EncodingTypeEnum;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.ImportCode;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.fileStructure.SourceGenerateProFile;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.FormatCodeFileServiceImpl;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.HarryingMark;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.generalframe.ResponseParam.CodePaneModelGenerateCodeResponseParam;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.TheFormatStatement;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.UpdateCodeTemporaryVariable;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.AbstractMarkBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.BaseBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.lable.FunctionAddBean;
import com.lazycoder.uicodegeneration.component.operation.container.codeeditparam.*;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.CodeShowPaneModel;
import com.lazycoder.uiutils.mycomponent.CodeShowTextArea;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.io.*;
import java.util.ArrayList;


public class CodeShowPane extends RTextScrollPane {

    /**
     *
     */
    private static final long serialVersionUID = 7897099640510326240L;

    @Getter
    @Setter
    private CodeShowTextArea codeShowTextArea;

    /**
     * 使用参数
     **/
    @Getter
    @Setter
    private CodeFormatFlagParam codeFormatFlagParam = null;

    private GeneralFileFormat codeModel;

    private TheFormatStatement formatModel;

    @Getter
    private String fileName = "";

//    private ProgramingLanguage codeLanguage = null;//本代码文件使用的编程语言

    private CodeFormatService codeFormatService = null;//代码格式化服务，如果有对应的代码格式化服务，则使用对应的代码格式化服务进行代码格式化

    /**
     * 测试使用
     *
     * @param codeTextArea
     */
    public CodeShowPane(CodeShowTextArea codeTextArea) {
        // TODO Auto-generated constructor stub
        super(codeTextArea);
        this.codeShowTextArea = codeTextArea;
    }

    /**
     * 新建一个代码文件面板
     *
     * @param codeModel
     * @param codeTextArea
     */
    public CodeShowPane(GeneralFileFormat codeModel, CodeShowTextArea codeTextArea) {
        // TODO Auto-generated constructor stub
        this(codeTextArea);
        this.codeModel = codeModel;
        this.formatModel = new TheFormatStatement(codeModel.getFormatContent(), codeModel.getFileName());
        this.codeFormatFlagParam = FormatCodeFileServiceImpl.getCodeFormatFlagParam(codeModel);
        this.codeFormatFlagParam.setFileName(codeModel.getFileName());
        this.fileName = codeModel.getFileName();
        setCodeFormatStype();
        this.codeShowTextArea.setTipText(codeModel.getPath());
        showCodeContent(null);
    }


    /**
     * 根据内容还原代码显示面板
     *
     * @param codeFormatFlagParam
     * @param codeShowPaneModel
     * @param codeTextArea
     */
    public CodeShowPane(CodeFormatFlagParam codeFormatFlagParam, CodeShowPaneModel codeShowPaneModel,
                        CodeShowTextArea codeTextArea) {
        // TODO Auto-generated constructor stub
        this(codeTextArea);
        this.codeModel = codeShowPaneModel.getCodeModel();
        this.formatModel = codeShowPaneModel.getFormatStatement();
        // 这里要根据formatStatement
        this.codeFormatFlagParam = codeFormatFlagParam;
        this.fileName = codeFormatFlagParam.getFileName();
        setCodeFormatStype();
        this.codeShowTextArea.setTipText(codeModel.getPath());
        showCodeContent(null);
    }




    /**
     * 设置对应的代码格式化类型（需要）
     */
    private void setCodeFormatStype() {
        String theEX = FileUtil.getFileEx(fileName).toLowerCase();
        this.codeFormatService = new CodeFormatService(theEX);
    }


    /**
     * 在格式中添加代码
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    public CodePaneModelGenerateCodeResponseParam addCodeToFormat(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam = formatModel.addCodeToFormat(addCodeEditParamForFormat);
        showCodeContent(codePaneModelGenerateCodeResponseParam.getIncrementalCursorPositionTemp());
        codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(this.codeFormatFlagParam);
        return codePaneModelGenerateCodeResponseParam;
    }

    /**
     * 检查要添加的功能是否符合添加到格式组件，是否可以添加到这里
     *
     * @param addCodeEditParamForFormat
     * @return
     */
    public boolean checkWhetherItMatchesForFormat(AddCodeEditParamForFormat addCodeEditParamForFormat) {
        return formatModel.checkWhetherItMatchesForFormat(addCodeEditParamForFormat);
    }

    /**
     * 获取格式类型的第1层对应组件
     *
     * @param pathFind
     * @return
     */
    public UpdateCodeTemporaryVariable getFormatFirstLayerLabelBeanList(PathFind pathFind) {
        return formatModel.getFormatLabelBeanList(pathFind);
    }

    /**
     * 根据pathFind获取格式类型第一个功能拓展组件其对应的所有CodeLabelId
     *
     * @param pathFind （只能传符合的pathFind）
     * @return
     */
    public ArrayList<String> getUnitFormatFirstLayerFunctionAddBeanCodeLabelIdList(PathFind pathFind) {
        ArrayList<String> list = new ArrayList<>();
        FunctionAddBean functionAddBean;
        String codeLabelId;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = formatModel.getFormatLabelBeanList(pathFind);
        ArrayList<LabelBean> functionAddBeanList = updateCodeTemporaryVariable.getList();
        for (LabelBean labelBean : functionAddBeanList) {
            if (labelBean instanceof FunctionAddBean) {
                functionAddBean = (FunctionAddBean) labelBean;
                codeLabelId = functionAddBean.getCodeLabelId();
                if (!(list.contains(codeLabelId))) {
                    list.add(codeLabelId);
                }
            }
        }
        return list;
    }


    /**
     * 更新代码值到格式
     *
     * @param updateCodeEditParamForFormat
     */
    public boolean updateValueInFormat(UpdateCodeEditParamForFormat updateCodeEditParamForFormat) {
        boolean editOrNot = true;

        int cursorPositionTemp = formatModel.updateValueInFormat(updateCodeEditParamForFormat);

        showCodeContent(cursorPositionTemp);
        if (cursorPositionTemp > 0) {
            editOrNot = true;
        } else {
            editOrNot = false;
        }
        return editOrNot;
    }

    /**
     * 显示格式化后的代码
     *
     * @param cursorPosition 对应定位到的光标位置
     */
    public void showCodeContent(Integer cursorPosition) {
        int positionTokenIndex;
        if (cursorPosition==null){
            positionTokenIndex = codeShowTextArea.getPosisitionTokenIndex(codeShowTextArea.getCaretPosition());
        }else {
            positionTokenIndex = codeShowTextArea.getPosisitionTokenIndex(cursorPosition);
        }
        String getCode = formatModel.getCodeContent();
        codeShowTextArea.setText(getCode);

        if (CodeGenerationFrameHolder.formattedSourceCodeCheckBox.isSelected() == true &&
                this.codeModel.getCodeFormatOrNot() == BaseModel.TRUE_) {
            String formatCode = this.codeFormatService.formatter(getCode);
            codeShowTextArea.setText(formatCode);
        } else {
            codeShowTextArea.setText(getCode);
        }
        codeShowTextArea.setCursorAtEndOfNthToken(positionTokenIndex);
//        if (cursorPosition != null && cursorPosition > 0) {
//            if (cursorPosition < getCode.length()) {
//                float proportion = MathUtil.txfloat(cursorPosition, getCode.length());
//                SysUtil.scrollToProportion(proportion, this);
////                if (codeShowTextArea.getText().length() > cursorPosition) {
////                    codeShowTextArea.setCaretPosition(cursorPosition);
////                }
//            }
//        }
    }

    /**
     * 从格式删除代码
     *
     * @param delCodeEditParamForFormat
     */
    public boolean delCodeFromFormat(DelCodeEditParamForFormat delCodeEditParamForFormat) {
        boolean editOrNot = true;
        int cursorPositionTemp = formatModel.delCodeFromFormat(delCodeEditParamForFormat);
        showCodeContent(cursorPositionTemp);
        if (cursorPositionTemp > 0) {
            editOrNot = true;
        } else {
            editOrNot = false;
        }
        return editOrNot;
    }

    /**
     * 添加代码到标记（仅限初始化、设置、必填模板设置、可选模板设置方法）
     *
     * @param addCodeEditParamForMark
     */
    public CodePaneModelGenerateCodeResponseParam addCodeToMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        CodePaneModelGenerateCodeResponseParam codePaneModelGenerateCodeResponseParam = formatModel.addCodeToMark(addCodeEditParamForMark);
        showCodeContent(codePaneModelGenerateCodeResponseParam.getIncrementalCursorPositionTemp());
        codePaneModelGenerateCodeResponseParam.setCodeFormatFlagParam(codeFormatFlagParam);
        return codePaneModelGenerateCodeResponseParam;
    }

    /**
     * 添加到指定地方
     *
     * @param thanMarkElement
     * @param pathFind
     * @param codeLabelId
     * @param codeSerialNumber
     * @param codeOrdinal
     * @param inserNewLineOrNot
     * @param codeStatement
     * @param addToLast
     * @param nextCodeSerialNumber
     * @return
     */
    public int addCodeTo(BaseMarkElement thanMarkElement, PathFind pathFind, String codeLabelId,
                         int codeSerialNumber, int codeOrdinal, boolean inserNewLineOrNot, String codeStatement,
                         boolean addToLast, Integer nextCodeSerialNumber) {
        int thisCursorPositionTemp = formatModel.addCodeToMark(
                thanMarkElement, pathFind, codeLabelId, codeSerialNumber, codeOrdinal, codeStatement, addToLast, nextCodeSerialNumber
                , inserNewLineOrNot
        );
        if (thisCursorPositionTemp >= 0) {
            showCodeContent(thisCursorPositionTemp);
        }
        return thisCursorPositionTemp;
    }

    /**
     * 添加代码到格式类型的第1层对应组件上
     *
     * @param codeSerialNumber
     * @param codeOrdinal
     * @param codeStatement
     * @param pathFind                   所传寻址字符串必须是格式类型的，而且不管该参数有多少层，添加代码都添加到对应格式的第1层组件上
     * @param commandAddRelatedAttribute
     * @return
     */
    public int addCodeToFirstLayerFormat(int codeSerialNumber, int codeOrdinal, String codeStatement, boolean inserNewLineOrNot,
                                         PathFind pathFind, CommandAddRelatedAttribute commandAddRelatedAttribute) {
        int thisCursorPositionTemp = formatModel.addCodeToFirstLayerFormat(codeSerialNumber, codeOrdinal, codeStatement, inserNewLineOrNot, pathFind, commandAddRelatedAttribute);
        if (thisCursorPositionTemp >= 0) {
            showCodeContent(thisCursorPositionTemp);
        }
        return thisCursorPositionTemp;
    }

    public boolean checkAddCodeToFirstLayerFormat(
            PathFind pathFind,
            CommandAddRelatedAttribute commandAddRelatedAttribute) {
        return formatModel.checkAddCodeToFirstLayerFormat(pathFind, commandAddRelatedAttribute);
    }

    /**
     * 检查要添加的代码是否可以添加到这里
     *
     * @param addCodeEditParamForMark
     * @return
     */
    public boolean checkWhetherItMatchesForMark(AddCodeEditParamForMark addCodeEditParamForMark) {
        return formatModel.checkWhetherItMatchesForMark(addCodeEditParamForMark);
    }

    /**
     * 从标记更改代码值（新的调整方法）
     *
     * @param updateCodeEditParamForMark
     */
    public boolean updateCodeFromMark(UpdateCodeEditParamForMark updateCodeEditParamForMark) {
        boolean flag = true;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = null;
        ArrayList<HarryingMark> correspondingMarkListTemp = getTheCorrespondingMark(updateCodeEditParamForMark.getThanMarkElement());
        if (correspondingMarkListTemp.size() > 0) {
            boolean temp = true;
            AbstractMarkBean markBean;
            //在对应的标记那里进行更改，并记下第一个的游标相对位置等信息
            for (HarryingMark harryingMark : correspondingMarkListTemp) {
                markBean = (AbstractMarkBean) harryingMark;
                if (temp == true) {
                    updateCodeTemporaryVariable = formatModel.updateCodeFromMark(updateCodeEditParamForMark, markBean);
                    flag = updateCodeTemporaryVariable.isEditOrNot();
                    temp = false;
                } else {
                    formatModel.updateCodeFromMark(updateCodeEditParamForMark, markBean);
                }
            }
            HarryingMark tempMark = correspondingMarkListTemp.get(0);
            //获取到第一个对应标记之前的字符数量
            for (BaseBean baseBean : formatModel.getCodeStatementBeanList()) {
                if (baseBean == tempMark) {
                    break;
                } else {
                    updateCodeTemporaryVariable.setCursorDisplacement(updateCodeTemporaryVariable.getCursorDisplacement() + baseBean.getValueLength());
                }
            }
            showCodeContent(updateCodeTemporaryVariable.getCursorDisplacement());
        } else if (correspondingMarkListTemp.size() == 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 从标记删除代码（仅限初始化和设置）新方法
     *
     * @param delCodeEditParamForMark
     */
    public boolean delCodeToMark(DelCodeEditParamForMark delCodeEditParamForMark) {
        boolean flag = false;
        UpdateCodeTemporaryVariable updateCodeTemporaryVariable = null;
        ArrayList<HarryingMark> correspondingMarkListTemp = getTheCorrespondingMark(delCodeEditParamForMark.getThanMarkElement());
        if (correspondingMarkListTemp.size() > 0) {
            boolean temp = true;
            AbstractMarkBean markBean;
            for (HarryingMark harryingMark : correspondingMarkListTemp) {
                markBean = (AbstractMarkBean) harryingMark;
                if (temp == true) {
                    updateCodeTemporaryVariable = formatModel.delCodeToMark(delCodeEditParamForMark, markBean);
                    flag = updateCodeTemporaryVariable.isEditOrNot();
                    temp = false;
                } else {
                    formatModel.delCodeToMark(delCodeEditParamForMark, markBean);

                }
            }
            HarryingMark tempMark = correspondingMarkListTemp.get(0);
            //获取到第一个对应标记之前的字符数量
            for (BaseBean baseBean : formatModel.getCodeStatementBeanList()) {
                if (baseBean == tempMark) {
                    break;
                } else {
                    updateCodeTemporaryVariable.setCursorDisplacement(updateCodeTemporaryVariable.getCursorDisplacement() + baseBean.getValueLength());
                }
            }
            showCodeContent(updateCodeTemporaryVariable.getCursorDisplacement());

        } else if (correspondingMarkListTemp.size() == 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检查代码面板有没有业务逻辑的标记
     *
     * @return
     */
    public boolean checkHaveBusinessLogicMark() {
        return formatModel.checkHaveBusinessLogicMark();
    }

    /**
     * 添加业务逻辑代码
     *
     * @param addCodeEditParamForMark
     */
//    public boolean addBusinessLogicCode(AddCodeEditParamForMark addCodeEditParamForMark) {
//        boolean editOrNot = true;
//        int cursorPositionTemp = formatModel.addBusinessLogicCode(addCodeEditParamForMark);
//        showCodeContent(cursorPositionTemp);
//        if (cursorPositionTemp >= 0) {
//            editOrNot = true;
//        } else {
//            editOrNot = false;
//        }
//        return editOrNot;
//    }

    /**
     * 更改业务代码的值
     *
     * @param updateCodeEditParamForMark
     */
//    public boolean updateValueInBusinessLogicCode(UpdateCodeEditParamForMark updateCodeEditParamForMark) {
//        boolean editOrNot = true;
//        int cursorPositionTemp = formatModel.updateValueInBusinessLogicCode(updateCodeEditParamForMark);
//        showCodeContent(cursorPositionTemp);
//        if (cursorPositionTemp >= 0) {
//            editOrNot = true;
//        } else {
//            editOrNot = false;
//        }
//        return editOrNot;
//    }

    /**
     * 删除业务代码
     *
     * @param delCodeEditParamForMark
     */
//    public boolean delCodeFromBusinessLogicCode(DelCodeEditParamForMark delCodeEditParamForMark) {
//        boolean editOrNot = true;
//        int cursorPositionTemp = formatModel.delCodeFromBusinessLogicCode(delCodeEditParamForMark);
//        showCodeContent(cursorPositionTemp);
//        if (cursorPositionTemp >= 0) {
//            editOrNot = true;
//        } else {
//            editOrNot = false;
//        }
//        return editOrNot;
//    }

    /**
     * 添加引入代码
     *
     * @param thanImportMarkElement
     * @param importCode
     */
    public void addImportCode(ImportMarkElement thanImportMarkElement, ImportCode importCode) {
        int position = getCodeShowTextArea().getCaretPosition();
        if (GeneralFileFormat.MODULE_TYPE == this.codeModel.getFormatType()) {// 如果就是这个模块的代码文件，不添加本模块的引入代码
            if (this.codeModel.getModuleId().equals(importCode.getModuleId()) == false) {
                formatModel.addImportCode(thanImportMarkElement, importCode);
            }
        } else {
            formatModel.addImportCode(thanImportMarkElement, importCode);
        }
        try {
            showCodeContent(position);
        }catch (Exception e){
            SysService.SYS_SERVICE_SERVICE.log_error(e.getMessage());
        }
    }

    /**
     * 删除引入代码
     *
     * @param thanImportMarkElement
     * @param importCode
     */
    public void delImportCode(ImportMarkElement thanImportMarkElement, ImportCode importCode) {
        formatModel.delImportCode(thanImportMarkElement, importCode);
    }

    /**
     * 根据比对标签获取对应标记
     *
     * @param thanMarkElement
     * @return
     */
    public ArrayList<HarryingMark> getTheCorrespondingMark(BaseMarkElement thanMarkElement) {
        return formatModel.getAllCorrespondingMark(thanMarkElement);
    }


    /**
     * 获取对应模型
     *
     * @return
     */
    private CodeShowPaneModel getCodeShowPaneModel() {
        CodeShowPaneModel model = new CodeShowPaneModel();
        model.setCodeModel(this.codeModel);
        model.setFormatStatement(formatModel);
        return model;
    }

    /**
     * 保存项目文件
     */
    public void saveProjectFile() {
        File file = SourceGenerateProFile.getCodeProFile(CodeGenerationFrameHolder.projectParentPath,
                CodeGenerationFrameHolder.projectName, codeFormatFlagParam);
        CodeShowPaneModel codeShowPaneModel = getCodeShowPaneModel();
//        FileUtil.writeFile(file, JsonUtil.getJsonStr(codeShowPaneModel), EncodingTypeEnum.UTF8.getCommonsIoParam());
        JsonUtil.writeFile(file, codeShowPaneModel);
    }

    /**
     * 生成代码
     */
    public void generateCode() {
        if (codeModel != null) {
            File putFolder = getPutPolder(), codeFile = getGenerateCodeFile();
            putFolder.mkdirs();
            EncodingTypeEnum encodingType = CodeGenerationFrameHolder.currentEncodingType == null ? CodeGenerationFrameHolder.currentEncodingType : EncodingTypeEnum.UTF8;
            if (encodingType != null) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(codeFile.getAbsolutePath());
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encodingType.getCommonsIoParam());
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    codeShowTextArea.write(bufferedWriter);
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
//                    FileUtils.writeByteArrayToFile(codeFile,
//                            codeShowTextArea.getText().getBytes(encodingType.getCommonsIoParam()));
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void delThisCodeFile() {
        File codeFile = getGenerateCodeFile();
        if (codeFile.isFile()) {
            codeFile.delete();
        }
    }

    private File getPutPolder() {
        String relativePath = codeModel.getPath() == null ? "" : codeModel.getPath();
        return new File(SourceGenerateFileStructure
                .getTheProjectPathInGenerateSourcePutPath(CodeGenerationFrameHolder.projectParentPath,
                        CodeGenerationFrameHolder.projectName)
                .getAbsolutePath() + File.separator + relativePath);
    }

    private File getGenerateCodeFile() {
        return new File(getPutPolder().getAbsolutePath() + File.separator + fileName);
    }

}
