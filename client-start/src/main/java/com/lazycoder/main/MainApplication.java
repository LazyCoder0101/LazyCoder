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

/**
 * -Dnashorn.args=--language=es6
 * <p>
 * -Xms512m -Xmx1024m -XX:PermSize=512m -XX:MaxNewSize=512m -XX:MaxPermSize=512m
 * <p>
 * 多个皮肤共用 https://github.com/jsettlers/settlers-remake/issues/268
 * <p>
 * 并且把application-db.yml的userdb url 换掉
 * <p>
 * sqlite后面替换https://blog.csdn.net/LoFyLoveXu/article/details/39637727
 * <p>
 * 后面代码生成的检测项目文件是否完整那里要优化，根据生成的root文件，找出有什么文件没有的，
 */
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan("com.lazycoder.database.dao") // mybatis的接口扫描包
@SpringBootApplication(scanBasePackages = {"com.lazycoder.database.bean", "com.lazycoder.service.service.impl"})
public class MainApplication {

    /**
     * 检查一下更改分类、模块、删除分类、模块，会涉及什么需要更改，比如有些接口是获取分类名和模块名的，改成id
     * <p>
     * 更改分类、模块、删除分类、模块时，检测当前编辑的分类是不是这个，如果是，不给删
     * <p>
     * 导入数据源的时候要检查一下，看看有没有这个对应的userid ，有的话，不给导入，让它重新删掉再导入一份
     * <p>
     * 把other全部改成additional
     * <p>
     * js替换方案 https://www.it1352.com/2280867.html
     * <p>
     * 看看生成代码那里，要生成对应的代码文件，并且删除时要删掉
     * <p>
     * <p>
     * 自动检测系统内存，并且给出提示要做
     * <p>
     * 代码生成那里要检测，如果要删模块，要先看看有没有模块是一定要用它的，给出提示
     * <p>
     * 开始去掉数据库里面的模块和分类字段
     * <p>
     * 后面改模块名，改包名，lannong全部改成lazycoder
     * <p>
     * <p>
     * 看看能不能优化需要使用菜单
     * <p>
     * 开始规范变量命名
     * <p>
     * 数据编辑那里，把必填模板，可选模板和模块的代码面板整合，至少起名字检测那里要统一，不要重复造轮
     * <p>-
     * 生成文件时要按照对应编码格式来生成
     * <p>
     * 看看数据库编辑那里继承保存的接口，看看哪些要删
     * <p>
     * 检查标记和标签有什么要补充的，比如检查，例如自定义变量和自定义方法就还没写按默认变量名自动生成名字
     *
     * 还有显示的可以添加的文件，没有出现需要使用的模块对应的代码面板！！！！！
     * <p>
     * <p>https://download.csdn.net/download/martairosy/3783115?spm=1001.2014.3001.5503
     *
     * formatOperating那个数据库表要改掉里面其他序号那个字段名
     *
     * 修改字节码 https://blog.csdn.net/luyinchangdejiqing/article/details/53084830?ops_request_misc=&request_id=&biz_id=102&utm_term=java%20%E4%BF%AE%E6%94%B9%E5%AD%97%E8%8A%82%E7%A0%81&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~sobaiduweb~default-7-53084830.pc_v2_rank_dl_default&spm=1018.2226.3001.4450
     */

