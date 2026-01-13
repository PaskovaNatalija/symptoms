package si.healthapp.symptoms.dto;

import java.util.ArrayList;

public class DiseaseInput {

    private Double temperature;

    private ArrayList<String> symptoms = new ArrayList<>();

    public DiseaseInput() {
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public String toString() {
        return "{" +
                "temperature=" + temperature +
                ", symptoms=" + symptoms +
                '}';
    }
}
