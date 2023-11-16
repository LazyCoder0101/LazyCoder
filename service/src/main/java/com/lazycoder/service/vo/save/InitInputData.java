package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.InitCodeModel;
import com.lazycoder.database.model.command.InitOperatingModel;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InitInputData {

    private ArrayList<InitOperatingModel> operatingList = new ArrayList<>();

    private ArrayList<InitCodeModel> codeList = new ArrayList<>();

}
