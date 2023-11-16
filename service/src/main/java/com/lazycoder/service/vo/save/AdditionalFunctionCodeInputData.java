package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.AdditionalFunctionCodeModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalFunctionCodeInputData {

    private int additionalSerialNumber = 0;

    private List<AdditionalFunctionCodeModel> additionalFunctionCodeModelList = new ArrayList<>();

}
