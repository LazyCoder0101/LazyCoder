package com.lazycoder.uiutils.folder;

import com.lazycoder.uiutils.mycomponent.MyToggleButton;

import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class FoldButton extends MyToggleButton implements ItemSelectable {

    /**
     *
     */
    private static final long serialVersionUID = 332081120052094106L;

    // 事件处理器集合
    private ArrayList<ItemListener> listeners = new ArrayList<ItemListener>();

    // 是否展开
    private boolean expanded;

    /**
     * 是否展开
     *
     * @param expanded
     */
    public FoldButton(boolean expanded) {
        // TODO Auto-generated constructor stub
        this.expanded = expanded;
        // setSelected(expanded);
    }

    @Override
    public void doClick() {
        // TODO Auto-generated method stub
        super.doClick();
        if (isEnabled() == true) {
            ((FoldButtonUI) getUI()).setArmed(true);
            // 折叠或者展开
            setExpanded(!isExpanded());
            // 触发选择事件
            ItemEvent evt = new ItemEvent(this, isExpanded() ? 0 : 1, getText(),
                    isExpanded() ? ItemEvent.SELECTED : ItemEvent.DESELECTED);
            fireItemStateChanged(evt);
            // 获得焦点
            requestFocus();
        }
    }

    // 添加选择事件处理器
    @Override
    public void addItemListener(ItemListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    // 删除选择事件处理器
    @Override
    public void removeItemListener(ItemListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public void removeAllItemListener() {
        listeners.clear();
    }

    // 触发事件处理器
    @Override
    public void fireItemStateChanged(ItemEvent e) {
        for (ItemListener l : listeners) {
            l.itemStateChanged(e);
        }
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
//		setSelected(expanded);
        repaint();
    }

    /**
     * 点击鼠标前需要进行的操作
     *
     * @param expanded
     */
    public void doSomethingWhenMousePressed(boolean expanded) {
    }

}
