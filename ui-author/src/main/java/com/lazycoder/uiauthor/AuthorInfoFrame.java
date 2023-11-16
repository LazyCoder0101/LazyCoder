package com.lazycoder.uiauthor;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.fileStructure.SysFileUtil;
import com.lazycoder.uiutils.mycomponent.LazyCoderCommonDialog;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.Getter;

public class AuthorInfoFrame extends LazyCoderCommonDialog {

    /**
     *
     */
    private static final long serialVersionUID = -1305715281519796849L;

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    AuthorInfoFrame frame = new AuthorInfoFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Font font = new Font("宋体", Font.PLAIN, 14);
    private static ImageIcon lastIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "图片浏览" + File.separator + "上一张.png"),
            nextIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "图片浏览" + File.separator + "下一张.png");
    private JLabel showPane, nameLabel;
    private MyButton last, next;

    private String imageFolder = "image", payFolder = "pay";

    private String[] picList = new String[]{
            imageFolder + File.separator + "作者QQ.jpeg",
            imageFolder + File.separator + "作者微信.png",
            imageFolder + File.separator + "懒农QQ群.jpg"
    };
    //    private final String lannongTiebaPath = //"https://tieba.baidu.com/f?ie=utf-8&kw=%E6%87%92%E5%86%9C&fr=search&traceid=",
//    "https://tieba.baidu.com/f?ie=utf-8&kw=懒农&fr=search",
    String qqQunPath = "https://shang.qq.com/wpa/qunwpa?idkey=4f2417035b77865b439e1f026f9cf10a0f4282201edb66007d388774d0f51dbe";
//    private String tipText = "点击打开网页";

    /**
     * Create the frame.
     */
    public AuthorInfoFrame() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("关于");
//		setBounds(100, 100, 769, 550);
        setBounds((SysUtil.SCREEN_SIZE.width / 2) - 330, (SysUtil.SCREEN_SIZE.height / 2) - 300, 769, 570);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("作者：懒仔");
        label.setFont(new Font("宋体", Font.PLAIN, 16));
        label.setBounds(46, 15, 93, 30);
        contentPane.add(label);

        JLabel lblNewLabel = new JLabel("QQ：3606515004 ");
        lblNewLabel.setFont(font);
        lblNewLabel.setBounds(46, 45, 183, 30);
        contentPane.add(lblNewLabel);

        JLabel qqqNewLabel = new JLabel("QQ群：893613856");
        qqqNewLabel.setFont(font);
        qqqNewLabel.setBounds(46, 65, 119, 30);//y:248
        contentPane.add(qqqNewLabel);
        qqqNewLabel.addMouseListener(mouseListener);
        qqqNewLabel.setToolTipText("点击添加qq群");

        try {
            JLabel donateLabel = new JLabel("项目完成不易，你的一点支持，将是我继续做下去的动力");
            donateLabel.setForeground(Color.DARK_GRAY);
            donateLabel.setBounds(30, 110, 300, 30);
            contentPane.add(donateLabel);

            JLabel zhbTextLabel = new JLabel("支付宝二维码");
            zhbTextLabel.setBounds(120, 140, 200, 30);
            contentPane.add(zhbTextLabel);
            BufferedImage zhbBufferedImage = ImageIO.read(FileUtil.getStaticResource(
                    imageFolder + File.separator + payFolder + File.separator + "zfb.png"));
            ImageIcon zhbPay = new ImageIcon(zhbBufferedImage.getScaledInstance(zhbBufferedImage.getWidth(), zhbBufferedImage.getHeight(), 0));
            JLabel zhbLabel = new JLabel(zhbPay);
            zhbLabel.setBounds(100, zhbTextLabel.getY() + zhbTextLabel.getHeight(), zhbPay.getIconWidth(), zhbPay.getIconHeight());
            contentPane.add(zhbLabel);

            JLabel wxTextLabel = new JLabel("微信二维码");
            wxTextLabel.setBounds(120, zhbLabel.getY() + zhbLabel.getHeight() + 10, 200, 30);
            contentPane.add(wxTextLabel);
            BufferedImage wxBufferedImage = ImageIO.read(FileUtil.getStaticResource(
                    imageFolder + File.separator + payFolder + File.separator + "wx.png"));
            ImageIcon wxPay = new ImageIcon(wxBufferedImage.getScaledInstance(wxBufferedImage.getWidth(), wxBufferedImage.getHeight(), 0));
            JLabel xwLabel = new JLabel(wxPay);
            xwLabel.setBounds(100, wxTextLabel.getY() + wxTextLabel.getHeight(), wxPay.getIconWidth(), wxPay.getIconHeight());
            contentPane.add(xwLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel versionLabel = new JLabel("版本：" + System.getProperty("clientVersion"));
        versionLabel.setBounds(260, 480, 100, 30);
        contentPane.add(versionLabel);
        JLabel separatorLabel = new JLabel("|");
        separatorLabel.setBounds(340, 480, 5, 30);
        contentPane.add(separatorLabel);
        JLabel jszcLabel = new JLabel("技术支持：");
        jszcLabel.setBounds(360, 480, 100, 30);
        contentPane.add(jszcLabel);
        JLabel thirdPartySoftwareLabel = new JLabel("开源软件");
        thirdPartySoftwareLabel.addMouseListener(thirdPartySoftwareMouseAdapter);
        thirdPartySoftwareLabel.setBounds(420, 480, 100, 30);
        thirdPartySoftwareLabel.setForeground(new Color(51, 127, 217));
        contentPane.add(thirdPartySoftwareLabel);

        JLabel copyrightLabel = new JLabel("Copyright © 2023 懒仔. All Rights Reserved.");
        copyrightLabel.setBounds(240, 500, 370, 30);
        contentPane.add(copyrightLabel);

//        JLabel lbllanzai = new JLabel("微信：lanzai2020");
//        lbllanzai.setFont(font);
//        lbllanzai.setBounds(46, 90, 119, 23);
//        contentPane.add(lbllanzai);

//        JLabel slabel = new JLabel("新浪微博：懒仔2020");
//        slabel.setFont(font);
//        slabel.setBounds(46, 115, 146, 15);
//        contentPane.add(slabel);

//        JLabel tlabel = new JLabel("虎牙：懒仔");
//        tlabel.setFont(font);
//        tlabel.setBounds(46, 135, 84, 15);
//        contentPane.add(tlabel);

//        JLabel blabel = new JLabel("百度贴吧：懒农吧");
//        blabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                try {//原文链接：https://blog.csdn.net/flycomos_lee/article/details/83924299
//                    Runtime.getRuntime().exec("cmd.exe /c start " + lannongTiebaPath);
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            }
//        });
//        blabel.setFont(font);
//        blabel.setBounds(46, 273, 119, 15);
//        contentPane.add(blabel);
//        blabel.addMouseListener(mouseListener);
//        blabel.setToolTipText(tipText);

//		JLabel mmlabel = new JLabel("更多信息请关注贴吧和微信公众号");
//		mmlabel.setFont(new Font("宋体", Font.PLAIN, 16));
//		mmlabel.setBounds(46, 454, 254, 15);
//		contentPane.add(mmlabel);


//        JLabel mlabel = new JLabel("更多");
//        mlabel.setFont(new Font("宋体", Font.PLAIN, 16));
//        mlabel.setBounds(46, 223, 93, 15);
//        contentPane.add(mlabel);

        qqqNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {//https://blog.csdn.net/flycomos_lee/article/details/83924299
                    Runtime.getRuntime().exec("cmd.exe /c start " + qqQunPath);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });


