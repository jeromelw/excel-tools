package io.ishuk.excel.tools.write;

import io.ishuk.excel.tools.entity.ExcelWriteContext;

public interface ExcelWriter {

    ExcelWriter export(ExcelWriteContext context);
    void write();
}
