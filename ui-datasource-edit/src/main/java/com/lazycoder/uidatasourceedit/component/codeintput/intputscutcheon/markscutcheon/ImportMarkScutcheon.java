package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon;

import com.lazycoder.database.common.MarkElementName;
import com.lazycoder.database.model.CodeLabel;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.mark.ImportMarkElement;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.markscutcheon.editframe.ImportMarkEditFrame;
import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.event.MouseAdapter;
import lombok.Setter;

public class ImportMarkScutcheon extends BaseMarkScutcheon {

    /**
     *
     */
    private static final long serialVersionUID = -5972528517587045310L;

    @Setter
    private ImportMarkElement markElement = new ImportMarkElement();

    // 在此填写“引入”源码
    public ImportMarkScutcheon() {
        init();
        addMouseListener(adapter);
        setShowText();
    }

    public ImportMarkScutcheon(ImportMarkElement markElement) {
        init();
        addMouseListener(adapter);
        this.markElement = markElement;
        setShowText();
    }

    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            new ImportMarkEditFrame(ImportMarkScutcheon.this);
        }
    };

    @Override
    public void deleteFromPanel() {
        // TODO Auto-generated method stub
    }


    @Override
    public ImportMarkElement getMarkElement() {
        return markElement;
    }

    @Override
    public ImportMarkElement property() {
        // TODO Auto-generated method stub
        return markElement;
    }

    public void setShowText() {
        String markText = "引入", temp;
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
        if (markElement.getOrdinal() != MarkElementName.MARK_NULL) {
            blackSpace++;
            htmlPar.add(HtmlPar.getBlankSpaceSymbol(BLANK_SPACE_NUM));

            temp = "引入" + markElement.getOrdinal();
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
    public String toString() {
        return markElement.toString();
    }

}
