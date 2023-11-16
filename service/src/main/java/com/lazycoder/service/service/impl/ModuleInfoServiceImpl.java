package com.lazycoder.service.service.impl;

import com.lazycoder.database.dao.ModuleInfoMapper;
import com.lazycoder.database.model.ModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ModuleInfoServiceImpl")
public class ModuleInfoServiceImpl {

    @Autowired
    private ModuleInfoMapper dao;

    public void addModuleInfo(ModuleInfo moduleInfo) {
        // TODO Auto-generated method stub
        dao.addModuleInfo(moduleInfo);
    }

    /**
     * 获取模块信息
     *
     * @return
     */
    public ModuleInfo getModuleInfo(String className, String moduleName) {
        // TODO Auto-generated method stub
        return dao.getModuleInfo(className, moduleName);
    }

    /**
     * 获取模块信息
     *
     * @return
     */
    public ModuleInfo getModuleInfo(String moduleId) {
        // TODO Auto-generated method stub
        return dao.getTheModuleInfo(moduleId);
    }

    /**
     * 更改模块信息
     *
     * @param moduleInfo
     */
    public void updateModuleInfo(ModuleInfo moduleInfo) {
        // TODO Auto-generated method stub
        dao.updateModuleInfo(moduleInfo);
    }

    /**
     * 更新附带文件数量
     *
     * @param moduleInfo
     */
    public void updateNumOfModuleAttachedFile(ModuleInfo moduleInfo) {
        // TODO Auto-generated method stub
        dao.updateNumOfModuleAttachedFile(moduleInfo);
    }

    /**
     * 查询分类className有没有添加过模块
     *
     * @param className
     * @return
     */
    public boolean selectHaveAddModuleFor(String className) {
        boolean flag = false;
        if (dao.selectExistDataForClassification(className) > 0) {
            flag = true;
        }
        return flag;
    }

    public void delDataOfModule(String className, String moduleName) {
        // TODO Auto-generated method stub
        dao.delDataOfModule(className, moduleName);
    }

    /**
     * 重命名分类名和模块名
     *
     * @param moduleId
     * @param className
     * @param moduleName
     */
    public void renameModuleName(String moduleId, String className, String moduleName) {
        dao.renameModuleName(moduleId, className, moduleName);
    }

    /**
     * 重命名分类名
     * @param newClassName
     * @param oldClassName
     */
    public void renameClassName(String newClassName, String oldClassName) {
        dao.renameClassName(newClassName, oldClassName);
    }

}
