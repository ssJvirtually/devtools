package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class CsvUtilTest {

    @Test
    public void testJsonToCsv() throws IOException {
        String json = "[{\"id\":1,\"name\":\"John\"},{\"id\":2,\"name\":\"Jane\"}]";
        String csv = CsvUtil.jsonToCsv(json);
        // Default CSV format includes headers
        assertTrue(csv.contains("id,name"));
        assertTrue(csv.contains("1,John"));
        assertTrue(csv.contains("2,Jane"));
    }

    @Test
    public void testCsvToJson() throws IOException {
        String csv = "id,name\n1,John\n2,Jane";
        String json = CsvUtil.csvToJson(csv);
        assertTrue(json.contains("\"id\" : \"1\""));
        assertTrue(json.contains("\"name\" : \"John\""));
    }

    @Test
    public void testInvalidJsonToCsv() {
        String json = "{\"id\":1}"; // Not an array
        assertThrows(IllegalArgumentException.class, () -> CsvUtil.jsonToCsv(json));
    }
}
