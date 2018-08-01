package io.ishuk.excel.tools.entity;

import io.ishuk.excel.tools.internal.util.ExcelBeanHelp;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExcelWriteContext {

    private List<Map<String, Object>> datasource;

    private LinkedHashMap<String, ExcelWriteHeader> headers;

    private BiConsumer<Sheet, ExcelWriteContext> createSheetHook = (w, v) -> {};

    private int startRow = 0;

    private String sheetName;

    public static ExcelWriteContextBuilder builder(){
        return new ExcelWriteContextBuilder();
    }

    public <T> ExcelWriteContext setDatasource(List<T> datasource){
        if (null == datasource || datasource.isEmpty()){
            this.datasource = Collections.emptyList();
            return this;
        }
        this.datasource = ExcelBeanHelp.beanToMap(datasource);
        return this;
    }

    public <T> ExcelWriteContext setHeaders(T bean){
        if(bean instanceof Map){
            ((Map<String,?>) bean)
                    .keySet()
                    .stream()
                    .collect(LinkedHashMap::new,
                            (r,o) -> {
                                r.put(o,ExcelWriteHeader.create(o));
                            },
                            Map::putAll);
        }else {
            this.headers = ExcelBeanHelp.beadToWriterHeaders(bean);
        }
        return this;
    }

    public ExcelWriteContext setHeaders(LinkedHashMap<String, ExcelWriteHeader> headers) {
        this.headers = headers;
        return this;
    }

    public ExcelWriteContext setCreateSheetHook(BiConsumer<Sheet, ExcelWriteContext> createSheetHook) {
        this.createSheetHook = createSheetHook;
        return this;
    }

    public ExcelWriteContext setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public ExcelWriteContext setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public List<Map<String, Object>> getDatasource() {
        return datasource;
    }

    public LinkedHashMap<String, ExcelWriteHeader> getHeaders() {
        return headers;
    }

    public BiConsumer<Sheet, ExcelWriteContext> getCreateSheetHook() {
        return createSheetHook;
    }

    public int getStartRow() {
        return startRow;
    }

    public String getSheetName() {
        return sheetName;
    }
}
