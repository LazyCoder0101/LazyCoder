package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.ModuleSetCodeModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModuleSetCodeModelListDeserializer implements ObjectDeserializer {


    @Override
    public ArrayList<ModuleSetCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<ModuleSetCodeModel> out = new ArrayList<>();
        ModuleSetCodeModel object;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            object = base.toJavaObject(ModuleSetCodeModel.class);
            out.add(object);
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
