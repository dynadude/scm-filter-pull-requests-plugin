package io.jenkins.plugins.scmfilter;

import jenkins.scm.api.SCMHead;

public abstract class SCMHeadMatcher {
    protected Matcher matcher;

    public SCMHeadMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public abstract boolean doesMatch(SCMHead head);
}
