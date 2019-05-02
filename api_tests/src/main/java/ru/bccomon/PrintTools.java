package ru.bccomon;


import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.util.Objects;

public class PrintTools {
    private final static Logger LOGGER = Logger.getLogger(PrintTools.class);

    @Deprecated
    public static void logSpecification(RequestSpecification requestSpecification) {
        Objects.requireNonNull(requestSpecification, "RequestSpecification = null");
        LOGGER.info("\n***** REQUEST *****\n" + requestSpecification.log().all());
    }
}
