package com.excelreporter.factory;

import com.excelreporter.handler.BgColorHandler;
import com.excelreporter.annotation.BgColors;

public abstract class AbsoluteSheetFactory implements SheetFactory {
   protected RowFactory getRowFactory(final Class<?> clazz) {
       final BgColors bgColors = clazz.getAnnotation(BgColors.class);
       final BgColorHandler bgColorHandler = bgColors == null ? null : new BgColorHandler(bgColors);

       return new DefaultRowFactory(bgColorHandler);
   }
}
