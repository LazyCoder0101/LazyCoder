package com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.control;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.vo.base.BaseOperatingPane;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uidatasourceedit.component.codeintput.inputmeta.pane.base.PassingComponentParams;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.ControlLabelButtonUI;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.FileSelectorLabel;
import com.lazycoder.uidatasourceedit.component.codeintput.intputscutcheon.labelscutcheon.editframe.FileSelectorEditFrame;
import com.lazycoder.utils.UUIDUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import lombok.Setter;


public class FileSelectorControlLabel extends FileSelectorLabel implements ControlLabelInterface, BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 3519711386085192163L;

    private File thisFolder;

    @Setter
    private FileSelectorControl controlElement = new FileSelectorControl();

    /**
     * 新建
     *
     * @param name
     */
    public FileSelectorControlLabel(String name, PassingComponentParams passingComponentParams) {
        // TODO Auto-generated constructor stub
        setPassingComponentParams(passingComponentParams);
        creatThisFolder();
        init(name);
    }

    /**
     * 还原
     *
     * @param controlElement
     * @param passingComponentParams
     */
    public FileSelectorControlLabel(FileSelectorControl controlElement, PassingComponentParams passingComponentParams) {
        // TODO Auto-generated constructor stub
        this.controlElement = controlElement;
        setPassingComponentParams(passingComponentParams);
        init(controlElement.getThisName());
        thisFolder = getFileFolder();
        if (thisFolder.isDirectory() == false && getPassingComponentParams() != null && getPassingComponentParams().getThisPane() != null) {
            File fileSelectFolder = ((BaseOperatingPane) getPassingComponentParams().getThisPane()).getFileSelectorRootPath();
            String text = "奇怪，有个文件选择组件对应的文件夹莫名其妙被删了	╮(╯﹏╰）╭";
            String logtext = "有个文件选择组件的对应文件夹被删，对应文件夹名：" + controlElement.getFolderName() + "\t对应路径：" + fileSelectFolder.getAbsolutePath();
            DataSourceEditHolder.errorLogging(text, logtext);
            thisFolder.mkdirs();
        }

    }

    public void init(String name) {
        setName(name);
        setText(name);
//		setToolTipText(((BaseOperatingPane) getPassingComponentParams().getThisPane()).getFileSelectorRootPath().getAbsolutePath() + File.separator + controlElement.getFolderName());
        setUI(new ControlLabelButtonUI());
        addActionListener(listener);
    }


    private void creatThisFolder() {
        if (getPassingComponentParams() != null && getPassingComponentParams().getThisPane() != null) {
            String folderNameTemp = "file" + UUIDUtil.getUUID();
            File pitureRootFolder = ((BaseOperatingPane) getPassingComponentParams().getThisPane()).getFileSelectorRootPath();
            thisFolder = new File(pitureRootFolder.getAbsolutePath() + File.separator + folderNameTemp);// 新建该组件的同时也新建一个文件夹
            if (thisFolder.isDirectory() == false) {
                this.thisFolder.mkdirs();
                this.controlElement.setFolderName(folderNameTemp);
            }
        }
    }

    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            new FileSelectorEditFrame(FileSelectorControlLabel.this);
        }
    };


    @Override
    public void deleteFromPanel() {
//		if (thisFolder != null) {
//			FileUtil.delFolder(thisFolder.getAbsolutePath());
//		}
    }

    /**
     * 路径
     *
     * @return
     */
    public String getFilePath() {
        return controlElement.getFilePath();
    }

    /**
     * 路径
     *
     * @param filePath
     */
    public void setFilePath(String filePath) {
        controlElement.setFilePath(filePath);
    }

    public void setFolderName(String folderName) {
        controlElement.setFolderName(folderName);
    }

    /**
     * 是否复制文件到源码
     *
     * @return
     */
    public int getCopyFileToSourceOrNot() {
        return controlElement.getCopyFileToSourceOrNot();
    }

    /**
     * 是否复制文件到源码
     *
     * @param copyFileToSourceOrNot
     */
    public void setCopyFileToSourceOrNot(int copyFileToSourceOrNot) {
        controlElement.setCopyFileToSourceOrNot(copyFileToSourceOrNot);
    }

    /**
     * 文件分隔符
     *
     * @return
     */
    public String getFileSeparator() {
        return controlElement.getFileSeparator();
    }

    /**
     * 文件分隔符
     *
     * @param fileSeparator
     */
    public void setFileSeparator(String fileSeparator) {
        controlElement.setFileSeparator(fileSeparator);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        controlElement.setThisName(name);
    }

    @Override
    public FileSelectorControl property() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public FileSelectorControl getControl() {
        return controlElement;
    }


    public File getFileFolder() {
        if (getPassingComponentParams() != null && getPassingComponentParams().getThisPane() != null) {
            File rootFile = ((BaseOperatingPane) getPassingComponentParams().getThisPane()).getFileSelectorRootPath(),
                    fileFolder = new File(rootFile.getAbsolutePath() + File.separator + controlElement.getFolderName());
            return fileFolder;
        }
        return null;
    }

}