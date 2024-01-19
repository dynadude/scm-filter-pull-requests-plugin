package io.jenkins.plugins.scmfilter.regexstringmatcher;

import io.jenkins.plugins.scmfilter.abstractclasses.StringMatcher;
import java.util.regex.Pattern;

public class RegexStringMatcher implements StringMatcher {

    public RegexStringMatcher() {}

    public boolean matches(String pattern, String testedString) {
        return Pattern.matches(pattern, testedString);
    }
}
