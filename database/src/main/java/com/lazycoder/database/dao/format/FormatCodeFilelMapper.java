package com.lazycoder.database.dao.format;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.GeneralFileFormat;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "FormatCodeFilelMapper")
public interface FormatCodeFilelMapper extends DataFormatType {

    public static final int TYPE_DEFAULT_FORMAT_FILE = 0;//默认文件类型

    public static final int TYPE_ADDITIONAL_FORMAT_FILE = 1;//附带子文件


    /**
     * 添加代码格式文件模型
     */
    public void addFormatCodeFile(List<GeneralFileFormat> codeFormatList);

    public void delFormatCodeFile(CodeFormatFlagParam codeFormatFlagParam);

    /**
     * 更改子格式代码文件
     *
     * @param codeFormatList
     */
    public void updateFormatCodeFile(List<GeneralFileFormat> codeFormatList);

    /**
     * 删除模块格式文件
     *
     * @param moduleId
     */
    public void delDataOfModule(@Param("moduleId") String moduleId, int formatType);


    public List<GeneralFileFormat> getFormatCodeFileList(CodeFormatFlagParam codeFormatFlagParam);


    /**
     * 根据id来获取对应的格式代码文件
     *
     * @param id
     * @return
     */
    public GeneralFileFormat getFormatCodeFileById(@Param("id") String id);

    /**
     * 获取代码格式文件模型 codeFormatFlagParam 只有设置 formatType、fileType、codeOrdinal、additionalSerialNumber 这几个参数才有效
     *
     * @return
     */
    public GeneralFileFormat getFormatFileByParam(CodeFormatFlagParam codeFormatFlagParam);

    /**
     * 根据条件获取对应的代码格式文件的名称
     *
     * @param codeFormatFlagParam
     * @return
     */
    public String getFormatFileName(CodeFormatFlagParam codeFormatFlagParam);

}
