package com.lazycoder.database.model.featureSelectionModel;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdditionalFeatureSelection {

    private int additionalSerialNumber = 0;

    private String typeName = "";

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

}
