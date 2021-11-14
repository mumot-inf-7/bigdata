import com.google.gson.Gson;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

@Builder
@Value
public class KilledData implements Serializable {
    int numberOfPedestriansInjured;
    int numberOfPedestriansKilled;
    int numberOfCyclistInjured;
    int numberOfCyclistKilled;
    int numberOfMotoristInjured;
    int numberOfMotoristsKilled;
    int allInjured;
    int allKilled;

    public static KilledData getData(){
        return new KilledData(0,0,0,0,0,0,0,0);
    }

    public KilledData add(KilledData o) {
        KilledData res = o.clone();

        Arrays.stream(KilledData.class.getDeclaredFields()).collect(Collectors.toList()).forEach(field -> {
            try {
                field.setAccessible(true);
                field.setInt(res, field.getInt(this) + field.getInt(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return res;
    }
    @Override
    public KilledData clone(){
        Gson gson = new Gson();
        return  gson.fromJson(gson.toJson(this), KilledData.class);
    }
}

