package com.lazycoder.database.model.format.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.format.AdditionalOperating;
import com.lazycoder.utils.JsonUtil;

import java.lang.reflect.Type;

public class AdditionalOperatingDeserializer implements ObjectDeserializer {

    @Override
    public AdditionalOperating deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        AdditionalOperating temp = JsonUtil.restoreByJSONObject(object, AdditionalOperating.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
