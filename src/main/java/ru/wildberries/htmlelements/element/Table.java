package ru.wildberries.htmlelements.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Table extends TypifiedElement {

    public List<WebElement> getHeadings() {
        return getWrappedElement().findElements(By.xpath(".//th"));
    }

    public List<String> getHeadingsAsString() {
        return getHeadings().stream()
                .map(WebElement::getText)
                .collect(toList());
    }

    public List<List<WebElement>> getRows() {
        return getWrappedElement()
                .findElements(By.xpath(".//tr"))
                .stream()
                .map(rowElement -> rowElement.findElements(By.xpath(".//td")))
                .filter(row -> row.size() > 0) // ignore rows with no <td> tags
                .collect(toList());
    }

    public List<List<String>> getRowsAsString() {
        return getRows().stream()
                .map(row -> row.stream()
                        .map(WebElement::getText)
                        .collect(toList()))
                .collect(toList());
    }

    public List<List<WebElement>> getColumns() {
        List<List<WebElement>> columns = new ArrayList<>();
        List<List<WebElement>> rows = getRows();

        if (rows.isEmpty()) {
            return columns;
        }

        int columnCount = rows.get(0).size();
        for (int i = 0; i < columnCount; i++) {
            List<WebElement> column = new ArrayList<>();
            for (List<WebElement> row : rows) {
                column.add(row.get(i));
            }
            columns.add(column);
        }

        return columns;
    }

    public List<WebElement> getColumnByIndex(int index) {
        return getWrappedElement().findElements(
                By.cssSelector(String.format("tr > td:nth-of-type(%d)", index)));
    }

    public List<List<String>> getColumnsAsString() {
        return getColumns().stream()
                .map(row -> row.stream()
                        .map(WebElement::getText)
                        .collect(toList()))
                .collect(toList());
    }

    public WebElement getCellAt(int i, int j) {
        return getRows().get(i).get(j);
    }

    public List<Map<String, WebElement>> getRowsMappedToHeadings() {
        List<String> headingsAsString = getHeadingsAsString();
        return getRows().stream()
                .map(row -> row.stream()
                        .collect(toMap(e -> headingsAsString.get(row.indexOf(e)), identity())))
                .collect(toList());
    }

    public List<Map<String, WebElement>> getRowsMappedToHeadings(List<String> headings) {
        return getRowsMappedToHeadings().stream()
                .map(e -> e.entrySet().stream().filter(m -> headings.contains(m.getKey()))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .collect(toList());
    }

    public List<Map<String, String>> getRowsAsStringMappedToHeadings() {
        return getRowsMappedToHeadings().stream()
                .map(m -> m.entrySet().stream()
                        .collect(toMap(Map.Entry::getKey, e -> e.getValue().getText())))
                .collect(toList());

    }

    public List<Map<String, String>> getRowsAsStringMappedToHeadings(List<String> headings) {
        return getRowsMappedToHeadings(headings).stream()
                .map(m -> m.entrySet().stream()
                        .collect(toMap(Map.Entry::getKey, e -> e.getValue().getText())))
                .collect(toList());
    }
}
