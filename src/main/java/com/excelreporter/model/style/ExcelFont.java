package com.excelreporter.model.style;

public class ExcelFont {
    private String fontName;
    private short fontHeight;
    private boolean italic;
    private boolean strikeout;
    private short color;
    private short typeOffset;
    private byte underline;
    private int charSet;
    private boolean bold;

    public boolean isBold() {
        return bold;
    }

    public void setBold(final boolean bold) {
        this.bold = bold;
    }

    public int getCharSet() {
        return charSet;
    }

    public void setCharSet(final int charSet) {
        this.charSet = charSet;
    }

    public short getColor() {
        return color;
    }

    public void setColor(final short color) {
        this.color = color;
    }

    public short getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(final short fontHeight) {
        this.fontHeight = fontHeight;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(final String fontName) {
        this.fontName = fontName;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(final boolean italic) {
        this.italic = italic;
    }

    public boolean isStrikeout() {
        return strikeout;
    }

    public void setStrikeout(final boolean strikeout) {
        this.strikeout = strikeout;
    }

    public short getTypeOffset() {
        return typeOffset;
    }

    public void setTypeOffset(final short typeOffset) {
        this.typeOffset = typeOffset;
    }

    public byte getUnderline() {
        return underline;
    }

    public void setUnderline(final byte underline) {
        this.underline = underline;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ExcelFont excelFont = (ExcelFont) o;

        if (fontHeight != excelFont.fontHeight) {
            return false;
        }
        if (italic != excelFont.italic) {
            return false;
        }
        if (strikeout != excelFont.strikeout) {
            return false;
        }
        if (color != excelFont.color) {
            return false;
        }
        if (typeOffset != excelFont.typeOffset) {
            return false;
        }
        if (underline != excelFont.underline) {
            return false;
        }
        if (charSet != excelFont.charSet) {
            return false;
        }
        if (bold != excelFont.bold) {
            return false;
        }
        return fontName != null ? fontName.equals(excelFont.fontName) : excelFont.fontName == null;

    }

    @Override
    public int hashCode() {
        int result = fontName != null ? fontName.hashCode() : 0;
        result = 31 * result + (int) fontHeight;
        result = 31 * result + (italic ? 1 : 0);
        result = 31 * result + (strikeout ? 1 : 0);
        result = 31 * result + (int) color;
        result = 31 * result + (int) typeOffset;
        result = 31 * result + (int) underline;
        result = 31 * result + charSet;
        result = 31 * result + (bold ? 1 : 0);
        return result;
    }
}
