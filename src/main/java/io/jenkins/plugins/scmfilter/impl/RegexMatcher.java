package io.jenkins.plugins.scmfilter.impl;

import io.jenkins.plugins.scmfilter.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher implements Matcher {

    public RegexMatcher() {}

    public boolean matches(String pattern, String testedString) {
        return Pattern.matches(pattern, testedString);
    }
}
