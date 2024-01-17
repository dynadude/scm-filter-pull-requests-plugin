package io.jenkins.plugins.scmfilter;

import jenkins.scm.api.SCMHead;

public interface SCMPrFilter {
    public boolean isExcluded(SCMHead head);
}
