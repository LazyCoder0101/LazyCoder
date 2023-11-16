package com.lazycoder.main;

import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.uiauthor.AuthorInfoFrame;
import com.lazycoder.uicodegeneration.component.CodeFormatServiceHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.dbselectframe.DataSourceSelectFrame;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uidatasourceedit.DataSourceClassificationEditFrame;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.component.AfToaster;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipButton;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonFrame;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FuncitonSelectFrame extends LazyCoderCommonFrame {

    public static final ImageIcon bgImage = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "bk.png");

    private JPanel contentPane;

    private MyButton dataEditFrameButton, codeGenerateFrameButton, restoreButton;

    private JLabel authorLabel;

    private OperatingTipButton tipButton;

//    生成程序时，在方法添加组件添加功能的校验有问题
//
//    需要再考虑一个功能，如果添加在方法组件里面，要是当前添加的方法，和这个方法组件不符，是否可以添加到外面同样的标签的位置

    /**
     * Create the frame.
     */
    public FuncitonSelectFrame() throws IOException {
        setTitle("懒农");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int paneWidth = 600, paneHight = 570;
        setBounds(SysUtil.SCREEN_SIZE.width / 2 - 310, SysUtil.SCREEN_SIZE.height / 2 - 290, paneWidth, paneHight);
        contentPane = new FunctionSelectPane(bgImage.getIconWidth(), bgImage.getIconHeight());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        Font font = new Font("微软雅黑", Font.PLAIN, 16);

        codeGenerateFrameButton = new MyButton("生成程序");
        codeGenerateFrameButton.addActionListener(listener);
        codeGenerateFrameButton.setFont(font);
        codeGenerateFrameButton.setForeground(Color.DARK_GRAY);
        codeGenerateFrameButton.setBounds(180, 170, 200, 40);
        contentPane.add(codeGenerateFrameButton);

        dataEditFrameButton = new MyButton("懒农数据源");
        dataEditFrameButton.setFont(font);
        dataEditFrameButton.setForeground(Color.DARK_GRAY);
        dataEditFrameButton.addActionListener(listener);
        dataEditFrameButton.setBounds(180, 240, 200, 40);
        contentPane.add(dataEditFrameButton);

        restoreButton = new MyButton("打开懒农文件");
        restoreButton.setFont(font);
        restoreButton.setForeground(Color.DARK_GRAY);
        restoreButton.addActionListener(listener);
        restoreButton.setBounds(180, 310, 200, 40);
        contentPane.add(restoreButton);

        authorLabel = new JLabel("关于");
        authorLabel.setFont(font);
        authorLabel.setForeground(Color.gray);
        authorLabel.addMouseListener(mouseAdapter);
        authorLabel.setBounds(365, 365, 110, 30);
        contentPane.add(authorLabel);

        tipButton = new OperatingTipButton(SysFileStructure.getOperatingTipImageFolder("新手指引").getAbsolutePath());
        tipButton.setLocation(335, 369);
        contentPane.add(tipButton);

        setVisible(true);
        GeneralHolder.changeSysDB();
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == dataEditFrameButton) {
                newDataSourceEditFrame();
                dispose();

            } else if (e.getSource() == codeGenerateFrameButton) {
                newCodeGenerationFrame();
                dispose();

            } else if (e.getSource() == restoreButton) {
                restoreCodeGenerationFrame();
            }
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            new AuthorInfoFrame();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            authorLabel.setForeground(Color.darkGray);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            authorLabel.setForeground(Color.gray);
        }
    };

    public void newDataSourceEditFrame() {
        new DataSourceClassificationEditFrame();
    }

    /**
     * 点击生成程序按钮的操作
     */
    public void newCodeGenerationFrame() {
        int userDbNum = SysService.DB_CHANGE_SERVICE.getAllUsrDataSourceNum();
        if (userDbNum == 0) {
            String[] options = new String[]{"去导入或者制作数据源", "知道了"};
            int temp = LazyCoderOptionPane.showOptionDialog(null,
                    "(^_^) 亲，现在没法生成程序喔， 你还没导入数据源或者没制作数据源喔，没法生成程序，先去导入或者制作数据源吧！！", "系统消息",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
                    options[0]);
            if (1 == temp) {//去导入或者制作数据源
                dispose();
                new DataSourceClassificationEditFrame();
            }
        } else if (userDbNum > 0) {//之前有导入数据源，进入数据源选择界面
            new DataSourceSelectFrame();
        }
    }

    private void restoreCodeGenerationFrame() {
        File folder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "打开懒农项目");
        if (folder != null) {
            ProInit proInit = new ProInit();
            proInit.setProjectName(folder.getName());
//            proInit.setProjectName("pro1");
//		proInit.setProjectName("fds");
            proInit.setProjectParentPath(folder.getParent());
//            proInit.setProjectParentPath("F:/源码生成测试路径");
            new CodeFormatServiceHolder();//在这里初始化代码格式化服务，避免后面等待时间太长

            boolean flag = StartStaticMethod.checkCanUseOrNot(proInit);
            if (flag == true) {
                AfToaster toaster = AfToaster.show(this, "正在打开项目文件，请稍后。。。", AfToaster.Level.WARN, 2000);

                new CodeFormatServiceHolder();//先初始化代码格式化服务
                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
//            GeneralHolder.changeUserDB("lannong_first");

                new CodeGenerationFrame(proInit);
                toaster.hidePopup();

                if (CodeGenerationFrameHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
                    CodeGenerationFrameHolder.showErrorListIfNeed("这数据源有点异常喔   (=ω=；)");
                } else {
                    dispose();
                }
//            } else {
//                dispose();
            }
        }
    }

}

class FunctionSelectPane extends JPanel {

    private int paneWidth, paneHight;

    public FunctionSelectPane(int paneWidth, int paneHight) {
        super();
        this.paneWidth = paneWidth;
        this.paneHight = paneHight;
        setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);// 执行超类方法
        Graphics2D g2d = (Graphics2D) g;
//                int width = getWidth();// 获取组件大小
//                int height = getHeight();

        Color color3 = new Color(66, 150, 255);//(66, 150, 255);
        // 创建填充模式对象，参数分别是起点坐标下x，y,起点颜色，终点坐标下x，y，终点颜色。
        GradientPaint paint1 = new GradientPaint(0, 0, color3,
                paneWidth, paneHight / 2, Color.white);
        g2d.setPaint(paint1);// 设置绘图对象的填充模式
        g.fillRect(0, paneHight / 2, paneWidth, paneHight);// 绘制矩形填充控件界面

        //绘制图片
        g.drawImage(FuncitonSelectFrame.bgImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

        //画一个透明圆角矩形
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(255, 255, 255, 60));//128, 128, 128,100
        g2d.fillRoundRect(
                paneWidth / 2 - 150,
                paneHight / 2 - 160,
                260, 260,
                30, 30);

//        g.setColor(new Color(90, 74, 235));
//        g.fillRect(180,110,210,17);

        g.setFont(new Font("微软雅黑", Font.BOLD, 46));
        g.setColor(new Color(255, 255, 255, 220));
        g.drawString("懒 农", 230, 80);
        g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        g.setColor(new Color(255, 255, 255, 190));
        g.drawString("—— Lazy Coder ——", 200, 110);
//        g.drawString("—— Lazy Coder ——",180,120);

    }

}
