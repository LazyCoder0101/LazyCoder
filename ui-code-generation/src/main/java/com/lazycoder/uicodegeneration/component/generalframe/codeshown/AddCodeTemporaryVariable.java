package com.lazycoder.uicodegeneration.component.generalframe.codeshown;

import com.lazycoder.uicodegeneration.CodeListCarrierInterface;
import com.lazycoder.uicodegeneration.PathFind;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 该类临时存储所需要的标签以及期间游标位移，还有对代码生成模型的业务操作
 *
 * @author admin
 */
@Data
@NoArgsConstructor
public class AddCodeTemporaryVariable {

    /**
     *
     */
    private static final long serialVersionUID = -1447616144102021523L;

    /**
     * 游标位移
     */
    private int cursorDisplacement = 0;

    private boolean editOrNot = false;    //该代码文件是否需要编辑，默认不需要

    private ArrayList<CodeListCarrierInterface> list = new ArrayList<>();

    /**
     * 添加代码的时候，才返回该参数，插入代码时，对应应该插入的填写位置
     */
    private PathFind codePathFind = null;

    public void add(CodeListCarrierInterface codeListCarrier) {
        list.add(codeListCarrier);
    }

    public void addAll(AddCodeTemporaryVariable addCodeTemporaryVariable) {
        list.addAll(addCodeTemporaryVariable.getList());
    }

}
