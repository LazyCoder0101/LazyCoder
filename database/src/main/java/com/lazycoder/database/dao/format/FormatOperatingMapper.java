package com.lazycoder.database.dao.format;

import com.lazycoder.database.model.featureSelectionModel.AdditionalFeatureSelection;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 其他操作数据层
 *
 * @author hmg
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "FormatOperatingMapper")
public interface FormatOperatingMapper {

    /**
     * 添加其他操作
     *
     * @return
     */
    public void addFormatOperatingList(List<AdditionalOperating> operatingList);

    /**
     * 删除其他操作
     */
    public void delAllAdditionalOperating(@Param("formatType") int formatType);

    /**
     * 获取所有其他操作
     *
     * @return
     */
    public List<AdditionalOperating> getAllOperatingList(@Param("formatType") int formatType);

    /**
     * 获取其他操作
     *
     * @return
     */
    public AdditionalOperating getAdditionalOperating(@Param("formatType") int formatType, @Param("additionalSerialNumber") int additionalSerialNumber);

    /**
     * 获取其他可选列表
     *
     * @return
     */
    public List<AdditionalFeatureSelection> getAdditionalFeatureSelectionList(@Param("formatType") int formatType);

    /**
     * 获取其他可选列表的数量（formatType固定传DataFormatType.ADDITIONAL_TYPE，为避免后面发生改变才传参数）
     *
     * @return
     */
    public Integer getAdditionalFeatureSelectionNum(@Param("formatType") int formatType);

    /**
     * 获取必填模板控制
     *
     * @param formatType 固定传DataFormatType.mainType
     * @return
     */
    public MainOperating getMainOperating(@Param("formatType") int formatType);

    /**
     * 更改必填模板控制
     *
     * @param formatOperating
     * @throws Exception
     */
    public void updateFormatOperating(@Param("formatOperating") GenerlFormatOperatingModel formatOperating, @Param("formatType") int formatType);

    /**
     * 按条件查找可选模板的操作数据
     *
     * @param setPropertyList
     * @return
     */
    public List<AdditionalOperating> getAdditionalOperatingList(@Param("setPropertyList") List<Integer> setPropertyList);

}
