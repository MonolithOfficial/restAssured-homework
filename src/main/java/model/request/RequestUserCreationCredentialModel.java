package model.request;

public class RequestUserCreationCredentialModel {
    private final String name;
    private final String job;

    public RequestUserCreationCredentialModel(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
