package com.excelreporter.service;

import com.excelreporter.model.SheetWrapper;

import java.io.OutputStream;
import java.util.List;

public interface ExcelBuilder {
    void build(SheetWrapper sheetWrapper, OutputStream os);
    void build(List<SheetWrapper> sheetWrappers, OutputStream os);
}
