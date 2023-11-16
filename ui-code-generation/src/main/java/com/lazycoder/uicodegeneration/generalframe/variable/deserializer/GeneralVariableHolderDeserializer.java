package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import com.lazycoder.uicodegeneration.generalframe.variable.FormatVariable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GeneralVariableHolderDeserializer implements ObjectDeserializer {

    @Override
    public ArrayList<AbstractVariable> deserialze(DefaultJSONParser parser, Type type, Object o) {
        ArrayList<AbstractVariable> out = new ArrayList<>();
        AbstractVariable variable = null;
        List<JSONObject> baseList = parser.parseArray(JSONObject.class);
        for (JSONObject base : baseList) {
            if (AbstractVariable.FORMAT_VARIABLE_TYPE == base.getIntValue(AbstractVariable.getTypeFieldName())) {
                variable = base.toJavaObject(FormatVariable.class);
                out.add(variable);
//			}else if (GeneralVariable.customVariableType == base.getIntValue(GeneralVariable.getTypeFieldName())) {
//				variable = base.toJavaObject(CustomVariable.class);
            }
        }
        return out;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
