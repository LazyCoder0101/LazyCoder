package com.lazycoder.database.model.featureSelectionModel;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;

@Data
public class AdditionalFunctionFeatureSelectionModel extends CommandFeatureSelectionModel {

    private int additionalSerialNumber = 0;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

    public AdditionalFunctionFeatureSelectionModel() {
        // TODO Auto-generated constructor stub
        super();
    }


}
