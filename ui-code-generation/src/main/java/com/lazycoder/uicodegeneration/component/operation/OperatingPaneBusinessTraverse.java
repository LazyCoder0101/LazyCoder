package com.lazycoder.uicodegeneration.component.operation;

public interface OperatingPaneBusinessTraverse extends BusinessTraverse {

	/**
	 * 方法名id为functionNameId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
	 */
	public void functionNameSynchronousChange(int functionNameId);

	/**
	 * 方法名id为functionNameId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
	 *
	 * @param functionNameId
	 */
	public void functionNameSynchronousDelete(int functionNameId);

	/**
	 * 方法名id为variableId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
	 *
	 * @param variableId
	 */
	public void variableSynchronousChange(int variableId);

	/**
	 * 方法名id为variableId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
	 *
	 * @param variableId
	 */
	public void variableSynchronousDelete(int variableId);

}
