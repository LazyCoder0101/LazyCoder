package com.lazycoder.database.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.deserializer.ModuleDeserializer;
import com.lazycoder.database.model.deserializer.ModuleInfoDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModuleRelatedParam {

    @JSONField(deserializeUsing = ModuleDeserializer.class)
    private Module module = null;

    @JSONField(deserializeUsing = ModuleInfoDeserializer.class)
    private ModuleInfo moduleInfo = null;

}
