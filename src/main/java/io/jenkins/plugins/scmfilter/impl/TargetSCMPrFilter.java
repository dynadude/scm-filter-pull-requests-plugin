package io.jenkins.plugins.scmfilter.impl;

import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilter;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;

public class TargetSCMPrFilter implements SCMPrFilter {
    private SCMHeadMatcher matcher;

    public TargetSCMPrFilter(SCMHeadMatcher matcher) {
        this.matcher = matcher;
    }

    public boolean isExcluded(SCMHead head) {
        if (!(head instanceof ChangeRequestSCMHead)) {
            return false;
        }

        head = ((ChangeRequestSCMHead) head).getTarget();

        return !matcher.matches(head);
    }
}
