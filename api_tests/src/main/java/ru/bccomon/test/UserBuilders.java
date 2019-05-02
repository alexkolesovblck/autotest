package ru.bccomon.test;

import org.testng.annotations.DataProvider;
import ru.bccomon.TestTools;
import ru.bccomon.dto.user.CreateRequest;
import ru.bccomon.dto.user.CreateResponseId;
import ru.bccomon.dto.user.DataInfo;

public class UserBuilders {

    @DataProvider
    public static Object[][] validValue() {
        return new Object[][]{
                {TestTools.generateRusLowerString(255)},
                {TestTools.generateRusLowerString(10)},
                {TestTools.generateRusUpperString(10)}

        };
    }

    public static CreateRequest createUser(String name, String job){
        return CreateRequest.builder().name(name).job(job).build();
    }

    public static CreateResponseId responseId(String firstName, String lastName, String id){
        return CreateResponseId.builder()
                .data(dataInfo(firstName, lastName, id))
                .build();
    }

    public static DataInfo dataInfo(String firstName, String lastName, String id){
        return DataInfo.builder()
                .firstName(firstName)
                .lastName(lastName)
                .id(Integer.valueOf(id))
                .avatar("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg").build();
    }

    public static final class Reason {
        public static final String USER_NOT_FOUND = "user_not_found";
    }
}
