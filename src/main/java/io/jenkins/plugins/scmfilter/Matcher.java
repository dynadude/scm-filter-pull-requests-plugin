package io.jenkins.plugins.scmfilter;

public interface Matcher {
    boolean matches(String stringToMatch);
}
