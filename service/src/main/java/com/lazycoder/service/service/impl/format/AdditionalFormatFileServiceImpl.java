package com.lazycoder.service.service.impl.format;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.dao.format.FormatCodeFilelMapper;
import com.lazycoder.database.dao.format.FormatOperatingMapper;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFeatureSelection;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.service.vo.meta.AdditionalMetaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Component(value = "AdditionalFormatFileServiceImpl")
public class AdditionalFormatFileServiceImpl {

    @Autowired
    private FormatCodeFilelMapper formatCodeFilelMapper;

    @Autowired
    private FormatOperatingMapper operatingMapper;

    /**
     * 保存其他操作
     *
     * @param operatingList
     */
    public void saveAdditionalOperatingList(List<AdditionalOperating> operatingList) {
        operatingMapper.delAllAdditionalOperating(DataFormatType.ADDITIONAL_TYPE);
        if (operatingList.size() > 0) {
            operatingMapper.addFormatOperatingList(operatingList);
        }
    }

    public String getAdditionalType(int additionalSerialNumber) {
        return operatingMapper.getAdditionalOperating(DataFormatType.ADDITIONAL_TYPE, additionalSerialNumber).getTypeName();
    }

    /**
     * 获取其他操作
     *
     * @return
     */
    public AdditionalOperating getAdditionalOperating(int additionalSerialNumber) {
        return operatingMapper.getAdditionalOperating(DataFormatType.ADDITIONAL_TYPE, additionalSerialNumber);
    }

    /**
     * 获取其他可选列表
     *
     * @return
     */
    public List<AdditionalFeatureSelection> getAdditionalFeatureSelectionList() {
        return operatingMapper.getAdditionalFeatureSelectionList(DataFormatType.ADDITIONAL_TYPE);
    }

    public int getAdditionalFeatureSelectionNum() {
        int anum = 0;//默认没写其他格式的函数
        Integer anum_temp = operatingMapper.getAdditionalFeatureSelectionNum(DataFormatType.ADDITIONAL_TYPE);
        if (anum_temp != null) {
            anum = anum_temp;
        }
        return anum;
    }

    public void saveFormatCodeFileList(List<GeneralFileFormat> codeFormatList) {
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(GeneralFileFormat.ADDITIONAL_TYPE);
        formatCodeFilelMapper.delFormatCodeFile(codeFormatFlagParam);
        if (codeFormatList.size() > 0) {
            formatCodeFilelMapper.addFormatCodeFile(codeFormatList);
        }
    }

    /**
     * 获取当前模块所有的其他模型
     */
    public ArrayList<AdditionalMetaModel> getAdditionalMetaModelList() {
        AdditionalMetaModel temp;
        GeneralFileFormat defaultCodeFormat;
        List<GeneralFileFormat> codeModelList;
        ArrayList<AdditionalMetaModel> list = new ArrayList<>();
        List<AdditionalOperating> oList = operatingMapper.getAllOperatingList(DataFormatType.ADDITIONAL_TYPE);// 获取操作层列表
        if (oList != null) {
            for (AdditionalOperating operatingTemp : oList) {
                temp = new AdditionalMetaModel();// 构建元模型
                temp.setOperatingModel(operatingTemp);// 获取操作模型

                defaultCodeFormat = getAdditionalDeafaultFormatCodeFile(operatingTemp.getAdditionalSerialNumber());
                temp.setDefaultCodeFormat(defaultCodeFormat);

                codeModelList = additionalSubFormatCodeFileList(operatingTemp.getAdditionalSerialNumber());// 获取附带格式代码文件列表
                if (codeModelList != null) {
                    for (GeneralFileFormat fileFormat : codeModelList) {
                        if (fileFormat.getFileType() == GeneralFileFormat.TYPE_ADDITIONAL_FORMAT_FILE) {
                            temp.getCodeModelList().add(fileFormat);
                        }
                    }
                }
                list.add(temp);
            }
        }
        return list;
    }

    public AdditionalMetaModel getAdditionalMetaModel(int additionalSerialNumber) {
        AdditionalMetaModel temp = new AdditionalMetaModel();// 构建元模型
        temp.setOperatingModel(operatingMapper.getAdditionalOperating(DataFormatType.ADDITIONAL_TYPE, additionalSerialNumber));// 获取操作模型

        GeneralFileFormat defaultCodeFormat = getAdditionalDeafaultFormatCodeFile(additionalSerialNumber);
        temp.setDefaultCodeFormat(defaultCodeFormat);

        List<GeneralFileFormat> codeModelList = additionalSubFormatCodeFileList(additionalSerialNumber);// 获取附带格式代码文件列表
        if (codeModelList != null) {
            temp.getCodeModelList().addAll(codeModelList);
        }
        return temp;
    }

    /**
     * 获取其他代码格式文件模型
     *
     * @return
     */
    public List<GeneralFileFormat> additionalSubFormatCodeFileList(int additionalSerialNumber) {
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(CodeFormatFlagParam.ADDITIONAL_TYPE);
        codeFormatFlagParam.setAdditionalSerialNumber(additionalSerialNumber);
        codeFormatFlagParam.setFileType(CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE);
        return formatCodeFilelMapper.getFormatCodeFileList(codeFormatFlagParam);
    }

    public GeneralFileFormat getAdditionalDeafaultFormatCodeFile(int additionalSerialNumber) {
        CodeFormatFlagParam additionalCodeFormat = new CodeFormatFlagParam();
        additionalCodeFormat.setFormatType(CodeFormatFlagParam.ADDITIONAL_TYPE);
        additionalCodeFormat.setFileType(CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE);
        additionalCodeFormat.setAdditionalSerialNumber(additionalSerialNumber);
        additionalCodeFormat.setCodeOrdinal(1);
        return formatCodeFilelMapper.getFormatFileByParam(additionalCodeFormat);
    }

    /**
     * 获取对应的可选模板格式的默认代码文件的文件名
     *
     * @param additionalSerialNumber
     * @return
     */
    public String getAdditionalDeafaultFormatCodeFileName(int additionalSerialNumber) {
        CodeFormatFlagParam additionalCodeFormat = new CodeFormatFlagParam();
        additionalCodeFormat.setFormatType(CodeFormatFlagParam.ADDITIONAL_TYPE);
        additionalCodeFormat.setFileType(CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE);
        additionalCodeFormat.setAdditionalSerialNumber(additionalSerialNumber);
        additionalCodeFormat.setCodeOrdinal(1);
        return formatCodeFilelMapper.getFormatFileName(additionalCodeFormat);
    }

    /**
     * 获取默认格式代码文件
     *
     * @param formatFile
     */
    public void updateDeafaultFormatCodeFile(GeneralFileFormat formatFile) {
        formatFile.setFileType(GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE);
        List<GeneralFileFormat> codeFormatList = new ArrayList<>();
        codeFormatList.add(formatFile);
        formatCodeFilelMapper.updateFormatCodeFile(codeFormatList);
    }

    /**
     * 按条件查找可选模板的操作数据
     *
     * @param setPropertyList
     * @return
     */
    public List<AdditionalOperating> getAdditionalOperatingList(List<Integer> setPropertyList) {
        return operatingMapper.getAdditionalOperatingList(setPropertyList);
    }

}
