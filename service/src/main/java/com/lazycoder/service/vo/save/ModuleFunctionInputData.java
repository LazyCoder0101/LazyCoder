package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.FunctionCodeModel;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleFunctionInputData {

    private ArrayList<FunctionOperatingModel> operatingList = new ArrayList<>();

    private ArrayList<FunctionCodeModel> codeList = new ArrayList<>();

}
