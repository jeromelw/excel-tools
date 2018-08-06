package io.ishuk.excel.tools;

import io.ishuk.excel.tools.entity.ExcelType;
import io.ishuk.excel.tools.exception.ExcelException;
import io.ishuk.excel.tools.read.DefaultExcelReader;
import io.ishuk.excel.tools.read.ExcelReader;
import io.ishuk.excel.tools.write.DefaultExcelWriter;
import io.ishuk.excel.tools.write.ExcelWriter;

import java.io.*;

public class ExcelTool {

    public static ExcelWriter export(ExcelType type, String fullFileName){
        try {
            return new DefaultExcelWriter(type,new FileOutputStream(fullFileName));
        } catch (FileNotFoundException e) {
            throw new ExcelException(e);
        }
    }

    public static ExcelWriter export(String fullFileName){
        try {
            return new DefaultExcelWriter(ExcelType.XLSX, new FileOutputStream(fullFileName));
        } catch (FileNotFoundException e) {
            throw new ExcelException(e);
        }
    }

    public static ExcelWriter export(ExcelType type, OutputStream outputStream){
        return new DefaultExcelWriter(type, outputStream);
    }

    public static ExcelWriter export(OutputStream outputStream){
        return new DefaultExcelWriter(ExcelType.XLSX, outputStream);
    }

    public static ExcelReader reader(String fullFilePath){
        try(FileInputStream fileInputStream = new FileInputStream(fullFilePath)){
            return new DefaultExcelReader(fileInputStream);
        } catch (IOException e) {
            throw new ExcelException(e);
        }
    }
}
