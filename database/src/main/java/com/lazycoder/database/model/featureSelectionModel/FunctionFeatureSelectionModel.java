package com.lazycoder.database.model.featureSelectionModel;

import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;

@Data
public class FunctionFeatureSelectionModel extends CommandFeatureSelectionModel {

    private String typeName = null;

    private String moduleId;

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

    private int typeSerialNumber = 0;

    public FunctionFeatureSelectionModel() {
        // TODO Auto-generated constructor stub
        super();
    }


}
