package si.healthapp.symptoms.repository;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.jboss.logging.Logger;
import si.healthapp.symptoms.LogEvent;
import si.healthapp.symptoms.dto.EndpointsCalls;
import si.healthapp.symptoms.dto.SymptomsInput;
import si.healthapp.symptoms.dto.DiseaseOutput;
import si.healthapp.symptoms.entity.Symptom;
import si.healthapp.symptoms.service.EndpointsService;
import si.healthapp.symptoms.service.SymptomsOldService;

import java.text.SimpleDateFormat;
import java.util.Date;


@Path("/symptoms")
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
})
public class SymptomsRepository {

    private static final Logger LOG = Logger.getLogger(SymptomsRepository.class);

    @Inject
    SymptomsOldService symptomsOldService;

    @Inject
    EndpointsService endpointsService;

    @Inject
    JWTParser parser;

    @Inject
    @ConfigProperty(name="secret.key")
    String secret;

    @Inject
    private Event<LogEvent> logEvent;

    @Context
    UriInfo info;

    @GET
    @Operation(summary = "Get all symptoms", description = "Returns a list of all symptoms")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully returned list of symptoms",
                    content = {
                        @Content(
                                mediaType = MediaType.APPLICATION_JSON,
                                schema = @Schema(implementation = Symptom.class)
                        )
                    }),
            @APIResponse(responseCode = "204", description = "No symptoms found"),
            @APIResponse(responseCode = "401", description = "Unauthorized")

    })
    @SecurityRequirement(name = "bearerAuth")
    public Response getAllSymptoms(@HeaderParam("Authorization") String token) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms"));
        return symptomsOldService.getAllSymptoms();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get symptom by id", description = "Returns a symptom by id")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully returned symptom by id",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Symptom.class)
                            )
                    }),
            @APIResponse(responseCode = "400", description = "Invalid id"),
            @APIResponse(responseCode = "404", description = "Not found"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response getSymptomById(@HeaderParam("Authorization") String token, @PathParam("id") String id) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/{id}"));
        return symptomsOldService.getSymptomById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new symptom", description = "Creates new symptom")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully created new symptom",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Symptom.class)
                            )
                    }),
            @APIResponse(responseCode = "400", description = "Bad Request"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response createNewSymptom(@HeaderParam("Authorization") String token, @RequestBody  Symptom symptom) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms"));
        return symptomsOldService.createSymptom(symptom);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update symptom by id", description = "Updates symptom by id")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully updated symptom",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Symptom.class)
                            )
                    }),
            @APIResponse(responseCode = "404", description = "Symptom not found"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response updateSymptom(@HeaderParam("Authorization") String token, @PathParam("id") String id, @RequestBody  Symptom updatedSymptoms) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/{id}"));
        return symptomsOldService.updateSymptom(id, updatedSymptoms);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete symptom by id", description = "Deletes symptom by id")
    @APIResponses( value = {
            @APIResponse(responseCode = "200", description = "Successfully deleted symptom"),
            @APIResponse(responseCode = "404", description = "Symptom not found"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response deleteSymptomById(@HeaderParam("Authorization") String token, @PathParam("id") String id) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/{id}"));
        return symptomsOldService.deleteSymptom(id);
    }

    @GET
    @Path("/cause/{cause}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get symptoms by cause", description = "Returns a list symptoms by cause")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully returned list of symptoms by cause",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Symptom.class)
                            )
                    }),
            @APIResponse(responseCode = "204", description = "No content"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response getSymptomByCause(@HeaderParam("Authorization") String token, @PathParam("cause") String cause) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/cause/{cause}"));
        return symptomsOldService.findByCause(cause);
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get symptoms by name", description = "Returns a list symptoms by name")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully returned list of symptoms by name",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Symptom.class)
                            )
                    }),
            @APIResponse(responseCode = "404", description = "Not found"),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    public Response getSymptomByName(@HeaderParam("Authorization") String token, @PathParam("name") String name) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/name/{name}"));
        return symptomsOldService.findByName(name);
    }

    @POST
    @Path("/disease")
    @Operation(summary = "Get disease by symptoms", description = "Returns a disease by name")
    @APIResponses( value = {
            @APIResponse(responseCode = "200",
                    description = "Successfully returned disease by name",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = DiseaseOutput.class)
                            )
                    }),
            @APIResponse(responseCode = "401", description = "Unauthorized")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @SecurityRequirement(name = "bearerAuth")
    public Response getDiseaseBySymptoms(@HeaderParam("Authorization") String token, @RequestBody SymptomsInput symptomsInput) {
        String actualToken = token;
        try {
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = actualToken.substring(7);
            }
            parser.verify(actualToken, secret);
        } catch (ParseException e) {
            return verifyToken();
        }
        DiseaseOutput diseaseOutput = symptomsOldService.getDiseaseBySymptoms(symptomsInput, actualToken);
        endpointsService.updateEndpointCalls(new EndpointsCalls("/symptoms/disease"));
        return Response.ok(diseaseOutput).build();
    }

    private Response verifyToken() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        String logMessage = String.format("%s ERROR %s Correlation: %s [%s] -  %s",
                dateFormat.format(new Date(System.currentTimeMillis())),
                info.getRequestUri(),
                "11000",
                SymptomsRepository.class.getName(),
                "<* Unauthorized *>");
        LOG.error(logMessage);
        logEvent.fire(new LogEvent(logMessage));
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
