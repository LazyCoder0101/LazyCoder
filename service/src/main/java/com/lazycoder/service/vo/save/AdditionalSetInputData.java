package com.lazycoder.service.vo.save;

import com.lazycoder.database.model.command.FormatTypeCodeModel;
import com.lazycoder.database.model.command.FormatTypeOperatingModel;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalSetInputData {

    private ArrayList<FormatTypeOperatingModel> operatingList = new ArrayList<>();

    private ArrayList<FormatTypeCodeModel> codeList = new ArrayList<>();

}
