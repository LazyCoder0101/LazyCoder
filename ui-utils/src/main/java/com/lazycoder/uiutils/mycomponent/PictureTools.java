package com.lazycoder.uiutils.mycomponent;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ztc on 15-11-13.
 */
public class PictureTools {

    public static List<File> fileBuffer = new ArrayList<File>();

    public static void the_clear() {
        fileBuffer.clear();
    }

    public static void addFile(File f) {
        if (!fileBuffer.contains(f)) {
            fileBuffer.add(f);
        }
    }

    public static File getNext(File f) {
        if (fileBuffer.indexOf(f) + 1 < fileBuffer.size()) {
            return fileBuffer.get(fileBuffer.indexOf(f) + 1);
        } else if (fileBuffer.size() > 0) {
            return fileBuffer.get(0);
        }
        return null;
    }

    public static int getNextIndex(File f) {
        if (fileBuffer.indexOf(f) + 1 < fileBuffer.size()) {
            return fileBuffer.indexOf(f) + 1;
        } else if (fileBuffer.size() > 0) {
            return 0;
        }
        return 0;
    }

    public static File getLast(File f) {
        if (fileBuffer.indexOf(f) - 1 >= 0) {
            return fileBuffer.get(fileBuffer.indexOf(f) - 1);
        } else if (fileBuffer.size() > 0) {
            return fileBuffer.get(fileBuffer.size() - 1);
        }
        return null;
    }

    public static int getLastIndex(File f) {
        if (fileBuffer.indexOf(f) - 1 >= 0) {
            return fileBuffer.indexOf(f) - 1;
        } else if (fileBuffer.size() > 0) {
            return fileBuffer.size() - 1;
        }
        return 0;
    }

    public static int getFileIndex(File f) {
        return fileBuffer.indexOf(f);
    }

    public static int getTotalNum() {
        return fileBuffer.size();
    }

    public static void clear() {
        fileBuffer.clear();
    }

    /*
     * 图片文件过滤器
     */
    public static FileFilter myFilter() {
        FileFilter ff = new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png")
                        || name.endsWith(".bmp") || !name.contains(".") || name.endsWith(".JPG")
                        || name.endsWith(".JPEG") || name.endsWith(".GIF") || name.endsWith(".PNG")
                        || name.endsWith(".BMP")) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return "*.jpg|*.jpeg|*.gif|*.bmp|*.png|*.JPG|*.JPEG|*.GIF|*.BMP|*.PNG";
            }
        };
        return ff;
    }

    public static FilenameFilter myFilenameFilter() {
        FilenameFilter ff = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png")
                        || name.endsWith(".bmp") || name.endsWith(".JPG") || name.endsWith(".JPEG")
                        || name.endsWith(".GIF") || name.endsWith(".PNG") || name.endsWith(".BMP")) {
                    return true;
                }
                return false;
            }
        };
        return ff;
    }

}
