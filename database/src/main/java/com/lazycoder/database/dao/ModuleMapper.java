package com.lazycoder.database.dao;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.database.model.Module;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 模块数据层
 *
 * @author hmg
 */
@Mapper // 标识为mybatis数据层接口
@Component(value = "ModuleMapper")
public interface ModuleMapper extends BaseModel {

    /**
     * 添加模块
     *
     * @return
     */
    public void addModule(Module module);


    /**
     * 获取所有模块
     *
     * @return
     */
    public List<Module> getAllModuleList();

    /**
     * 按条件查询对应模块
     *
     * @param className       不需要该值就传null
     * @param usingRangeParam 不需要该值就传null
     * @param useSettingValue 不需要选择该参数就传一个小于-1的值
     * @return
     */
    public List<Module> getModuleList(@Param("className") String className,
                                      @Param("usingRangeParam") String usingRangeParam,
                                      @Param("useSettingValue") int useSettingValue);

    /**
     * 获取模块
     *
     * @param className
     * @param moduleName
     * @return
     */
    public Module getModule(@Param("className") String className, @Param("moduleName") String moduleName);

    /**
     * 根据 moduleId 获取模块
     *
     * @param moduleId
     * @return
     */
    public Module getModuleById(@Param("moduleId") String moduleId);

    /**
     * 设置模块信息
     *
     * @param module
     * @throws Exception
     */
    public void setModuleInfo(Module module);

    /**
     * 获取所有非必选（设置了“生成程序都要使用该模块”）的模块列表
     */
    public List<Module> getModulesListThatAreNotRequired(int whetherTheChoice);

    /**
     * 查询除了输入的模块和非模块以外所有的模块
     */
    public List<Module> getModuleListExceptNonModuleAnd(@Param("moduleId") String moduleId);

    /**
     * 查询usingRangeParam字段带有usingObjectParam的所有模块
     *
     * @param usingObjectParam
     * @return
     */
    public List<Module> getAllModulesUsedby(@Param("usingObjectParam") String usingObjectParam);

    /**
     * 查询有没有添加过该模块
     *
     * @param moduleName
     * @return
     */
    public Integer selectExist(@Param("className") String className, @Param("moduleName") String moduleName);

    /**
     * 获取使用了某某模块的所有模块
     *
     * @return
     */
    public List<Module> getModulesListThatUsedTheModule(@Param("moduleId") String moduleId);

    /**
     * 获取已经设置不能使用某某模块的所有模块
     *
     * @param moduleId
     * @return
     */
    public List<Module> getModulesListThatCanNotUsedTheModule(@Param("moduleId") String moduleId);

    public List<String> getModuleIdOrderByIndexParamAsc(List<String> moduleIdList);

    public void renameClassName(@Param("newClassName") String newClassName, @Param("oldClassName") String oldClassName);


    /**
     * 更改模块
     *
     * @param module
     */
    public void updateModule(Module module);


    /**
     * 删除模块
     *
     * @param className
     * @param moduleName
     */
    public void delModule(@Param("className") String className, @Param("moduleName") String moduleName);


}
