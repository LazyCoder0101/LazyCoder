package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.database.model.featureSelectionModel.ModuleSetFeatureSelectionModel;
import com.lazycoder.uicodegeneration.component.operation.component.typeset.module.SetButton;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleTypeOperatingContainerParam extends GeneralOperatingContainerParam {

    private ModuleSetFeatureSelectionModel moduleSetFeatureSelectionModel;

    private String moduleSetType = "";

    private SetButton setButton = null;

}
