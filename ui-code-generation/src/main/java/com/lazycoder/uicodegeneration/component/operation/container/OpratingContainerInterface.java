package com.lazycoder.uicodegeneration.component.operation.container;

import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.OpratingContainerBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.component.CodeGenerationComponentInterface;
import com.lazycoder.uicodegeneration.generalframe.functionname.holder.CustomFunctionNameHolder;
import com.lazycoder.uicodegeneration.generalframe.variable.holder.CustomVariableHolder;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.AbstractOperatingContainerModel;
import java.io.File;

public interface OpratingContainerInterface
        extends OpratingContainerBusinessTraverse {

    /**
     * 获取该功能容器组件的内部的控制组件放置面板类型
     *
     * @return
     */
    public String getPaneType();

    /**
     * 分类名
     *
     * @return
     */
    public String getClassName();

    /**
     * 模块名
     *
     * @return
     */
    public String getModuleName();

    /**
     * 获取模块id
     *
     * @return
     */
    public String getModuleId();

    /**
     * 其他代码序号
     *
     * @return
     */
    public int getAdditionalSerialNumber();

    /**
     * 更新对应代码值（该方法只能提供给所在功能）
     * @param codeGenerationComponent
     * @param paneType
     * @param updateParam
     */
    public void updateValue(CodeGenerationComponentInterface codeGenerationComponent,int paneType, Object updateParam);


//    @Override
//    public void delThis();

    /**
     * 获取图片路径（供该面板上的图片组件调用，继承该面板都要重写）
     *
     * @return
     */
    public File getImageRootPath();

    /**
     * 获取文件选择路径（供该面板的文件选择组件调用，继承该面板都要重写）
     *
     * @return
     */
    public File getFileSelectorRootPath();

    /**
     * 返回对应的变量列表
     *
     * @return
     */
    public CustomVariableHolder getThisCustomVariableHolder();

    /**
     * 返回对应的方法名列表
     *
     * @return
     */
    public CustomFunctionNameHolder getThisCustomMethodNameHolder();

    public PathFind getPathFind();

    public int getCodeSerialNumber();

    public AbstractOperatingContainerModel getFormatStructureModel();

    public void setParam(AbstractOperatingContainerModel model);

    /**
     * 获取放置该容器的首个命令容器
     *
     * @return
     */
    public AbstractCommandOpratingContainer getFirstCommandOpratingContainer();

    /**
     * 获取放置该容器的首个格式容器
     *
     * @return
     */
    public AbstractFormatContainer getFormatContainer();

    /**
     * 是否是同一层级
     *
     * @return
     */
    public boolean isItTheSameLevel(OpratingContainerInterface opratingContainer);

    /**
     * 设置变量组件里面自动选择的值（仅在第一次创建代码生成界面的时候使用）
     */
    public void setNoUserSelectionIsRequiredValue();

    /**
     * 打开项目文件的时候才用，选择之前选好的值
     */
    public void showSelectedValue();

}
