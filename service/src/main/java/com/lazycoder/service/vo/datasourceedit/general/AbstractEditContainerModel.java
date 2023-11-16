package com.lazycoder.service.vo.datasourceedit.general;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import lombok.Data;

@Data
public abstract class AbstractEditContainerModel {

    /**
     * 不常用控制列表
     */
    protected LinkedHashMap<String, BaseOperatingPane> infrequentlyUsedControlList = new LinkedHashMap<>();
    /**
     * 添加的控制组件的对应信息
     **/
    protected ArrayList<BaseElementInterface> controlComponentCorrespondingInformationList = new ArrayList<>();
    /**
     * 各个组件使用的数量
     */
    private LinkedHashMap<String, Integer> useComponentNum = new LinkedHashMap<String, Integer>();

    public AbstractEditContainerModel() {
        // TODO Auto-generated constructor stub
        clearUseComponentNum();
    }

    /**
     * 根据json字符串还原controlComponentCorrespondingInformationList（待写）
     *
     * @param useComponentNumJsonToString
     */
    private static void setControlComponentCorrespondingInformationList(AbstractEditContainerModel model, String useComponentNumJsonToString) {
        model.getControlComponentCorrespondingInformationList().clear();
        ArrayList<BaseElementInterface> list = DeserializeElementMethods.getControlComponentCorrespondingInformationList(useComponentNumJsonToString);
        model.controlComponentCorrespondingInformationList = list;
    }

    /**
     * 根据json字符串还原use_component_num
     *
     * @param UseComponentNumJsonToString
     */
    private static void setUseComponentNum(AbstractEditContainerModel model, String UseComponentNumJsonToString) {
        model.useComponentNum.clear();
        model.useComponentNum = JSON.parseObject(UseComponentNumJsonToString,
                new TypeReference<LinkedHashMap<String, Integer>>() {
                });
    }

    /**
     * 把controlComponentCorrespondingInformationList转为json字符串
     *
     * @param model
     * @return
     */
    public static String getControlComponentCorrespondingInformationListJsonStr(AbstractEditContainerModel model) {
        return JsonUtil.getJsonStr(model.getControlComponentCorrespondingInformationList());
    }

    /**
     * 设置组件信息和各个组件使用数量信息（还原时用）
     *
     * @param operatingModel
     */
    public void reductionContent(GeneralOperatingModel operatingModel) {
        if (operatingModel != null) {
            setUseComponentNum(this, operatingModel.getNumberOfComponents());
            setControlComponentCorrespondingInformationList(this,
                    operatingModel.getControlComponentCorrespondingInformation());
        }
    }

    /**
     * 更改选项值
     *
     * @param option
     */
    public void updateComboboxItems(OptionDataModel option) {
        ArrayList<BaseCodePane> codePaneList = getCodePaneList();
        for (BaseCodePane codePane : codePaneList) {
            codePane.updateComboboxItems(option);
        }
        ArrayList<BaseOperatingPane> operatingPaneList = getAllControlPaneList();
        for (BaseOperatingPane operatingPane : operatingPaneList) {
            operatingPane.updateItems(option);
        }
    }

    /**
     * 删除选项
     *
     * @param optionId
     */
    public void delContentChoose(String optionId) {
        ArrayList<BaseOperatingPane> list = getAllControlPaneList();
        for (BaseOperatingPane operatingPane : list) {
            operatingPane.delContentChoose(optionId);
        }
        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.delContentChooseControlComponentCorrespondingInformation(optionId, controlComponentCorrespondingInformationList);
        this.controlComponentCorrespondingInformationList = elementList;
    }

    /**
     * 获取所有的控制面板
     *
     * @return
     */
    public ArrayList<BaseOperatingPane> getAllControlPaneList() {
        ArrayList<BaseOperatingPane> list = new ArrayList<BaseOperatingPane>();
        for (String key : infrequentlyUsedControlList.keySet()) {
            list.add(infrequentlyUsedControlList.get(key));
        }
        return list;
    }

    /**
     * 获取代码面板列表
     *
     * @return
     */
    public abstract ArrayList<BaseCodePane> getCodePaneList();

    /**
     * 清空各组件使用数量
     */
    private void clearUseComponentNum() {
        useComponentNum.clear();
        GeneralOperatingModel.generateUseComponentNumData(useComponentNum);
    }

    /**
     * 清空
     */
    public void clear() {
        clearUseComponentNum();
        infrequentlyUsedControlList.clear();
        controlComponentCorrespondingInformationList.clear();
    }


}
