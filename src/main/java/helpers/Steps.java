package helpers;

import constants.Routes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.request.RequestRegistrationCredentialModel;
import model.request.RequestUserCreationCredentialModel;
import model.response.GeneralResponse;
import model.response.RegErrorModel;
import model.response.RegSuccessModel;
import model.response.UserCreatedResponseModel;
import utils.ConfigWorker;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Steps {
    protected static String BASE_URL = null;

    static {
        try {
            BASE_URL = ConfigWorker.getInstance().getString("baseURL");
            RestAssured.baseURI = BASE_URL;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GeneralResponse registrationPOST(RequestRegistrationCredentialModel rqReg){
        Response response = given()
                .contentType(ContentType.JSON)
                .body(rqReg)
                .post(Routes.registerPOST);

        if (response.statusCode() == 200){
            return response.body().as(RegSuccessModel.class);
        }
        else if (response.statusCode() == 400){
            return response.body().as(RegErrorModel.class);
        }
        else {
            return new GeneralResponse();
        }
    }

    public static UserCreatedResponseModel createUserPOST(RequestUserCreationCredentialModel rqCreation){
        Response rs = given()
                .contentType(ContentType.JSON)
                .body(rqCreation)
                .post(Routes.createUserPOST);

        return rs.body().as(UserCreatedResponseModel.class);
    }
}
