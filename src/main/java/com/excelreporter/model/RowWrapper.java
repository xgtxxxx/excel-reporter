package com.excelreporter.model;

import com.excelreporter.model.style.RowStyle;
import com.excelreporter.style.Template;

import java.util.List;
import java.util.Optional;

public class RowWrapper {
    private int index;
    private short backgroundColor;
    private List<CellWrapper> cells;

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public List<CellWrapper> getCells() {
        return cells;
    }

    public void setCells(final List<CellWrapper> cells) {
        this.cells = cells;
    }

    public short getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(final short backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Optional<RowStyle> getStyle(final Template template) {
        return Optional.ofNullable(template.getRowStyle(index, backgroundColor));
    }
}
