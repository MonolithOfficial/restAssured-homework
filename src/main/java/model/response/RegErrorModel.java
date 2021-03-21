package model.response;

import java.util.HashMap;
import java.util.Map;

public class RegErrorModel extends GeneralResponse {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}