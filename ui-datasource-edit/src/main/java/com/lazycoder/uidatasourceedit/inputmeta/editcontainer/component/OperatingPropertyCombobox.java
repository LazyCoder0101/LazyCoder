package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import java.awt.Component;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class OperatingPropertyCombobox extends JComboBox<FunctionUseProperty> {


    /**
     *
     */
    private static final long serialVersionUID = 4791335183023951261L;

    /**
     * 该下拉框的选项要和ModuleSetOperatingModel的setProperty属性对应
     */
    private OperatingPropertyCombobox() {
        // TODO Auto-generated constructor stub
        super();
        setRenderer(new OperatingPropertyCellRenderer());
    }

    public void clearInit(){
        setSelectedItem(FunctionUseProperty.no);
    }

    /**
     * 生成一个设置属性相关的下拉框
     *
     * @return
     */
    public static OperatingPropertyCombobox creatSetPropertyCombobox() {
        OperatingPropertyCombobox combobox = new OperatingPropertyCombobox();
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
    public static OperatingPropertyCombobox creatFormatSetPropertyCombobox() {
        OperatingPropertyCombobox combobox = new OperatingPropertyCombobox();
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
     * 生成一个业务逻辑相关功能使用的属性下拉框
     *
     * @return
     */
    public static OperatingPropertyCombobox creatBusinessPropertyCombobox() {
        OperatingPropertyCombobox combobox = new OperatingPropertyCombobox();
        DefaultComboBoxModel<FunctionUseProperty> model = new DefaultComboBoxModel<>();
        model.addElement(FunctionUseProperty.no);
        model.addElement(FunctionUseProperty.onlyBeAddedToBusinessSchtcheonOfATemplate);
        model.addElement(FunctionUseProperty.onlyAddedItOnceToBusinessSchtcheonOfATemplate);
        model.addElement(FunctionUseProperty.onlyBeAddedToFunctionAddLabel);
        combobox.setModel(model);
        combobox.setSelectedItem(FunctionUseProperty.no);
        return combobox;
    }

    @Override
    public FunctionUseProperty getSelectedItem() {
        // TODO Auto-generated method stub
        return (FunctionUseProperty) super.getSelectedItem();
    }

    public void setSelectedItem(FunctionUseProperty functionUseProperty) {
        // TODO Auto-generated method stub
        super.setSelectedItem(functionUseProperty);
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
    class OperatingPropertyCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        JLabel renderer;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String theText = null;
            if (value instanceof FunctionUseProperty) {
                FunctionUseProperty valueTemp = (FunctionUseProperty) value;
                theText = valueTemp.getShowText();
            }
            renderer.setText(theText);
            return renderer;
        }
    }

}