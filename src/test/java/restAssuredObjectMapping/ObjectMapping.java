package restAssuredObjectMapping;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ObjectMapping {

    @BeforeClass
    public void config(){
        RestAssured.baseURI = "https://reqres.in/api";
    }


//  ==========Using ONE method to deserialize both SUCCESS and FAILURE==========
//  Called by executeRegistrationAutomation method.
    private void executeRegistration(Object email, Object password){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("email", email);
        rqParameters.put("password", password);

        Response rs = given()
                .contentType(ContentType.JSON)
                .body(rqParameters)
                .post("/register");

        if (rs.statusCode() == 200){
            RegistrationSuccess rSuccess = rs.body().as(RegistrationSuccess.class);
            Assert.assertEquals(rSuccess.getId(), 4);
            Assert.assertEquals(rSuccess.getToken(), "QpwL5tke4Pnpja7X4");
            System.out.println("[executeRegistration] Status code " + rs.statusCode());
            System.out.println(rs.body().asPrettyString());
        }
        else if (rs.statusCode() == 400){
            RegistrationUnsuccessful rFail = rs.body().as(RegistrationUnsuccessful.class);
            Assert.assertEquals(rFail.getError(), "Missing password");
            System.out.println("[executeRegistration] Status code " + rs.statusCode());
            System.out.println(rs.body().asPrettyString());
        }
    }

    @Test
    public void executeRegistrationAutomation(){
        executeRegistration("eve.holt@reqres.in", "pistol");
        executeRegistration("sydney@fife", "");
    }


//  ==========Using TWO methods below to deserialize SUCCESS and FAILURE respectively==========
    @Test
    public void executeSuccessfulRegistration(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("email", "eve.holt@reqres.in");
        rqParameters.put("password", "pistol");
        Response rs = basicPOST(rqParameters);

        RegistrationSuccess rSuccess = rs.body().as(RegistrationSuccess.class);
        Assert.assertEquals(rSuccess.getId(), 4);
        Assert.assertEquals(rSuccess.getToken(), "QpwL5tke4Pnpja7X4");
        System.out.println("[executeRegistration] Status code " + rs.statusCode());
        System.out.println(rs.body().asPrettyString());
    }

    @Test
    public void executeUnsuccessfulRegistration(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("email", "sydney@fife");

        Response rs = basicPOST(rqParameters);
        RegistrationUnsuccessful rFail = rs.body().as(RegistrationUnsuccessful.class);

        Assert.assertEquals(rFail.getError(), "Missing password");
        System.out.println("[executeRegistration] Status code " + rs.statusCode());
        System.out.println(rs.body().asPrettyString());
    }

//  Testing POST method, serializing a programming object into JSON format.
    @Test
    public void executeUsersTest(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("name", "morpheus");
        rqParameters.put("job", "leader");
        Response rs = given()
                .contentType(ContentType.JSON)
                .body(rqParameters, ObjectMapperType.JACKSON_2)
                .post("/users");

        rs.then().assertThat().body("id", not(empty()))
                .and()
                .body("createdAt", not(empty()));

        System.out.println("[executeUsersTest] Status code: " + rs.statusCode());
        System.out.println(rs.body().asPrettyString());
    }

    private Response basicPOST(HashMap<String, Object> hMap){
        return given()
                .contentType(ContentType.JSON)
                .body(hMap)
                .post("/register");
    }
}
