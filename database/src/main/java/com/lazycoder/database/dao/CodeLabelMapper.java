package com.lazycoder.database.dao;

import com.lazycoder.database.model.CodeLabel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper // 标识为mybatis数据层接口
@Component(value = "CodeLabelMapper")
public interface CodeLabelMapper {

    public void addCodeLabel(CodeLabel codeLabel);

    public void delCodeLabelById(@Param("codeLabelId") String codeLabelId);

    public void updateCodeLabel(CodeLabel codeLabel);

    public List<CodeLabel> getCodeLabelList();

    public CodeLabel getCodeLabelById(@Param("codeLabelId") String codeLabelId);

    public Integer selectExistByShowText(@Param("codeLabelShowText") String codeLabelShowText);

}
