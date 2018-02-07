package com.excelreporter.style;

import com.excelreporter.model.style.ColumnStyle;
import com.excelreporter.model.style.RowStyle;
import com.excelreporter.model.style.ExcelStyle;

import java.util.List;

public interface Template {

    List<ColumnStyle> getDefaultColumnStyle();

    RowStyle getRowStyle(int rowIndex, short bgcolor);

    ExcelStyle getCellStyle(int rowIndex, int columnIndex, final Object cellValue, Style powerStyle);

    ExcelStyle getTitleStyle(int rowIndex, int columnIndex, final Object cellValue, Style powerStyle);
}
