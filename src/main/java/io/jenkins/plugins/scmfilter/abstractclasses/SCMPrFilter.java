package io.jenkins.plugins.scmfilter.abstractclasses;

import jenkins.scm.api.SCMHead;

public interface SCMPrFilter {
    public boolean isExcluded(SCMHead head);
}
