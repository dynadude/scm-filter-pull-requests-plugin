package io.jenkins.plugins.scmfilter.prfilterimplementations;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;

public class TargetSCMPrFilter extends AbstractSCMPrFilter {
    public TargetSCMPrFilter(SCMHeadMatcher matcher) {
        super(matcher);
    }

    @Override
    @SuppressFBWarnings
    // Cast is checked in the base class
    protected SCMHead getHeadToMatch(SCMHead head) {
        return ((ChangeRequestSCMHead) head).getTarget();
    }
}
