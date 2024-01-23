package io.jenkins.plugins.scmfilter.testingutils;

import static org.mockito.Mockito.mock;

import io.jenkins.plugins.scmfilter.mock.MockChangeRequestSCMHead2;
import io.jenkins.plugins.scmfilter.mock.MockTagSCMHead;
import java.io.IOException;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.SCMHeadObserver;
import jenkins.scm.api.SCMSourceCriteria;
import jenkins.scm.impl.mock.MockSCMController;
import jenkins.scm.impl.mock.MockSCMHead;
import jenkins.scm.impl.mock.MockSCMSource;
import jenkins.scm.impl.mock.MockSCMSourceContext;

public class TestInitialization {
    public static String mockRepoName = "mock-repo";
    public static String[] mockBranches = {"master", "develop", "staging "};

    public static SCMHead mockPrToMasterHead = new MockChangeRequestSCMHead2("test-pr", "master", "fake");
    public static SCMHead mockPrToDevelopHead = new MockChangeRequestSCMHead2("test-pr-2", "develop", "fake");
    public static SCMHead mockPrToNameTagHead =
            new MockChangeRequestSCMHead2("test-pr-3", new MockTagSCMHead("name"), "fake");
    public static SCMHead mockPrToPoliceTagHead =
            new MockChangeRequestSCMHead2("test-pr-4", new MockTagSCMHead("police"), "fake");
    public static SCMHead mockPrFromMasterHead = new MockChangeRequestSCMHead2("test-pr", "main", "master");
    public static SCMHead mockPrFromDevelopHead = new MockChangeRequestSCMHead2("test-pr-2", "master", "develop");
    public static SCMHead mockMasterHead = new MockSCMHead("master");

    public static MockSCMSourceContext initializeMockSCMSourceContext() throws IOException {
        SCMHeadObserver observer = mock(SCMHeadObserver.class);
        return new MockSCMSourceContext(initializeMockSCMSource(), mock(SCMSourceCriteria.class), observer);
    }

    public static MockSCMSource initializeMockSCMSource() throws IOException {
        return new MockSCMSource(initializeMockSCMController(), mockRepoName);
    }

    private static MockSCMController initializeMockSCMController() throws IOException {
        return MockSCMController.create();
    }
}
