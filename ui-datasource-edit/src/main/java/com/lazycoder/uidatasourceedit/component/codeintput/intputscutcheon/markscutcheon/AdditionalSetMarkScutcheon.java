package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.AdditionalSetMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe.AdditionalSetMarkEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.Setter;

public class AdditionalSetMarkScutcheon extends BaseMarkScutcheon {

    /**
     *
     */
    private static final long serialVersionUID = -2326692558451154562L;

    @Setter
    private AdditionalSetMarkElement markElement = new AdditionalSetMarkElement();

    // 在此填写“设置”源码
    public AdditionalSetMarkScutcheon() {
        init();
        addMouseListener(adapter);
        setShowText();
    }

    public AdditionalSetMarkScutcheon(AdditionalSetMarkElement markElement) {
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
            new AdditionalSetMarkEditFrame(AdditionalSetMarkScutcheon.this);
        }
    };

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }

    @Override
    public AdditionalSetMarkElement property() {
        // TODO Auto-generated method stub
        return markElement;
    }

    public void setShowText() {
        String markText = "可选模板设置", temp;
        int blackSpace = 0;

        HTMLText htmlText = new HTMLText();
        HtmlPar htmlPar = new HtmlPar();

        htmlPar.addColorText(markText, HtmlPar.RED, false);
        StringBuilder out = new StringBuilder(markText);
        if (markElement.getClassificationSerial() != MarkElementName.MARK_NULL) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "分类" + markElement.getClassificationSerial();
            out.append(temp);
            htmlPar.addColorText(temp, FEATURE_COLOR, false);
        }
        if (markElement.getOperatingSerialNumber() != MarkElementName.MARK_NULL) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "方法" + markElement.getOperatingSerialNumber();
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
    public AdditionalSetMarkElement getMarkElement() {
        return markElement;
    }

    @Override
    public String toString() {
        return markElement.toString();
    }

}
