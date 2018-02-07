package com.excelreporter.model.style;

public class ExcelStyle {
    private ExcelFont font;
    private short alignment;
    private boolean wrapText;
    private short verticalAlignment;
    private ExcelBorders border;
    private short fillPattern;
    private short bgColor;

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(final short alignment) {
        this.alignment = alignment;
    }

    public short getBgColor() {
        return bgColor;
    }

    public void setBgColor(final short bgColor) {
        this.bgColor = bgColor;
    }

    public ExcelBorders getBorder() {
        return border;
    }

    public void setBorder(final ExcelBorders border) {
        this.border = border;
    }

    public short getFillPattern() {
        return fillPattern;
    }

    public void setFillPattern(final short fillPattern) {
        this.fillPattern = fillPattern;
    }

    public ExcelFont getFont() {
        return font;
    }

    public void setFont(final ExcelFont font) {
        this.font = font;
    }

    public short getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(final short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public boolean isWrapText() {
        return wrapText;
    }

    public void setWrapText(final boolean wrapText) {
        this.wrapText = wrapText;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExcelStyle that = (ExcelStyle) o;

        if (alignment != that.alignment) {
            return false;
        }
        if (wrapText != that.wrapText) {
            return false;
        }
        if (verticalAlignment != that.verticalAlignment) {
            return false;
        }
        if (fillPattern != that.fillPattern) {
            return false;
        }
        if (bgColor != that.bgColor) {
            return false;
        }
        if (font != null ? !font.equals(that.font) : that.font != null) {
            return false;
        }
        return border != null ? border.equals(that.border) : that.border == null;

    }

    @Override
    public int hashCode() {
        int result = font != null ? font.hashCode() : 0;
        result = 31 * result + (int) alignment;
        result = 31 * result + (wrapText ? 1 : 0);
        result = 31 * result + (int) verticalAlignment;
        result = 31 * result + (border != null ? border.hashCode() : 0);
        result = 31 * result + (int) fillPattern;
        result = 31 * result + (int) bgColor;
        return result;
    }
}
