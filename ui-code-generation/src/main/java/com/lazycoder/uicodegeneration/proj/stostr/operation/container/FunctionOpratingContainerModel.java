package com.lazycoder.uicodegeneration.proj.stostr.operation.container;

import com.lazycoder.service.vo.meta.FunctionMetaModel;
import lombok.Data;

@Data
public class FunctionOpratingContainerModel extends AbstractCommandOperatingContainerModel {

    private String className = null;

    private String moduleName = null;

    private String moduleId = null;

    private FunctionMetaModel metaModel = null;

    public FunctionOpratingContainerModel() {
        // TODO Auto-generated constructor stub
        super(FUNCTION_OPERATING_CONTAINER);
    }

}
