package io.ishuk.excel.tools.exception;

public class ExcelException extends RuntimeException {

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
