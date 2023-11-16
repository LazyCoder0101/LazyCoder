package com.lazycoder.database.model.format.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.format.MainOperating;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class MainOperatingDeserializer implements ObjectDeserializer {

    @Override
    public MainOperating deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        MainOperating temp = JsonUtil.restoreByJSONObject(object, MainOperating.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
