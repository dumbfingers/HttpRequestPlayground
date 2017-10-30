import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestMain {

    @Test
    public void testIsUrlLegal() {
        assertFalse(Util.isURLLegal("abc"));
        assertFalse(Util.isURLLegal(""));
        assertFalse(Util.isURLLegal("http123://"));
        assertFalse(Util.isURLLegal("http:/ /"));
        assertTrue(Util.isURLLegal("http://www"));
        assertTrue(Util.isURLLegal("http://www.bbc.co.uk"));
        assertTrue(Util.isURLLegal("https://www.google.com"));
    }

    @Test
    public void testAddTrailingSlash() {
        String urlWithTrail = "http://www.abc.com/";
        String urlWithoutTrail = "http://www.abc.com";

        assertEquals(Util.addTrailingSlash(urlWithoutTrail), Util.addTrailingSlash(urlWithTrail));
    }
}
