package com.lazycoder.database.dao;

import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.AdditionalInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 格式状态信息数据层
 *
 * @author hmg
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "FormatInfoMapper")
public interface FormatInfoMapper {


    /**
     * 添加其他信息
     *
     * @param additionalInfoList
     */
    public void addAdditionalSetInfoList(List<AdditionalInfo> additionalInfoList);

    /**
     * 更新必填模板
     *
     * @param mainInfo
     */
    public void updateMainSetInfo(MainInfo mainInfo);

    /**
     * 删除所有的其他信息
     */
    public void delAllAdditionalSetInfo(@Param("formatType") int formatType);

    /**
     * 获取必填模板信息
     *
     * @return
     */
    public MainInfo getMainInfo(@Param("formatType") int formatType);

    /**
     * 获取可选模板的信息
     *
     * @param additionalSerialNumber
     * @return
     */
    public AdditionalInfo getAdditionalInfo(@Param("additionalSerialNumber") int additionalSerialNumber, @Param("formatType") int formatType);

    public void updateNumOfAdditionalAttachedFile(AdditionalInfo additionalInfo);

    /**
     * @param additionalSerialNumber
     * @return
     */
    public String getSetTypeParamOf(@Param("additionalSerialNumber") int additionalSerialNumber, @Param("formatType") int formatType);

    /**
     * 获取所有可选模板的信息
     *
     * @return
     */
    public List<AdditionalInfo> getAdditionalInfoList(@Param("formatType") int formatType);

}
