package com.excelreporter.factory;

import com.excelreporter.annotation.Title;
import com.excelreporter.handler.BgColorHandler;
import com.excelreporter.model.CellWrapper;
import com.excelreporter.model.FieldWrapper;
import com.excelreporter.model.RowWrapper;
import com.excelreporter.utils.Iterators;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultRowFactory implements RowFactory {
    private CellFactory cellFactory;
    private final Optional<BgColorHandler> bgColorHandler;

    public DefaultRowFactory(final BgColorHandler bgColorHandler) {
        this.bgColorHandler = Optional.ofNullable(bgColorHandler);
    }

    @Override
    public RowWrapper create(final int rowIndex, final Object rowObj, final List<FieldWrapper> fields) {
        final List<CellWrapper> cells =
            Iterators.mapToListWithIndex(fields, (columnIndex, field) ->
                getOrDefaultCellFactory().create(
                    field.getValue(rowObj),
                    field.getStyle(),
                    rowIndex,
                    columnIndex,
                    false));

        return getDecoratedRow(rowIndex, cells);
    }

    @Override
    public Optional<RowWrapper> createTitle(final int rowIndex, final List<FieldWrapper> fields) {
        if (hasTitle(fields)) {
            final List<CellWrapper> titles =
                Iterators.mapToListWithIndex(fields, (columnIndex, field) ->
                    getOrDefaultCellFactory().create(
                        field.getTitle(),
                        field.getStyle(),
                        rowIndex,
                        columnIndex,
                        true));

            return Optional.of(getDecoratedRow(rowIndex, titles));
        }

        return Optional.empty();
    }

    private RowWrapper getDecoratedRow(final int index, final List<CellWrapper> cells) {
        final RowWrapper row = new RowWrapper();
        row.setIndex(index);
        row.setCells(cells);
        if(bgColorHandler.isPresent()) {
            bgColorHandler.get().getColor(index).ifPresent(color ->  row.setBackgroundColor(color));
        }

        return row;
    }

    /**
     * If all title in @Title is blank, return false, else true.
     *
     * @param fields
     * @return
     */
    private boolean hasTitle(final List<FieldWrapper> fields) {
        return fields
            .stream()
            .anyMatch(field -> Objects.nonNull(field.getAnnotation(Title.class)));
    }

    public void setCellFactory(final CellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    private CellFactory getOrDefaultCellFactory() {
        if (cellFactory == null) {
            cellFactory = new DefaultCellFactory();
        }

        return cellFactory;
    }
}
