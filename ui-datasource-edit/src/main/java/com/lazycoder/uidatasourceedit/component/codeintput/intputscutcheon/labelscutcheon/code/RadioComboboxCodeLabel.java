package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.TheOptionModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.vo.element.lable.ContentChooseElement;
import com.lazycoder.service.vo.element.lable.code.ContentChooseCodeElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.general.GeneralControl;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.AbstractRadioComboboxLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import lombok.Getter;

public class RadioComboboxCodeLabel extends AbstractRadioComboboxLabel<String> implements CodeLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = -6167721417263636641L;

    @Getter
    private ContentChooseCodeElement codeElement = null;

    /**
     * 新建/还原
     *
     * @param codeElement
     */
    public RadioComboboxCodeLabel(ContentChooseCodeElement codeElement) {
        super(new String[]{});
        this.codeElement = codeElement;
        super.setName(codeElement.getThisName());
        updateSelectIndexs();
        setTheSize();

        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(codeElement.getOptionId());
        if (optionDataModel != null) {
            ArrayList<String> noteList = JSON.parseObject(optionDataModel.getRowNoteParam(),
                    new TypeReference<ArrayList<String>>() {
                    });
            String tip = ContentChooseElement.getShowNameForCode(codeElement, (codeElement.getSelectGroup() + 1), optionDataModel);
            if (noteList.size() > codeElement.getSelectGroup() && !("".equals(noteList.get(codeElement.getSelectGroup())))) {
                tip = tip + "  " + noteList.get(codeElement.getSelectGroup());
            }
            setToolTipText(tip);
        }
    }

    @Override
    protected void init() {
        super.init();
        setRenderer(new CustomComboBoxTextSizeRenderer(8));
    }

    /**
     * 根据对应的控制标签当前选中的值选对应值
     */
    public void setCurrentCorrespondingControlLableSelectedIndex() {
        if (getPassingComponentParams() != null && getPassingComponentParams().getGeneralModel() != null) {
            ContentChooseControl control = GeneralControl.getContentChooseControlModel(getPassingComponentParams().getGeneralModel(), codeElement);
            if (control != null) {
                ArrayList<Integer> selectedList = control.getSelectList();
                if (selectedList != null) {
                    for (Integer selectedTemp : selectedList) {
                        setSelectedIndex(selectedTemp);
                    }
                }
            }
        }
    }

    /**
     * 更新选项，本方法只在初始化构造时调用
     */
    private void updateSelectIndexs() {
        int selectedIndex = getSelectedIndex();
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(this.codeElement.getOptionId());

        int selectGroup = this.codeElement.getSelectGroup();
        if (selectGroup >= optionDataModel.getValueListGroupNum()) {
            String text = "有个代码内容面板，里面有个\"" + optionDataModel.getOptionName() + "\"单选内容选择组件，选第" + (selectGroup + 1) + "组选项作为代码值，可这组选项已经被删了！	(✪ω✪)";
            String logtext = getClass() + "（生成组件异常）————\"" + optionDataModel.getOptionName() + "\"这个选项，当前有" + optionDataModel.getValueListGroupNum() + "组选项，有个单选组件却默认选第" + (selectGroup + 1) + "组作为代码下拉框列表";
            DataSourceEditHolder.errorLogging(text, logtext);

            selectGroup = 0;
            this.codeElement.setSelectGroup(selectGroup);
        }
        ArrayList<String> list = TheOptionModel.getOptionValueList(optionDataModel, selectGroup);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String valueTemp : list) {
            model.addElement(valueTemp);
        }
        setModel(model);

        if (getModel().getSize() > selectedIndex) {
            setSelectedIndex(selectedIndex);
        }
        SysUtil.updateFrameUI(this);
    }

    /**
     * 更改选项组件里面的可选值，在生成界面，经过初始化构造后，如果需要更改可选值，才调用该函数
     *
     * @param option
     */
    public void updateComboboxItems(OptionDataModel option) {
        int selectedIndex = getSelectedIndex();
        this.codeElement.setOptionName(option.getOptionName());

        int selectGroup = this.codeElement.getSelectGroup();
        if (selectGroup >= option.getValueListGroupNum()) {
            String text = "有个代码内容面板，里面有个\"" + option.getOptionName() + "\"单选内容选择组件，选第" + (selectGroup + 1) + "组选项作为代码值，可这组选项已经被删了！	(✪ω✪)";
            String logtext = getClass() + "（更改选项异常）————\"" + option.getOptionName() + "\"这个选项，当前有" + option.getValueListGroupNum() + "组选项，有个单选组件却默认选第" + (selectGroup + 1) + "组作为代码下拉框列表";
            DataSourceEditHolder.errorLogging(text, logtext);

            selectGroup = 0;
            this.codeElement.setSelectGroup(selectGroup);
        }
        ArrayList<String> list = TheOptionModel.getOptionValueList(option, selectGroup);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String valueTemp : list) {
            model.addElement(valueTemp);
        }
        setModel(model);

        if (getModel().getSize() > selectedIndex) {
            setSelectedIndex(selectedIndex);
        } else {
            String text = "有个代码内容面板，里面有个\"" + option.getOptionName() + "\"单选内容选择组件，选第" + (selectedIndex + 1) + "组选项作为默认值，可这个选项已经被删了！	(✪ω✪)";
            String logtext = getClass() + "（更改选项异常）————\"" + option.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项，有个单选组件却默认选第" + (selectedIndex + 1) + "个作为默认值";
            DataSourceEditHolder.errorLogging(text, logtext);

            setSelectedIndex(0);
        }
    }

    /**
     * 设值，操作面板上的选项组件选值后，调用该函数显示对应值
     *
     * @param selectIndex
     */
    public void setValue(int selectIndex) {
        if (getModel().getSize() > selectIndex) {
            setSelectedIndex(selectIndex);
        }
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        codeElement.setThisName(name);
    }

    /**
     * 匹配
     *
     * @param chooseControl
     * @return
     */
    public boolean match(ContentChooseControl chooseControl) {
        return codeElement.match(chooseControl);
    }

    @Override
    public ContentChooseCodeElement property() {
        // TODO Auto-generated method stub
        return codeElement;
    }

    /**
     * 获取该组件对应的选项名
     *
     * @return
     */
    public String getOptionName() {
        return codeElement.getOptionName();
    }

}