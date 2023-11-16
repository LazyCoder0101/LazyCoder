package com.lazycoder.database.model.command.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.database.model.command.FunctionCodeModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FunctionCodeModelListDeserializer implements ObjectDeserializer {


    @Override
    public ArrayList<FunctionCodeModel> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<FunctionCodeModel> out = new ArrayList<>();
        FunctionCodeModel object;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            object = base.toJavaObject(FunctionCodeModel.class);
            out.add(object);
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
