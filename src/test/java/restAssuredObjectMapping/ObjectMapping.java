package restAssuredObjectMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import helpers.Steps;
import model.request.RequestUserCreationCredentialModel;
import model.response.RegErrorModel;
import model.response.RegSuccessModel;
import model.request.RequestRegistrationCredentialModel;
import model.response.UserCreatedResponseModel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class    ObjectMapping {
    Gson gson;

    @BeforeClass
    public void config(){
        gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Test
    public void executeSuccessfulRegistration() {
        RequestRegistrationCredentialModel rqReg = new RequestRegistrationCredentialModel("eve.holt@reqres.in", "pistol");
        RegSuccessModel rSuccess = (RegSuccessModel) Steps.registrationPOST(rqReg);
        Assert.assertEquals(rSuccess.getId(), 4);
        Assert.assertEquals(rSuccess.getToken(), "QpwL5tke4Pnpja7X4");

        System.out.println(gson.toJson(rSuccess));
    }

    @Test
    public void executeUnsuccessfulRegistration(){
        RequestRegistrationCredentialModel rqReg = new RequestRegistrationCredentialModel("sydney@fife", "");
        RegErrorModel rFail = (RegErrorModel) Steps.registrationPOST(rqReg);
        Assert.assertEquals(rFail.getError(), "Missing password");

        System.out.println(gson.toJson(rFail));
    }

//  Testing POST method, serializing a programming object into JSON format.
    @Test
    public void executeUsersTest(){
        RequestUserCreationCredentialModel rqCreation = new RequestUserCreationCredentialModel("morpheus", "leader");
        UserCreatedResponseModel userCreated = Steps.createUserPOST(rqCreation);
        Assert.assertNotNull(userCreated.getId());
        Assert.assertNotNull(userCreated.getCreatedAt());

        System.out.println(gson.toJson(userCreated));
    }
}
