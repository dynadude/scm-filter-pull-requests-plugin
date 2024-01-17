package io.jenkins.plugins.scmfilter.traitimplementations;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilterTrait;
import io.jenkins.plugins.scmfilter.impl.SourceSCMPrFilter;
import io.jenkins.plugins.scmfilter.impl.WildcardSCMHeadMatcher;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class SourceWildcardSCMPrFilterTrait extends SCMPrFilterTrait {

    @NonNull
    private final String includes;

    @NonNull
    private final String excludes;

    /**
     * Stapler constructor.
     *
     * @param includes the branch include rules.
     * @param excludes the branch exclude rules.
     * @param matcher  the matcher for the pull requests.
     */
    @DataBoundConstructor
    public SourceWildcardSCMPrFilterTrait(@NonNull String includes, @NonNull String excludes) {
        super(new SourceSCMPrFilter(new WildcardSCMHeadMatcher(includes, excludes)));

        this.includes = includes;
        this.excludes = excludes;
    }

    public String getIncludes() {
        return includes;
    }

    public String getExcludes() {
        return excludes;
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
