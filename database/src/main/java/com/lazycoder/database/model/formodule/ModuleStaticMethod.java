package com.lazycoder.database.model.formodule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.Module;
import com.lazycoder.utils.JsonUtil;

import com.lazycoder.utils.RealNumberUtil;
import java.util.ArrayList;
import java.util.List;

public class ModuleStaticMethod {

    /**
     * 把usingRangeParam 格式字符串转为 UsingRange列表对象
     *
     * @return
     */
    public static ArrayList<UsingObject> getUsingRange(Module module) {
        ArrayList<UsingObject> list = JSON.parseObject(module.getUsingRangeParam(),
                new TypeReference<ArrayList<UsingObject>>() {
                });
        return list;
    }

    /**
     * 把UsingRange列表对象转为格式字符串存储
     *
     * @param usingRangeList
     */
    public static void setUsingRangeParam(Module module, List<UsingObject> usingRangeList) {
        module.setUsingRangeParam(JsonUtil.getJsonStr(usingRangeList));
    }

    /**
     * 记录需要的模块
     *
     * @param list
     */
    public static void setNeedModuleParam(Module module, List<Module> list) {
        module.setNeedModuleParam(JsonUtil.getJsonStr(list));
    }


    /**
     * 记录不能调用的模块
     *
     * @param list
     */
    public static void setNoUseModuleParam(Module module, List<Module> list) {
        module.setNoUseModuleParam(JsonUtil.getJsonStr(list));
    }

    public static void setUseSettingValueParam(Module module, List<Integer> values) {
        ArrayList<String> data = new ArrayList<>();
        for (Integer i : values) {
            if (i != null) {
                data.add(i + "");
            }
        }
        module.setUseSettingParam(JsonUtil.getJsonStr(data));
    }

    public static ArrayList<Integer> getUseSettingValues(Module module) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<String> getData = JSON.parseObject(module.getUseSettingParam(), new TypeReference<ArrayList<String>>() {
        });
        for (String temp : getData) {
            if (RealNumberUtil.isInteger(temp)) {
                list.add(RealNumberUtil.convertedToInteger(temp));
            }
        }
        return list;
    }

}
