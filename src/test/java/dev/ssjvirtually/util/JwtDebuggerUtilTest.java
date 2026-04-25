package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtDebuggerUtilTest {

    @Test
    public void testDecodeJwt() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
                + "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ."
                + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        JwtDebuggerUtil.JwtParts parts = JwtDebuggerUtil.decode(token);

        assertTrue(parts.headerJson().contains("\"alg\" : \"HS256\""));
        assertTrue(parts.payloadJson().contains("\"name\" : \"John Doe\""));
        assertTrue(parts.claimsSummary().contains("iat"));
    }
}
