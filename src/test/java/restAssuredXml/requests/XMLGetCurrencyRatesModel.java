package restAssuredXml.requests;

import io.restassured.config.RestAssuredConfig;
import io.restassured.config.XmlConfig;
import io.restassured.specification.RequestSpecification;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import static io.restassured.RestAssured.given;

public class XMLGetCurrencyRatesModel {

    public static RequestSpecification GetCurrencyRates() throws IOException {
        FileInputStream xmlRaw=new FileInputStream("GetCurrencyRates.xml");

        return given()
                .config(RestAssuredConfig.config()
                        .xmlConfig(XmlConfig.xmlConfig()
                                .with()
                                .namespaceAware(true)
                                .declareNamespace("xs", "http://www.w3.org/2001/XMLSchema")))
                .header("Content-Type","text/xml")
                .and().body(IOUtils.toString(xmlRaw, StandardCharsets.UTF_8));
    }
}
