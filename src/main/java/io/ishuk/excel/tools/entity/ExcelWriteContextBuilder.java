package io.ishuk.excel.tools.entity;

import io.ishuk.excel.tools.internal.util.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class ExcelWriteContextBuilder {

    private ExcelWriteContext context;

    public ExcelWriteContextBuilder(){
        this.context = new ExcelWriteContext();
    }

    public <T> ExcelWriteContextBuilder datasource(List<T> datasource){
        this.context.setDatasource(datasource);

        if(null == this.context.getHeaders()){
            if(CollectionUtils.isNotEmpty(datasource)){
                this.context.setHeaders(datasource.get(0));
            } else {
                this.context.setHeaders(Collections.emptyList());
            }
        }
        return this;
    }

    public ExcelWriteContextBuilder headers(LinkedHashMap<String, ExcelWriteHeader> headers) {
        this.context.setHeaders(headers);
        return this;
    }

    public ExcelWriteContextBuilder createSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
        this.context.setCreateSheetHook(createSheetHook);
        return this;
    }

    public ExcelWriteContextBuilder startRow(int startRow) {
        this.context.setStartRow(startRow);
        return this;
    }

    public ExcelWriteContextBuilder sheetName(String sheetName) {
        this.context.setSheetName(sheetName);
        return this;
    }

    public ExcelWriteContext build() {
        beforeBuildCheck();
        return context;
    }


    private void beforeBuildCheck() {
        Assert.notNull(context.getDatasource(), "datasource can't be null");
        Assert.notNull(context.getHeaders(), "headers can't be null");
    }

}
