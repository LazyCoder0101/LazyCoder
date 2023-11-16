package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import com.lazycoder.uicodegeneration.generalframe.functionname.FormatFunctionName;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneralFunctionNameHolderDeserializer implements ObjectDeserializer {

    public GeneralFunctionNameHolderDeserializer(){};

    @Override
    public ArrayList<AbstractMethodName> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<AbstractMethodName> out = new ArrayList<>();
        AbstractMethodName functionName = null;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            if (AbstractMethodName.FORMAT_FUNCTION_NAME_TYPE == base.getIntValue(AbstractMethodName.getTypeFieldName())) {
                functionName = base.toJavaObject(FormatFunctionName.class);
                out.add(functionName);
//            } else if (GeneralMethodName.customFunctionNameType == base.getIntValue(GeneralMethodName.getTypeFieldName())) {
//                functionName = base.toJavaObject(CustomFunctionName.class);
            }
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
