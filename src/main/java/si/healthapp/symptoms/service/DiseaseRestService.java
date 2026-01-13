package si.healthapp.symptoms.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import si.healthapp.symptoms.dto.DiseaseInput;
import si.healthapp.symptoms.dto.DiseaseOutput;

import java.net.URI;

@ApplicationScoped
public class DiseaseRestService {

    @Inject
    @ConfigProperty(name="diseases.url")
    String diseasesUrl;

    public DiseaseOutput getDiseaseBySymptoms(DiseaseInput diseaseInput, String jwt) {

        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(UriBuilder.fromUri(URI.create(diseasesUrl)).build());

        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);
        builder.header("Authorization", jwt);

        Response response = builder.post(Entity.json(diseaseInput));

        DiseaseOutput diseaseOutput = null;

        if(response.getStatus() == Response.Status.OK.getStatusCode()) {
            diseaseOutput = response.readEntity(DiseaseOutput.class);
        }

        client.close();
        return diseaseOutput;
    }
}
