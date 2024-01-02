package io.jenkins.plugins.scmfilter.impl;

import io.jenkins.plugins.scmfilter.StringMatcher;
import java.util.regex.Pattern;

public class RegexStringMatcher implements StringMatcher {

    public RegexStringMatcher() {}

    public boolean matches(String pattern, String testedString) {
        return Pattern.matches(pattern, testedString);
    }
}
