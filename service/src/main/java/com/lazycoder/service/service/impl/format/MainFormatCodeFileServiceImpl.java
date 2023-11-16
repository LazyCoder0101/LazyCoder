package com.lazycoder.service.service.impl.format;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.dao.format.FormatCodeFilelMapper;
import com.lazycoder.database.dao.format.FormatOperatingMapper;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.format.MainOperating;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "MainFormatCodeFileServiceImpl")
public class MainFormatCodeFileServiceImpl {

    @Autowired
    private FormatCodeFilelMapper mainFormatCodeFileMapper;

    @Autowired
    private FormatOperatingMapper mainOperatingMapper;

    public void save(List<GeneralFileFormat> formatCodeFileList) {
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(GeneralFileFormat.MAIN_TYPE);
        mainFormatCodeFileMapper.delFormatCodeFile(codeFormatFlagParam);
        if (formatCodeFileList.size() > 0) {
            mainFormatCodeFileMapper.addFormatCodeFile(formatCodeFileList);
        }
    }

    /**
     * 更新主要控制
     *
     * @param mainOperating
     * @return
     */
    public void updateMainOperating(MainOperating mainOperating) {
        mainOperatingMapper.updateFormatOperating(mainOperating, DataFormatType.MAIN_TYPE);
    }

    /**
     * 获取主要控制
     *
     * @return
     */
    public MainOperating getMainOperating() {
        return mainOperatingMapper.getMainOperating(DataFormatType.MAIN_TYPE);
    }

    /**
     * 获取必填模板代码格式文件模型
     *
     * @return
     */
    public List<GeneralFileFormat> getMainFormatCodeFileList() {
        CodeFormatFlagParam codeFormatFlagParam = new CodeFormatFlagParam();
        codeFormatFlagParam.setFormatType(CodeFormatFlagParam.MAIN_TYPE);
        return mainFormatCodeFileMapper.getFormatCodeFileList(codeFormatFlagParam);
    }

    /**
     * 获取附加代码文件的对应数据
     *
     * @return
     */
    public List<GeneralFileFormat> getMainSubFormatCodeFileList() {
        List<GeneralFileFormat> list = getMainFormatCodeFileList();
        GeneralFileFormat mainCodeFormat;
        for (int i = 0; i < list.size(); i++) {
            mainCodeFormat = list.get(i);
            if (mainCodeFormat.getFileType() == GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE) {
                list.remove(i);
                break;
            }
        }
        return list;
    }


    /**
     * 获取必填模板的默认代码文件
     *
     * @return
     */
    public GeneralFileFormat getMainDefaultFormatFile() {
        CodeFormatFlagParam mainCodeFormat = new CodeFormatFlagParam();
        mainCodeFormat.setFormatType(CodeFormatFlagParam.MAIN_TYPE);
        mainCodeFormat.setFileType(GeneralFileFormat.TYPE_DEFAULT_FORMAT_FILE);
        mainCodeFormat.setCodeOrdinal(1);
        GeneralFileFormat defaultCodeFormat = mainFormatCodeFileMapper.getFormatFileByParam(mainCodeFormat);// 获取默认主格式代码文件
        return defaultCodeFormat;
    }


}
