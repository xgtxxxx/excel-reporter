package com.excelreporter.style;

import com.excelreporter.model.style.ExcelStyle;

public interface Style {
    ExcelStyle getCellStyle(final int rowIndex, final int columnIndex, final Object cellValue);
}
