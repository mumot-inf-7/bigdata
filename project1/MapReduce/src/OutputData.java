import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Value
public class OutputData implements Serializable {
    String code;
    String street;
    Integer numberOfPedestriansInjured;
    Integer numberOfPedestriansKilled;
    Integer numberOfCyclistInjured;
    Integer numberOfCyclistKilled;
    Integer numberOfMotoristInjured;
    Integer numberOfMotoristsKilled;
    Integer allInjured;
    Integer allKilled;

    public static OutputData fromKilledData(String street, String code, KilledData data){
        return new OutputData(
                code,
                street,
                data.getNumberOfPedestriansInjured(),
                data.getNumberOfPedestriansKilled(),
                data.getNumberOfCyclistInjured(),
                data.getNumberOfCyclistKilled(),
                data.getNumberOfMotoristInjured(),
                data.getNumberOfMotoristsKilled(),
                data.getAllInjured(),
                data.getAllKilled()
        );
    }
    public String toCsvString(){
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, '\t', '\0', ICSVWriter.NO_ESCAPE_CHARACTER, ICSVWriter.DEFAULT_LINE_END);

        String[] arr = new String[]{
                code,
                street,
                numberOfPedestriansInjured.toString(),
        numberOfPedestriansKilled.toString(),
         numberOfCyclistInjured.toString(),
         numberOfCyclistKilled.toString(),
         numberOfMotoristInjured.toString(),
         numberOfMotoristsKilled.toString(),
         allInjured.toString(),
         allKilled.toString(),
        };
        csvWriter.writeNext(arr);

        return stringWriter.toString();
    }
}


