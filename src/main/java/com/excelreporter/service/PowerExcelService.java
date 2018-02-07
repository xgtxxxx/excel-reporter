package com.excelreporter.service;

import com.excelreporter.model.Sheets;
import com.excelreporter.style.Template;

import java.io.OutputStream;
import java.util.List;

public interface PowerExcelService {
    <T> void create(String path, List<T> models);
    <T> void create(OutputStream outputStream, List<T> models);
    <T> void create(String path, List<T> models, Template template);
    <T> void create(OutputStream outputStream, List<T> models, Template template);
    <T> void create(String path, Sheets sheets);
    <T> void create(OutputStream outputStream, Sheets sheets);
}
