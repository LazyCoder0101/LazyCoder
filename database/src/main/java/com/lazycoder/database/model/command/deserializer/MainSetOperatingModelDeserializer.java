package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.MainSetOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class MainSetOperatingModelDeserializer implements ObjectDeserializer {

    @Override
    public MainSetOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        MainSetOperatingModel temp = JsonUtil.restoreByJSONObject(object, MainSetOperatingModel.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
