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
}
