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

    @Test
    public void executeSuccessfulRegistration(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("email", "eve.holt@reqres.in");
        rqParameters.put("password", "pistol");
        Response rs = basicPOST(rqParameters);

        RegistrationSuccess rSuccess = rs.body().as(RegistrationSuccess.class);
        Assert.assertEquals(rSuccess.getId(), 4);
        Assert.assertEquals(rSuccess.getToken(), "QpwL5tke4Pnpja7X4");
        System.out.println("[executeSuccessfulRegistration] No errors were emitted.");
    }

    @Test
    public void executeUnsuccessfulRegistration(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("email", "sydney@fife");

        Response rs = basicPOST(rqParameters);
        RegistrationUnsuccessful rFail = rs.body().as(RegistrationUnsuccessful.class);

        Assert.assertEquals(rFail.getError(), "Missing password");
        System.out.println("[executeUnsuccessfulRegistration] No errors were emitted.");
    }

    @Test
    public void executeUsersTest(){
        HashMap<String, Object> rqParameters = new HashMap<>();
        rqParameters.put("name", "morpheus");
        rqParameters.put("job", "leader");
        given()
                .contentType(ContentType.JSON)
                .body(rqParameters, ObjectMapperType.JACKSON_2)
                .post("/users")
                .then().assertThat().body("id", not(empty()))
                .and()
                .body("createdAt", not(empty()));
        System.out.println("[executeUsersTest] No errors were emitted.");
    }

    private Response basicPOST(HashMap<String, Object> hMap){
        return given()
                .contentType(ContentType.JSON)
                .body(hMap)
                .post("/register");
    }
}
