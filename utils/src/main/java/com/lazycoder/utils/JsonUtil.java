package com.lazycoder.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 原文链接：https://blog.csdn.net/qq_28958027/article/details/100023302
 */
public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger("json工具类文件操作方法");

    private static final SerializeConfig CONFIG;
    private static final SerializerFeature[] FEATURES = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    static {
        CONFIG = new SerializeConfig();
        CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    /**
     * 把字符串转成 JSONObject
     *
     * @param str
     * @return
     */
    public static JSONObject strToJSONObject(String str) {
        return JSONObject.parseObject(str);
    }

    /**
     * 获取解析前后对应字符串都是固定的json字符串（）
     *
     * @param object
     * @return
     */
//	public static String getFixedSortingJsonStr(Object object) {
//		LinkedHashMap<String, Object> json = JSON.parseObject(
//				JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect), LinkedHashMap.class,
//				Feature.OrderedField);
//		JSONObject jsonObject = new JSONObject(true);
//		jsonObject.putAll(json);
//		return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
//	}

    /**
     * 获取对应的json字符串
     *
     * @param object
     * @return
     */
    public static String getJsonStr(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static void writeFile(File file, Object object) {
        try {
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);

            JSON.writeJSONString(writer, object, SerializerFeature.DisableCircularReferenceDetect);
            if(writer!=null) {
                writer.close();
            }
            if(write!=null) {
                write.close();
            }
        } catch (Exception e) {
            log.error("writeFile方法报错：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 根据jSONObject还原
     *
     * @param jSONObject
     * @param clazz
     * @return
     */
    public static <T> T restoreByJSONObject(JSONObject jSONObject, Class<T> clazz) {
        return JSON.toJavaObject(jSONObject, clazz);
    }

    /**
     * 根据字符串还原成某个类
     *
     * @param string
     * @param clazz
     * @return
     */
    public static <T> List<T> restoreArrayByStr(String string, Class<T> clazz) {
        return JSON.parseArray(string, clazz);
    }


    /////////////////
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, CONFIG, FEATURES);
    }

    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, CONFIG);
    }


    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T fileToBean(String path, Class<T> clazz) {
        try {
            InputStream is = new FileInputStream(path);
            return JSON.parseObject(is, clazz);//SerializerFeature
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将javabean转化为序列化的json字符串
     *
     * @param keyvalue
     * @return
     */
    public static Object beanToJson(KeyValue keyvalue) {
        String textJson = JSON.toJSONString(keyvalue);
        Object objectJson = JSON.parse(textJson);
        return objectJson;
    }

    /**
     * 将string转化为序列化的json字符串
     *
     * @param
     * @return
     */
    public static Object textToJson(String text) {
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    /**
     * json字符串转化为map
     *
     * @param s
     * @return
     */
    public static Map stringToCollect(String s) {
        Map m = JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将map转化为string
     *
     * @param m
     * @return
     */
    public static String collectToString(Map m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }


}

