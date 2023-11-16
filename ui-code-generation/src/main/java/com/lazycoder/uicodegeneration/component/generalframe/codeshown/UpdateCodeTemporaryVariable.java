package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.format.LabelBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类临时存储所需要的标签以及期间游标位移，还有对代码生成模型的业务操作
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public class UpdateCodeTemporaryVariable {

    /**
     *
     */
    private static final long serialVersionUID = -1447616144102021523L;

    /**
     * 游标位移
     */
    private int cursorDisplacement = 0;

    private boolean editOrNot = false;    //该代码文件是否需要编辑，默认不需要

    private ArrayList<LabelBean> list = new ArrayList<>();

    /**
     * 向里面功能拓展组件添加代码时才用，用来临时记录对应的缩进符
     */
    private LinkedHashMap<String, String> indentList = new LinkedHashMap<String, String>();

    /**
     * 添加代码的时候，才返回该参数，插入代码时，对应应该插入的填写位置
     */
    private PathFind codePathFind = null;

    public void add(LabelBean labelBean) {
        list.add(labelBean);
    }

    public void addAll(UpdateCodeTemporaryVariable updateCodeTemporaryVariable) {
        list.addAll(updateCodeTemporaryVariable.getList());
    }

    /**
     * 放置功能拓展标签对应的缩进符
     * @param key
     * @param indent
     */
    public void putIndent(String key, String indent) {
        indentList.put(key, indent);
    }

    /**
     * 获取功能拓展标签对应的缩进符
     * @param key
     * @return
     */
    public String getIndent(String key) {
        return indentList.get(key);
    }

}
