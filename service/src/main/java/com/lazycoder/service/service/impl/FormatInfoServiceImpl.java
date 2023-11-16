package com.lazycoder.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.dao.FormatInfoMapper;
import com.lazycoder.database.model.AbstractFormatInfo;
import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.MainInfo;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "FormatInfoServiceImpl")
public class FormatInfoServiceImpl implements DataFormatType {

    @Autowired
    private FormatInfoMapper formatInfoMapper;

    /**
     * 更新必填模板
     *
     * @param mainInfo
     */
    public void updateMainSetInfo(MainInfo mainInfo) {
        formatInfoMapper.updateMainSetInfo(mainInfo);
    }

    public void saveAdditionalSetInfoList(List<AdditionalInfo> additionalInfoList) {
        formatInfoMapper.delAllAdditionalSetInfo(AbstractFormatInfo.ADDITIONAL_TYPE);
        if (additionalInfoList.size() > 0) {
            formatInfoMapper.addAdditionalSetInfoList(additionalInfoList);
        }
    }

    /**
     * 获取必填模板信息
     *
     * @return
     */
    public MainInfo getMainInfo() {
        return formatInfoMapper.getMainInfo(AbstractFormatInfo.MAIN_TYPE);
    }

    /**
     * 获取可选模板的信息
     *
     * @param additionalSerialNumber
     * @return
     */
    public AdditionalInfo getAdditionalInfo(int additionalSerialNumber) {
        return formatInfoMapper.getAdditionalInfo(additionalSerialNumber, AbstractFormatInfo.ADDITIONAL_TYPE);
    }

    public void updateNumOfAdditionalAttachedFile(AdditionalInfo additionalInfo) {
        formatInfoMapper.updateNumOfAdditionalAttachedFile(additionalInfo);
    }


    /**
     * 获取所有设置类型
     *
     * @param formatInfo
     * @return
     */
    public ArrayList<String> getSetTypeListParam(AbstractFormatInfo formatInfo) {
        ArrayList<String> list = JSON.parseObject(formatInfo.getSetTheTypeOfSetCodeParam(),
                new TypeReference<ArrayList<String>>() {
                });
        return list;
    }

    /**
     * 记录设置类型参数
     *
     * @param mainInfo
     * @param MainSetTypeList
     */
    public static void setMainSetTypeListParam(MainInfo mainInfo, ArrayList<String> MainSetTypeList) {
        mainInfo.setSetTheTypeOfSetCodeParam(JsonUtil.getJsonStr(MainSetTypeList));
    }


}
