package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class UseSettingMenu extends JMenu implements ModuleUseSetting, ModuleEditComponentInterface {

    private ModuleUseSettingMenuItem userShieldingMenuItem;

    public UseSettingMenu() {
        super();

        //这里添加的菜单项需要按照 ModuleUseSetting 里面的值按顺序进行添加
//        mustUseMenuItem = new ModuleUseSettingMenuItem("生成程序时都要使用该模块", ModuleUseSetting.MUST_USE);
//        mustUseMenuItem.addActionListener(actionListener);
//        add(mustUseMenuItem);

        userShieldingMenuItem = new ModuleUseSettingMenuItem("对用户屏蔽（仅作其他模块需要时自动选择使用）", ModuleUseSetting.USER_SHIELDING);
        userShieldingMenuItem.addActionListener(actionListener);
        add(userShieldingMenuItem);

        setForeground(new Color(160, 32, 240));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boolean flag = DataSourceEditHolder.moduleEditPane.checkModuleState();
                if (flag == true) {
                    doClick();
                }
            }
        });
        Dimension dimension = new Dimension(160, 30);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
    }

    public void setUseSettingParam(Module module) {
        ArrayList<Integer> selectValueList = new ArrayList<>();
        ModuleUseSettingMenuItem item;
        Component[] items = getMenuComponents();
        for (Component component : items) {
            if (component instanceof ModuleUseSettingMenuItem) {
                item = (ModuleUseSettingMenuItem) component;
                if (item.isSelected()) {
                    selectValueList.add(item.getUseSettingValue());
                }
            }
        }

        ModuleStaticMethod.setUseSettingValueParam(module, selectValueList);
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        ModuleUseSettingMenuItem item;
        ArrayList<Integer> values = ModuleStaticMethod.getUseSettingValues(module);
        Component[] items = getMenuComponents();
        for (Integer value : values) {
            if (value != null) {
                for (Component component : items) {
                    if (component instanceof ModuleUseSettingMenuItem) {
                        item = (ModuleUseSettingMenuItem) component;
                        if (item.getUseSettingValue()==value){
                            item.setSelected(true);
                            break;
                        }
                    }
                }
            }
        }
        showText();
    }

    @Override
    public void clearModuleContents() {
        ModuleUseSettingMenuItem item;
        Component[] items = getMenuComponents();
        for (Component component : items) {
            if (component instanceof ModuleUseSettingMenuItem) {
                item = (ModuleUseSettingMenuItem) component;
                item.setSelected(false);
            }
        }
        showText();
    }

    private void showText() {
        ArrayList<String> selectedTextList = new ArrayList<>();

        ModuleUseSettingMenuItem item;
        Component[] items = getMenuComponents();
        for (Component component : items) {
            if (component instanceof ModuleUseSettingMenuItem) {
                item = (ModuleUseSettingMenuItem) component;
                if (item.isSelected()) {
                    selectedTextList.add(item.getText());
                }
            }
        }
        String text = StringUtils.join(selectedTextList, "、");
        setText(text);

//        if (selectedTextList.size() > 0) {
//            setToolTipText(HTMLText.createHtmlContent(text, HtmlPar.maroon, false));
//        } else {
//            setToolTipText(null);
//        }
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showText();
        }
    };

    class ModuleUseSettingMenuItem extends JCheckBoxMenuItem implements ModuleUseSetting {

        @Getter
        private int useSettingValue = 0;

        public ModuleUseSettingMenuItem(String text, int useSettingValue) {
            super(text);
            this.useSettingValue = useSettingValue;
        }

    }

}

//public class UseSettingCombobox<E> extends MultiSelectComboBox<String> implements ModuleUseSetting, ModuleEditComponentInterface {
//
//    public UseSettingCombobox() {
//        super();
//        setRenderer(new UseSettingCellRenderer());
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
//        model.addElement("生成程序时都要使用该模块");
//        model.addElement("对用户屏蔽（仅作其他模块需要时自动选择使用）");
//        setModel(model);
//    }
//
//    public void setUseSettingParam(Module module) {
//        ModuleStaticMethod.setUseSettingValueParam(module, getSelectedSortedIndexs());
//    }
//
//    @Override
//    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
//        ArrayList<Integer> values = ModuleStaticMethod.getUseSettingValues(module);
//        for (Integer value : values) {
//            if (value != null) {
//                addSelectedIndex(value);
//            }
//        }
//    }
//
//    @Override
//    public void clearModuleContents() {
//        clearSelectedIndexs();
//    }
//
//    class UseSettingCellRenderer implements ListCellRenderer {
//
//        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
//
//        @Override
//        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
//                                                      boolean cellHasFocus) {
//
//            // 下拉列表每个选项显示的就是这个
//            JComponent theRenderer = (JComponent) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//            theRenderer.setToolTipText((String) value);
//            return theRenderer;
//        }
//
//    }
//
//}
