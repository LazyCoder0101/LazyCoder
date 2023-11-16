package com.lazycoder.utils.compare;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 摘自： https://blog.csdn.net/sunnyzyq/article/details/124603360
 * 使用须知： <br>
 * （1）该工具类主要用于两个同类对象的属性值比较； <br>
 * （2）使用本工具类前，请将对应的类属性上打上 @Compare("xxx") 注解，其中xxx为字段的表达名称；<br>
 * （3）为了比较灵活，只有打了该注解才会进行比较，不打的字段则不会进行比较 <br>
 * （4）比较后，只会返回有变化的字段，无变化的字符则不返回 <br>
 *
 * @author zyqok
 * @since 2021/05/05
 */
public class CompareUtils<T> {

    private static final String COMMA = "，";

    /**
     * 属性比较
     *
     * @param source 源数据对象
     * @param target 目标数据对象
     * @return 对应属性值的比较变化
     */
    public String compare(T source, T target) {
        return compare(source, target, null);
    }


    /**
     * 属性比较
     *
     * @param source 源数据对象
     * @param target 目标数据对象
     * @param ignoreCompareFields 忽略比较的字段
     * @return 对应属性值的比较变化
     */
    public String compare(T source, T target, List<String> ignoreCompareFields) {
        if (Objects.isNull(source) && Objects.isNull(target)) {
            return "";
        }
        Map<String, CompareNode> sourceMap = this.getFiledValueMap(source);
        Map<String, CompareNode> targetMap = this.getFiledValueMap(target);
        if (sourceMap.isEmpty() && targetMap.isEmpty()) {
            return "";
        }
        // 如果源数据为空，则只显示目标数据，不显示属性变化情况
        if (sourceMap.isEmpty()) {
            return doEmpty(targetMap, ignoreCompareFields);
        }
        // 如果源数据为空，则显示属性变化情况
        String s = doCompare(sourceMap, targetMap, ignoreCompareFields);
        if (!s.endsWith(COMMA)) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    private String doEmpty(Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        StringBuilder sb = new StringBuilder();
        Collection<CompareNode> values = targetMap.values();
        int size = values.size();
        int current = 0;
        for (CompareNode node : values) {
            current++;
            Object o = Optional.ofNullable(node.getFieldValue()).orElse("");
            if (Objects.nonNull(ignoreCompareFields) && ignoreCompareFields.contains(node.getFieldKey())) {
                continue;
            }
            if (o.toString().length() > 0) {
                sb.append("[" + node.getFieldName() + "：" + o + "]");
                if (current < size) {
                    sb.append(COMMA);
                }
            }
        }
        return sb.toString();
    }

    private String doCompare(Map<String, CompareNode> sourceMap, Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        StringBuilder sb = new StringBuilder();
        Set<String> keys = sourceMap.keySet();
        int size = keys.size();
        int current = 0;
        for (String key : keys) {
            current++;
            CompareNode sn = sourceMap.get(key);
            CompareNode tn = targetMap.get(key);
            if (Objects.nonNull(ignoreCompareFields) && ignoreCompareFields.contains(sn.getFieldKey())) {
                continue;
            }
            String sv = Optional.ofNullable(sn.getFieldValue()).orElse("").toString();
            String tv = Optional.ofNullable(tn.getFieldValue()).orElse("").toString();
            // 只有两者属性值不一致时, 才显示变化情况
            if (!sv.equals(tv)) {
                sb.append(String.format("[%s：%s -> %s]", sn.getFieldName(), sv, tv));
                if (current < size) {
                    sb.append(COMMA);
                }
            }
        }
        return sb.toString();
    }

    private Map<String, CompareNode> getFiledValueMap(T t) {
        if (Objects.isNull(t)) {
            return Collections.emptyMap();
        }
        Field[] fields = t.getClass().getDeclaredFields();
        if (Objects.isNull(fields) || fields.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, CompareNode> map = new LinkedHashMap();
        for (Field field : fields) {
            Compare compareAnnotation = field.getAnnotation(Compare.class);
            if (Objects.isNull(compareAnnotation)) {
                continue;
            }
            field.setAccessible(true);
            try {
                String fieldKey = field.getName();
                CompareNode node = new CompareNode();
                node.setFieldKey(fieldKey);
                node.setFieldValue(field.get(t));
                node.setFieldName(compareAnnotation.value());
                map.put(field.getName(), node);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
