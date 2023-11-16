package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.InitCodeModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InitCodeModelListDeserializer implements ObjectDeserializer {


    @Override
    public ArrayList<InitCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<InitCodeModel> out = new ArrayList<>();
        InitCodeModel object;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            object = base.toJavaObject(InitCodeModel.class);
            out.add(object);
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
