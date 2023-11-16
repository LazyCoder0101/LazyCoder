package com.lazycoder.database.dao;

import com.lazycoder.database.model.FunctionNameData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "FunctionNameDataMapper")
public interface FunctionNameDataMapper {

    /**
     * 添加变量
     */
    public void addFunctionNameDataList(List<FunctionNameData> functionNameDataList);

    /**
     * 删除变量
     */
    public void delFunctionNameDataList(@Param("moduleId") String moduleId,
                                        @Param("additionalSerialNumber") int additionalSerialNumber, @Param("functionNameProperty") int functionNameProperty);

    /**
     * 获取方法名
     *
     * @return
     */
    public List<FunctionNameData> getFunctionNameDataList(@Param("moduleId") String moduleId, @Param("additionalSerialNumber") int additionalSerialNumber,
                                                          @Param("functionNameProperty") int functionNameProperty);

}
