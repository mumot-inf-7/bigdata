import com.opencsv.CSVParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class LineReader {

    public LineData parse(String line) throws IOException {
        CSVParser parser = new CSVParser();
        String[] fields = parser.parseLine(line);
        return new LineData(
                Arrays.stream(new String[]{fields[6], fields[7], fields[8]}).filter(s -> {return s.length() > 0;}).collect(Collectors.toList()),
                fields[2],
                Integer.parseInt(fields[0].split("/")[2]),
                Integer.parseInt(fields[11]),
                Integer.parseInt(fields[12]),
                Integer.parseInt(fields[13]),
                Integer.parseInt(fields[14]),
                Integer.parseInt(fields[15]),
                Integer.parseInt(fields[16]),
                Integer.parseInt(fields[9]),
                Integer.parseInt(fields[10])
                );
    }
}
