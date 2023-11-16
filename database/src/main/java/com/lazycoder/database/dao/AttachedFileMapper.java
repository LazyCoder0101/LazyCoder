package com.lazycoder.database.dao;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.AttachedFile;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "AttachedFileMapper")
public interface AttachedFileMapper extends DataFormatType {

    /**
     * 添加附带文件
     *
     * @param attachedFileList
     */
    public void addAttachedFileList(List<AttachedFile> attachedFileList);

    /**
     * 获取模块附带文件路径
     *
     * @return
     */
    public List<AttachedFile> getModuleAttachedFileList(@Param("moduleId") String moduleId,
                                                        @Param("attachedFileType") int attachedFileType);

    /**
     * 获取可选模板附带文件路径
     *
     * @param additionalSerialNumber
     * @return
     */
    public List<AttachedFile> getAdditionalAttachedFileList(@Param("additionalSerialNumber") int additionalSerialNumber, @Param("attachedFileType") int attachedFileType);

    /**
     * 获取模块附带文件路径
     *
     * @return
     */
    public AttachedFile getModuleAttachedFile(@Param("moduleId") String moduleId, @Param("ordinal") int ordinal, @Param("attachedFileType") int attachedFileType);

    /**
     * 获取可选模板附带文件路径
     *
     * @param additionalSerialNumber
     * @param ordinal
     * @return
     */
    public AttachedFile getAdditionalAttachedFile(@Param("additionalSerialNumber") int additionalSerialNumber,
                                                  @Param("ordinal") int ordinal);

    /**
     * 获取模块附带文件路径数量
     *
     * @param moduleId
     */
    public Integer getNumberOfModule(@Param("moduleId") String moduleId, @Param("attachedFileType") int attachedFileType);

    /**
     * 获取可选模板附带文件路径数量
     *
     * @param additionalSerialNumber
     */
    public Integer getNumberOfAdditional(@Param("additionalSerialNumber") int additionalSerialNumber, @Param("attachedFileType") int attachedFileType);

    /**
     * 删除模块附带文件路径
     *
     * @param moduleId
     */
    public void delDataOfModule(@Param("moduleId") String moduleId, @Param("attachedFileType") int attachedFileType);

    /**
     * 删除可选模板附带文件路径
     *
     * @param additionalSerialNumber
     */
    public void delDataOfAdditional(@Param("additionalSerialNumber") int additionalSerialNumber, @Param("attachedFileType") int attachedFileType);

}
