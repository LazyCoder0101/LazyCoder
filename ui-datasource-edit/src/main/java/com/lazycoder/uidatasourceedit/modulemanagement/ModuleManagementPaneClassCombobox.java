package com.lazycoder.uidatasourceedit.modulemanagement;

import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Component;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/**
 * 模块管理功能分类显示下拉框
 *
 * @author admin
 */
public class ModuleManagementPaneClassCombobox extends JComboBox<String[]> {

    /**
     *
     */
    private static final long serialVersionUID = 3666315119382139193L;

    private final static int THE_SHOW_TEXT = 0, THE_ITEM = 1;

    private final static String ALL_SHOW_TEXT =HTMLText.createHtmlContent("全部",HtmlPar.SBLUE,true);

    @SuppressWarnings("unchecked")
    public ModuleManagementPaneClassCombobox() {
        // TODO Auto-generated constructor stub
        setRenderer(new ModuleManagementPaneClassComboboxCellRenderer());
        updateItems();
    }

    public void updateItems() {
        removeAllItems();
        List<TheClassification> list = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
        DefaultComboBoxModel<String[]> model = new DefaultComboBoxModel<>();
        model.addElement(new String[]{ALL_SHOW_TEXT, null});
        for (TheClassification classification : list) {
            model.addElement(new String[]{classification.getClassName(), classification.getClassName()});
        }
        setModel(model);
        if (list.size() > 0) {
            setSelectedIndex(0);
        }
    }

    @Override
    public String getSelectedItem() {
        // TODO Auto-generated method stub
        String[] temp = (String[]) super.getSelectedItem();
        return temp[THE_ITEM];
    }

    //此类用于显示提示信息
    @SuppressWarnings("rawtypes")
    class ModuleManagementPaneClassComboboxCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        JLabel renderer;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {

            // 下拉列表每个选项显示的就是这个
            renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
            if (value instanceof String[]) {
                String[] valueTemp = (String[]) value;
//				if (allShowText.equals(valueTemp[theShowText])){
//					renderer.setText(allShowText);
//				}else {
                String thisShowText = valueTemp[THE_SHOW_TEXT];
                renderer.setText(thisShowText);
//				}
            } else if (value == null) {//该组件不能传空值，空值默认为选择全部
                renderer.setText(ALL_SHOW_TEXT);
            }
            return renderer;
        }
    }


}
