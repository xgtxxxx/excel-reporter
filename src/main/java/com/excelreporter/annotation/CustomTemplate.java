package com.excelreporter.annotation;

import com.excelreporter.style.Template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTemplate {
    @AliasFor("value")
    Class<? extends Template> template();
}
