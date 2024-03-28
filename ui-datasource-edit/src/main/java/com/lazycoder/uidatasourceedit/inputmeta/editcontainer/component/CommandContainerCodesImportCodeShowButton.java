package com.lazycoder.uidatasourceedit.inputmeta.editcontainer.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazycoder.database.model.CommandContainerImportCode;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.MyPopupButton;
import com.lazycoder.uiutils.mycomponent.MyToolBar;
import com.lazycoder.utils.JsonUtil;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 *
 */
public class CommandContainerCodesImportCodeShowButton extends MyPopupButton {

    private JPanel theContentPane;

    protected JScrollPane scrollPane;

    @Getter
    private MyToolBar toolBar;

    private Box vBox;

    private MyButton addImportButton, delImporButton;

    private int curentImportNum = 0;

    private MyButton closeBt;

    private static ImageIcon iIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator
            + "base_code_content_edit" + File.separator + "code_input_pane" + File.separator + "i.png"),

            closeIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭.png"),

            closePessedIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭_ressed.png"),

            closeRolloverIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "my" + File.separator
                    + "close" + File.separator + "关闭_rollover.png");

    /**
     * Create the panel.
     */
    public CommandContainerCodesImportCodeShowButton() {
        super(iIcon);
        setBorderPainted(false);
        setToolTipText("该代码所需要插入的引入代码");
        buttonHeight = 30;
        buttonWidth = 30;
        paneWidth = 390;
        paneHeight = 300;
        init();
        setHorizontalAlignment(SwingConstants.LEFT);
        //setHorizontalTextPosition(SwingConstants.RIGHT);
        //setPreferredSize(new Dimension(buttonWidth,buttonHeight));
    }

    /**
     * 还原之前录入的引入代码的内容
     *
     * @param importCodeParam
     */
    public void setThisImport(String importCodeParam) {
        ArrayList<CommandContainerImportCode> list = JSON.parseObject(importCodeParam,
                new TypeReference<ArrayList<CommandContainerImportCode>>() {
                });
        CommandContainerImportCodeInputPane importCodeInputPane;
        if (list.size() > 0) {//有编辑过,且此前添加过内容，显示之前编辑过的内容
            for (int a = 0; a < list.size(); a++) {
                curentImportNum++;
                importCodeInputPane = new CommandContainerImportCodeInputPane(list.get(a));
                vBox.add(importCodeInputPane);
            }
        }
    }

    private void init() {
        theContentPane = new JPanel();
        theContentPane.setLayout(new BorderLayout());

        toolBar = new MyToolBar();
        toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
        toolBar.setFloatable(false);
        theContentPane.add(toolBar, BorderLayout.NORTH);

        addImportButton = new MyButton("添加");
        addImportButton.addActionListener(importCodeListener);
        toolBar.add(addImportButton);
        delImporButton = new MyButton("删除");
        delImporButton.addActionListener(importCodeListener);
        toolBar.add(delImporButton);
        toolBar.add(Box.createHorizontalGlue());
        closeBt = new MyButton(closeIcon);
        closeBt.setBorderPainted(false);
        closeBt.setRolloverIcon(closeRolloverIcon);
        closeBt.setPressedIcon(closePessedIcon);
        closeBt.addActionListener(importCodeListener);
        toolBar.add(closeBt);

        vBox = Box.createVerticalBox();
        scrollPane = new JScrollPane(vBox);
        theContentPane.add(scrollPane, BorderLayout.CENTER);

        setInternalComponent(theContentPane);
    }

    private ActionListener importCodeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addImportButton) {
                addImportCode();
                scrollPane.updateUI();
                scrollPane.repaint();

            } else if (e.getSource() == delImporButton) {
                delImportCode();
                scrollPane.updateUI();
                scrollPane.repaint();
            } else if (e.getSource() == closeBt) {
                packUpPanel();
            }
        }
    };


    /**
     * 添加引入代码
     */
    public void addImportCode() {
        curentImportNum++;
        CommandContainerImportCodeInputPane importCodeInputPane = new CommandContainerImportCodeInputPane(curentImportNum);
        vBox.add(importCodeInputPane);
    }

    /**
     * 删除最后一条引入代码
     */
    public void delImportCode() {
        if (curentImportNum > 0) {
            curentImportNum--;
            vBox.remove(vBox.getComponentCount() - 1);
            vBox.updateUI();
            vBox.repaint();
        }
    }

    /**
     * 获取编辑好的的引入代码数据
     *
     * @return
     */
    public ArrayList<CommandContainerImportCode> getImportCode() {
        CommandContainerImportCodeInputPane temp;
        CommandContainerImportCode importCode;
        ArrayList<CommandContainerImportCode> list = new ArrayList<>();
        for (int i = 0; i < vBox.getComponentCount(); i++) {
            temp = (CommandContainerImportCodeInputPane) vBox.getComponent(i);
            importCode = temp.getImportCode();
            list.add(importCode);
        }
        return list;
    }

    /**
     * 根据编辑好的引入代码生成存到数据库对应的字符
     *
     * @return
     */
    public String getImportCodeParam() {
        return JsonUtil.getJsonStr(getImportCode());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (curentImportNum != 0) {
            drawBadge(g, curentImportNum + "");

            Graphics2D g2d = (Graphics2D) g.create();

            // 开启文本和图形的抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        }
        //g2d.dispose();
    }

    // 绘制角标
    private void drawBadge(Graphics g, String text) {
        // 设置颜色和字体
        g.setColor(new Color(63, 178, 232));
        g.setFont(new Font("Arial", Font.BOLD, 12));

        // 获取文本的宽度和高度，以便正确定位
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        // 计算角标的位置和大小
        int badgeX = getWidth() - textWidth - 5;//10
        int badgeY = 0;
        int badgeWidth = textWidth + 6;
        int badgeHeight = textHeight;

        // 绘制角标背景
        g.fillOval(badgeX, badgeY, badgeWidth, badgeHeight);

        // 设置文本颜色并绘制文本
        g.setColor(Color.WHITE);
        g.drawString(text, badgeX + 3, badgeY + textHeight - 3);
    }

}
