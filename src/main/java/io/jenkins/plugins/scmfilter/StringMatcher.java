package io.jenkins.plugins.scmfilter;

public interface StringMatcher {
    boolean matches(String pattern, String stringToMatch);
}
