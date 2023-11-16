package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.database.common.LabelElementName;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.DeserializeElementMethods;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.base.BaseElementInterface;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.transferparam.InfrequentlyUsedSettingParam;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.AbstractFunctionControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.command.CommandCodeControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.AbstractFormatControlInputPane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.LazyCoderFormatControl;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.InfrequentlyUsedSettingLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.InfrequentlyUsedSettingEditFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InfrequentlyUsedSettingControlLabel extends InfrequentlyUsedSettingLabel
        implements ControlLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 1649853920971003208L;

    private InfrequentlyUsedSettingControl controlElement = new InfrequentlyUsedSettingControl();

    private InfrequentlyUsedSettingParam infrequentlyUsedSettingParam;

    private AbstractEditContainerModel model;

    /**
     * 新建(可自行生成文件名)
     *
     * @param model
     * @param infrequentlyUsedSettingParam
     */
    public InfrequentlyUsedSettingControlLabel(AbstractEditContainerModel model,
                                               InfrequentlyUsedSettingParam infrequentlyUsedSettingParam) {
        // TODO Auto-generated constructor stub
        super();
        String name = GeneralControl.generateComponentName(model, LabelElementName.INFREQUENTLY_USED_SETTING);
        init(name);
        this.model = model;
        this.infrequentlyUsedSettingParam = infrequentlyUsedSettingParam;
    }

    /**
     * 还原
     *
     * @param controlElement
     * @param model
     * @param infrequentlyUsedSettingParam
     */
    public InfrequentlyUsedSettingControlLabel(InfrequentlyUsedSettingControl controlElement, AbstractEditContainerModel model,
                                               InfrequentlyUsedSettingParam infrequentlyUsedSettingParam) {
        // TODO Auto-generated constructor stub
        super();
        this.controlElement = controlElement;
        init(controlElement.getThisName());
        this.model = model;
        this.infrequentlyUsedSettingParam = infrequentlyUsedSettingParam;

        //找出里面的内容选择组件，然后将对应代码框里面的内容选择组件都选上默认值
        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.getControlPaneElmentList(controlElement.getControlStatementFormat());
        if (elementList != null) {
            ContentChooseControl chooseControl;
            for (BaseElementInterface element : elementList) {
                if (element instanceof ContentChooseControl) {
                    chooseControl = (ContentChooseControl) element;

                    OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(chooseControl.getOptionId());
                    if (chooseControl.getSelectList() != null && chooseControl.getSelectList().size() > 0) {
                        if (optionDataModel.getOptionType() == OptionDataModel.EXECLUSIVE) {
                            if (this.model != null) {
                                ArrayList<BaseCodePane> list = this.model.getCodePaneList();
                                for (BaseCodePane codePane : list) {
                                    codePane.updateRadioComboboxShowValue(chooseControl, chooseControl.getSelectList().get(0));
                                }
                            }
                        } else if (optionDataModel.getOptionType() == OptionDataModel.MULTIPLE) {
                            if (this.model != null) {
                                ArrayList<BaseCodePane> list = this.model.getCodePaneList();
                                for (BaseCodePane codePane : list) {
                                    codePane.updateMutiComboboxShowValue(chooseControl, chooseControl.getSelectList());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void init(String name) {
        setName(name);
        setUI(new ControlLabelButtonUI());
        setText(name);
        addActionListener(listener);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new InfrequentlyUsedSettingEditFrame(InfrequentlyUsedSettingControlLabel.this, model,
                    InfrequentlyUsedSettingControlLabel.this.infrequentlyUsedSettingParam);
        }
    };

    public String getControlStatementFormat() {
        return controlElement.getControlStatementFormat();
    }

    public void setControlStatementFormat(String controlStatementFormat) {
        controlElement.setControlStatementFormat(controlStatementFormat);
    }

    @Override
    public void deleteFromPanel() {//把不常用组件里面的所有元素都删掉
        BasePane componentPutPane = infrequentlyUsedSettingParam.getThisPane();

        String delLabelType, delName, optionId;
        BaseLableElement lableElement;
        ContentChooseControl contentChooseControl;

        ArrayList<BaseElementInterface> elementList = DeserializeElementMethods.getControlPaneElmentList(controlElement.getControlStatementFormat());//根据不常用组件模型里面的内容还原成元素数组
        for (BaseElementInterface element : elementList) {
            if (element instanceof BaseLableElement) {//找出里面所有的标签
                lableElement = (BaseLableElement) element;
                delLabelType = lableElement.getLabelType();
                delName = lableElement.getThisName();
                if (lableElement instanceof ContentChooseControl) {//有内容选择控制标签的话，从模型中删掉对应内容选择控制标签
                    contentChooseControl = (ContentChooseControl) lableElement;
                    optionId = contentChooseControl.getOptionId();
                    if (componentPutPane != null) {
                        if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                            CommandCodeControl.delContentChoose(model, optionId, contentChooseControl.getUseNumbered());

                        } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                            LazyCoderFormatControl.delContentChoose(model, optionId, contentChooseControl.getUseNumbered());

                        }
                    }
                } else {//其他的控制标签，从模型中删掉对应的控制标签
                    if (componentPutPane != null) {
                        if (componentPutPane instanceof AbstractFunctionControlInputPane) {
                            CommandCodeControl.delControlLabel(model, delLabelType, delName);

                        } else if (componentPutPane instanceof AbstractFormatControlInputPane) {
                            LazyCoderFormatControl.delControlLabel(model, delLabelType, delName);
                        }
                    }
                }
            }

        }
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        controlElement.setThisName(name);
    }

    @Override
    public InfrequentlyUsedSettingControl property() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public InfrequentlyUsedSettingControl getControl() {
        // TODO Auto-generated method stub
        return controlElement;
    }

}
