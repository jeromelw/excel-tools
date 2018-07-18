package io.ishuk.excel.tools.entity;

import io.ishuk.excel.tools.internal.restrain.WorkbookCreate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public enum ExcelType implements WorkbookCreate {

    XLS {
        @Override
        public Workbook workbook(int mayRowCount) {
            return new HSSFWorkbook();
        }
    },

    XLSX {
        @Override
        public Workbook workbook(int mayRowCount) {
            Workbook workbook = new XSSFWorkbook();
            if (mayRowCount > 3000) {
                workbook = new SXSSFWorkbook((XSSFWorkbook) workbook,100);
            }
            return workbook;
        }
    }
}
