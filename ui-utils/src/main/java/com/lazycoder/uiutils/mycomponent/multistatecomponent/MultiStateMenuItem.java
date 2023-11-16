package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import javax.swing.JCheckBoxMenuItem;
import lombok.Getter;

/**
 * 有6种状态提供选择的菜单项（手动操作只能选前3种）
 */
public class MultiStateMenuItem extends JCheckBoxMenuItem implements MultiStateModelInterface {

    @Getter
    private String[] enableTips = new String[]{null, null, null, null, null, null};//这里 null 的数量需要和上面设置的状态的数量一致

    @Getter
    private String[] disenableTips = new String[]{null, null, null, null, null, null};//这里 null 的数量需要和上面设置的状态的数量一致

    public MultiStateMenuItem(String text) {
        init(text, null);
        setModel(new MultiStateModel());
        setFocusable(false);
        MultiStateMenuItemUI menuItemUI = new MultiStateMenuItemUI();
        setUI(menuItemUI);
    }

    public int getCurrentState() {
        return ((MultiStateModel) getModel()).getCurrentState();
    }

    public void setCurrentState(int state) {
        ((MultiStateModel) getModel()).setCurrentState(state);
    }

    /**
     * 设置某个状态使能的时候对应的提示
     *
     * @param currentState
     * @param text
     */
    public void setEnableToolTipText(int currentState, String text) {
        if (currentState < enableTips.length) {
            enableTips[currentState] = text;
        }
    }

    /**
     * 设置某个状态失能的时候对应的提示
     *
     * @param currentState
     * @param text
     */
    public void setDisenableToolTipText(int currentState, String text) {
        if (currentState < disenableTips.length) {
            disenableTips[currentState] = text;
        }
    }

    /**
     * 查看是不是某个状态
     *
     * @param state
     * @return
     */
    public boolean isState(int state) {
        return state == getCurrentState() ? true : false;
    }

}
