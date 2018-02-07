package com.excelreporter.model;

import com.excelreporter.annotation.CustomConverter;
import com.excelreporter.annotation.Title;
import com.excelreporter.exception.PowerExcelException;
import com.excelreporter.converter.Converter;
import com.excelreporter.style.Style;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class FieldWrapper {
    private int index;
    private Field field;
    private CustomConverter classCustomConverter;
    private Converter fieldConverter;
    private Converter classConverter;
    private Style style;

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setClassConverter(final CustomConverter classCustomConverter) {
        try{
            this.classCustomConverter = classCustomConverter;
            if(classCustomConverter != null) {
                this.classConverter = classCustomConverter.converter().newInstance();
            }
        } catch (final IllegalAccessException|InstantiationException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    public void setStyle(final Style style) {
        this.style = style;
    }

    public void setFieldConverter(final Converter fieldConverter) {
        this.fieldConverter = fieldConverter;
    }

    public void setField(final Field field) {
        this.field = field;
    }

    public Object getValue(final Object row) {
        try{
            field.setAccessible(true);
            final Object value = field.get(row);
            if (fieldConverter != null) {
                return fieldConverter.convertTo(value);
            }
            if(needToClassConvert(value)) {
                return classConverter.convertTo(value);
            }

            return value;
        } catch (final IllegalAccessException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }
    }

    private boolean needToClassConvert(final Object value) {
        for(final Class<?> clazz: classCustomConverter.applyTo()) {
            if(clazz.isAssignableFrom(value.getClass())) {
                return true;
            }
        }
        if (Arrays.asList(classCustomConverter.fields()).contains(field.getName())) {
            return true;
        }

        return classCustomConverter.applyToAll();
    }

    public String getTitle() {
        String name = field.getName();
        final Title title = field.getAnnotation(Title.class);
        if (Objects.nonNull(title)) {
            name = title.value();
        }

        return name;
    }

    public <T extends Annotation> T getAnnotation(final Class<T> clazz) {
        return field.getAnnotation(clazz);
    }

    public Style getStyle() {
        return this.style;
    }
}
