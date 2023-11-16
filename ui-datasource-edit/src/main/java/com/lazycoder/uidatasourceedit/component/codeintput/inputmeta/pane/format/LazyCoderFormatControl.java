package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format;

import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.format.CorrespondingFormatCodePaneInterface;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uidatasourceedit.AbstractFormatCodeInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.utils.MapUtil;

import com.lazycoder.utils.UUIDUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 控制面板和代码的控制
 *
 * @author Administrator
 */
public class LazyCoderFormatControl extends GeneralControl {

    /**
     * 构建一个新的格式控制面板
     *
     * @param formatControlInputPane
     * @param formatModel
     */
    public static void buildingNewOperatingPane(AbstractFormatControlInputPane formatControlInputPane,
                                                FormatModel formatModel) {
        formatControlInputPane.setModel(formatModel);
        formatModel.setControlPane(formatControlInputPane);
    }

    /**
     * 构建原来的控制面板
     *
     * @param formatControlInputPane
     * @param formatModel
     * @param operatingModel
     */
    public static void buildingOriginalOperatingPane(AbstractFormatControlInputPane formatControlInputPane,
                                                     FormatModel formatModel, GenerlFormatOperatingModel operatingModel) {
        formatControlInputPane.setModel(formatModel);
        formatModel.setControlPane(formatControlInputPane);

        formatModel.reductionContent(operatingModel);

        ArrayList<BaseElementInterface> codeElementList = DeserializeElementMethods.getControlPaneElmentList(operatingModel.getDefaultControlStatementFormat());
        formatControlInputPane.reductionContent(codeElementList);

    }

    /**
     * 构建新的默认 代码面板
     *
     * @param formatCodeInputPane
     * @param formatModel
     * @param fileName
     */
    public static void buildingNewDefaultCodePane(AbstractFormatCodeInputPane formatCodeInputPane,
                                                  FormatModel formatModel, String fileName) {
        formatCodeInputPane.setModel(formatModel);
        formatCodeInputPane.setCodeFileId(UUIDUtil.getUUID());
        formatModel.setDefaultFormatCodePane(formatCodeInputPane);
        LazyCoderFormatControl.updateCodePaneMenu(formatModel);
    }

    /**
     * 构建原有的默认代码面板
     *
     * @param formatCodeInputPane
     * @param formatModel
     * @param fileName
     * @param fileFormat
     */
    public static void buildingOriginalDefaultCodePane(AbstractFormatCodeInputPane formatCodeInputPane,
                                                       FormatModel formatModel, String fileName, GeneralFileFormat fileFormat) {

        formatModel.setDefaultFormatCodePane(formatCodeInputPane);
        formatCodeInputPane.setModel(formatModel);
        formatCodeInputPane.setCodeFileId(fileFormat.getId());
        ArrayList<BaseElementInterface> codeElementList = DeserializeElementMethods.getCodeFilePaneElementList(fileFormat.getFormatContent());
        formatCodeInputPane.reductionContent(codeElementList);
//		formatCodeInputPane.reductionContent(fileFormat.getFormatContent());
        LazyCoderFormatControl.updateCodePaneMenu(formatModel);
    }

    /**
     * 构建新的代码面板
     *
     * @param formatCodeInputPane
     * @param formatModel
     * @param fileName
     */
    public static void buildingNewSubCodePane(AbstractFormatCodeInputPane formatCodeInputPane, FormatModel formatModel,
                                              String fileName) {
        formatCodeInputPane.setModel(formatModel);
        formatCodeInputPane.setCodeFileId(UUIDUtil.getUUID());
        formatModel.getSubFormatCodePaneList().put(fileName, formatCodeInputPane);
        LazyCoderFormatControl.updateCodePaneMenu(formatModel);
    }

    /**
     * 构建原有的附带代码面板
     *
     * @param formatCodeInputPane
     * @param formatModel
     * @param fileName
     * @param fileFormat
     */
    public static void buildingOriginalSubCodePane(AbstractFormatCodeInputPane formatCodeInputPane,
                                                   FormatModel formatModel, String fileName, GeneralFileFormat fileFormat) {

        formatModel.getSubFormatCodePaneList().put(fileName, formatCodeInputPane);
        formatCodeInputPane.setModel(formatModel);
        formatCodeInputPane.setCodeFileId(fileFormat.getId());
        ArrayList<BaseElementInterface> codeElementList = DeserializeElementMethods.getCodeFilePaneElementList(fileFormat.getFormatContent());
        formatCodeInputPane.reductionContent(codeElementList);
//		formatCodeInputPane.reductionContent(fileFormat.getFormatContent());
        LazyCoderFormatControl.updateCodePaneMenu(formatModel);
    }

    /**
     * 清空所有
     *
     * @param formatModel
     */
    public static void clear(FormatModel formatModel) {
        formatModel.clear();
        if (formatModel.getControlPane() != null) {
            formatModel.getControlPane().clear();
        }
        if (formatModel.getDefaultFormatCodePane() != null) {
            formatModel.getDefaultFormatCodePane().clear();
        }
        if (formatModel.getSubFormatCodePaneList() != null) {
            for (String key : formatModel.getSubFormatCodePaneList().keySet()) {
                formatModel.getSubFormatCodePaneList().get(key).clear();
            }
        }
    }

