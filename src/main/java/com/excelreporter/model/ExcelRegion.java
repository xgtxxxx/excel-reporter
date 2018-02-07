package com.excelreporter.model;

public class ExcelRegion {
    private final int startRow;
    private final int endRow;
    private final int startColumn;
    private final int endColumn;

    public ExcelRegion(final int startRow, final int endRow, final int startColumn, final int endColumn) {
        this.endColumn = endColumn;
        this.endRow = endRow;
        this.startColumn = startColumn;
        this.startRow = startRow;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getStartRow() {
        return startRow;
    }
}
