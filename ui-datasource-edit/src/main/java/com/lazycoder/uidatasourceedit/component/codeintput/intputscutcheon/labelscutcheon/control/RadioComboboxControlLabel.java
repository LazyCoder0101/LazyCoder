package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.database.model.TheOptionModel;
import com.lazycoder.service.OptionDataModelTempHolder;
import com.lazycoder.service.vo.base.BaseCodePane;
import com.lazycoder.service.vo.datasourceedit.general.AbstractEditContainerModel;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.AbstractRadioComboboxLabel;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import lombok.Setter;


public class RadioComboboxControlLabel extends AbstractRadioComboboxLabel<String> implements ControlLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = -6350860319117118458L;

    @Setter
    private ContentChooseControl control = null;

    public RadioComboboxControlLabel(String[] items) {
        super(items);
        // TODO Auto-generated constructor stub
    }

    public RadioComboboxControlLabel(ContentChooseControl control) {
        // TODO Auto-generated constructor stub
        super(new String[]{});
        this.control = control;
        super.setName(control.getThisName());

        OptionDataModel optionDataModel =OptionDataModelTempHolder.getOption(control.getOptionId());
        if(optionDataModel!=null) {
            setToolTipText(
                    ContentChooseControl.getShowNameForOprating(control, optionDataModel));
            itemInit(optionDataModel);

            for (int i = 0; i < control.getSelectList().size(); i++) {
                int selectedTemp = control.getSelectList().get(i);
                if (selectedTemp < optionDataModel.getValueNum()) {
                    setSelectedIndex(selectedTemp);
                } else {
                    String text = "有个代码操作面板，里面的一个个\"" + control.getOptionName() + "\"单选内容选择组件默认选第" + (selectedTemp + 1) + "个选项，可这个选项已经被删了！	(✪ω✪)";
                    String logtext = getClass() + "（生成组件异常）————\"" + control.getOptionName() + "\"这个选项，当前有" + optionDataModel.getValueNum() + "个选项值，有个单选组件却默认选第" + (selectedTemp + 1) + "个";
                    DataSourceEditHolder.errorLogging(text, logtext);

                    setSelectedIndex(0);
                }
            }
        }
        setTheSize();
        addPopupMenuListener(listener);
    }

    @Override
    protected void init() {
        super.init();
        setRenderer(new CustomComboBoxTextSizeRenderer(6));
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

    /**
     * 更改选项
     */
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

    /**
     * 更改选项值，当选项值发生更改时提供给外部调用
     */
    public void updateItems(OptionDataModel option) {
        int selectedIndex = getSelectedIndex();
        this.control.setOptionName(option.getOptionName());
        itemInit(option);
        if (selectedIndex >= 0) {
            if (selectedIndex >= option.getValueNum()) {
                String text = "有个代码操作面板，里面有个\"" + control.getOptionName() + "\"单选内容选择组件默认选第" + (selectedIndex + 1) + "个选项，可这个选项已经被删了！	(✪ω✪)";
                String logtext = getClass() + "（更改选项异常）————\"" + control.getOptionName() + "\"这个选项，当前有" + option.getValueNum() + "个选项值，有个单选组件却默认选第" + (selectedIndex + 1) + "个";
                DataSourceEditHolder.errorLogging(text, logtext);

                setSelectedIndex(0);
            } else {
                setSelectedIndex(selectedIndex);
            }
        } else {
            setSelectedIndex(0);
        }
        updateCodeComponetValue();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        control.setThisName(name);
    }

    @Override
    public ContentChooseControl property() {
        // TODO Auto-generated method stub
        return control;
    }

    @Override
    public ContentChooseControl getControl() {
        return control;
    }

    @Override
    public void setTheSize() {
        // TODO Auto-generated constructor stub
        int lenth = 0;
        String temp;
        for (int i = 0; i < getModel().getSize(); i++) {
            temp = getModel().getElementAt(i);
            if (temp.length() > lenth) {
                lenth = temp.length();
            }
        }
        Dimension dd = new Dimension(lenth * 16 + 50, 30);
        if (lenth > 5) {
            dd = new Dimension(120, 30);
        }
        this.setPreferredSize(dd);
        this.setMinimumSize(dd);
        this.setMaximumSize(dd);
    }

    /**
     * 更改对应代码组件的值
     */
    public void updateCodeComponetValue() {
        PassingComponentParams params = getPassingComponentParams();
        if (params != null && null != params.getGeneralModel()) {
            int selectIndex = getSelectedIndex();
            control.getSelectList().clear();
            control.getSelectList().add(getSelectedIndex());

            AbstractEditContainerModel model = params.getGeneralModel();
            if (model != null) {
                ArrayList<BaseCodePane> list = model.getCodePaneList();
                for (BaseCodePane baseCodePane : list) {
                    baseCodePane.updateRadioComboboxShowValue(control, selectIndex);
                }
            }
        }
    }

    @Override
    public void setSelectedIndex(int i) {
        super.setSelectedIndex(i);
        control.getSelectList().clear();
        control.getSelectList().add(getSelectedIndex());
    }

}