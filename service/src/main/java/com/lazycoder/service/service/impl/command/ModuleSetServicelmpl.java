package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.ModuleSetCodeModelMapper;
import com.lazycoder.database.dao.command.ModuleSetOperatingModelMapper;
import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.service.vo.meta.ModuleSetMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ModuleSetServicelmpl")
public class ModuleSetServicelmpl {

    @Autowired
    private ModuleSetOperatingModelMapper operatingModelMapper;

    @Autowired
    private ModuleSetCodeModelMapper codeModelMapper;

    /**
     * 保存
     *
     * @param list
     * @param moduleId
     */
    public void saveOperatingModel(List<ModuleSetOperatingModel> list, String moduleId) {
        operatingModelMapper.delOperatingModel(moduleId);
        if (list.size() > 0) {
            operatingModelMapper.addOperatingModel(list);
        }
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<ModuleSetFeatureSelectionModel> getFeatureList(String moduleId, String typeName) {
        return operatingModelMapper.getFeatureList(moduleId, typeName);
    }

    public void saveCodeList(List<ModuleSetCodeModel> moduleSetCodeModelList, String moduleId) {
        codeModelMapper.delCodeModel(moduleId);
        if (moduleSetCodeModelList.size() > 0) {
            codeModelMapper.addCodeModel(moduleSetCodeModelList);
        }
    }

    /**
     * 获取当前模块所有的设置模型
     */
    public ArrayList<ModuleSetMetaModel> getModuleSetMetaModelList(String moduleId, String typeName) {
        ModuleSetMetaModel temp;
        List<ModuleSetOperatingModel> oList = operatingModelMapper.getOperatingModelList(moduleId, typeName);
        List<ModuleSetCodeModel> cList = codeModelMapper.getCodeModelList(moduleId, typeName);
        ArrayList<ModuleSetMetaModel> list = new ArrayList<>();
        for (ModuleSetOperatingModel moduleSetOperatingModel : oList) {
            temp = new ModuleSetMetaModel();
            temp.setOperatingModel(moduleSetOperatingModel);
            list.add(temp);
        }
        for (ModuleSetCodeModel codeTemp : cList) {
            int ordinal = codeTemp.getOrdinal();
            for (ModuleSetMetaModel tempModel : list) {
                temp = tempModel;
                if (temp.getOperatingModel().getOrdinal() == ordinal) {
                    temp.getCodeModelList().add(codeTemp);
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 获取代码模型
     *
     * @param param
     * @return
     */
    public ModuleSetMetaModel getModuleSetMetaModel(ModuleSetFeatureSelectionModel param) {
        ModuleSetMetaModel metaModel = null;

        ModuleSetOperatingModel operatingModel = operatingModelMapper.getOperatingModel(param);
        if (operatingModel != null) {
            metaModel = new ModuleSetMetaModel();
            metaModel.setOperatingModel(operatingModel);

            List<ModuleSetCodeModel> codeModelList = codeModelMapper.getCorrespondingCodeModelList(param);
            if (codeModelList != null) {
                for (ModuleSetCodeModel moduleSetCodeModel : codeModelList) {
                    metaModel.getCodeModelList().add(moduleSetCodeModel);
                }
            }
        }
        return metaModel;
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
