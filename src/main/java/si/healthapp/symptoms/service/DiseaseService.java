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
import si.healthapp.symptoms.dto.SymptomsInput;

import java.net.URI;

@ApplicationScoped
public class DiseaseService {

    public DiseaseOutput getDiseaseBySymptoms(SymptomsInput symptomsInput) {
        DiseaseOutput output = new DiseaseOutput();
        if (isTrue(symptomsInput.getChestPain()) && isTrue(symptomsInput.getBreathingDifficulty())) {
            output.setDiseaseName("Heart Attack");
            return output;
        }
        if (isTrue(symptomsInput.getFever()) && isTrue(symptomsInput.getRash())) {
            output.setDiseaseName("Measles");
            return output;
        }
        if (isTrue(symptomsInput.getFever()) && isTrue(symptomsInput.getHeadache()) && isTrue(symptomsInput.getVomiting())) {
            output.setDiseaseName("Meningitis");
            return output;
        }
        if (isTrue(symptomsInput.getFever()) && isTrue(symptomsInput.getSoreThroat()) && isTrue(symptomsInput.getHeadache())) {
            output.setDiseaseName("Flu");
            return output;
        }
        if (isTrue(symptomsInput.getFever()) && isTrue(symptomsInput.getBodyAches()) && isTrue(symptomsInput.getFatigue())) {
            output.setDiseaseName("Viral Infection");
            return output;
        }
        if (isTrue(symptomsInput.getNausea()) && isTrue(symptomsInput.getVomiting()) && isTrue(symptomsInput.getAbdominalPain())) {
            output.setDiseaseName("Food Poisoning");
            return output;
        }
        if (isTrue(symptomsInput.getBreathingDifficulty()) || isTrue(symptomsInput.getChestPain())) {
            output.setDiseaseName("Pneumonia");
            return output;
        }
        if (isTrue(symptomsInput.getHeadache()) && isTrue(symptomsInput.getFaintness())) {
            output.setDiseaseName("Migraine");
            return output;
        }
        output.setDiseaseName("Unknown Condition");
        return output;

    }

    private boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }
}
