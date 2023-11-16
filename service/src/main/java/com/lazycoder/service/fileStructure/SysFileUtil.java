package com.lazycoder.service.fileStructure;

import com.lazycoder.utils.FileUtil;
import java.io.File;
import java.io.IOException;

public class SysFileUtil {


    /**
     * 数据库图标文件的文件名
     */
    protected static final String DB_ICON_FILE_NAME = "database.ico";

    /**
     * 数据库图标
     */
    public static final File DB_ICO = new File(
            SysFileStructure.getTempFolder().getAbsolutePath() + File.separator + DB_ICON_FILE_NAME);
    /**
     * 懒农图标文件的文件名
     */
    protected static final String LAZY_CODE_ICON_FILE_NAME = "lannong.ico";

    /**
     * 懒农图标
     */
    public static final File LANNONG_ICO = new File(
            SysFileStructure.getTempFolder().getAbsolutePath() + File.separator + LAZY_CODE_ICON_FILE_NAME);

    /**
     * 懒农项目的启动文件
     */
    public static final File PRO_RUN_EXE = new File(
            SysFileStructure.getTempFolder().getAbsolutePath() + File.separator + "LazyCoder.exe");

    /**
     * 第三方开源软件
     */
    public static final File THIRD_PARTY_SOFTWARE = new File(
            SysFileStructure.getTempFolder().getAbsolutePath() +
                    File.separator + "third_party" + File.separator + "third_party_software.html");

    /**
     * 生成设置图标命令的命令行字符
     *
     * @param iconFileName
     * @return
     */
    private static String getSetIconCommand(String iconFileName) {
        String out = "[.ShellClassInfo]\nIconFile=" + iconFileName + "\nIconIndex=0";
        return out;
    }

    /**
     * 生成文件夹图标改成懒农图标的文件
     *
     * @param folder 需要改图标的文件夹
     */
    protected static void generateUpdateLannongIconFile(File folder) {
        File file = new File(folder.getAbsolutePath() + File.separator + "desktop.ini");
        //FileUtil.clearFileHidden(file);
        FileUtil.writeFile(file, getSetIconCommand(LAZY_CODE_ICON_FILE_NAME));
        FileUtil.setFileHidden(file);
    }

    /**
     * 生成文件夹图标改成数据库图标的文件
     *
     * @param folder 需要改图标的文件夹
     */
    protected static void generateUpdateDBIconFile(File folder) {
        File file = new File(folder.getAbsolutePath() + File.separator + "desktop.ini");
        if (file.exists()) {
            file.delete();
        }
        FileUtil.writeFile(file, getSetIconCommand(DB_ICON_FILE_NAME));
        FileUtil.setFileHidden(file);
    }

    /**
     * 设置图标的命令 blog.csdn.net/sinat_33248260/article/details/78688156
     *
     * @param folder
     */
    protected static void commandForSetIcon(File folder) {
        try {
            Runtime.getRuntime().exec("attrib " + folder.getName() + " +s /s /d", null,
                    folder.getParentFile());

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Process process = Runtime.getRuntime().exec("attrib " + folder.getName() + " +s /s /d", null,
//                    folder.getParentFile());
//
//            // 记录dos命令的返回信息
//            StringBuffer resStr = new StringBuffer();
//            // 获取返回信息的流
//            InputStream in = process.getInputStream();
//            Reader reader = new InputStreamReader(in);
//            BufferedReader bReader = new BufferedReader(reader);
//            for (String res = ""; (res = bReader.readLine()) != null; ) {
//                resStr.append(res + "\n");
//            }
//            bReader.close();
//            reader.close();
//            process.getOutputStream().close(); // 不要忘记了一定要关
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

//    protected static File createSetIconBAT(File folder) {
//        String command = "attrib " + folder.getName() + " +s /s /d" + "\n" +
//                "choice /t 5 /d y /n >nul" +
//                "attrib " + folder.getName() + " +s /s /d";//有时候会无法设置图标，延时一段时间再执行一次
//        File batFile = new File(folder.getAbsolutePath() + File.separator + "setIcon.bat");
//        FileUtil.writeFile(batFile, command);
//        return batFile;
//    }

    /**
     * 执行bat文件
     *
     * @param file
     */
//    protected static boolean runBatFile(File file) {
//        if (file.exists()) {
//            String command = "cmd /c " + file.getAbsolutePath();
//            try {
//                Runtime.getRuntime().exec(command);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }

}
