package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import com.lazycoder.uiutils.component.MultiSelectComboBox;
import com.lazycoder.utils.JsonUtil;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;

public class OperatingPropertyMutiCombobox extends MultiSelectComboBox<FunctionUseProperty> {

    /**
     * 该下拉框的选项要和ModuleSetOperatingModel的setProperty属性对应
     */
    private OperatingPropertyMutiCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setRenderer(new OperatingPropertyCellRenderer());
    }

    /**
     * 生成一个设置属性相关的下拉框
     *
     * @return
     */
    public static OperatingPropertyMutiCombobox creatSetPropertyCombobox() {
        OperatingPropertyMutiCombobox combobox = new OperatingPropertyMutiCombobox();
        DefaultComboBoxModel<FunctionUseProperty> model = new DefaultComboBoxModel<>();
        model.addElement(FunctionUseProperty.no);
        model.addElement(FunctionUseProperty.autoGenerateOnce);
        model.addElement(FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce);
        model.addElement(FunctionUseProperty.onlyBeUsedOnce);
        model.addElement(FunctionUseProperty.autoGenerateOnceAndCanNotDel);
        combobox.setModel(model);
        combobox.setSelectedItem(FunctionUseProperty.no);
        return combobox;
    }

    /**
     * 生成一个其他模板使用的设置属性相关的下拉框
     *
     * @return
     */
    public static OperatingPropertyMutiCombobox creatFormatSetPropertyCombobox() {
        OperatingPropertyMutiCombobox combobox = new OperatingPropertyMutiCombobox();
        DefaultComboBoxModel<FunctionUseProperty> model = new DefaultComboBoxModel<>();
        model.addElement(FunctionUseProperty.no);
        model.addElement(FunctionUseProperty.autoGenerateOnce);
        model.addElement(FunctionUseProperty.autoGenerateOnceCanOnlyBeUsedOnce);
        model.addElement(FunctionUseProperty.onlyBeUsedOnce);
        combobox.setModel(model);
        combobox.setSelectedItem(FunctionUseProperty.no);
        return combobox;
    }

    /**
     * 获取设置的属性参数
     *
     * @return
     */
    public String getSetPropertyParam() {
        ArrayList<Integer> list = new ArrayList<>();
        List<FunctionUseProperty> selectedItems = getSelectedItems();
        for (FunctionUseProperty functionUseProperty : selectedItems) {
            list.add(functionUseProperty.getSysDictionaryValue());
        }
        return JsonUtil.getJsonStr(list);
    }

    public void setSelectedSetPropertyParam(String setPropertyParam) {
        ArrayList<Integer> setPropertyList = JSON.parseObject(setPropertyParam, new TypeReference<ArrayList<Integer>>() {
        });
        FunctionUseProperty functionUsePropertyTemp;
        if (setPropertyList.size() > 0) {
            for (int i = 0; i < getModel().getSize(); i++) {
                functionUsePropertyTemp = getModel().getElementAt(i);
                for (Integer param : setPropertyList) {
                    if (functionUsePropertyTemp.getSysDictionaryValue() == param) {
                        addSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    @Deprecated
    @Override
    public void setSelectedIndex(int anIndex) {
        super.setSelectedIndex(anIndex);
    }

    @Deprecated
    @Override
    public int getSelectedIndex() {
        return super.getSelectedIndex();
    }


    @SuppressWarnings("rawtypes")
    class OperatingPropertyCellRenderer extends MultiSelectComboBox.MultiSelectComboBoxRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            String theText = null;
            if (value != null && value instanceof FunctionUseProperty) {
                FunctionUseProperty valueTemp = (FunctionUseProperty) value;
                theText = valueTemp.getShowText();
            }
            this.setText(theText);
            return this;
        }
    }

}