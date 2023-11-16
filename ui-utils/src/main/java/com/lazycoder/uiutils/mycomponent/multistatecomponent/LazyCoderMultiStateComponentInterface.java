package com.lazycoder.uiutils.mycomponent.multistatecomponent;

import com.lazycoder.database.model.Module;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import java.util.ArrayList;
import java.util.List;

/**
 * 围绕着系统对应这种UI组件的共同继承接口
 */
public interface LazyCoderMultiStateComponentInterface {

    /**
     * 设置为当前编辑模块的寄生模块
     *
     * @param tipText 对应提示
     */
    public void setParasiticModule(String tipText);

    /**
     * 是否为当前编辑模块的寄生模块
     *
     * @return
     */
    public boolean isParasiticModule();

    /**
     * 设置为当前编辑模块的冲突模块
     *
     * @param tipText 对应提示
     */
    public void setConflictModule(String tipText);

    /**
     * 是否为当前编辑模块的冲突模块
     *
     * @return
     */
    public boolean isConflictModule();

    /**
     * 设置为事先使用的模块
     *
     * @param tipText 对应提示
     */
    public void setPreUseModule(String tipText);

    /**
     * 是否为事先使用的模块
     *
     * @return
     */
    public boolean isPreUseModule();

    /**
     * 设置为当前编辑模块不能使用的模块
     *
     * @param tipText 对应提示
     */
    public void setCannotUseModule(String tipText);

    /**
     * 是否为当前编辑模块不能使用的模块
     *
     * @return
     */
    public boolean isCannotUseModule();

    /**
     * 设置为当前编辑模块还要使用的模块
     *
     * @param tipText 对应提示
     */
    public void setAlsoUseModule(String tipText);

    /**
     * 是否为当前编辑模块还要使用的模块
     *
     * @return
     */
    public boolean isAlsoUseModule();

    /**
     * 设置为当前编辑模块的还要禁用的模块
     *
     * @param tipText 对应提示
     */
    public void setAlsoDisableModule(String tipText);

    /**
     * 是否为当前编辑模块还要禁用的模块
     *
     * @return
     */
    public boolean isAlsoDisableModule();

    /**
     * 设置为当前编辑模块事先且首先使用的模块（用户选了要用这个模块，而且用户选的其他模块也需要事先使用这个模块）
     *
     * @param tipText 对应提示
     */
    public void setPreFirUseModule(String tipText);

    /**
     * 是否为当前编辑模块事先且首先使用的模块（用户选了要用这个模块，而且用户选的其他模块也需要事先使用这个模块）
     *
     * @return
     */
    public boolean isPreFirUseModule();

    /**
     * 用户手动设置为当前编辑模块不能使用的模块，且其他需要的模块中不能使用该模块
     *
     * @param tipText 对应提示
     */
    public void setPreCannotUseModule(String tipText);

    /**
     * 是否为用户设置，当前编辑模块不能使用，且其他模块不能使用的模块
     *
     * @return
     */
    public boolean isPreCannotUseModule();

    /**
     * 设置模块不选
     *
     * @param tipText
     */
    public void setModuleSelectedNull(String tipText);

    /**
     * 查看模块是不是不选
     *
     * @return
     */
    public boolean isModuleSelectedNull();

    /**
     * 不能手动进行选择
     */
    public void setCannotChooseManual(String tipText);

    /**
     * 是不是不能收到进行选择
     */
    public boolean isCannotChooseManual();

