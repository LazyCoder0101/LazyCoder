package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.FunctionOperatingModel;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class FunctionOperatingModelDeserializer implements ObjectDeserializer {

    @Override
    public FunctionOperatingModel deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        FunctionOperatingModel temp = JsonUtil.restoreByJSONObject(object, FunctionOperatingModel.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
