package io.jenkins.plugins.scmfilter;

import jenkins.scm.api.SCMHead;

public abstract class SCMHeadMatcher {
    protected StringMatcher matcher;

    public SCMHeadMatcher(StringMatcher matcher) {
        this.matcher = matcher;
    }

    public abstract boolean doesMatch(SCMHead head);
}
