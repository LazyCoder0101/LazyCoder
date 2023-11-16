package com.lazycoder.uiutils.component;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

//TEXTAREA 行号显示插件  

/**
 * 源码摘自 https://blog.csdn.net/wangjianyu0115/article/details/50780269
 *
 * @author Administrator
 */
public class LineNumberHeaderView extends JComponent {
    /**
     * JAVA TextArea行数显示插件
     */
    private static final long serialVersionUID = 1L;
    public final Color DEFAULT_BACKGROUD = new Color(228, 228, 228);
    public final Color DEFAULT_FOREGROUD = Color.BLACK;
    public final int nHEIGHT = Integer.MAX_VALUE - 1000000;
    public final int MARGIN = 5;
    private final Font DEFAULT_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);
    private int lineHeight;
    private int fontLineHeight;
    private int currentRowWidth;
    private FontMetrics fontMetrics;

    public LineNumberHeaderView() {
        setFont(DEFAULT_FONT);
        setForeground(DEFAULT_FOREGROUD);
        setBackground(DEFAULT_BACKGROUD);
        setPreferredSize(9999);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Throwable e) {
        }
        JFrame jf = new JFrame();

        Container c = jf.getContentPane();

        c.setLayout(null); // 设置布局管理器为 null,即绝对定位

        JTextArea jta = new JTextArea();
        jta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 9000; i++) {
            out.append("\n");
        }
        jta.setText(out.toString());

        jta.setLineWrap(true); // 设置自动换行,自动换行则不会出现横向的滚动条
        jta.setEditable(true); // 设置可编辑

        JScrollPane jsp = new JScrollPane(jta); // 添加滚动条

//		jta.setBounds(20,20,100,500);    //设置 JTextArea 宽100,高500
        LineNumberHeaderView lineNumberHeaderView = new LineNumberHeaderView();
        lineNumberHeaderView.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        jsp.setRowHeaderView(lineNumberHeaderView);
        jsp.setBounds(20, 20, 100, 200); // 设置 JScrollPane 宽100,高200

        c.add(jsp); // 将组件加入容器
        jf.setSize(150, 300);
        jf.setVisible(true);
    }

    public void setPreferredSize(int row) {
        int width = fontMetrics.stringWidth(String.valueOf(row));
        if (currentRowWidth < width) {
            currentRowWidth = width;
            setPreferredSize(new Dimension(2 * MARGIN + width+1, nHEIGHT));
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(DEFAULT_FONT);
        fontMetrics = getFontMetrics(font);
        fontLineHeight = fontMetrics.getHeight();
    }

    public int getLineHeight() {
        if (lineHeight == 0) {
            return fontLineHeight;
        }
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        if (lineHeight > 0) {
            this.lineHeight = lineHeight;
        }
    }

    public int getStartOffset() {
        return 4;
    }

    @Override
    protected void paintComponent(Graphics g) {
        String lineNum;
        int nlineHeight = getLineHeight();
        int startOffset = getStartOffset();
        Rectangle drawHere = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g.setColor(getForeground());
        int startLineNum = (drawHere.y / nlineHeight) + 1;
        int endLineNum = startLineNum + (drawHere.height / nlineHeight);
//        int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight - startOffset + 3;
        int start = (drawHere.y / nlineHeight) * nlineHeight + nlineHeight - startOffset-4;
        for (int i = startLineNum; i <= endLineNum; ++i) {
            lineNum = String.valueOf(i);
            int width = fontMetrics.stringWidth(lineNum);
//            g.drawString(lineNum + " ", MARGIN + currentRowWidth - width, start);
            g.drawString(lineNum + " ", MARGIN + currentRowWidth - width - 1, start);
            start += nlineHeight;
        }
        setPreferredSize(endLineNum);
    }
}