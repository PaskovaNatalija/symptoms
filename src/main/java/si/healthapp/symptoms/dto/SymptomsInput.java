package si.healthapp.symptoms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SymptomsInput {

   private Double temperature;
   private Boolean bodyAches;
   private Boolean nausea;
   private Boolean vomiting;
   private Boolean soreThroat;
   private Boolean chestPain;
   private Boolean rash;
   private Boolean breathingDifficulty;
   private Boolean backPain;
   private Boolean headache;
   private Boolean abdominalPain;
   private Boolean faintness;
   private Boolean fever;
   private Boolean fatigue;
}
