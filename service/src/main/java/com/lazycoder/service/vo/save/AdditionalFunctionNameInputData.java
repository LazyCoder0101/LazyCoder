package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.FunctionNameData;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFunctionNameInputData {

    private int additionalSerialNumber = 0;

    private ArrayList<FunctionNameData> functionNameDataList = new ArrayList<>();

}
