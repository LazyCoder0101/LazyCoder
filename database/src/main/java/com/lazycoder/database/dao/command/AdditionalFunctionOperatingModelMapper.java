package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component(value = "AdditionalFunctionOperatingModelMapper")
public interface AdditionalFunctionOperatingModelMapper {

    /**
     * 添加操作模型
     */
    public void addOperatingModel(List<AdditionalFunctionOperatingModel> operatingModelList);

    /**
     * 删除操作模型
     */
    public void delOperatingModel(@Param("additionalSerialNumber") int additionalSerialNumber);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<AdditionalFunctionOperatingModel> getOperatingModelList(
            @Param("additionalSerialNumber") int additionalSerialNumber);

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public AdditionalFunctionOperatingModel getOperatingModel(AdditionalFunctionFeatureSelectionModel model);

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<AdditionalFunctionFeatureSelectionModel> getFeatureList(
            @Param("additionalSerialNumber") int additionalSerialNumber);

}
