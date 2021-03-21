package restAssuredXml.stepDefinitions;

import io.restassured.internal.path.xml.NodeImpl;
import org.testng.annotations.Test;
import restAssuredXml.APIFramework.APIUtils;

import java.io.IOException;
import java.util.List;


public class Steps {

    @Test
    public void executeRatesBetween() throws IOException {
        List<NodeImpl> nodeList = APIUtils.getNodesBetween(100, 200);

        System.out.println("[ExecuteRatesBetween] Number of nodes: " + nodeList.size());

//      For visual validation.
        for (NodeImpl rate : nodeList) {
            System.out.println(rate);
        }

    }

    @Test
    public void executeGetCertainNode() throws IOException {
        String node = APIUtils.getNodeRateByDescription("GEL");
        System.out.println("[ExecuteGetCertainNode] " + node);
    }


    @Test
    public void executeDescriptionsViaRateValue() throws IOException {
        List<String> nodeList = APIUtils.getNodeDescriptionsByRatesBetween(10, 20);

        for (String node : nodeList) {
            System.out.println(node);
        }
    }
}