    /**
     * 移除所有子代码列表
     *
     * @param formatModel
     */
    public static void removeAllSubCodePaneList(FormatModel formatModel) {
        if (formatModel.getSubFormatCodePaneList() != null) {
            formatModel.getSubFormatCodePaneList().clear();
        }
    }

    public static void setOperating(GenerlFormatOperatingModel operatingModel, FormatModel model) {
        operatingModel.setControlComponentCorrespondingInformation(
                AbstractEditContainerModel.getControlComponentCorrespondingInformationListJsonStr(model));
        operatingModel.setNumberOfComponents(GeneralOperatingModel.getUseComponentNumParam(model.getUseComponentNum()));
    }

    /**
     * 删除代码面板（最后一个）
     *
     * @param model
     */
    public static void delLastFunctionCode(AbstractEditContainerModel model) {
        Entry<String, CorrespondingFormatCodePaneInterface> last_element = MapUtil
                .getLastElement(((FormatModel) model).getSubFormatCodePaneList());
        ((FormatModel) model).getSubFormatCodePaneList().remove(last_element.getKey());

    }

    /**
     * 通知代码面板更新菜单
     */
    public static void updateCodePaneMenu(AbstractEditContainerModel model) {
        if (((FormatModel) model).getDefaultFormatCodePane() != null) {
            ((FormatModel) model).getDefaultFormatCodePane().updateMenu();
        }
        if (((FormatModel) model).getSubFormatCodePaneList() != null) {
            Map<String, CorrespondingFormatCodePaneInterface> list = ((FormatModel) model).getSubFormatCodePaneList();
            for (String key : list.keySet()) {
                list.get(key).updateMenu();
            }
        }

    }

    /**
     * 删除对应控制模型以及删除
     *
     * @param model
     * @param labelType
     * @param componentName
     */
    public static void delControlLabel(AbstractEditContainerModel model, String labelType, String componentName) {
        delCorrespondingLabelFromModel(model, labelType, componentName);
        delCorrespondingLabelFromCodePane(model, labelType, componentName);
        updateCodePaneMenu(model);
    }

    /**
     * 在控制面板上添加标签组件 通知其他代码模板更新
     *
     * @param model
     * @param addLabelModel 进行操作如下 把对应控制组件的模型添加到总模型 通知总模型的其他的源码面板更新
     */
    public static void addControlLabelAndUpdateCodePaneMenu(AbstractEditContainerModel model, BaseLableElement addLabelModel) {
        model.getControlComponentCorrespondingInformationList().add(addLabelModel);
        updateCodePaneMenu(model);
    }

    /**
     * 从代码面板删除对应标签
     *
     * @param model
     * @param labelType
     * @param componentName
     */
    private static void delCorrespondingLabelFromCodePane(AbstractEditContainerModel model, String labelType,
                                                          String componentName) {
        if (((FormatModel) model).getDefaultFormatCodePane() != null) {
            ((FormatModel) model).getDefaultFormatCodePane().delLabel(labelType, componentName);
        }
        Map<String, CorrespondingFormatCodePaneInterface> formatList = ((FormatModel) model).getSubFormatCodePaneList();
        for (String key : formatList.keySet()) {
            formatList.get(key).delLabel(labelType, componentName);
        }
    }

    public static void delContentChoose(AbstractEditContainerModel model, String optionId, int useNumbered) {
        CorrespondingFormatCodePaneInterface formatCodePane = null;

        FormatModel formatModel = (FormatModel) model;
        delContentChooseLableInfomation(formatModel, optionId, useNumbered);
        if (((FormatModel) model).getDefaultFormatCodePane() != null) {
            formatCodePane = ((FormatModel) model).getDefaultFormatCodePane();
            formatCodePane.delContentChooseLable(optionId, useNumbered);
            formatCodePane.updateMenu();
        }
        LinkedHashMap<String, CorrespondingFormatCodePaneInterface> subFormatCodePaneList = formatModel
                .getSubFormatCodePaneList();
        for (String key : subFormatCodePaneList.keySet()) {
            formatCodePane = subFormatCodePaneList.get(key);
            formatCodePane.delContentChooseLable(optionId, useNumbered);
            formatCodePane.updateMenu();
        }
    }

    public static void delContentChoose(AbstractEditContainerModel model, String optionId) {
        CorrespondingFormatCodePaneInterface formatCodePane = null;

        FormatModel formatModel = (FormatModel) model;
        delContentChooseLableInfomation(formatModel, optionId);
        if (((FormatModel) model).getDefaultFormatCodePane() != null) {
            formatCodePane = ((FormatModel) model).getDefaultFormatCodePane();
            formatCodePane.delContentChooseLable(optionId);
            formatCodePane.updateMenu();
        }
        LinkedHashMap<String, CorrespondingFormatCodePaneInterface> subFormatCodePaneList = formatModel
                .getSubFormatCodePaneList();
        for (String key : subFormatCodePaneList.keySet()) {
            formatCodePane = subFormatCodePaneList.get(key);
            formatCodePane.delContentChooseLable(optionId);
            formatCodePane.updateMenu();
        }
    }

}
