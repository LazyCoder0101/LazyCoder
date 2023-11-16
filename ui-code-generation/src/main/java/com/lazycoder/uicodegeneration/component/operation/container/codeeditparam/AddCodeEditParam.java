package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.lazycodercommon.vo.CommandAddRelatedAttribute;
import lombok.Data;

@Data
public class AddCodeEditParam extends CodeEditParam {


    /**
     * 本次进行代码编辑的一些相关属性，构造的时候一定要根据实际情况设置该参数，
     */
    private CommandAddRelatedAttribute commandAddRelatedAttribute = null;

    /**
     * 代码格式参数
     */
    private String codeStatementParam;

    /**
     * 代码编号
     */
    private int codeOrdinal;

    /**
     * 缩进符
     */
    private String indent = "";

    private boolean inserNewLineOrNot = true;

    public AddCodeEditParam() {
        // TODO Auto-generated constructor stub
        super();
    }


}
