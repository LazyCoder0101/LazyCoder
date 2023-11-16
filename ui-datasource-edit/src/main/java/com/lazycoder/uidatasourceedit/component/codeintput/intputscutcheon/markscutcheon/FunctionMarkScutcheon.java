package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.FunctionMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe.FunctionMarkEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.Setter;

public class FunctionMarkScutcheon extends BaseMarkScutcheon {

    /**
     *
     */
    private static final long serialVersionUID = 4379287673248666196L;

    @Setter
    private FunctionMarkElement markElement = new FunctionMarkElement();

    // 在此填写“功能”源码
    public FunctionMarkScutcheon() {
        init();
        addMouseListener(adapter);
        setShowText();
    }

    public FunctionMarkScutcheon(FunctionMarkElement markElement) {
        init();
        addMouseListener(adapter);
        this.markElement = markElement;
        setShowText();
    }

    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            super.mousePressed(e);
            new FunctionMarkEditFrame(FunctionMarkScutcheon.this);
        }
    };


    public void setShowText() {
        String markText = "功能";
        HTMLText htmlText = new HTMLText();
        HtmlPar htmlPar = new HtmlPar();
        htmlPar.addColorText(markText, HtmlPar.RED, false);

        StringBuilder out = new StringBuilder(markText);
        if (markElement.getCodeLabelId() != null && "".equals(markElement.getCodeLabelId()) == false) {
            CodeLabel codeLabel = SysService.CODE_LABEL_SERVICE.getCodeLabelById(markElement.getCodeLabelId());
            if (codeLabel != null) {
                out.append(codeLabel.getCodeLabelShowText());

                htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));
                htmlPar.addColorText(codeLabel.getCodeLabelShowText(), CODE_LABEL_COLOR, true);
            }
        }
        htmlText.addPar(htmlPar);

        setText(htmlText.getHtmlContent());
        setTextSize(out.toString().length() + BLANK_SPACE_NUM);
    }

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }

    @Override
    public FunctionMarkElement property() {
        // TODO Auto-generated method stub
        return markElement;
    }

    @Override
    public FunctionMarkElement getMarkElement() {
        return markElement;
    }

}
