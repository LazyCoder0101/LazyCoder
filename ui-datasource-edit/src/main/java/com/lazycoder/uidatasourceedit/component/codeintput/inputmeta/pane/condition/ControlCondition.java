package com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 如果根据不同类型的控制面板有不同的组件添加或其他区别，在此填写对应参数
 *
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlCondition {

    private boolean infrequentlyUsedSettingState = true;//不常用组件的使用

    private boolean delContentChooseFromDBMenu = true;//是否显示从数据库删除选项的菜单项

    private boolean updateContentChooseMenu = true;//是否显示更改选项内容的菜单项

    private boolean correspondingAdditionalDefaultFileState = false;//是否显示对应可选模板的默认代码文件名这个组件


    //备注：由于目非常用组件和功能拓展组件里面对应的控制面板不允许出现更改选项和删除选项的菜单项，避免组件当前的更改过大，导致对应存储数据模型数据变化大，和当前实时更改的内容会起冲突
}
