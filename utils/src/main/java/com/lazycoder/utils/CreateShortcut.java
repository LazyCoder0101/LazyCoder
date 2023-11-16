package com.lazycoder.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 摘自 https://blog.csdn.net/weixin_43217817/article/details/104821787
 * 更改为管理员身份运行参考 https://blog.csdn.net/ha134ha/article/details/53819393
 * 贺驰宇
 * 2020-3-12
 */
public class CreateShortcut {
    private String prefixFile;
    private HashMap<String, String> pathAndName;
    /**
     * 开机启动目录
     */
//    public final static String startup=System.getProperty("user.home")+
//            "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\";
    /**
     * 桌面目录
     */
//    public final static String desktop= FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"\\";
    /**
     * 文件头，固定字段
     */
    private byte[] headFile = {0x4c, 0x00, 0x00, 0x00,
            0x01, 0x14, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00,
            (byte) 0xc0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x46
    };
    /**
     * 文件头属性 普通快捷方式
     */
//    private byte[] fileAttributes={(byte) 0x93,0x00,0x08,0x00,//可选文件属性
//            0x20, 0x00, 0x00, 0x00,//目标文件属性
//            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//文件创建时间
//            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//文件修改时间
//            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,//文件最后一次访问时间
//            0x00,0x00,0x00,0x00,//文件长度
//            0x00,0x00,0x00,0x00,//自定义图标个数
//            0x01,0x00,0x00,0x00,//打开时窗口状态
//            0x00,0x00,0x00,0x00,//热键
//            0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00//未知
//    };

    //管理员身份运行方式
    private byte[] fileAttributes = {(byte) 0x93, 0x20, 0x08, 0x00,//可选文件属性
            0x20, 0x00, 0x00, 0x00,//目标文件属性
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//文件创建时间
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//文件修改时间
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,//文件最后一次访问时间
            0x00, 0x00, 0x00, 0x00,//文件长度
            0x00, 0x00, 0x00, 0x00,//自定义图标个数
            0x01, 0x00, 0x00, 0x00,//打开时窗口状态
            0x00, 0x00, 0x00, 0x00,//热键
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00//未知
    };
    /**
     * 固定字段1
     */
    byte[] fixedValueOne = {
            (byte) 0x83, 0x00, 0x14, 0x00
            , 0x1F, 0x50, (byte) 0xE0, 0x4F
            , (byte) 0xD0, 0x20, (byte) 0xEA
            , 0x3A, 0x69, 0x10, (byte) 0xA2
            , (byte) 0xD8, 0x08, 0x00, 0x2B
            , 0x30, 0x30, (byte) 0x9D, 0x19, 0x00, 0x2f
    };
    /**
     * 固定字段2
     */
    byte[] fixedValueTwo = {
            0x3A, 0x5C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
            , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
            , 0x00, 0x54, 0x00, 0x32, 0x00, 0x04
            , 0x00, 0x00, 0x00, 0x67, 0x50, (byte) 0x91, 0x3C, 0x20, 0x00
    };

    /**
     * @param prefixFile  需要生成的快捷方式的文件前缀路径
     * @param pathAndName 文件和生成快捷方式的对应关系，k为文件位置，v为生成快捷方式位置
     */
    public CreateShortcut(String prefixFile, HashMap<String, String> pathAndName) {
        this.prefixFile = prefixFile;
        this.pathAndName = pathAndName;
    }

    /**
     * @param path 生成快捷方式前缀路径
     */
    public void start(String path) {
        for (String k : pathAndName.keySet()) {
            String v = pathAndName.get(k);
            start0(path + k, prefixFile + v);
        }
    }

