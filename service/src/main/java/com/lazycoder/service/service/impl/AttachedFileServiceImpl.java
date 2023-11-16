package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.AttachedFileMapper;
import com.lazycoder.database.model.AttachedFile;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "AttachedFileServiceImpl")
public class AttachedFileServiceImpl {

    @Autowired
    private AttachedFileMapper dao;

    /**
     * 添加附带文件
     *
     * @param moduleId
     * @param attachedFileList
     */
    public void saveModuleAttachedFileList(String moduleId, List<AttachedFile> attachedFileList) {
        dao.delDataOfModule(moduleId, AttachedFileMapper.MODULE_TYPE);
        if (attachedFileList.size() > 0) {
            dao.addAttachedFileList(attachedFileList);
        }
    }

    /**
     * 获取模块附带文件路径
     *
     * @return
     */
    public List<AttachedFile> getModuleAttachedFileList(String moduleId) {
        return dao.getModuleAttachedFileList(moduleId, AttachedFileMapper.MODULE_TYPE);
    }

    /**
     * 获取可选模板附带文件路径
     *
     * @param additionalSerialNumber
     * @return
     */
    public List<AttachedFile> getAdditionalAttachedFileList(int additionalSerialNumber) {
        return dao.getAdditionalAttachedFileList(additionalSerialNumber, AttachedFileMapper.ADDITIONAL_TYPE);
    }

    /**
     * 获取模块附带文件路径
     *
     * @return
     */
    public AttachedFile getModuleAttachedFile(String moduleId, int ordinal) {
        return dao.getModuleAttachedFile(moduleId, ordinal, AttachedFileMapper.MODULE_TYPE);
    }

    /**
     * 获取可选模板附带文件路径
     *
     * @param additionalSerialNumber
     * @param ordinal
     * @return
     */
    public AttachedFile getAdditionalAttachedFile(int additionalSerialNumber, int ordinal) {
        return getAdditionalAttachedFile(additionalSerialNumber, ordinal);
    }

    /**
     * 删除可选模板附带文件路径
     *
     * @param additionalSerialNumber
     */
    public void saveAdditionalAttachedFileList(int additionalSerialNumber, List<AttachedFile> attachedFileList) {
        dao.delDataOfAdditional(additionalSerialNumber, AttachedFileMapper.ADDITIONAL_TYPE);
        if (attachedFileList.size() > 0) {
            dao.addAttachedFileList(attachedFileList);
        }
    }

    /**
     * 获取模块附带文件路径数量
     *
     * @param moduleId
     */
    public Integer getNumberOfModule(String moduleId) {
        return dao.getNumberOfModule(moduleId, AttachedFileMapper.MODULE_TYPE);
    }

    /**
     * 获取可选模板附带文件路径数量
     *
     * @param additionalSerialNumber
     */
    public Integer getNumberOfAdditonal(int additionalSerialNumber) {
        return dao.getNumberOfAdditional(additionalSerialNumber, AttachedFileMapper.ADDITIONAL_TYPE);
    }


    public void delDataOfModule(String moduleId) {
        dao.delDataOfModule(moduleId, AttachedFileMapper.MODULE_TYPE);
    }


}
