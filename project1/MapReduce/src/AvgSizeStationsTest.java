import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AvgSizeStationsTest {

    @Test
    void main() {
        assertTrue(true);
    }

    @Test
    void run() throws IOException, ClassNotFoundException {
        LineData line = new LineReader().parse("08/03/2013,18:00,11223,40.5914514,-73.9765134,POINT (-73.9765134 40.5914514),8 STREET,\"WEST 6, street\",1165      BROADWAY,10,11,2,4,3,6,5,1,Driver Inattention/Distraction,Unspecified,,,,115333,SPORT UTILITY / STATION WAGON,PASSENGER VEHICLE,,,");
        assertEquals(new LineData(
                Arrays.stream(new String[]{
                        "8 STREET", "WEST 6, STREET", "BROADWAY"}).collect(Collectors.toList()),
                "11223",
                2013,
                2,
                4,
                3,
                6,
                5,
                1,
                10,
                11
        ), line);

//        System.out.print(line.toString());
        Gson gson = new Gson();
        String jsonString = gson.toJson(line);

        System.out.println(gson.fromJson(jsonString, LineData.class));
        System.out.println(gson.fromJson(jsonString, LineData.class).getStreets().stream().map(s -> '|' + s).collect(Collectors.toList()));
    }

    @Test
    void add_KilledData_as_vector() throws CloneNotSupportedException {
        KilledData data1 = new KilledData(
                5, 1, 1, 1, 1, 1, 1, 1
        );

        KilledData data2 = new KilledData(
                1, 1, 1, 1, 1, 1, 1, 1
        );

        assertEquals(new KilledData(6, 2, 2, 2, 2, 2, 2, 2), data1.add(data2));
    }

    @Test
    void testSplitStreetAndCode() {
        assertEquals("street-name", "1239234//:street-name".split("//:")[1]);
    }

    @Test
    void OutputDataToCsv() {
        OutputData outputData = OutputData.fromKilledData("Street,\ttab-street", "123123", new KilledData(1, 2, 3, 4, 5, 6, 7, 8));
        System.out.println(
                outputData.toCsvString()
        );
    }

    @Test
    void Transform3rdLine() {
        assertEquals("BROADWAY", new LineReader().transform3rdstreet("1165      BROADWAY"));
        assertEquals("BEACH 63 STREET", new LineReader().transform3rdstreet("429 BEACH 63 STREET"));
        assertEquals("137 AVENUE", new LineReader().transform3rdstreet("230-16    137 AVENUE"));
        assertEquals("ROCKAWAY BOULEVARD", new LineReader().transform3rdstreet("P/L 238-01 ROCKAWAY BOULEVARD"));
        assertEquals("20 AVENUE", new LineReader().transform3rdstreet("135-05 20 AVENUE"));
        assertEquals("20 AVENUE", new LineReader().transform3rdstreet("135-05 20 AVENUE"));
        assertEquals("COLLEGE PT BLVD", new LineReader().transform3rdstreet("F/O 9-17 COLLEGE PT BLVD"));
        assertEquals("144 STREET", new LineReader().transform3rdstreet("F/O 86-25 144 STREET"));
        assertEquals("LIVERPOOL STREET", new LineReader().transform3rdstreet("95-30     LIVERPOOL STREET"));



    }
}