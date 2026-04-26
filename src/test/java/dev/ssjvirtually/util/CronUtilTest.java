package dev.ssjvirtually.util;

import com.cronutils.model.CronType;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CronUtilTest {

    @Test
    public void testDescribeUnix() {
        String cron = "*/5 * * * *";
        String description = CronUtil.describe(cron, CronType.UNIX);
        assertNotNull(description);
        assertTrue(description.toLowerCase().contains("every 5 minutes"));
    }

    @Test
    public void testNextExecutions() {
        String cron = "0 0 * * *"; // Every day at midnight
        List<String> next = CronUtil.getNextExecutions(cron, CronType.UNIX, 5);
        assertEquals(5, next.size());
    }

    @Test
    public void testInvalidCron() {
        assertThrows(IllegalArgumentException.class, () -> {
            CronUtil.describe("invalid", CronType.UNIX);
        });
    }

    @Test
    public void testEmptyInput() {
        assertEquals("", CronUtil.describe("", CronType.UNIX));
        assertTrue(CronUtil.getNextExecutions(null, CronType.UNIX, 5).isEmpty());
    }
}
