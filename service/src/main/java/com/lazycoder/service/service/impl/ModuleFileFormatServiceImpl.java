package com.lazycoder.service.service.impl;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.dao.format.FormatCodeFilelMapper;
import com.lazycoder.database.model.GeneralFileFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ModuleFileFormatServiceImpl")
public class ModuleFileFormatServiceImpl {

    @Autowired
    private FormatCodeFilelMapper formatCodeFilelMapper;
//	@Autowired
//	private ModuleFileFormatMapper dao;

    public void save(List<GeneralFileFormat> moduleFileFormatList, String moduleId) {
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(CodeFormatFlagParam.MODULE_TYPE);
        codeFormatFlagParam.setModuleId(moduleId);
        formatCodeFilelMapper.delFormatCodeFile(codeFormatFlagParam);
        if (moduleFileFormatList.size() > 0) {
            formatCodeFilelMapper.addFormatCodeFile(moduleFileFormatList);
        }
    }

    public void addModuleFileFormat(List<GeneralFileFormat> moduleFileFormatList) {
        // TODO Auto-generated method stub
        if (moduleFileFormatList.size() > 0) {
            formatCodeFilelMapper.addFormatCodeFile(moduleFileFormatList);
//		dao.addModuleFileFormat(moduleFileFormatList);
        }
    }

    /**
     * 获取模块信息
     *
     * @return
     */
    public List<GeneralFileFormat> getModuleFileFormatList(String moduleId) {
        // TODO Auto-generated method stub
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(CodeFormatFlagParam.MODULE_TYPE);
        codeFormatFlagParam.setModuleId(moduleId);
        return formatCodeFilelMapper.getFormatCodeFileList(codeFormatFlagParam);
    }


    /**
     * 根据id来获取对应的格式代码文件
     *
     * @param id
     * @return
     */
    public GeneralFileFormat getFormatCodeFileById(String id) {
        return formatCodeFilelMapper.getFormatCodeFileById(id);
    }


    /**
     * 更改模块信息
     *
     * @param codeFormatList
     */
    public void updateModuleFileFormat(List<GeneralFileFormat> codeFormatList) {
        if (codeFormatList.size() > 0) {
            formatCodeFilelMapper.updateFormatCodeFile(codeFormatList);
        }
    }


    public void delDataOfModule(String moduleId) {
        formatCodeFilelMapper.delDataOfModule(moduleId, GeneralFileFormat.MODULE_TYPE);
    }

}
