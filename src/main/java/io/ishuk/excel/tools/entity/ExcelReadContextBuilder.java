package io.ishuk.excel.tools.entity;

import io.ishuk.excel.tools.internal.util.Assert;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;
import java.util.function.BiConsumer;

public class ExcelReadContextBuilder<T> {

    private ExcelReadContext<T> context;

    public ExcelReadContextBuilder(ExcelReadContext<T> context){
        this.context = context;
    }

    public ExcelReadContextBuilder<T> clazz(Class<T> clazz){
        context.setClazz(clazz);
        if(null == this.context.getHeaders()){
            this.context.setHeaders(clazz);
        }
        return this;
    }

    public ExcelReadContextBuilder<T> sheetIndex(int sheetIndex){
        this.context.setSheetIndex(sheetIndex);
        return this;
    }

    public ExcelReadContextBuilder<T> headerStart(int headerStart){
        this.context.setHeaderStart(headerStart);
        return this;
    }

    public ExcelReadContextBuilder<T> readSheetHook(BiConsumer<Sheet, ExcelReadContext> readSheetHook){
        this.context.setReadSheetHook(readSheetHook);
        return this;
    }

    public ExcelReadContextBuilder<T> headers(Map<String, ExcelReadHeader> headers){
        this.context.setHeaders(headers);
        return this;
    }

    public ExcelReadContext<T> build(){
        beforeBuild();
        return this.context;
    }

    private void beforeBuild() {
        Assert.notNull(this.context.getClazz(),"hasn't set clazz");
    }
}
