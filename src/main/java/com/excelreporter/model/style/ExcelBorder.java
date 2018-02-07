package com.excelreporter.model.style;

public class ExcelBorder {
    private short color;
    private short width;

    public ExcelBorder() {}

    public ExcelBorder(final short width) {
        this.width = width;
    }

    public ExcelBorder(final short color, final short width) {
        this.color = color;
        this.width = width;
    }

    public short getColor() {
        return color;
    }

    public void setColor(final short color) {
        this.color = color;
    }

    public short getWidth() {
        return width;
    }

    public void setWidth(final short width) {
        this.width = width;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExcelBorder that = (ExcelBorder) o;

        if (color != that.color) {
            return false;
        }
        return width == that.width;
    }

    @Override
    public int hashCode() {
        int result = (int) color;
        result = 31 * result + (int) width;
        return result;
    }
}
