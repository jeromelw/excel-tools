package io.ishuk.excel.tools.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConvertHelper {

    private static final Map<String, Object> CONVERT_BEAN_MAP = new HashMap<>(10);

    public static <T extends Function> T getConvert(Class<T> clazz) {
        Object bean = CONVERT_BEAN_MAP.get(clazz.getName());
        if(null == bean) {
            synchronized (CONVERT_BEAN_MAP){
                bean = CONVERT_BEAN_MAP.get(clazz.getName());
                if(null == bean){
                    try {
                        bean = clazz.newInstance();
                    CONVERT_BEAN_MAP.put(clazz.getName(), bean);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return (T) bean;
    }
}
