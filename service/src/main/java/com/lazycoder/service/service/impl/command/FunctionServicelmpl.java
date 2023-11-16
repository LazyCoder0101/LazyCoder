package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.FunctionCodeModelMapper;
import com.lazycoder.database.dao.command.FunctionOperatingModelMapper;
import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "FunctionServicelmpl")
public class FunctionServicelmpl {

    @Autowired
    private FunctionOperatingModelMapper operatingModelMapper;

    @Autowired
    private FunctionCodeModelMapper codeModelMapper;

    /**
     * 保存
     *
     * @param list
     * @param moduleId
     */
    public void saveOperatingModel(List<FunctionOperatingModel> list, String moduleId) {
        operatingModelMapper.delOperatingModel(moduleId);
        if (list.size() > 0) {
            operatingModelMapper.addOperatingModel(list);
        }
    }

    /**
     * 保存
     *
     * @param list
     * @param moduleId
     */
    public void saveCodeModel(List<FunctionCodeModel> list, String moduleId) {
        codeModelMapper.delCodeModel(moduleId);
        if (list.size() > 0) {
            codeModelMapper.addCodeModel(list);
        }
    }

    /**
     * 获取当前模块所有的功能模型
     */
    public ArrayList<FunctionMetaModel> getFunctionMetaModelList(String moduleId, String typeName) {
        FunctionMetaModel temp;
        FunctionCodeModel codeTemp;
        List<FunctionOperatingModel> oList = operatingModelMapper.getOperatingModelList(moduleId,
                typeName);
        List<FunctionCodeModel> cList = codeModelMapper.getCodeModelList(moduleId, typeName);
        ArrayList<FunctionMetaModel> list = new ArrayList<>();
        for (int i = 0; i < oList.size(); i++) {
            temp = new FunctionMetaModel();
            temp.setOperatingModel(oList.get(i));
            list.add(temp);
        }
        for (int i = 0; i < cList.size(); i++) {
            codeTemp = cList.get(i);
            int ordinal = codeTemp.getOrdinal();
            for (FunctionMetaModel tempModel : list) {
                temp = tempModel;
                if (temp.getOperatingModel().getOrdinal() == ordinal) {
                    temp.getCodeModelList().add(codeTemp);
                    break;
                }
            }
        }
        return list;
    }

    public FunctionMetaModel getFunctionMetaModel(FunctionFeatureSelectionModel param) {
        FunctionMetaModel metaModel = null;

        FunctionOperatingModel operatingModel = operatingModelMapper.getOperatingModel(param);
        if (operatingModel != null) {
            metaModel = new FunctionMetaModel();
            metaModel.setOperatingModel(operatingModel);

            List<FunctionCodeModel> codeModelList = codeModelMapper.getCorrespondingCodeModelList(param);
            if (codeModelList != null) {
                for (FunctionCodeModel functionCodeModel : codeModelList) {
                    metaModel.getCodeModelList().add(functionCodeModel);
                }
            }
        }
        return metaModel;
    }

    /**
     * 获取初始化操作模型
     *
     * @return
     */
    public FunctionOperatingModel getOperatingModel(FunctionFeatureSelectionModel model) {
        return operatingModelMapper.getOperatingModel(model);
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<FunctionFeatureSelectionModel> getFeatureList(String moduleId, String typeName) {
        return operatingModelMapper.getFeatureList(moduleId, typeName);
    }

    public void delDataOfModule(String moduleId) {
        operatingModelMapper.delOperatingModel(moduleId);
        codeModelMapper.delCodeModel(moduleId);
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
