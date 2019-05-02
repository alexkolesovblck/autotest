package ru.api.get;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.bccomon.StatusCode;
import ru.bccomon.dto.user.CreateResponseId;
import ru.bccomon.test.UserBuilders;
import ru.bccomon.test.UsersSendRequest;
import ru.yandex.qatools.allure.annotations.Title;

public class GetUserByIdFalseTest {

    /**
     * Тесты по сценарию "Знаю, что такой идентификатор существует"
     */

    @Title("Get user by id [success]")
    @Test
    public void testGetUserByIdSuccess(){
        CreateResponseId createResponse = UsersSendRequest.getUserByIdSuccess("2");
        Assert.assertEquals(createResponse, UserBuilders.responseId("Janet", "Weaver", "2"));
    }


    @Title("Get user by id [negative]")
    @Test
    public void testGetUserByIdNegative(){
        Response response = UsersSendRequest.getUserById("23");
        Assert.assertEquals(response.getStatusCode(), StatusCode.is404());
    }

}
