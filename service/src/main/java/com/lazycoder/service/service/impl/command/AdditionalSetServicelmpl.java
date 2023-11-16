package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.FormatTypeCodeModelMapper;
import com.lazycoder.database.dao.command.FormatTypeOperatingModelMapper;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.service.vo.meta.AdditionalSetMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "AdditionalSetServicelmpl")
public class AdditionalSetServicelmpl {

    @Autowired
    private FormatTypeOperatingModelMapper operatingModelMapper;

    @Autowired
    private FormatTypeCodeModelMapper codeModelMapper;

    public void saveOperatingModel(List<FormatTypeOperatingModel> formatTypeOperatingModelList) {
        operatingModelMapper.delOperatingModel(FormatTypeOperatingModelMapper.ADDITIONAL_TYPE);
        if (formatTypeOperatingModelList.size() > 0) {
            operatingModelMapper.addOperatingModel(formatTypeOperatingModelList);
        }
    }

    /**
     * 获取可选模板的模型
     *
     * @param model
     * @return
     */
    public AdditionalSetMetaModel getAdditionalSetMetaModel(FormatTypeFeatureSelectionModel model) {
        AdditionalSetMetaModel metaModel = null;
        FormatTypeOperatingModel operatingModel = operatingModelMapper.getOperatingModel(model);
        if (operatingModel != null) {
            metaModel = new AdditionalSetMetaModel();
            List<FormatTypeCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(model);
            metaModel.setOperatingModel(operatingModel);
            metaModel.getCodeModelList().addAll(cList);
        }
        return metaModel;
    }

    /**
     * 获取分类为 typeName 的所有模型列表
     *
     * @param typeName
     * @return
     */
    public ArrayList<AdditionalSetMetaModel> getAdditionalSetMetaModelList(int additionalSerialNumber, String typeName) {
        ArrayList<AdditionalSetMetaModel> list = new ArrayList<>();
        AdditionalSetMetaModel metaModel;
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getAdditionalFormatTypeFeatureSelectionModel();
        featureSelectionModel.setAdditionalSerialNumber(additionalSerialNumber);
        featureSelectionModel.setTypeName(typeName);
        List<FormatTypeOperatingModel> oList = operatingModelMapper.getOperatingModelList(featureSelectionModel);
        List<FormatTypeCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(featureSelectionModel);
        for (FormatTypeOperatingModel formatTypeOperatingModel : oList) {
            metaModel = new AdditionalSetMetaModel();
            metaModel.setOperatingModel(formatTypeOperatingModel);
            list.add(metaModel);
        }
        for (FormatTypeCodeModel codeTemp : cList) {
            int ordinal = codeTemp.getOrdinal();
            for (int a = 0; a < list.size(); a++) {
                metaModel = list.get(a);
                if (metaModel.getOperatingModel().getOrdinal() == ordinal) {
                    metaModel.getCodeModelList().add(codeTemp);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<FormatTypeOperatingModel> getOperatingModelList(int additionalSerialNumber, String typeName) {
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getAdditionalFormatTypeFeatureSelectionModel();
        featureSelectionModel.setAdditionalSerialNumber(additionalSerialNumber);
        featureSelectionModel.setTypeName(typeName);
        return operatingModelMapper.getOperatingModelList(featureSelectionModel);
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<FormatTypeFeatureSelectionModel> getFeatureList(int additionalSerialNumber, String typeName) {
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getAdditionalFormatTypeFeatureSelectionModel();
        featureSelectionModel.setAdditionalSerialNumber(additionalSerialNumber);
        featureSelectionModel.setTypeName(typeName);
        return operatingModelMapper.getFeatureList(featureSelectionModel);
    }

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public FormatTypeOperatingModel getOperatingModel(FormatTypeFeatureSelectionModel featureSelectionModel) {
        return operatingModelMapper.getOperatingModel(featureSelectionModel);
    }

    public void saveCodeModel(List<FormatTypeCodeModel> formatTypeCodeModelList) {
        codeModelMapper.delCodeModel(FormatTypeCodeModelMapper.ADDITIONAL_TYPE);
        if (formatTypeCodeModelList.size() > 0) {
            codeModelMapper.addCodeModel(formatTypeCodeModelList);
        }
    }

    /**
     * 添加操作模型
     */
    public void addCodeModel(List<FormatTypeCodeModel> formatTypeCodeModelList) {
        try {
            codeModelMapper.addCodeModel(formatTypeCodeModelList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 删除操作模型
     */
    public void delCodeModel() {
        codeModelMapper.delCodeModel(FormatTypeCodeModelMapper.ADDITIONAL_TYPE);
    }

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<FormatTypeCodeModel> getCodeModelList(int additionalSerialNumber, String typeName) {
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getAdditionalFormatTypeFeatureSelectionModel();
        featureSelectionModel.setAdditionalSerialNumber(additionalSerialNumber);
        featureSelectionModel.setTypeName(typeName);
        return codeModelMapper.getCorrespondingCodeModelList(featureSelectionModel);
    }

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public List<FormatTypeCodeModel> getCorrespondingCodeModelList(FormatTypeFeatureSelectionModel model) {
        return codeModelMapper.getCorrespondingCodeModelList(model);
    }

}
