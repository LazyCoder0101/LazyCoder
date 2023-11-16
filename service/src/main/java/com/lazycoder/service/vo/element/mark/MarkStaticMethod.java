package com.lazycoder.service.vo.element.mark;

import java.util.ArrayList;

public class MarkStaticMethod {

    /**
     * 获取所有匹配的承载标记的组件
     *
     * @param statementInformation
     * @param allMarkComponetList
     * @return
     */
    public static ArrayList<HarryingMark> getHarryingMarkWithCorrespondingQualifyingMarkList(
            BaseMarkElement statementInformation, ArrayList<HarryingMark> allMarkComponetList) {
        ArrayList<MarkThanObject> getMarkMatchList = getElementsThatMatchSuccessfully(statementInformation,
                allMarkComponetList);// 获取匹配成功的元素

        int maxNumOfMatchingFeatures = getMaxNumOfMatchingFeatures(getMarkMatchList);// 获取最大特征数量
        ArrayList<MarkThanObject> maxNumOfMatchingFeaturesList = getMatchListFor(maxNumOfMatchingFeatures,
                getMarkMatchList);// 获取所有为最大匹配数量的列表

        int maxMatchValue = getMaxMatchValue(maxNumOfMatchingFeaturesList);// 获取最大匹配值
        ArrayList<MarkThanObject> maxMatchValueList = getMatchValueListFor(maxMatchValue, maxNumOfMatchingFeaturesList);
        ArrayList<HarryingMark> harryingMarkWithCorrespondingQualifyingMarkList = getHarryingMarkList(
                maxMatchValueList);

        return harryingMarkWithCorrespondingQualifyingMarkList;
    }

    /**
     * 获取匹配成功的元素
     *
     * @param statementInformation 对应语句的信息
     * @param allMarkComponetList  所有的标记
     * @return
     */
    private static ArrayList<MarkThanObject> getElementsThatMatchSuccessfully(BaseMarkElement statementInformation,
                                                                              ArrayList<HarryingMark> allMarkComponetList) {
        BaseMarkElement temp;
        MarkThanObject markThanTemp;
        boolean flag = true;
        ArrayList<MarkThanObject> list = new ArrayList<>();
        for (HarryingMark harryingMarkTemp : allMarkComponetList) {
            temp = harryingMarkTemp.getMarkElement();

            if (statementInformation.getMarkType().equals(temp.getMarkType())) {// 如果类型一样
                if (statementInformation.isGeneral() == true) {
                    if (temp.isGeneral() == true) {// 如果是通用

                        markThanTemp = new MarkThanObject();
                        markThanTemp.setHarryingMark(harryingMarkTemp);// 记下这个标签
                        markThanTemp.setMatchValue(0);
                        markThanTemp.setNumOfMatchingFeatures(0);
                        list.add(markThanTemp);

                    } else {
                        flag = statementInformation.matchThan(temp);// 对两者进行比对
                        if (flag == true) {
                            markThanTemp = new MarkThanObject();
                            markThanTemp.setHarryingMark(harryingMarkTemp);// 记下这个标签
                            markThanTemp.setMatchValue(statementInformation.getMatchValue(temp));// 记下他的匹配值
                            markThanTemp.setNumOfMatchingFeatures(statementInformation.getNumOfMatchingFeatures(temp));// 记下他的特征数量
                            list.add(markThanTemp);

                        }
                    }
                } else {
                    flag = statementInformation.matchThan(temp);// 对两者进行比对
                    if (flag == true) {

                        markThanTemp = new MarkThanObject();
                        markThanTemp.setHarryingMark(harryingMarkTemp);// 记下这个标签
                        markThanTemp.setMatchValue(statementInformation.getMatchValue(temp));// 记下他的匹配值
                        markThanTemp.setNumOfMatchingFeatures(statementInformation.getNumOfMatchingFeatures(temp));// 记下他的特征数量
                        list.add(markThanTemp);

                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取最大特征数量
     *
     * @param list
     * @return
     */
    private static int getMaxNumOfMatchingFeatures(ArrayList<MarkThanObject> list) {
        int maxNumOfMatchingFeatures = 0;
        for (MarkThanObject temp : list) {
            if (temp.getNumOfMatchingFeatures() > maxNumOfMatchingFeatures) {
                maxNumOfMatchingFeatures = temp.getNumOfMatchingFeatures();
            }
        }
        return maxNumOfMatchingFeatures;
    }

    /**
     * 根据 list 来获取其 特征数量为 numOfMatchingFeatures 的所有参数
     *
     * @param numOfMatchingFeatures 特征数量
     * @param list                  参数
     * @return
     */
    private static ArrayList<MarkThanObject> getMatchListFor(int numOfMatchingFeatures,
                                                             ArrayList<MarkThanObject> list) {
        ArrayList<MarkThanObject> matchList = new ArrayList<>();
        for (MarkThanObject temp : list) {
            if (temp.getNumOfMatchingFeatures() == numOfMatchingFeatures) {
                matchList.add(temp);
            }
        }
        return matchList;
    }

    /**
     * 获取最大匹配值
     *
     * @param list
     * @return
     */
    private static int getMaxMatchValue(ArrayList<MarkThanObject> list) {
        int maxMatchValue = 0;
        for (MarkThanObject temp : list) {
            if (temp.getMatchValue() > maxMatchValue) {
                maxMatchValue = temp.getMatchValue();
            }
        }
        return maxMatchValue;
    }

    /**
     * 根据 list 来获取其 匹配值为 matchValue 的所有参数
     *
     * @param matchValue
     * @param list
     * @return
     */
    private static ArrayList<MarkThanObject> getMatchValueListFor(int matchValue, ArrayList<MarkThanObject> list) {
        ArrayList<MarkThanObject> matchList = new ArrayList<>();
        for (MarkThanObject temp : list) {
            if (temp.getMatchValue() == matchValue) {
                matchList.add(temp);
            }
        }
        return matchList;
    }

    /**
     * 根据list获取对应的承载标签的组件
     *
     * @param list
     * @return
     */
    private static ArrayList<HarryingMark> getHarryingMarkList(ArrayList<MarkThanObject> list) {
        ArrayList<HarryingMark> harryingMarkList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            harryingMarkList.add(list.get(i).getHarryingMark());
        }
        return harryingMarkList;
    }
}
