package io.jenkins.plugins.scmfilter.prfilterimplementations;

import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilter;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;

public abstract class SCMPrFilterTemplate implements SCMPrFilter {
    private SCMHeadMatcher matcher;

    public SCMPrFilterTemplate(SCMHeadMatcher matcher) {
        this.matcher = matcher;
    }

    public boolean isExcluded(SCMHead head) {
        if (!(head instanceof ChangeRequestSCMHead)) {
            return false;
        }

        head = getHeadToMatch(head);

        return !matcher.matches(head);
    }

    protected abstract SCMHead getHeadToMatch(SCMHead head);
}
