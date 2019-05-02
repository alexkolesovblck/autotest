package ru.api.post;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.bccomon.StatusCode;
import ru.bccomon.dto.user.CreateRequest;
import ru.bccomon.dto.user.CreateResponse;
import ru.bccomon.test.UsersSendRequest;
import ru.bccomon.test.UserBuilders;
import ru.yandex.qatools.allure.annotations.Title;

public class CreateUserTest {

    @Title("Create user - valid <name> [success]")
    @Test(dataProviderClass = UserBuilders.class, dataProvider = "validValue")
    public void testCreateUserValidNameSuccess(String name){
        CreateResponse createResponse = UsersSendRequest.createUserSuccess(UserBuilders.createUser(name, "developer"));
        assertEquals(createResponse, name, "developer");
    }

    @Title("Create user - valid <job> [success]")
    @Test(dataProviderClass = UserBuilders.class, dataProvider = "validValue")
    public void testCreateUserValidJobSuccess(String job){
        CreateResponse createResponse = UsersSendRequest.createUserSuccess(UserBuilders.createUser("Markus", job));
        assertEquals(createResponse, "Markus", job);
    }

    @Title("Create user [negative]")
    @Test
    public void testCreateUserNegative(){
        Response response = UsersSendRequest.createUser(CreateRequest.builder().build(), false);
        Assert.assertEquals(response.getStatusCode(), StatusCode.is522());
    }

    private void assertEquals(CreateResponse createResponse, String name, String job){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(createResponse.getName().equals(name), "Name is not same");
        softAssert.assertTrue(createResponse.getJob().equals(job), "Job is not same");
        softAssert.assertAll();
    }
}
