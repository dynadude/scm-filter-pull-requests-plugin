package io.jenkins.plugins.scmfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WildcardMatcherTest {
    @Test
    public void testWildcardAtTheEnd() throws Exception {
        String pattern = "dev*";
        WildcardMatcher matcher = new WildcardMatcher();

        assertTrue(matcher.matches(pattern, "dev"));

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "new-dev"));
    }

    @Test
    public void testWildcardAtTheMiddle() throws Exception {
        String pattern = "ma*er";
        WildcardMatcher matcher = new WildcardMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "maker"));

        assertFalse(matcher.matches(pattern, "mast"));

        assertFalse(matcher.matches(pattern, "error"));

        assertFalse(matcher.matches(pattern, "computer"));
    }

    @Test
    public void testWildcardAtTheBeginning() throws Exception {
        String pattern = "*ter";
        WildcardMatcher matcher = new WildcardMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testEmptyPattern() throws Exception {
        String pattern = "";
        WildcardMatcher matcher = new WildcardMatcher();

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testmultiplePatterns() throws Exception {
        String pattern = "develop master";
        WildcardMatcher matcher = new WildcardMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "dev master"));
    }
}
