package com.lazycoder.database.dao;

import com.lazycoder.database.model.ModuleControl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 模块控制数据层
 *
 * @author
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "ModuleControlMapper")
public interface ModuleControlMapper {

    /**
     * 添加模块控制
     *
     * @return
     */
    public void addModuleControl(ModuleControl module);

    /**
     * 删除模块控制
     *
     * @param moduleId
     */
    public void delModuleControl(@Param("moduleId") String moduleId);

    /**
     * 获取模块控制
     *
     * @param moduleId
     * @return
     */
    public ModuleControl getModuleControl(@Param("moduleId") String moduleId);

    /**
     * 更改模块控制
     *
     * @param module
     * @throws Exception
     */
    public void updateModuleControl(ModuleControl module);

}
