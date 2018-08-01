package io.ishuk.excel.tools.entity;


import io.ishuk.excel.tools.internal.util.ExcelBeanHelp;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.function.BiConsumer;

public class ExcelReadContext<T> {

    private Class<T> clazz;

    private int sheetIndex = 0;

    private int headerStart = 0;

    private BiConsumer<Sheet, ExcelReadContext> readSheetHook = (w, v) -> {};

    private Map<String, ExcelReadHeader> headers;

    public static <T> ExcelReadContextBuilder<T> builder(){
        return new ExcelReadContextBuilder<>(new ExcelReadContext<>());
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public int getHeaderStart() {
        return headerStart;
    }

    public BiConsumer<Sheet, ExcelReadContext> getReadSheetHook() {
        return readSheetHook;
    }

    public Map<String, ExcelReadHeader> getHeaders() {
        return headers;
    }

    public ExcelReadContext<T> setClazz(Class<T> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ExcelReadContext<T> setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
        return this;
    }

    public ExcelReadContext<T> setHeaderStart(int headerStart) {
        this.headerStart = headerStart;
        return this;
    }

    public ExcelReadContext<T> setReadSheetHook(BiConsumer<Sheet, ExcelReadContext> readSheetHook) {
        this.readSheetHook = readSheetHook;
        return this;
    }

    public void setHeaders(Map<String, ExcelReadHeader> headers) {
        this.headers = headers;
    }

    public ExcelReadContext<T> setHeaders(Class<T> clazz) {
        this.headers = ExcelBeanHelp.beanToReaderHeaders(clazz);
        return this;
    }
}
