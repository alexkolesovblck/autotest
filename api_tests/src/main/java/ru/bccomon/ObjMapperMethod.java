package ru.bccomon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ObjMapperMethod {
    public static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static Object readValue(Response response, Class classObjectMapName, String className) {
        Objects.requireNonNull(response, "response = null");
        return readValue(response.getBody().asString(), classObjectMapName);
    }

    public static Object readValue(String stringValue, Class classObjectMapName) {
        Objects.requireNonNull(stringValue, "response = null");
        Objects.requireNonNull(classObjectMapName, "classObjectMapName = null");

        Object readValue;
        try {
            readValue = MAPPER.readValue(stringValue, classObjectMapName);
        } catch (IOException e) {
            throw new IllegalStateException("Error deserialize Response JSON:  " + stringValue +
                    " \n class " + classObjectMapName.getSimpleName(), e);
        }
        return readValue;
    }

    public static List readValueList(Response response, TypeReference typeReference) {
        Objects.requireNonNull(response, "response = null");
        Objects.requireNonNull(typeReference, "typeReference = null");
        List list;
        try {
            list = MAPPER.readValue(response.getBody().asString(), typeReference);
        } catch (IOException e) {
            throw new IllegalStateException("Error read value from response " + typeReference.getType() + " !!!!", e);
        }
        return list;
    }

    public static String convertValueAsString(Object object) {
        Objects.requireNonNull(object, "object is null");
        String valueAsString = null;
        try {
            valueAsString = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
        }
        return valueAsString;
    }

    public static boolean compareJsonObject(String jsonString1, String jsonString2) {
        JsonNode tree1 = null;
        JsonNode tree2 = null;
        try {
            tree1 = MAPPER.readTree(jsonString1);
            tree2 = MAPPER.readTree(jsonString2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree1.equals(tree2);
    }
}
