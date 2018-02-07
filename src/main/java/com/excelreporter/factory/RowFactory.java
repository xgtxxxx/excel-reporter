package com.excelreporter.factory;

import com.excelreporter.model.FieldWrapper;
import com.excelreporter.model.RowWrapper;

import java.util.List;
import java.util.Optional;

public interface RowFactory {
    RowWrapper create(final int rowIndex, final Object rowObj, final List<FieldWrapper> fields);
    Optional<RowWrapper> createTitle(final int rowIndex, final List<FieldWrapper> fields);
}
