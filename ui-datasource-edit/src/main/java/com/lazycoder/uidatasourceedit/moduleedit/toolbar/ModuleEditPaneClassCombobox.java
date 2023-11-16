package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import com.lazycoder.database.model.TheClassification;
import com.lazycoder.service.service.SysService;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


/**
 * 模块编辑的分类显示下拉框
 *
 * @author admin
 */
public class ModuleEditPaneClassCombobox extends JComboBox<String> {

    /**
     *
     */
    private static final long serialVersionUID = -1626160163547241950L;

    public ModuleEditPaneClassCombobox() {
        // TODO Auto-generated constructor stub
        updateItems();
    }

    /**
     * 选择了别的分类以后，让模块下拉框显示该分类的模块
     */
    private PopupMenuListener listener = new PopupMenuListener() {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            // TODO Auto-generated method stub
            String className = (String) getSelectedItem();
            if (null != className) {
                if ("".equals(className) == false) {
//                    DataSourceEditHolder.className = className;
//                    if (ModuleEditPaneHolder.moduleCombobox != null) {
//                        ModuleEditPaneHolder.moduleCombobox.updateItems();
//                    }
                }
            }
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            // TODO Auto-generated method stub
        }
    };

    /**
     * 更新下拉框内容
     */
    public void updateItems() {
        removePopupMenuListener(listener);
        removeAllItems();
        List<TheClassification> list = SysService.CLASSIFICATION_SERVICE.getAllClassificationList();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 0; i < list.size(); i++) {
            model.addElement(list.get(i).getClassName());
        }
        setModel(model);
//        String className = DataSourceEditHolder.className;
//        if ("".equals(className) == false) {// 如果当前有选中那个分类
//            setSelectedItem(className);
//        }
        addPopupMenuListener(listener);
    }

}
