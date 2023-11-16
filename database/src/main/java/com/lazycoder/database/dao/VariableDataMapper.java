package com.lazycoder.database.dao;

import com.lazycoder.database.model.VariableData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "VariableDataMapper")
public interface VariableDataMapper {

    /**
     * 添加变量
     */
    public void addVariableList(List<VariableData> VariableDataList);

    /**
     * 删除变量
     */
    public void delVariableList(@Param("moduleId") String moduleId,
                                @Param("additionalSerialNumber") int additionalSerialNumber, @Param("variableProperty") int variableProperty);

    /**
     * 获取初始化变量
     *
     * @return
     */
    public List<VariableData> getVariableDataList(@Param("moduleId") String moduleId, @Param("additionalSerialNumber") int additionalSerialNumber,
                                                  @Param("variableProperty") int variableProperty);

}
