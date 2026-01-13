package si.healthapp;

import si.healthapp.symptoms.entity.Symptom;

public class FixtureSymptom {

    public static Symptom createSymptom(String name) {
        Symptom symptom = new Symptom();
        symptom.setName(name);
        symptom.setDescription("Description");
        return symptom;
    }
}
