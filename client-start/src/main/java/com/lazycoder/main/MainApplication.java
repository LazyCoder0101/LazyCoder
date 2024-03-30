package com.lazycoder.main;

import com.lazycoder.service.fileStructure.SysFileStructure;
import com.lazycoder.uicodegeneration.component.CodeFormatServiceHolder;
import com.lazycoder.uicodegeneration.component.CodeGenerationFrameHolder;
import com.lazycoder.uicodegeneration.generalframe.CodeGenerationFrame;
import com.lazycoder.uicodegeneration.generalframe.ProInit;
import com.lazycoder.uidatasourceedit.DataSourceEditHolder;
import com.lazycoder.uiutils.utils.TheUI;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


//@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan("com.lazycoder.database.dao") // mybatis的接口扫描包
@SpringBootApplication(scanBasePackages = {"com.lazycoder.database.bean", "com.lazycoder.service.service.impl"})
public class MainApplication {

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

                if (args.length == 0) {
                    try {
                        new FuncitonSelectFrame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (args.length == 2) {//打开项目文件的操作写这里
                    if ("f4f6223062261a64caafb9aafb5d474e".equals(args[0])){//打开文件的秘钥
                        new CodeFormatServiceHolder();//在这里初始化代码格式化服务，避免后面等待时间太长
//                        TheUI.flatLookAndFeel();
                        if (new File(args[1]).isDirectory()){
                            String path = args[1];
                            File proFolder = new File(path);
//                            File parentFolder = proFolder.getParentFile();
                            ProInit proInit = new ProInit();
                            proInit.setProjectName(proFolder.getName());
                            proInit.setProjectParentPath(proFolder.getParent());

                            boolean flag = StartStaticMethod.checkCanUseOrNot(proInit);
                            if (flag == true) {
                                DataSourceEditHolder.temporaryErrorList.clear();//先把临时错误列表清空
                                new CodeGenerationFrame(proInit);

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


}

