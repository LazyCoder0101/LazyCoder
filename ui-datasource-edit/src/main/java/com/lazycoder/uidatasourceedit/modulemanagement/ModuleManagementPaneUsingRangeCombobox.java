package com.lazycoder.uidatasourceedit.modulemanagement;

import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.uidatasourceedit.FormatEditPaneHolder;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class ModuleManagementPaneUsingRangeCombobox extends JComboBox<UsingObject> {

    public static final UsingObject ALL_USING_OBJECT = null;
    /**
     *
     */
    private static final long serialVersionUID = 3666315119382139193L;

    private final static String ALL_SHOW_TEXT = HTMLText.createHtmlContent("全部", HtmlPar.SBLUE, true);

    @SuppressWarnings("unchecked")
    public ModuleManagementPaneUsingRangeCombobox() {
        // TODO Auto-generated constructor stub
        setRenderer(new ModuleManagementPaneUsingRangeComboboxCellRenderer());
        updateItems();
        setSelectedIndex(0);
        addPopupMenuListener(popupMenuListener);
    }

    /**
     * 获取查询对应使用范围所传的参数
     *
     * @return
     */
    public UsingObject getQueryParam() {
        return (UsingObject) getSelectedItem();
    }

    /**
     * 获取使用范围
     *
     * @return
     */
    public void updateItems() {
        UsingObject selectedUsingObject = (UsingObject) getModel().getSelectedItem();
        removeAllItems();
        ArrayList<UsingObject> list = FormatEditPaneHolder.getUsingRange();
        DefaultComboBoxModel<UsingObject> model = new DefaultComboBoxModel<>();
        model.addElement(ALL_USING_OBJECT);
        if (list != null) {
            for (UsingObject usingObject : list) {
                model.addElement(usingObject);
            }
        }
        setModel(model);
        setSelectedItem(selectedUsingObject);
    }

    private void setSelectedItem(UsingObject selectedUsingObject) {
        // TODO Auto-generated method stub
        UsingObject tempObject;
        if (selectedUsingObject != null) {
            for (int a = 0; a < getModel().getSize(); a++) {// 在使用范围一个个查
                tempObject = getModel().getElementAt(a);
                if (tempObject == ALL_USING_OBJECT) {
                    if (selectedUsingObject == ALL_USING_OBJECT) {
                        setSelectedIndex(0);
                        break;
                    }

                } else if (tempObject.getType() == selectedUsingObject.getType()) {
                    if (tempObject.getType() == UsingObject.MAIN_TYPE) {
                        setSelectedIndex(a);
                        break;
                    } else if (tempObject.getType() == UsingObject.ADDITIONAL_TYPE) {
                        if (tempObject.getSerial() == selectedUsingObject.getSerial()) {
                            setSelectedIndex(a);
                            break;
                        }
                    }
                }
            }
        }
    }

    private PopupMenuListener popupMenuListener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            updateItems();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }
    };


    //此类用于显示提示信息
    @SuppressWarnings("rawtypes")
    class ModuleManagementPaneUsingRangeComboboxCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        JLabel renderer;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {

            // 下拉列表每个选项显示的就是这个
            renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);

            if (value == ALL_USING_OBJECT) {
                renderer.setText(ALL_SHOW_TEXT);
            } else if (value instanceof UsingObject) {
                UsingObject valueTemp = (UsingObject) value;
                String thisShowText = valueTemp.getShowName();
                renderer.setText(thisShowText);
            }
            return renderer;
        }
    }


}
