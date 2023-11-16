package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "FunctionOperatingModelMapper")
public interface FunctionOperatingModelMapper {

    /**
     * 添加操作模型
     */
    public void addOperatingModel(List<FunctionOperatingModel> operatingModelList);

    /**
     * 更改操作模型
     */
    // public void updateOperatingModel(FunctionOperatingModel operatingModel)
    // throws Exception;

    /**
     * 删除操作模型
     */
    public void delOperatingModel(@Param("moduleId") String moduleId);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<FunctionOperatingModel> getOperatingModelList(@Param("moduleId") String moduleId, @Param("typeName") String typeNames);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public FunctionOperatingModel getOperatingModel(FunctionFeatureSelectionModel model);

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<FunctionFeatureSelectionModel> getFeatureList(@Param("moduleId") String moduleId, @Param("typeName") String typeName);

}
