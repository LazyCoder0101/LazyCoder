package com.lazycoder.service;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.service.SysService;
import java.util.ArrayList;

/**
 * 集中用来存储选项数据，还原数据的时候从这拿，避免过多向请求数据库获取重复数据（在代码编辑界面的时候从这拿，代码生成界面没必要）
 */
public class OptionDataModelTempHolder {

    private static ArrayList<OptionDataModel> optionDataModelArrayList = new ArrayList<>();

    private static OptionDataModel checkAndGetOption(String optionId) {
        OptionDataModel out = null;
        for (OptionDataModel optionDataModel : optionDataModelArrayList) {
            if (optionDataModel.getOptionId().equals(optionId)) {
                out = optionDataModel;
                break;
            }
        }
        return out;
    }

    public static void delOption(String optionId) {
        OptionDataModel temp = null;
        for (int i = 0; i < optionDataModelArrayList.size(); i++) {
            temp = optionDataModelArrayList.get(i);
            if (temp.getOptionId().equals(optionId)) {
                optionDataModelArrayList.remove(i);
                break;
            }
        }
    }

    public static void updateOption(OptionDataModel optionDataModel) {
        OptionDataModel optionDataModelTemp;
        for (int i = 0; i < optionDataModelArrayList.size(); i++) {
            optionDataModelTemp = optionDataModelArrayList.get(i);
            if (optionDataModelTemp.getOptionId().equals(optionDataModel.getOptionId())) {
                optionDataModelArrayList.set(i, optionDataModel);
                break;
            }
        }
    }

//    public static OptionDataModel getOption(ContentChooseElement contentChooseElement) {
//        OptionDataModel optionDataModel = null;
//        if (null == contentChooseElement.getOptionId()) {
//            optionDataModel = SysService.optionServiceImpl.getOption(contentChooseElement.getOptionName());
//            contentChooseElement.setOptionId(optionDataModel.getOptionId());
//
//        } else {
//            optionDataModel = getOption(contentChooseElement.getOptionId());
//            contentChooseElement.setOptionId(optionDataModel.getOptionId());
//        }
//        return optionDataModel;
//    }

    public static OptionDataModel getOption(String optionId) {
        OptionDataModel optionDataModel = checkAndGetOption(optionId);
        if (optionDataModel == null) {
            optionDataModel = SysService.OPTION_SERVICE.getOptionById(optionId);
            if (optionDataModel != null) {
                optionDataModelArrayList.add(optionDataModel);
            }
        }
        return optionDataModel;
    }

    public static void clearAllOptionData() {
        optionDataModelArrayList.clear();
    }

}
