package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.lazycoder.database.dao.CodeLabelMapper;
import com.lazycoder.database.model.CodeLabel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "CodeLabelServiceImpl")
public class CodeLabelServiceImpl {

    @Autowired
    private CodeLabelMapper dao;

    @TransactionMulti
    public void addCodeLabel(CodeLabel codeLabel) {
        dao.addCodeLabel(codeLabel);
    }

    public void delCodeLabelById(String codeLabelId) {
        dao.delCodeLabelById(codeLabelId);
    }

    public void updateCodeLabel(CodeLabel codeLabel) {
        dao.updateCodeLabel(codeLabel);
    }

    public List<CodeLabel> getCodeLabelList() {
        return dao.getCodeLabelList();
    }

    public CodeLabel getCodeLabelById(String codeLabelId) {
        return dao.getCodeLabelById(codeLabelId);
    }

    public boolean selectExistByShowText(String showText) {
        boolean flag = true;
        Integer result = dao.selectExistByShowText(showText);
        if (result == null) {
            flag = false;
        } else if (result == 1) {
            flag = true;
        }
        return flag;
    }

}
