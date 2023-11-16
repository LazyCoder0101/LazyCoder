package com.lazycoder.database.dao.command;

import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "FormatTypeCodeModelMapper")
public interface FormatTypeCodeModelMapper extends DataFormatType, CommandCodeModelInterface {

    /**
     * 添加代码模型
     */
    public void addCodeModel(List<FormatTypeCodeModel> codeModelList);

    /**
     * 删除代码模型
     */
    public void delCodeModel(int formatType);


    /**
     * 获取代码模型（ featureSelectionModel 仅设置 formatType、additionalSerialNumber、typeName、typeSerialNumber）
     *
     * @return
     */
    public List<FormatTypeCodeModel> getCorrespondingCodeModelList(FormatTypeFeatureSelectionModel featureSelectionModel);

}
