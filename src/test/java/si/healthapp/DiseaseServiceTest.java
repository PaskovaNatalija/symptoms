package si.healthapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import si.healthapp.symptoms.dto.DiseaseOutput;
import si.healthapp.symptoms.dto.SymptomsInput;
import si.healthapp.symptoms.service.DiseaseService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiseaseServiceTest {

    @InjectMocks
    DiseaseService diseaseService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void heartAttackDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setChestPain(true);
        symptomsInput.setBreathingDifficulty(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Heart Attack", result.getDiseaseName());
    }

    @Test
    void measlesDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setFever(true);
        symptomsInput.setRash(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Measles", result.getDiseaseName());
    }

    @Test
    void meningitisDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setFever(true);
        symptomsInput.setHeadache(true);
        symptomsInput.setVomiting(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Meningitis", result.getDiseaseName());
    }

    @Test
    void fluDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setFever(true);
        symptomsInput.setSoreThroat(true);
        symptomsInput.setHeadache(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Flu", result.getDiseaseName());
    }

    @Test
    void viralInfectionDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setFever(true);
        symptomsInput.setBodyAches(true);
        symptomsInput.setFatigue(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Viral Infection", result.getDiseaseName());
    }

    @Test
    void foodPoisoningDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setNausea(true);
        symptomsInput.setVomiting(true);
        symptomsInput.setAbdominalPain(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Food Poisoning", result.getDiseaseName());
    }

    @Test
    void pneumoniaDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setBreathingDifficulty(true);
        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Pneumonia", result.getDiseaseName());

        symptomsInput = new SymptomsInput();
        symptomsInput.setChestPain(true);
        result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Pneumonia", result.getDiseaseName());
    }

    @Test
    void migraineDetected() {
        SymptomsInput symptomsInput = new SymptomsInput();
        symptomsInput.setHeadache(true);
        symptomsInput.setFaintness(true);

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Migraine", result.getDiseaseName());
    }

    @Test
    void unknownWhenNoSymptoms() {
        SymptomsInput symptomsInput = new SymptomsInput();

        DiseaseOutput result = diseaseService.getDiseaseBySymptoms(symptomsInput);
        assertEquals("Unknown Condition", result.getDiseaseName());
    }

}
