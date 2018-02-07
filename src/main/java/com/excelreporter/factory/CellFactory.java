package com.excelreporter.factory;

import com.excelreporter.model.CellWrapper;
import com.excelreporter.style.Style;

public interface CellFactory {
    CellWrapper create(final Object obj, final Style style, final int row, final int column, final boolean isTitle);
}
