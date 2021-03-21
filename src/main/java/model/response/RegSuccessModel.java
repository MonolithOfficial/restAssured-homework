package model.response;

import java.util.HashMap;
import java.util.Map;

public class RegSuccessModel extends GeneralResponse {

    private Integer id;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}