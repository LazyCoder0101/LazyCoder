package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.uiutils.htmlstyte.HTMLText;
import com.lazycoder.uiutils.htmlstyte.HtmlPar;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Token;

public class CodeShowTextArea extends RSyntaxTextArea {

    /**
     *
     */
    private static final long serialVersionUID = 5967594083089403786L;

    public CodeShowTextArea() {
        setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        setCodeFoldingEnabled(true);
        setAntiAliasingEnabled(true);
        setEditable(false);
        setFont(new Font("宋体", Font.PLAIN, 20));
        setBackground(new Color(255, 250, 237));
    }

    public CodeShowTextArea(String code) {
        setText(code);
    }

    public void setTipText(String path) {
        setToolTipText(HTMLText.createHtmlContent("源码文件路径：" + File.separator + path, HtmlPar.WHITE, false));
    }

    /**
     * 获取当前位置所在的token对应的序号（如果是-1就是没有）
     *
     * @return
     */
    public int getPosisitionTokenIndex(int caretPosition) {
        int tokenIndex = -1;
        Token token;
        for (int line = 0; line < getLineCount(); line++) {
            token = getTokenListForLine(line);
            while (token != null && token.isPaintable()) {
                if (caretPosition >= token.offset && caretPosition < (token.offset + token.textCount - 1)) {
                    return tokenIndex;
                }
                token = token.getNextToken();
                tokenIndex++;
            }
        }
        return -1; // 如果没有找到对应的token，返回-1
    }

    /**
     * 设置第n个token结尾位置为当前位置
     *
     * @param n
     */
    public void setCursorAtEndOfNthToken(int n) {
        if (n > -1) {
            boolean flag = false;
            int position = 0;
            int tokenIndex = 0;
            Token token;
            for (int line = 0; line < getLineCount(); line++) {
                token = getTokenListForLine(line);
                while (token != null && token.isPaintable()) {
                    if (tokenIndex == n) {
                        position = token.offset + token.textCount - 1;
                        flag = true;
                        break;
                    }
                    token = token.getNextToken();
                    tokenIndex++;
                }
                if (flag) {
                    break;
                }
            }
            if (position != 0) {
                setCaretPosition(position);
            }
        }
    }

}
