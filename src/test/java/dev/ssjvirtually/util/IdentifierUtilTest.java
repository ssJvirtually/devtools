package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdentifierUtilTest {

    @Test
    public void testGenerateUuid() {
        String uuid = IdentifierUtil.generateUuid();
        assertEquals(36, uuid.length());
        assertEquals('-', uuid.charAt(8));
    }

    @Test
    public void testGenerateAndDecodeUlid() {
        String ulid = IdentifierUtil.generateUlid();
        assertEquals(26, ulid.length());
        long ts = IdentifierUtil.decodeUlidTimestamp(ulid);
        assertTrue(ts > 0);
    }
}
