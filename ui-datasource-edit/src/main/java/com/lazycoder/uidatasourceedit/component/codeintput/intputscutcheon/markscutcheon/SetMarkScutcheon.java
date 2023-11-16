package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.SetMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe.SetMarkEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import lombok.Setter;

public class SetMarkScutcheon extends BaseMarkScutcheon {

    /**
     *
     */
    private static final long serialVersionUID = 6568147318006538993L;

    @Setter
    private SetMarkElement markElement = new SetMarkElement();

    // 在此填写“设置”源码
    public SetMarkScutcheon() {
        init();
        addMouseListener(adapter);
        setShowText();
    }

    public SetMarkScutcheon(SetMarkElement markElement) {
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
            new SetMarkEditFrame(SetMarkScutcheon.this);
        }
    };


    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }

    @Override
    public SetMarkElement property() {
        // TODO Auto-generated method stub
        return markElement;
    }

    public void setShowText() {
        String markText = "设置", temp;
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
    public SetMarkElement getMarkElement() {
        return markElement;
    }

    @Override
    public String toString() {
        return markElement.toString();
    }

}
