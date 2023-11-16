package com.lazycoder.uicodegeneration.generalframe.functionname.deserializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.lazycoder.uicodegeneration.generalframe.functionname.AbstractMethodName;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GeneralFunctionNameHolderSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o instanceof ArrayList) {
            ArrayList<AbstractMethodName> list = (ArrayList<AbstractMethodName>) o;
            ArrayList<AbstractMethodName> outList = new ArrayList<>();
            for (AbstractMethodName methodName : list) {
                if (AbstractMethodName.FORMAT_FUNCTION_NAME_TYPE == methodName.getType()) {//只保存格式类型的，自定义类型的还原时随着组件还原即时添加
                    outList.addAll(list);
                    break;
                }
            }
            jsonSerializer.write(outList);
        }
    }

}
