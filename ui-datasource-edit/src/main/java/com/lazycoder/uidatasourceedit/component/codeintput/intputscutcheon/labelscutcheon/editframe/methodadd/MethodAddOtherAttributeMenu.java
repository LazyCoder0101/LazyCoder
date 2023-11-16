package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.methodadd;

import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import com.lazycoder.service.ModuleUseSetting;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import lombok.Getter;

public class MethodAddOtherAttributeMenu extends JMenu {

    private MethodAddOtherAttributeMenuItem noneMenuItem, addToTheCorrespondingMarkMenuItem, stepByStepToFindCorrespondingMenuItem;

    private ButtonGroup buttonGroup;

    public MethodAddOtherAttributeMenu() {
        super();

        buttonGroup = new ButtonGroup();

        noneMenuItem = new MethodAddOtherAttributeMenuItem("无", CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE);
        noneMenuItem.addActionListener(actionListener);
        add(noneMenuItem);
        buttonGroup.add(noneMenuItem);

        stepByStepToFindCorrespondingMenuItem = new MethodAddOtherAttributeMenuItem("逐级往上，添加至符合的组件", CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE);
        stepByStepToFindCorrespondingMenuItem.addActionListener(actionListener);
        add(stepByStepToFindCorrespondingMenuItem);
        buttonGroup.add(stepByStepToFindCorrespondingMenuItem);

        addToTheCorrespondingMarkMenuItem = new MethodAddOtherAttributeMenuItem("添加到第1级符合标记组件", CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE);
        addToTheCorrespondingMarkMenuItem.addActionListener(actionListener);
        add(addToTheCorrespondingMarkMenuItem);
        buttonGroup.add(addToTheCorrespondingMarkMenuItem);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doClick();
            }
        });

        setForeground(new Color(160, 32, 240));
        Dimension dimension = new Dimension(160, 30);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
    }

    /**
     * 根据属性选择对应的菜单项
     *
     * @param otherAttribute
     */
    public void setOtherAttribute(int otherAttribute) {
        if (otherAttribute == CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE) {
            noneMenuItem.setSelected(true);
        } else if (otherAttribute == CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE) {
            addToTheCorrespondingMarkMenuItem.setSelected(true);
        } else if (otherAttribute == CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE) {
            stepByStepToFindCorrespondingMenuItem.setSelected(true);
        }
    }

    /**
     * 获取当前选中的属性
     *
     * @return
     */
    public int getSelectedOtherAttribute() {
        int otherAttributeValue = CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE;
        if (noneMenuItem.isSelected()) {
            otherAttributeValue = CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE;
        } else if (addToTheCorrespondingMarkMenuItem.isSelected()) {
            otherAttributeValue = CommandAddRelatedAttribute.ADD_TO_THE_FIRST_CORRESPONDING_MARK_OTHER_ATTRIBUTE;
        } else if (stepByStepToFindCorrespondingMenuItem.isSelected()) {
            otherAttributeValue = CommandAddRelatedAttribute.STEP_BY_STEP_TO_FIND_CORRESPONDING_MARK_OTHER_ATTRIBUTE;
        }
        return otherAttributeValue;
    }

    public void showText() {
        String selectedText = null;

        MethodAddOtherAttributeMenuItem item;
        Component[] items = getMenuComponents();
        for (Component component : items) {
            if (component instanceof MethodAddOtherAttributeMenuItem) {
                item = (MethodAddOtherAttributeMenuItem) component;
                if (item.isSelected()) {
                    selectedText = item.getText();
                    break;
                }
            }
        }
        setText(selectedText);
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showText();
        }
    };

    class MethodAddOtherAttributeMenuItem extends JCheckBoxMenuItem implements ModuleUseSetting {

        @Getter
        private int otherAttributeValue = CommandAddRelatedAttribute.NONE_OTHER_ATTRIBUTE;

        public MethodAddOtherAttributeMenuItem(String text, int otherAttributeValue) {
            super(text);
            this.otherAttributeValue = otherAttributeValue;
        }

    }

}

