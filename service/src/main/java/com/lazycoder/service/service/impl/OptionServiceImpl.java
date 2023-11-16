package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.lazycoder.database.dao.OptionDataModelMapper;
import com.lazycoder.database.model.OptionDataModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "OptionServiceImpl")
public class OptionServiceImpl {

    @Autowired
    private OptionDataModelMapper dao;

    @TransactionMulti
    public void addOption(OptionDataModel optionInputValue) {
        dao.addOption(optionInputValue);
    }

    @TransactionMulti
    public void delOptionById(String optionId) {
        dao.delOptionById(optionId);
    }

    @TransactionMulti
    public void delOption(String optionName) {
        dao.delOption(optionName);
    }

    public OptionDataModel getOption(String optionName) {
        return dao.getOption(optionName);
    }

    public OptionDataModel getOptionById(String optionId) {
        return dao.getOptionById(optionId);
    }

    @TransactionMulti
    public void updateOption(OptionDataModel optionInputValue) {
        dao.updateOption(optionInputValue);
    }

    public List<OptionDataModel> getGeneralOptionNameList() {
        OptionDataModel optionDataModel = new OptionDataModel();
        optionDataModel.setUsingRange(OptionDataModel.GENERAL_TYPE);
        return dao.getOptionNameList(optionDataModel);
    }

    public List<OptionDataModel> getCorrespondingOptionNameList(String moduleId) {
        OptionDataModel optionDataModel = new OptionDataModel();
        optionDataModel.setUsingRange(OptionDataModel.MODULE_TYPE);
        optionDataModel.setModuleId(moduleId);
        return dao.getOptionNameList(optionDataModel);
    }

    public List<OptionDataModel> getAdditionalOptionNameList(int additionalSerialNumber) {
        OptionDataModel optionDataModel = new OptionDataModel();
        optionDataModel.setUsingRange(OptionDataModel.ADDITIONAL_TYPE);
        optionDataModel.setAdditionalSerialNumber(additionalSerialNumber);
        return dao.getOptionNameList(optionDataModel);
    }

    public List<OptionDataModel> getMainOptionNameList() {
        OptionDataModel optionDataModel = new OptionDataModel();
        optionDataModel.setUsingRange(OptionDataModel.MAIN_TYPE);
        return dao.getOptionNameList(optionDataModel);
    }

    public Integer getValueListGroupNum(String optionId) {
        return dao.getValueListGroupNum(optionId);
    }

    public Integer getValueListGroupNumById(String optionId) {
        return dao.getValueListGroupNumById(optionId);
    }

    /**
     * 查询有没有添加过该模块
     *
     * @param optionName
     * @return
     */
    public boolean selectExist(String optionName) {
        boolean flag = true;
        Integer result = dao.selectExist(optionName);
        if (result == null) {
            flag = false;
        } else if (result == 1) {
            flag = true;
        }
        return flag;
    }

    public boolean selectExistById(String optionId) {
        boolean flag = true;
        Integer result = dao.selectExistById(optionId);
        if (result == null) {
            flag = false;
        } else if (result == 1) {
            flag = true;
        }
        return flag;
    }


    public void delDataOfModule(String moduleId) {
        dao.delDataOfModule(moduleId, OptionDataModel.MODULE_TYPE);
    }

}
