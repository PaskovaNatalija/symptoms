package si.healthapp;

import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.healthapp.symptoms.dto.DiseaseOutput;
import si.healthapp.symptoms.dto.SymptomsInput;
import si.healthapp.symptoms.entity.Symptom;
import si.healthapp.symptoms.repository.SymptomRepository;
import si.healthapp.symptoms.service.DiseaseService;
import si.healthapp.symptoms.service.SymptomService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SymptomServiceTest {

    @InjectMocks
    SymptomService symptomService;
    @Mock
    SymptomRepository symptomRepository;
    @Mock
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
    void getById_invalidId() {
        assertThrows(IllegalArgumentException.class, () -> symptomService.getById("123"));
    }

    @Test
    void getAll() {
        when(symptomRepository.findAll()).thenReturn(List.of(new Symptom(), new Symptom()));

        List<Symptom> result = symptomService.getAll();
        assertEquals(2, result.size());
    }
    @Test
    void getById_notFound() {
        when(symptomRepository.findById(any())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> symptomService.getById("507f1f77bcf86cd799439011"));
    }

    @Test
    void create_success() {
        Symptom s = new Symptom();
        s.setName("Test");

        when(symptomRepository.save(s)).thenReturn(s);
        Symptom result = symptomService.create(s);
        assertEquals("Test", result.getName());
    }

    @Test
    void getDiseaseBySymptoms() {
        SymptomsInput input = new SymptomsInput();
        input.setFever(true);

        when(diseaseService.getDiseaseBySymptoms(any())).thenReturn(new DiseaseOutput());
        DiseaseOutput output = symptomService.getDiseaseBySymptoms(input);
        assertNotNull(output);
    }
}
