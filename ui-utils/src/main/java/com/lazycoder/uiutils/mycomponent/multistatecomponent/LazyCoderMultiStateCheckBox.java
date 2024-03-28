package com.lazycoder.uiutils.mycomponent.multistatecomponent;

/**
 * 围绕系统业务功能再封装出来的一些专有功能
 */
public class LazyCoderMultiStateCheckBox extends MultiStateCheckBox implements LazyCoderMultiStateComponentInterface{

    public LazyCoderMultiStateCheckBox(String text) {
        super(text);
    }

    @Override
    public void setParasiticModule(String tipText) {
        //设置为【预定选择】，然后失能
        setEnabled(false);
        setDisenableToolTipText(MARK_PRE_SELECTED, tipText);
        setCurrentState(MARK_PRE_SELECTED);
    }


    public static void setState(LazyCoderMultiStateCheckBox srcLazyCoderMultiStateCheckBox,LazyCoderMultiStateCheckBox toLazyCoderMultiStateCheckBox){
        toLazyCoderMultiStateCheckBox.setCurrentState(toLazyCoderMultiStateCheckBox.getCurrentState());
        if (srcLazyCoderMultiStateCheckBox.isParasiticModule()){
            toLazyCoderMultiStateCheckBox.setParasiticModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_PRE_SELECTED]);
        }
        if (srcLazyCoderMultiStateCheckBox.isConflictModule()){
            toLazyCoderMultiStateCheckBox.setConflictModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_PRE_NULL]);
        }
        if (srcLazyCoderMultiStateCheckBox.isPreUseModule()){
            toLazyCoderMultiStateCheckBox.setPreUseModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_SELECTED]);
        }
        if (srcLazyCoderMultiStateCheckBox.isCannotUseModule()){
            toLazyCoderMultiStateCheckBox.setCannotUseModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_NO]);
        }
        if (srcLazyCoderMultiStateCheckBox.isAlsoUseModule()){
            toLazyCoderMultiStateCheckBox.setAlsoUseModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_PRE_SELECTED]);
        }
        if (srcLazyCoderMultiStateCheckBox.isPreFirUseModule()){
            toLazyCoderMultiStateCheckBox.setPreFirUseModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_SELECTED]);
        }
        if (srcLazyCoderMultiStateCheckBox.isPreCannotUseModule()){
            toLazyCoderMultiStateCheckBox.setPreCannotUseModule(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_NO]);
        }
        if (srcLazyCoderMultiStateCheckBox.isModuleSelectedNull()){
            toLazyCoderMultiStateCheckBox.setModuleSelectedNull(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_NULL]);
        }
        if (srcLazyCoderMultiStateCheckBox.isCannotChooseManual()){
            toLazyCoderMultiStateCheckBox.setCannotChooseManual(srcLazyCoderMultiStateCheckBox.getDisenableTips()[MARK_NULL]);
        }
    }

    @Override
    public boolean isParasiticModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_PRE_SELECTED && isEnabled() == false) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setConflictModule(String tipText) {
        //设置为【预定不选】，然后失能
        setEnabled(false);
        setDisenableToolTipText(MARK_PRE_NULL, tipText);
        setCurrentState(MARK_PRE_NULL);
    }

    @Override
    public boolean isConflictModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_PRE_NULL && isEnabled() == false) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setPreUseModule(String tipText) {
        //设置为【选择】，然后使能
        setEnabled(true);
        setEnableToolTipText(MARK_SELECTED, tipText);
        setCurrentState(MARK_SELECTED);
    }

    @Override
    public boolean isPreUseModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_SELECTED && isEnabled() == true) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setCannotUseModule(String tipText) {
        //设置为【禁止】，然后使能
        setEnabled(true);
        setEnableToolTipText(MARK_NO, tipText);
        setCurrentState(MARK_NO);
    }

    @Override
    public boolean isCannotUseModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_NO && isEnabled() == true) {
            flag = true;
        }
        return flag;
    }


    @Override
    public void setAlsoUseModule(String tipText) {
        //设置为【预定选择】，然后使能
        setEnabled(true);
        setEnableToolTipText(MARK_PRE_SELECTED, tipText);
        setCurrentState(MARK_PRE_SELECTED);
    }

    @Override
    public boolean isAlsoUseModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_PRE_SELECTED && isEnabled() == true) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setAlsoDisableModule(String tipText) {
        //设置为【预定禁止】，然后使能
        setEnabled(true);
        setEnableToolTipText(MARK_PRE_SELECTED_NO, tipText);
        setCurrentState(MARK_PRE_SELECTED_NO);
    }

    @Override
    public boolean isAlsoDisableModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_PRE_SELECTED_NO && isEnabled() == true) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setPreFirUseModule(String tipText) {
        //设置为【选中】，然后失能
        setEnabled(false);
        setDisenableToolTipText(MARK_SELECTED, tipText);
        setCurrentState(MARK_SELECTED);
    }

    @Override
    public boolean isPreFirUseModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_SELECTED && isEnabled() == false) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setPreCannotUseModule(String tipText) {
        //设置为【禁止】，然后失能
        setEnabled(false);
        setDisenableToolTipText(MARK_NO, tipText);
        setCurrentState(MARK_NO);
    }

    @Override
    public boolean isPreCannotUseModule() {
        boolean flag = false;
        if (getCurrentState() == MARK_NO && isEnabled() == false) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setModuleSelectedNull(String tipText) {
        //设置为【不选】，然后使能
        setEnabled(true);
        setEnableToolTipText(MARK_NULL, tipText);
        setCurrentState(MARK_NULL);
    }

    @Override
    public boolean isModuleSelectedNull() {
        boolean flag = false;
        if (getCurrentState() == MARK_NULL && isEnabled() == true) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void setCannotChooseManual(String tipText) {
        //设置为【不选】，然后失能
        setEnabled(false);
        setDisenableToolTipText(MARK_NULL, tipText);
        setCurrentState(MARK_NULL);
    }

    @Override
    public boolean isCannotChooseManual() {
        boolean flag = false;
        if (getCurrentState() == MARK_NULL && isEnabled() == false) {
            flag = true;
        }
        return flag;
    }




}
