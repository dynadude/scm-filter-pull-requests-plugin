package io.jenkins.plugins.scmfilter.impl.trait;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import hudson.Extension;
import io.jenkins.plugins.scmfilter.impl.TargetSCMPrFilter;
import io.jenkins.plugins.scmfilter.impl.WildcardSCMHeadMatcher;
import io.jenkins.plugins.scmfilter.trait.SCMPrFilterTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class WildcardSCMPrFilterTrait extends SCMPrFilterTrait {
    /**
     * Stapler constructor.
     *
     * @param includes    the branch include rules.
     * @param excludes    the branch exclude rules.
     * @param tagIncludes the tag include rules.
     * @param tagExcludes the tag exclude rules.
     * @param matcher     the matcher for the pull requests.
     */
    @DataBoundConstructor
    public WildcardSCMPrFilterTrait(
            @CheckForNull String includes, String excludes, String tagIncludes, String tagExcludes) {
        super(new TargetSCMPrFilter(new WildcardSCMHeadMatcher(
                StringUtils.defaultIfBlank(includes, ""),
                StringUtils.defaultIfBlank(excludes, ""),
                StringUtils.defaultIfBlank(tagIncludes, ""),
                StringUtils.defaultIfBlank(tagExcludes, ""))));
    }

    /**
     * Our descriptor.
     */
    @Symbol("wildcardSCMPrFilterTrait")
    @Extension
    @Selection
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.WildcardSCMPrFilterTrait_DisplayName();
        }
    }
}
