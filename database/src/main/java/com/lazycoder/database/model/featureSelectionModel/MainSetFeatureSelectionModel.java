package com.lazycoder.database.model.featureSelectionModel;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MainSetFeatureSelectionModel extends CommandFeatureSelectionModel {

    private String typeName = null;

    private int typeSerialNumber = 0;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

}
