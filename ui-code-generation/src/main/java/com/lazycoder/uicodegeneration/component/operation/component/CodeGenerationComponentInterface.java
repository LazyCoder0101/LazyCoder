package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFormatContainer;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.MainSetContainer;
import com.lazycoder.uicodegeneration.component.operation.container.ModuleSetOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractVariableHolder;
import java.util.ArrayList;

public interface CodeGenerationComponentInterface {

    /**
     * 更新对应代码值
     */
    public void updateValue();


    /**
     * 收起该组件
     */
    public void collapseThis();

    /**
     * 删除该组件
     */
    public void delThis();

    /**
     * 获取组件信息
     *
     * @return
     */
    public BaseLableElement getControlElement();

    /**
     * 获取路径信息
     *
     * @return
     */
    public PathFind getPathFind();

    /**
     * 获取代码序号
     *
     * @return
     */
    public int getCodeSerialNumber();

    /**
     * 获取操作组件放置面板
     *
     * @return
     */
    public AbstractOperatingPane getOperatingComponentPlacePane();

    /**
     * 该组件对应的功能容器
     *
     * @return
     */
    public OpratingContainerInterface getThisOpratingContainer();

    /**
     * 获取组件宽度
     *
     * @return
     */
    public int getComponentWidth();

    /**
     * 设置默认变量名
     * （对于有属性，且属性设置了只能使用一次或者自动生成一次只能用一次的，直接以originalValue查找有没有重复的名字，没有就递增，其他的，即有可以用多次的，以originalValue + 1查找有没有重复的名字，没有就递增）
     *
     * @param codeGenerationComponent 控制组件
     * @param originalValue           原来设置的默认值
     * @return
     */
    public static String getDeafaultVariableName(CodeGenerationComponentInterface codeGenerationComponent, String originalValue) {
        String finalValue = originalValue;
        ArrayList<AbstractVariableHolder> allVariableHolderList = CodeGenerationFrameHolder.getAllVariableHolderList();//获取当前所有的变量
        if (PathFind.COMMAND_TYPE == codeGenerationComponent.getPathFind().getMetaType()) {//该组件放在命令功能里面
            if (codeGenerationComponent.getThisOpratingContainer() instanceof ModuleSetOpratingContainer) {
                //目前模块的设置功能带有属性，只能使用一次 以及 自动生成且只能用一次，这两种所取的变量名不重复的话，后面不用带数字，原因显示就好
                ModuleSetOpratingContainer moduleSetOpratingContainer = (ModuleSetOpratingContainer) codeGenerationComponent.getThisOpratingContainer();
                if (moduleSetOpratingContainer.getMetaModel() != null) {
                    int setProperty = moduleSetOpratingContainer.getMetaModel().getOperatingModel().getSetProperty();
                    if (setProperty == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() ||
                            setProperty == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue()||
                            setProperty == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 0, allVariableHolderList);
                    } else {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                    }
                } else {
                    finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                }
            } else if (codeGenerationComponent.getThisOpratingContainer() instanceof MainSetContainer) {
                //目前必填模板的设置功能带有属性，只能使用一次 以及 自动生成且只能用一次，这两种所取的变量名不重复的话，后面不用带数字，原因显示就好
                MainSetContainer mainSetContainer = (MainSetContainer) codeGenerationComponent.getThisOpratingContainer();
                if (mainSetContainer.getMetaModel() != null) {
                    int setProperty = mainSetContainer.getMetaModel().getOperatingModel().getSetProperty();
                    if (setProperty == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() ||
                            setProperty == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue()||
                            setProperty == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 0, allVariableHolderList);
                    } else {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                    }
                } else {
                    finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                }
            } else if (codeGenerationComponent.getThisOpratingContainer() instanceof AdditionalSetContainer) {
                //目前可选模板的设置功能带有属性，只能使用一次 以及 自动生成且只能用一次，这两种所取的变量名不重复的话，后面不用带数字，原因显示就好
                AdditionalSetContainer additionalSetContainer = (AdditionalSetContainer) codeGenerationComponent.getThisOpratingContainer();
                if (additionalSetContainer.getMetaModel() != null) {
                    int setProperty = additionalSetContainer.getMetaModel().getOperatingModel().getSetProperty();
                    if (setProperty == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() ||
                            setProperty == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue()||
                            setProperty == FunctionUseProperty.autoGenerateOnceAndCanNotDel.getSysDictionaryValue()) {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 0, allVariableHolderList);
                    } else {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                    }
                } else {
                    finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                }
            } else {
                finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
            }
        } else if (PathFind.FORMAT_TYPE == codeGenerationComponent.getPathFind().getMetaType()) {
            if (codeGenerationComponent.getThisOpratingContainer() instanceof AdditionalFormatContainer) {
                //目前可选模板的格式带有属性，只能使用一次 以及 自动生成且只能用一次，这两种所取的变量名不重复的话，后面不用带数字，原因显示就好
                AdditionalFormatContainer additionalFormatContainer = (AdditionalFormatContainer) codeGenerationComponent.getThisOpratingContainer();
                if (additionalFormatContainer.getFormatControlPane() instanceof AdditionalFormatControlPane) {
                    AdditionalFormatControlPane additionalFormatControlPane = (AdditionalFormatControlPane) additionalFormatContainer.getFormatControlPane();
                    AdditionalOperating operating = additionalFormatControlPane.getOperating();
                    if (operating != null) {
                        if (operating.getSetProperty() == FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce.getSysDictionaryValue() ||
                                operating.getSetProperty() == FunctionUseProperty.onlyBeUsedOnce.getSysDictionaryValue()) {
                            finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 0, allVariableHolderList);
                        } else {
                            finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                        }
                    } else {
                        finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 1, allVariableHolderList);
                    }
                }
            } else {
                finalValue = CodeGenerationFrameHolder.getAvailableVariableName(originalValue, 0, allVariableHolderList);
            }
        }
        return finalValue;
    }

}
