package com.lazycoder.database.model.general.format;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.DataFormatType;
import com.lazycoder.database.model.general.GeneralOperatingModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenerlFormatOperatingModel extends GeneralOperatingModel implements DataFormatType {

    private int formatType = ADDITIONAL_TYPE;

    /**
     * 序号 可选模板使用
     */
    private int additionalSerialNumber;

    /**
     * 分类名称（可选模板使用）
     */
    private String typeName = "";


    /**
     * 查看当前有没有填写操作内容
     *
     * @return
     */
    @JSONField(deserialize = false, serialize = false)
    public boolean getOperatingContent() {
        boolean flag = true;
        if ("[]".equals(getDefaultControlStatementFormat())) {
            flag = false;
        }
        return flag;
    }

}
