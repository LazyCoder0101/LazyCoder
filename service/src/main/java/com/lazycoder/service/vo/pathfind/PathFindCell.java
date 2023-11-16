package com.lazycoder.service.vo.pathfind;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.service.vo.element.lable.BaseLableElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PathFindCell {

    /**
     * 一般类型
     */
    public static final int FORMAT_TYPE = 0;

    /**
     * 默认类型
     */
    public static final int DEFAULT_TYPE = 1;

    /**
     * 隐藏类型
     */
    public static final int HIDDEN_TYPE = 2;

    @JSONField(ordinal = 1)
    private int paneType = FORMAT_TYPE;

    @JSONField(ordinal = 2)
    private Integer codeSerialNumber = null;

    @JSONField(deserializeUsing = PathFindCellOpratingLableDeserializer.class, ordinal = 3)
    private BaseLableElement opratingLabel = null;

    public PathFindCell(Integer codeSerialNumber, BaseLableElement opratingLabel, int paneType) {
        // TODO Auto-generated constructor stub
        this.codeSerialNumber = codeSerialNumber;
        this.paneType = paneType;
        this.opratingLabel = opratingLabel;
    }


}
