package com.lazycoder.uidatasourceedit.formatedit.additional.attachedfile;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;


public class AdditionalAttachedFileSettingFrame extends LazyCoderCommonDialog {

    /**
     *
     */
    private static final long serialVersionUID = 903902156198293180L;

    private JPanel contentPane;

    private MyButton addButton, delButton, okButton, cancelButton;

    private AdditionalAttachedFileSettingContainerPane attachedFileSettingContainerPane;

    private OperatingTipButton tipButton;

    private JTabbedPane tabbedPane;
    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JPanel panel2;

    /**
     * Create the frame.
     */
    private AdditionalAttachedFileSettingFrame() {
        setTitle("可选模板设置");
        Dimension dimension = SysUtil.SCREEN_SIZE;
        setBounds(dimension.width / 2 - 400, dimension.height / 2 - 300, 795, 625);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(795, 70));
        contentPane.add(panel2, BorderLayout.SOUTH);
        panel2.setLayout(null);

        okButton = new MyButton("确定");
        okButton.addActionListener(actionListener);
        okButton.setBounds(200, 15, 113, 30);
        panel2.add(okButton);

        cancelButton = new MyButton("取消");
        cancelButton.setBounds(430, 15, 113, 30);
        panel2.add(cancelButton);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public AdditionalAttachedFileSettingFrame(int additionalSerialNumber) {
        this();
        attachedFilePaneInit(additionalSerialNumber);
        setVisible(true);
    }

    private void attachedFilePaneInit(int additionalSerialNumber) {
        scrollPane1 = new JScrollPane();
        tabbedPane.addTab("附带文件", null, scrollPane1, null);

        panel1 = new JPanel();
        scrollPane1.setViewportView(panel1);
        panel1.setLayout(null);

        attachedFileSettingContainerPane = new AdditionalAttachedFileSettingContainerPane(additionalSerialNumber);
        JScrollPane scrollPane = new JScrollPane(attachedFileSettingContainerPane);
        scrollPane.setBounds(30, 30, 690, 355);
        panel1.add(scrollPane);

        addButton = new MyButton("添加");
        addButton.setBounds(200, 400, 100, 30);
        panel1.add(addButton);

        delButton = new MyButton("删除");
        delButton.setBounds(430, 400, 100, 30);
        panel1.add(delButton);
        delButton.addActionListener(actionListener);
        addButton.addActionListener(actionListener);

        tipButton = new OperatingTipButton(
                SysFileStructure.getOperatingTipImageFolder(
                        "懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "格式设置" + File.separator + "可选模板" + File.separator + "设置").getAbsolutePath()
        );
        tipButton.setLocation(addButton.getX() - 100, addButton.getY() + 5);
        panel1.add(tipButton);
    }

    private ActionListener actionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == addButton) {
                attachedFileSettingContainerPane.addAttachedFileSettingContainer();

            } else if (e.getSource() == delButton) {
                attachedFileSettingContainerPane.delAttachedFileSettingContainer();

            } else if (e.getSource() == okButton) {
                attachedFileSettingContainerPane.save();
                dispose();

            } else if (e.getSource() == cancelButton) {
                dispose();
            }
        }
    };


}
