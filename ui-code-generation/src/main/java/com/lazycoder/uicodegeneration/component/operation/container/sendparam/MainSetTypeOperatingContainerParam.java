package com.lazycoder.uicodegeneration.component.operation.container.sendparam;


import com.lazycoder.database.model.MainInfo;
import com.lazycoder.database.model.featureSelectionModel.FormatTypeFeatureSelectionModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainSetTypeOperatingContainerParam extends GeneralOperatingContainerParam {

	private FormatTypeFeatureSelectionModel featureSelectionModel;

	private String mainSetType = "";

	private MainInfo mainInfo;

}
