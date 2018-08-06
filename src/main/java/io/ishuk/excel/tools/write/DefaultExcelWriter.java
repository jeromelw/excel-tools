package io.ishuk.excel.tools.write;

import io.ishuk.excel.tools.entity.ExcelType;
import io.ishuk.excel.tools.entity.ExcelWriteContext;
import io.ishuk.excel.tools.entity.ExcelWriteHeader;
import io.ishuk.excel.tools.exception.ExcelException;
import io.ishuk.excel.tools.internal.util.Assert;
import io.ishuk.excel.tools.internal.util.ExcelBeanHelp;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultExcelWriter implements ExcelWriter {

    private static Logger logger = LoggerFactory.getLogger(DefaultExcelWriter.class);

    private Workbook workbook;

    private OutputStream outputStream;

    private final ExcelType excelType;

    public DefaultExcelWriter(ExcelType excelType, OutputStream outputStream){
        Assert.notNull(excelType,"excelType can't be null");
        Assert.notNull(outputStream, "outputStream can't be null");
        this.excelType = excelType;
        this.outputStream = outputStream;
    }

    @Override
    public ExcelWriter export(ExcelWriteContext context) {

        createWorkbookIfNull(context);

        Sheet sheet = StringUtils.isEmpty(context.getSheetName())
                ? workbook.createSheet()
                : workbook.createSheet(context.getSheetName());
        context.getCreateSheetHook().accept(sheet,context);

        int startRow = context.getStartRow();
        Row headerRow = sheet.createRow(startRow++);
        int[] tempCol = {0};
        LinkedHashMap<String, ExcelWriteHeader> headers = context.getHeaders();
        headers.forEach((k, v) -> {
            Cell cell = headerRow.createCell(tempCol[0]++);
            cell.setCellValue(v.getName());
        });

        for (Map<String, Object> rowData : context.getDatasource()){
            Row row = sheet.createRow(startRow++);
            tempCol[0] = 0;
            headers.forEach((k, v) -> {
                Cell cell = row.createCell(tempCol[0]++);
                Object value = rowData.get(k);
                ExcelBeanHelp.autoFitCell(cell,v.getConvert().apply(value));
            });
        }
        return this;
    }


    private void createWorkbookIfNull(ExcelWriteContext context) {
        if(null == workbook){
            workbook = excelType.workbook(context.getDatasource().size());
        }
    }

    @Override
    public void write(){
        try{
           this.workbook.write(outputStream);
        } catch (IOException e){
            throw new ExcelException(e);
        } finally {
            try{
                this.workbook.close();
                outputStream.flush();
                outputStream.close();
            } catch (IOException e){
                logger.error("write fail.", e);
            }
        }
    }
}
