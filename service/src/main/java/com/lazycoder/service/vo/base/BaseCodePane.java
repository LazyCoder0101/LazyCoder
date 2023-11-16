package com.lazycoder.service.vo.base;

import com.lazycoder.database.model.OptionDataModel;
import com.lazycoder.service.vo.BasePane;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import com.lazycoder.service.vo.element.lable.control.ContentChooseControl;
import java.util.ArrayList;
import javax.swing.border.Border;

public interface BaseCodePane extends BasePane {

    /**
     * 更新菜单
     */
    public void updateMenu();

    /**
     * 删除标签
     *
     * @param labelType
     * @param componentName
     */
    public void delLabel(String labelType, String componentName);

    /**
     * 删除内容选择标签
     */
    public void delContentChooseLable(String optionId, int useNumbered);

    /**
     * 删除内容选择标签
     */
    public void delContentChooseLable(String optionId);

    /**
     * 更改单选组件的显示值
     *
     * @param contentChooseControl
     */
    public void updateRadioComboboxShowValue(ContentChooseControl contentChooseControl, int selectIndex);

    /**
     * 更改多选组件的显示值
     *
     * @param chooseControl
     */
    public void updateMutiComboboxShowValue(ContentChooseControl chooseControl, ArrayList<Integer> selectList);

    /**
     * 更改选项组件里面的可选值
     *
     * @param option
     */
    public void updateComboboxItems(OptionDataModel option);

    /**
     * 让对应的标签组件响应
     * @param lableElement
     * @param border
     */
    public void makeCorrespondingLabelScutcheonRespond(BaseLableElement lableElement, Border border);

}
