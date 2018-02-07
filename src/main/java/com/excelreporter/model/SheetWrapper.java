package com.excelreporter.model;

import com.excelreporter.handler.RegionHandler;
import com.excelreporter.style.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SheetWrapper {
    private static final String DEFAULT_NAME_PREFIX = "sheet";
    private int index = 1;
    private String name;
    private Template template;
    private List<RowWrapper> rows = new ArrayList<>();
    private RegionHandler regionHandler;

    public List<RowWrapper> getRows() {
        return rows;
    }

    public void setRows(final List<RowWrapper> rows) {
        this.rows = rows;
    }

    public void addRow(final RowWrapper row) {
        rows.add(row);
    }

    public String getName() {
        return Optional
            .ofNullable(name)
            .orElse(DEFAULT_NAME_PREFIX + index);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public int nextRowIndex() {
        return rows.size();
    }

    public void setRegionHandler(final RegionHandler regionHandler) {
        this.regionHandler = regionHandler;
    }

    public List<ExcelRegion> getRegions(){
        if(regionHandler == null) {
            return Collections.emptyList();
        }
        final List<ExcelRegion> regions = regionHandler.getRegions(rows.size());

        return regions == null ? Collections.emptyList() : regions;
    }
}
