package com.excelreporter.factory;

import com.excelreporter.annotation.*;
import com.excelreporter.exception.PowerExcelException;
import com.excelreporter.utils.Iterators;
import com.excelreporter.handler.AnnotationRegionHandler;
import com.excelreporter.handler.BgColorHandler;
import com.excelreporter.handler.RegionHandler;
import com.excelreporter.model.FieldWrapper;
import com.excelreporter.model.SheetWrapper;
import com.excelreporter.model.Sheets;
import com.excelreporter.style.Template;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultSheetFactory extends AbsoluteSheetFactory {
    @Override
    public List<SheetWrapper> create(final Sheets sheets) {
        final Map<String, List<SheetWrapper>> sheetMap =
            Iterators
                .mapWithIndex(
                    sheets.getSheets(),
                    (index, sheetPair) -> create(index, sheetPair.getModels(), sheetPair.getTemplate()))
                .collect(Collectors.groupingBy(sheetWrapper -> sheetWrapper.getName()));

        sheetMap.forEach((name, sheetWrappers) -> {
            if (name != null && sheetWrappers.size() > 1) {
                Iterators
                    .forEachWithIndex(
                        sheetWrappers,
                        (index, sheetWrapper) -> sheetWrapper.setName(sheetWrapper.getName() + (index + 1)));
            }
        });

        return sheetMap.values()
            .stream()
            .flatMap(sheetWrappers -> sheetWrappers.stream())
            .collect(Collectors.toList());
    }

    @Override
    public SheetWrapper create(final List<?> models, final Template template) {
        return create(1, models, template);
    }

    private SheetWrapper create(final int sheetIndex, final List<?> models, final Template powerTemplate) {
        final Class<?> clazz = models.get(0).getClass();
        final List<FieldWrapper> sortedFields =
            Iterators
                .mapWithIndex(getFields(clazz), (index, field) -> mapTo(index + 1, field, clazz))
                .sorted(comparator())
                .collect(Collectors.toList());

        final SheetWrapper sheet = new SheetWrapper();
        sheet.setIndex(sheetIndex);
        sheet.setTemplate(powerTemplate);
        getRegionHandler(clazz, models.size()).ifPresent(regionHandler -> sheet.setRegionHandler(regionHandler));
        getTitle(clazz).ifPresent(title -> sheet.setName(title));
        getTemplate(clazz).ifPresent(tpl -> sheet.setTemplate(tpl));
        final RowFactory rowFactory = getRowFactory(clazz);
        rowFactory.createTitle(sheet.nextRowIndex(), sortedFields).ifPresent(titleRow -> sheet.addRow(titleRow));
        models
            .stream()
            .forEach(row -> {
                sheet.addRow(rowFactory.create(sheet.nextRowIndex(), row, sortedFields));
            });

        return sheet;
    }

    private Optional<RegionHandler> getRegionHandler(final Class<?> clazz, final int totalRows) {
        final CustomRegionHandler handler = clazz.getAnnotation(CustomRegionHandler.class);
        if(handler != null) {
            try{
                return Optional.of(handler.value().newInstance());
            } catch (final IllegalAccessException|InstantiationException e) {
                throw new PowerExcelException(e.getMessage(), e);
            }
        }

        final Region region = clazz.getAnnotation(Region.class);
        if(region != null) {
            return Optional.of(new AnnotationRegionHandler(region));
        }

        return Optional.empty();
    }

    private Optional<BgColorHandler> getBgColorHandler(final Class<?> clazz) {
        final BgColors bgColors = clazz.getAnnotation(BgColors.class);
        if (bgColors != null) {
            return Optional.of(new BgColorHandler(bgColors));
        }
        return Optional.empty();
    }

    private Optional<Template> getTemplate(final Class<?> clazz) {
        try {
            final CustomTemplate
                template = clazz.getAnnotation(CustomTemplate.class);
            if (template != null) {
                return Optional.of(template.template().newInstance());
            }
        } catch (final IllegalAccessException | InstantiationException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }

        return Optional.empty();
    }

    private FieldWrapper mapTo(final int fieldIndex, final Field field, final Class<?> clazz) {
        final FieldWrapper fieldWrapper = new FieldWrapper();
        fieldWrapper.setIndex(fieldIndex);
        fieldWrapper.setField(field);
        try {
            final CustomConverter fieldConverter = field.getAnnotation(CustomConverter.class);
            if (fieldConverter != null) {
                fieldWrapper.setFieldConverter(fieldConverter.converter().newInstance());
            }
            final CustomConverter classConverter = clazz.getAnnotation(CustomConverter.class);
            if (classConverter != null) {
                fieldWrapper.setClassConverter(classConverter);
            }
            final CustomStyle customStyle = field.getAnnotation(CustomStyle.class);
            if (customStyle != null) {
                fieldWrapper.setStyle(customStyle.value().newInstance());
            }
        } catch (final IllegalAccessException | InstantiationException e) {
            throw new PowerExcelException(e.getMessage(), e);
        }

        return fieldWrapper;
    }

    private Optional<String> getTitle(final Class<?> clazz) {
        final Title title = clazz.getAnnotation(Title.class);
        if (title != null) {
            return Optional.of(title.value());
        }

        return Optional.empty();
    }

    /**
     * All field from this class and parent class, except the ignored field
     *
     * @param clazz
     * @return
     */
    private List<Field> getFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<>();
        fields.addAll(0, Arrays.asList(clazz.getDeclaredFields()));
        final Class<?> parent = clazz.getSuperclass();
        if (Objects.nonNull(parent)) {
            fields.addAll(0, getFields(parent));
        }

        return removeIgnoredField(fields);
    }

    private Comparator<FieldWrapper> comparator() {
        return (next, last) -> {
            int indexOne = last.getIndex();
            int indexTwo = next.getIndex();
            final OrderIndex columnOne = last.getAnnotation(OrderIndex.class);
            final OrderIndex columnTwo = next.getAnnotation(OrderIndex.class);

            if (Objects.nonNull(columnOne) && Objects.nonNull(columnTwo)) {
                if (columnOne.value() == columnTwo.value()) {
                    return 0;
                }
            }

            if (Objects.nonNull(columnOne) && columnOne.value() >= indexTwo) {
                indexOne = columnOne.value() + 1;
            }
            if (Objects.nonNull(columnTwo) && columnTwo.value() <= indexOne) {
                indexTwo = columnTwo.value() - 1;
            }

            return indexTwo - indexOne;
        };
    }

    private List<Field> removeIgnoredField(final List<Field> fields) {

        return fields
            .stream()
            .filter(field -> Objects.isNull(field.getAnnotation(Ignore.class)))
            .collect(Collectors.toList());
    }
}
