package com.lazycoder.database.dao.command;

import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.database.model.general.CommandCodeModelInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "InitCodeModelMapper")
public interface InitCodeModelMapper extends CommandCodeModelInterface {

    /**
     * 添加代码模型
     */
    public void addCodeModel(List<InitCodeModel> codeModelList);


    /**
     * 删除代码模型
     *
     * @param moduleId
     */
    public void delCodeModel(@Param("moduleId") String moduleId);

    /**
     * 获取初始化代码模型
     *
     * @param moduleId
     * @return
     */
    public List<InitCodeModel> getCodeModelList(@Param("moduleId") String moduleId);

    /**
     * 获取初始化代码模型
     *
     * @param model
     * @return
     */
    public List<InitCodeModel> getCorrespondingCodeModelList(InitFeatureSelectonModel model);

    /**
     * 获取默认代码
     *
     * @param moduleId
     * @param whetherTheDefault
     * @return
     */
    public List<InitCodeModel> getDeafaultCodeModelList(@Param("moduleId") String moduleId, int whetherTheDefault);

}
