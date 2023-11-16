package com.lazycoder.database;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.dao.format.FormatCodeFilelMapper;
import com.lazycoder.database.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodeFormatFlagParam implements BaseModel, DataFormatType {

    /**
     * 代码文件类型
     */
    private int formatType = MAIN_TYPE;

    /**
     * 默认代码文件
     */
    public static final int TYPE_DEFAULT_FORMAT_FILE = FormatCodeFilelMapper.TYPE_DEFAULT_FORMAT_FILE;
    /**
     * 附带代码文件
     */
    public static final int TYPE_ADDITIONAL_FORMAT_FILE = FormatCodeFilelMapper.TYPE_ADDITIONAL_FORMAT_FILE;

    /**
     * 代码文件属性
     */
    private int fileType = -1;

    /**
     * 分类标志（可选模板的代码文件才用）
     */
    private int additionalSerialNumber = 0;

    /**
     * 代码标志
     */
    private int codeOrdinal = 0;

    /**
     * 文件名
     */
    private String fileName = "";

    private String id = null;

    private String moduleId = "";

    /**
     * 比较两者是否相等
     *
     * @param codeFormatFlagParam1
     * @param codeFormatFlagParam2
     */
    @JSONField(deserialize = false, serialize = false)
    public static boolean compare(CodeFormatFlagParam codeFormatFlagParam1, CodeFormatFlagParam codeFormatFlagParam2) {
        boolean flag = false;
        if (codeFormatFlagParam1.getFileName().equals(codeFormatFlagParam2.getFileName()) &&
                codeFormatFlagParam1.getId().equals(codeFormatFlagParam2.getId())) {
            flag = true;
        }
//        if (codeFormatFlagParam1.getFormatType() == codeFormatFlagParam2.getFormatType()) {// 先判断类型是否相同
//
//            if (CodeFormatFlagParam.MAIN_TYPE == codeFormatFlagParam2.getFormatType()) {// 必填模板类型情况下
//                if (codeFormatFlagParam2.getFileType() == codeFormatFlagParam1.getFileType()) {// 属性类型相同
//                    if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam2
//                            .getFileType()) {// 默认类型
//                        flag = true;
//
//                    } else if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam2
//                            .getFileType()) {// 附带类型
//                        if (codeFormatFlagParam2.getFileName().equals(codeFormatFlagParam1.getFileName())) {
//                            flag = true;
//                        }
//                    }
//                }
//
//            } else if (CodeFormatFlagParam.ADDITIONAL_TYPE == codeFormatFlagParam2.getFormatType()) {
//                if (codeFormatFlagParam2.getFileType() == codeFormatFlagParam1.getFileType()) {// 属性类型相同
//                    if (codeFormatFlagParam2.getAdditionalSerialNumber() == codeFormatFlagParam1.getAdditionalSerialNumber()) {// Ordinal相同
//
//                        if (CodeFormatFlagParam.TYPE_DEFAULT_FORMAT_FILE == codeFormatFlagParam2
//                                .getFileType()) {// 默认类型
//                            flag = true;
//
//                        } else if (CodeFormatFlagParam.TYPE_ADDITIONAL_FORMAT_FILE == codeFormatFlagParam2
//                                .getFileType()) {// 附带类型
//                            if (codeFormatFlagParam2.getFileName().equals(codeFormatFlagParam1.getFileName())) {
//                                flag = true;
//                            }
//                        }
//                    }
//                }
//
//            } else if (CodeFormatFlagParam.MODULE_TYPE == codeFormatFlagParam2.getFormatType()) {// 模块类型
//                if (codeFormatFlagParam2.getModuleId().equals(codeFormatFlagParam1.getModuleId())) {// 判断模块名是否相同
//                    if (codeFormatFlagParam2.getFileName().equals(codeFormatFlagParam1.getFileName())) {// 判断代码编号是否相同
//                        flag = true;
//
//                    }
//                }
//            }
//        }
        return flag;
    }

}
