package io.ishuk.excel.tools.entity;

import java.util.function.Function;

public class ExcelWriteHeader {

    private String name;

    private Function convert;

    private ExcelWriteHeader(String name, Function convert){
        this.name = name;
        this.convert = convert;
    }

    public static ExcelWriteHeader create(String name){
        return new ExcelWriteHeader(name,Function.identity());
    }

    public static ExcelWriteHeader create(String name, Function convert){
        return new ExcelWriteHeader(name, convert);
    }

    public String getName() {
        return name;
    }

    public Function getConvert() {
        return convert;
    }
}
