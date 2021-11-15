import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Builder
@Value
public class LineData implements Serializable {
    List<String> streets;
    String code;
    Integer year;
    Integer numberOfPedestriansInjured;
    Integer numberOfPedestriansKilled;
    Integer numberOfCyclistInjured;
    Integer numberOfCyclistKilled;
    Integer numberOfMotoristInjured;
    Integer numberOfMotoristsKilled;
    Integer allInjured;
    Integer allKilled;

    public KilledData getKilledData(){
        return new KilledData(
                this.numberOfPedestriansInjured,
                this.numberOfPedestriansKilled,
                this.numberOfCyclistInjured,
                this.numberOfCyclistKilled,
                this.numberOfMotoristInjured,
                this.numberOfMotoristsKilled,
                this.allInjured,
                this.allKilled
        );
    }
}


