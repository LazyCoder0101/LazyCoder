package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.InitMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe.InitMarkEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.Setter;

public class InitMarkScutcheon extends BaseMarkScutcheon {

    /**
     *
     */
    private static final long serialVersionUID = -7828809009156409831L;

    @Setter
    private InitMarkElement markElement = new InitMarkElement();

    // 在此填写“初始化”源码
    public InitMarkScutcheon() {
        init();
        addMouseListener(adapter);
        setShowText();
    }

    public InitMarkScutcheon(InitMarkElement markElement) {
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
            new InitMarkEditFrame(InitMarkScutcheon.this);
        }
    };

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }

    public void setShowText() {
        String markText = "初始化",temp;
        int blackSpace = 0;

        HTMLText htmlText = new HTMLText();
        HtmlPar htmlPar = new HtmlPar();

        htmlPar.addColorText(markText, HtmlPar.RED, false);
        StringBuilder out = new StringBuilder(markText);
        if (markElement.getModuleId() != null && "".equals(markElement.getModuleId()) == false) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "\"" + SysService.MODULE_SERVICE.getModuleById(markElement.getModuleId()).getModuleName() + "\"模块";
            out.append(temp);
            htmlPar.addColorText(temp, FEATURE_COLOR, false);
        }
        if (markElement.getInitSerialNumber() != MarkElementName.MARK_NULL) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "方法" + markElement.getInitSerialNumber();
            out.append(temp);
            htmlPar.addColorText(temp, FEATURE_COLOR, false);
        }
        if (markElement.getCodeNumber() != MarkElementName.MARK_NULL) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "代码" + markElement.getCodeNumber();
            out.append(temp);
            htmlPar.addColorText(temp, FEATURE_COLOR, false);
        }
        if (markElement.getCodeLabelId() != null && "".equals(markElement.getCodeLabelId()) == false) {
            CodeLabel codeLabel = SysService.CODE_LABEL_SERVICE.getCodeLabelById(markElement.getCodeLabelId());
            if (codeLabel != null) {
                out.append(codeLabel.getCodeLabelShowText());
                blackSpace++;

                htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));
                htmlPar.addColorText(codeLabel.getCodeLabelShowText(), CODE_LABEL_COLOR, true);
            }
        }
        htmlText.addPar(htmlPar);
        setText(htmlText.getHtmlContent());
        setTextSize(out.toString().length() + blackSpace * BLANK_SPACE_NUM);
    }

    @Override
    public InitMarkElement property() {
        // TODO Auto-generated method stub
        return markElement;
    }

    public Integer getInitSerialNumber() {
        return markElement.getInitSerialNumber();
    }

    public void setInitSerialNumber(int initSerialNumber) {
        markElement.setInitSerialNumber(initSerialNumber);
    }

    public int getCodeNumber() {
        return markElement.getCodeNumber();
    }

    public void setCodeNumber(int codeNumber) {
        markElement.setCodeNumber(codeNumber);
    }

    @Override
    public InitMarkElement getMarkElement() {
        return markElement;
    }

    @Override
    public String toString() {
        return markElement.toString();
    }

}
