package com.excelreporter.model.style;

public class ColumnStyle extends ExcelStyle {
    private int index;
    private int width;

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
