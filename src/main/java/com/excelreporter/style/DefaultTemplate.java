package com.excelreporter.style;

import com.excelreporter.model.style.*;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultTemplate implements Template {
    @Override
    public List<ColumnStyle> getDefaultColumnStyle() {
        return Collections.emptyList();
    }

    @Override
    public RowStyle getRowStyle(final int rowIndex, final short bgcolor) {
        final RowStyle rowStyle = getDefault();
        if(bgcolor != 0) {
            rowStyle.setBgColor(bgcolor);
            rowStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        }

        return rowStyle;
    }

    @Override
    public ExcelStyle getCellStyle(final int rowIndex, final int columnIndex, final Object cellValue, final Style powerStyle) {
        final ExcelStyle cellStyle = getFrom(rowIndex, columnIndex, cellValue, powerStyle).orElse(null);

        return cellStyle == null ? getDefault() : cellStyle;
    }

    @Override
    public ExcelStyle getTitleStyle(final int rowIndex, final int columnIndex, final Object cellValue, final Style powerStyle) {
        final ExcelStyle cellStyle = getFrom(rowIndex, columnIndex, cellValue, powerStyle).orElse(getDefault());
        cellStyle.setFont(getDefaultTitleFont());

        return cellStyle;
    }

    private ExcelFont getDefaultTitleFont() {
        final ExcelFont font = new ExcelFont();
        font.setBold(true);

        return font;
    }

    private Optional<ExcelStyle> getFrom(final int rowIndex, final int columnIndex, final Object cellValue, final Style powerStyle) {
        ExcelStyle cellStyle = null;
        if (powerStyle != null) {
            cellStyle = powerStyle.getCellStyle(rowIndex, columnIndex, cellValue);
        }

        return Optional.ofNullable(cellStyle);
    }

    private RowStyle getDefault() {
        final RowStyle cellStyle = new RowStyle();
        cellStyle.setBorder(ExcelBorders.newDefaultInstance());

        return cellStyle;
    }
}
