package com.excelreporter.exception;

public class PowerExcelException extends RuntimeException {
    private static final long serialVersionUID = 3356001110747986623L;

    public PowerExcelException() {
        super();
    }

    public PowerExcelException(final String message) {
        super(message);
    }

    public PowerExcelException(final String message, final Throwable e) {
        super(message, e);
    }
}
