package io.jenkins.plugins.scmfilter.mock;

import static org.mockito.Mockito.mock;

import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.ChangeRequestCheckoutStrategy;
import jenkins.scm.api.mixin.ChangeRequestSCMHead2;

public class MockChangeRequestSCMHead2 extends MockChangeRequestSCMHead implements ChangeRequestSCMHead2 {
    private String originName;

    public MockChangeRequestSCMHead2(String name, String targetName, String originName) {
        super(name, targetName);
        this.originName = originName;
    }

    public MockChangeRequestSCMHead2(String name, SCMHead target, String originName) {
        super(name, target);
        this.originName = originName;
    }

    public String getOriginName() {
        return originName;
    }

    public ChangeRequestCheckoutStrategy getCheckoutStrategy() {
        return mock(ChangeRequestCheckoutStrategy.class);
    }
}
