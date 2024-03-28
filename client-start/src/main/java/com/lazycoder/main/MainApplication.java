package com.lazycoder.main;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uicodegeneration.component.CodeFormatServiceHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.utils.TheUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * 多个皮肤共用 https://github.com/jsettlers/settlers-remake/issues/268
 */
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan("com.lazycoder.database.dao") // mybatis的接口扫描包
@SpringBootApplication(scanBasePackages = {"com.lazycoder.database.bean", "com.lazycoder.service.service.impl"})
public class MainApplication {

    //真正生产环境运行的代码
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(MainApplication.class);
                springApplicationBuilder.bannerMode(Banner.Mode.OFF);
                springApplicationBuilder.web(WebApplicationType.NONE);
                springApplicationBuilder.headless(false);
                springApplicationBuilder.run(args);

                TheUI.flatLookAndFeel();
                SysFileStructure.copyCoolFormatDll();
                SysFileStructure.generateSysFileStrucure();//以防万一，检测一下基本文件结构有没有损坏，有的话生成基本机构

                if (args.length == 0) {
                    try {
                        FuncitonSelectFrame frame = new FuncitonSelectFrame();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (args.length == 2) {//打开项目文件的操作写这里
                    if ("f4f6223062261a64caafb9aafb5d474e".equals(args[0])) {//打开文件的秘钥
                        new CodeFormatServiceHolder();//在这里初始化代码格式化服务，避免后面等待时间太长
//                        TheUI.flatLookAndFeel();
                        if (new File(args[1]).isDirectory()) {
                            String path = args[1];
                            File proFolder = new File(path);
//                            File parentFolder = proFolder.getParentFile();
                            ProInit proInit = new ProInit();
                            proInit.setProjectName(proFolder.getName());
                            proInit.setProjectParentPath(proFolder.getParent());

                            boolean flag = StartStaticMethod.checkCanUseOrNot(proInit);
                            if (flag == true) {
                                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
                                new CodeGenerationFrame(proInit, CodeGenerationFrame.USER_CODE_GENERATETION_FRAME);

                                if (CodeGenerationFrameHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
                                    CodeGenerationFrameHolder.showErrorListIfNeed("这数据源有点异常喔   (=ω=；)");
                                }
                            }
                        }
                    }
                }
            }
        });
//        NativeInterface.runEventPump();
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(MainApplication.class);
//                springApplicationBuilder.bannerMode(Banner.Mode.OFF);
//                springApplicationBuilder.web(WebApplicationType.NONE);
//                springApplicationBuilder.headless(false);
//                springApplicationBuilder.run(args);
//
//                TheUI.flatLookAndFeel();
//                SysFileStructure.copyCoolFormatDll();
//
//                if (args.length == 0) {
//                    restoreCodeGenerationFrame();
//                }
//            }
//        });
////        NativeInterface.runEventPump();
//    }


    //测试代码
    private static void restoreCodeGenerationFrame() {
        TheUI.flatLookAndFeel();
        File folder = new File("E:\\懒农生成项目\\pro2");
        if (folder != null) {
            ProInit proInit = new ProInit();
            proInit.setProjectName(folder.getName());
            proInit.setProjectParentPath(folder.getParent());
            new CodeFormatServiceHolder();//在这里初始化代码格式化服务，避免后面等待时间太长

            boolean flag = StartStaticMethod.checkCanUseOrNot(proInit);
            if (flag == true) {

                new CodeFormatServiceHolder();//先初始化代码格式化服务
                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空

                new CodeGenerationFrame(proInit, CodeGenerationFrame.USER_CODE_GENERATETION_FRAME);

                if (CodeGenerationFrameHolder.temporaryErrorList.size() > 0) {//如果在执行过程中出现问题，把问题显示出来
                    CodeGenerationFrameHolder.showErrorListIfNeed("这数据源有点异常喔   (=ω=；)");
//                } else {
//                    dispose();
                }
            }
        }
    }


}

