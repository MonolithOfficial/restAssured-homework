package RestAssuredHW02;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    @Test
    public void executeLastNameValidation(){
//      Calling the endpoint.
        Response res = given()
                .when()
                .get("https://chercher.tech/sample/api/product/read");

//      Extracting only names from the response and enlisting them / getting the last element.
        List<String> jsonList = res.jsonPath().getList("records.name");
        String lastName = jsonList.get(jsonList.size() - 1);

//      I am not sure what the assertion shall be in here. Would realistically be coming from a database?
        Assert.assertEquals(lastName, "rest version");

        System.out.println(jsonList.get(jsonList.size() - 1) + "\nNo errors were emitted.");
    }

    @Test
    public void executeCreationDateValidation(){
//      Constructing a validatable response.
        ValidatableResponse res = given()
                .when()
                .get("https://chercher.tech/sample/api/product/read")
                .then();

//      Logging for visual comparison.
        System.out.println(res.extract().path("records.created").toString());

//      Validating all dates in the Json array on being earlier than the current date & time.
        res.assertThat().body("records.created",
                everyItem(lessThan(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));

//      Pattern -> yyyy-MM-dd HH:mm:ss
        System.out.println("Current Date & Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("No errors were emitted.");
    }

    @Test
    public void executePOST(){
//      New Json library. Generified PUT method on HashMap data structure.
        JSONObject rqParameters = new JSONObject();
        rqParameters.put("id", 7);
        rqParameters.put("email", "gendo.ikari2015@nerv.org");

        System.out.println(rqParameters.toMap());

//      Sending as Json.
        given().header("contentType", "application/json")
                .body(rqParameters.toMap())
                .post("https://reqres.in/api/users")
//       Logging only if created.
        .then().log().ifStatusCodeIsEqualTo(201).log().all();
    }
}
