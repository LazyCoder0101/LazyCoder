package com.lazycoder.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lazycoder.database.common.ModuleRelatedParam;
import com.lazycoder.database.dao.ModuleMapper;
import com.lazycoder.database.model.Module;
import com.lazycoder.database.model.ModuleInfo;
import com.lazycoder.database.model.formodule.UsingObject;
import com.lazycoder.service.ModuleUseSetting;
import com.lazycoder.service.service.SysService;
import com.lazycoder.service.vo.AssociatedModule;
import com.lazycoder.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "ModuleServiceImpl")
public class ModuleServiceImpl implements ModuleUseSetting {

    @Autowired
    private ModuleMapper dao;

    public void addModule(Module module) {
        // TODO Auto-generated method stub
        dao.addModule(module);
    }

    public List<Module> getAllModuleList() {
        // TODO Auto-generated method stub
        return dao.getAllModuleList();
    }

    public PageInfo<Module> getAllModuleList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Module> list = dao.getAllModuleList();
        PageInfo<Module> pageInfo = new PageInfo<Module>(list);
        return pageInfo;
    }

    public PageInfo<Module> getModuleList(String className, UsingObject usingObject, int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        PageHelper.startPage(pageNum, pageSize);
        String usingRangeParam = usingObject == null ? null : JsonUtil.getJsonStr(usingObject);
        List<Module> list = dao.getModuleList(className, usingRangeParam, -2);
        PageInfo<Module> pageInfo = new PageInfo<Module>(list);
        return pageInfo;
    }

    /**
     * 根据条件获取对应的模块
     *
     * @param className       模块的分类，不需要传null
     * @param usingObject     模块的使用范围
     * @param useSettingValue 是否一定需要使用 不需要传一个小于-1的值
     * @return
     */
    public List<Module> getModuleList(String className, UsingObject usingObject, int useSettingValue) {
        // TODO Auto-generated method stub
        String usingRangeParam = usingObject == null ? null : JsonUtil.getJsonStr(usingObject);
        return dao.getModuleList(className, usingRangeParam, useSettingValue);
    }

    /**
     * 获取分类className所有的模块
     *
     * @param className
     * @return
     */
    public List<Module> getModuleList(String className) {
        // TODO Auto-generated method stub
        return dao.getModuleList(className, null, -2);
    }

    /**
     * 更改模块
     */
    public void updateModule(Module module) {
        dao.updateModule(module);
    }

    /**
     * 设置模块信息
     */
    public void setModuleInfo(Module module) {
        dao.setModuleInfo(module);
    }

    /**
     * 获取所有非必选（设置了“生成程序都要使用该模块”）的模块列表
     */
    public List<Module> getModulesListThatAreNotRequired() {
        // TODO Auto-generated method stub
        return dao.getModulesListThatAreNotRequired(ModuleMapper.FALSE_);
    }

    /**
     * 获取模块
     *
     * @param className
     * @param moduleName
     * @return
     */
    public Module getModule(String className, String moduleName) {
        // TODO Auto-generated method stub
        return dao.getModule(className, moduleName);
    }

    /**
     * 根据 moduleId 获取模块
     *
     * @param moduleId
     * @return
     */
    public Module getModuleById(String moduleId) {
        return dao.getModuleById(moduleId);
    }

    /**
     * 查询除了输入的模块和非模块以外所有的模块
     */
    public List<Module> getModuleListExceptNonModuleAnd(String moduleId) {
        // TODO Auto-generated method stub
        if (moduleId != null && ("".equals(moduleId) == false)) {
            return dao.getModuleListExceptNonModuleAnd(moduleId);
        }
        return new ArrayList<>();
    }

    /**
     * 获取是某个格式使用的所有模块
     *
     * @param usingObject
     * @return
     */
    public List<Module> getAllModulesUsedby(UsingObject usingObject) {
        return dao.getAllModulesUsedby(JsonUtil.getJsonStr(usingObject));
    }

    /**
     * 查看该数据源能否使用（仅从模块之间有无冲突判断）
     */
    public boolean checkCanUseOrNot() {
        boolean flag = SysService.SYS_PARAM_SERVICE.getEnabledState();
//        List<Module> moduleList = getModuleList(null, UsingObject.MAIN_USING_OBJECT, ModuleUseSetting.MUST_USE);
//        if (moduleList != null && moduleList.size() > 0) {
//            ArrayList<Module> mList = new ArrayList<>();
//            mList.addAll(moduleList);
//            List<AssociatedModule> alist = getAllNeedUsedModuleList(mList);
//            for (AssociatedModule associatedModule : alist) {
//                if (associatedModule.getModule().getEnabledState() == Module.FALSE_) {
//                    flag = false;
//                    LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	这个数据源不能使用，因为需要用到的\"" +
//                            associatedModule.getModule().getModuleName() + "\"模块没有写任何内容");
//                    break;
//                }
//            }
//
//            if (flag == true) {
//                List<AdditionalFeatureSelection> selectionList = SysService.ADDITIONAL_FORMAT_FILE_SERVICE.getAdditionalFeatureSelectionList();
//                if (selectionList != null && selectionList.size() > 0) {
//                    UsingObject usingObject;
//                    for (AdditionalFeatureSelection additionalFeatureSelection : selectionList) {
//                        usingObject = new UsingObject(additionalFeatureSelection.getAdditionalSerialNumber());
//                        moduleList = getModuleList(null, usingObject, ModuleUseSetting.MUST_USE);
//                        if (moduleList != null && moduleList.size() > 0) {
//                            mList = new ArrayList<>();
//                            mList.addAll(moduleList);
//                            alist = getAllNeedUsedModuleList(mList);
//                            for (AssociatedModule associatedModule : alist) {
//                                if (associatedModule.getModule().getEnabledState() == Module.FALSE_) {
//                                    flag = false;
//                                    LazyCoderOptionPane.showMessageDialog(null, "∑(っ°Д°;)っ	这个数据源不能使用，因为需要用到的\"" +
//                                            associatedModule.getModule().getModuleName() + "\"模块没有写任何内容");
//                                    break;
//                                }
//                            }
//                            if (flag == false) {
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return flag;
    }

    /**
     * 查询有没有添加过该模块
     *
     * @param className
     * @param moduleName
     * @return
     */
    public boolean selectExist(String className, String moduleName) {
        boolean flag = true;
        Integer result = dao.selectExist(className, moduleName);
        if (result == null) {
            flag = false;
        } else if (result == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 重命名模块
     *
     * @param className
     * @param moduleName
     */
    public void delModule(String className, String moduleName) {
        // TODO Auto-generated method stub
        dao.delModule(className, moduleName);
    }

    /**
     * 从 moduleList 中获取这些模块的模块id
     *
     * @param moduleList
     * @return
     */
    public ArrayList<String> getModuleIdList(List<Module> moduleList) {
        ArrayList<String> list = new ArrayList<>();
        for (Module module : moduleList) {
            list.add(module.getModuleId());
        }
        return list;
    }

    /**
     * 获取模块 module 中 noUseModuleParam 里面对应的模块（不能使用的各个模块）
     *
     * @param module
     * @return
     */
    public ArrayList<Module> getNoUseModuleList(Module module) {
        ArrayList<Module> moduleList = new ArrayList<>();
        if (Module.NEW_STATE.equals(module.getNoUseModuleParam()) == false) {
            ArrayList<String> moduleIdList = JSON.parseObject(module.getNoUseModuleParam(), new TypeReference<ArrayList<String>>() {
            });
            Module moduleTemp;
            if (moduleIdList != null) {
                for (String moduleId : moduleIdList) {
                    if (moduleId != null && ("".equals(moduleId) == false)) {
                        moduleTemp = dao.getModuleById(moduleId);
                        if (moduleTemp != null) {
                            moduleList.add(moduleTemp);
                        }
                    }
                }
            }
        }
        return moduleList;
    }

    /**
     * 获取使用模块 module 中 needModuleParam 里面对应的模块（之前需要添加的各个模块）
     *
     * @param module
     * @return
     */
    public ArrayList<Module> getNeedModuleList(Module module) {
        ArrayList<Module> moduleList = new ArrayList<>();
        if (Module.NEW_STATE.equals(module.getNeedModuleParam()) == false) {
            ArrayList<String> moduleIdList = JSON.parseObject(module.getNeedModuleParam(), new TypeReference<ArrayList<String>>() {
            });
            Module moduleTemp;
            if (moduleIdList != null) {
                for (String moduleId : moduleIdList) {
                    if (moduleId != null && ("".equals(moduleId) == false)) {
                        moduleTemp = dao.getModuleById(moduleId);
                        if (moduleTemp != null) {
                            moduleList.add(moduleTemp);
                        }
                    }
                }
            }
        }
        return moduleList;
    }

    /**
     * 获取某个模块需要用到的模块，然后这个模块再需要用到的模块（不排序）
     *
     * @param module
     * @return
     */
    public List<AssociatedModule> getAllNeedUsedModuleList(Module module) {
        List<AssociatedModule> associatedModuleList = new ArrayList<>(), nextAssociatedModuleList;
        AssociatedModule associatedModuleTemp;
        /**
         * 获取这个
         */
        List<Module> needList = getNeedModuleList(module);//搜一下这个模块需要哪些模块
        if (needList != null) {
            for (Module needTemp : needList) {
                if (Module.NEW_STATE.equals(needTemp.getNeedModuleParam()) == false) {
                    nextAssociatedModuleList = getAllNeedUsedModuleList(needTemp);//再搜搜这个模块又需要哪些模块
                    for (AssociatedModule associatedModule : nextAssociatedModuleList) {
                        if (associatedModule.getModule().getModuleId().equals(module.getModuleId()) == false) {
                            if (haveTheModuleInList(associatedModule.getModule(), associatedModuleList) == false) {
                                associatedModuleList.add(associatedModule);
                            }
                        }
                    }
                }
                if (needTemp.getModuleId().equals(module.getModuleId()) == false) {
                    if (haveTheModuleInList(needTemp, associatedModuleList) == false) {//看看这个模块在在要返回的列表里面有没有，没有才处理
                        associatedModuleTemp = new AssociatedModule();
                        associatedModuleTemp.setModule(needTemp);
                        associatedModuleTemp.getCurrentAssociatedModuleList().add(module);
                        associatedModuleList.add(associatedModuleTemp);
                    }
                }
            }
        }
        return associatedModuleList;
    }

    /**
     * 获取某些模块需要用到的模块，然后这个模块再需要用到的模块（不排序）
     *
     * @param theList
     * @return
     */
    public List<AssociatedModule> getAllNeedUsedModuleList(ArrayList<Module> theList) {
        List<AssociatedModule> associatedModuleList = new ArrayList<>(), tempList;
        AssociatedModule associatedModule;
        for (Module sourceModuleTemp : theList) {
            tempList = getAllNeedUsedModuleList(sourceModuleTemp);//逐个看看这些模块需要到什么模块
            for (AssociatedModule associatedModuleTemp : tempList) {//拿到其中一个模块的所有需要的模块的关联模块
                if (haveTheModuleInList(theList, associatedModuleTemp.getModule()) == false) {
                    if (haveTheModuleInList(associatedModuleTemp.getModule(), associatedModuleList)) {//如果associatedModuleList已经有这个模块，在原来里面对应的模块加上关联模块
                        associatedModule = getAssociatedModuleInList(associatedModuleList, associatedModuleTemp.getModule().getModuleId());
                        if (associatedModule != null) {
                            associatedModule.addAssociatedModuleList(associatedModuleTemp);
                        }
                    } else {
                        associatedModuleList.add(associatedModuleTemp);
                    }
                }
            }
        }
        return associatedModuleList;
    }

    /**
     * 获取该模块（这个模块需要的模块，然后这些模块不能使用的模块）所有不能使用的模块
     *
     * @param module
     * @return
     */
    public ArrayList<AssociatedModule> getAllNoUsedModuleList(Module module) {
        ArrayList<AssociatedModule> associatedModuleList = new ArrayList<>();
        List<Module> nextNoUseList;
        AssociatedModule associatedModule;
        ArrayList<Module> thisNoList = getNoUseModuleList(module);
        for (Module temp : thisNoList) {
            associatedModule = new AssociatedModule();
            associatedModule.setModule(temp);
            associatedModule.getCurrentAssociatedModuleList().add(module);
            associatedModuleList.add(associatedModule);
        }

        List<AssociatedModule> needList = getAllNeedUsedModuleList(module);//搜一下这个模块需要哪些模块
        if (needList != null) {
            for (AssociatedModule associatedModuleTemp : needList) {
                if (Module.NEW_STATE.equals(associatedModuleTemp.getModule().getNoUseModuleParam()) == false) {
                    nextNoUseList = getNoUseModuleList(associatedModuleTemp.getModule());//再搜搜这个模块又需要哪些模块
                    for (Module nextNoUseTemp : nextNoUseList) {
                        if (nextNoUseTemp.getModuleId().equals(module.getModuleId()) == false) {
                            if (haveTheModuleInList(nextNoUseTemp, associatedModuleList) == true) {//看看要返回的list有没有这个模块，没有就添加到list
                                associatedModule = getAssociatedModuleInList(associatedModuleList, nextNoUseTemp.getModuleId());
                                if (associatedModule != null) {
                                    associatedModule.getCurrentAssociatedModuleList().add(associatedModuleTemp.getModule());
                                }
                            } else {
                                associatedModule = new AssociatedModule();
                                associatedModule.setModule(nextNoUseTemp);
                                associatedModule.getCurrentAssociatedModuleList().add(associatedModuleTemp.getModule());
                                associatedModuleList.add(associatedModule);
                            }
                        }
                    }
                }
            }
        }
        return associatedModuleList;
    }

    /**
     * 获取某些模块（这个模块需要的模块，然后这些模块不能使用的模块）所有不能使用的模块
     *
     * @param theList
     * @return
     */
    public List<AssociatedModule> getAllNoUsedModuleList(ArrayList<Module> theList) {
        List<AssociatedModule> associatedModuleList = new ArrayList<>(), tempList;
        AssociatedModule associatedModule;
        for (Module sourceModuleTemp : theList) {
            tempList = getAllNoUsedModuleList(sourceModuleTemp);//逐个看看这些模块需要到什么模块   G   H
            for (AssociatedModule associatedModuleTemp : tempList) {
                if (haveTheModuleInList(theList, associatedModuleTemp.getModule()) == false) {

                    if (haveTheModuleInList(associatedModuleTemp.getModule(), associatedModuleList) == true) {
                        associatedModule = getAssociatedModuleInList(associatedModuleList, associatedModuleTemp.getModule().getModuleId());
                        if (associatedModule != null) {
                            associatedModule.getCurrentAssociatedModuleList().add(associatedModuleTemp.getModule());
                        }
                    } else {
                        associatedModuleList.add(associatedModuleTemp);
                    }
                }
            }
        }
        return associatedModuleList;
    }

    /**
     * 看看 moduleList 里面有没有 module 这个模块
     *
     * @param moduleList
     * @param module
     * @return
     */
    public boolean haveTheModuleInList(List<Module> moduleList, Module module) {
        boolean flag = false;
        for (Module temp : moduleList) {
            if (module.getModuleId().equals(temp.getModuleId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 看看 moduleList 里面有没有 module 这个模块
     *
     * @param module
     * @param moduleList
     * @return
     */
    private boolean haveTheModuleInList(Module module, List<AssociatedModule> moduleList) {
        boolean flag = false;
        for (AssociatedModule temp : moduleList) {
            if (module.getModuleId().equals(temp.getModule().getModuleId())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取moduleList对应的关联模块
     *
     * @param moduleList 要添加的列表
     * @param moduleId   要添加到的模块的id
     */
    private AssociatedModule getAssociatedModuleInList(List<AssociatedModule> moduleList, String moduleId) {
        AssociatedModule associatedModule = null;
        for (AssociatedModule temp : moduleList) {
            if (temp.getModule().getModuleId().equals(moduleId)) {
                associatedModule = temp;
                break;
            }
        }
        return associatedModule;
    }

    /**
     * 把sourceList里面retrieveModuleId模块， 里面需要的模块再提前，并返回最终结果的模块
     * 获取最新的排序列表，以及这个列表接下来要检索的模块
     * 遍历这个列表，看看这个列表要检索的模块，后面的模块有哪些是它需要的，有的话，放在它前面
     */
    private static ArrayList<Module> sortByNeedModule(ArrayList<Module> sourceList, Module retrieveModule) {
        ArrayList<Module> list = new ArrayList<>(),
                retrieveInList = new ArrayList<>(),
                retrieveOutTempList = new ArrayList<>() ;
        retrieveInList.addAll(sourceList);
        List<String> needModuleTempList;
        Module needModuleTemp;
        if (retrieveModule != null) {
            if (Module.NEW_STATE.equals(retrieveModule.getNeedModuleParam()) == false) {
                needModuleTempList = JSON.parseObject(retrieveModule.getNeedModuleParam(), new TypeReference<List<String>>() {
                });
                if (needModuleTempList.size() == 0) {
                    retrieveOutTempList.addAll(sourceList);
                } else if (needModuleTempList.size() > 0) {
                    for (String needIdTemp : needModuleTempList) {
                        if (isItInFront(retrieveInList, retrieveModule.getModuleId(), needIdTemp)) {
                            retrieveOutTempList = retrieveInList;
                        } else {
                            needModuleTemp = SysService.MODULE_SERVICE.getModuleById(needIdTemp);
                            if (needModuleTemp != null) {
                                retrieveOutTempList = placeIdInFrontOfModule(retrieveInList, retrieveModule, needIdTemp);
                                retrieveOutTempList = sortByNeedModule(retrieveOutTempList, needModuleTemp);
                                retrieveInList = retrieveOutTempList;
                            }
                        }
                    }
                }
            }
        }
        list.addAll(retrieveOutTempList);
        return list;
    }

    /**
     * 查看needUseModuleId这个模块是不是在moduleId的前面
     *
     * @param sourceList
     * @param moduleId
     * @param needUseModuleId
     * @return
     */
    private static boolean isItInFront(ArrayList<Module> sourceList, String moduleId, String needUseModuleId) {
        boolean flag = false;
        for (Module temp : sourceList) {
            if (temp.getModuleId().equals(needUseModuleId)) {//在moduleId之前遍历到needUseModuleId，说明它就在moduleId前面
                flag = true;
                break;
            } else if (temp.getModuleId().equals(moduleId)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 生成代码之前要使用的函数（把现有的模块通通按照indexParam还有需要使用的模块进行排序）
     *
     * @param sourceList
     * @return
     */
    public ArrayList<Module> sortModuleListByNeedModuleParam(ArrayList<Module> sourceList) {
        ArrayList<Module> list = new ArrayList<>(), sortList = sort(sourceList), currentSortSourceTempList, currentSortOutTempList = new ArrayList<>();//先把模块按照index参数重新进行排序
        currentSortSourceTempList = sortList;
        for (Module temp : sourceList) {
            currentSortOutTempList = sortByNeedModule(currentSortSourceTempList, temp);
            currentSortSourceTempList = currentSortOutTempList;
        }
        list.addAll(currentSortOutTempList);
        return list;
    }


    /**
     * 把 sourceList 里面的 moduleId 模块放到 positionModule前面
     *
     * @param sourceList
     * @param positionModule
     * @param moduleId
     * @return
     */
    public static ArrayList<Module> placeIdInFrontOfModule(ArrayList<Module> sourceList, Module positionModule, String moduleId) {
        ArrayList<Module> list = new ArrayList<>();
        list.addAll(sourceList);

        Module updateModuleTemp = getModule(list, moduleId), temp;
        list.remove(updateModuleTemp);
        if (updateModuleTemp != null) {
            for (int i = 0; i < list.size(); i++) {
                temp = list.get(i);
                if (temp.getNeedModuleParam().contains(moduleId)) {//如果遍历到的这个模块，需要使用到这个模块moduleId
                    list.add(i, updateModuleTemp);//直接放到这个模块前面即可
                    break;
                } else if (temp.getModuleId().equals(positionModule.getModuleId())) {//如果遍历到的这个模块，就是positionModule
                    list.add(i, updateModuleTemp);//直接放到这个模块前面
                    break;
                } else {
                    if (temp.getModuleId().equals(moduleId)) {//如果这个模块现在就已经在这个模块的前面了，直接退出
                        break;
                    }
                }
            }
        }
        return list;
    }


    /**
     * 从 soureList 中获取模块 moduleId
     *
     * @param soureList
     * @param moduleId
     * @return
     */
    private static Module getModule(ArrayList<Module> soureList, String moduleId) {
        for (Module temp : soureList) {
            if (temp != null) {
                if (temp.getModuleId().equals(moduleId)) {
                    return temp;
                }
            }
        }
        return null;
    }

    /**
     * 对模块进行排序,并且，需要依赖
     * https://blog.csdn.net/zzzgd_666/article/details/87634775
     *
     * @param modules
     * @return
     */
    private static ArrayList<Module> sort(ArrayList<Module> modules) {
        ArrayList<Module> list = new ArrayList<>();
        list.addAll(modules);
        Module temp;
        if (list.size() > 1) {
            for (int a = 0; a < list.size() - 1; a++) {
                // 初始化一个布尔值
                boolean flag = true;
                for (int j = 0; j < list.size() - a - 1; j++) {
                    if (list.get(j).getIndexParam() > list.get(j + 1).getIndexParam()) {
                        // 调换
                        temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, temp);
                        // 改变flag
                        flag = false;
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return list;
    }

    /**
     * 获取使用了某某模块的所有模块
     *
     * @return
     */
    public List<Module> getModulesListThatUsedTheModule(String moduleId) {
        return dao.getModulesListThatUsedTheModule(moduleId);
    }

    /**
     * 获取已经设置不能使用某某模块的所有模块
     *
     * @param moduleId
     * @return
     */
    public List<Module> getModulesListThatCanNotUsedTheModule(String moduleId) {
        return dao.getModulesListThatCanNotUsedTheModule(moduleId);
    }

    /**
     * 根据模块列表把所有模块名转成字符串
     *
     * @param moduleList
     * @return
     */
    public String getModuleListStr(List<Module> moduleList) {
        ArrayList<String> list = new ArrayList<>();
        for (Module module : moduleList) {
            list.add(module.getModuleName());
        }
        return StringUtils.join(list, "、");
    }

    /**
     * 重命名分类名
     *
     * @param newClassName
     * @param oldClassName
     */
    public void renameClassName(String newClassName, String oldClassName) {
        dao.renameClassName(newClassName, oldClassName);
    }

    /**
     * 根据list获取对应的模块列表
     *
     * @param list
     */
    public ArrayList<ModuleRelatedParam> getCurrentModuleInfoListBy(ArrayList<Module> list) {
        ArrayList<ModuleRelatedParam> moduleRelatedParamList = new ArrayList<ModuleRelatedParam>();
        ModuleInfo moduleInfo;
        ModuleRelatedParam moduleRelatedParam;
        for (Module module : list) {
            moduleInfo = SysService.MODULE_INFO_SERVICE.getModuleInfo(module.getModuleId());
            if (moduleInfo != null) {
                moduleRelatedParam = new ModuleRelatedParam(module, moduleInfo);
                moduleRelatedParamList.add(moduleRelatedParam);
            }
        }
        return moduleRelatedParamList;
    }


}
