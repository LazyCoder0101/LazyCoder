package com.lazycoder.uidatasourceedit;

import com.lazycoder.lazycodercommon.vo.DataSourceLabel;
import com.lazycoder.service.GeneralHolder;
import com.lazycoder.service.fileStructure.DatabaseFileStructure;
import com.lazycoder.service.fileStructure.DatabaseFrameFileMethod;
import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.service.impl.DBChangeServiceImpl;
import com.lazycoder.service.vo.UserDataSourceIdentifyFile;
import com.lazycoder.uiutils.mycomponent.MyIconLabel;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.JsonUtil;
import com.lazycoder.utils.StringUtil;
import com.lazycoder.utils.UUIDUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import lombok.Getter;
import lombok.Setter;


public class DataSourceClassificationFolderPane extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -4770462122448895906L;

    private final ImageIcon dbIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
            + "DataSourceClassificationFolderPane" + File.separator + "database.png"),
            folderIcon = new ImageIcon(SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "DataSourceEdit" + File.separator
                    + "DataSourceClassificationFolderPane" + File.separator + "folder.png");

//    private final Font font = new Font("微软雅黑", Font.PLAIN, 16);

    private JPopupMenu popupMenu;

    private JMenuItem renameMenuItem, delMenuItem, exportDataSourceMenuItem;

    private Box vBox;

    @Getter
    @Setter
    private File currentPath = null;

    /**
     * Create the panel.
     */
    public DataSourceClassificationFolderPane() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        setLayout(fl);
        vBox = Box.createVerticalBox();
        add(vBox);

        popupMenu = new JPopupMenu();
        renameMenuItem = new JMenuItem("重命名");
        renameMenuItem.addActionListener(menuListener);

        delMenuItem = new JMenuItem("删除");
        delMenuItem.addActionListener(menuListener);

        exportDataSourceMenuItem = new JMenuItem("导出该数据源");
        exportDataSourceMenuItem.addActionListener(menuListener);

        popupMenu.add(renameMenuItem);
        popupMenu.add(delMenuItem);
        popupMenu.add(exportDataSourceMenuItem);

        currentPath = SysFileStructure.getFileListFolder();
        updateListPane();
    }

    private static void delDataSource(File file) {
        Boolean flag = false;
        File dbDataFile = null, dbFile = null;
        if (DatabaseFrameFileMethod.isDataSourceFile(file) == true) {
            flag = true;
        }
        if (flag) {
            //在这里如果要删除的sqlite文件和文件夹被占用的话，将无法成功删除，试用过改为暴力删除，还是不行
            String delName = file.getName();
            //FileUtil.delFolder(file.getAbsolutePath());
            File sysFolder = SysFileStructure.getDataFileFolder();
            dbDataFile = DatabaseFileStructure.getDatabaseFileRootPath(sysFolder, delName);
            FileUtil.delFolder(dbDataFile.getAbsolutePath());

            dbFile = SysFileStructure.getSqliteDBFile(delName);
            dbFile.delete();

            FileUtil.delFolder(file.getAbsolutePath());
        }
    }

    private static void delFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File temp : files) {
                if (temp.isDirectory()) {
                    if (DatabaseFrameFileMethod.isDataSourceFile(temp) == true) {
                        delDataSource(temp);
                    } else {
                        delFolder(temp);
                    }
                }
            }
        }
    }

    public void updateListPane() {
        vBox.removeAll();
        if (currentPath != null) {
            ThisLabel label;
            File sysDataFolder = SysFileStructure.getDataFileFolder();
            File dbFile;

            File[] fileList = currentPath.listFiles();
            if (fileList != null) {
                DataSourceLabel dataSourceLabel;
                for (File temp : fileList) {
                    if (DatabaseFrameFileMethod.isDataSourceFile(temp) == true) {
                        dbFile = DatabaseFileStructure.getDatabaseFileRootPath(sysDataFolder, temp.getName());
                        dataSourceLabel = SysService.DB_CHANGE_SERVICE.getDataSourcesInPath(dbFile);
                        label = new DBLabel(dataSourceLabel.getDataSourceName(), dataSourceLabel);
                        vBox.add(label);

                    } else {
                        label = new FolderLabel(temp.getName());
                        vBox.add(label);
                    }
                }
            }
        }
        updateUI();
        repaint();

//        Window window = SwingUtilities.getWindowAncestor(this);
//        if (window != null) {
//            window.validate();
//        }
    }

    /**
     * 重命名文件夹
     */
    private void renameFolder(FolderLabel label) {
        String newName = LazyCoderOptionPane.showInputDialog(null, "请输入新的文件夹名：（原来名称：" + label.getFolderName() + "）\n", "更改文件夹",
                JOptionPane.PLAIN_MESSAGE);
        if (newName != null) {
            if ("".equals((newName.trim())) == true) {
                LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	什么都不写。。。那我就当没事发生了");
            } else {
                File oldFile = new File(getCurrentPath().getAbsolutePath() + File.separator + label.getFolderName()),
                        newFile = new File(getCurrentPath().getAbsolutePath() + File.separator + newName);
                if (newFile.isDirectory() == true) {
                    LazyCoderOptionPane.showMessageDialog(null, "(～￣▽￣)～	看清楚喔，这个文件夹之前早就添加过了");
                } else {
                    if (FileUtil.isValidFileName(newName) == true || StringUtil.isSpecialChar(newName) == false) {
                        oldFile.renameTo(newFile);
                        updateListPane();
                    } else {
                        LazyCoderOptionPane.showMessageDialog(null, "(￣▽￣)／	换个名称吧，这名字。。。我感觉不合适");
                    }
                }
            }
        }
    }

    /**
     * 重命名数据库文件
     *
     * @param label
     */
    private void renameDB(DBLabel label) {
        checkAndDelPossibleFile();
        String newName = LazyCoderOptionPane.showInputDialog(null, "请输入新的数据源名：（原来名称：" + label.getDbName() + "）\n", "更改数据源名称",
                JOptionPane.PLAIN_MESSAGE);
        if (newName != null) {//这里要检测是不是符合文件名，有没有对应数据源
            if (("".equals(newName.trim())) == true) {
                LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	什么都不写。。。那我就当没事发生了");
            } else if (newName.trim().equals(label.getDbName()) == false) {
                if (DBChangeServiceImpl.checkAddDataSourceName(null, newName)) {
                    String oldName = label.getDbName();
                    // 更改显示文件夹对应文件
                    File sysFolder = SysFileStructure.getDataFileFolder(),

                            oldShowFile = new File(getCurrentPath().getAbsolutePath() + File.separator + oldName),
                            newShowFile = new File(getCurrentPath().getAbsolutePath() + File.separator + newName),

                            // 更改用户文件数据对应文件夹
                            oldDataFile = DatabaseFileStructure.getDatabaseFileRootPath(sysFolder, oldName),
                            newDataFile = DatabaseFileStructure.getDatabaseFileRootPath(sysFolder, newName),

                            // 更改sqlite数据库文件
                            oldDBFile = SysFileStructure.getSqliteDBFile(oldName),
                            newDBFile = SysFileStructure.getSqliteDBFile(newName);

                    if (newShowFile.isDirectory() == true || newDataFile.isDirectory() == true || newDBFile.exists() == true) {
                        LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	换个名字吧，起过这名字了");
                    } else {//这里才是真正重命名数据源的操作

                        boolean renameDBFlag = oldDBFile.renameTo(newDBFile);
                        if (renameDBFlag) {//可以重命名对应sqlite数据文件

                            boolean flag1 = oldShowFile.renameTo(newShowFile);
                            boolean flag2 = oldDataFile.renameTo(newDataFile);

                            DataSourceLabel oldDataSourceLabel = label.getDataSourceLabel(),
                                    newDataSourceLabel = new DataSourceLabel();
                            newDataSourceLabel.setDbId(oldDataSourceLabel.getDbId());
                            newDataSourceLabel.setDataSourceName(newName);
                            boolean flag3 = SysService.DB_CHANGE_SERVICE.renameDataSource(newDataSourceLabel);

                            if (flag1 && flag2 && flag3) {//重命名过程中没出现问题

                                File file = DatabaseFileStructure.getDBIdentifyFile(
                                        DatabaseFileStructure.getDatabaseFileRootPath(SysFileStructure.getDataFileFolder(), newName));
                                String dbId = null;
                                String cliVer = null;
                                if (file.exists()) {
//                                    UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.toBean(
//                                            FileUtil.getFileContent(file.getAbsolutePath()),
//                                            UserDataSourceIdentifyFile.class);
                                    UserDataSourceIdentifyFile olduserDataSourceIdentifyFile = JsonUtil.fileToBean(
                                            file.getAbsolutePath(),
                                            UserDataSourceIdentifyFile.class);
                                    dbId = olduserDataSourceIdentifyFile.getDbId();
                                    cliVer = olduserDataSourceIdentifyFile.getCliVer();
                                }
                                dbId = dbId == null ? UUIDUtil.getUUID() : dbId;
                                file.delete();

                                UserDataSourceIdentifyFile userDataSourceIdentifyFile = new UserDataSourceIdentifyFile(newName, dbId, cliVer);
//                                FileUtil.writeFile(file, JsonUtil.getJsonStr(userDataSourceIdentifyFile));
                                JsonUtil.writeFile(file, userDataSourceIdentifyFile);
                                FileUtil.setFileHidden(file);
                            } else {
                                newShowFile.renameTo(oldShowFile);
                                newDataFile.renameTo(oldDataFile);
                                newDBFile.renameTo(oldDBFile);
                                LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ 不知道为什么，改不了这数据源的名字了！");
                            }
                            updateListPane();
                        } else {//无法重命名sqlite文件，这个数据源应该是本次程序运行的时候新建的
                            LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ 亲，真的要给这数据源改名吗？要不再考虑一下，等下次再看看是不是要改名吧");
                        }
                    }
                }
            }
        }
    }

    private void delDB(DBLabel label) {
        String delName = label.getDbName();
        int n = LazyCoderOptionPane.showConfirmDialog(null, "真的要删除\"" + delName + "\"这个数据源吗?", "确认一下",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.OK_OPTION) {
//            SysService.DB_CHANGE_SERVICE.changeSYSDB();
            SysService.DB_CHANGE_SERVICE.delDataBase(label.dataSourceLabel.getDbId());
            File file = new File(getCurrentPath().getAbsolutePath() + File.separator + delName);
            delDataSource(file);
            updateListPane();
            SysService.DB_CHANGE_SERVICE.delDataBase(label.dataSourceLabel.getDbId());
//            File dbFile = SysFileStructure.getSqliteDBFile(delName);
//            dbFile.delete();
        }
    }

    private void delFolder(FolderLabel label) {
        String delName = label.getFolderName();
        int n = LazyCoderOptionPane.showConfirmDialog(null, "(￣ェ￣;)  真的要删除\"" + delName + "\"这个文件夹吗，\n删掉的话，文件夹里面的数据源也会一起删掉的喔",
                "确认一下", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.OK_OPTION) {
            File delFolder = new File(getCurrentPath().getAbsolutePath() + File.separator + delName);
            delFolder(delFolder);
            FileUtil.delFolder(delFolder.getAbsolutePath());
            updateListPane();
            checkAndDelPossibleFile();
        }
    }

    private ActionListener menuListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == renameMenuItem) {
                if (popupMenu.getInvoker() instanceof FolderLabel) {
                    renameFolder((FolderLabel) popupMenu.getInvoker());
                } else if (popupMenu.getInvoker() instanceof DBLabel) {
                    renameDB((DBLabel) popupMenu.getInvoker());
                }
            } else if (e.getSource() == delMenuItem) {
                if (popupMenu.getInvoker() instanceof FolderLabel) {
                    delFolder((FolderLabel) popupMenu.getInvoker());
                } else if (popupMenu.getInvoker() instanceof DBLabel) {
                    delDB((DBLabel) popupMenu.getInvoker());
                }
            } else if (e.getSource() == exportDataSourceMenuItem) {
                if (exportDataSourceMenuItem.isEnabled()) {
                    if (popupMenu.getInvoker() instanceof DBLabel) {
                        exportDataSource((DBLabel) popupMenu.getInvoker());
                    }
                }
            }
        }
    };

    private MouseAdapter dbSourceAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                DBLabel label = (DBLabel) e.getSource();
                clickDBLable(label);
            }
        }
    };

    /**
     * 这里是为了对目前在实际情况中遇到的两种问题而做的处理
     *
     */
    public void checkAndDelPossibleFile() {
        //之前删除数据源的时候，应该是当时数据源文件存放文件夹，里面对应的数据源文件夹被程序占用，导致当时可能删除存有残余文件，在这里进行残余文件删除处理（如果在调用此方法时残余文件依然被占用，哪怕使用暴力删除可能也不行）
        ArrayList<File> list = DatabaseFrameFileMethod.getRubbishDataFile();
        if (list.size() > 0) {
            File dbFile;
            for (File file : list) {
                //FileUtil.delFolder(file.getAbsolutePath());
                FileUtil.mandatory_delete_file(file);

                dbFile = SysFileStructure.getSqliteDBFile(file.getName());
                if (dbFile.exists()) {
                    //dbFile.delete();
                    FileUtil.mandatory_delete_file(dbFile);
                }
            }
        }

        //之前删除数据源的时候，由于当时sqlite数据源文件所有连接未得到释放，导致，当时删除sqlite文件没能删除成功，在这里在次尝试删除
        File sysDataFolder = SysFileStructure.getDataFileFolder();
        String dbTempName;
        File currentDataFile;
        File[] userSqliteList = SysFileStructure.getUserDBFolder().listFiles();
        for(File userSqliteFile:userSqliteList){
            dbTempName = FileUtil.getFileNameNoEx(userSqliteFile.getName());
            currentDataFile = new File(sysDataFolder.getAbsolutePath()+File.separator+dbTempName);
            if (currentDataFile.isDirectory()==false){
                //userSqliteFile.delete();
                FileUtil.mandatory_delete_file(userSqliteFile);//经实际运行测试，在这里使用暴力删除也没法删除对应在占用中的sqlite文件，已多次调整程序，目前无法处理
            }
        }
    }

    private MouseAdapter folderAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                FolderLabel label = (FolderLabel) e.getSource();
                currentPath = new File(currentPath.getAbsolutePath() + File.separator + label.getFolderName());
                updateListPane();
            }
        }
    };

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.isMetaDown()) {// 检测鼠标右键单击
                Component label = e.getComponent();
                if (label instanceof FolderLabel) {
                    exportDataSourceMenuItem.setEnabled(false);
                } else if (label instanceof DBLabel) {
                    exportDataSourceMenuItem.setEnabled(true);
                }
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

    /**
     * 导出用户数据源文件
     *
     * @param label
     */
    private void exportDataSource(DBLabel label) {
        DataSourceLabel dataSourceLabel = label.getDataSourceLabel();
        //先看看这个数据源能不能用，不能用不给导出
        boolean flag = SysService.DB_CHANGE_SERVICE.getEnabledState(dataSourceLabel);
        if (flag == true) {//无法使用的数据源不给导出
            String newDSName = LazyCoderOptionPane.showInputDialog(this, "请输入导出数据源的新名称", dataSourceLabel.getDataSourceName());
            if (newDSName != null && "".equals(newDSName) == false) {
                File pathFolder = FileUtil.selectFile(FileUtil.DIRECTORY_ONLY_MODEL, "导出路径");
                if (pathFolder != null) {
                    File exPath = new File(pathFolder.getAbsolutePath() + File.separator + newDSName);
                    if (exPath.isDirectory() == true) {
                        LazyCoderOptionPane.showMessageDialog(null, "(^_−)☆	换个名字或者重新换个路径吧，这路径有这文件夹了");
                    } else {
                        boolean theFlag = SysService.DB_CHANGE_SERVICE.exportUserDataSource(dataSourceLabel.getDataSourceName(), newDSName, exPath);
                        if (theFlag == true) {
                            LazyCoderOptionPane.showMessageDialog(null, "已导出数据源！");
                            FileUtil.openAndSelect(new File(pathFolder.getAbsolutePath() + File.separator + newDSName));
                        }
                    }
                }
            }
        } else {
            LazyCoderOptionPane.showMessageDialog(null, "(～￣▽￣)～	这数据源还有很多内容没写好，等填完了再导出吧");
        }
    }

    /**
     * 点击对应数据源标签
     */
    protected void clickDBLable(DBLabel label) {
        GeneralHolder.currentUserDataSourceLabel = label.dataSourceLabel;
        GeneralHolder.changeUserDB(label.getDataSourceLabel());

        //DataSourceEditFrame frame = new DataSourceEditFrame();
        DataSourceEditHolder.dataSourceEditFrame = new DataSourceEditFrame();
    }

    class DBLabel extends ThisLabel {

        private static final long serialVersionUID = -7715260060203147355L;

        @Getter
        private DataSourceLabel dataSourceLabel = null;

        public DBLabel(String text, DataSourceLabel dataSourceLabel) {
            super(text, dbIcon);
            this.dataSourceLabel = dataSourceLabel;
            addMouseListener(dbSourceAdapter);
            // TODO Auto-generated constructor stub
        }

        public String getDbName() {
            return textLabel.getText();
        }

    }

    class FolderLabel extends ThisLabel {

        private static final long serialVersionUID = -1367638874303152532L;

        public FolderLabel(String text) {
            super(text, folderIcon);
            addMouseListener(folderAdapter);
            // TODO Auto-generated constructor stub
        }

        public String getFolderName() {
            return textLabel.getText();
        }

    }

    class ThisLabel extends MyIconLabel {

        private static final long serialVersionUID = 7799815936106676076L;

        private Dimension dimension = new Dimension(315, 50);

        public ThisLabel(String text, ImageIcon icon) {
            // TODO Auto-generated constructor stub
            init(text, icon);
            setPreferredSize(dimension);
            addMouseListener(mouseAdapter);
        }

    }

}
