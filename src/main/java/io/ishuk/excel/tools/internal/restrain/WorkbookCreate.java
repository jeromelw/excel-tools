package io.ishuk.excel.tools.internal.restrain;

import org.apache.poi.ss.usermodel.Workbook;

public interface WorkbookCreate {

    Workbook workbook(int mayRowCount);
}
