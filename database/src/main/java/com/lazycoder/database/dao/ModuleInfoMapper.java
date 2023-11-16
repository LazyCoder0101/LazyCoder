package com.lazycoder.database.dao;

import com.lazycoder.database.model.ModuleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 模块信息数据层
 *
 * @author hmg
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "ModuleInfoMapper")
public interface ModuleInfoMapper {

    /**
     * 添加模块信息
     *
     * @return
     */
    public void addModuleInfo(ModuleInfo moduleInfo);

    /**
     * 获取模块信息
     *
     * @return
     */
    public ModuleInfo getModuleInfo(@Param("className") String className, @Param("moduleName") String moduleName);

    /**
     * 获取模块信息
     *
     * @return
     */
    public ModuleInfo getTheModuleInfo(@Param("moduleId") String moduleId);

    /**
     * 更改模块信息
     *
     * @param moduleInfo
     * @throws Exception
     */
    public void updateModuleInfo(ModuleInfo moduleInfo);

    /**
     * 更新附带文件数量
     *
     * @param moduleInfo
     */
    public void updateNumOfModuleAttachedFile(ModuleInfo moduleInfo);

    /**
     * 查看有没有某个分类的数据
     *
     * @param className
     * @return
     */
    public Integer selectExistDataForClassification(String className);

    /**
     * 删除引入代码
     */
    public void delDataOfModule(@Param("className") String className, @Param("moduleName") String moduleName);

    /**
     * 重命名分类名和模块名
     *
     * @param moduleId
     * @param className
     * @param moduleName
     */
    public void renameModuleName(@Param("moduleId") String moduleId, @Param("className") String className, @Param("moduleName") String moduleName);


    /**
     * 重命名分类
     *
     * @param newClassName
     * @param oldClassName
     */
    public void renameClassName(String newClassName, String oldClassName);

}