    /**
     * 待完成工作
     * 1、代码生成那里，在必填模板点击【更多】。然后再一直点击里面的更多，不断尝试，会发现有时关闭后无法把业务功能面板再设置为当前面板
     * 2、数据编辑那里，设置必填模板的默认名称后，再点击模块，会发现里面对应文件没有改为更改后的文件名  √
     * 3、数据编辑的模块编辑，生成程序都要使用该模块的组件去掉，改成【使用设置】，暂定一下选项（多选下拉框），生成程序都要使用该模块、对用户屏蔽（仅作其他模块需要时自动选择使用）   √
     * 4、要做提示页面
     * 5、代码生成的容器中，获取放置面板的背景颜色的发方法要改  √
     * 6、必填模板改成必填模板，可选模板改成可选模板  √
     * 7、代码生成的初始化按钮，对应展开的面板高度要加长  √
     * 8、代码生成的格式容器，格式容器以及模块格式容器，要自适应高度  √
     * 9、再挣扎一下，看看能不能把代码生成的功能容器，默认和隐藏面板改成圆角多余部分没有瑕疵
     * 10、数据编辑部分，分割面板改成可活动  √
     * 11、数据编辑部分要做美化，另外功能编辑那里，各个容器的布局比例还不太合理  √
     * 12、数据编辑那里要把模块编辑一堆边框给去掉，改成分割线
     * 13、改一下数据库以及方法里面用下划线不规范的名字
     * 14、代码生成那个为了测试稿的接口后面要删除，数据编辑那里只保留检查接口，保存接口不要了
     * 15、添加标签管理功能，以及功能的代码可以选择标签  √
     * 16、代码编辑的新建不知道为什么不能添加文件了  √
     * 17、设置代码生成路径那个功能改成右击菜单  √
     * 18、功能选项里面加上一个，仅自动生成一次且不能删除 另一个改成仅自动生成一次  √
     * 19、代码生成那里，代码面板的生成路径要处理null的情况  √
     * 20、生成代码的变量组件以及方法组件那里做更改，如果有标签内容，只筛选出标签内容一样的变量，如果没有标签内容，只筛选数据类型一样的变量
     * 21、代码编辑的模块编辑那里，初始化、模块控制、设置、方法、方法添加 都要处理，能添加当前模块需要使用的模块的选项  √
     * 22、代码编辑的模块编辑那里，有时候添加模块了，模块编辑的菜单不刷新，看看怎么处理，比如添加以后延时一下再刷新，或者加个刷新按钮
     * 23、后面重新设计框架，比如把共用的都扔到common模块，为以后开发SDK包的需求搭好基础
     * 24、代码编辑的属性框那里，属性改成多选，不选就是空，有 自动生成一次、只生成一次、仅自动生成一次且不能删除、无需写入引入文件  √
     * 25、代码编辑的设置分类输入框，要做长点，其他类似的也是  √
     * 26、代码编辑的自定义变量的三个输入框要长点，其他类似的也是  √
     * 27、初始化的检查要改，如果模块控制还有模块设置没写内容，可以不写初始化，另外初始化可以一条代码都不写  √
     * 28、代码编辑那里，设置的功能，要把第几个分类这些参数也传过去，不然的话，检查的时候不好说明，其他也类似
     * 29、数据编辑和代码生成退出要给提示  √
     * 30、代码生成的方法添加组件，收起有问题，有时点击收起或者直接删除某些功能，没有把当前面板及时变化  √
     * 32、重新测试代码生成的更改代码部分、另外数据源命名功能出现异常
     * 33、代码生成的模块选择窗口有个逻辑问题，假如事先选了某个模块A，后面又选其他模块B，这个模块需要该模块A，结果现在可以取消选择A  √
     * 34、看看生成代码设置文件夹图标的地方能不能改，有时候没法设置图标  √（待测）
     * 图片浏览器加个透明输入框，字体紫色，改成轮播图，并且图片组件加字段  √（待测）
     * 更改图标的命令该成延时执行  √
     * 查一下数据生成的还原操作，是不是只要没写引入和初始化的都会自动生成一条，应该说，没有编辑过的模块才这样，编辑过的该怎样就怎样  √
     * 模块选择那里，看看是不是有没有对没有编辑过的模块做不能选择的处理，比如选择某个模块，看看它是不是没有编辑过的，它需要使用的模块是不是有没有编辑过的，有的话，不给选  √
     * 数据编辑那里，显示某个模块的内容好像没有显示 相关模块设置 和 模块使用设置  √
     * 方法的功能容器看看能不能再加一个属性，只能在模板格式中添加一次
     * 那个不明原因要处理，当前编辑某个没编辑过的模块后，保存编辑其他模块，回头再编辑那个模块居然说没编辑过
     * 代码生成那里，改成检测模板有没有方法标签，没有的话就设置当前面板为空，并且切换模板以及添加方法组件、等操作，都要判断当前面板是否为空，切换到控制面板的方法面板时都要判断是不是能切换过去  √
     * 删除代码标签的时候，要查查有没有哪个代码用它的，有的话不给删，并且给出提示，删除后要记得保存有用过这个代码标签的地方，不然会出错  √
     * 现在选某个已编辑的模块，模块代码文件没法加载已经添加的组件  √
     * 有个逻辑漏洞，代码生成那里，模块选择没有考虑到，选中的模块所需要的模块没有设置允许使用该使用范围，以及可能存在没编辑过，的情况  √
     * 变量和方法再加范围，无、需要使用的模块、本模块和需要使用的模块
     * 代码生成部分，模块设置和初始化要检测，手动的给提示，自动的记录到日志
     * 代码生成那里，主函数设置和其他设置没有插入标签
     * 看看新建生成代码那里，1点击确定是怎么选择必须使用该模块的
     */

