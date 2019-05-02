package ru.bccomon.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.bccomon.CheckMethod;
import ru.bccomon.MethodRequest;
import ru.bccomon.ObjMapperMethod;
import ru.bccomon.dto.user.CreateRequest;
import ru.bccomon.dto.user.CreateResponse;
import ru.bccomon.dto.user.CreateResponseId;
import ru.bccomon.services.MethodEnum;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Objects;

public class UsersSendRequest {

    private static RequestSpecification userBaseSpec(boolean noFilters) {
        RequestSpecification specification = RestAssured
                .given()
                .baseUri("https://reqres.in/")
                .contentType("application/json;charset=UTF-8")
                .accept("application/json;charset=UTF-8");
        return noFilters ? specification.noFilters() : specification;
    }


    @Step("Create user")
    public static Response createUser(CreateRequest createRequest, boolean noFilters) {
        Objects.requireNonNull(createRequest, "createRequest is null");
        return TestStepsCommon.sendRequest(userBaseSpec(noFilters).body(createRequest),
                MethodRequest.POST, MethodEnum.POST_USER.getKey());
    }

    public static CreateResponse createUserSuccess(CreateRequest createRequest) {
        Response response = createUser(createRequest, false);
        CheckMethod.checkCreateSuccess(response);
        return (CreateResponse) ObjMapperMethod.readValue(response, CreateResponse.class, CreateResponse.class.getSimpleName());
    }

    @Step("Get user by id = {0}")
    public static Response getUserById(String userId) {
        return TestStepsCommon.sendRequest(userBaseSpec(false).pathParam("userId", userId),
                MethodRequest.GET, MethodEnum.GET_SINGLE_USER.getKey());
    }

    public static CreateResponseId getUserByIdSuccess(String userId) {
        Response response = getUserById(userId);
        CheckMethod.checkSuccess(response);
        return (CreateResponseId) ObjMapperMethod.readValue(response, CreateResponseId.class, CreateResponseId.class.getSimpleName());
    }

}