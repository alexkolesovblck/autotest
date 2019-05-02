package ru.bccomon.test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import ru.bccomon.MethodRequest;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Objects;

public class TestStepsCommon {
    private final static Logger LOGGER = Logger.getLogger(TestStepsCommon.class);

    @Step("<{0}>")
    public static void printCheckStep(String nameStep) {
        LOGGER.info("Check " + nameStep);
    }

    @Step("{0}")
    public static void printStep(String nameStep) {
        LOGGER.info("# " + nameStep);
    }

    @Step("Send request {1}: {2}")
    public static Response sendRequest(RequestSpecification requestSpecification, MethodRequest typeRequest, String url) {
        Objects.requireNonNull(requestSpecification, "requestSpecification is null");
        Objects.requireNonNull(url, "url is null");
       LOGGER.info("\n***** REQUEST*****\n" + requestSpecification.log().all());

        Response response;
        switch (typeRequest) {
            case GET:
                response = requestSpecification.get(url);
                break;
            case POST:
                response = requestSpecification.post(url);
                break;
            case DELETE:
                response = requestSpecification.delete(url);
                break;
            case PUT:
                response = requestSpecification.put(url);
                break;
            default:
                throw new IllegalArgumentException(typeRequest.name() + " unknown!!!");
        }
        LOGGER.info("\n***** RESPONSE*****\n" + response.prettyPrint());
        return response;
    }
}
