package com.excelreporter.annotation;

import com.excelreporter.handler.RegionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRegionHandler {
    Class<? extends RegionHandler> value();
}
