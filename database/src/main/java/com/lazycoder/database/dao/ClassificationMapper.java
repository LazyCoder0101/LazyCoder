package com.lazycoder.database.dao;

import com.lazycoder.database.model.TheClassification;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 分类数据层
 *
 * @author hmg
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "ClassificationMapper")
public interface ClassificationMapper {

    /**
     * 添加分类
     *
     * @return
     */
    public void addClassification(TheClassification classification);

    /**
     * 删除分类
     *
     * @param className
     */
    public void delClassification(String className);

    /**
     * 获取分类列表
     *
     * @return
     */
    public List<TheClassification> getClassificationList();

    /**
     * 获取所有分类
     *
     * @return
     */
    public List<TheClassification> getAllClassificationList();

    /**
     * 更改分类
     *
     * @param classification
     * @param oldClassName
     */
    public void updateClassification(@Param("classification") TheClassification classification, @Param("oldClassName") String oldClassName);

    /**
     * 查询有没有添加过该分类
     *
     * @param className
     * @return
     */
    public Integer selectExist(String className);

    /**
     * 获取分类
     *
     * @return
     */
    public TheClassification getTheClassification(String className);

}
