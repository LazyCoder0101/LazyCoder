package com.lazycoder.uidatasourceedit.moduleedit.toolbar;

import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.uidatasourceedit.moduleedit.ModuleEditComponentInterface;

import javax.swing.JRadioButton;


public class WhetherTheChoiceRadioButton extends JRadioButton implements ModuleEditComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -3664043469629588308L;

    public WhetherTheChoiceRadioButton(String text) {
        // TODO Auto-generated constructor stub
        super(text);
    }

    @Override
    public void displayModuleContents(ModuleInfo moduleInfo, Module module) {
        // TODO Auto-generated method stub
//        int result = module.getWhetherTheChoice();
//        if (Module.true_ == result) {
//            setSelected(true);
//        } else if (Module.false_ == result) {
//            setSelected(false);
//        }
    }

    @Override
    public void clearModuleContents() {
        // TODO Auto-generated method stub
    }

    /**
     * 获取参数，查看当前设置是否每次生成程序都要使用该模块
     *
     * @return
     */
    public int getWhetherTheChoice() {
        return isSelected() == true ? Module.TRUE_ : Module.FALSE_;
    }

}
