package si.healthapp.symptoms.repository;

import org.bson.types.ObjectId;
import si.healthapp.symptoms.entity.Symptom;

import java.util.List;

public interface SymptomRepository {
    List<Symptom> findAll();
    Symptom findById(ObjectId id);
    Symptom findByName(String name);
    List<Symptom> findByCause(String cause);
    Symptom save(Symptom symptom);
    boolean deleteById(ObjectId id);
}
