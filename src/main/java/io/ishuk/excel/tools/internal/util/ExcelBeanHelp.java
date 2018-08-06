package io.ishuk.excel.tools.internal.util;

import io.ishuk.excel.tools.annotation.ExcelField;
import io.ishuk.excel.tools.annotation.ExcelIgore;
import io.ishuk.excel.tools.entity.ExcelReadHeader;
import io.ishuk.excel.tools.entity.ExcelWriteHeader;
import io.ishuk.excel.tools.exception.ExcelException;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Cell;

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

        return Arrays.
                stream(fields).
                map(x ->{
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

    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ExcelException(e);
        }
    }

    public static void fieldSetValue(Field field, Object target, Object value){
        field.setAccessible(true);
        try {
            field.set(target,value);
        } catch (IllegalAccessException e) {
            throw new ExcelException(e);
        }
    }

    public static void autoFitCell(Cell cell, Object value){
        if(value instanceof Date){
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }
    }

    public static <T> Map<String, ExcelReadHeader> beanToReaderHeaders(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays
                .stream(fields)
                .filter(x -> !Objects.equals(x.getName(), "this$0") && !Objects.equals(x.getName(),"serialVersionUID"))
                .filter(x -> Objects.isNull(x.getAnnotation(ExcelIgore.class)))
                .map(x -> {
                    x.setAccessible(Boolean.TRUE);
                    ExcelField annotation = x.getAnnotation(ExcelField.class);
                    if (null != annotation){
                        return new Pair<>(annotation.columnName(), ExcelReadHeader.create(
                                x,ConvertHelper.getConvert(annotation.readConvert())
                        ));
                    }
                    return new Pair<>(x.getName(), ExcelReadHeader.create(x));
                })
                .collect(HashMap::new,
                        (r,o) -> {
                            r.put(o.getKey(), o.getValue());
                        },
                        HashMap::putAll);

    }

    public static <T> LinkedHashMap<String, ExcelWriteHeader> beadToWriterHeaders(T bean) {
        if(bean instanceof LinkedHashMap){
            return ((Map<String,?>) bean)
                    .keySet()
                    .stream()
                    .collect(LinkedHashMap::new,
                            (r,o) -> {
                                r.put(o, ExcelWriteHeader.create(o));
                            },
                            Map::putAll);
        }
        final Field[] fields = bean.getClass().getDeclaredFields();

        return Arrays
                .stream(fields)
                .filter(x -> !Objects.equals(x.getName(),"this$0") && !Objects.equals(x.getName(),"serialVersionUID"))
                .filter(x -> Objects.isNull(x.getAnnotation(ExcelIgore.class)))
                .map(x -> {
                    x.setAccessible(Boolean.TRUE);
                    ExcelField annotation = x.getAnnotation(ExcelField.class);
                    if(null != annotation){
                        return new Pair<>(x.getName(), ExcelWriteHeader.create(annotation.columnName(),
                                ConvertHelper.getConvert(annotation.writeConvert())));
                    }
                    return new Pair<>(x.getName(), ExcelWriteHeader.create(x.getName()));
                }).collect(LinkedHashMap::new,
                        (r,o) -> {
                            r.put(o.getKey(),o.getValue());
                        },
                        HashMap::putAll);

    }

    public static Object getColumnValue(Cell cell) {
        switch (cell.getCellTypeEnum()){
            case STRING:
            case FORMULA:
            case BLANK:
                return cell.getStringCellValue();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue());
            case _NONE:
            case ERROR:
                default:
                    return null;
        }
    }
}
