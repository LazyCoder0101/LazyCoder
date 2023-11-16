package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.RealNumberUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//import com.sun.awt.AWTUtilities;

/*
 * 源码改自http://www.dlxedu.com/detail/19/464831.html
 */

public class PicturesViewer extends JDialog {

    public static void main(String[] args) {
        new PicturesViewer("I:\\图片", "标题", DELETE_NOT_STATE);
    }

    public static final boolean DELETE_STATE = true, DELETE_NOT_STATE = false;
    /**
     *
     */
    private static final long serialVersionUID = 4299116668301440655L;

    private int thePer = 0;//当前百分比
    private final static int BIGGER = 0, SMALLER = 1, YUANTU = 2;
    private JPanel panel = new JPanel();
    private JLabel title, msg, showPiture, close, left, right, delete, info, name;
    private JScrollPane scrollPane;
    private JTextArea noteTextArea;
    private ImageIcon

            closeIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "clouse.png"),
            closeEnterIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "clouse_hover.png"),
            closePressIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "clouse_press.png"),

    previousIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "previous.png"),
            nextIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "next.png"),

    deleteIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "delete.png"),
            deleteEnterIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "delete_hover.png"),
            deletePressIcon = new ImageIcon(
                    SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "picturesViewer" + File.separator + "delete_pressed.png");
    private Font font = new Font("微软雅黑", Font.PLAIN, 18);
    private String folderPath;
    private float width, height;
    private File f = null;
    private ImageIcon imgIco, showii;

    protected ArrayList<String> noteList = new ArrayList<>();

    private void init(String tittle, boolean state) {
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModal(true);
//        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setOpacity(0.9f);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((d.width / 2) - 500, (d.height / 2) - 370, 1000, 700);

        panel.setLayout(null);
        panel.setBounds(0, 0, getWidth(), getHeight());
        panel.setBackground(Color.BLACK);
        this.add(panel);

        title = new JLabel(tittle);
        title.setBounds(10, 0, 500, 30);
        title.setFont(font);
        title.setForeground(Color.WHITE);
        panel.add(title);

        left = new JLabel(previousIcon);
        left.setBounds(0, 260, previousIcon.getIconWidth(), previousIcon.getIconHeight());
        left.addMouseListener(adapter);
        panel.add(left);

        right = new JLabel(nextIcon);
        right.setBounds(getWidth() - nextIcon.getIconWidth(), 260, nextIcon.getIconWidth(), nextIcon.getIconHeight());
        right.addMouseListener(adapter);
        panel.add(right);

        showPiture = new JLabel();
        showPiture.setHorizontalAlignment(JLabel.CENTER);
        showPiture.setVerticalAlignment(JLabel.CENTER);
        showPiture.setBounds(previousIcon.getIconWidth(), 50, getWidth() - nextIcon.getIconWidth() - previousIcon.getIconWidth(), 520);
        showPiture.addMouseWheelListener(mouseWheel);
        showPiture.addMouseListener(adapter);
        showPiture.setBackground(Color.yellow);
        // showPiture.setBorder(BorderFactory.createLineBorder(Color.red,3));
        panel.add(showPiture);

        if (state == PicturesViewer.DELETE_STATE) {
            delete = new JLabel(deleteIcon);
            delete.setBounds(20, 500, deleteIcon.getIconWidth(),
                    deleteIcon.getIconHeight());
            delete.addMouseListener(adapter);
            delete.setToolTipText("删除该图片");
            panel.add(delete);
        }

        info = new JLabel("");
        info.setBounds(showPiture.getX() + showPiture.getWidth() / 2 - 40, showPiture.getY() + showPiture.getHeight() + 5, 240, 20);//getHeight() - 180
        info.setFont(font);
        info.setForeground(Color.yellow);
        panel.add(info);

        name = new JLabel("");
        name.setBounds(info.getX() + info.getWidth() + 10, showPiture.getY() + showPiture.getHeight() + 5, 120, 20);//getHeight() - 180
        name.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        name.setForeground(Color.yellow);
        panel.add(name);

        msg = new JLabel("");
        msg.setForeground(Color.white);
        msg.setBounds(5, getHeight() - 25, 700, 20);
        panel.add(msg);

        close = new JLabel(closeIcon);
        close.setBounds(getWidth() - closeIcon.getIconWidth(), 0, closeIcon.getIconWidth(), closeIcon.getIconHeight());
        close.addMouseListener(adapter);
        panel.add(close);

        noteTextArea = new JTextArea();
        noteTextArea.setLineWrap(true);
        noteTextArea.setWrapStyleWord(true);
        noteTextArea.setOpaque(false);
        noteTextArea.setCaretColor(Color.WHITE);
        noteTextArea.setForeground(Color.magenta);
        noteTextArea.getDocument().addDocumentListener(documentListener);
        noteTextArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        scrollPane = new JScrollPane(noteTextArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBounds(previousIcon.getIconWidth() + 100, getHeight() - 90, 600, 60);
        panel.add(scrollPane);
        if (state == PicturesViewer.DELETE_STATE) {
            noteTextArea.setToolTipText("在此输入图片对应信息");
        } else if (state == PicturesViewer.DELETE_NOT_STATE) {
            noteTextArea.setEditable(false);
        }

        pituresInit(folderPath);
    }

    public PicturesViewer(String folderPath, String tittle, boolean state) {
        this.folderPath = folderPath;
        init(tittle, state);
        this.setVisible(true);

    }

    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateNote();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateNote();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateNote();
        }
    };

    private void updateNote() {
        int currentIndex = PictureTools.getFileIndex(f);
        this.noteList.set(currentIndex, noteTextArea.getText());
    }

    public PicturesViewer(String folderPath, String tittle, boolean state, ArrayList<String> noteList) {
        this.folderPath = folderPath;
        init(tittle, state);
        this.noteList = noteList;
        if (f != null && noteList.size() > 0) {
            noteTextArea.setText(noteList.get(0));
        }
        this.setVisible(true);
    }

    public void ShowImg() {
        if (imgIco != null) {
            int xwidth = showPiture.getWidth(), xhight = showPiture.getHeight();
            if (imgIco.getIconWidth() > xwidth || imgIco.getIconHeight() > xhight) {
                width = xwidth;
                height = (xhight + 10) * (float) imgIco.getIconHeight() / imgIco.getIconWidth();
            } else {
                width = imgIco.getIconWidth();
                height = imgIco.getIconHeight();
            }
            showii = new ImageIcon(imgIco.getImage().getScaledInstance((int) width, (int) height, 0));
            this.showPiture.setIcon(showii);

            if (f != null) {
                info.setText((PictureTools.getFileIndex(f) + 1) + "/" + PictureTools.getTotalNum());
            }
            if (f != null) {
                name.setText(f.getName());

            } else {
                name.setText("");
            }
            ShowMsg();
        }
    }

    private void pituresInit(String folderPath) {
        PictureTools.the_clear();
        File folderFile = new File(folderPath);
        if (folderFile.isDirectory() == true) {
            String[] pitureList = folderFile.list();
            if (pitureList.length > 0) {
                f = new File(folderPath + File.separator + pitureList[0]);
                PictureTools.addFile(f);
                imgIco = new ImageIcon(f.getPath());

            } else if (pitureList.length == 0) {
                f = null;
            }
            new getFileList(folderFile).start();
            ShowImg();

            if (pitureList.length > 0) {
                PictureTools.getNext(f);
                PictureTools.getLast(f);
            }
        }
    }

    private MouseWheelListener mouseWheel = new MouseWheelListener() {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (f != null) {
                if (e.getWheelRotation() == 1) {
                    Zoom(PicturesViewer.SMALLER);
                } else if (e.getWheelRotation() == -1) {
                    Zoom(PicturesViewer.BIGGER);
                }
            }
        }
    };

    private MouseAdapter adapter = new MouseAdapter() {

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
//			if (e.getSource() == left) {
//				left.setIcon(null);
//			} else if (e.getSource() == right) {
//				right.setIcon(null);
//			} else
            if (e.getSource() == close) {
                close.setIcon(closeIcon);
            } else if (e.getSource() == delete) {
                delete.setIcon(deleteIcon);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
//			if (e.getSource() == left) {
//				left.setIcon(previousIcon);
//			} else if (e.getSource() == right) {
//				right.setIcon(nextIcon);
//			} else
            if (e.getSource() == close) {
                close.setIcon(closeEnterIcon);
            } else if (e.getSource() == delete) {
                delete.setIcon(deleteEnterIcon);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO 自动生成的方法存根
            super.mousePressed(e);
            if (e.getSource() == close) {
                close.setIcon(closePressIcon);
            } else if (e.getSource() == delete) {
                delete.setIcon(deletePressIcon);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO 自动生成的方法存根
            super.mouseClicked(e);
            if (e.getSource() == showPiture) {
                Zoom(PicturesViewer.YUANTU);
            } else if (e.getSource() == close) {
                frameDispose();
            } else if (e.getSource() == left) {
                noteTextArea.getDocument().removeDocumentListener(documentListener);
                // 上一张
                int lastIndex = PictureTools.getLastIndex(f);
                if (lastIndex < noteList.size()) {
                    noteTextArea.setText(noteList.get(lastIndex));
                } else {
                    noteTextArea.setText("");
                }
                f = PictureTools.getLast(f);
                if (f != null) {
                    imgIco = new ImageIcon(f.getPath());
                    ShowImg();
                }
                noteTextArea.getDocument().addDocumentListener(documentListener);

            } else if (e.getSource() == right) {
                noteTextArea.getDocument().removeDocumentListener(documentListener);
                // 下一张
                int nextIndex = PictureTools.getNextIndex(f);
                if (nextIndex < noteList.size()) {
                    noteTextArea.setText(noteList.get(nextIndex));

                } else {
                    noteTextArea.setText("");
                }
                f = PictureTools.getNext(f);
                if (f != null) {
                    imgIco = new ImageIcon(f.getPath());
                    ShowImg();
                }
                noteTextArea.getDocument().addDocumentListener(documentListener);

            } else if (e.getSource() == delete) {
                if (f == null) {
                    LazyCoderOptionPane.showMessageDialog(PicturesViewer.this, "没有图片可删了！	(✪ω✪)");
                } else {
                    if (f.getName() != null || (!("".equals(f.getName().trim())))) {
                        int n = LazyCoderOptionPane.showConfirmDialog(PicturesViewer.this, "确认要删除图片\"" + f.getName() + "\"?", "",
                                JOptionPane.YES_NO_OPTION);
                        if (n == JOptionPane.OK_OPTION) {
                            int fileIndex = PictureTools.getFileIndex(f);
                            noteList.remove(fileIndex);
                            f.delete();
                            PictureTools.clear();
                            pituresInit(folderPath);
                        }
                    }
                }
            }
        }
    };

    protected void frameDispose() {
        PicturesViewer.this.dispose();
    }

    public void ShowMsg() {
        // 设置底部信息
        if (imgIco == null || showii == null) {
            this.msg.setText("");
        } else {
            if (f != null) {
                String[] n = f.getName().split("\\.");
                float per = (float) showii.getIconWidth() / imgIco.getIconWidth();
                thePer = (int) (per * 100);
                String msg = "原图：" + imgIco.getIconWidth() + "x" + imgIco.getIconHeight() + "像素  " + "显示："
                        + showii.getIconWidth() + "x" + showii.getIconHeight() + "像素  " + "缩放度：" + thePer
                        + "%" + "  格式:" + (n.length == 1 ? "未知" : n[1]);
                this.msg.setText(msg);
            } else {
                this.msg.setText("");
            }
        }

    }

    public void Zoom(int flag) {
        if ((width < 5000 && height < 4000) && (width > 5 && height > 4)) {
            if (flag == 0) {
                // 放大
                width *= 1.3;
                height *= 1.3;
            } else if (flag == 1) {
                // 缩小
                width *= 0.7;
                height *= 0.7;
            } else if (flag == 2) {
                // 原图
                width = imgIco.getIconWidth();
                height = imgIco.getIconHeight();
            }
            showii = new ImageIcon(imgIco.getImage().getScaledInstance((int) width, (int) height, 0));
            this.showPiture.setIcon(showii);
            ShowMsg();
        } else if (width > 5000 || height > 4000) {
            width *= 0.8;
            height *= 0.8;
            LazyCoderOptionPane.showMessageDialog(this, "再放大会失真！");

        } else if (thePer <= 3 || width < 5 || height < 3) {
            width *= 1.2;
            height *= 1.2;
            LazyCoderOptionPane.showMessageDialog(this, "再缩小就看不见了！");
        }
    }

}

/**
 * Created by ztc on 15-11-14.
 */
class getFileList extends Thread {
    File CurrentDirectory;

    public getFileList(File f) {
        this.CurrentDirectory = f;
    }

    @Override
    public void run() {
        PictureTools.the_clear();
        File[] cd = CurrentDirectory.listFiles(PictureTools.myFilenameFilter());
        orderByName(cd);
        for (int i = 0; i < cd.length; i++) {
            PictureTools.addFile(cd[i]);
        }
    }

    /**
     * 摘自 https://blog.csdn.net/qq_34246164/article/details/111785553
     *
     * @param sourceSiles
     */
//    public static void orderByDate(File[] sourceSiles) {
////        File file = new File(filePath);
////        File[] files = file.listFiles();
//        Arrays.sort(sourceSiles, new Comparator<File>() {
//            @Override
//            public int compare(File f1, File f2) {
//                long diff = f1.lastModified() - f2.lastModified();
//                if (diff > 0) {
//                    return 1;
//                } else if (diff == 0) {
//                    return 0;
//                } else {
//                    return -1;//如果 if 中修改为 返回-1 同时此处修改为返回 1  排序就会是递减
//                }
//            }
//
//            @Override
//            public boolean equals(Object obj) {
//                return true;
//            }
//
//        });
//    }

    /**
     * 按名字排序
     *
     * @param sourceSiles
     */
    public static void orderByName(File[] sourceSiles) {
//        File file = new File(filePath);
//        File[] files = file.listFiles();
        List fileList = Arrays.asList(sourceSiles);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                }
                if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                }
                if (RealNumberUtil.isInteger(FileUtil.getFileNameNoEx(o1.getName())) && RealNumberUtil.isInteger(FileUtil.getFileNameNoEx(o2.getName()))) {
                    //如果文件名完全是数字的话，直接返回两个数字之间的差值，避免出现类似于10排在2前面的情况
                    return RealNumberUtil.convertedToInteger(FileUtil.getFileNameNoEx(o1.getName())) -
                            RealNumberUtil.convertedToInteger(FileUtil.getFileNameNoEx(o2.getName()));
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

}

