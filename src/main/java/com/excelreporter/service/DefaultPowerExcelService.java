package com.excelreporter.service;

import com.excelreporter.annotation.CustomTemplate;
import com.excelreporter.exception.PowerExcelException;
import com.excelreporter.factory.DefaultSheetFactory;
import com.excelreporter.factory.SheetFactory;
import com.excelreporter.model.SheetWrapper;
import com.excelreporter.model.Sheets;
import com.excelreporter.style.Template;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class DefaultPowerExcelService implements PowerExcelService {

    private ExcelBuilder excelBuilder;
    private SheetFactory sheetFactory;

    public void setExcelBuilder(final ExcelBuilder excelBuilder) {
        this.excelBuilder = excelBuilder;
    }

    public void setSheetFactory(final SheetFactory sheetFactory) {
        this.sheetFactory = sheetFactory;
    }

    @Override
    public <T> void create(final String path, final List<T> models) {
        try (final OutputStream outputStream = new FileOutputStream(path)) {
            create(outputStream, models);
        } catch (final Exception e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    @Override
    public <T> void create(final OutputStream outputStream, final List<T> models) {
        final Class<?> clazz = models.get(0).getClass();
        final CustomTemplate template = clazz.getAnnotation(CustomTemplate.class);
        try {
            Template powerTemplate = null;
            if (template != null) {
                powerTemplate = template.template().newInstance();
            }
            create(outputStream, models, powerTemplate);
        } catch (final Exception e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    @Override
    public <T> void create(final String path, final List<T> models, final Template template) {
        try (final OutputStream outputStream = new FileOutputStream(path)) {
            create(outputStream, models, template);
        } catch (final Exception e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    @Override
    public <T> void create(final OutputStream outputStream, final List<T> models, final Template template) {
        final SheetWrapper sheetWrapper = getOrDefaultFactory().create(models, template);
        getOrDefaultExcelBuilder().build(sheetWrapper, outputStream);
    }

    @Override
    public <T> void create(final String path, final Sheets sheets) {
        try (final OutputStream outputStream = new FileOutputStream(path)) {
            create(outputStream, sheets);
        } catch (final Exception e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    @Override
    public <T> void create(final OutputStream outputStream, final Sheets sheets) {
        getOrDefaultExcelBuilder().build(getOrDefaultFactory().create(sheets), outputStream);
    }

    private ExcelBuilder getOrDefaultExcelBuilder() {
        return excelBuilder == null ? new DefaultExcelBuilder() : excelBuilder;
    }

    private SheetFactory getOrDefaultFactory() {
        return sheetFactory == null ? new DefaultSheetFactory() : sheetFactory;
    }
}
