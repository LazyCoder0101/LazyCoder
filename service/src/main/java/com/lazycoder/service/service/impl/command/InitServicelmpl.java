package com.lazycoder.service.service.impl.command;

import com.lazycoder.database.dao.command.InitCodeModelMapper;
import com.lazycoder.database.dao.command.InitOperatingModelMapper;
import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.database.model.featureSelectionModel.InitFeatureSelectonModel;
import com.lazycoder.service.vo.meta.InitMetaModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "InitServicelmpl")
public class InitServicelmpl {

    @Autowired
    private InitOperatingModelMapper operatingModelMapper;

    @Autowired
    private InitCodeModelMapper codeModelMapper;

    /**
     * 保存操作模型
     *
     * @param list
     * @param moduleId
     */
    public void saveOperatingModel(List<InitOperatingModel> list, String moduleId) {
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
    public void saveCodeList(List<InitCodeModel> list, String moduleId) {
        codeModelMapper.delCodeModel(moduleId);
        if (list.size() > 0) {
            codeModelMapper.addCodeModel(list);
        }
    }

    /**
     * 获取当前模块所有的初始化模型
     */
    public ArrayList<InitMetaModel> getInitMetaModelList(String moduleId) {
        InitMetaModel temp;
        List<InitOperatingModel> oList = operatingModelMapper.getOperatingModelList(moduleId);
        List<InitCodeModel> cList = codeModelMapper.getCodeModelList(moduleId);
        ArrayList<InitMetaModel> list = new ArrayList<>();
        for (int i = 0; i < oList.size(); i++) {
            temp = new InitMetaModel();
            temp.setOperatingModel(oList.get(i));
            list.add(temp);
        }
        for (InitCodeModel codeTemp : cList) {
            int ordinal = codeTemp.getOrdinal();

            for (InitMetaModel tempModel : list) {
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
    public InitMetaModel getModuleSetMetaModel(InitFeatureSelectonModel param) {
        InitMetaModel metaModel = null;

        InitOperatingModel operatingModel = operatingModelMapper.getOperatingModel(param);
        if (operatingModel != null) {
            metaModel = new InitMetaModel();
            metaModel.setOperatingModel(operatingModel);

            List<InitCodeModel> codeModelList = codeModelMapper.getCorrespondingCodeModelList(param);
            if (codeModelList != null) {
                for (int i = 0; i < codeModelList.size(); i++) {
                    metaModel.getCodeModelList().add(codeModelList.get(i));
                }
            }
        }
        return metaModel;
    }

    /**
     * 获取默认模型
     *
     * @param moduleId
     * @return
     */
    public InitMetaModel getDeafaultModuleSetMetaModel(String moduleId) {
        InitMetaModel metaModel = new InitMetaModel();

        InitOperatingModel operatingModel = operatingModelMapper.getDeafaultOperatingModel(moduleId, InitOperatingModel.TRUE_);
        if (operatingModel == null) {
            metaModel = null;
        } else {
            List<InitCodeModel> codeModelList = codeModelMapper.getDeafaultCodeModelList(moduleId, InitCodeModel.TRUE_);
            if (codeModelList != null) {
                for (int i = 0; i < codeModelList.size(); i++) {
                    metaModel.getCodeModelList().add(codeModelList.get(i));
                }
            }
        }
        metaModel.setOperatingModel(operatingModel);
        return metaModel;
    }

    /**
     * 获取功能列表
     *
     * @return
     */
    public List<InitFeatureSelectonModel> getFeatureList(String moduleId) {
        return operatingModelMapper.getFeatureList(moduleId);
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
