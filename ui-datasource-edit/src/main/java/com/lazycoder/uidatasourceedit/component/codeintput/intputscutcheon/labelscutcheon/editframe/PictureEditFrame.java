package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe;

import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control.PictureControlLabel;
import com.lazycoder.uiutils.mycomponent.LazyCoderFileChooser;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.PicturesViewer;
import com.lazycoder.uiutils.utils.SysUtil;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

public class PictureEditFrame extends AbstractEditFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1616839661579194542L;

    private JPanel contentPane;
    private JTextField textField;
    private MyButton tjButton, delButton;

    private PictureControlLabel controlLabel = null;

    /**
     * Create the frame.
     */
    private PictureEditFrame() {
        super();
        setBounds(SysUtil.SCREEN_SIZE.width / 2 - 150, SysUtil.SCREEN_SIZE.height / 2 - 220, 364, 240);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("图片名：");
        label.setBounds(25, 25, 100, 30);
        contentPane.add(label);

        textField = new JTextField();
        textField.setBounds(120, 25, 180, 30);
        contentPane.add(textField);
        textField.setColumns(10);

        tjButton = new MyButton("添加图片");
        tjButton.addActionListener(listener);
        tjButton.setBounds(25, 80, 110, 30);
        contentPane.add(tjButton);

        delButton = new MyButton("查看或删除图片");
        delButton.addActionListener(listener);
        delButton.setBounds(160, 80, 150, 30);
        contentPane.add(delButton);

        ok.setBounds(55, 140, 90, 30);
        ok.addActionListener(listener);
        contentPane.add(ok);

        cancel.setBounds(206, 140, 90, 30);
        cancel.addActionListener(listener);
        contentPane.add(cancel);
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == tjButton) {
                addPitures();
            } else if (e.getSource() == delButton) {
                browseAndDelPictures();
            } else if (e.getSource() == ok) {
                ok();
            } else if (e.getSource() == cancel) {
                PictureEditFrame.this.dispose();
            }
        }
    };

    public PictureEditFrame(PictureControlLabel controlLabel) {
        this();
        this.setTitle("更改图片");
        this.controlLabel = controlLabel;
        setData();
        ok.addActionListener(listener);
        cancel.addActionListener(listener);
        setVisible(true);
    }

    private void browseAndDelPictures() {
        File folder = controlLabel.getPitureFolder();
        new PicturesViewer(folder.getAbsolutePath(), "更改图片", PicturesViewer.DELETE_STATE);
    }

    private void addPitures() {
        LazyCoderFileChooser fc = new LazyCoderFileChooser(); // 查找文件
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY); // 接收文件和文件夹
        fc.setDialogTitle("添加图片");
        MyFileFilter pitureFileFilter = new MyFileFilter(new String[]{".BMP", ".JPG", ".JPEG", ".GIF", ".PNG"},
                "所有图形文件");
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(pitureFileFilter);
        fc.setMultiSelectionEnabled(true);

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File[] files = fc.getSelectedFiles();
            for (File file : files) {

                try {
                    File toFile = new File(
                            controlLabel.getPitureFolder().getAbsolutePath() + File.separator + file.getName());
                    if (toFile.exists() == true) {
                        LazyCoderOptionPane.showMessageDialog(this, "(҂‾▵‾)  之前添加过图片\"" + file.getName() + "\"啦！");
                    } else {
                        FileUtil.fileCopyNormal(file, toFile);
                    }
                } catch (FileNotFoundException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setData() {
        this.textField.setText(this.controlLabel.getPitureName());
    }

    /**
     * 设置图片名称
     */
    private void setValue() {
        this.controlLabel.setPitureName(this.textField.getText());
    }

    @Override
    public void ok() {
        // TODO Auto-generated method stub
        setValue();
        PictureEditFrame.this.dispose();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        super.dispose();
    }

    class MyFileFilter extends FileFilter {
        String[] suffArr;
        String discription;

        public MyFileFilter() {
            super();
        }

        public MyFileFilter(String[] suffArr, String discription) {
            super();
            this.suffArr = suffArr;
            this.discription = discription;
        }

        @Override
        public boolean accept(File f) {
            for (String s : suffArr) {
                if (f.getName().toUpperCase().endsWith(s)) {
                    return true;
                }
            }
            return f.isDirectory();
        }

        @Override
        public String getDescription() {
            return this.discription;
        }
    }

}
