package com.lazycoder.uicodegeneration.component.generalframe;

import com.lazycoder.database.DataFormatType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormatControlPaneLable implements DataFormatType {

    private int type = MAIN_TYPE;

    /**
     * 默认文件名
     */
    private String defaultFileName = "";

    private boolean canBeDelOrNot = false;

    /**
     * 可选模板才用，序号
     */
    private int additionalSerialNumber = 0;

}
