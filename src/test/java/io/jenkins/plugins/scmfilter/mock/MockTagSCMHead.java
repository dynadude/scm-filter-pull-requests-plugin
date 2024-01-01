package io.jenkins.plugins.scmfilter.mock;

import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.TagSCMHead;

public class MockTagSCMHead extends SCMHead implements TagSCMHead {
    public MockTagSCMHead(String name) {
        super(name);
    }

    public long getTimestamp() {
        return 1L;
    }
}