    /**
     * 7491fc7065774a7e97310e0c221bedf8、c58810e5f0f544da8a82ec5a7c461d52、927f143ea54740edbfa52e8b8abe2a0f、
     * 53a6eeb1a46d4ac78625dd7b59d7c881、cff6ab02f0ff429aa543530b5d9ca48f、dc000beed53f491884a1309cfb7bbc04、
     * a5ed1f2cea78490781960cf6779e2795、e26d0ddc39464e0bbf67cceef58b3290、22c34cc13fad4231a9df71c6dd2ffe80、
     * f80188314523482b8e81aea3f9a8f0d4、ebf222e7635f4754a6bed45885ad405e、17cc0ac28396455bae61f714c2ca74d7、
     * 39cfe21e8fd24be4bf5a69d0b707d4f3、
     * 时间、数字、OV5640摄像头、照相机（OV5640摄像头）、TFT显示屏（基本）、
     * SD卡、外部FLASH、内存管理、文件系统（FATFS）、SPI1、DCMI、
     * SD卡（FATFS文件系统）、外部flash（FATFS文件系统）、
     *
     * 拿到排序后[{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"e26d0ddc39464e0bbf67cceef58b3290","moduleName":"内存管理","needModuleParam":"[]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"dc000beed53f491884a1309cfb7bbc04","moduleName":"SD卡","needModuleParam":"[]","noUseModuleParam":"[]","note":"SD卡基本配置","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"22c34cc13fad4231a9df71c6dd2ffe80","moduleName":"文件系统（FATFS）","needModuleParam":"[\"e26d0ddc39464e0bbf67cceef58b3290\"]","noUseModuleParam":"[]","note":"该模块需搭配 “W25Q128（外部FLASH）”共同使用","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"17cc0ac28396455bae61f714c2ca74d7","moduleName":"SD卡（FATFS文件系统）","needModuleParam":"[\"dc000beed53f491884a1309cfb7bbc04\",\"22c34cc13fad4231a9df71c6dd2ffe80\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"f80188314523482b8e81aea3f9a8f0d4","moduleName":"SPI1","needModuleParam":"[]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"a5ed1f2cea78490781960cf6779e2795","moduleName":"外部FLASH","needModuleParam":"[\"7491fc7065774a7e97310e0c221bedf8\",\"f80188314523482b8e81aea3f9a8f0d4\"]","noUseModuleParam":"[]","note":"使用芯片W25Q128","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"39cfe21e8fd24be4bf5a69d0b707d4f3","moduleName":"外部flash（FATFS文件系统）","needModuleParam":"[\"a5ed1f2cea78490781960cf6779e2795\",\"22c34cc13fad4231a9df71c6dd2ffe80\",\"f80188314523482b8e81aea3f9a8f0d4\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"显示屏","enabledState":1,"indexParam":1,"moduleId":"cff6ab02f0ff429aa543530b5d9ca48f","moduleName":"TFT显示屏（基本）","needModuleParam":"[\"7491fc7065774a7e97310e0c221bedf8\",\"c58810e5f0f544da8a82ec5a7c461d52\",\"e26d0ddc39464e0bbf67cceef58b3290\",\"22c34cc13fad4231a9df71c6dd2ffe80\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非用户选择模块","enabledState":1,"indexParam":1,"moduleId":"ebf222e7635f4754a6bed45885ad405e","moduleName":"DCMI","needModuleParam":"[\"cff6ab02f0ff429aa543530b5d9ca48f\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非模块","enabledState":1,"indexParam":1,"moduleId":"7491fc7065774a7e97310e0c221bedf8","moduleName":"时间","needModuleParam":"[]","noUseModuleParam":"[]","note":"","useSettingParam":"[]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"摄像头","enabledState":1,"indexParam":1,"moduleId":"927f143ea54740edbfa52e8b8abe2a0f","moduleName":"OV5640摄像头","needModuleParam":"[\"7491fc7065774a7e97310e0c221bedf8\",\"ebf222e7635f4754a6bed45885ad405e\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[\"1\"]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"摄像头","enabledState":1,"indexParam":1,"moduleId":"53a6eeb1a46d4ac78625dd7b59d7c881","moduleName":"照相机（OV5640摄像头）","needModuleParam":"[\"927f143ea54740edbfa52e8b8abe2a0f\",\"22c34cc13fad4231a9df71c6dd2ffe80\",\"ebf222e7635f4754a6bed45885ad405e\",\"17cc0ac28396455bae61f714c2ca74d7\",\"39cfe21e8fd24be4bf5a69d0b707d4f3\"]","noUseModuleParam":"[]","note":"","useSettingParam":"[]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"},{"className":"非模块","enabledState":1,"indexParam":1,"moduleId":"c58810e5f0f544da8a82ec5a7c461d52","moduleName":"数字","needModuleParam":"[]","noUseModuleParam":"[]","note":"","useSettingParam":"[]","usingRangeParam":"[{\"showName\":\"必填模板\",\"type\":1,\"serial\":0}]"}]
     */

    //https://blog.csdn.net/salerzhang/article/details/50147069?utm_medium=distribute.pc_relevant_download.none-task-blog-2~default~searchFromBaidu~default-5.nonecase&depth_1-utm_source=distribute.pc_relevant_download.none-task-blog-2~default~searchFromBaidu~default-5.nonecas
//    public MainApplication() {
//        // TODO Auto-generated constructor stub
//        SwingUtilities.invokeLater(new Runnable() {
//
//            @Override
//            public void run() {
//                TheUI.flatLookAndFeel();
//                try {
//                    SysFileStructure.copyCoolFormatDll();
//                    new FuncitonSelectFrame();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

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

