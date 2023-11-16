package com.lazycoder.service.service.impl;

import com.lazycoder.database.CodeFormatFlagParam;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;

public class FormatCodeFileServiceImpl {

    /**
     * 把GeneralFileFormat里面的内容转成代码格式参数
     *
     * @param fileFormat
     * @return
     */
    public static CodeFormatFlagParam getCodeFormatFlagParam(GeneralFileFormat fileFormat) {
        CodeFormatFlagParam codeFormat = new CodeFormatFlagParam();
        codeFormat.setId(fileFormat.getId());
        codeFormat.setFormatType(fileFormat.getFormatType());
        codeFormat.setFileType(fileFormat.getFileType());
        codeFormat.setFileName(fileFormat.getFileName());
        codeFormat.setCodeOrdinal(fileFormat.getCodeOrdinal());
        codeFormat.setAdditionalSerialNumber(fileFormat.getAdditionalSerialNumber());
        if (fileFormat.getFormatType()==GeneralFileFormat.MODULE_TYPE){
            codeFormat.setModuleId(fileFormat.getModuleId());
        }
        return codeFormat;
    }


    /**
     * 获取路径参数
     *
     * @param codeFormatFlagParamList
     * @return
     */
    public static String getPathParamToString(ArrayList<CodeFormatFlagParam> codeFormatFlagParamList) {
        String str = JsonUtil.getJsonStr(codeFormatFlagParamList);
        return str;
    }

}
