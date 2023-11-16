package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.code;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.code.FunctionAddCodeElement;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.FunctionAddLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.MethodAddCodeEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lombok.Getter;


public class FunctionAddCodeLabel extends FunctionAddLabel implements CodeLabelInterface {

    /**
     *
     */
    private static final long serialVersionUID = 4086538337229038502L;

    @Getter
    private FunctionAddCodeElement codeElement = new FunctionAddCodeElement();

    /**
     * 新建
     */
    public FunctionAddCodeLabel(String name) {
        // TODO Auto-generated constructor stub
        super();
        init(name);
    }

    /**
     * 还原
     *
     * @param codeElement
     */
    public FunctionAddCodeLabel(FunctionAddCodeElement codeElement) {
        // TODO Auto-generated constructor stub
        this.codeElement = codeElement;
        init(codeElement.getThisName());
        setCodeLabelId(codeElement.getCodeLabelId());
    }

    private ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new MethodAddCodeEditFrame(FunctionAddCodeLabel.this);
        }
    };

    private void init(String name) {
        setText(name);
        setName(name);
        setUI(new ControlLabelButtonUI());
        addActionListener(listener);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        codeElement.setThisName(name);
    }

    @Override
    public FunctionAddCodeElement property() {
        // TODO Auto-generated method stub
        return codeElement;
    }

    public String getCodeLabelId() {
        return codeElement.getCodeLabelId();
    }

    public void setCodeLabelId(String codeLabelId) {
        codeElement.setCodeLabelId(codeLabelId);
        if (codeLabelId==null){
            setText(codeElement.getThisName());

            Dimension dd = new Dimension(110, 30);
            this.setMaximumSize(dd);
            this.setPreferredSize(dd);
            this.setMinimumSize(dd);
        }else {
            CodeLabel codeLabel = codeLabelId == null ? null : SysService.CODE_LABEL_SERVICE.getCodeLabelById(codeLabelId);
            if (codeLabel == null) {
//            codeElement.setCodeLabelId(null);
                setText(codeElement.getThisName());

                Dimension dd = new Dimension(110, 30);
                this.setMaximumSize(dd);
                this.setPreferredSize(dd);
                this.setMinimumSize(dd);

                String text = "有个代码操作面板，里面的一个功能拓展组件默认选择的代码标签已经被删了	(✪ω✪)";
                String logtext = getClass() + "（生成组件异常）————\"" + "有个代码操作面板，里面的一个功能拓展组件默认选择的代码标签已经被删了！！！这个值是" + codeLabelId;
                DataSourceEditHolder.errorLogging(text, logtext);
            } else {
//            setToolTipText("代码标签：" + codeLabel.getCodeLabelShowText());
                HTMLText htmlText = new HTMLText();
                HtmlPar htmlPar1 = new HtmlPar();
                htmlPar1.addText(codeElement.getThisName(), false);
                htmlPar1.addText(HtmlPar.getBlankSpaceSymbol(2), false);
                htmlPar1.addColorText(codeLabel.getCodeLabelShowText(), "rgb(160,32,240)", true);
                htmlText.addPar(htmlPar1);
                setText(htmlText.getHtmlContent());

                int lenth = codeElement.getThisName().length() + codeLabel.getCodeLabelShowText().length() + 2;
                Dimension dd = new Dimension(moreIcon.getIconWidth() + lenth * 16, 30);
                this.setMaximumSize(dd);
                this.setPreferredSize(dd);
                this.setMinimumSize(dd);
            }
        }
    }

    public void setIndent(String indent){
        codeElement.setIndent(indent);
    }

    public String getIndent() {
        return codeElement.getIndent();
    }

}