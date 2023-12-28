package io.jenkins.plugins.scmfilter.impl.trait;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import hudson.model.TaskListener;
import java.io.IOException;
import java.util.List;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.SCMHeadObserver;
import jenkins.scm.api.SCMSourceCriteria;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;
import jenkins.scm.api.mixin.TagSCMHead;
import jenkins.scm.api.trait.SCMHeadPrefilter;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.impl.mock.MockSCMController;
import jenkins.scm.impl.mock.MockSCMHead;
import jenkins.scm.impl.mock.MockSCMSource;
import jenkins.scm.impl.mock.MockSCMSourceContext;
import org.junit.Test;

public class WildcardSCMPrFilterTraitTest {
    private String mockRepoName = "mock-repo";
    private String[] mockBranches = {"master", "develop", "staging"};
    SCMHead mockPrToMasterHead = new MockChangeRequestSCMHead("test-pr", "master");
    SCMHead mockPrToDevelopHead = new MockChangeRequestSCMHead("test-pr-2", "develop");
    SCMHead mockPrToNameTagHead = new MockChangeRequestSCMHead("test-pr-2", new MockTagSCMHead("mock", "name"));
    SCMHead mockPrToPoliceTagHead = new MockChangeRequestSCMHead("test-pr-2", new MockTagSCMHead("mock", "police"));
    SCMHead mockMasterHead = new MockSCMHead("master");

    @Test
    public void testFilterToMaster() throws Exception {
        SCMSourceContext context = initializeMockSCMSourceContext();
        WildcardSCMPrFilterTrait prWildcardFilter = new WildcardSCMPrFilterTrait(mockBranches[0], "", "", "*");
        prWildcardFilter.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR to master let through?",
                    !prefilter.isExcluded(initializeMockSCMSource(), mockPrToMasterHead));

            assertTrue(
                    "Is the PR to develop filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToDevelopHead));

            assertTrue(
                    "Is the PR to the name tag filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToNameTagHead));

            assertTrue(
                    "Is the PR to the police tag filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToPoliceTagHead));

            assertTrue("Is master let through?", !prefilter.isExcluded(initializeMockSCMSource(), mockMasterHead));

            assertTrue(
                    "Is develop let through?",
                    !prefilter.isExcluded(initializeMockSCMSource(), new MockSCMHead("develop")));
        }
    }

    @Test
    public void testFilterToNameTag() throws Exception {
        SCMSourceContext context = initializeMockSCMSourceContext();
        WildcardSCMPrFilterTrait prWildcardFilter = new WildcardSCMPrFilterTrait("", "", "name", "");
        prWildcardFilter.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR to master filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToMasterHead));

            assertTrue(
                    "Is the PR to develop filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToDevelopHead));

            assertTrue(
                    "Is the PR to the name tag let through?",
                    !prefilter.isExcluded(initializeMockSCMSource(), mockPrToNameTagHead));

            assertTrue(
                    "Is the PR to the police tag filtered out?",
                    prefilter.isExcluded(initializeMockSCMSource(), mockPrToPoliceTagHead));

            assertTrue("Is master let through?", !prefilter.isExcluded(initializeMockSCMSource(), mockMasterHead));

            assertTrue(
                    "Is develop let through?",
                    !prefilter.isExcluded(initializeMockSCMSource(), new MockSCMHead("develop")));
        }
    }

    private MockSCMSourceContext initializeMockSCMSourceContext() throws IOException {
        SCMHeadObserver observer = mock(SCMHeadObserver.class);
        return new MockSCMSourceContext(
                initializeMockSCMSource(),
                new SCMSourceCriteria() {
                    public boolean isHead(Probe prb, TaskListener lsnr) {
                        return true;
                    }
                },
                observer);
    }

    private MockSCMSource initializeMockSCMSource() throws IOException {
        return new MockSCMSource(initializeMockSCMController(), mockRepoName);
    }

    private MockSCMController initializeMockSCMController() throws IOException {
        MockSCMController controller = MockSCMController.create();

        controller.createRepository(mockRepoName);
        for (String branch : mockBranches) {
            controller.createBranch(mockRepoName, branch);
        }

        controller.createTag(mockRepoName, mockBranches[0], "name");

        controller.createTag(mockRepoName, mockBranches[0], "police");

        controller.openChangeRequest(mockRepoName, mockBranches[0]);
        controller.openChangeRequest(mockRepoName, mockBranches[1]);

        return controller;
    }
}

class MockChangeRequestSCMHead extends SCMHead implements ChangeRequestSCMHead {
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

class MockTagSCMHead extends SCMHead implements TagSCMHead {
    public MockTagSCMHead(String name, String targetName) {
        super(name);
    }

    public long getTimestamp() {
        return 1L;
    }
}
