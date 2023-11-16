package com.lazycoder.uicodegeneration.generalframe.palette;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.featureSelectionModel.FunctionFeatureSelectionModel;
import com.lazycoder.database.model.formodule.ModuleInfoStaticMethod;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.functionadd.FunctionAddCodeControlPane;
import com.lazycoder.uicodegeneration.component.operation.container.FunctionOpratingContainer;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.operation.component.BusinessLogicCodeControlPane;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import lombok.Getter;

public class FeatureSelectionLabel extends MyIconLabel {

    /**
     *
     */
    private static final long serialVersionUID = 8332052703366419294L;

    private final static ImageIcon ICON = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "CodeGeneration" + File.separator + "业务模块.png");

    @Getter
    private ModuleRelatedParam moduleRelatedParam;

    @Getter
    private ArrayList<String> moduleTypeList = new ArrayList<>();

    public FeatureSelectionLabel(ModuleRelatedParam moduleRelatedParam) {
        this.moduleRelatedParam = moduleRelatedParam;
        moduleTypeList = ModuleInfoStaticMethod.getFunctionTypeListParam(moduleRelatedParam.getModuleInfo());
        init(moduleRelatedParam.getModule().getModuleName(), ICON);
        ArrayList<Integer> useSetting = ModuleStaticMethod.getUseSettingValues(this.moduleRelatedParam.getModule());
        if (useSetting.contains(ModuleUseSetting.USER_SHIELDING)) {
            textLabel.setForeground(Color.gray);
        } else {
            textLabel.setForeground(Color.black);
        }
        setPreferredSize(new Dimension((int) (0.109 * SysUtil.SCREEN_SIZE.width), 40));
    }

    /**
     * 更新菜单
     */
    public void updateMenu(JPopupMenu menu, JMenuItem del) {
        menu.removeAll();

        if (moduleTypeList != null) {
            JMenu menuTemp;
            List<FunctionFeatureSelectionModel> functionList;
            FeatureMenuItem menuItem;
            FunctionOpratingContainer functionOpratingContainer;

            ArrayList<OpratingContainerInterface> opratingContainerList =
                    CodeGenerationFrameHolder.currentAdditiveMethodCodePane == null ?
                            new ArrayList<>() : CodeGenerationFrameHolder.currentAdditiveMethodCodePane.getAllOpratingContainerListInThisPane();

            for (String typeName : moduleTypeList) {
                menuTemp = new JMenu(typeName);
                menu.add(menuTemp);
                functionList = SysService.FUNCTION_SERVICE.getFeatureList(moduleRelatedParam.getModule().getModuleId(), typeName);
                if (functionList != null) {
                    for (FunctionFeatureSelectionModel featureSelectionModel : functionList) {
                        menuItem = new FeatureMenuItem(featureSelectionModel);
                        menuTemp.add(menuItem);

                        if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane != null &&
                                CodeGenerationFrameHolder.currentAdditiveMethodCodePane instanceof BusinessLogicCodeControlPane) {//业务逻辑面板
                            if (FunctionUseProperty.onlyAddedItOnceToBusinessSchtcheonOfATemplate.getSysDictionaryValue() == featureSelectionModel.getSetProperty()) {
                                for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                                    if (opratingContainer instanceof FunctionOpratingContainer) {
                                        functionOpratingContainer = (FunctionOpratingContainer) opratingContainer;
                                        if (featureSelectionModel.getOrdinal() == functionOpratingContainer.getOrdinalInUserDB()) {
                                            if (typeName.equals(functionOpratingContainer.getMetaModel().getOperatingModel().getTypeName())) {
                                                menuItem.setEnabled(false);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (FunctionUseProperty.onlyBeAddedToFunctionAddLabel.getSysDictionaryValue() == featureSelectionModel.getSetProperty()) {
                                //只能添加到方法组件的，菜单失能
                                menuItem.setEnabled(false);
                            }
                        } else if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane != null &&
                                CodeGenerationFrameHolder.currentAdditiveMethodCodePane instanceof FunctionAddCodeControlPane) {//当前添加方法的面板是功能拓展组件的面板

                            if (FunctionUseProperty.onlyAddedItOnceToBusinessSchtcheonOfATemplate.getSysDictionaryValue() == featureSelectionModel.getSetProperty()) {
                                //如果属性是只能添加到业务方法面板那两个，菜单失能
                                menuItem.setEnabled(false);
//                            }else if (featureSelectionModel.getSetProperty() == FunctionUseProperty.onlyBeAddedToFunctionAddLabel.getSysDictionaryValue()) {
                            } else if (FunctionUseProperty.onlyBeAddedToBusinessSchtcheonOfATemplate.getSysDictionaryValue() == featureSelectionModel.getSetProperty()) {
                                if (!CodeGenerationFrameHolder.currentAdditiveMethodCodePane.getPathFind().getMarkType().equals(MarkElementName.FUNCTION_MARK)) {
                                    menuItem.setEnabled(false);
                                }
                            }
                        }

                    }
                }
            }
        }
        menu.add(del);
    }

}

