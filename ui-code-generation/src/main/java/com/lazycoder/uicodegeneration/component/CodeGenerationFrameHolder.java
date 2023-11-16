package com.lazycoder.uicodegeneration.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.common.EncodingTypeEnum;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeShowTabbedPanel;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.AbstractFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.AdditionalFormatControlPane;
import com.lazycoder.uicodegeneration.generalframe.operation.CodeControlTabbedPane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.AbstractAdditiveMethodCodePane;
import com.lazycoder.uicodegeneration.generalframe.operation.component.BusinessLogicCodeControlPane;
import com.lazycoder.uicodegeneration.generalframe.palette.FeatureSelectedPane;
import com.lazycoder.uicodegeneration.generalframe.palette.additional.AdditionalOperationPane;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.AbstractVariableHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * 代码生成界面共用的变量参数
 *
 * @author admin
 */
public class CodeGenerationFrameHolder extends GeneralHolder {

    //public static final Color currentAdditiveColor = new Color(255, 151, 4);
    public static final Color CURRENT_ADDITIVE_COLOR = new Color(255, 151, 4);

    public static final Color NOMAL_ADDITIVE_COLOR = UIManager.getColor("Panel.background");

    public static EncodingTypeEnum currentEncodingType = EncodingTypeEnum.UTF8;

    public static String projectParentPath = null;

    public static String projectName = null;

    public static CodeGenerationFrame codeGenerationFrame = null;

    /**
     * 存放生成代码面板的组件
     */
    public static CodeShowTabbedPanel codeShowPanel = null;

    /**
     * 代码控制面板
     */
    public static CodeControlTabbedPane codeControlTabbedPane = null;

    /**
     * 方法选择面板
     */
    public static FeatureSelectedPane featureSelectedPane = null;

    /**
     * 可选模板业务方法的操作选择面板
     */
    public static AdditionalOperationPane additionalOperationPane = null;

    /**
     * 当前的添加方法的代码面板
     */
    public static AbstractAdditiveMethodCodePane currentAdditiveMethodCodePane = null;

    /**
     * 当前格式控制面板
     */
    public static AbstractFormatControlPane currentFormatControlPane = null;

    /**
     * 自动折叠、格式化代码
     */
    public static JCheckBox autoCollapseCheckBox = null, formattedSourceCodeCheckBox = null;

    /**
     * 显示路径
     */
    public static JTextField pathTextField = null;


