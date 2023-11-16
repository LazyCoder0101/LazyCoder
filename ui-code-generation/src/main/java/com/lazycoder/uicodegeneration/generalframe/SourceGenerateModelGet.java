package com.lazycoder.uicodegeneration.generalframe;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.service.fileStructure.SourceGenerateProFile;
import com.lazycoder.uicodegeneration.proj.stostr.codeshown.CodeShowPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.AdditionalFormatControlPaneModel;
import com.lazycoder.uicodegeneration.proj.stostr.operation.MainFormatControlPaneModel;
import com.lazycoder.utils.JsonUtil;
import java.io.File;

/**
 * 代码生成模型获取
 *
 * @author admin
 */
public class SourceGenerateModelGet {

    /**
     * 获取代码生成的根模型
     *
     * @param projectParentPath
     * @param projectName
     * @return
     */
    public static CodeGenerationFrameModel getCodeGenerationFrameModel(String projectParentPath, String projectName) {
//		String str = FileUtil.getFileContent(
//				SourceGenerateProFile.getCodeGenerationRootProFile(projectParentPath, projectName).getAbsolutePath());
//		CodeGenerationFrameModel codeGenerationFrameModel = JsonUtil.toBean(str, CodeGenerationFrameModel.class);
        CodeGenerationFrameModel codeGenerationFrameModel = JsonUtil.fileToBean(
                SourceGenerateProFile.getCodeGenerationRootProFile(projectParentPath, projectName).getAbsolutePath(),
                CodeGenerationFrameModel.class
        );
        return codeGenerationFrameModel;
    }

    /**
     * 获取代码模型
     *
     * @param projectParentPath
     * @param projectName
     * @param codeFormatFlagParam
     * @return
     */
    public static CodeShowPaneModel getCodeShowPaneModel(String projectParentPath, String projectName,
                                                         CodeFormatFlagParam codeFormatFlagParam) {
        File file = SourceGenerateProFile.getCodeProFile(projectParentPath, projectName, codeFormatFlagParam);
//		String str = FileUtil.getFileContent(file.getAbsolutePath());
//		CodeShowPaneModel model = JsonUtil.toBean(str, CodeShowPaneModel.class);
        CodeShowPaneModel model = JsonUtil.fileToBean(file.getAbsolutePath(), CodeShowPaneModel.class);
        return model;
    }

    /**
     * 获取必填模板格式控制面板模型
     *
     * @param projectParentPath
     * @param projectName
     * @param fileName
     * @return
     */
    public static MainFormatControlPaneModel getMainFormatControlPane(String projectParentPath, String projectName,
                                                                      String fileName) {
        File file = SourceGenerateProFile.getMainOpratingPaneProFile(projectParentPath, projectName, fileName);
//        String str = FileUtil.getFileContent(file.getAbsolutePath());
//        MainFormatControlPaneModel mainFormatControlPaneModel = JsonUtil.toBean(str, MainFormatControlPaneModel.class);
        MainFormatControlPaneModel mainFormatControlPaneModel = JsonUtil.fileToBean(file.getAbsolutePath(), MainFormatControlPaneModel.class);
        return mainFormatControlPaneModel;
    }

    /**
     * 获取可选模板格式控制面板模型
     *
     * @param projectParentPath
     * @param projectName
     * @param fileName
     * @param additionalSerialNumber
     * @return
     */
    public static AdditionalFormatControlPaneModel getFormatControlPaneModel(String projectParentPath,
                                                                             String projectName, String fileName, int additionalSerialNumber) {
        File file = SourceGenerateProFile.getAdditionalOpratingPaneProFile(projectParentPath, projectName, fileName,
                additionalSerialNumber);
        AdditionalFormatControlPaneModel formatControlPaneModel = JsonUtil.fileToBean(
                file.getAbsolutePath(), AdditionalFormatControlPaneModel.class);
//        String str = FileUtil.getFileContent(file.getAbsolutePath());
//        AdditionalFormatControlPaneModel formatControlPaneModel = JsonUtil.toBean(str, AdditionalFormatControlPaneModel.class);
        return formatControlPaneModel;
    }

}
