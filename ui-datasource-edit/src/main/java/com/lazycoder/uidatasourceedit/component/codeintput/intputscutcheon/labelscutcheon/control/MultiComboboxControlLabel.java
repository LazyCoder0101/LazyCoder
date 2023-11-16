package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.TheOptionModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.MultiComboboxLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import lombok.Setter;

public class MultiComboboxControlLabel extends MultiComboboxLabel<String> implements ControlLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7238524236115667763L;

    @Setter
    private ContentChooseControl control;


    public MultiComboboxControlLabel(ContentChooseControl controlElement) {
        // TODO Auto-generated constructor stub
        super(new String[]{});
        this.control = controlElement;
        init(controlElement.getThisName());
        OptionDataModel optionDataModel = OptionDataModelTempHolder.getOption(controlElement.getOptionId());
        if(optionDataModel!=null) {
            setToolTipText(ContentChooseControl.getShowNameForOprating(controlElement,optionDataModel));
            itemInit(optionDataModel);
            for (int i = 0; i < control.getSelectList().size(); i++) {
                int selectedTemp = control.getSelectList().get(i);
                if (selectedTemp < optionDataModel.getValueNum()) {
                    super.addSelectedIndex(selectedTemp);
                } else {
                    String text = "有个代码操作面板，里面有个个\"" + optionDataModel.getOptionName() + "\"多选内容选择组件默认选第" + (selectedTemp + 1) + "个选项，可这个选项已经被删了！	(✪ω✪)";
                    String logtext = getClass() + "（生成组件异常）————\"" + optionDataModel.getOptionName() + "\"这个选项，当前有" + optionDataModel.getValueNum() + "个选项值，有个多选组件却默认选第" + (selectedTemp + 1) + "个";
                    DataSourceEditHolder.errorLogging(text, logtext);
                }
            }
        }
        setTheSize();
        addPopupMenuListener(listener);
    }

    public void init(String name) {
        setName(name);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        control.setThisName(name);
    }

    @Override
    public boolean addSelectedIndex(Integer index) {
        boolean flag = super.addSelectedIndex(index);
        ArrayList<Integer> selectList = new ArrayList<>(getSelectedSortedIndexs());
        control.setSelectList(selectList);
        return flag;
    }

    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            updateCodeComponetValue();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    public ContentChooseControl property() {
        // TODO Auto-generated method stub
        return control;
    }

    @Override
    public ContentChooseControl getControl() {
        return control;
    }


    /**
     * 设置代码面板对应组件的值
     */
    public void updateCodeComponetValue() {
        PassingComponentParams params = getPassingComponentParams();
        if (params != null && null != params.getGeneralModel()) {
            Set<Integer> selects = getSelectedIndexs();
            ArrayList<Integer> selectList = new ArrayList<>(selects);
            control.setSelectList(selectList);
            AbstractEditContainerModel model = getPassingComponentParams().getGeneralModel();
            if (model != null) {
                ArrayList<BaseCodePane> list = model.getCodePaneList();
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).updateMutiComboboxShowValue(control, selectList);
                }
            }
        }
    }

    /**
     * 更改选项
     */
    public void updateItems(OptionDataModel option) {
        List<Integer> selectIndexList = getSelectedSortedIndexs();
        this.control.setOptionName(option.getOptionName());
        itemInit(option);
        clearSelectedIndexs();
        if (selectIndexList != null) {
            for (Integer selectIndexTemp : selectIndexList) {
                if (selectIndexTemp < option.getValueNum()) {
                    addSelectedIndex(selectIndexTemp);
                } else {
                    String text = "有个代码操作面板，里面有个\"" + option.getOptionName() + "\"多选内容选择组件默认选第" + (selectIndexTemp + 1) + "个选项，可这个选项已经被删了！	(✪ω✪)";
                    String logtext = getClass() + "（更改选项异常）————\"" + option.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个多选组件却默认选第" + (selectIndexTemp + 1) + "个";
                    DataSourceEditHolder.errorLogging(text, logtext);
                }
            }
        }
        updateCodeComponetValue();
    }

    private void itemInit(OptionDataModel option) {
        if (option != null) {
            ArrayList<String> list = TheOptionModel.getOptionNameList(option);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (int i = 0; i < list.size(); i++) {
                model.addElement(list.get(i));
            }
            setModel(model);
        }
    }


}