    /**
     * 获取需要模块的对应参数
     *
     * @param moduleNeedList                   输入参数 使用某个模块A所需要的所有模块
     * @param selectedModuleList               输入参数 目前选中的其他模块所需要的模块
     * @param onlyANeedModuleList              输出参数 只是A这个模块所需要的模块
     * @param A_ModuleUseInCommonWithOtherList A这个模块和其他模块共用的模块
     */
    public static void setNeedModuleParams(List<Module> moduleNeedList, ArrayList<Module> selectedModuleList, ArrayList<Module> onlyANeedModuleList, ArrayList<Module> A_ModuleUseInCommonWithOtherList) {
//        Logger log = LoggerFactory.getLogger("getNeedModuleParams");

        onlyANeedModuleList.addAll(moduleNeedList);

        List<AssociatedModule> allPriUseAssociatedModuleList = SysService.MODULE_SERVICE.getAllNeedUsedModuleList(selectedModuleList);//获取当前选中的模块，对应所有需要使用的模块，这些模块目前对应在菜单应该都是【事先并优先使用】或者【还要使用】状态的
        List<Module> allPriUseModuleList = new ArrayList<>();
        for (AssociatedModule associatedModule : allPriUseAssociatedModuleList) {
            allPriUseModuleList.add(associatedModule.getModule());
        }
//        log.info("这些模块需要模块" + JsonUtil.getJsonStr(allPriUseModuleList));
        if (allPriUseModuleList.size() > 0) {
            Module temp;
            for (Module priUseTemp : allPriUseModuleList) {//搜索onlyANeedModuleList，看看里面有哪个模块是allPriUseModuleList也有的，把它去掉，那剩下的就只是module才需要使用的模块
                for (int i = 0; i < onlyANeedModuleList.size(); i++) {
                    temp = onlyANeedModuleList.get(i);
                    if (temp.getModuleId().equals(priUseTemp.getModuleId())) {
                        A_ModuleUseInCommonWithOtherList.add(temp);
                        onlyANeedModuleList.remove(i);
                        break;
                    }
                }
            }
        }
//        log.info("过滤以后，剩下的模块有" + JsonUtil.getJsonStr(onlyANeedModuleList));
    }

    /**
     * 获取不需要模块的对应参数
     * @param moduleNoUseList                   输入参数 使用某个模块A不能使用的所有模块
     * @param selectedModuleList                输入参数 使用目前选中的其他模块不能使用的模块
     * @param onlyANoNeedModuleList             输出参数 只是使用A这个模块不能使用的模块
     * @param A_ModuleNoUseInCommonWithOtherList    使用A这个模块和其他模块都不能使用的模块
     */
    public static void setNoNeedModuleParams(List<Module> moduleNoUseList, ArrayList<Module> selectedModuleList, ArrayList<Module> onlyANoNeedModuleList, ArrayList<Module> A_ModuleNoUseInCommonWithOtherList) {
        onlyANoNeedModuleList.addAll(moduleNoUseList);
//        Logger log = LoggerFactory.getLogger("getNeedModuleParams");

        List<AssociatedModule> allPriNoUseAssociatedModuleList = SysService.MODULE_SERVICE.getAllNoUsedModuleList(selectedModuleList);//获取当前选中的模块，对应所有需要使用的模块，这些模块目前对应在菜单应该都是【事先并优先使用】或者【还要使用】状态的
        List<Module> allPriNoUseModuleList = new ArrayList<>();
        for (AssociatedModule associatedModule : allPriNoUseAssociatedModuleList) {
            allPriNoUseModuleList.add(associatedModule.getModule());
        }
//        log.info("剩下的打勾的模块不需要什么模块" + JsonUtil.getJsonStr(allPriNoUseModuleList));

        if (allPriNoUseModuleList.size() > 0) {
            Module temp;
            for (Module priNoUseTemp : allPriNoUseModuleList) {//搜索onlyANeedModuleList，看看里面有哪个模块是allPriUseModuleList也有的，把它去掉，那剩下的就只是module才需要使用的模块
                for (int i = 0; i < onlyANoNeedModuleList.size(); i++) {
                    temp = onlyANoNeedModuleList.get(i);
                    if (temp.getModuleId().equals(priNoUseTemp.getModuleId())) {
                        A_ModuleNoUseInCommonWithOtherList.add(temp);
                        onlyANoNeedModuleList.remove(i);
                        break;
                    }
                }
            }
        }
//        log.info("只是模块" + module.getModuleName() + "才用到的模块有" + JsonUtil.getJsonStr(onlyANoNeedModuleList));
    }


}
