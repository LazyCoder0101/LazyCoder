package com.lazycoder.uiutils.mycomponent;

import com.lazycoder.utils.FileUtil;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * 文件选择器，仅设置任务栏图标为懒农图标
 */
public class LazyCoderFileChooser extends JFileChooser {

    public LazyCoderFileChooser() {
        super();
    }

    public LazyCoderFileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
    }

    public LazyCoderFileChooser(File currentDirectory) {
        super(currentDirectory);
    }

    public LazyCoderFileChooser(FileSystemView fsv) {
        super(fsv);
    }

    public LazyCoderFileChooser(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
    }

    public LazyCoderFileChooser(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
    }

    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dialog = super.createDialog(parent);
        dialog.setIconImage(FileUtil.LOGO_IMAGE.getImage());
        return dialog;
    }

}
