package restAssuredXml.APIFramework;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import restAssuredXml.requests.XMLGetCurrencyRatesModel;

import java.io.IOException;

public class Endpoints {
    private static final String BASE_URI = "http://currencyconverter.kowabunga.net";

    public static Response getResponseWithDate() throws IOException {
        RestAssured.baseURI = BASE_URI;
        RequestSpecification request = XMLGetCurrencyRatesModel.GetCurrencyRates();
        return request.when().post("/converter.asmx").then().assertThat().statusCode(200).extract().response();
    }
}
