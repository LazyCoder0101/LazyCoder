package com.lazycoder.uidatasourceedit;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.command.ContainerModel;
import com.lazycoder.service.vo.datasourceedit.format.FormatModel;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FileSelectorControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.FunctionAddControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.InfrequentlyUsedSettingControlLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.PictureControlLabel;
import com.lazycoder.uidatasourceedit.formatedit.FormatEditPane;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditPane;
import com.lazycoder.uidatasourceedit.modulemanagement.ModuleManagementPane;
import com.lazycoder.uiutils.utils.MyColoursBorder;
import com.lazycoder.utils.FileUtil;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class DataSourceEditHolder extends GeneralHolder {

    public static final Color RESPONSE_FALSE_COLOR = UIManager.getColor("Panel.background");

    public static final Color RESPONSE_TRUE_COLOR = new Color(253, 203, 127);

    public static final Border RESPONSE_TRUE_BORDER = new MyColoursBorder(3);

    public static Module currentModule = null;

    public static ModuleEditPane moduleEditPane = null;
    public static FormatEditPane formatEditPane = null;
    public static ModuleManagementPane moduleManagementPane = null;
    /**
     * 数据编辑窗口
     */
    public static DataSourceEditFrame dataSourceEditFrame = null;

    /**
     * 默认按钮组
     */
    public static ButtonGroup defaultButtonGroup = null;

    /**
     * 自动定位坐标
     */
    public static JCheckBox autoPositionCheckBox;



    /**
     * 删除多余的文件
     *
     * @param correspondingFolderList 用来比对的文件（有和它同名的就不能删）
     * @param folder                  要删除子文件的文件夹
     */
    public static void delRedundantFiles(ArrayList<File> correspondingFolderList, File folder) {
        File[] folderList = folder.listFiles();
        if (folderList != null) {
            for (File folderTemp1 : folderList) {
                boolean flag = false;
                for (File folderTemp2 : correspondingFolderList) {
                    if (folderTemp2.getAbsolutePath().equals(folderTemp1.getAbsolutePath())) {
                        flag = true;
                        correspondingFolderList.remove(folderTemp2);
                        break;
                    }
                }
                if (flag == false) {
                    FileUtil.delFolder(folderTemp1.getAbsolutePath());
                }
            }
        }
    }


    /**
     * 从modelList中获取里面所有文件选择组件对应的文件夹
     *
     * @param parentFolder
     * @param modelList
     * @return
     */
    public static ArrayList<File> getCorrespondingModelFileSelectFolderList(File parentFolder, ArrayList<AbstractEditContainerModel> modelList) {
        ArrayList<Class<?>> specifiedClassList = new ArrayList<>();
        specifiedClassList.add(FunctionAddControlLabel.class);
        specifiedClassList.add(InfrequentlyUsedSettingControlLabel.class);
        specifiedClassList.add(FileSelectorControlLabel.class);

        ArrayList<File> fileArrayList = new ArrayList<>(), fileList;
        ArrayList<BaseElementInterface> elementList;
        for (AbstractEditContainerModel model : modelList) {
            if (model instanceof ContainerModel) {
                ContainerModel containerModel = (ContainerModel) model;
                if (containerModel.getMainFunctionControl() != null) {
                    elementList = containerModel.getMainFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在默认面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
                if (containerModel.getHiddenFunctionControl() != null) {
                    elementList = containerModel.getHiddenFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在隐藏面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
            } else if (model instanceof FormatModel) {
                FormatModel formatModel = (FormatModel) model;
                if (formatModel.getControlPane() != null) {
                    elementList = formatModel.getControlPane().getTheSpecifiedLabelModels(specifiedClassList);//在控制面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalFSCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
            }
        }
        return fileArrayList;
    }

    /**
     * 从modelList中获取里面所有图片组件对应的文件夹
     *
     * @param parentFolder
     * @param modelList
     * @return
     */
    public static ArrayList<File> getCorrespondingModelFilePictureList(File parentFolder, ArrayList<AbstractEditContainerModel> modelList) {
        ArrayList<Class<?>> specifiedClassList = new ArrayList<>();
        specifiedClassList.add(FunctionAddControlLabel.class);
        specifiedClassList.add(InfrequentlyUsedSettingControlLabel.class);
        specifiedClassList.add(PictureControlLabel.class);

        ArrayList<File> fileArrayList = new ArrayList<>(), fileList;
        ArrayList<BaseElementInterface> elementList;
        for (AbstractEditContainerModel model : modelList) {
            if (model instanceof ContainerModel) {
                ContainerModel containerModel = (ContainerModel) model;
                if (containerModel.getMainFunctionControl() != null) {
                    elementList = containerModel.getMainFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在默认面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
                if (containerModel.getHiddenFunctionControl() != null) {
                    elementList = containerModel.getHiddenFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在隐藏面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
            } else if (model instanceof FormatModel) {
                FormatModel formatModel = (FormatModel) model;
                if (formatModel.getControlPane() != null) {
                    elementList = formatModel.getControlPane().getTheSpecifiedLabelModels(specifiedClassList);//在控制面板里面的文件选择、功能拓展、不常用模型中拿到里面所有文件选择模型的对应文件夹
                    for (BaseElementInterface element : elementList) {
                        fileList = DeserializeElementMethods.getInternalPictureCorrespondingFolder(parentFolder, element);
                        fileArrayList.addAll(fileList);
                    }
                }
            }
        }
        return fileArrayList;
    }


    /**
     * 更改modelList所有模型的对应内容选择
     *
     * @param option
     */
    public static void updateCorrespondingContentChooseModel(OptionDataModel option) {
        ArrayList<AbstractEditContainerModel> modelList = getCorrespondingCodeEditingModel(option);
        if (modelList != null) {
            for (AbstractEditContainerModel model : modelList) {
                model.updateComboboxItems(option);//先把所有对应可以添加这个选项的面板的内容选择组件都进行更改

                ArrayList<BaseElementInterface> elementList;

                ArrayList<Class<?>> specifiedClassList = new ArrayList<>();
                specifiedClassList.add(FunctionAddControlLabel.class);
                specifiedClassList.add(InfrequentlyUsedSettingControlLabel.class);

                if (model instanceof ContainerModel) {
                    ContainerModel containerModel = (ContainerModel) model;
                    if (containerModel.getMainFunctionControl() != null) {

                        elementList = containerModel.getMainFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在默认面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行更改
                        for (BaseElementInterface element : elementList) {
                            DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
                        }
                    }
                    if (containerModel.getHiddenFunctionControl() != null) {
                        elementList = containerModel.getHiddenFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在隐藏面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行更改
                        for (BaseElementInterface element : elementList) {
                            DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
                        }
                    }
                    CommandCodeControl.updateCodePaneMenu(containerModel);
                } else if (model instanceof FormatModel) {
                    FormatModel formatModel = (FormatModel) model;
                    if (formatModel.getControlPane() != null) {
                        elementList = formatModel.getControlPane().getTheSpecifiedLabelModels(specifiedClassList);//在控制面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行更改
                        for (BaseElementInterface element : elementList) {
                            DeserializeElementMethods.updateControlStatementInternalCorrespondingContentChooseModel(option, element);
                        }
                    }
                    LazyCoderFormatControl.updateCodePaneMenu(formatModel);
                }
            }
        }
    }

    /**
     * 根据选项的使用范围，获取对应的代码编辑模型
     *
     * @param option
     * @return
     */
    private static ArrayList<AbstractEditContainerModel> getCorrespondingCodeEditingModel(OptionDataModel option) {
        ArrayList<AbstractEditContainerModel> allModelList = null;
        if (option != null) {
            int usingRange = option.getUsingRange();
            if (usingRange == OptionDataModel.MODULE_TYPE) {
                if (DataSourceEditHolder.moduleEditPane != null) {
                    allModelList = DataSourceEditHolder.moduleEditPane.getAllEditContainerModel();
                }
            } else if (usingRange == OptionDataModel.GENERAL_TYPE) {
                if (DataSourceEditHolder.dataSourceEditFrame != null) {
                    allModelList = DataSourceEditHolder.dataSourceEditFrame.getAllEditContainerModel();
                }
            } else if (usingRange == OptionDataModel.MAIN_TYPE) {
                if (FormatEditPaneHolder.mainFormatEditPane != null) {
                    allModelList = FormatEditPaneHolder.mainFormatEditPane.getAllEditContainerModel();
                }
            } else if (usingRange == OptionDataModel.ADDITIONAL_TYPE) {
                if (FormatEditPaneHolder.additionalEditPane != null) {
                    allModelList = FormatEditPaneHolder.additionalEditPane.getAllEditContainerModel();
                }
            }
        }
        return allModelList;
    }


    /**
     * 更改modelList所有模型的对应内容选择
     *
     * @param option
     */
    public static void delCorrespondingContentChooseModel(OptionDataModel option) {
        ArrayList<AbstractEditContainerModel> allModelList = getCorrespondingCodeEditingModel(option);//获取对应代码编辑面板的所有模型
        if (allModelList != null) {
            ArrayList<BaseElementInterface> elementList;

            ArrayList<Class<?>> specifiedClassList = new ArrayList<>();
            specifiedClassList.add(FunctionAddControlLabel.class);
            specifiedClassList.add(InfrequentlyUsedSettingControlLabel.class);

            for (AbstractEditContainerModel model : allModelList) {
                model.delContentChoose(option.getOptionId());//先删掉目前所有对应代码编辑面板里面的这个选项

                if (model instanceof ContainerModel) {//把里面所有的功能拓展组件和不常用组件里的模型，里面对应这个选项的内容都删掉
                    ContainerModel containerModel = (ContainerModel) model;
                    if (containerModel.getMainFunctionControl() != null) {
                        elementList = containerModel.getMainFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在默认面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行删除
                        DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, elementList);
                    }
                    if (containerModel.getHiddenFunctionControl() != null) {
                        elementList = containerModel.getHiddenFunctionControl().getTheSpecifiedLabelModels(specifiedClassList);//在隐藏面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行删除
                        DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, elementList);
                    }

                } else if (model instanceof FormatModel) {
                    FormatModel formatModel = (FormatModel) model;
                    if (formatModel.getControlPane() != null) {
                        elementList = formatModel.getControlPane().getTheSpecifiedLabelModels(specifiedClassList);//在控制面板里面的功能拓展、不常用模型中拿到里面所有文件选择模型，进行删除
                        DeserializeElementMethods.delControlStatementInternalCorrespondingContentChooseModel(option, elementList);
                    }
                }
            }
        }
    }


}
