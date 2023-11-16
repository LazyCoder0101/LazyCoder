package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.InitOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class InitOperatingModelDeserializer implements ObjectDeserializer {

    @Override
    public InitOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        InitOperatingModel temp = JsonUtil.restoreByJSONObject(object, InitOperatingModel.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
