package io.jenkins.plugins.scmfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.jenkins.plugins.scmfilter.impl.WildcardStringMatcher;
import org.junit.Test;

public class WildcardStringMatcherTest {
    @Test
    public void testWildcardAtTheEnd() throws Exception {
        String pattern = "dev*";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertTrue(matcher.matches(pattern, "dev"));

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "new-dev"));
    }

    @Test
    public void testWildcardAtTheMiddle() throws Exception {
        String pattern = "ma*er";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "maker"));

        assertFalse(matcher.matches(pattern, "mast"));

        assertFalse(matcher.matches(pattern, "error"));

        assertFalse(matcher.matches(pattern, "computer"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testWildcardAtTheBeginning() throws Exception {
        String pattern = "*ter";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testEmptyPattern() throws Exception {
        String pattern = "";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testmultiplePatterns() throws Exception {
        String pattern = "develop master";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "dev master"));
    }

    @Test
    public void testOnlyMaster() throws Exception {
        String pattern = "master";
        WildcardStringMatcher matcher = new WildcardStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "dev master"));
    }
}
