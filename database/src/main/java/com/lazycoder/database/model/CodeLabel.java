package com.lazycoder.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeLabel implements BaseModel {

    public final static CodeLabel NO_CODE_LABEL = new CodeLabel(null, "无");

    /**
     * 唯一id标识
     */
    private String codeLabelId;

    /**
     * 标签名（和codeLabelId一样，唯一，不能同名）
     */
    private String codeLabelShowText;

}