    /**
     * 重新设置要添加方法的面板
     *
     * @param additiveMethodCodePane
     */
    public static void setCurrentMethodAddPaneAs(AbstractAdditiveMethodCodePane additiveMethodCodePane) {
        if (additiveMethodCodePane != null) {
            //CodeGenerationFrameHolder.currentAdditiveMethodCodePane;
            if (CodeGenerationFrameHolder.currentAdditiveMethodCodePane == null) {//当前没有添加功能的面板
                //CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                if (additiveMethodCodePane instanceof BusinessLogicCodeControlPane &&
                        additiveMethodCodePane.getFormatControlPane() != null) {//是业务逻辑面板
                    if (additiveMethodCodePane.getFormatControlPane().isHaveBusinessLogicMarkFlag()) {//这个模板有添加业务逻辑标记
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                        additiveMethodCodePane.setCurrentSelected(true);

                    } else {//这个模板没有添加业务逻辑标记
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = null;
                    }
                } else {//是功能拓展面板
                    CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                    additiveMethodCodePane.setCurrentSelected(true);
                }
            } else {//当前有添加功能的面板
                if (CodeGenerationFrameHolder.currentFormatControlPane.getBusinessLogicCodePane() == additiveMethodCodePane) {//是当前的业务逻辑面板
                    if (CodeGenerationFrameHolder.currentFormatControlPane.isHaveBusinessLogicMarkFlag()) {//这个模板有添加业务逻辑标记

                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                        additiveMethodCodePane.setCurrentSelected(true);
                    } else {//这个模板没有添加业务逻辑标记
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = null;
                    }
                } else {
                    if (additiveMethodCodePane instanceof BusinessLogicCodeControlPane &&
                            additiveMethodCodePane.getFormatControlPane() != null) {//是业务逻辑面板

                        if (additiveMethodCodePane.getFormatControlPane().isHaveBusinessLogicMarkFlag()) {//这个模板有添加业务逻辑标记
                            CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                            CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                            additiveMethodCodePane.setCurrentSelected(true);
                        } else {//这个模板没有添加业务逻辑标记
                            CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                            CodeGenerationFrameHolder.currentAdditiveMethodCodePane = null;
                        }
                    } else {//是功能拓展面板
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane.setCurrentSelected(false);
                        CodeGenerationFrameHolder.currentAdditiveMethodCodePane = additiveMethodCodePane;
                        additiveMethodCodePane.setCurrentSelected(true);
                    }
                }
            }
        }

//		additiveMethodCodePane.updateUI();
//		additiveMethodCodePane.repaint();
//		SysUtil.updateFrameUI(additiveMethodCodePane);
    }


    /**
     * 获取所有变量
     *
     * @return
     */
    public static ArrayList<AbstractVariableHolder> getAllVariableHolderList() {
        ArrayList<AbstractVariableHolder> allVariableHolderList = new ArrayList<AbstractVariableHolder>();

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatVariableHolder() != null) {

            allVariableHolderList.add(
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatVariableHolder());
        }

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomVariableHolder() != null) {

            allVariableHolderList
                    .add(CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomVariableHolder());
        }

        if (CodeGenerationModuleFormatVariableHolder.moduleFormatVariableList != null) {
            allVariableHolderList.addAll(CodeGenerationModuleFormatVariableHolder.moduleFormatVariableList);
        }

        if (CodeGenerationModuleCustomVariableHolder.moduleCustomVariableList != null) {
            allVariableHolderList.addAll(CodeGenerationModuleCustomVariableHolder.moduleCustomVariableList);
        }

        if (CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList != null) {
            allVariableHolderList.addAll(CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList);
        }

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            AdditionalFormatControlPane formatControlPaneTemp;
            for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
                if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {

                    formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                            .getComponent(i);
                    allVariableHolderList.add(formatControlPaneTemp.getThisCustomVariableHolder());
                }
            }
            ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
            if (opratingContainerList != null) {
                for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                    allVariableHolderList.add(opratingContainer.getThisCustomVariableHolder());
                }
            }
        }
        return allVariableHolderList;
    }

    /**
     * 获取一个可用的变量名（没有重复的）
     *
     * @param originalName 要起的变量名
     * @param i            如果i设置大于0，如果没有相同的变量名，就取值为originalName+i,有的话，依次递增
     * @return
     */

    /**
     * @param originalName          要起的变量名
     * @param i                     如果i设置大于0，如果没有相同的变量名，就取值为originalName+i,有的话，依次递增  ，如果i设置为0，比对发现没有重复后，会返回传入的名称，有重复则递增
     * @param allVariableHolderList 传入所有的变量，用于进行比对，看看有无重复
     * @return
     */
    public static String getAvailableVariableName(String originalName, int i, ArrayList<AbstractVariableHolder> allVariableHolderList) {
        String finalName = "";
        if (originalName != null && "".equals(originalName) == false) {
            finalName = originalName;
            if (i > 0) {
                finalName = originalName + i;
            }
//            ArrayList<GeneralVariableHolder> vlist = getAllVariableHolderList();
            boolean flag = true;
            for (AbstractVariableHolder variableHolderTemp : allVariableHolderList) {
                for (AbstractVariable variableTemp : variableHolderTemp.getVariableList()) {
                    if (finalName.equals(variableTemp.getVariableValue())) {
                        flag = false;
                        break;
                    }
                }
                if (flag == false) {
                    break;
                }
            }
            if (flag == false) {
                finalName = getAvailableVariableName(originalName, i + 1, allVariableHolderList);
            }
        }
        return finalName;
    }


    /**
     * 获取某个格式控制面板能用到的所有变量
     *
     * @param formatControlPane
     * @return
     */
    public static ArrayList<AbstractVariableHolder> getAllVariableHolderList(AbstractFormatControlPane formatControlPane) {
        ArrayList<AbstractVariableHolder> allVariableHolderList = new ArrayList<AbstractVariableHolder>();
        if (formatControlPane != null) {
            ArrayList<ModuleRelatedParam> formatControlPaneUseModuleRelatedParamList = formatControlPane.getUseModuleRelatedParamList();

            if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatVariableHolder() != null) {
                allVariableHolderList.add(
                        CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatVariableHolder());
            }

            if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomVariableHolder() != null) {
                allVariableHolderList
                        .add(CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomVariableHolder());
            }

            allVariableHolderList.addAll(CodeGenerationModuleFormatVariableHolder.getModuleFormatVariableList(formatControlPaneUseModuleRelatedParamList));

            allVariableHolderList.addAll(CodeGenerationModuleCustomVariableHolder.getModuleCustomVariableList(formatControlPaneUseModuleRelatedParamList));

            allVariableHolderList.addAll(CodeGenerationAdditionalFormatVariableHolder.additionalFormatVariableList);

            if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
                AdditionalFormatControlPane formatControlPaneTemp;
                for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
                    if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {

                        formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                                .getComponent(i);
                        allVariableHolderList.add(formatControlPaneTemp.getThisCustomVariableHolder());
                    }
                }

                ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
                if (opratingContainerList != null) {
                    for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                        allVariableHolderList.add(opratingContainer.getThisCustomVariableHolder());
                    }
                }
            }
        }
        return allVariableHolderList;
    }

    /**
     * 获取所有方法名
     *
     * @return
     */
    public static ArrayList<AbstractFunctionNameHolder> getAllFunctionNameHolderList() {
        ArrayList<AbstractFunctionNameHolder> allFunctionNameHolderList = new ArrayList<AbstractFunctionNameHolder>();

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatFunctionNameHolder() != null) {
            allFunctionNameHolderList.add(
                    CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatFunctionNameHolder());
        }

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomFunctionNameHolder() != null) {
            allFunctionNameHolderList.add(CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane()
                    .getThisCustomFunctionNameHolder());
        }

        allFunctionNameHolderList.addAll(CodeGenerationModuleFormatFunctionNameHolder.moduleFormatFunctionNameList);

        allFunctionNameHolderList.addAll(CodeGenerationModuleCustomFunctionNameHolder.moduleCustomFunctionNameList);

        allFunctionNameHolderList
                .addAll(CodeGenerationAdditionalFormatFunctionNameHolder.additionalFormatFunctionNameHolderList);

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null) {
            AdditionalFormatControlPane formatControlPaneTemp;
            for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
                if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {
                    formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                            .getComponent(i);
                    allFunctionNameHolderList.add(formatControlPaneTemp.getThisCustomFunctionNameHolder());
                }
            }
            ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
            if (opratingContainerList != null) {
                for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                    allFunctionNameHolderList.add(opratingContainer.getThisCustomMethodNameHolder());
                }
            }
        }

        return allFunctionNameHolderList;
    }

    /**
     * 获取一个可用的方法名（没有重复的）
     *
     * @param originalName 要起的方法名
     * @param i            如果i设置大于0，如果没有相同的方法名，就取值为originalName+i,有的话，依次递增
     * @return
     */
    public static String getAvailableFunctionName(String originalName, int i, ArrayList<AbstractFunctionNameHolder> allFunctionNameHolderList) {
        String finalName = "";
        if (originalName != null && "".equals(originalName) == false) {
            finalName = originalName;
            if (i > 0) {
                finalName = originalName + i;
            }
//            ArrayList<GeneralFunctionNameHolder> flist = getAllFunctionNameHolderList();
            boolean flag = true;
            for (AbstractFunctionNameHolder functionNameHolderTemp : allFunctionNameHolderList) {
                for (AbstractMethodName methodNameTemp : functionNameHolderTemp.getFunctionNameList()) {
                    if (finalName.equals(methodNameTemp.getFunctionNameValue())) {
                        flag = false;
                        break;
                    }
                }
                if (flag == false) {
                    break;
                }
            }
            if (flag == false) {
                finalName = getAvailableFunctionName(originalName, i + 1, allFunctionNameHolderList);
            }
        }
        return finalName;
    }

    /**
     * 获取某个格式控制面板能用到的所有方法名
     *
     * @param formatControlPane
     * @return
     */
    public static ArrayList<AbstractFunctionNameHolder> getAllFunctionNameHolderList(AbstractFormatControlPane formatControlPane) {
        ArrayList<ModuleRelatedParam> formatControlPaneUseModuleList = formatControlPane.getUseModuleRelatedParamList();//获取当前这个控制面板添加的所有模块

        AbstractFunctionNameHolder functionNameHolderTemp;
        ArrayList<AbstractFunctionNameHolder> allFunctionNameHolderList = new ArrayList<AbstractFunctionNameHolder>();

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatFunctionNameHolder() != null) {
            functionNameHolderTemp = CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getFormatFunctionNameHolder();
            allFunctionNameHolderList.add(functionNameHolderTemp);//添加必填模板的格式方法名
        }

        if (CodeGenerationFrameHolder.codeControlTabbedPane != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane() != null &&
                CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane().getThisCustomFunctionNameHolder() != null) {
            functionNameHolderTemp = CodeGenerationFrameHolder.codeControlTabbedPane.getMainCodeControlPane()
                    .getThisCustomFunctionNameHolder();
            allFunctionNameHolderList.add(functionNameHolderTemp);//添加必填模板的自定义方法名
        }

        //添加当前所有模块的格式方法名
        allFunctionNameHolderList.addAll(CodeGenerationModuleFormatFunctionNameHolder.getModuleFormatFunctionNameList(formatControlPaneUseModuleList));
        //添加当前所有模块的自定义方法名
        allFunctionNameHolderList.addAll(CodeGenerationModuleCustomFunctionNameHolder.getModuleCustomFunctionNameList(formatControlPaneUseModuleList));
        //添加所有其他控制面板的格式方法
        allFunctionNameHolderList
                .addAll(CodeGenerationAdditionalFormatFunctionNameHolder.additionalFormatFunctionNameHolderList);

        //添加所有其他控制面板的自定义方法
        AdditionalFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {
                formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                        .getComponent(i);
                allFunctionNameHolderList.add(formatControlPaneTemp.getThisCustomFunctionNameHolder());
            }
        }

        ArrayList<OpratingContainerInterface> opratingContainerList = CodeGenerationFrameHolder.codeControlTabbedPane.getAllOpratingContainer();
        if (opratingContainerList != null) {
            for (OpratingContainerInterface opratingContainer : opratingContainerList) {
                allFunctionNameHolderList.add(opratingContainer.getThisCustomMethodNameHolder());
            }
        }
        return allFunctionNameHolderList;
    }

    /**
     * 获取指定类型的格式面板里的自定义变量
     *
     * @param additionalSerialNumber
     * @return
     */
    public static ArrayList<CustomVariableHolder> getAdditionalCustomVariableHolderList(int additionalSerialNumber) {
        ArrayList<CustomVariableHolder> allAdditionalVariableHolderList = new ArrayList<CustomVariableHolder>();
        AdditionalFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {

                formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                        .getComponent(i);
                if (formatControlPaneTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                    allAdditionalVariableHolderList.add(formatControlPaneTemp.getThisCustomVariableHolder());
                }
            }
        }
        return allAdditionalVariableHolderList;
    }

    /**
     * 获取指定类型的格式面板里的格式变量
     *
     * @param additionalSerialNumber
     * @return
     */
    public static ArrayList<CustomFunctionNameHolder> getAdditionalCustomMethodNameHolderList(int additionalSerialNumber) {
        ArrayList<CustomFunctionNameHolder> allAdditionalCustomMethodNameHolderList = new ArrayList<CustomFunctionNameHolder>();
        AdditionalFormatControlPane formatControlPaneTemp;
        for (int i = 0; i < CodeGenerationFrameHolder.codeControlTabbedPane.getComponentCount(); i++) {
            if (CodeGenerationFrameHolder.codeControlTabbedPane.getComponent(i) instanceof AdditionalFormatControlPane) {

                formatControlPaneTemp = (AdditionalFormatControlPane) CodeGenerationFrameHolder.codeControlTabbedPane
                        .getComponent(i);
                if (formatControlPaneTemp.getAdditionalSerialNumber() == additionalSerialNumber) {
                    allAdditionalCustomMethodNameHolderList
                            .add(formatControlPaneTemp.getThisCustomFunctionNameHolder());
                }
            }
        }
        return allAdditionalCustomMethodNameHolderList;
    }

    public static void generateCode() {
        CodeGenerationFrameHolder.codeGenerationFrame.saveProjectFile();
        CodeGenerationFrameHolder.codeShowPanel.saveProjectFile();
        CodeGenerationFrameHolder.codeControlTabbedPane.saveProjectFile();
        CodeGenerationFrameHolder.codeShowPanel.generateCode();
    }


    public static HTMLText getNoteToolTip(String noteListParam) {
        HTMLText htmlText = null;
        HtmlPar par;
        if (noteListParam != null && "".equals(noteListParam) == false) {
            ArrayList<String> list = JSON.parseObject(noteListParam, new TypeReference<ArrayList<String>>() {
            });
            if (list.size() > 0) {
                htmlText = new HTMLText();
                for (String temp : list) {
                    par = new HtmlPar();
                    par.addText(temp, true);
                    htmlText.addPar(par);
                }
            }
        }
        return htmlText;
    }


}
