package com.lazycoder.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class MapUtil {

	/**
	 * 获取最后一个元素
	 *
	 * @param map
	 * @return
	 */
	public static <K, V> Entry<K, V> getLastElement(LinkedHashMap<K, V> map) {
		Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
		Entry<K, V> tail = null;
		while (iterator.hasNext()) {
			tail = iterator.next();
		}
		return tail;
	}
}
