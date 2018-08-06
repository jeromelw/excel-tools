package io.ishuk.excel.tools.read;

import io.ishuk.excel.tools.entity.ExcelReadContext;

import java.util.List;

public interface ExcelReader {

    <T> List<T> resolve(ExcelReadContext<T> context);

    void close();
}
