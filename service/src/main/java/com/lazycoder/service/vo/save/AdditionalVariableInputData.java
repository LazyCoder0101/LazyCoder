package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.VariableData;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalVariableInputData {

    private int additionalSerialNumber = 0;

    private ArrayList<VariableData> variableDataList = new ArrayList<>();

}
