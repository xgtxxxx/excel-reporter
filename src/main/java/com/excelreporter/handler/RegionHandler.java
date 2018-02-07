package com.excelreporter.handler;

import com.excelreporter.model.ExcelRegion;

import java.util.List;

public interface RegionHandler {
    List<ExcelRegion> getRegions(int totalRows);
}
