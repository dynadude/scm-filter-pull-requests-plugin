package io.jenkins.plugins.scmfilter.impl.trait;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.jenkins.plugins.scmfilter.TestInitialization;
import io.jenkins.plugins.scmfilter.trait.SCMPrFilterTrait;
import java.util.List;
import jenkins.scm.api.trait.SCMHeadPrefilter;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.impl.mock.MockSCMHead;
import org.junit.Test;

public class WildcardSCMPrFilterTraitTest {
    @Test
    public void testFilterToMaster() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new WildcardSCMPrFilterTrait("master", "", "", "*");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertFalse(
                    "Is the PR to master let through?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToMasterHead));

            assertTrue(
                    "Is the PR to develop filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToDevelopHead));

            assertTrue(
                    "Is the PR to the name tag filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToNameTagHead));

            assertTrue(
                    "Is the PR to the police tag filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToPoliceTagHead));

            assertFalse(
                    "Is master let through?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockMasterHead));

            assertFalse(
                    "Is develop let through?",
                    prefilter.isExcluded(TestInitialization.initializeMockSCMSource(), new MockSCMHead("develop")));
        }
    }

    public void testBranchExcludes() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new WildcardSCMPrFilterTrait("*", "mas*", "", "*");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR to master filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToMasterHead));

            assertTrue(
                    "Is the PR to develop let through?",
                    !prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToDevelopHead));

            assertTrue(
                    "Is the PR to the name tag filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToNameTagHead));

            assertTrue(
                    "Is the PR to the police tag filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToPoliceTagHead));

            assertTrue(
                    "Is master let through?",
                    !prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockMasterHead));

            assertTrue(
                    "Is develop let through?",
                    !prefilter.isExcluded(TestInitialization.initializeMockSCMSource(), new MockSCMHead("develop")));
        }
    }

    @Test
    public void testFilterToNameTag() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new WildcardSCMPrFilterTrait("", "", "name", "");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        assertTrue("prefilters only have one item", prefilters.size() == 1);
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR to master filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToMasterHead));

            assertTrue(
                    "Is the PR to develop filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToDevelopHead));

            assertTrue(
                    "Is the PR to the name tag let through?",
                    !prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToNameTagHead));

            assertTrue(
                    "Is the PR to the police tag filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrToPoliceTagHead));

            assertTrue(
                    "Is master let through?",
                    !prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockMasterHead));

            assertTrue(
                    "Is develop let through?",
                    !prefilter.isExcluded(TestInitialization.initializeMockSCMSource(), new MockSCMHead("develop")));
        }
    }
}
