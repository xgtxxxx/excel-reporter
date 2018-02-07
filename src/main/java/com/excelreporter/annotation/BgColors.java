package com.excelreporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BgColors {
    /**
     * the color declared later will recover the one declared before.
     * @return
     */
    @AliasFor("value")
    BgColor[] colors();
}
