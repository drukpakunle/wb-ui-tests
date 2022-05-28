package ru.wildberries.dataprovider;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import static org.testng.Assert.fail;

public class DtoController<T> {

    private T dtoObject;
    private final Class<T> dtoTypeClass;

    public DtoController(Class<T> dtoTypeClass) {
        this.dtoTypeClass = dtoTypeClass;
    }

    public T getDataFromFile(String valuesFileName) {
        String valuesDirectory = "src/main/resources/";
        File valuesFile = new File(valuesDirectory, valuesFileName);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            dtoObject = objectMapper.readValue(valuesFile, dtoTypeClass);
        } catch (Exception e) {
            fail("Can not parse file " + valuesFileName + "\n" + e.getMessage());
        }

        return dtoObject;
    }
}
