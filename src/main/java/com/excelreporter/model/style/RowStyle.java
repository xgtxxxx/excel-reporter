package com.excelreporter.model.style;

public class RowStyle extends ExcelStyle {
    private int index;
    private short height;

    public short getHeight() {
        return height;
    }

    public void setHeight(final short height) {
        this.height = height;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }
}
