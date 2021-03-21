package restAssuredXml.APIFramework;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.*;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Endpoints {
    private static final String BASE_URI = "http://currencyconverter.kowabunga.net";

    public static Response GetCurrencyRates() throws IOException {
        FileInputStream xmlRaw=new FileInputStream("GetCurrencyRates.xml");

        RestAssured.baseURI = BASE_URI;
        return given()
                .config(RestAssuredConfig.config()
                        .xmlConfig(XmlConfig.xmlConfig()
                                .with()
                                .namespaceAware(true)
                                .declareNamespace("xs", "http://www.w3.org/2001/XMLSchema")))
                .header("Content-Type","text/xml")
                .and().body(IOUtils.toString(xmlRaw, StandardCharsets.UTF_8))
                .when().post("/converter.asmx").then().assertThat().statusCode(200).extract().response();
    }
}
