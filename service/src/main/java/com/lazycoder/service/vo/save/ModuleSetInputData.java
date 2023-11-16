package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.ModuleSetCodeModel;
import com.lazycoder.database.model.command.ModuleSetOperatingModel;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleSetInputData {

    private ArrayList<ModuleSetOperatingModel> operatingList = new ArrayList<>();

    private ArrayList<ModuleSetCodeModel> codeList = new ArrayList<>();

}
