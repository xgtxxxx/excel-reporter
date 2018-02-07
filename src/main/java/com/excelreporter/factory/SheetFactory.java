package com.excelreporter.factory;

import com.excelreporter.model.SheetWrapper;
import com.excelreporter.model.Sheets;
import com.excelreporter.style.Template;

import java.util.List;

public interface SheetFactory {
    SheetWrapper create(final List<?> models, final Template powerTemplate);
    List<SheetWrapper> create(final Sheets sheets);
}
