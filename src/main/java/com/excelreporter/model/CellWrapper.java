package com.excelreporter.model;

import com.excelreporter.model.style.ExcelStyle;
import com.excelreporter.style.Style;
import com.excelreporter.style.Template;

import java.util.Optional;

public class CellWrapper {
    private boolean title;
    private Object value;
    private Style powerStyle;
    private int columnIndex;
    private int rowIndex;

    public Object getValue() {
        return value;
    }

    public Optional<ExcelStyle> getStyle(final Template template) {
        if (title) {
            return Optional.of(template.getTitleStyle(rowIndex, columnIndex, value, powerStyle));
        } else {
            return Optional.of(template.getCellStyle(rowIndex, columnIndex, value, powerStyle));
        }
    }

    public void setColumnIndex(final int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setStyle(final Style powerStyle) {
        this.powerStyle = powerStyle;
    }

    public void setRowIndex(final int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setTitle(final boolean title) {
        this.title = title;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
