package si.healthapp.symptoms.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.ArrayList;
import java.util.List;

@MongoEntity(database = "healthApp", collection = "symptoms")
public class Symptom extends PanacheMongoEntity {

    public String name;

    public String description;

    public List<String> causes = new ArrayList<>();

    public Symptom() {
    }

    public Symptom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCauses() {
        return causes;
    }
}

