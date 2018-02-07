package com.excelreporter.model;

import com.excelreporter.style.Template;

import java.util.List;

public class SheetPair {
    private final List<?> models;
    private final Template template;

    public SheetPair(final List<?> models, final Template template) {
        this.models = models;
        this.template = template;
    }

    public List<?> getModels() {
        return models;
    }

    public Template getTemplate() {
        return template;
    }
}
