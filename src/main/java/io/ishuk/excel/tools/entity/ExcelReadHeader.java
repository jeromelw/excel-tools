package io.ishuk.excel.tools.entity;

import java.lang.reflect.Field;
import java.util.function.Function;

public class ExcelReadHeader {

    private Field field;

    private Function convert;

    private ExcelReadHeader(Field field, Function convert){
        this.field = field;
        this.convert = convert;
    }

    public static ExcelReadHeader create(Field field, Function convert){
        return new ExcelReadHeader(field, convert);
    }

    public static ExcelReadHeader create(Field field){
        return new ExcelReadHeader(field, Function.identity());
    }

    public Field getField() {
        return field;
    }

    public Function getConvert() {
        return convert;
    }
}
