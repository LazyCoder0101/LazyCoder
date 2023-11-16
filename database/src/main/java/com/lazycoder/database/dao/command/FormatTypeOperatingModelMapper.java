package com.lazycoder.database.dao.command;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "FormatTypeOperatingModelMapper")
public interface FormatTypeOperatingModelMapper extends DataFormatType {

    /**
     * 添加操作模型
     */
    public void addOperatingModel(List<FormatTypeOperatingModel> operatingModelList);

    /**
     * 删除操作模型
     */
    public void delOperatingModel(int formatType);


    /**
     * 获取初始化操作模型（ featureSelectionModel 仅设置 formatType、additionalSerialNumber、typeName、typeSerialNumber
     *
     * @return
     */
    public FormatTypeOperatingModel getOperatingModel(FormatTypeFeatureSelectionModel featureSelectionModel);

    /**
     * 仅设置 formatType、 typeName、additionalSerialNumber
     *
     * @param featureSelectionModel
     * @return
     */
    public List<FormatTypeOperatingModel> getOperatingModelList(FormatTypeFeatureSelectionModel featureSelectionModel);

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<FormatTypeFeatureSelectionModel> getFeatureList(FormatTypeFeatureSelectionModel featureSelectionModel);

}
