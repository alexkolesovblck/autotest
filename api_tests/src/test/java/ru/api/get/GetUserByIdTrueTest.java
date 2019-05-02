package ru.api.get;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.bccomon.CheckMethod;
import ru.bccomon.StatusCode;
import ru.bccomon.TestTools;
import ru.bccomon.dto.user.CreateResponse;
import ru.bccomon.dto.user.CreateResponseId;
import ru.bccomon.test.UsersSendRequest;
import ru.bccomon.test.UserBuilders;
import ru.yandex.qatools.allure.annotations.Title;

public class GetUserByIdTrueTest {

    /**
     * Тесты по сценарию "Необходимо создать пользователя, чтобы получить информацию о нем"
     */

    private CreateResponse createResponse;
    private String userId;

    @BeforeMethod()
    public void setUp(){
        createResponse = UsersSendRequest.createUserSuccess(UserBuilders.createUser("Markus", "developer"));
        userId = createResponse.getId();
    }

    @Title("Get user by id [success]")
    @Test
    public void testGetUserByIdSuccess(){
        CreateResponseId createResponse = UsersSendRequest.getUserByIdSuccess(userId);
        Assert.assertEquals(createResponse, UserBuilders.responseId("Markus", null, userId));
    }


    @Title("Get user by id [negative]")
    @Test
    public void testGetUserByIdNegative(){
        Response response = UsersSendRequest.getUserById(TestTools.generateNumber(5));
        CheckMethod.checkErrorCommon(StatusCode.is404(), UserBuilders.Reason.USER_NOT_FOUND, response);
    }

}
