package com.excelreporter.service;

import com.excelreporter.exception.PowerExcelException;
import com.excelreporter.model.SheetWrapper;
import com.excelreporter.model.style.ColumnStyle;
import com.excelreporter.model.style.ExcelBorders;
import com.excelreporter.model.style.ExcelFont;
import com.excelreporter.style.Template;
import com.excelreporter.model.style.ExcelStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractExcelBuilder implements ExcelBuilder {
    protected static final short SHORT_ZERO = 0;
    private final Map<ExcelStyle, CellStyle> cellStyles = new HashMap<>();
    private final Map<Integer, ColumnStyle> columnStyles = new HashMap<>();

    @Override
    public void build(final SheetWrapper sheetWrapper, final OutputStream os) {
        try (Workbook workbook = new SXSSFWorkbook(500)) {
            init(getDefaultTemplateIfAbsent(sheetWrapper));
            createSheet(sheetWrapper, workbook);
            workbook.write(os);
        } catch (final IOException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    @Override
    public void build(final List<SheetWrapper> sheetWrappers, final OutputStream os) {
        try (Workbook workbook = new SXSSFWorkbook(500)) {
            for (final SheetWrapper sheetWrapper : sheetWrappers) {
                init(getDefaultTemplateIfAbsent(sheetWrapper));
                createSheet(sheetWrapper, workbook);
            }
            workbook.write(os);
        } catch (final IOException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    public abstract Sheet createSheet(final SheetWrapper sheetWrapper, final Workbook workbook);

    public abstract Template getDefaultTemplateIfAbsent(final SheetWrapper sheetWrapper);

    protected Optional<ColumnStyle> getColumnStyle(final int columnIndex) {
        return Optional.ofNullable(columnStyles.get(columnIndex));
    }

    protected <T> T getDefaultIfAbsent(final T newValue, final T defaultValue, final T absentValue) {
        if (newValue == null) {
            return defaultValue;
        }

        if (newValue.getClass().isPrimitive()) {
            return newValue == absentValue ? defaultValue : newValue;
        } else {
            return newValue.equals(absentValue) ? defaultValue : newValue;
        }
    }

    protected CellStyle getCellStyleFrom(final ExcelStyle excelStyle, final Workbook workbook) {
        if (excelStyle == null) {
            return null;
        }

        CellStyle cellStyle = cellStyles.get(excelStyle);
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cellStyles.put(excelStyle, cellStyle);
            cellStyle.setFont(getFontFrom(excelStyle.getFont(), workbook));
            cellStyle.setAlignment(getDefaultIfAbsent(excelStyle.getAlignment(), cellStyle.getAlignment(), SHORT_ZERO));
            cellStyle.setWrapText(getDefaultIfAbsent(excelStyle.isWrapText(), cellStyle.getWrapText(), false));
            cellStyle.setVerticalAlignment(getDefaultIfAbsent(
                excelStyle.getVerticalAlignment(),
                cellStyle.getVerticalAlignment(),
                SHORT_ZERO));
            cellStyle.setFillPattern(getDefaultIfAbsent(
                excelStyle.getFillPattern(),
                cellStyle.getFillPattern(),
                SHORT_ZERO));
            cellStyle.setFillForegroundColor(getDefaultIfAbsent(
                excelStyle.getBgColor(),
                cellStyle.getFillForegroundColor(),
                SHORT_ZERO));

            final ExcelBorders excelBorders = excelStyle.getBorder();
            if (excelBorders != null) {
                cellStyle.setBorderTop(excelBorders.getTop().getWidth());
                cellStyle.setTopBorderColor(excelBorders.getTop().getColor());

                cellStyle.setBorderRight(excelBorders.getRight().getWidth());
                cellStyle.setRightBorderColor(excelBorders.getRight().getColor());

                cellStyle.setBorderBottom(excelBorders.getBottom().getWidth());
                cellStyle.setBottomBorderColor(excelBorders.getBottom().getColor());

                cellStyle.setBorderLeft(excelBorders.getLeft().getWidth());
                cellStyle.setLeftBorderColor(excelBorders.getLeft().getColor());
            }
        }

        return cellStyle;
    }

    private Font getFontFrom(final ExcelFont excelFont, final Workbook workbook) {
        if (excelFont != null) {
            final Font font = workbook.createFont();
            font.setFontName(getDefaultIfAbsent(excelFont.getFontName(), font.getFontName(), null));
            font.setFontHeight(getDefaultIfAbsent(excelFont.getFontHeight(), font.getFontHeight(), SHORT_ZERO));
            font.setItalic(getDefaultIfAbsent(excelFont.isItalic(), font.getItalic(), false));
            font.setStrikeout(getDefaultIfAbsent(excelFont.isStrikeout(), font.getStrikeout(), false));
            font.setColor(getDefaultIfAbsent(excelFont.getColor(), font.getColor(), SHORT_ZERO));
            font.setTypeOffset(getDefaultIfAbsent(excelFont.getTypeOffset(), font.getTypeOffset(), SHORT_ZERO));
            font.setUnderline(getDefaultIfAbsent(excelFont.getUnderline(), font.getUnderline(), (byte) 0));
            font.setBold(getDefaultIfAbsent(excelFont.isBold(), font.getBold(), false));
            font.setCharSet(getDefaultIfAbsent(excelFont.getCharSet(), font.getCharSet(), 0));

            return font;
        }

        return null;
    }

    private void init(final Template template) {
        cellStyles.clear();
        columnStyles.clear();
        columnStyles.putAll(
            template.getDefaultColumnStyle()
                .stream()
                .collect(Collectors.toMap(columnStyle -> columnStyle.getIndex(), columnStyle -> columnStyle)));
    }
}
