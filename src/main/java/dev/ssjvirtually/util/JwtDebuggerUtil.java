package dev.ssjvirtually.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class JwtDebuggerUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

    private JwtDebuggerUtil() {
    }

    public static JwtParts decode(String token) throws Exception {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }

        String[] parts = token.trim().split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("JWT must contain exactly 3 parts");
        }

        String headerRaw = TextCodecUtil.base64UrlDecode(parts[0]);
        String payloadRaw = TextCodecUtil.base64UrlDecode(parts[1]);

        JsonNode headerNode = MAPPER.readTree(headerRaw);
        JsonNode payloadNode = MAPPER.readTree(payloadRaw);

        String headerPretty = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(headerNode);
        String payloadPretty = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(payloadNode);
        String signature = parts[2];
        String claimsSummary = buildClaimsSummary(payloadNode);

        return new JwtParts(headerPretty, payloadPretty, signature, claimsSummary);
    }

    private static String buildClaimsSummary(JsonNode payloadNode) {
        StringBuilder sb = new StringBuilder();
        JsonNode exp = payloadNode.get("exp");
        JsonNode nbf = payloadNode.get("nbf");
        JsonNode iat = payloadNode.get("iat");

        if (exp != null && exp.isNumber()) {
            sb.append("exp: ").append(asReadableEpoch(exp.asLong())).append('\n');
        }
        if (nbf != null && nbf.isNumber()) {
            sb.append("nbf: ").append(asReadableEpoch(nbf.asLong())).append('\n');
        }
        if (iat != null && iat.isNumber()) {
            sb.append("iat: ").append(asReadableEpoch(iat.asLong())).append('\n');
        }

        if (sb.length() == 0) {
            return "No standard time claims (exp/nbf/iat) found.";
        }

        return sb.toString().trim();
    }

    private static String asReadableEpoch(long epochSeconds) {
        return epochSeconds + " (" + TIME_FORMATTER.format(Instant.ofEpochSecond(epochSeconds)) + ")";
    }

    public record JwtParts(String headerJson, String payloadJson, String signature, String claimsSummary) {
    }
}
