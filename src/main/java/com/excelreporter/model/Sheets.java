package com.excelreporter.model;

import com.excelreporter.style.Template;

import java.util.ArrayList;
import java.util.List;

public class Sheets {
    private final List<SheetPair> sheetPairs = new ArrayList<>();

    public List<SheetPair> getSheets() {
        return sheetPairs;
    }

    public static Sheets from(final List<List<?>> sheetModels, final Template template) {
        final Sheets sheets = new Sheets();
        for(final List<?> models: sheetModels) {
            sheets.sheetPairs.add(new SheetPair(models, template));
        }

        return sheets;
    }

    public static Sheets from(final List<SheetPair> sheetPairs) {
        final Sheets sheets = new Sheets();
        sheets.sheetPairs.addAll(sheetPairs);

        return sheets;
    }
}
