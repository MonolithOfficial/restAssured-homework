package RestAssuredHW01;

import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class RJsonTest {

    @DataProvider
    public Object[][] circuitProvider(){
//      partially avoiding code repetition.
        Response res = given().when().get("http://ergast.com/api/f1/2017/circuits.json");

        return new Object[][]{
                {res.
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits[1].circuitId").toString(),
                res.
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits.Location[1].country").toString()},

                {res.
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits[5].circuitId").toString(),
                res.
                        then().
                        extract().
                        path("MRData.CircuitTable.Circuits.Location[5].country").toString()}
        };
    }

    @Test(dataProvider = "circuitProvider")
    public void executeRJsonTest(String circuitId, String countryId){
        System.out.println(circuitId);
        given()
                .pathParam("circuitId", circuitId)
                .when()
                .get("http://ergast.com/api/f1/circuits/{circuitId}.json")
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.Location[0].country", equalTo(countryId));
    }

    @Test(dependsOnMethods = {"executeRJsonTest"})
    public void successLog(){
        System.out.println("No failed assertions.");
    }
}
