package com.lazycoder.uicodegeneration.component.operation.container.codeeditparam;

import com.lazycoder.uicodegeneration.PathFind;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeEditParam {

    /**
     * 代码序号
     */
    private int codeSerialNumber;

    /**
     * 要添加的功能，
     */
    private PathFind trulyPathFind;

}
