package io.jenkins.plugins.scmfilter.prfilterimplementations;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead2;

public class SourceSCMPrFilter extends AbstractSCMPrFilter {
    public SourceSCMPrFilter(SCMHeadMatcher matcher) {
        super(matcher);
    }

    @Override
    @SuppressFBWarnings
    // Cast is checked in the base class
    protected SCMHead getHeadToMatch(SCMHead head) {
        return new SCMHead(((ChangeRequestSCMHead2) head).getOriginName());
    }
}
