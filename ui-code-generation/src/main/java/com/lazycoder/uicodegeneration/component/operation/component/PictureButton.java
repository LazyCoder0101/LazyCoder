package com.lazycoder.uicodegeneration.component.operation.component;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.service.vo.element.lable.control.PictureControl;
import com.lazycoder.service.vo.pathfind.PathFindCell;
import com.lazycoder.uicodegeneration.PathFind;
import com.lazycoder.uicodegeneration.component.operation.container.sendparam.GeneralContainerComponentParam;
import com.lazycoder.uicodegeneration.component.operation.container.OpratingContainerInterface;
import com.lazycoder.uicodegeneration.component.operation.container.pane.AbstractOperatingPane;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.CodeGenerationFormatUIComonentInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.base.FormatStructureModelInterface;
import com.lazycoder.uicodegeneration.proj.stostr.operation.component.PictureMeta;
import com.lazycoder.uiutils.mycomponent.MyButton;
import com.lazycoder.uiutils.mycomponent.PicturesViewer;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * 图片
 *
 * @author admin
 */
public class PictureButton extends MyButton
        implements CodeGenerationFormatUIComonentInterface, CodeGenerationComponentInterface {

    /**
     *
     */
    private static final long serialVersionUID = 7861843417411228133L;

    private static ImageIcon icon = new ImageIcon(
            SysFileStructure.getImageFolder().getAbsolutePath() + File.separator + "code_content_edit" + File.separator + "base_code_content_edit" + File.separator
                    + "label_element" + File.separator + "piture" + File.separator + "piture.png");

    private PictureControl controlElement = new PictureControl();

    private PathFind pathFind;

    private GeneralContainerComponentParam codeGenerationalOpratingContainerParam;

    /**
     * @param codeGenerationalOpratingContainerParam
     * @param controlElement
     */
    public PictureButton(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                         PictureControl controlElement) {
        setIcon(icon);
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
        addActionListener(listener);
    }

    public PictureButton(GeneralContainerComponentParam codeGenerationalOpratingContainerParam,
                         PictureMeta pictureMeta) {
        setIcon(icon);
        // TODO Auto-generated constructor stub
        this.controlElement = pictureMeta.getControlElement();
        this.codeGenerationalOpratingContainerParam = codeGenerationalOpratingContainerParam;
        this.pathFind = pictureMeta.getPathFind();

        setTheSize();
        addActionListener(listener);
    }

    // private int codeSerialNumber;
    private ActionListener listener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String filePath = getPitureFolder().getAbsolutePath();
            File file = new File(filePath);
            if (file.isDirectory() == true) {
                if (file.listFiles().length == 0) {
                    LazyCoderOptionPane.showMessageDialog(null, "这组件没有设置图片。	o(´^｀)o");

                } else if (file.listFiles().length > 0) {
                    new PicturesViewer(filePath, controlElement.getPitureName(), PicturesViewer.DELETE_NOT_STATE, controlElement.getPitureNoteList());
                }
            }
        }
    };

    @Override
    public OpratingContainerInterface getThisOpratingContainer() {
        return this.codeGenerationalOpratingContainerParam.getThisOpratingContainer();
    }

    private void setTheSize() {
        Dimension dd = new Dimension(icon.getIconWidth(), icon.getIconHeight());
        this.setMaximumSize(dd);
        this.setMinimumSize(dd);
        this.setPreferredSize(dd);

    }

    public File getPitureFolder() {
        OpratingContainerInterface opratingContainer = codeGenerationalOpratingContainerParam
                .getThisOpratingContainer();
        File file = new File(opratingContainer.getImageRootPath().getAbsolutePath() + File.separator
                + controlElement.getPitureFolderName());
        return file;
    }

    @Override
    public void setParam(FormatStructureModelInterface model) {
        // TODO Auto-generated method stub
        PictureMeta theModel = (PictureMeta) model;
        theModel.setControlElement(controlElement);
        theModel.setPathFind(pathFind);

    }

    @Override
    public PictureMeta getFormatStructureModel() {
        // TODO Auto-generated method stub
        PictureMeta model = new PictureMeta();
        setParam(model);
        return model;
    }

    @Override
    public void updateValue() {
        // TODO Auto-generated method stub

    }

    @Override
    public void delThis() {
        // TODO Auto-generated method stub

    }

    @Override
    public PictureControl getControlElement() {
        // TODO Auto-generated method stub
        return controlElement;
    }

    @Override
    public PathFind getPathFind() {
        // TODO Auto-generated method stub
        return this.pathFind;
    }

    @Override
    public int getCodeSerialNumber() {
        // TODO Auto-generated method stub
        return this.codeGenerationalOpratingContainerParam.getCodeSerialNumber();
    }

    @Override
    public AbstractOperatingPane getOperatingComponentPlacePane() {
        // TODO Auto-generated method stub
        return this.codeGenerationalOpratingContainerParam.getOperatingComponentPlacePane();
    }

    @Override
    public int getComponentWidth() {
        // TODO Auto-generated method stub
        return icon.getIconWidth();
    }

    @Override
    public void collapseThis() {}

}
