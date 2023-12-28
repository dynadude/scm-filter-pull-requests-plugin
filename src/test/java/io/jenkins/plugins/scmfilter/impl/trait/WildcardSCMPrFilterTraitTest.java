package io.jenkins.plugins.scmfilter.impl.trait;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import hudson.model.TaskListener;
import java.io.IOException;
import java.util.List;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.SCMHeadObserver;
import jenkins.scm.api.SCMSourceCriteria;
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
    SCMHead mockPrHead = new SCMHead("test-pr") {
        public SCMHead getTarget() {
            return new SCMHead(mockBranches[0]);
        }
    };
    SCMHead mockMasterHead = new MockSCMHead("master");

    @Test
    public void testPrToMasterMasterNotFiltered() throws Exception {
        SCMSourceContext context = initializeMockSCMSourceContext();
        WildcardSCMPrFilterTrait prWildcardFilter = new WildcardSCMPrFilterTrait(mockBranches[0], "", "", "*");
        prWildcardFilter.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR to master let through?", !prefilter.isExcluded(initializeMockSCMSource(), mockPrHead));

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

        controller.openChangeRequest(mockRepoName, mockBranches[0]);

        return controller;
    }
}
