package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "ModuleSetCodeModelMapper")
public interface ModuleSetCodeModelMapper extends CommandCodeModelInterface {

    /**
     * 添加代码模型
     */
    public void addCodeModel(List<ModuleSetCodeModel> codeModelList);

    /**
     * 更改代码模型
     */
    // public void updateCodeModel(ModuleSetCodeModel codeModel) throws
    // Exception;

    /**
     * 删除代码模型
     */
    public void delCodeModel(@Param("moduleId") String moduleId);

    /**
     * 获取初始化代码模型
     *
     * @return
     */
    public List<ModuleSetCodeModel> getCodeModelList(@Param("moduleId") String moduleId, @Param("typeName") String typeName);

    /**
     * 获取初始化代码模型
     *
     * @return
     */
    public List<ModuleSetCodeModel> getCorrespondingCodeModelList(ModuleSetFeatureSelectionModel model);

}
