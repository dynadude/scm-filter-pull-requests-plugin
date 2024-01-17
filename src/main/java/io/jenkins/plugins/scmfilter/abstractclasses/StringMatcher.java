package io.jenkins.plugins.scmfilter.abstractclasses;

public interface StringMatcher {
    boolean matches(String pattern, String stringToMatch);
}
