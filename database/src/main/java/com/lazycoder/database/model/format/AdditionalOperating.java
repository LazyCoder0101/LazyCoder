package com.lazycoder.database.model.format;

import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import com.lazycoder.lazycodercommon.vo.FunctionUseProperty;
import lombok.Data;

@Data
public class AdditionalOperating extends GenerlFormatOperatingModel {

    private int setProperty = FunctionUseProperty.no.getSysDictionaryValue();

    public AdditionalOperating() {
        // TODO Auto-generated constructor stub
        setFormatType(ADDITIONAL_TYPE);
    }

}
