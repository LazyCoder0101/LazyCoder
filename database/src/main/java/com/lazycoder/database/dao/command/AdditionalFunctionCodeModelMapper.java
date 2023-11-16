package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


@Mapper
@Component(value = "AdditionalFunctionCodeModelMapper")
public interface AdditionalFunctionCodeModelMapper extends CommandCodeModelInterface {

    /**
     * 添加代码模型
     */
    public void addCodeModel(List<AdditionalFunctionCodeModel> codeModelList);

    /**
     * 删除代码模型
     */
    public void delCodeModel(@Param("additionalSerialNumber") int additionalSerialNumber);

    /**
     * 获取初始化代码模型
     *
     * @return
     */
    public List<AdditionalFunctionCodeModel> getCodeModelList(@Param("additionalSerialNumber") int additionalSerialNumber);

    /**
     * 根据方法列表的参数来获取操作层
     *
     * @param model
     * @return
     */
    public List<AdditionalFunctionCodeModel> getCorrespondingCodeModelList(
            AdditionalFunctionFeatureSelectionModel model);


}
