package com.lazycoder.uicodegeneration.proj.stostr.operation.base;

/**
 * 代码生成界面的数据，需要保存到生成的项目文件，继承该接口
 *
 * @author admin
 */
public interface CodeGenerationFormatUIComonentInterface {

	public void setParam(FormatStructureModelInterface model);

	public AbstractOpratingPaneElement getFormatStructureModel();

}
