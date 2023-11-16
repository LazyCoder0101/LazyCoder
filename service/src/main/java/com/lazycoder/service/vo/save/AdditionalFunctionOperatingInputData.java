package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.AdditionalFunctionOperatingModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFunctionOperatingInputData {

    private int additionalSerialNumber = 0;

    private List<AdditionalFunctionOperatingModel> additionalFunctionOperatingModelList = new ArrayList<>();

}
