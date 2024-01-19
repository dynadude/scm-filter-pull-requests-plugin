package io.jenkins.plugins.scmfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import io.jenkins.plugins.scmfilter.abstractclasses.StringMatcher;
import io.jenkins.plugins.scmfilter.regexstringmatcher.RegexStringMatcher;
import java.util.regex.PatternSyntaxException;
import org.junit.Test;

public class RegexStringMatcherTest {
    @Test
    public void testRegexAtTheEnd() throws Exception {
        String pattern = "dev.*";
        StringMatcher matcher = new RegexStringMatcher();

        assertTrue(matcher.matches(pattern, "dev"));

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "new-dev"));
    }

    @Test
    public void testRegexAtTheMiddle() throws Exception {
        String pattern = "ma.*er";
        StringMatcher matcher = new RegexStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "maker"));

        assertFalse(matcher.matches(pattern, "mast"));

        assertFalse(matcher.matches(pattern, "error"));

        assertFalse(matcher.matches(pattern, "computer"));
    }

    @Test
    public void testRegexAtTheBeginning() throws Exception {
        String pattern = ".*ter";
        StringMatcher matcher = new RegexStringMatcher();

        assertTrue(matcher.matches(pattern, "master"));

        assertTrue(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testEmptyPattern() throws Exception {
        String pattern = "";
        StringMatcher matcher = new RegexStringMatcher();

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "develop"));
    }

    @Test
    public void testmultiplePatterns() throws Exception {
        String pattern = "develop master";
        StringMatcher matcher = new RegexStringMatcher();

        assertTrue(matcher.matches(pattern, "develop master"));

        assertFalse(matcher.matches(pattern, "master"));

        assertFalse(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "toaster"));

        assertFalse(matcher.matches(pattern, "terminal"));
    }

    @Test
    public void testAccidentalWildcard() throws Exception {
        String pattern = "dev*";
        StringMatcher matcher = new RegexStringMatcher();

        assertFalse(matcher.matches(pattern, "develop"));

        assertTrue(matcher.matches(pattern, "de"));

        assertTrue(matcher.matches(pattern, "dev"));

        assertTrue(matcher.matches(pattern, "devvv"));
    }

    @Test
    public void testContains() throws Exception {
        String pattern = "dev.*";
        StringMatcher matcher = new RegexStringMatcher();

        assertTrue(matcher.matches(pattern, "develop"));

        assertFalse(matcher.matches(pattern, "de"));

        assertTrue(matcher.matches(pattern, "dev"));

        assertTrue(matcher.matches(pattern, "devvv"));
    }

    @Test
    public void testOnlyWildcard() throws Exception {
        String pattern = "*";
        StringMatcher matcher = new RegexStringMatcher();

        assertThrows(PatternSyntaxException.class, () -> {
            matcher.matches(pattern, "develop");
        });
    }
}
