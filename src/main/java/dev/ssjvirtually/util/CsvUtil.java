package dev.ssjvirtually.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class CsvUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private CsvUtil() {
    }

    public static String jsonToCsv(String json) throws IOException {
        JsonNode rootNode = mapper.readTree(json);
        if (!rootNode.isArray()) {
            throw new IllegalArgumentException("JSON must be an array of objects");
        }

        ArrayNode arrayNode = (ArrayNode) rootNode;
        if (arrayNode.isEmpty()) {
            return "";
        }

        List<String> headers = new ArrayList<>();
        JsonNode firstElement = arrayNode.get(0);
        if (firstElement.isObject()) {
            firstElement.fieldNames().forEachRemaining(headers::add);
        } else {
            throw new IllegalArgumentException("JSON array must contain objects");
        }

        StringWriter writer = new StringWriter();
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader(headers.toArray(new String[0]))
                .build();

        try (CSVPrinter printer = new CSVPrinter(writer, format)) {
            for (JsonNode node : arrayNode) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    JsonNode valueNode = node.get(header);
                    values.add(valueNode == null ? "" : valueNode.asText());
                }
                printer.printRecord(values);
            }
        }

        return writer.toString();
    }

    public static String csvToJson(String csv) throws IOException {
        if (csv == null || csv.trim().isEmpty()) {
            return "[]";
        }

        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (CSVParser parser = new CSVParser(new StringReader(csv), format)) {
            Map<String, Integer> headerMap = parser.getHeaderMap();
            List<String> headers = new ArrayList<>(headerMap.keySet());
            
            ArrayNode arrayNode = mapper.createArrayNode();
            for (CSVRecord record : parser) {
                ObjectNode objectNode = mapper.createObjectNode();
                for (String header : headers) {
                    objectNode.put(header, record.get(header));
                }
                arrayNode.add(objectNode);
            }
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
        }
    }
}
