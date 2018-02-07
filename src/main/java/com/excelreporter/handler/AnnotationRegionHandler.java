package com.excelreporter.handler;

import com.excelreporter.annotation.Region;
import com.excelreporter.model.ExcelRegion;

import java.util.ArrayList;
import java.util.List;

public class AnnotationRegionHandler implements RegionHandler {
    private final Region region;

    public AnnotationRegionHandler(final Region region) {
        this.region = region;
    }

    private void addRegion(final int startIndex, final int totalRows, final List<ExcelRegion> excelRegions) {
        final int endIndex = startIndex + (region.endRow() - region.startRow());
        final ExcelRegion excelRegion =
            new ExcelRegion(startIndex - 1, endIndex - 1, region.startColumn() - 1, region.endColumn() - 1);
        excelRegions.add(excelRegion);
        if(region.loop()) {
            final int nextEndIndex = endIndex + 1 + (region.endRow() - region.startRow());
            if(nextEndIndex <= totalRows) {
                addRegion(endIndex + 1, totalRows, excelRegions);
            }
        }
    }

    @Override
    public List<ExcelRegion> getRegions(final int totalRows) {
        final List<ExcelRegion> excelRegions = new ArrayList<>();
        addRegion(region.startRow(), totalRows, excelRegions);

        return excelRegions;
    }
}
