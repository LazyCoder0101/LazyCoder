package com.lazycoder.service.vo.transferparam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FunctionAddParam {

    private String paneType;

    private String className = "";

    private String moduleName = "";

    private String moduleId = "";

    /**
     * 可选模板序号（只有可选模板才用）
     */
    private int additionalSerialNumber = 0;


}
