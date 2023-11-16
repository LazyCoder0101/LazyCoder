package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;


import com.formdev.flatlaf.ui.FlatMenuBarUI;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.PictureLabel;
import com.lazycoder.uiutils.component.animatedcarousel.net.codemap.carousel.helpcarousel.OperatingTipMenuItem;
import com.lazycoder.uiutils.mycomponent.LazyCoderFileChooser;
import com.lazycoder.uiutils.mycomponent.PicturesViewer;
import com.lazycoder.uiutils.utils.ShineEffect;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;


public class PictureControlLabel extends PictureLabel implements ControlLabelInterface {


    /**
     *
     */
    private static final long serialVersionUID = 8333689381346161689L;

    private PictureControl controlElement = new PictureControl();

    private JMenuItem updatePictureNameItem, addPictureItem, browseOrDelItem;

    private OperatingTipMenuItem operatingTipMenuItem;

    private File thisFolder;


    /**
     * 新建
     *
     * @param name
     */
    public PictureControlLabel(String name, PassingComponentParams passingComponentParams) {
        // TODO Auto-generated constructor stub
        super();
        setPassingComponentParams(passingComponentParams);
        init(name);
        controlElement.setPitureName(name);
        menu.setText(controlElement.getPitureName());
        creatThisFolder();
    }

    /**
     * 还原
     *
     * @param controlElement
     */
    public PictureControlLabel(PictureControl controlElement, PassingComponentParams passingComponentParams) {
        // TODO Auto-generated constructor stub
        super();
        this.controlElement = controlElement;
        setPassingComponentParams(passingComponentParams);
        init(controlElement.getThisName());
        menu.setText(controlElement.getPitureName());
        thisFolder = new File(((BaseOperatingPane) passingComponentParams.getThisPane()).getImageRootPath().getAbsolutePath() + File.separator
                + controlElement.getPitureFolderName());
        if (thisFolder.isDirectory() == true) {
            File[] listFiles = thisFolder.listFiles();
            if (listFiles != null) {
                if (listFiles.length > controlElement.getPitureNoteList().size()) {//pitureNoteList这个字段是后面加的，在此做处理，有多少个图片就生成多少个对应字符
                    int differenceValue = listFiles.length - controlElement.getPitureNoteList().size();
                    for (int k = 0; k < differenceValue; k++) {
                        this.controlElement.getPitureNoteList().add("");
                    }
                }
            }
        } else {
            if (getPassingComponentParams() != null && getPassingComponentParams().getThisPane() != null) {

                File pictureFolder = ((BaseOperatingPane) getPassingComponentParams().getThisPane()).getImageRootPath();
                String text = "奇怪，有个图片组件对应的文件夹莫名其妙被删了	 (ಥ_ಥ) ";
                String logtext = "有个图片组件的对应文件夹被删，对应文件夹名：" + controlElement.getPitureFolderName() + "\t对应路径：" + pictureFolder.getAbsolutePath();
                DataSourceEditHolder.errorLogging(text, logtext);
                thisFolder.mkdirs();
            }
        }


    }

