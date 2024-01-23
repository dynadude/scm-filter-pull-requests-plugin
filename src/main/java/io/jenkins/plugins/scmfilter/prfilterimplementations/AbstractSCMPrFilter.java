package io.jenkins.plugins.scmfilter.prfilterimplementations;

import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilter;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead2;

public abstract class AbstractSCMPrFilter implements SCMPrFilter {
    private SCMHeadMatcher matcher;

    public AbstractSCMPrFilter(SCMHeadMatcher matcher) {
        this.matcher = matcher;
    }

    public final boolean isExcluded(SCMHead head) {
        if (!(head instanceof ChangeRequestSCMHead2)) {
            return false;
        }

        head = getHeadToMatch(head);

        return !matcher.matches(head);
    }

    protected abstract SCMHead getHeadToMatch(SCMHead head);
}