    /**
     * 生成快捷方式
     *
     * @param start  完整的文件路径
     * @param target 完整的快捷方式路径
     */
    public void start0(String start, String target) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(createDirectory(start));
            fos.write(headFile);
            fos.write(fileAttributes);
            fos.write(fixedValueOne);
            fos.write((target.toCharArray()[0] + "").getBytes());
            fos.write(fixedValueTwo);
            fos.write(target.substring(3).getBytes("gbk"));
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解决父路径问题
     */
    public static File createDirectory(String file) {
        File f = new File(file);
        //获取父路径
        File fileParent = f.getParentFile();
        //如果文件夹不存在
        if (fileParent != null && !fileParent.exists()) {
            //创建文件夹
            fileParent.mkdirs();
        }
        return f;
    }
//    public static void main(String[] args) {
//        String fileName="D:\\软件\\本地软件\\";
//        HashMap<String,String> hashMap=new HashMap<String,String>();
//        hashMap.put("360压缩.lnk","360压缩\\360zip.exe");
//        hashMap.put("截包软件.lnk","Fiddler\\Fiddler.exe");
//        hashMap.put("Host快捷修改.lnk","Host快捷修改\\Host快捷修改.exe");
//        hashMap.put("发包软件.lnk","Postman\\Postman.exe");
//        hashMap.put("QQ影音.lnk","QQ影音\\QQPlayer.exe");
//        hashMap.put("SSR.lnk","SSR\\SSTap.exe");
//        hashMap.put("MD文件查看.lnk","Typora\\Typora.exe");
//        hashMap.put("百度思维导图.lnk","百度思维导图\\DesktopNaotu.exe");
//        hashMap.put("百度网盘破解.lnk","百度网盘破解\\PanDownload.exe");
//        hashMap.put("酷狗音乐.lnk","酷狗音乐\\KuGou.exe");
//        hashMap.put("天若OCR文字识别.lnk","天若OCR文字识别\\天若OCR文字识别.exe");
//        hashMap.put("图吧工具箱2019.lnk","图吧工具箱2019\\图吧工具箱2019.exe");
//        hashMap.put("微信.lnk","微信\\WeChat.exe");
//        hashMap.put("文件查找.lnk","文件查找\\Listary.exe");
//        hashMap.put("印象笔记.lnk","印象笔记\\Evernote.exe");
//        hashMap.put("硬盘管理\\硬盘测速.lnk","硬盘管理\\硬盘测速\\DiskMark64.exe");
//        hashMap.put("硬盘管理\\硬盘格式化.lnk","硬盘管理\\硬盘格式化\\DiskGenius.exe");
//        hashMap.put("硬盘管理\\硬盘缓存查看.lnk","硬盘管理\\硬盘缓存查看\\HD Tune Pro.exe");
//        hashMap.put("硬盘管理\\硬盘信息查看.lnk","硬盘管理\\硬盘信息查看\\DiskInfo64.exe");
//        hashMap.put("GHOST备份还原.lnk","GHOST备份还原.exe");
//        hashMap.put("软件卸载.lnk","软件卸载.exe");
//        hashMap.put("文件校验.lnk","文件校验.exe");
//        CreateShortcut shortcut=new CreateShortcut(fileName,hashMap);
//        shortcut.start(CreateShortcut.desktop+"本地软件\\");
//        hashMap=new HashMap<String,String>();
//        hashMap.put("文件查找.lnk","文件查找\\Listary.exe");
//        hashMap.put("天若OCR文字识别.lnk","天若OCR文字识别\\天若OCR文字识别.exe");
//        shortcut=new CreateShortcut(fileName,hashMap);
//        shortcut.start(CreateShortcut.startup);
//    }

    /**
     * 创建快捷方式
     *
     * @param path    快捷方式的路径
     * @param exeName 需要生成快捷方式的文件
     * @param lnkName 快捷方式名称
     */
    public static void createLnk(File path, File exeName, String lnkName) {
        String fileName = path.getAbsolutePath() + File.separator;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(lnkName + ".lnk", exeName.getName());
        CreateShortcut shortcut = new CreateShortcut(fileName, hashMap);
        shortcut.start(fileName);
    }

}

