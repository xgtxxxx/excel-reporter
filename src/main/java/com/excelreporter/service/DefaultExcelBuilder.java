package com.excelreporter.service;

import com.excelreporter.model.ExcelRegion;
import com.excelreporter.model.style.ExcelBorders;
import com.excelreporter.style.DefaultTemplate;
import com.excelreporter.utils.Iterators;
import com.excelreporter.model.RowWrapper;
import com.excelreporter.model.SheetWrapper;
import com.excelreporter.model.style.ExcelBorder;
import com.excelreporter.model.style.ExcelFont;
import com.excelreporter.model.style.ExcelStyle;
import com.excelreporter.style.Template;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DefaultExcelBuilder extends AbstractExcelBuilder {
    @Override
    public Sheet createSheet(final SheetWrapper sheetWrapper, final Workbook workbook) {
        final Sheet sheet = workbook.createSheet(sheetWrapper.getName());
        fillSheet(sheet, sheetWrapper, workbook);
        mergeRegions(sheet, sheetWrapper.getRegions());
        return sheet;
    }

    @Override
    public Template getDefaultTemplateIfAbsent(final SheetWrapper sheetWrapper) {
        return Optional.ofNullable(sheetWrapper.getTemplate()).orElse(new DefaultTemplate());
    }

    private void mergeRegions(final Sheet sheet, final List<ExcelRegion> regions) {
        regions
            .stream()
            .map(region -> new CellRangeAddress(region.getStartRow(),region.getEndRow(),region.getStartColumn(),region.getEndColumn()))
            .forEach(cellRangeAddress -> sheet.addMergedRegion(cellRangeAddress));
    }

    private void fillSheet(final Sheet sheet, final SheetWrapper sheetWrapper, final Workbook workbook) {
        final Template template = getDefaultTemplateIfAbsent(sheetWrapper);
        setWidthToColumn(sheet, template);
        Iterators
            .forEachWithIndex(sheetWrapper.getRows(), (index, rowWrapper) -> {
                final Row row = sheet.createRow(index);
                fillRow(row, rowWrapper, template, workbook);
            });
    }

    private void setWidthToColumn(final Sheet sheet, final Template template) {
        Optional
            .ofNullable(template.getDefaultColumnStyle())
            .orElse(Collections.emptyList())
            .stream()
            .forEach(columnStyle -> sheet.setColumnWidth(columnStyle.getIndex(), columnStyle.getWidth()));
    }

    private void fillRow(final Row row, final RowWrapper rowWrapper, final Template template, final Workbook workbook) {
        setHeightToRow(row, rowWrapper, template);
        Iterators
            .forEachWithIndex(rowWrapper.getCells(), (index, cellWrapper) -> {
                final ExcelStyle cellStyle =
                    getFinalStyleFrom(
                        cellWrapper.getStyle(template).orElse(null),
                        rowWrapper.getStyle(template).orElse(null),
                        getColumnStyle(index).orElse(null));
                final Cell cell = row.createCell(index);
                fillCell(cell, cellWrapper.getValue(), cellStyle, workbook);
            });
    }

    private void setHeightToRow(final Row row, final RowWrapper rowWrapper, final Template template) {
        rowWrapper.getStyle(template).ifPresent(
            rowStyle -> row.setHeight(getDefaultIfAbsent(rowStyle.getHeight(), row.getHeight(), SHORT_ZERO)));
    }

    private ExcelStyle getFinalStyleFrom(
        final ExcelStyle cellStyle,
        final ExcelStyle rowStyle,
        final ExcelStyle columnStyle) {
        final ExcelStyle style = getFinalStyleFrom(cellStyle, columnStyle);

        return getFinalStyleFrom(style, rowStyle);
    }

    private ExcelStyle getFinalStyleFrom(final ExcelStyle mainStyle, final ExcelStyle defaultStyle) {
        if (mainStyle == null) {
            return defaultStyle;
        }

        if (defaultStyle != null) {
            mainStyle.setBgColor(getDefaultIfAbsent(mainStyle.getBgColor(), defaultStyle.getBgColor(), SHORT_ZERO));
            mainStyle.setBorder(mergeBorder(mainStyle.getBorder(), defaultStyle.getBorder()));
            mainStyle.setAlignment(getDefaultIfAbsent(
                mainStyle.getAlignment(),
                defaultStyle.getAlignment(),
                SHORT_ZERO));
            mainStyle.setFillPattern(getDefaultIfAbsent(
                mainStyle.getFillPattern(),
                defaultStyle.getFillPattern(),
                SHORT_ZERO));
            mainStyle.setFont(mergeFont(mainStyle.getFont(), defaultStyle.getFont()));
            mainStyle.setVerticalAlignment(getDefaultIfAbsent(
                mainStyle.getVerticalAlignment(),
                defaultStyle.getVerticalAlignment(),
                SHORT_ZERO));
            mainStyle.setWrapText(getDefaultIfAbsent(mainStyle.isWrapText(), defaultStyle.isWrapText(), false));
        }

        return mainStyle;
    }

    private ExcelFont mergeFont(final ExcelFont mainFont, final ExcelFont defaultFont) {
        if (mainFont == null) {
            return defaultFont;
        }

        if (defaultFont != null) {
            mainFont.setColor(getDefaultIfAbsent(mainFont.getColor(), defaultFont.getColor(), SHORT_ZERO));
            mainFont.setBold(getDefaultIfAbsent(mainFont.isBold(), defaultFont.isBold(), false));
            mainFont.setCharSet(getDefaultIfAbsent(mainFont.getCharSet(), defaultFont.getCharSet(), 0));
            mainFont.setFontHeight(getDefaultIfAbsent(
                mainFont.getFontHeight(),
                defaultFont.getFontHeight(),
                SHORT_ZERO));
            mainFont.setFontName(getDefaultIfAbsent(mainFont.getFontName(), defaultFont.getFontName(), null));
            mainFont.setItalic(getDefaultIfAbsent(mainFont.isItalic(), defaultFont.isItalic(), false));
            mainFont.setStrikeout(getDefaultIfAbsent(mainFont.isStrikeout(), defaultFont.isStrikeout(), false));
            mainFont.setTypeOffset(getDefaultIfAbsent(
                mainFont.getTypeOffset(),
                defaultFont.getTypeOffset(),
                SHORT_ZERO));
            mainFont.setUnderline(getDefaultIfAbsent(mainFont.getUnderline(), defaultFont.getUnderline(), (byte) 0));
        }

        return mainFont;
    }

    private ExcelBorders mergeBorder(final ExcelBorders mainBorder, final ExcelBorders defaultBorder) {
        if (mainBorder == null) {
            return defaultBorder;
        }

        if (defaultBorder != null) {
            mainBorder.setLeft(mergeBorder(mainBorder.getLeft(), defaultBorder.getLeft()));
            mainBorder.setBottom(mergeBorder(mainBorder.getBottom(), defaultBorder.getBottom()));
            mainBorder.setTop(mergeBorder(mainBorder.getTop(), defaultBorder.getTop()));
            mainBorder.setRight(mergeBorder(mainBorder.getRight(), defaultBorder.getRight()));
        }

        return mainBorder;
    }

    private ExcelBorder mergeBorder(final ExcelBorder mainBorder, final ExcelBorder defaultBorder) {
        if (mainBorder == null) {
            return defaultBorder;
        }
        if (defaultBorder != null) {
            mainBorder.setColor(getDefaultIfAbsent(mainBorder.getColor(), defaultBorder.getColor(), SHORT_ZERO));
            mainBorder.setWidth(getDefaultIfAbsent(mainBorder.getWidth(), defaultBorder.getWidth(), SHORT_ZERO));
        }

        return mainBorder;
    }

    private void fillCell(
        final Cell cell, final Object cellValue, final ExcelStyle cellStyle, final Workbook workbook) {

        if (cellValue instanceof Boolean) {
            cell.setCellValue((Boolean) cellValue);
        } else if (cellValue instanceof Number) {
            cell.setCellValue(((Number) cellValue).doubleValue());
        } else if (cellValue instanceof Date) {
            cell.setCellValue((Date) cellValue);
        } else if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else if (cellValue instanceof RichTextString) {
            cell.setCellValue((RichTextString) cellValue);
        } else {
            cell.setCellValue(String.valueOf(cellValue));
        }

        cell.setCellStyle(getCellStyleFrom(cellStyle, workbook));
    }
}
