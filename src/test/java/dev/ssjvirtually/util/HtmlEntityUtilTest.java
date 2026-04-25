package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlEntityUtilTest {

    @Test
    public void testEncode() {
        assertEquals("&lt;div&gt;Hello &amp; World&lt;/div&gt;", HtmlEntityUtil.encode("<div>Hello & World</div>"));
    }

    @Test
    public void testDecode() {
        assertEquals("<div>Hello & World</div>", HtmlEntityUtil.decode("&lt;div&gt;Hello &amp; World&lt;/div&gt;"));
    }

    @Test
    public void testNull() {
        assertNull(HtmlEntityUtil.encode(null));
        assertNull(HtmlEntityUtil.decode(null));
    }
}
