package com.lazycoder.uicodegeneration.generalframe.palette;

import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.formodule.ModuleStaticMethod;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.uicodegeneration.generalframe.operation.AbstractFormatControlPane;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FeatureSelectedTabPane extends JTabbedPane {

    private FeatureSelectedPane userSelectctedPane, sysSelectedPane;

    private JScrollPane userSelectctedJSP, sysSelectedJSP;

    @Getter
    private AbstractFormatControlPane formatControlPane;

    public FeatureSelectedTabPane() {
        super();
        userSelectctedPane = new FeatureSelectedPane(this);
        userSelectctedJSP = new JScrollPane(userSelectctedPane);
        addTab("用户选择", userSelectctedJSP);

        sysSelectedPane = new FeatureSelectedPane(this);
        sysSelectedJSP = new JScrollPane(sysSelectedPane);
        addTab("系统设置", sysSelectedJSP);
    }

    public void clearContent() {
        userSelectctedPane.clearContent();
        sysSelectedPane.clearContent();
    }

    /**
     * 检查是否为对用户屏蔽的属性
     *
     * @param module
     * @return
     */
    private boolean check_user_shielding_or_not(Module module) {
        boolean flag = false;
        ArrayList<Integer> useSettingValues = ModuleStaticMethod.getUseSettingValues(module);
        if (useSettingValues.contains(ModuleUseSetting.USER_SHIELDING)) {//对用户屏蔽
            flag = true;
        }
        return flag;
    }

    /**
     * 更新模块列表
     *
     * @param formatControlPane
     */
    public void updateModuleList(AbstractFormatControlPane formatControlPane) {
        this.formatControlPane = formatControlPane;
        clearContent();
        addModuleList(formatControlPane.getUseModuleRelatedParamList());
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.validate();
        }
    }

    /**
     * 添加模块
     *
     * @param list
     */
    private void addModuleList(ArrayList<ModuleRelatedParam> list) {
        ArrayList<ModuleRelatedParam> userSelectctedList = new ArrayList<>(),
                sysSelectedList = new ArrayList<>();
        for (ModuleRelatedParam moduleRelatedParam : list) {
            if (check_user_shielding_or_not(moduleRelatedParam.getModule())) {//设置了对用户屏蔽属性
                sysSelectedList.add(moduleRelatedParam);
            } else {
                userSelectctedList.add(moduleRelatedParam);
            }
        }
        userSelectctedPane.addModuleList(userSelectctedList);
        sysSelectedPane.addModuleList(sysSelectedList);
    }


}
