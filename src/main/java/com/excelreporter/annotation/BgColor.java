package com.excelreporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BgColor {
    @AliasFor("value")
    short color();
    /**
     * start from 0
     * @return
     */
    int rowIndex() default -1;
    int stepLength() default 1;
    int loopSkipLength() default 0;
}
