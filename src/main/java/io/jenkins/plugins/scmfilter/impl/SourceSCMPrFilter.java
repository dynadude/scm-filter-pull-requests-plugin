package io.jenkins.plugins.scmfilter.impl;

import io.jenkins.plugins.scmfilter.SCMHeadMatcher;
import io.jenkins.plugins.scmfilter.SCMPrFilter;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead2;

public class SourceSCMPrFilter implements SCMPrFilter {
    private SCMHeadMatcher matcher;

    public SourceSCMPrFilter(SCMHeadMatcher matcher) {
        this.matcher = matcher;
    }

    public boolean isExcluded(SCMHead head) {
        if (!(head instanceof ChangeRequestSCMHead2)) {
            return false;
        }

        head = new SCMHead(((ChangeRequestSCMHead2) head).getOriginName());

        return !matcher.matches(head);
    }
}