    public void init(String name) {
        setName(name);
//		setToolTipText(((BaseOperatingPane) getPassingComponentParams().getThisPane()).getImageRootPath().getAbsolutePath() + File.separator + controlElement.getPitureFolderName());
        setUI(new ControlLabelMenuBarUI());//ShineEffect.createShineEffect(g, c, ShineEffect.defaultComponentColor);
//        menu.setText(name);
        if (name.length() > 5) {
            menu.setToolTipText(name);
        } else {
            menu.setToolTipText(null);
        }

        updatePictureNameItem = new JMenuItem("更改图片名");
        updatePictureNameItem.addActionListener(listener);
        menu.add(updatePictureNameItem);
        addPictureItem = new JMenuItem("添加图片");
        addPictureItem.addActionListener(listener);
        menu.add(addPictureItem);
        browseOrDelItem = new JMenuItem("查看或删除图片");
        browseOrDelItem.addActionListener(listener);
        menu.add(browseOrDelItem);
        operatingTipMenuItem = new OperatingTipMenuItem(
                SysFileStructure.getOperatingTipImageFolder("懒农数据源管理" + File.separator + "数据源编辑" + File.separator + "操作组件说明" + File.separator + "9.png").getAbsolutePath()
        );
        menu.add(operatingTipMenuItem);

    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == updatePictureNameItem) {
                String out = LazyCoderOptionPane.showInputDialog(PictureControlLabel.this, "请输入图片集名称", controlElement.getPitureName());
                if (out != null && "".equals(out.trim()) == false) {
                    controlElement.setPitureName(out);
                    menu.setText(out);
                    if (out.length() > 5) {
                        menu.setToolTipText(out);
                    } else {
                        menu.setToolTipText(null);
                    }
                }
            } else if (e.getSource() == addPictureItem) {
                addPitures();
            } else if (e.getSource() == browseOrDelItem) {
                browseAndDelPictures();
            }
        }
    };

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
                            getPitureFolder().getAbsolutePath() + File.separator + file.getName());
                    if (toFile.exists() == true) {
                        LazyCoderOptionPane.showMessageDialog(null, "(҂‾▵‾)  之前添加过图片\"" + file.getName() + "\"啦！");
                    } else {
                        FileUtil.fileCopyNormal(file, toFile);
                        controlElement.getPitureNoteList().add("");
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

    private void browseAndDelPictures() {
        File folder = getPitureFolder();
        if (folder.isDirectory() == true) {
            if (folder.listFiles().length > 0) {
                new PicturesViewer(folder.getAbsolutePath(), "更改图片", PicturesViewer.DELETE_STATE, controlElement.getPitureNoteList()) {
                    @Override
                    protected void frameDispose() {
                        PictureControlLabel.this.controlElement.setPitureNoteList(this.noteList);
                        super.frameDispose();
                    }
                };
            } else {
                LazyCoderOptionPane.showMessageDialog(null, "(*^▽^*)  你还没在里面添加图片呢，先添加图片吧！");
            }
        } else {
            String text = "奇怪，这图片组件对应的文件夹莫名其妙被删了	 (ಥ_ಥ) ";
            String logtext = "有个图片组件的对应文件夹被删，对应文件夹名：" + controlElement.getPitureFolderName() + "\t对应路径：" + folder.getAbsolutePath();
            DataSourceEditHolder.errorLogging(text, logtext);
            folder.mkdirs();
        }
    }

    private void creatThisFolder() {
        if (getPassingComponentParams() != null && getPassingComponentParams().getThisPane() != null) {
            String folderNameTemp = "piture" + UUIDUtil.getUUID();
            File pitureRootFolder = ((BaseOperatingPane) getPassingComponentParams().getThisPane()).getImageRootPath();
            thisFolder = new File(pitureRootFolder.getAbsolutePath() + File.separator + folderNameTemp);// 新建该组件的同时也新建一个文件夹
            if (thisFolder.isDirectory() == false) {
                this.thisFolder.mkdirs();
                this.controlElement.setPitureFolderName(folderNameTemp);
            }
        }
    }

    /**
     * 图片存放文件夹
     *
     * @return
     */
    public File getPitureFolder() {
        return thisFolder;
    }

    @Override
    public void deleteFromPanel() {
    }

    public String getPitureName() {
        return this.controlElement.getPitureName();
    }

    public void setPitureName(String pitureName) {
        this.controlElement.setPitureName(pitureName);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        controlElement.setThisName(name);
    }

    @Override
    public PictureControl property() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public PictureControl getControl() {
        // TODO Auto-generated method stub
        return controlElement;
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

    class ControlLabelMenuBarUI extends FlatMenuBarUI {

        public ControlLabelMenuBarUI() {
            super();
        }

        @Override
        public void paint(Graphics graphics, JComponent jComponent) {
            ShineEffect.createShineEffect(graphics, jComponent, ShineEffect.defaultComponentColor);
            super.paint(graphics, jComponent);
        }
    }


}
