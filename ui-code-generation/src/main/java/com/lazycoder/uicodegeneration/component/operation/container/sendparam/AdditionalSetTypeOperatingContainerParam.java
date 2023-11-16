package com.lazycoder.uicodegeneration.component.operation.container.sendparam;

import com.lazycoder.database.model.AdditionalInfo;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalSetTypeOperatingContainerParam extends GeneralOperatingContainerParam {

	private FormatTypeFeatureSelectionModel featureSelectionModel;

	private String additionalSetType = "";

	private AdditionalInfo additionalInfo;

}
