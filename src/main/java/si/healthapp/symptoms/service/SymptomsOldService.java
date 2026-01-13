package si.healthapp.symptoms.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;
import si.healthapp.symptoms.LogEvent;
import si.healthapp.symptoms.dto.DiseaseInput;
import si.healthapp.symptoms.dto.SymptomsInput;
import si.healthapp.symptoms.dto.DiseaseOutput;
import si.healthapp.symptoms.entity.Symptom;
import si.healthapp.symptoms.repository.SymptomsRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SymptomsOldService {

    private static final Logger LOG = Logger.getLogger(SymptomsRepository.class);

    @Inject
    DiseaseRestService diseaseRestService;

    @Inject
    private Event<LogEvent> logEvent;

    public Response getAllSymptoms() {
        List<Symptom> symptoms = Symptom.listAll();
        if(symptoms.isEmpty()) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
//            String logMessage = String.format("%s WARN %s Correlation: %s [%s] -  %s",
//                    dateFormat.format(new Date(System.currentTimeMillis())),
//                    info.getRequestUri(),
//                    "11000",
//                    SymptomsRepository.class.getName(),
//                    "<* No symptoms found *>");
//            LOG.warn(logMessage);
//            logEvent.fire(new LogEvent(logMessage));
            return Response.noContent().build();
        }
        return Response.ok(symptoms).build();
    }

    public Response getSymptomById(String id) {
        Response validationResponse = validateId(id);
        if(validationResponse == null) {
            ObjectId objectId = new ObjectId(id);
            Symptom symptom = Symptom.findById(objectId);
            if(symptom != null) {
                return Response.ok(symptom).build();
            }
//            messageSympthomNotFound(info, id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return validationResponse;
    }

    private void messageSympthomNotFound(UriInfo info, String id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        String logMessage = String.format("%s WARN %s Correlation: %s [%s] -  %s",
                dateFormat.format(new Date(System.currentTimeMillis())),
                info.getRequestUri(),
                "11000",
                SymptomsRepository.class.getName(),
                "<* No symptom found with id " + id + " *>");
        LOG.warn(logMessage);
        logEvent.fire(new LogEvent(logMessage));
    }

    public Response createSymptom(Symptom symptom) {
        Response validationResponse = validateSymptomInput(symptom);
        if(validationResponse == null) {
            Symptom.persist(symptom);
            return Response.ok(symptom).build();
        }
        return validationResponse;
    }

    public Response updateSymptom(String id, Symptom updatedSymptom) {
        ObjectId objectId = new ObjectId(id);
        Symptom symptom = Symptom.findById(objectId);
        if (symptom != null) {
            symptom.name = updatedSymptom.name;
            symptom.description = updatedSymptom.description;
            return Response.ok(symptom).build();
        }
//        messageSympthomNotFound(info, "<* No symptom found with id " + id + "*>");
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    public Response deleteSymptom(String id) {
        ObjectId objectId = new ObjectId(id);
        if(Symptom.findById(objectId) == null) {
//            messageSympthomNotFound(info, "<* No symptom found with id " + id + "*>");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(Symptom.deleteById(objectId)).build();
    }

    public Response findByCause(String cause) {
        List<Symptom> symptoms = Symptom.find("causes", cause).list();
        if(symptoms.isEmpty()) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
//            String logMessage = String.format("%s WARN %s Correlation: %s [%s] -  %s",
//                    dateFormat.format(new Date(System.currentTimeMillis())),
//                    info.getRequestUri(),
//                    "11000",
//                    SymptomsRepository.class.getName(),
//                    "<* No symptom found with cause " + cause + "*>");
//            LOG.warn(logMessage);
//            logEvent.fire(new LogEvent(logMessage));
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("There are not symptoms with cause: " + cause)
                    .build();
        }
        return Response.ok(symptoms).build();
    }

    public Response findByName(String name) {
        Symptom symptom = Symptom.find("name", name).firstResult();
        if(symptom == null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
//            String logMessage = String.format("%s WARN %s Correlation: %s [%s] -  %s",
//                    dateFormat.format(new Date(System.currentTimeMillis())),
//                    info.getRequestUri(),
//                    "11000",
//                    SymptomsRepository.class.getName(),
//                    "<* No symptom found with name " + name + "*>");
//            LOG.warn(logMessage);
//            logEvent.fire(new LogEvent(logMessage));
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("There are not symptom with name: " + name)
                    .build();
        }
        return Response.ok(symptom).build();
    }

    public DiseaseOutput getDiseaseBySymptoms(SymptomsInput symptomsInput, String jwt) {
        DiseaseInput diseaseInput = createDiseaseInput(symptomsInput);
        return diseaseRestService.getDiseaseBySymptoms(diseaseInput, jwt);
    }

    private Response validateId(String id) {
        if(id.length() < 24) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Id should have 24 characters")
                    .build();
        }
        return null;
    }

    private Response validateSymptomInput(Symptom symptomsInput) {
        if(symptomsInput.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Name must me entered!")
                    .build();
        }
        return null;
    }

    private DiseaseInput createDiseaseInput(SymptomsInput symptomsInput) {
        DiseaseInput diseaseInput = new DiseaseInput();
        diseaseInput.setTemperature(symptomsInput.getTemperature());
        if(symptomsInput.getAbdominalPain()) {
            diseaseInput.getSymptoms().add("abdominalPain");
        }
        if(symptomsInput.getBackPain()) {
            diseaseInput.getSymptoms().add("backPain");
        }
        if(symptomsInput.getBodyAches()) {
            diseaseInput.getSymptoms().add("bodyAches");
        }
        if(symptomsInput.getFaintness()) {
            diseaseInput.getSymptoms().add("faintness");
        }
        if(symptomsInput.getFatigue()) {
            diseaseInput.getSymptoms().add("fatigue");
        }
        if(symptomsInput.getBreathingDifficulty()) {
            diseaseInput.getSymptoms().add("breathingDifficulty");
        }
        if(symptomsInput.getChestPain()) {
            diseaseInput.getSymptoms().add("chestPain");
        }
        if(symptomsInput.getHeadache()) {
            diseaseInput.getSymptoms().add("headache");
        }
        if(symptomsInput.getFever()) {
            diseaseInput.getSymptoms().add("fever");
        }
        if(symptomsInput.getNausea()) {
            diseaseInput.getSymptoms().add("nausea");
        }
        if(symptomsInput.getSoreThroat()) {
            diseaseInput.getSymptoms().add("soreThroat");
        }
        if(symptomsInput.getVomiting()) {
            diseaseInput.getSymptoms().add("vomiting");
        }
        return diseaseInput;
    }
}

