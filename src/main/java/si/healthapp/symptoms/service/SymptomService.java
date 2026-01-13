package si.healthapp.symptoms.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;
import si.healthapp.symptoms.dto.DiseaseInput;
import si.healthapp.symptoms.dto.SymptomsInput;
import si.healthapp.symptoms.dto.DiseaseOutput;
import si.healthapp.symptoms.entity.Symptom;
import si.healthapp.symptoms.repository.SymptomRepository;

import java.util.List;

public class SymptomService {

    @Inject
    SymptomRepository repository;
    @Inject
    DiseaseService diseaseService;

    public List<Symptom> getAll() {
        return repository.findAll();
    }

    public Symptom getById(String id) {
        validateId(id);
        Symptom symptom = repository.findById(new ObjectId(id));
        if (symptom == null) {
            throw new NotFoundException();
        }
        return symptom;
    }

    public Symptom create(Symptom symptom) {
        validateSymptom(symptom);
        return repository.save(symptom);
    }

    public Symptom update(String id, Symptom updated) {
        Symptom existing = getById(id);
        existing.name = updated.name;
        existing.description = updated.description;
        return repository.save(existing);
    }

    public void delete(String id) {
        validateId(id);
        if (!repository.deleteById(new ObjectId(id))) {
            throw new NotFoundException();
        }
    }

    public List<Symptom> findByCause(String cause) {
        return repository.findByCause(cause);
    }

    public Symptom findByName(String name) {
        Symptom symptom = repository.findByName(name);
        if (symptom == null) {
            throw new NotFoundException();
        }
        return symptom;
    }

    public DiseaseOutput getDiseaseBySymptoms(SymptomsInput input) {
        return diseaseService.getDiseaseBySymptoms(input);
    }

    void validateId(String id) {
        if (id == null || id.length() != 24) {
            throw new IllegalArgumentException("Invalid id");
        }
    }

    void validateSymptom(Symptom s) {
        if (s.getName() == null) {
            throw new IllegalArgumentException("Name required");
        }
    }
}
