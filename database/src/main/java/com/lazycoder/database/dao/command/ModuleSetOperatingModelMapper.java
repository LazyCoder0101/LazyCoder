package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ModuleSetOperatingModelMapper")
public interface ModuleSetOperatingModelMapper {

    /**
     * 添加操作模型
     */
    public void addOperatingModel(List<ModuleSetOperatingModel> operatingModelList);

    /**
     * 更改操作模型
     */
    // public void updateOperatingModel(ModuleSetOperatingModel operatingModel)
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
    public List<ModuleSetOperatingModel> getOperatingModelList(@Param("moduleId") String moduleId, @Param("typeName") String typeName);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public ModuleSetOperatingModel getOperatingModel(ModuleSetFeatureSelectionModel model);

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<ModuleSetFeatureSelectionModel> getFeatureList(@Param("moduleId") String moduleId, @Param("typeName") String typeName);

}
