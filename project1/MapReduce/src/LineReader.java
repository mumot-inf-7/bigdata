import com.opencsv.CSVParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public final class LineReader {

    public String transform3rdstreet(String street){
//        if(street.length() > 0){
//            return "3rd "+street;
//        }
//        return street;
        street = street.replaceFirst("(\\w\\/\\w*\\s?)?(\\d{1,4}\\s?)", "");
        int space = street.lastIndexOf("  ");
        return street.substring(Math.max(space-1, 0))
                .replaceFirst("(\\d{0,3}-\\d{0,3}\\s?)", "").trim()
                ;
    }

    public LineData parse(String line) throws IOException {
        CSVParser parser = new CSVParser();
        String[] fields = parser.parseLine(line);
        return new LineData(
                Arrays.stream(new String[]{fields[6], fields[7], transform3rdstreet(fields[8])}).filter(s -> s.length() > 0).map(s -> s.toUpperCase(Locale.ROOT)).collect(Collectors.toList()),
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
