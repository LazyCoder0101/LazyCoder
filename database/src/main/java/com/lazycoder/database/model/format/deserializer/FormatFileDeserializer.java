package com.lazycoder.database.model.format.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.GeneralFileFormat;
import com.lazycoder.utils.JsonUtil;
import java.lang.reflect.Type;

public class FormatFileDeserializer implements ObjectDeserializer {

    @Override
    public GeneralFileFormat deserialze(DefaultJSONParser parser, Type type, Object o) {
        JSONObject object = parser.parseObject();
        GeneralFileFormat temp = JsonUtil.restoreByJSONObject(object, GeneralFileFormat.class);
        return temp;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
