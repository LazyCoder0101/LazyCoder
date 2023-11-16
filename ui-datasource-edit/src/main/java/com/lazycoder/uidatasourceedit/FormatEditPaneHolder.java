package com.lazycoder.uidatasourceedit;

import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.MainDeafaultFormatCodePane;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.operation.MainFormatControlPane;
import com.lazycoder.uidatasourceedit.formatedit.additional.AdditionalEditPane;
import com.lazycoder.uidatasourceedit.formatedit.main.MainFormatEditPane;
import com.lazycoder.uidatasourceedit.formatedit.main.MainFormatPutPane;

import com.lazycoder.uidatasourceedit.formatedit.main.settype.MainSetCodeEditPane;
import javax.swing.JSplitPane;
import java.util.ArrayList;

public class FormatEditPaneHolder {

    public static MainFormatPutPane mainCodePane;

    public static JSplitPane splitPane;

    /**
     * 必填模板格式控制面板
     */
    public static MainFormatControlPane mainFormatControlPane = null;

    /**
     * 必填模板格式代码面板（默认不能删的那个）
     */
    public static MainDeafaultFormatCodePane mainFormatFunctionPane = null;

    /**
     * 其他编辑面板
     */
    public static AdditionalEditPane additionalEditPane = null;

    /**
     * 必填模板格式标记面板
     */
    public static MainFormatEditPane mainFormatEditPane = null;

    public static MainSetCodeEditPane mainSetCodeEditPane = null;

    /**
     * 获取使用范围,给可使用范围下拉框调用
     *
     * @return
     */
    public static ArrayList<UsingObject> getUsingRange() {
        ArrayList<UsingObject> list = new ArrayList<>();
        list.add(UsingObject.MAIN_USING_OBJECT);
        ArrayList<UsingObject> nList = FormatEditPaneHolder.additionalEditPane.getAdditionalTypeList();
        if (nList.size() > 0) {
            for (int i = 0; i < nList.size(); i++) {
                list.add(nList.get(i));
            }
        }
        return list;
    }

}
