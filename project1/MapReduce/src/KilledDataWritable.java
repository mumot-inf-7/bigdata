import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.hadoop.io.WritableComparable;

import java.io.*;

public class KilledDataWritable implements WritableComparable<KilledData> {
    KilledData data;

    public KilledDataWritable() {
        set(new KilledData(0,0,0,0,0,0,0,0));
    }

    public KilledDataWritable(KilledData data) {
        set(data);
    }

    public void set(KilledData data) {
        this.data = data;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this.data);
        dataOutput.writeChars(jsonString);
    }

    @SneakyThrows
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        Gson gson = new Gson();
        this.data = gson.fromJson(dataInput.toString() , KilledData.class);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public int compareTo(KilledData o) {
        return this.data.equals(o) ? 0 : -1;
    }
}