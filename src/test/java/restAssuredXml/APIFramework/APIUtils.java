package restAssuredXml.APIFramework;

import io.restassured.internal.path.xml.NodeImpl;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;

public class APIUtils {

    final static String xmlRoot = "Envelope.Body.GetCurrencyRatesResponse.GetCurrencyRatesResult.diffgram.NewDataSet";

    public static List<NodeImpl> getNodesBetween(int lowerBound, int upperBound) throws IOException {
        Response res = Endpoints.getResponseWithDate();
        XmlPath xmlPath = new XmlPath(res.asString());

        return xmlPath.getList(xmlRoot +
                String.format(".Table.Rate.findAll {it.text().toFloat() >= %d && it.text().toFloat() <= %d}", lowerBound, upperBound));
    }

    public static String getNodeByDescription(String desc) throws IOException {
        Response res = Endpoints.getResponseWithDate();
        XmlPath xmlPath = new XmlPath(res.asString());

        return xmlPath.getString(xmlRoot +
                String.format(".Table.Description.find {it.text() == '%s'}", desc));
    }

    public static List<String> getNodeDescriptionsByRatesBetween(int lowerBound, int upperBound) throws IOException {
        Response res = Endpoints.getResponseWithDate();
        XmlPath xmlPath = new XmlPath(res.asString());

        return  xmlPath.getList(xmlRoot +
                String.format(".Table.findAll {it.Rate.text().toFloat() >= %d && it.Rate.text().toFloat() <= %d}.Description", lowerBound, upperBound));
    }
}
