package io.jenkins.plugins.scmfilter.impl.trait;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import hudson.Extension;
import io.jenkins.plugins.scmfilter.impl.SourceSCMPrFilter;
import io.jenkins.plugins.scmfilter.impl.WildcardSCMHeadMatcher;
import io.jenkins.plugins.scmfilter.trait.SCMPrFilterTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class SourceWildcardSCMPrFilterTrait extends SCMPrFilterTrait {
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
    public SourceWildcardSCMPrFilterTrait(@CheckForNull String includes, String excludes) {
        super(new SourceSCMPrFilter(new WildcardSCMHeadMatcher(
                StringUtils.defaultIfBlank(includes, ""), StringUtils.defaultIfBlank(excludes, ""), "", "")));
    }

    /**
     * Our descriptor.
     */
    @Symbol("sourceWildcardSCMPrFilterTrait")
    @Extension
    @Selection
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.SourceWildcardSCMPrFilterTrait_DisplayName();
        }
    }
}