//        JLabel wclabel = new JLabel("微信公众号：懒农");
//        wclabel.setFont(font);
//        wclabel.setBounds(46, 155, 146, 15);
//        contentPane.add(wclabel);
//
//        JLabel lblUid = new JLabel("哔哩哔哩：懒农-懒仔");
//        lblUid.setFont(font);
//        lblUid.setBounds(46, 175, 254, 15);
//        contentPane.add(lblUid);
//
//        JLabel uidLabel = new JLabel("UID：485557874");
//        uidLabel.setBounds(115, 198, 104, 15);
//        uidLabel.setFont(font);
//        contentPane.add(uidLabel);

        last = new MyButton(lastIcon);
        last.setBounds(292 + 50, 414, nextIcon.getIconWidth(), nextIcon.getIconHeight());
        contentPane.add(last);
        last.addMouseListener(adapter);

        next = new MyButton(nextIcon);
        next.setBounds(560 + 30, 414, nextIcon.getIconWidth(), nextIcon.getIconHeight());
        next.addMouseListener(adapter);
        contentPane.add(next);

        showPane = new JLabel("", JLabel.CENTER);
        showPane.setBounds(350, 55, 300, 310);
        showPane.addMouseWheelListener(mouseWheel);
        contentPane.add(showPane);
//        showPane.setBorder(BorderFactory.createLineBorder(Color.black));

        nameLabel = new JLabel("", JLabel.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        nameLabel.setBounds(257 + 30, 385, 419, 20);
        contentPane.add(nameLabel);

        pituresInit();

        closingWindow();
        setVisible(true);
    }

    private MouseAdapter thirdPartySoftwareMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().open(SysFileUtil.THIRD_PARTY_SOFTWARE);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    };

    private MouseWheelListener mouseWheel = new MouseWheelListener() {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (imgIco != null) {
                if (e.getWheelRotation() == 1) {
                    Zoom(AuthorInfoFrame.smaller);
                } else if (e.getWheelRotation() == -1) {
                    Zoom(AuthorInfoFrame.BIGGER);
                }
            }
        }
    };

    private void pituresInit() {
        GetImageIconList iconList = new GetImageIconList(picList);
        if (iconList.getImgList().size() > 0) {
            imgIco = iconList.getImgList().get(iconList.getImgList().size() - 1);
        }
        iconList.start();
        ShowImg();
    }

    private float width, height;

    public void ShowImg() {
        if (imgIco != null) {
            if (imgIco.getIconWidth() > 695 || imgIco.getIconHeight() > 465) {
                width = 695;
                height = 675 * (float) imgIco.getIconHeight() / imgIco.getIconWidth();
            } else {
                width = imgIco.getIconWidth();
                height = imgIco.getIconHeight();
            }
            showii = new ImageIcon(
                    imgIco.getImage().getScaledInstance((int) width, (int) height, 0));
            this.showPane.setIcon(showii);
            nameLabel.setText(getFileNameNoEx(imgIco.getDescription()));
        }
    }

    private String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    private ImageIcon imgIco = null, showii;


    private final static int BIGGER = 0,
            smaller = 1,
            yuantu = 2;

    public void Zoom(int flag) {
        if ((width < 5000 && height < 4000) && (width > 5 && height > 4)) {
            if (flag == 0) {
                //放大  
                width *= 1.3;
                height *= 1.3;
            } else if (flag == 1) {
                //缩小  
                width *= 0.7;
                height *= 0.7;
            } else if (flag == 2) {
                //原图  
                width = imgIco.getIconWidth();
                height = imgIco.getIconHeight();
            }
            showii = new ImageIcon(imgIco.getImage().getScaledInstance((int) width, (int) height, 0));
            showPane.setIcon(showii);
        } else if (width > 5000 || height > 4000) {
            width *= 0.8;
            height *= 0.8;
            LazyCoderOptionPane.showMessageDialog(this, "再放大会失真！");
        } else if (width < 5 || height < 3) {
            width *= 1.2;
            height *= 1.2;
            LazyCoderOptionPane.showMessageDialog(this, "再缩小就看不见了！");
        }
    }

    private MouseAdapter adapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO 自动生成的方法存根
            super.mouseClicked(e);
            if (e.getSource() == showPane) {
                Zoom(yuantu);
            } else if (e.getSource() == last) {
                //上一张
                imgIco = Tools.getLast(imgIco);
                ShowImg();

            } else if (e.getSource() == next) {
                //下一张
                imgIco = Tools.getNext(imgIco);
                ShowImg();
            }
        }
    };

    private MouseAdapter mouseListener = new MouseAdapter() {

        @Override
        public void mouseExited(MouseEvent e) {
            JComponent theSource = null;
            if (e.getSource() instanceof JLabel) {
                theSource = (JLabel) e.getSource();
            }
            //退出时字体变黑
            theSource.setForeground(Color.black);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JComponent theSource = null;
            if (e.getSource() instanceof JLabel) {
                theSource = (JLabel) e.getSource();
            }
            //进入时字体变蓝，弹出窗口
            theSource.setForeground(Color.blue);
        }
    };

    /**
     * 关闭窗口
     */
    private void closingWindow() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AuthorInfoFrame.this.dispose();
            }
        });
    }
}

