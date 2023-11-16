package com.lazycoder.service.service.impl;

import chy.frame.multidatasourcespringstarter.annotation.TransactionMulti;
import com.lazycoder.database.dao.ClassificationMapper;
import com.lazycoder.database.model.TheClassification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationServiceImpl {

    @Autowired
    private ClassificationMapper dao;

    @TransactionMulti
    public void addClassification(TheClassification classification) {
        dao.addClassification(classification);
    }

    @TransactionMulti
    public void delClassification(String className) {
        // TODO Auto-generated method stub
        dao.delClassification(className);
    }

    /**
     * 获取分类列表
     *
     * @return
     */
    public List<TheClassification> getClassificationList() {
        // TODO Auto-generated method stub
        return dao.getClassificationList();
    }

    /**
     * 获取所有分类列表
     *
     * @return
     */
    public List<TheClassification> getAllClassificationList() {
        // TODO Auto-generated method stub
        return dao.getAllClassificationList();
    }

    /**
     * 更改分类
     *
     * @param classification
     * @param oldClassName
     */
    public void updateClassification(TheClassification classification, String oldClassName) {
        dao.updateClassification(classification, oldClassName);
    }

    /**
     * 查询有没有添加过该分类
     *
     * @param className
     * @return
     */
    public boolean selectExist(String className) {
        boolean flag = true;
        Integer result = dao.selectExist(className);
        if (result == null) {
            flag = false;
        } else if (result == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 获取分类
     *
     * @return
     */
    public TheClassification getTheClassification(String className) {
        if (className != null) {
            return dao.getTheClassification(className);
        }
        return null;
    }

}
