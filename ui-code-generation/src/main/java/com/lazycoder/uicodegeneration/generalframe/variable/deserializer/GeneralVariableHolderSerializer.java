package com.lazycoder.uicodegeneration.generalframe.variable.deserializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.lazycoder.uicodegeneration.generalframe.variable.AbstractVariable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GeneralVariableHolderSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o instanceof ArrayList) {
            ArrayList<AbstractVariable> list = (ArrayList<AbstractVariable>) o;
            ArrayList<AbstractVariable> outList = new ArrayList<>();
            for (AbstractVariable variable : list) {
                if (AbstractVariable.FORMAT_VARIABLE_TYPE == variable.getType()) {//只保存格式类型的，自定义类型的还原时随着组件还原即时添加
                    outList.addAll(list);
                    break;
                }
            }
            jsonSerializer.write(outList);
        }
    }

}
