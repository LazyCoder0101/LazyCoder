package com.lazycoder.database.model.format;

import com.lazycoder.database.model.general.format.GenerlFormatOperatingModel;
import lombok.Data;

@Data
public class MainOperating extends GenerlFormatOperatingModel {

    public MainOperating() {
        // TODO Auto-generated constructor stub
        super();
        setFormatType(MAIN_TYPE);
    }

}
