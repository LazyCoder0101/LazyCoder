package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "InitOperatingModelMapper")
public interface InitOperatingModelMapper {

    /**
     * 添加操作模型
     */
    public void addOperatingModel(List<InitOperatingModel> operatingModelList);

    /**
     * 删除操作模型
     */
    public void delOperatingModel(@Param("moduleId") String moduleId);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<InitOperatingModel> getOperatingModelList(@Param("moduleId") String moduleId);

    /**
     * 获取初始化选择列表
     *
     * @return
     */
    // public List<InitSelectionOption> getInitSelectionList(@Param("className")
    // String className,
    // @Param("moduleName") String moduleName);

    /**
     * 获取功能列表
     *
     * @param moduleId
     * @return
     */
    public List<InitFeatureSelectonModel> getFeatureList(@Param("moduleId") String moduleId);

    /**
     * 获取初始化操作模型
     *
     * @param model
     * @return
     */
    public InitOperatingModel getOperatingModel(InitFeatureSelectonModel model);

    /**
     * 获取默认操作模型
     *
     * @param moduleId
     * @param whetherTheDefault
     * @return
     */
    public InitOperatingModel getDeafaultOperatingModel(@Param("moduleId") String moduleId, @Param("whetherTheDefault") int whetherTheDefault);

}
