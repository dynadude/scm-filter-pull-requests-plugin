package io.jenkins.plugins.scmfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WildcardMatcherTest {
    @Test
    public void testWildcardAtTheEnd() throws Exception {
        WildcardMatcher matcher = new WildcardMatcher("dev*");

        assertTrue(matcher.matches("dev"));

        assertTrue(matcher.matches("develop"));

        assertFalse(matcher.matches("master"));

        assertFalse(matcher.matches("new-dev"));
    }

    @Test
    public void testWildcardAtTheMiddle() throws Exception {
        WildcardMatcher matcher = new WildcardMatcher("ma*er");

        assertTrue(matcher.matches("master"));

        assertTrue(matcher.matches("maker"));

        assertFalse(matcher.matches("mast"));

        assertFalse(matcher.matches("error"));

        assertFalse(matcher.matches("computer"));
    }

    @Test
    public void testWildcardAtTheBeginning() throws Exception {
        WildcardMatcher matcher = new WildcardMatcher("*ter");

        assertTrue(matcher.matches("master"));

        assertTrue(matcher.matches("toaster"));

        assertFalse(matcher.matches("terminal"));

        assertFalse(matcher.matches("develop"));
    }
}
