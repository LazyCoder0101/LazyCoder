package com.lazycoder.service;

import com.alibaba.fastjson.JSONObject;
import com.lazycoder.database.common.ElementName;
import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.base.BaseElement;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.base.StringElement;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.CodeInputElement;
import com.lazycoder.service.vo.element.lable.ConstantElement;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import com.lazycoder.service.vo.element.lable.CorrespondingAdditionalDefaultFileElement;
import com.lazycoder.service.vo.element.lable.CustomMethodNameElement;
import com.lazycoder.service.vo.element.lable.CustomVariableElement;
import com.lazycoder.service.vo.element.lable.FileSelectorElement;
import com.lazycoder.service.vo.element.lable.FunctionAddElement;
import com.lazycoder.service.vo.element.lable.InfrequentlyUsedSettingElement;
import com.lazycoder.service.vo.element.lable.MethodChooseElement;
import com.lazycoder.service.vo.element.lable.NoteElement;
import com.lazycoder.service.vo.element.lable.PictureElement;
import com.lazycoder.service.vo.element.lable.TextInputElement;
import com.lazycoder.service.vo.element.lable.VariableElement;
import com.lazycoder.service.vo.element.lable.code.CodeInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.ConstantCodeElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.CorrespondingAdditionalDefaultFileCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomMethodNameCodeElement;
import com.lazycoder.service.vo.element.lable.code.CustomVariableCodeElement;
import com.lazycoder.service.vo.element.lable.code.FileSelectorCodeElement;
import com.lazycoder.service.vo.element.lable.code.FunctionAddCodeElement;
import com.lazycoder.service.vo.element.lable.code.MethodChooseCodeElement;
import com.lazycoder.service.vo.element.lable.code.TextInputCodeElement;
import com.lazycoder.service.vo.element.lable.code.ThisFileNameCodeElement;
import com.lazycoder.service.vo.element.lable.code.VariableCodeElement;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.element.lable.control.ConstantControl;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.CorrespondingAdditionalDefaultFileControl;
import com.lazycoder.service.vo.element.lable.control.CustomMethodNameControl;
import com.lazycoder.service.vo.element.lable.control.CustomVariableControl;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.element.lable.control.FunctionAddControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.element.lable.control.MethodChooseControl;
import com.lazycoder.service.vo.element.lable.control.NoteControl;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.element.lable.control.TextInputControl;
import com.lazycoder.service.vo.element.lable.control.VariableControl;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.service.vo.element.mark.BaseMarkElement;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.service.vo.element.mark.MainSetMarkElement;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.utils.JsonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DeserializeElementMethods {

    /**
     * 还原代码输入基本单元的控制组件信息
     *
     * @param text
     * @return
     */
    public static ArrayList<BaseElementInterface> getControlComponentCorrespondingInformationList(String text) {
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        String elementType;
        BaseElement element;
        List<JSONObject> tempList = JsonUtil.restoreArrayByStr(text, JSONObject.class);
        if (tempList != null) {
            for (JSONObject base : tempList) {
                elementType = BaseElement.getElementType(base);
                if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    element = getDeserializeControlComponentCorrespondingInformation(base);
                    if (element != null) {
                        list.add(element);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 还原成控制面板的的模型元素
     *
     * @param text
     * @return
     */
    public static ArrayList<BaseElementInterface> getControlPaneElmentList(String text) {
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        BaseElement element;
        String elementType;
        List<JSONObject> tempList = JsonUtil.restoreArrayByStr(text, JSONObject.class);
        if (tempList != null) {
            for (JSONObject base : tempList) {
                elementType = BaseElement.getElementType(base);
                if (ElementName.STRING_ELEMENT.equals(elementType)) {
                    element = JsonUtil.restoreByJSONObject(base, StringElement.class);
                    if (element != null) {
                        list.add(element);
                    }

                } else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    element = getDeserializeControlElement(base);
                    if (element != null) {
                        list.add(element);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 还原成代码面板的模型元素
     *
     * @param text
     * @return
     */
    public static ArrayList<BaseElementInterface> getCodePaneElementList(String text) {
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        BaseElement element;
        String elementType;
        List<JSONObject> tempList = JsonUtil.restoreArrayByStr(text, JSONObject.class);
        if (tempList != null) {
            for (JSONObject base : tempList) {
                elementType = BaseElement.getElementType(base);
                if (ElementName.STRING_ELEMENT.equals(elementType)) {
                    element = JsonUtil.restoreByJSONObject(base, StringElement.class);
                    if (element != null) {
                        list.add(element);
                    }

                } else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    element = getDeserializeCodeElement(base);
                    if (element != null) {
                        list.add(element);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 还原成代码文件面板的模型元素
     *
     * @param text
     * @return
     */
    public static ArrayList<BaseElementInterface> getCodeFilePaneElementList(String text) {
        ArrayList<BaseElementInterface> list = new ArrayList<>();
        BaseElement element;
        String elementType;
        List<JSONObject> tempList = JsonUtil.restoreArrayByStr(text, JSONObject.class);
        if (tempList != null) {
            for (JSONObject base : tempList) {
                elementType = BaseElement.getElementType(base);
                if (ElementName.STRING_ELEMENT.equals(elementType)) {
                    element = JsonUtil.restoreByJSONObject(base, StringElement.class);
                    if (element != null) {
                        list.add(element);
                    }

                } else if (ElementName.LABEL_ELEMENT.equals(elementType)) {
                    element = getDeserializeCodeElement(base);
                    if (element != null) {
                        list.add(element);
                    }

                } else if (ElementName.MARK_ELEMENT.equals(elementType)) {
                    element = getDeserializeMarkElement(base);
                    if (element != null) {
                        list.add(element);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 还原成控制组件信息的模型
     *
     * @param labelJSONObject
     * @return
     */
    protected static BaseLableElement getDeserializeControlComponentCorrespondingInformation(JSONObject labelJSONObject) {
        String labelType = BaseLableElement.getLabelType(labelJSONObject);
        BaseLableElement temp = null;
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, TextInputElement.class);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            temp = ContentChooseElement.restoreByJSONObject(labelJSONObject);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FunctionAddElement.class);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomVariableElement.class);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, VariableElement.class);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, ConstantElement.class);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FileSelectorElement.class);

        } else if (LabelElementName.NOTE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, NoteElement.class);

        } else if (LabelElementName.PICTURE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, PictureElement.class);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CodeInputElement.class);

        } else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, InfrequentlyUsedSettingElement.class);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomMethodNameElement.class);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, MethodChooseElement.class);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CorrespondingAdditionalDefaultFileElement.class);

        }
        return temp;
    }

    /**
     * 还原成控制标签的模型
     *
     * @param labelJSONObject
     * @return
     */
    public static BaseLableElement getDeserializeControlElement(JSONObject labelJSONObject) {

        String labelType = BaseLableElement.getLabelType(labelJSONObject);
        BaseLableElement temp = null;
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, TextInputControl.class);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            temp = ContentChooseControl.restoreByJSONObject(labelJSONObject);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FunctionAddControl.class);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomVariableControl.class);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, VariableControl.class);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, ConstantControl.class);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FileSelectorControl.class);

        } else if (LabelElementName.NOTE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, NoteControl.class);

        } else if (LabelElementName.PICTURE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, PictureControl.class);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CodeInputControl.class);

        } else if (LabelElementName.INFREQUENTLY_USED_SETTING.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, InfrequentlyUsedSettingControl.class);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomMethodNameControl.class);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, MethodChooseControl.class);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CorrespondingAdditionalDefaultFileControl.class);
        }
        return temp;
    }

    /**
     * 还原成代码标签的模型
     *
     * @param labelJSONObject
     * @return
     */
    protected static BaseLableElement getDeserializeCodeElement(JSONObject labelJSONObject) {
        String labelType = BaseLableElement.getLabelType(labelJSONObject);
        BaseLableElement temp = null;
        if (LabelElementName.TEXT_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, TextInputCodeElement.class);

        } else if (LabelElementName.CONTENT_CHOOSE.equals(labelType)) {
            temp = ContentChooseCodeElement.restoreByJSONObject(labelJSONObject);

        } else if (LabelElementName.FUNCTION_ADD.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FunctionAddCodeElement.class);

        } else if (LabelElementName.CUSTOM_VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomVariableCodeElement.class);

        } else if (LabelElementName.VARIABLE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, VariableCodeElement.class);

        } else if (LabelElementName.CONSTANT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, ConstantCodeElement.class);

        } else if (LabelElementName.FILE_SELECTOR.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, FileSelectorCodeElement.class);

        } else if (LabelElementName.CODE_INPUT.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CodeInputCodeElement.class);

        } else if (LabelElementName.CUSTOM_METHOD_NAME.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CustomMethodNameCodeElement.class);

        } else if (LabelElementName.METHOD_CHOOSE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, MethodChooseCodeElement.class);

        } else if (LabelElementName.THIS_FILE_NAME.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, ThisFileNameCodeElement.class);

        } else if (LabelElementName.CORRESPONDING_ADDITIONAL_DEFAULT_FILE.equals(labelType)) {
            temp = JsonUtil.restoreByJSONObject(labelJSONObject, CorrespondingAdditionalDefaultFileCodeElement.class);
        }
        return temp;
    }

    /**
     * 还原标记模型
     *
     * @param markJSONObject
     */
    protected static BaseMarkElement getDeserializeMarkElement(JSONObject markJSONObject) {
        String markType = BaseMarkElement.getMarkType(markJSONObject);
        BaseMarkElement markElement = null;
        if (MarkElementName.FUNCTION_MARK.equals(markType)) {
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, FunctionMarkElement.class);

        } else if (MarkElementName.SET_MARK.equals(markType)) {
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, SetMarkElement.class);

        } else if (MarkElementName.INIT_MARK.equals(markType)) {
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, InitMarkElement.class);

        } else if (MarkElementName.IMPORT_MARK.equals(markType)) {
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, ImportMarkElement.class);

        } else if (MarkElementName.MAIN_SET_TYPE_MARK.equals(markType)) {
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, MainSetMarkElement.class);

        } else if (MarkElementName.ADDITIONAL_SET_TYPE_MARK.equals(markType)) {
//		} else if ("otherSetTypeMark".equals(markType)) {=
            markElement = JsonUtil.restoreByJSONObject(markJSONObject, AdditionalSetMarkElement.class);
        }
        return markElement;
    }

    /**
     * 从标签控制组件模型里面获取里面所有的对应文件夹
     *
     * @param parentFolder
     * @param labelControlElement
     * @return
     */
    public static ArrayList<File> getInternalFSCorrespondingFolder(File parentFolder, BaseElementInterface labelControlElement) {
        ArrayList<File> fileArrayList = new ArrayList<>(), fileTempList;
        File fileTemp;
        FileSelectorControl fileSelectorControl;
        FunctionAddControl methodAddControl;
        InfrequentlyUsedSettingControl infrequentlyUsedSettingControl;
        if (labelControlElement instanceof FileSelectorControl) {
            fileSelectorControl = (FileSelectorControl) labelControlElement;
            fileTemp = fileSelectorControl.getCorrespondingFolder(parentFolder);
            fileArrayList.add(fileTemp);

        } else if (labelControlElement instanceof FunctionAddControl) {
            methodAddControl = (FunctionAddControl) labelControlElement;
            fileTempList = methodAddControl.getInternalFSCorrespondingFolder(parentFolder);
            fileArrayList.addAll(fileTempList);

        } else if (labelControlElement instanceof InfrequentlyUsedSettingControl) {
            infrequentlyUsedSettingControl = (InfrequentlyUsedSettingControl) labelControlElement;
            fileTempList = infrequentlyUsedSettingControl.getInternalFSCorrespondingFolder(parentFolder);
            fileArrayList.addAll(fileTempList);
        }
        return fileArrayList;
    }

    /**
     * 获取图片模型对应的文件夹
     *
     * @param parentFolder
     * @param labelControlElement
     * @return
     */
    public static ArrayList<File> getInternalPictureCorrespondingFolder(File parentFolder, BaseElementInterface labelControlElement) {
        ArrayList<File> fileArrayList = new ArrayList<>(), fileTempList;
        File fileTemp;
        PictureControl pictureControl;
        FunctionAddControl methodAddControl;
        InfrequentlyUsedSettingControl infrequentlyUsedSettingControl;
        if (labelControlElement instanceof PictureControl) {
            pictureControl = (PictureControl) labelControlElement;
            fileTemp = pictureControl.getCorrespondingFolder(parentFolder);
            fileArrayList.add(fileTemp);

        } else if (labelControlElement instanceof FunctionAddControl) {
            methodAddControl = (FunctionAddControl) labelControlElement;
            fileTempList = methodAddControl.getInternalPictureCorrespondingFolder(parentFolder);
            fileArrayList.addAll(fileTempList);

        } else if (labelControlElement instanceof InfrequentlyUsedSettingControl) {
            infrequentlyUsedSettingControl = (InfrequentlyUsedSettingControl) labelControlElement;
            fileTempList = infrequentlyUsedSettingControl.getInternalPictureCorrespondingFolder(parentFolder);
            fileArrayList.addAll(fileTempList);
        }
        return fileArrayList;
    }


    /**
     * 更改控制语句内部的对应选项模型
     */
    public static void updateControlStatementInternalCorrespondingContentChooseModel(
            OptionDataModel option, BaseElementInterface labelControlElement) {

        ContentChooseControl contentChooseControl;
        FunctionAddControl methodAddControl;
        InfrequentlyUsedSettingControl infrequentlyUsedSettingControl;
        if (labelControlElement instanceof ContentChooseControl) {
            contentChooseControl = (ContentChooseControl) labelControlElement;
            if (contentChooseControl.getOptionId().equals(option.getOptionId())) {//找到该选项，对该选项的数据进行更改
                contentChooseControl.updateParam(option);
            }
        } else if (labelControlElement instanceof FunctionAddControl) {
            methodAddControl = (FunctionAddControl) labelControlElement;
            methodAddControl.updateControlStatementInternalCorrespondingContentChooseModel(option);

        } else if (labelControlElement instanceof InfrequentlyUsedSettingControl) {
            infrequentlyUsedSettingControl = (InfrequentlyUsedSettingControl) labelControlElement;
            infrequentlyUsedSettingControl.updateControlStatementInternalCorrespondingContentChooseModel(option);
        }
    }


    /**
     * 更改代码语句内部的对应选项模型
     */
    public static void updateCodeStatementInternalCorrespondingContentChooseModel(OptionDataModel option, BaseElementInterface labelControlElement) {
        ContentChooseCodeElement contentChooseCodeElement;
        if (labelControlElement instanceof ContentChooseCodeElement) {
            contentChooseCodeElement = (ContentChooseCodeElement) labelControlElement;
            if (contentChooseCodeElement.getOptionId().equals(option.getOptionId())) {//找到该选项，对该选项的数据进行更改
                contentChooseCodeElement.updateParam(option);
            }
        }
    }


    /**
     * 删除控制语句内部的对应选项模型
     */
    public static ArrayList<BaseElementInterface> delControlStatementInternalCorrespondingContentChooseModel(OptionDataModel option, ArrayList<BaseElementInterface> elementListTemp) {
        ArrayList<BaseElementInterface> elementList = new ArrayList<>();
        ContentChooseControl contentChooseControl;
        FunctionAddControl methodAddControl, newMethodAddControl;
        InfrequentlyUsedSettingControl infrequentlyUsedSettingControl, newInfrequentlyUsedSettingControl;
        for (BaseElementInterface labelControlElement : elementListTemp) {
            if (labelControlElement instanceof ContentChooseControl) {
                contentChooseControl = (ContentChooseControl) labelControlElement;
                if (contentChooseControl.getOptionId().equals(option.getOptionId()) == false) {//找到该选项，对该选项的数据进行更改
                    elementList.add(labelControlElement);
                }
            } else if (labelControlElement instanceof FunctionAddControl) {
                methodAddControl = (FunctionAddControl) labelControlElement;
                newMethodAddControl = methodAddControl.delControlStatementInternalCorrespondingContentChooseModel(option);
                elementList.add(newMethodAddControl);

            } else if (labelControlElement instanceof InfrequentlyUsedSettingControl) {
                infrequentlyUsedSettingControl = (InfrequentlyUsedSettingControl) labelControlElement;
                newInfrequentlyUsedSettingControl = infrequentlyUsedSettingControl.delControlStatementInternalCorrespondingContentChooseModel(option);
                elementList.add(newInfrequentlyUsedSettingControl);
            } else {
                elementList.add(labelControlElement);
            }
        }
        return elementList;
    }


    /**
     * 删除添加组件对应信息里面对应的选项
     */
    public static ArrayList<BaseElementInterface> delContentChooseControlComponentCorrespondingInformation(String optionId, ArrayList<BaseElementInterface> elementListTemp) {
        ArrayList<BaseElementInterface> elementList = new ArrayList<>();
        ContentChooseElement contentChooseElement;
        for (BaseElementInterface labelControlElement : elementListTemp) {
            if (labelControlElement instanceof ContentChooseElement) {
                contentChooseElement = (ContentChooseElement) labelControlElement;
                if (contentChooseElement.getOptionId().equals(optionId) == false) {//找到该选项，对该选项的数据进行更改
                    elementList.add(contentChooseElement);
                }
            } else {
                elementList.add(labelControlElement);
            }
        }
        return elementList;
    }

    /**
     * 删除代码语句内部的对应选项模型
     */
    public static ArrayList<BaseElementInterface> delCodeStatementInternalCorrespondingContentChooseModel(OptionDataModel option, ArrayList<BaseElementInterface> elementListTemp) {
        ArrayList<BaseElementInterface> elementList = new ArrayList<>();
        ContentChooseCodeElement contentChooseCodeElement;
        for (BaseElementInterface labelControlElement : elementListTemp) {
            if (labelControlElement instanceof ContentChooseCodeElement) {
                contentChooseCodeElement = (ContentChooseCodeElement) labelControlElement;
                if (contentChooseCodeElement.getOptionId().equals(option.getOptionId()) == false) {//找到该选项，对该选项的数据进行更改
                    elementList.add(labelControlElement);
                }
            } else {
                elementList.add(labelControlElement);
            }
        }
        return elementList;
    }


}