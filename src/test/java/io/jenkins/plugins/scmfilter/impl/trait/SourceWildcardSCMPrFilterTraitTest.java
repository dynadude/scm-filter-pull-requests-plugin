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

public class SourceWildcardSCMPrFilterTraitTest {
    @Test
    public void testFilterToMaster() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new SourceWildcardSCMPrFilterTrait("master", "");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertFalse(
                    "Is the PR from master let through?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromMasterHead));

            assertTrue(
                    "Is the PR from develop filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromDevelopHead));

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
        SCMPrFilterTrait filterTrait = new SourceWildcardSCMPrFilterTrait("*", "mas*");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR from master filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromMasterHead));

            assertTrue(
                    "Is the PR from develop let through?",
                    !prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromDevelopHead));

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
        SCMPrFilterTrait filterTrait = new SourceWildcardSCMPrFilterTrait("", "");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        assertTrue("prefilters only have one item", prefilters.size() == 1);
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR from master filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromMasterHead));

            assertTrue(
                    "Is the PR from develop filtered out?",
                    prefilter.isExcluded(
                            TestInitialization.initializeMockSCMSource(), TestInitialization.mockPrFromDevelopHead));

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
