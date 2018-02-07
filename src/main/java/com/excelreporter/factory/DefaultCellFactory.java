package com.excelreporter.factory;

import com.excelreporter.model.CellWrapper;
import com.excelreporter.style.Style;

public class DefaultCellFactory implements CellFactory {
    @Override
    public CellWrapper create(
        final Object obj, final Style style, final int row, final int column, final boolean isTitle) {

        final CellWrapper cell = new CellWrapper();
        cell.setTitle(isTitle);
        cell.setValue(obj);
        cell.setStyle(style);
        cell.setRowIndex(row);
        cell.setColumnIndex(column);

        return cell;
    }
}
