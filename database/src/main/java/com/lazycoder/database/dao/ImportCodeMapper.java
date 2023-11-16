package com.lazycoder.database.dao;

import com.lazycoder.database.model.ImportCode;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ImportCodeMapper")
public interface ImportCodeMapper extends CommandCodeModelInterface {

    /**
     * 添加引入代码
     */
    public void addImportCode(List<ImportCode> importCodeList);

    /**
     * 更改引入代码
     */
    // public void updateImportCode(UpdateImportCode ImportCode) throws
    // Exception;

    /**
     * 删除引入代码
     */
    public void delDataOfModule(@Param("moduleId") String moduleId);

    /**
     * 获取初始化引入代码
     *
     * @return
     */
    public List<ImportCode> getImportCodeList(@Param("moduleId") String moduleId);

}
