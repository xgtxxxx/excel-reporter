package com.excelreporter.model.style;

public class ExcelBorders {
    private static final short DEFAULT_BORDER_WIDTH = 1;
    private ExcelBorder top;
    private ExcelBorder right;
    private ExcelBorder bottom;
    private ExcelBorder left;

    public static ExcelBorders newDefaultInstance() {
        return newInstanceWith(DEFAULT_BORDER_WIDTH);
    }

    public static ExcelBorders newInstanceWith(final short width) {
        final ExcelBorders excelBorders = new ExcelBorders();
        excelBorders.setTop(new ExcelBorder(width));
        excelBorders.setRight(new ExcelBorder(width));
        excelBorders.setBottom(new ExcelBorder(width));
        excelBorders.setLeft(new ExcelBorder(width));

        return excelBorders;
    }

    public ExcelBorder getBottom() {
        return bottom == null ? new ExcelBorder() : bottom;
    }

    public void setBottom(final ExcelBorder bottom) {
        this.bottom = bottom;
    }

    public ExcelBorder getLeft() {
        return left == null ? new ExcelBorder() : left;
    }

    public void setLeft(final ExcelBorder left) {
        this.left = left;
    }

    public ExcelBorder getRight() {
        return right == null ? new ExcelBorder() : right;
    }

    public void setRight(final ExcelBorder right) {
        this.right = right;
    }

    public ExcelBorder getTop() {
        return top == null ? new ExcelBorder() : top;
    }

    public void setTop(final ExcelBorder top) {
        this.top = top;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExcelBorders that = (ExcelBorders) o;

        if (top != null ? !top.equals(that.top) : that.top != null) {
            return false;
        }
        if (right != null ? !right.equals(that.right) : that.right != null) {
            return false;
        }
        if (bottom != null ? !bottom.equals(that.bottom) : that.bottom != null) {
            return false;
        }
        return left != null ? left.equals(that.left) : that.left == null;

    }

    @Override
    public int hashCode() {
        int result = top != null ? top.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        result = 31 * result + (bottom != null ? bottom.hashCode() : 0);
        result = 31 * result + (left != null ? left.hashCode() : 0);
        return result;
    }
}
