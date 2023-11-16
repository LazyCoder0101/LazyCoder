package com.lazycoder.uicodegeneration.component;

import com.lazycoder.codeformat.format.coolformat.DoFormatter;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uicodegeneration.generalframe.codeshown.CodeFormatService;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
public class CodeFormatServiceHolder {

    public static ArrayList<CodeFormatService> formatServiceList = new ArrayList<>();

    public CodeFormatServiceHolder() {
        try {
            DoFormatter.init();
        } catch (Exception e) {
            //e.printStackTrace();
            Map<String, Object> error = new HashMap<String, Object>();
            error.put("异常信息",e.getMessage());
            error.put("异常位置",e.getStackTrace());
            error.put("异常原因",e.getCause());
            error.put("异常异常的本地化信息",e.getLocalizedMessage());
            error.put("异常类型",e.getClass());

            SysService.SYS_SERVICE_SERVICE.log_error("代码格式化初始化错误：" + JsonUtil.getJsonStr(error));
        }
//        log.info("已初始化CoolFormat");
    }


}
