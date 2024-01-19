package io.jenkins.plugins.scmfilter.traitimplementations;

import static org.junit.Assert.assertTrue;

import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilterTrait;
import io.jenkins.plugins.scmfilter.testingutils.TestInitialization;
import java.util.List;
import jenkins.scm.api.trait.SCMHeadPrefilter;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.impl.mock.MockSCMHead;
import org.junit.Test;

public class SourceRegexSCMPrFilterTraitTest {
    @Test
    public void testFilterFromMaster() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new SourceRegexSCMPrFilterTrait(TestInitialization.mockBranches[0]);
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
        for (SCMHeadPrefilter prefilter : prefilters) {
            assertTrue(
                    "Is the PR from master let through?",
                    !prefilter.isExcluded(
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

    public void testBranchExcludes() throws Exception {
        SCMSourceContext context = TestInitialization.initializeMockSCMSourceContext();
        SCMPrFilterTrait filterTrait = new SourceRegexSCMPrFilterTrait("(:!mas).*");
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
        SCMPrFilterTrait filterTrait = new SourceRegexSCMPrFilterTrait("");
        filterTrait.decorateContext(context);
        List<SCMHeadPrefilter> prefilters = context.prefilters();
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