class GetImageIconList extends Thread {

    @Getter
    private List<ImageIcon> imgList = new ArrayList<ImageIcon>();

    public GetImageIconList(String[] picList) {
        BufferedImage imageTemp;
        ImageIcon imageIconTemp;
        for (String picPath : picList) {
            try {
                imageTemp = ImageIO.read(FileUtil.getStaticResource(picPath));
                imageIconTemp = new ImageIcon(imageTemp.getScaledInstance(imageTemp.getWidth(), imageTemp.getHeight(), 0));
                imageIconTemp.setDescription(picPath.substring(picPath.lastIndexOf(File.separator) + 1));
                imgList.add(imageIconTemp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Tools.theClear();
        for (int i = 0; i < imgList.size(); i++) {
            Tools.addFile(imgList.get(i));
        }
    }

}

class Tools {
    public static List<ImageIcon> imgBuffer = new ArrayList<ImageIcon>();

    public static void theClear() {
        imgBuffer.clear();
    }

    public static void addFile(ImageIcon f) {
        if (!imgBuffer.contains(f)) {
            imgBuffer.add(f);
        }
    }

    public static ImageIcon getNext(ImageIcon f) {
        if (imgBuffer.indexOf(f) + 1 < imgBuffer.size()) {
            return imgBuffer.get(imgBuffer.indexOf(f) + 1);
        } else if (imgBuffer.size() > 0) {
            return imgBuffer.get(0);
        }
        return null;
    }

    public static ImageIcon getLast(ImageIcon f) {
        if (imgBuffer.indexOf(f) - 1 >= 0) {
            return imgBuffer.get(imgBuffer.indexOf(f) - 1);
        } else if (imgBuffer.size() > 0) {
            return imgBuffer.get(imgBuffer.size() - 1);
        }
        return null;
    }

    public static void clear() {
        imgBuffer.clear();
    }

}


