package com.lazycoder.utils;

import com.lazycoder.lazycoderbaseconfiguration.LazyCoderBaseConfiguration;
import com.lazycoder.utils.swing.LazyCoderOptionPane;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FileUtil {

    public static final ImageIcon LOGO_IMAGE = new ImageIcon(LazyCoderBaseConfiguration.getImageFolder().getAbsolutePath() + File.separator + "logo.png");

    private static final Logger log = LoggerFactory.getLogger("文件操作通用方法");

    public static final int FILE_ONLY_MODEL = JFileChooser.FILES_ONLY;

    public static final int FILE_AND_DIRECTORY_MODEL = JFileChooser.FILES_AND_DIRECTORIES;

    public static final int DIRECTORY_ONLY_MODEL = JFileChooser.DIRECTORIES_ONLY;

//    public static void main(String[] args) {
//        Runtime rn = Runtime.getRuntime();
//        Process p = null;
//        String[] parm = {"D:/LazyCoder/懒农.exe","fada fdsds"};
//        try {
//            p = rn.exec(parm);//执行exe
//        } catch (Exception e) {
//            System.out.println("Error exec!");
//        }
//    }

    /**
     * 选择文件（单选）
     */
    public static File selectFile(int fileSelectionMode, String dialogTitle) {
        File selectFile = null;
        JFileChooser fc = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setIconImage(LOGO_IMAGE.getImage());
                return dialog;
            }
        };
        fc.setDialogTitle(dialogTitle);
        fc.setFileSelectionMode(fileSelectionMode); // 只接收文件
        fc.setMultiSelectionEnabled(false);// 单选
        int returnVal = fc.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {// 选中并按下确定后
            selectFile = fc.getSelectedFile();
//			if (file.isFile()) {
//				
//			}
        }
        return selectFile;
    }

    /**
     * 选择文件（多选 ）
     *
     * @param fileSelectionMode
     * @param dialogTitle
     * @return
     */
    public static File[] selectFileList(int fileSelectionMode, String dialogTitle) {
        File[] selectFiles = null;
        JFileChooser fc = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setIconImage(LOGO_IMAGE.getImage());
                return dialog;
            }
        };
        fc.setDialogTitle(dialogTitle);
        fc.setFileSelectionMode(fileSelectionMode); // 只接收文件
        fc.setMultiSelectionEnabled(true);// 多选
        int returnVal = fc.showOpenDialog(fc);
        if (returnVal == JFileChooser.APPROVE_OPTION) {// 选中并按下确定后
            selectFiles = fc.getSelectedFiles();
        }
        return selectFiles;
    }

    public static String addCodeFile() {
        String fileName = LazyCoderOptionPane.showInputDialog(null, "请输入文件名：\n", "添加源文件", JOptionPane.PLAIN_MESSAGE);
        return fileName;
    }

    /**
     * 用filechannel进行文件复制
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     */
    public static void fileCopyNormal(File fromFile, File toFile) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannelInput = null;
        FileChannel fileChannelOutput = null;
        try {
            fileInputStream = new FileInputStream(fromFile);
            fileOutputStream = new FileOutputStream(toFile);
            // 得到fileInputStream的文件通道
            fileChannelInput = fileInputStream.getChannel();
            // 得到fileOutputStream的文件通道
            fileChannelOutput = fileOutputStream.getChannel();
            // 将fileChannelInput通道的数据，写入到fileChannelOutput通道
            fileChannelInput.transferTo(0, fileChannelInput.size(), fileChannelOutput);

        } catch (FileNotFoundException e) {
            log.error("fileCopyNormal方法报错（FileNotFoundException）：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("fileCopyNormal方法报错：" + e.getMessage());
            e.printStackTrace();

        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileChannelInput != null) {
                    fileChannelInput.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileChannelOutput != null) {
                    fileChannelOutput.close();
                }
            } catch (IOException e) {
                log.error("fileCopyNormal方法报错，无法关闭" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 清空已有的文件内容，以便下次重新写入新的内容
    public static void clearInfoForFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            log.error("clearInfoForFile方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 把文件夹里面的文件和文件夹复制过去
     *
     * @param sourcePath
     * @param newPath
     * @throws IOException
     */
    public static void copyDirIn(String sourcePath, String newPath) throws IOException {
        File sourcefile = new File(sourcePath), toFile = new File(newPath);
        String[] filePath = sourcefile.list();

        if (!(toFile).exists()) {
            (toFile).mkdir();
        }

        String fileStr;
        for (int i = 0; i < filePath.length; i++) {
            fileStr = filePath[i];
            if ((new File(sourcePath + File.separator + fileStr)).isDirectory()) {
                copyDirIn(sourcePath + File.separator + fileStr, newPath + File.separator + fileStr);
            }
            if (new File(sourcePath + File.separator + fileStr).isFile()) {
                fileCopyNormal(new File(sourcePath + File.separator + fileStr),
                        new File(newPath + File.separator + fileStr));
            }
        }
    }

    /**
     * 复制文件夹和文件夹里面的内容到里面
     *
     * @param sourcePath
     * @param newPath
     * @throws IOException
     */
    public static void copyDir(String sourcePath, String newPath) throws IOException {
        File sourcefile = new File(sourcePath);
        // toFile = new File(newPath);
        String fileName = sourcefile.getName();

        if (sourcefile.isDirectory()) {
            copyDirIn(sourcePath, newPath + File.separator + fileName);
        } else if (sourcefile.isFile()) {
            fileCopyNormal(sourcefile, new File(newPath + File.separator + fileName));
        }
    }

    // 获取文件字符个数
    public static int getFileNum(File file) {
        int fileCharNum = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            // TODO 自动生成的 catch 块
            log.error("getFileNum方法报错，没有发现该文件：" + e1.getMessage());
            e1.printStackTrace();
        }
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(fis, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(read);
        String s = null;
        StringBuilder ss = new StringBuilder();
        try {
            while ((s = br.readLine()) != null) {
                ss.append(s + "\n");
            }
            ss.deleteCharAt(ss.length() - 1);
            fileCharNum = ss.length();

            br.close();
            read.close();
            fis.close();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            log.error("getFileNum方法报错：" + e.getMessage());
            e.printStackTrace();
        }
        return fileCharNum;
    }

    // 追加内容
    public static void appendFile(File file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            out.write(conent);
        } catch (Exception e) {
            log.error("appendFile方法报错：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("appendFile方法报错，无法关闭BufferedWriter：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // 把内容写到文件，覆盖原有文件
    public static void writeFile(File file, String fileContent) {
        try {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);

            writer.write(fileContent);
            writer.close();
            write.close();
        } catch (Exception e) {
            log.error("writeFile方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    // 把内容写到文件，覆盖原有文件
    public static void writeFile(File file, String fileContent, String codingFormat) {
        try {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), codingFormat);
            BufferedWriter writer = new BufferedWriter(write);

            writer.write(fileContent);
            writer.close();
            write.close();
        } catch (Exception e) {
            log.error("writeFile方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹及里面所有文件
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            log.error("delFolder方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取某路径的文件的内容
     */
    public static String getFileContent(String path) {
        File file = new File(path);
        FileInputStream fis;
        StringBuilder ss = new StringBuilder();
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                InputStreamReader read = new InputStreamReader(fis, "UTF-8");
                BufferedReader br = new BufferedReader(read);
                String s;
                while ((s = br.readLine()) != null) {
                    ss.append(s + "\n");
                }
                if (ss.length() >= 1) {
                    ss.deleteCharAt(ss.length() - 1);
                }
                br.close();
                read.close();
                fis.close();
            }
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            log.error("getFileContent方法报错" + e.getMessage());
            e.printStackTrace();
        }
        return ss.toString();
    }


    /**
     * https://blog.csdn.net/hello_word2/article/details/52677667 设置文件为隐藏文件
     */
    public static void setFileHidden(File file) {
        try {
            // R ： 只读文件属性。A：存档文件属性。S：系统文件属性。H：隐藏文件属性。
            String sets = "attrib +H \"" + file.getAbsolutePath() + "\"";
            // 运行命令
            Runtime.getRuntime().exec(sets);

        } catch (IOException e) {
            log.error("setFileHidden方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 取消隐藏属性
     *
     * @param file
     */
    public static void clearFileHidden(File file) {
        try {
            String sets = "cmd /c attrib " + file.getAbsolutePath() + " -H";
            // 运行命令
            Runtime.getRuntime().exec(sets);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            log.error("clearFileHidden方法报错：" + e2.getMessage());
            e2.printStackTrace();
        }
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileEx(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取文件后缀（无.）
     *
     * @param fileName
     * @return
     */
    public static String getFileExNoPoint(String fileName) {
        String tempString = fileName.substring(fileName.indexOf("."));
        return tempString.substring(1);
    }

    /**
     * 判断是否有写后缀，且后缀是否合法
     *
     * @param fileName
     * @return ture:有后缀，且合法。 false：无后缀
     */
    public static boolean haveExOrNot(String fileName) {
        boolean flag = true;
        if (fileName == null) {
            flag = false;
        } else {
            if (fileName.contains(".")) {
                try {
                    String tempString = getFileExNoPoint(fileName);
                    if (tempString == null || "".equals(tempString.trim())) {
                        flag = false;
                    } else if (StringUtil.isSpecialChar(tempString)) {
                        flag = false;
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // TODO: handle exception
                    flag = false;
                    log.error(fileName + "haveExOrNot方法报错" + e.getMessage());
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }


    /**
     * 获取文件名（无后缀）
     *
     * @param fileName
     * @return
     */
    public static String getFileNameNoEx(String fileName) {
        String out = fileName;
        if (fileName != null && !("".equals(fileName))) {
            out = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return out;
    }

    /**
     * 判断文件名是否合法（摘自https://www.cnblogs.com/interdrp/p/5578557.html）
     *
     * @param fileName
     * @return
     */
    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255) {
            return false;
        } else {
            return fileName.matches(
                    "[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
        }
    }


    /**
     * 获取对应路径指定后缀的所有文件
     *
     * @param file
     * @param suffix
     * @return
     */
    public static ArrayList<String> getSuffixFileList(File file, String suffix) {
        ArrayList<String> data = new ArrayList<String>();
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            if (fs != null) {
                for (File temp : fs) {
                    if (temp.isFile() == true) {
                        if (temp.getName().endsWith(suffix)) {
                            data.add(temp.getName());
                        }
                    }
                }
            }
        }
        return data;
    }

    /**
     * 选中某个文件并打开其所在文件夹
     *
     * @param file
     */
    public static void openAndSelect(File file) {
        try {
            String sets = "explorer /e,/select," + file.getAbsolutePath();
            // 运行命令
            Runtime.getRuntime().exec(sets);

        } catch (IOException e) {
            log.error("openAndSelect方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 访问resources文件夹下的静态文件（转成字符串）
     *
     * @return
     */
    public static String getStaticResourceStr(String path) {
        Resource resource = new ClassPathResource(path);
        String result = "";
        try {
            result = new BufferedReader(new InputStreamReader(resource.getInputStream()))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            log.error("getStaticResourceStr方法报错:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static InputStream getStaticResource(String path) {
        Resource resource = new ClassPathResource(path);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 访问resources文件夹下的静态文件（转成字符串）
     *
     * @return
     */
    public static BufferedReader getStaticResourceBr(String path) {
        Resource resource = new ClassPathResource(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        } catch (IOException e) {
            log.error("getStaticResourceBr方法报错：" + e.getMessage());
            e.printStackTrace();
        }
        return br;
    }

    /**
     * 强制性删除文件（文件夹）
     * @param file
     */
    public static void mandatory_delete_file(File file) {
        Process process;
        try {
            if (file.isFile()){
                String sets = "cmd /c del/f/s/q \"" + file.getAbsolutePath() + "\"";
                // 运行命令
                process = Runtime.getRuntime().exec(sets);
                process.waitFor();
            }else if(file.isDirectory()){
                String sets = "cmd /c rd/s/q \"" + file.getAbsolutePath() + "\"";
                // 运行命令
                process = Runtime.getRuntime().exec(sets);
                process.waitFor();
            }
        } catch (IOException e) {
            if (file.isFile()) {
                log.error("暴力删除文件" + file.getAbsolutePath() + "报错：" + e.getMessage());

            } else if (file.isDirectory()) {
                log.error("暴力删除文件夹" + file.getAbsolutePath() + "报错：" + e.getMessage());

            }
            e.printStackTrace();
        }catch (InterruptedException e1){
            log.error("暴力删除" + file.getAbsolutePath() + "过程中，命令行执行报错：" + e1.getMessage());
        }
    }

}
