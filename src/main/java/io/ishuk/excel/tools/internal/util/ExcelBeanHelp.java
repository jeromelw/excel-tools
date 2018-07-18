package io.ishuk.excel.tools.internal.util;

import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelBeanHelp {

    public static List<Map<String,Object>> beanToMap(List<?> bean){

        if(null == bean || bean.isEmpty()){
            return Collections.emptyList();
        }
        Object tempBean = bean.get(0);

        if(tempBean instanceof Map){
            return (List<Map<String, Object>>) bean;
        }
        return bean.stream().map(ExcelBeanHelp::toMap).collect(Collectors.toList());
    }

    private static <T> Map<String,Object> toMap(T bean) {
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();

        return Arrays.stream(fields).map(x ->{
            x.setAccessible(true);
            try {
                return new Pair<>(x.getName(),x.get(bean));
            } catch (IllegalAccessException e){

            }
            return null;
        })
                .filter(x -> x != null && !Objects.equals(x.getKey(), "this$0"))
                .collect(HashMap::new, (l, v) -> l.put(v.getKey(), v.getValue()), HashMap::putAll);
    }
}
