package ru.bccomon;

import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.bccomon.dto.user.ResponseError;
import ru.yandex.qatools.allure.annotations.Step;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.String.format;

public class CheckMethod extends MatcherAssert {
    private final static Logger LOGGER = Logger.getLogger(CheckMethod.class);

    @Step("Check response code is {0}, reason is \"{1}\"")
    public static ResponseError checkErrorCommon(int errorStatusCode, String expectedReason, Response response) {
        Objects.requireNonNull(expectedReason, "reason = null");
        Objects.requireNonNull(response, "response = null");
        Assert.assertEquals(response.statusCode(), errorStatusCode, "Wrong status code!");
        ResponseError error = (ResponseError) ObjMapperMethod.readValue(response, ResponseError.class, "ResponseError");
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(error.getReason(), expectedReason, "Wrong reason!");
        sa.assertEquals(error.getCode(), errorStatusCode, "Wrong code!");
        sa.assertNotNull(error.getHostname(), "Hostname is null!");
        sa.assertNotNull(error.getMessage(), "Message is null!");
        sa.assertAll();
        return error;
    }

    public static ResponseError checkErrorCommon(int errorStatusCode, String reason, String message, Response response) {
        ResponseError error = checkErrorCommon(errorStatusCode, reason, response);
        String str_error = format("Error in message!\nExpected: %s, actual: %s", message, error.getMessage());
        Assert.assertTrue(error.getMessage().contains(message), str_error);
        return error;
    }

    @Step("Check response code is 200")
    public static Response checkSuccess(Response response) {
        Objects.requireNonNull(response, "response = null");
        Assert.assertEquals(response.getStatusCode(), StatusCode.is200(), "Wrong status code!");
        return response;
    }

    @Step("Check response code is 201")
    public static Response checkCreateSuccess(Response response) {
        Objects.requireNonNull(response, "response = null");
        Assert.assertEquals(response.getStatusCode(), StatusCode.is201(), "Wrong status code!");
        return response;
    }

    @Step("Expect value: {0}")
    public static void expectValue(String nameCheck, Runnable runnable) {
        LOGGER.info("Expect value: " + nameCheck);
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void equalsDataTime(LocalDateTime actualData, LocalDateTime expectedData) {
        Objects.requireNonNull(actualData, "actualData is null");
        Objects.requireNonNull(expectedData, "expectedData is null");
        Assert.assertEquals(actualData.getYear(), expectedData.getYear(), "Год не совпадает");
        Assert.assertEquals(actualData.getMonth(), expectedData.getMonth(), "Месяц не совпадает");
        Assert.assertEquals(actualData.getHour(), expectedData.getHour(), "Час не совпадает");
        Assert.assertTrue(actualData.getMinute() - 10 <= expectedData.getMinute()
                && expectedData.getMinute() <= actualData.getMinute() + 10, "Минуты различаются больше, чем на 10 минут");
    }
}
