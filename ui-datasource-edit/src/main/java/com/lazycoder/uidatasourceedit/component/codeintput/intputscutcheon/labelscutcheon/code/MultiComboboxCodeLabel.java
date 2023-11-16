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
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.MultiComboboxLabel;
import com.lazycoder.uiutils.utils.SysUtil;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import lombok.Getter;


public class MultiComboboxCodeLabel extends MultiComboboxLabel<String> implements CodeLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 3889106091621771857L;

    @Getter
    private ContentChooseCodeElement codeElement;

    /**
     * 新建/还原
     *
     * @param codeElement
     */
    public MultiComboboxCodeLabel(ContentChooseCodeElement codeElement) {
        // TODO Auto-generated constructor stub
        super(new String[]{});
        this.codeElement = codeElement;
        super.setName(codeElement.getThisName());
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(codeElement.getOptionId());
        if (optionDataModel != null) {
            updateSelectIndexs();
//            String tip = ContentChooseElement.getShowNameForCode(codeElement, (codeElement.getSelectGroup() + 1));

            ArrayList<String> noteList = JSON.parseObject(optionDataModel.getRowNoteParam(),
                    new TypeReference<ArrayList<String>>() {
                    });
            String tip = ContentChooseElement.getShowNameForCode(codeElement, (codeElement.getSelectGroup() + 1), optionDataModel);
            if (noteList.size() > codeElement.getSelectGroup() && !("".equals(noteList.get(codeElement.getSelectGroup())))) {
                tip = tip + "  " + noteList.get(codeElement.getSelectGroup());
            }
            setToolTipText(tip);
        }
        setTheSize();
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
                        addSelectedIndex(selectedTemp);
                    }
                }
            }
        }
    }

    /**
     * 更新选项，本方法只在初始化构造时调用
     */
    private void updateSelectIndexs() {
        List<Integer> orinalSelectList = getSelectedSortedIndexs();

        clearSelectedIndexs();
        int selectGroup = this.codeElement.getSelectGroup();
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(this.codeElement.getOptionId());
        if (optionDataModel != null) {
            if (selectGroup >= optionDataModel.getValueListGroupNum()) {
                String text = "有个代码内容面板，里面有个\"" + optionDataModel.getOptionName() + "\"多选内容选择组件，选第" + (selectGroup + 1) + "组选项作为代码下拉框列表，可这组已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（生成组件异常）————\"" + optionDataModel.getOptionName() + "\"这个选项，当前有" + optionDataModel.getValueListGroupNum() + "组，有个多选组件却默认选第" + (selectGroup + 1) + "组作为代码下拉框列表";
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

            for (Integer index : orinalSelectList) {
                if (getItemCount() > index) {
                    addSelectedIndex(index);
                }
            }

            SysUtil.updateFrameUI(this);
        }
    }

    /**
     * 更改选项组件里面的可选值，在生成界面，经过初始化构造后，如果需要更改可选值，才调用该函数
     *
     * @param option
     */
    public void updateComboboxItems(OptionDataModel option) {
        List<Integer> orinalSelectList = getSelectedSortedIndexs();

        clearSelectedIndexs();
        this.codeElement.setOptionName(option.getOptionName());

        int selectGroup = this.codeElement.getSelectGroup();
        if (selectGroup >= option.getValueListGroupNum()) {
            String text = "有个代码内容面板，里面有个\"" + option.getOptionName() + "\"多选内容选择组件，选第" + (selectGroup + 1) + "组选项作为代码下拉框列表，可这组选项已经被删了！	(✪ω✪)";
            String logtext = getClass() + "（更改选项异常）————\"" + option.getOptionName() + "\"这个选项，当前有" + option.getValueListGroupNum() + "组选项，有个多选组件却默认选第" + (selectGroup + 1) + "组作为代码下拉框列表";
            DataSourceEditHolder.errorLogging(text, logtext);

            selectGroup = 0;
            this.codeElement.setSelectGroup(selectGroup);
        }

        ArrayList<String> list = TheOptionModel.getOptionValueList(option, selectGroup);
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 0; i < list.size(); i++) {
            model.addElement(list.get(i));
        }
        setModel(model);

        for (Integer index : orinalSelectList) {
            if (getModel().getSize() > index) {
                addSelectedIndex(index);
            } else {
                String text = "有个代码内容面板，里面有个\"" + option.getOptionName() + "\"多选内容选择组件，选第" + (index + 1) + "个选项作为默认值，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（更改选项异常）————\"" + option.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个多选组件却默认选第" + (index + 1) + "个";
                DataSourceEditHolder.errorLogging(text, logtext);
            }
        }
    }

    @Override
    public String getSelectedItemsString() {
        ArrayList<Integer> selectList = new ArrayList<>();
        selectList.addAll(getSelectedSortedIndexs());
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(this.codeElement.getOptionId());
        String selectedItemsString = "";
        if (optionDataModel != null) {
            if (getSelectedIndexs().size() > 0) {
                selectedItemsString = TheOptionModel.getValue(optionDataModel, codeElement.getSelectGroup(), selectList);
            }
        }
        return selectedItemsString;
    }

    /**
     * 设置选项选择值（给操作层的选项组件使用）
     */
    public void setValue(ArrayList<Integer> selectList) {
        // TODO Auto-generated method stub
        clearSelectedIndexs();
        for (Integer selectIndexTemp : selectList) {
            if (getItemCount() > selectIndexTemp) {
                addSelectedIndex(selectIndexTemp);
            }
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
        return this.codeElement.getOptionName();
    }

}
