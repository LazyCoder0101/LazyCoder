package com.lazycoder.database.model.format.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.GeneralFileFormat;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FormatFilelListDeserializer implements ObjectDeserializer {

    @Override
    public ArrayList<GeneralFileFormat> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<GeneralFileFormat> out = new ArrayList<>();
        GeneralFileFormat object;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            object = base.toJavaObject(GeneralFileFormat.class);
            out.add(object);
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
