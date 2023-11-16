package com.lazycoder.database.model.general;

import com.lazycoder.database.model.general.command.GeneralCommandCodeModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 带路径的代码模型需要继承的类
 */
@NoArgsConstructor
@Data
public class GeneralCommandPathCodeModel extends GeneralCommandCodeModel {

    /**
     * 路径参数
     */
    private String pathParam = "";

}
