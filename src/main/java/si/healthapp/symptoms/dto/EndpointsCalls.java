package si.healthapp.symptoms.dto;

public class EndpointsCalls {

    private String endpoint;

    public EndpointsCalls() {
    }

    public EndpointsCalls(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
