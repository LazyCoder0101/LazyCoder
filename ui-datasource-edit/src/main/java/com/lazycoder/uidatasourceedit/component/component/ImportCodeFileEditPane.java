package com.lazycoder.uidatasourceedit.component.component;

import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.format.code.ImportCodeTextPane;
import com.lazycoder.uiutils.mycomponent.LazyCoderFileChooser;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

public class ImportCodeFileEditPane extends BaseUseCodeFileEditPane {

    /**
     *
     */
    private static final long serialVersionUID = -3150160552953262131L;

    protected int currentCodeFileNum = 0;

    protected LazyCoderFileChooser fc;

    public ImportCodeFileEditPane(String labelText) {
        super(labelText);
        fileChooserInit();
        // TODO Auto-generated constructor stub
    }

    protected void fileChooserInit(){
        fc = new LazyCoderFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY); // 只接收文件
        fc.setDialogTitle("请选择需要导入的源码文件");
        fc.setMultiSelectionEnabled(false);// 单选
        currentCodeFileNum = 0;
    }

    protected File selectFile(){
        File selectFile = null;
        int returnVal = fc.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {// 选中并按下确定后
            selectFile = fc.getSelectedFile();
        }
        return selectFile;
    }

    /**
     * 添加空白源文件
     *
     * @param formatCodePane
     * @return
     */
    public boolean createNewBlankSourceFile(ImportCodeTextPane formatCodePane) {
        String fileName = FileUtil.addCodeFile();
        Boolean returnFlag = false;
        if (fileName != null) {
            boolean flag = checkForThisFile(fileName);
            if (flag == true) {
                LazyCoderOptionPane.showMessageDialog(this, "已添加过该文件");
            } else {
                returnFlag = true;
                JScrollPane scrollPane = new JScrollPane(formatCodePane);
                formatCodePane.createNewBlankSourceFile(fileName);
                getTabbedPane().addTab(fileName, scrollPane);
                setSelectedLast();
            }
        }
        return returnFlag;
    }

    /**
     * 打开源文件
     */
    public boolean openSourceFile(ImportCodeTextPane formatCodePane) {
        boolean returnFlag = false;
        File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL, "请选择需要导入的懒农数据源文件");
        if (file != null) {
            boolean flag = checkForThisFile(file.getName());
            if (flag == true) {
                LazyCoderOptionPane.showMessageDialog(this, "已添加过这个源文件了！");
            } else {
                returnFlag = true;
                JScrollPane scrollPane = new JScrollPane(formatCodePane);
                formatCodePane.openSourceFile(file);
                getTabbedPane().addTab(file.getName(), scrollPane);
                setSelectedLast();
            }
        }
        return returnFlag;
    }

}
