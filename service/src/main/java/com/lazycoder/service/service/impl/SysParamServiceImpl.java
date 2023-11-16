package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.DataSource;
import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.bean.DruidDBConfig;
import com.lazycoder.database.common.EncodingTypeEnum;
import com.lazycoder.database.dao.SysParamMapper;
import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.SysParam;
import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.RealNumberUtil;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "SysParamServiceImpl")
public class SysParamServiceImpl implements BaseModel {

    @Autowired
    private SysParamMapper dao;


    /**
     * 获取设置编程语言
     *
     * @return
     */
    public ProgramingLanguage getUseProgramingLanguage() {
        ProgramingLanguage programingLanguage = ProgramingLanguage.C;
        String sysParam = getParamValue(SysParam.USE_PROGRAMING_LANGUAGE);
        if (sysParam != null) {
            boolean flag = RealNumberUtil.isInteger(sysParam);
            if (flag == true) {
                programingLanguage = ProgramingLanguage.getEncodingTypeBy(RealNumberUtil.convertedToInteger(sysParam));
            }
        }
        return programingLanguage;
    }

    /**
     * 设置使用编程语言
     */
    @TransactionMulti
    public void setUseProgramingLanguage(ProgramingLanguage programingLanguage) {
        setSysParam(SysParam.USE_PROGRAMING_LANGUAGE, programingLanguage.getSysDictionaryValue() + "");
    }

    /**
     * 获取数据源的启用状态（目前数据源是否可以使用）
     *
     * @return
     */
    public boolean getEnabledState() {
        boolean enabledState = true;
        String sysParam = getParamValue(SysParam.ENABLED_STATE);
        if ("1".equals(sysParam)) {
            enabledState = true;
        } else if ("0".equals(sysParam)) {
            enabledState = false;
        }
        return enabledState;
    }

    /**
     * 设置数据源目前是否可以使用
     *
     * @param enabledState
     */
    public void setEnabledState(boolean enabledState) {
        if (enabledState == true) {
            setSysParam(SysParam.ENABLED_STATE, TRUE_ + "");
        } else if (enabledState == false) {
            setSysParam(SysParam.ENABLED_STATE, FALSE_ + "");
        }
    }

    /**
     * 获取编码格式
     *
     * @return
     */
    public EncodingTypeEnum getEncoding() {
        EncodingTypeEnum encodingTypeEnum = EncodingTypeEnum.UTF8;
        String sysParam = getParamValue(SysParam.ENCODING);
        if (sysParam != null) {
            boolean flag = RealNumberUtil.isInteger(sysParam);
            if (flag == true) {
                encodingTypeEnum = EncodingTypeEnum.getEncodingTypeBy(RealNumberUtil.convertedToInteger(sysParam));
            }
        }
        return encodingTypeEnum;
    }

    /**
     * 设置编码格式
     */
    @TransactionMulti
    public void setEncoding(EncodingTypeEnum encodingTypeEnum) {
        setSysParam(SysParam.ENCODING, encodingTypeEnum.getDictionaryValue() + "");
    }

    /**
     * 获取重命名文件
     *
     * @return
     */
    public ArrayList<String> getRenameFileList() {
        ArrayList<String> list = new ArrayList<String>();
        String renameListStr = getParamValue(SysParam.RENAME_FILE_PARAM);
        if (renameListStr != null) {
            list = JSON.parseObject(renameListStr, new TypeReference<ArrayList<String>>() {
            });
        }
        return list;
    }

    /**
     * 记录需要重命名的文件
     *
     * @param renameList
     */
    @TransactionMulti
    public void setRenameFileList(ArrayList<String> renameList) {
        String str = JsonUtil.getJsonStr(renameList);
        setSysParam(SysParam.RENAME_FILE_PARAM, str);
    }

    /**
     * 获取上一次设置的项目路径（固定从默认系统数据源获取）
     */
    @DataSource(DruidDBConfig.SYS_DATA_SOURCE)
    public String getLastSetProPath() {
        return getParamValue(SysParam.LAST_SET_PRO_PATH);
    }

    @DataSource(DruidDBConfig.SYS_DATA_SOURCE)
    public boolean getAutoPositionState(){
        boolean autoPositionState = true;
        String sysParam = getParamValue(SysParam.AUTO_POSITION);
        if ("1".equals(sysParam)) {
            autoPositionState = true;
        } else if ("0".equals(sysParam)) {
            autoPositionState = false;
        }
        return autoPositionState;
    }

    /**
     *
     * @param autoPositionState
     */
    @DataSource(DruidDBConfig.SYS_DATA_SOURCE)
    public void setAutoPositionState(boolean autoPositionState) {
        if (autoPositionState == true) {
            setSysParam(SysParam.AUTO_POSITION, TRUE_ + "");
        } else if (autoPositionState == false) {
            setSysParam(SysParam.AUTO_POSITION, FALSE_ + "");
        }
    }


    /**
     * 设置最后一次的项目路径
     *
     * @param path
     */
    @DataSource(DruidDBConfig.SYS_DATA_SOURCE)
    public void setLastProPath(String path) {
        setSysParam(SysParam.LAST_SET_PRO_PATH, path);
    }

    /**
     * 获取系统信息
     *
     * @param id
     * @return
     */
    public String getParamValue(int id) {
        SysParam sysParam = dao.getSysParam(id);
        return sysParam.getParamValue();
    }

    /**
     * 更改系统信息
     *
     * @param id
     * @param paramValue
     */
    @TransactionMulti
    public void updateSysParam(int id, String paramValue) {
        setSysParam(id, paramValue);
    }

    /**
     * 更新客户端版本号（该方法不加@TransactionMulti注释，专门用于保存数据的时候一起调用，以更新客户端版本）
     */
    public void updateDataSourceClientVersion() {
        setSysParam(SysParam.DS_CLI_VER, System.getProperty("clientVersion"));
    }

    private void setSysParam(int id, String paramValue) {
        SysParam sysParam = new SysParam();
        sysParam.setId(id);
        sysParam.setParamValue(paramValue);
        dao.updateSysParam(sysParam);
    }

}
