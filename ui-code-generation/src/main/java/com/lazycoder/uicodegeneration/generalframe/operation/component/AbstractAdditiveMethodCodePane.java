package com.lazycoder.uicodegeneration.generalframe.operation.component;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.featureSelectionModel.AdditionalFunctionFeatureSelectionModel;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.meta.AdditionalFunctionMetaModel;
import com.lazycoder.service.vo.meta.FunctionMetaModel;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.container.AdditionalFunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.FunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.AdditionalFunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.FunctionOperatingContainerParam;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.CurrentAddOperaitonEffect;
import java.awt.Color;
import java.awt.Graphics;
import lombok.Getter;

/**
 * 可添加方法的代码面板 给业务逻辑面板和功能拓展组件的对应代码面板继承，左边模块列表的方法就是添加在这面板上
 */

public abstract class AbstractAdditiveMethodCodePane extends AbstractCodeControlPane {

    /**
     *
     */
    private static final long serialVersionUID = -3372848583508684480L;

    /**
     * 是否为当前选中
     */
    @Getter
    private boolean currentSelected = false;

    public AbstractAdditiveMethodCodePane(AbstractFormatControlPane formatControlPane) {
        super(formatControlPane, 10);
//		setUI(new AdditiveMethodCodePaneUI());
        // TODO Auto-generated constructor stub
    }

    /**
     * 添加方法
     *
     * @param featureSelectionModel
     */
    public FunctionOpratingContainer addFunction(FunctionFeatureSelectionModel featureSelectionModel, boolean checkOrNot) {
        FunctionOpratingContainer functionOpratingContainer = null;

        FunctionOperatingContainerParam functionOperatingContainerParam = new FunctionOperatingContainerParam();
        setThisParam(functionOperatingContainerParam);
        functionOperatingContainerParam.setFeatureSelectionModel(featureSelectionModel);
        functionOperatingContainerParam.setCodeControlPane(this);
        functionOperatingContainerParam.setFormatControlPane(this.getFormatControlPane());
        ModuleRelatedParam moduleRelatedParam = getFormatControlPane().getModuleRelatedParam(featureSelectionModel.getModuleId());
        if (moduleRelatedParam != null) {
            functionOperatingContainerParam.setModule(moduleRelatedParam.getModule());
            functionOperatingContainerParam.setModuleInfo(moduleRelatedParam.getModuleInfo());
        }

        FunctionMetaModel metaModel = SysService.FUNCTION_SERVICE
                .getFunctionMetaModel(functionOperatingContainerParam.getFeatureSelectionModel());
        if (metaModel != null) {

            boolean flag = true;
            if (checkOrNot) {
                flag = FunctionOpratingContainer.checkWhetherItMatches(functionOperatingContainerParam, metaModel);
            }

            if (flag) {
                if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                    if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                        autoCollapse(null);//先把这个面板之前添加的方法都折叠起来
                    } else {
                        collapseThis();
                    }
                } else {
                    collapseThis();
                }

                functionOpratingContainer = new FunctionOpratingContainer(
                        functionOperatingContainerParam, metaModel, true);
                addContainer(functionOpratingContainer);

                scrollToBottom();
            }
        }
        return functionOpratingContainer;
    }

    /**
     * 可选模板的添加方法
     *
     * @param featureSelectionModel
     */
    public AdditionalFunctionOpratingContainer addAdditionalFunction(AdditionalFunctionFeatureSelectionModel featureSelectionModel, boolean checkOrNot) {
        AdditionalFunctionOpratingContainer additionalFunctionOpratingContainer = null;

        AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam = new AdditionalFunctionOperatingContainerParam();
        additionalFunctionOperatingContainerParam.setFeatureSelectionModel(featureSelectionModel);
        additionalFunctionOperatingContainerParam.setCodeControlPane(this);
        additionalFunctionOperatingContainerParam.setFormatControlPane(this.getFormatControlPane());
        setThisParam(additionalFunctionOperatingContainerParam);

        AdditionalFunctionMetaModel metaModel = SysService.ADDITIONAL_FUNCTION_SERVICE
                .getAdditionalMetaModel(additionalFunctionOperatingContainerParam.getFeatureSelectionModel());
        if (metaModel != null) {

            boolean flag = true;
            if (checkOrNot) {//查看这功能是否可以添加在这里
                flag = AdditionalFunctionOpratingContainer.checkWhetherItMatches(additionalFunctionOperatingContainerParam, metaModel);
            }

            if (flag) {
                if (CodeGenerationFrameHolder.autoCollapseCheckBox != null) {
                    if (CodeGenerationFrameHolder.autoCollapseCheckBox.isSelected() == true) {
                        autoCollapse(null);//先把这个面板之前添加的方法都折叠起来
                    } else {
                        collapseThis();
                    }
                } else {
                    collapseThis();
                }

                additionalFunctionOpratingContainer = new AdditionalFunctionOpratingContainer(
                        additionalFunctionOperatingContainerParam, metaModel, true);
                addContainer(additionalFunctionOpratingContainer);

                scrollToBottom();
            }
        }
        return additionalFunctionOpratingContainer;
    }

    /**
     * 继承该类都要重写此方法，添加方法功能容器的时候要设置什么参数
     *
     * @param functionOperatingContainerParam
     */
    protected abstract void setThisParam(FunctionOperatingContainerParam functionOperatingContainerParam);

    /**
     * 继承该类都要重写此方法，添加方法功能容器的时候要设置什么参数
     *
     * @param additionalFunctionOperatingContainerParam
     */
    protected abstract void setThisParam(
            AdditionalFunctionOperatingContainerParam additionalFunctionOperatingContainerParam);

    /**
     * 是否可以添加外部方法
     *
     * @return
     */
    public abstract boolean canAddExternalMethods();

    public void setCurrentSelected(boolean currentSelected) {
        this.currentSelected = currentSelected;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        super.paint(g);
        Color theColor = CodeGenerationFrameHolder.NOMAL_ADDITIVE_COLOR;
        if (currentSelected == true) {
            theColor = CodeGenerationFrameHolder.CURRENT_ADDITIVE_COLOR;
            CurrentAddOperaitonEffect.createCurrentAddPaneEffect(g, getParentScrollPane().getViewport());

        }
        if (getParentScrollPane() != null) {
            getParentScrollPane().getViewport().setBackground(theColor);
//			if (currentSelected == true) {
//				//CrystalEffect.createCrystalEffect(g, theColor, getParentScrollPane());
//                //getParentScrollPane().setBackground(theColor);
//			}
        }

        super.paintChildren(g);
//		if (getParent() != null) {
//			getParent().repaint();
//		}
    }

    /**
     * 获取本模板对应功能添加的相关属性（没有设置标签id）
     * @return
     */
    public abstract CommandAddRelatedAttribute getCorrespondingCommandAddRelatedAttribute();

}