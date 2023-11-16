package com.lazycoder.database.dao;

import com.lazycoder.database.model.OptionDataModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper // 标识为mybatis数据层接口
@Component(value = "OptionDataModelMapper")
public interface OptionDataModelMapper {

    public List<OptionDataModel> getOptionNameList(OptionDataModel optionInputValue);

    public void addOption(OptionDataModel optionInputValue);

    public void delOptionById(String optionId);

    public void delOption(String optionName);

    public OptionDataModel getOptionById(String optionId);

    public OptionDataModel getOption(String optionName);

    public void updateOption(OptionDataModel optionInputValue);

    public List<OptionDataModel> getCorrespondingOptionNameList(@Param("moduleId") String moduleId, @Param("usingRange") int usingRange);

    public Integer getValueListGroupNumById(@Param("optionId") String optionId);

    public Integer getValueListGroupNum(@Param("optionId") String optionId);

    public Integer selectExistById(@Param("optionId") String optionId);

    public Integer selectExist(@Param("optionName") String optionName);

    /**
     * 删除引入代码
     */
    public void delDataOfModule(@Param("moduleId") String moduleId, @Param("usingRange") int usingRange);

}
