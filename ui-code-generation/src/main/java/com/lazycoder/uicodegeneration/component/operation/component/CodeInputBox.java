package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.lazycodercommon.vo.ProgramingLanguage;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.control.CodeInputControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.component.extendscompoment.CodeInputBoxForCodeGeneration;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.CodeInputMeta;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 代码输入组件
 *
 * @author admin
 */
public class CodeInputBox extends CodeInputBoxForCodeGeneration implements CodeGenerationComponentInterface,
        CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = -7265071360944295949L;

    private CodeInputControl controlElement = new CodeInputControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    public CodeInputBox() {
        // TODO Auto-generated constructor stub
        // textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);这里要根据需要选择使用什么编程语言
    }

    public CodeInputBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                        CodeInputControl controlElement) {
        // TODO Auto-generated constructor stub
        super();
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;

        if (controlElement.getDefaultCode() != null && ("".equals(controlElement.getDefaultCode()) == false)) {
            textArea.setText(controlElement.getDefaultCode());
        }
        String languageStyle = ProgramingLanguage.getEncodingTypeBy(controlElement.getInputProgramingLanguageDictionaryValue()).getRsyntaxTextAreaEditingStyle();
        textArea.setSyntaxEditingStyle(languageStyle);
        updateValue();
        textArea.getDocument().addDocumentListener(documentListener);
    }


    public CodeInputBox(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                        CodeInputMeta codeInputMeta) {
        super();
        this.controlElement = codeInputMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;

        this.pathFind = codeInputMeta.getPathFind();
        textArea.setText(codeInputMeta.getInputContent());

        String languageStyle = SysService.SYS_PARAM_SERVICE.getUseProgramingLanguage().getRsyntaxTextAreaEditingStyle();
        textArea.setSyntaxEditingStyle(languageStyle);
        textArea.getDocument().addDocumentListener(documentListener);
    }

    private DocumentListener documentListener = new DocumentListener() {

        @Override
        public void removeUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // TODO Auto-generated method stub
            updateValue();
        }
    };


    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
        CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(),textArea.getText());
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        hidePopupPanel();
    }

    @Override
    public CodeInputControl getControlElement() {
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        CodeInputMeta theModel = (CodeInputMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        theModel.setInputContent(textArea.getText());

    }

    @Override
    public CodeInputMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        CodeInputMeta model = new CodeInputMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return buttonWidth;
    }

    @Override
    public void collapseThis() {
        packUpPanel();
    }

}
