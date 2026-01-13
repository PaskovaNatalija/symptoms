package si.healthapp.symptoms.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.UriBuilder;
import si.healthapp.symptoms.dto.EndpointsCalls;

import java.net.URI;

@ApplicationScoped
public class EndpointsService {

    public void updateEndpointCalls(EndpointsCalls endpointsCallsInput) {

        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(UriBuilder.fromUri(URI.create("https://endpoints-service.onrender.com/updateEndpoint")).build());

        target.request().post(Entity.json(endpointsCallsInput));

        client.close();
    }
}
