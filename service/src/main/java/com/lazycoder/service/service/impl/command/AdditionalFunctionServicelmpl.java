package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.AdditionalFunctionCodeModelMapper;
import com.lazycoder.database.dao.command.AdditionalFunctionOperatingModelMapper;
import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "AdditionalFunctionServicelmpl")
public class AdditionalFunctionServicelmpl {

    @Autowired
    private AdditionalFunctionOperatingModelMapper operatingModelMapper;

    @Autowired
    private AdditionalFunctionCodeModelMapper codeModelMapper;

    public void saveOperatingModel(List<AdditionalFunctionOperatingModel> additionalFunctionOperatingModelList,
                                   int additionalSerialNumber) {
        operatingModelMapper.delOperatingModel(additionalSerialNumber);
        if (additionalFunctionOperatingModelList.size() > 0) {
            operatingModelMapper.addOperatingModel(additionalFunctionOperatingModelList);
        }
    }

    /**
     * 获取其他附带函数的模型
     *
     * @param model
     * @return
     */
    public AdditionalFunctionMetaModel getAdditionalMetaModel(AdditionalFunctionFeatureSelectionModel model) {
        AdditionalFunctionMetaModel metaModel = null;
        AdditionalFunctionOperatingModel operatingModel = operatingModelMapper.getOperatingModel(model);
        if (operatingModel != null) {
            metaModel = new AdditionalFunctionMetaModel();
            List<AdditionalFunctionCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(model);
            metaModel.setOperatingModel(operatingModel);
            metaModel.getCodeModelList().addAll(cList);
        }
        return metaModel;
    }

    /**
     * 获取additionalSerialNumber 的所有模型列表
     *
     * @param additionalSerialNumber
     * @return
     */
    public ArrayList<AdditionalFunctionMetaModel> getAdditionalMetaModelList(int additionalSerialNumber) {
        ArrayList<AdditionalFunctionMetaModel> list = new ArrayList<>();
        AdditionalFunctionMetaModel metaModel;
        List<AdditionalFunctionOperatingModel> oList = operatingModelMapper.getOperatingModelList(additionalSerialNumber);
        List<AdditionalFunctionCodeModel> cList = codeModelMapper.getCodeModelList(additionalSerialNumber);
        for (AdditionalFunctionOperatingModel additionalFunctionOperatingModel : oList) {
            metaModel = new AdditionalFunctionMetaModel();
            metaModel.setOperatingModel(additionalFunctionOperatingModel);
            list.add(metaModel);
        }
        for (AdditionalFunctionCodeModel codeTemp : cList) {
            int ordinal = codeTemp.getOrdinal();
            for (AdditionalFunctionMetaModel tempMetaModel : list) {
                metaModel = tempMetaModel;
                if (metaModel.getOperatingModel().getOrdinal() == ordinal) {
                    metaModel.getCodeModelList().add(codeTemp);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<AdditionalFunctionFeatureSelectionModel> getFeatureList(int additionalSerialNumber) {
        return operatingModelMapper.getFeatureList(additionalSerialNumber);
    }

    public void saveCodeModel(List<AdditionalFunctionCodeModel> moduleSetCodeModelList, int additionalSerialNumber) {
        codeModelMapper.delCodeModel(additionalSerialNumber);
        if (moduleSetCodeModelList.size() > 0) {
            codeModelMapper.addCodeModel(moduleSetCodeModelList);
        }
    }

    /**
     * 查看是否有使用这个代码标签
     *
     * @param codeLabelId 代码标签的对应id
     * @return
     */
    public boolean selectExistCodeLabelIdUsed(String codeLabelId) {
        boolean flag = false;
        if (codeModelMapper.selectExistCodeLabelIdUsed(codeLabelId) > 0) {
            flag = true;
        }
        return flag;
    }

}
