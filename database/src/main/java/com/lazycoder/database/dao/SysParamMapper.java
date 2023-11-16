package com.lazycoder.database.dao;

import com.lazycoder.database.model.SysParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 系统信息
 *
 * @author
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "SysParamMapper")
public interface SysParamMapper {

    /**
     * 获取系统信息
     *
     * @param id
     * @return
     */
//	@DataSource("sysdb")
    public SysParam getSysParam(@Param("id") int id);

    /**
     * 更改系统信息
     *
     * @param sysParam
     */
//	@DataSource("sysdb")
    public void updateSysParam(SysParam sysParam);

}
