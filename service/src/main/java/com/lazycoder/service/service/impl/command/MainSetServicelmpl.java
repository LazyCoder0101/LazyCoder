package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.FormatTypeCodeModelMapper;
import com.lazycoder.database.dao.command.FormatTypeOperatingModelMapper;
import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import com.lazycoder.service.vo.meta.MainSetMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "MainSetServicelmpl")
public class MainSetServicelmpl {

    //	@Autowired
//	private MainSetOperatingModelMapper operatingModelMapper;
    @Autowired
    private FormatTypeOperatingModelMapper operatingModelMapper;

    //    @Autowired
//    private MainSetCodeModelMapper codeModelMapper;
    @Autowired
    private FormatTypeCodeModelMapper codeModelMapper;

    //    public void saveOperatingModel(List<MainSetOperatingModel> mainSetOperatingModelList) {
//        operatingModelMapper.delOperatingModel();
//        if (mainSetOperatingModelList.size() > 0) {
//            operatingModelMapper.addOperatingModel(mainSetOperatingModelList);
//        }
    public void saveOperatingModel(List<FormatTypeOperatingModel> operatingModelList) {
        operatingModelMapper.delOperatingModel(FormatTypeOperatingModelMapper.MAIN_TYPE);
        if (operatingModelList.size() > 0) {
            operatingModelMapper.addOperatingModel(operatingModelList);
        }
    }

    /**
     * 获取必填模板的模型
     *
     * @param featureSelectionModel
     * @return
     */
    public MainSetMetaModel getMainSetMetaModel(FormatTypeFeatureSelectionModel featureSelectionModel) {
        MainSetMetaModel metaModel = null;
        FormatTypeOperatingModel operatingModel = operatingModelMapper.getOperatingModel(featureSelectionModel);
        if (operatingModel != null) {
            metaModel = new MainSetMetaModel();
            List<FormatTypeCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(featureSelectionModel);
            metaModel.setOperatingModel(operatingModel);
            metaModel.getCodeModelList().addAll(cList);
        }
        return metaModel;
    }
//    public MainSetMetaModel getMainSetMetaModel(MainSetFeatureSelectionModel model) {
//        MainSetMetaModel metaModel = new MainSetMetaModel();
//        MainSetOperatingModel operatingModel = operatingModelMapper.getOperatingModel(model);
//        List<MainSetCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(model);
//        metaModel.setOperatingModel(operatingModel);
//        metaModel.getCodeModelList().addAll(cList);
//        return metaModel;
//    }

    /**
     * 获取分类为 typeName 的所有模型列表
     *
     * @param typeName
     * @return
     */
    public ArrayList<MainSetMetaModel> getMainSetMetaModelList(String typeName) {
        ArrayList<MainSetMetaModel> list = new ArrayList<>();
        MainSetMetaModel metaModel;
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getMainFormatTypeFeatureSelectionModel();
        featureSelectionModel.setTypeName(typeName);
        List<FormatTypeOperatingModel> oList = operatingModelMapper.getOperatingModelList(featureSelectionModel);
        List<FormatTypeCodeModel> cList = codeModelMapper.getCorrespondingCodeModelList(featureSelectionModel);
        for (FormatTypeOperatingModel formatTypeOperatingModel : oList) {
            metaModel = new MainSetMetaModel();
            metaModel.setOperatingModel(formatTypeOperatingModel);
            list.add(metaModel);
        }
        for (FormatTypeCodeModel codeTemp : cList) {
            int ordinal = codeTemp.getOrdinal();
            for (MainSetMetaModel tempModel: list) {
                metaModel = tempModel;
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
    public List<FormatTypeFeatureSelectionModel> getFeatureList(String typeName) {
        FormatTypeFeatureSelectionModel featureSelectionModel = FormatTypeFeatureSelectionModel.getMainFormatTypeFeatureSelectionModel();
        featureSelectionModel.setTypeName(typeName);
        return operatingModelMapper.getFeatureList(featureSelectionModel);
    }

    public void saveCodeModel(List<FormatTypeCodeModel> formatTypeCodeModelList) {
        codeModelMapper.delCodeModel(FormatTypeCodeModelMapper.MAIN_TYPE);
        if (formatTypeCodeModelList.size() > 0) {
            codeModelMapper.addCodeModel(formatTypeCodeModelList);
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
