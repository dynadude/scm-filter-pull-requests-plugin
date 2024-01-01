package io.jenkins.plugins.scmfilter.mock;

import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;

public class MockChangeRequestSCMHead extends SCMHead implements ChangeRequestSCMHead {
    private SCMHead target;

    public MockChangeRequestSCMHead(String name, String targetName) {
        this(name, new SCMHead(targetName));
    }

    public MockChangeRequestSCMHead(String name, SCMHead target) {
        super(name);
        this.target = target;
    }

    public SCMHead getTarget() {
        return target;
    }

    public String getId() {
        return "mock";
    }
}
