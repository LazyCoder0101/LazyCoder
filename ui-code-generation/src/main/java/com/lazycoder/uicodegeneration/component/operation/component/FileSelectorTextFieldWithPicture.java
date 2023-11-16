package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.database.model.BaseModel;
import com.lazycoder.service.fileStructure.SourceGenerateFileStructure;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.element.lable.control.FileSelectorControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.component.FileSelectorHolder;
import com.lazycoder.uicodegeneration.component.generalframe.codeshown.CodeGenerationStaticFunction;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.generalframe.tool.FileRecord;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.FileSelectorMeta;
import com.lazycoder.utils.FileUtil;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * 文件选择
 *
 * @author admin
 */
public class FileSelectorTextFieldWithPicture extends JTextField implements CodeGenerationComponentInterface,
        CodeGenerationFormatUIComonentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7369556653782184293L;

    private File oldFile = null;

    private FileSelectorControl controlElement = new FileSelectorControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    public FileSelectorTextFieldWithPicture(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                            FileSelectorControl controlElement) {
        setEditable(false);
        // TODO Auto-generated constructor stub
        this.controlElement = controlElement;
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        // this.codeSerialNumber = codeSerialNumber;
        PathFindCell pathFindCell = new PathFindCell(codeGenerationalOpratingContainerParam.getCodeSerialNumber(),
                controlElement, codeGenerationalOpratingContainerParam.getPaneType());
        PathFind pathFindTemp = new PathFind(codeGenerationalOpratingContainerParam.getParentPathFind());
        pathFindTemp.getPathList().add(pathFindCell);
        this.pathFind = pathFindTemp;
        setTheSize();

        setToolTipText("点击添加文件");
        copyAndShowDeafaultFile();
        addMouseListener(adapter);
    }

    public FileSelectorTextFieldWithPicture(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                                            FileSelectorMeta fileSelectorMeta) {
        setEditable(false);
        // TODO Auto-generated constructor stub
        this.controlElement = fileSelectorMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = fileSelectorMeta.getPathFind();
        setTheSize();

        setToolTipText("点击添加文件");
        restore(fileSelectorMeta);
        addMouseListener(adapter);
    }

    private void restore(FileSelectorMeta fileSelectorMeta) {
        if (BaseModel.TRUE_ == controlElement.getCopyFileToSourceOrNot()) {//如果需要把文件复制到源码
            setBorder(BorderFactory.createRaisedBevelBorder());
            if ("".equals(fileSelectorMeta.getSelectFileName())) {
                setText(fileSelectorMeta.getSelectFileName());
                oldFile = null;
            } else {
                File proFolder = SourceGenerateFileStructure.getTheProjectPathInGenerateSourcePutPath(
                        CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName),
                        toFolder = new File(proFolder.getAbsolutePath() + File.separator + controlElement.getFilePath()),
                        toFile = new File(toFolder.getAbsolutePath() + File.separator + fileSelectorMeta.getSelectFileName());
                if (toFile.exists() == true) {
                    setText(fileSelectorMeta.getSelectFileName());
                    oldFile = toFile;
                } else {
                    setText(fileSelectorMeta.getSelectFileName());
                    oldFile = null;

                    String text = "奇怪，有个之前选好的文件找不到了	┗( ▔, ▔ )┛";
                    String logtext = "有个文件选择组件选好的文件被删了，对应文件名：" + toFile.getName() + "\t对应路径：" + toFolder.getAbsolutePath();
                    CodeGenerationFrameHolder.errorLogging(text, logtext);
                }
            }
        } else {
            setText(fileSelectorMeta.getSelectFileName());
            oldFile = null;
        }
    }

    private void copyAndShowDeafaultFile() {
        if (BaseModel.TRUE_ == this.controlElement.getCopyFileToSourceOrNot()) {
            setBorder(BorderFactory.createRaisedBevelBorder());
        }

        File fileSelectorFolder = getFileSelectorFolder();
        File[] fileList = fileSelectorFolder.listFiles();
        if (fileList != null) {
            for (File fileTemp : fileList) {
                if (BaseModel.TRUE_ == this.controlElement.getCopyFileToSourceOrNot()) {
                    FileSelectorHolder.addFileRecord(fileTemp.getName(), controlElement.getFilePath());
                    copyFile(fileTemp);

                }
                updateValue();
            }
        }
    }

    private void copyFile(File sourceFile) {
        if (controlElement.getCopyFileToSourceOrNot() == FileSelectorControl.TRUE_) {
            File proFolder = SourceGenerateFileStructure.getTheProjectPathInGenerateSourcePutPath(
                    CodeGenerationFrameHolder.projectParentPath, CodeGenerationFrameHolder.projectName),
                    toFolder = new File(proFolder.getAbsolutePath() + File.separator + controlElement.getFilePath()),
                    toFile = new File(toFolder.getAbsolutePath() + File.separator + sourceFile.getName());
            if (toFolder.isDirectory() == false) {
                toFolder.mkdirs();
            }
            try {
                FileUtil.fileCopyNormal(sourceFile, toFile);
                oldFile = toFile;
                setText(sourceFile.getName());
            } catch (Exception e) {
                // TODO Auto-generated catch block
//				e.printStackTrace();
                LazyCoderOptionPane.showMessageDialog(this, "这个文件不能选，换一个吧	ψ(*｀ー´)ψ");
                SysService.SYS_SERVICE_SERVICE.log_error(FileSelectorTextFieldWithPicture.class +
                        " 的copyFile方法出错" +
                        e.getMessage());
            }
        }
    }

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    private MouseAdapter adapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            super.mouseClicked(e);
            File file = FileUtil.selectFile(FileUtil.FILE_ONLY_MODEL, "请选择需要添加的文件");
            if (file != null) {
                if (BaseModel.TRUE_ == controlElement.getCopyFileToSourceOrNot()) {
                    String oldFileName = "";
                    if (oldFile != null) {
                        oldFileName = oldFile.getName();
                    }
                    int flag = FileSelectorHolder.changeFileRecord(file.getName(), controlElement.getFilePath(), oldFileName,
                            controlElement.getFilePath());
                    if (flag == 0) {
                        if (oldFile != null) {
                            oldFile.delete();
                        }
                    }
                    copyFile(file);
                }
                String pathTemp = controlElement.getCodeInputPath(
                        controlElement.getFilePath(), controlElement.getCodeInputShieldingPath(), controlElement.getFileSeparator());
                String value = pathTemp == null || pathTemp.equals("") ?
                        file.getName() : pathTemp + controlElement.getFileSeparator() + file.getName();
                setText(value);
                updateValue();
            }
        }
    };


    private void setTheSize() {
        Dimension dd = new Dimension(200, 30);
        setMaximumSize(dd);
        setMinimumSize(dd);
        setPreferredSize(dd);
    }

    /**
     * 获取懒农项目文件的对应文件设置文件夹
     *
     * @return
     */
    public File getFileSelectorFolder() {
        OpratingContainerInterface opratingContainer = codeGenerationalOpratingContainerParam
                .getThisOpratingContainer();
        File file = opratingContainer.getFileSelectorRootPath(),
                fileSelectorFolder = new File(file.getAbsolutePath() + File.separator + controlElement.getFolderName());
        if (file.isDirectory() == false) {
            String text = "奇怪，有个文件选择组件对应的文件夹莫名其妙被没了	╮(╯﹏╰）╭";
            String logtext = "有个文件选择组件的对应文件夹被删，对应文件夹名：" + controlElement.getFolderName() + "\t对应路径：" + file.getAbsolutePath();
            CodeGenerationFrameHolder.errorLogging(text, logtext);

            file.mkdirs();
        }
        return fileSelectorFolder;
    }


    @Override
    public void updateValue() {
        // TODO Auto-generated method stub
//		String pathTemp = controlElement.getFilePath();
//		String thePath=controlElement.getCodeInputPath(controlElement.getFilePath(),controlElement.getCodeInputShieldingPath(),controlElement.getFileSeparator());
//		String thePath = pathTemp.replace(System.getProperty("file.separator"), controlElement.getFileSeparator());
//		String value = thePath + controlElement.getFileSeparator() + getText();
        CodeGenerationStaticFunction.setValue(this, codeGenerationalOpratingContainerParam.getPaneType(), getText());
    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub
        if (oldFile != null) {
            FileRecord fileRecord = FileSelectorHolder.getFileRecord(oldFile.getName(), controlElement.getFilePath());
            if (fileRecord != null) {
                int addTimes = fileRecord.getAddTimes();
                FileSelectorHolder.delFileRecord(oldFile.getName(), controlElement.getFilePath());
                if (addTimes == 1) {
                    oldFile.delete();
                }
            }
        }
    }

    @Override
    public FileSelectorControl getControlElement() {
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        return pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        return codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        FileSelectorMeta theModel = (FileSelectorMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);
        theModel.setSelectFileName(getText());

    }

    @Override
    public FileSelectorMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        FileSelectorMeta model = new FileSelectorMeta();
        setParam(model);
        return model;
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return 200;
    }

    @Override
    public void collapseThis() {
    }

}
