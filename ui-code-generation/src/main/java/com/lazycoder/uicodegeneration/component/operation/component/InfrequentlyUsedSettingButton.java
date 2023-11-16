package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.vo.element.lable.control.InfrequentlyUsedSettingControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.CodeOpratingComponentBusinessTraverse;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.InfrequentlyUsedSettingButtonForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.pane.InfrequentlyUsedSettingOperationPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.InfrequentlyUsedSettingMeta;
import java.util.ArrayList;

/**
 * 不常用设置
 *
 * @author admin
 */
public class InfrequentlyUsedSettingButton extends InfrequentlyUsedSettingButtonForCodeGeneration
        implements CodeGenerationComponentInterface, CodeGenerationFormatUIComonentInterface,
        CodeOpratingComponentBusinessTraverse {

    /**
     *
     */
    private static final long serialVersionUID = 231023466321564252L;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    private InfrequentlyUsedSettingOperationPane infrequentlyUsedSettingOperationPane;

    private InfrequentlyUsedSettingControl controlElement = new InfrequentlyUsedSettingControl();

    private PathFind pathFind;

    public InfrequentlyUsedSettingButton(OpratingContainerInterface opratingContainer,
                                         GeneralContainerComponentParam codeGenerationalOpratingContainerParam, InfrequentlyUsedSettingControl controlElement) {
        super();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.controlElement = controlElement;
        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;
        newInit(opratingContainer, codeGenerationalOpratingContainerParam, controlElement);
    }

    public InfrequentlyUsedSettingButton(OpratingContainerInterface opratingContainer,
                                         GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                         InfrequentlyUsedSettingMeta meta) {
        super();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.controlElement = meta.getControlElement();
        this.pathFind = meta.getPathFind();
        restoreInit(opratingContainer, codeGenerationalOpratingContainerParam, meta);
    }

    private void newInit(OpratingContainerInterface opratingContainer,
                         GeneralContainerComponentParam codeGenerationalOpratingContainerParam, InfrequentlyUsedSettingControl controlElement) {

        infrequentlyUsedSettingOperationPane = new InfrequentlyUsedSettingOperationPane(opratingContainer,
                codeGenerationalOpratingContainerParam, controlElement);
        scrollPane.setViewportView(infrequentlyUsedSettingOperationPane);
//		init();
    }

    private void restoreInit(OpratingContainerInterface opratingContainer,
                             GeneralContainerComponentParam codeGenerationalOpratingContainerParam, InfrequentlyUsedSettingMeta meta) {

        infrequentlyUsedSettingOperationPane = new InfrequentlyUsedSettingOperationPane(opratingContainer,
                codeGenerationalOpratingContainerParam, meta);
        scrollPane.setViewportView(infrequentlyUsedSettingOperationPane);
//		init();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        InfrequentlyUsedSettingMeta theModel = (InfrequentlyUsedSettingMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        theModel.setPaneElementList(infrequentlyUsedSettingOperationPane.getComponentList());
//		theModel.setInfrequentlyUsedSettingOperationPaneModel(
//				infrequentlyUsedSettingOperationPane.getFormatStructureModel());

    }

    @Override
    public InfrequentlyUsedSettingMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        InfrequentlyUsedSettingMeta model = new InfrequentlyUsedSettingMeta();
        setParam(model);
        return model;
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    @Override
    public void delModuleOpratingContainerFromComponent(String moduleId) {
        infrequentlyUsedSettingOperationPane.delModuleOpratingContainerFromComponent(moduleId);
    }

    @Override
    public ArrayList<OpratingContainerInterface> getAllOpratingContainer() {
        return infrequentlyUsedSettingOperationPane.getAllOpratingContainer();
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
    }

    @Override
    public void collapseThis() {
        // TODO Auto-generated method stub
        if (isExpanded()) {
            doClick();
        }
    }

    @Override
    protected void doSomeThingWhenPackUpPanel() {
        infrequentlyUsedSettingOperationPane.collapseThis();
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        packUpPanel();
        infrequentlyUsedSettingOperationPane.delThis();
        hidePopupPanel();
    }

    @Override
    public InfrequentlyUsedSettingControl getControlElement() {
        // TODO Auto-generated method stub
        return infrequentlyUsedSettingOperationPane.getControlElement();
    }

    @Override
    public PathFind getPathFind() {
        // TODO Auto-generated method stub
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        // TODO Auto-generated method stub
        return infrequentlyUsedSettingOperationPane.getThisCodeGenerationalOpratingContainerParam()
                .getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return infrequentlyUsedSettingOperationPane.getThisCodeGenerationalOpratingContainerParam()
                .getOperatingComponentPlacePane();
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return buttonWidth;
    }

    /**
     * 方法名id为functionNameId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     */
    public void functionNameSynchronousChange(int functionNameId){
        infrequentlyUsedSettingOperationPane.functionNameSynchronousChange(functionNameId);
    }

    /**
     * 方法名id为functionNameId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param functionNameId
     */
    public void functionNameSynchronousDelete(int functionNameId){
        infrequentlyUsedSettingOperationPane.functionNameSynchronousDelete(functionNameId);
    }

    /**
     * 方法名id为variableId的变量有改动，查看当前组件有没有选中该方法名，有的话，同步更改
     *
     * @param variableId
     */
    public void variableSynchronousChange(int variableId){
        infrequentlyUsedSettingOperationPane.variableSynchronousChange(variableId);
    }

    /**
     * 方法名id为variableId的变量被删了，查看当前组件有没有选中该方法名，有的话，删了
     *
     * @param variableId
     */
    public void variableSynchronousDelete(int variableId){
        infrequentlyUsedSettingOperationPane.variableSynchronousDelete(variableId);
    }

    /**
     * 设置变量组件里面不需要用户选择的值（仅在第一次创建代码生成界面的时候使用）
     */
    public void setNoUserSelectionIsRequiredValue() {
        infrequentlyUsedSettingOperationPane.setNoUserSelectionIsRequiredValue();
    }

    /**
     * 打开项目文件的时候才用，选择之前选好的值
     */
    public void showSelectedValue() {
        infrequentlyUsedSettingOperationPane.showSelectedValue();
    }

}
