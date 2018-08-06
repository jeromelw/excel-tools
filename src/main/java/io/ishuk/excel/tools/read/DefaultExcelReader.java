package io.ishuk.excel.tools.read;

import io.ishuk.excel.tools.entity.ExcelReadContext;
import io.ishuk.excel.tools.entity.ExcelReadHeader;
import io.ishuk.excel.tools.exception.ExcelException;
import io.ishuk.excel.tools.internal.util.ExcelBeanHelp;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultExcelReader implements ExcelReader {

    private Workbook workbook;

    private InputStream inputStream;

    public DefaultExcelReader(InputStream inputStream){
        this.inputStream = inputStream;
        try  {
            this.workbook = WorkbookFactory.create(inputStream);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> resolve(ExcelReadContext<T> context) {
        Sheet sheet = this.workbook.getSheetAt(context.getSheetIndex());

        context.getReadSheetHook().accept(sheet,context);

        int startRow = context.getHeaderStart();
        Row header = sheet.getRow(startRow++);
        List<String> realHeaders = getHeaders(header);
        int lastRowNum = sheet.getLastRowNum();
        int totalCount = lastRowNum = header.getRowNum();
        ArrayList<T> resultContainer = new ArrayList<>(totalCount);
        Map<String, ExcelReadHeader> configHeaders = context.getHeaders();

        for(; startRow <= lastRowNum; startRow++){
            Row row = sheet.getRow(startRow);
            T instance = ExcelBeanHelp.newInstance(context.getClazz());
            row.cellIterator().forEachRemaining(x -> {
                String columName = realHeaders.get(x.getColumnIndex());
                ExcelReadHeader tempHeader = configHeaders.get(columName);
                if (null == tempHeader){
                    return;
                }
                Object value = tempHeader.getConvert().apply(ExcelBeanHelp.getColumnValue(x));
                ExcelBeanHelp.fieldSetValue(tempHeader.getField(), instance, value);
            });

            resultContainer.add(instance);
        }
        return resultContainer;
    }

    private List<String> getHeaders(Row header) {
        ArrayList<String> headers = new ArrayList<>();
        header.cellIterator().forEachRemaining(x -> headers.add(x.getStringCellValue()));
        return headers;
    }

    @Override
    public void close() {

        try{
            inputStream.close();
            workbook.close();
        } catch (IOException e){
            throw new ExcelException(e);
        }
    }
}
