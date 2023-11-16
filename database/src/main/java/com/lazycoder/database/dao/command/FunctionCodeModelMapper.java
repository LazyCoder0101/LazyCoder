package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "FunctionCodeModelMapper")
public interface FunctionCodeModelMapper extends CommandCodeModelInterface {

    /**
     * 添加代码模型
     */
    public void addCodeModel(List<FunctionCodeModel> codeModelList);


    /**
     * 删除代码模型
     */
    public void delCodeModel(@Param("moduleId") String moduleId);

    /**
     * 获取初始化代码模型
     *
     * @return
     */
    public List<FunctionCodeModel> getCodeModelList(@Param("moduleId") String moduleId, @Param("typeName") String typeName);

    /**
     * 根据方法列表的参数来获取操作层
     *
     * @param model
     * @return
     */
    public List<FunctionCodeModel> getCorrespondingCodeModelList(FunctionFeatureSelectionModel model);

}
