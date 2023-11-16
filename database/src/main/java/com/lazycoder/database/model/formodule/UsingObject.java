package com.lazycoder.database.model.formodule;

import com.alibaba.fastjson.annotation.JSONType;
import com.lazycoder.database.DataFormatType;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用范围
 *
 * @author admin
 */
@JSONType(orders = {"showName", "type", "serial"})
@NoArgsConstructor
@Data
public class UsingObject implements DataFormatType {

    /**
     * 选择必填模板使用范围
     */
    public static final UsingObject MAIN_USING_OBJECT = new UsingObject("必填模板", UsingObject.MAIN_TYPE);

    /**
     * 显示名称
     */
    private String showName;

    /**
     * 类型
     */
    private int type = MAIN_TYPE;

    /**
     * 可选模板序号（可选模板才用，拿来确认是第几个可选模板）
     */
    private int serial = 0;

    // 目前仅必填模板使用
    public UsingObject(String showName, int type) {
        this.showName = showName;
        this.type = type;
    }

    // 可选模板使用
    public UsingObject(int additionalSerialNumber) {
        this.showName = "可选模板" + additionalSerialNumber;
        this.serial = additionalSerialNumber;
        this.type = ADDITIONAL_TYPE;
    }

    public static boolean contantOrNot(UsingObject usingObject, List<UsingObject> list) {
        boolean flag = false;
        if (usingObject.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
            for (UsingObject usingObjectTemp : list) {
                if (usingObjectTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.MAIN_TYPE) {
                    flag = true;
                    break;
                }
            }
        } else if (usingObject.getType() == UsingObject.ADDITIONAL_TYPE) {
            for (UsingObject usingObjectTemp : list) {
                if (usingObjectTemp.getType() == com.lazycoder.database.model.formodule.UsingObject.ADDITIONAL_TYPE) {
                    if (usingObjectTemp.getSerial() == usingObject.getSerial()) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }


    @Override
    public String toString() {
        return "UsingObject [showName=" + showName + ", type=" + type + ", serial=" + serial + "]";
    }

}
