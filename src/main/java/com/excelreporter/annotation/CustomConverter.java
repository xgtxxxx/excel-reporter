package com.excelreporter.annotation;

import com.excelreporter.converter.Converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomConverter {
    @AliasFor("value")
    Class<? extends Converter> converter();
    String[] fields() default{};
    boolean applyToAll() default false;
    Class<?>[] applyTo() default {};
}
