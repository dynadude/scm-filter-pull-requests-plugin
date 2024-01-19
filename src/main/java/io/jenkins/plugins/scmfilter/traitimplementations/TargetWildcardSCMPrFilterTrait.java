package io.jenkins.plugins.scmfilter.traitimplementations;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMPrFilterTrait;
import io.jenkins.plugins.scmfilter.impl.WildcardSCMHeadMatcher;
import io.jenkins.plugins.scmfilter.prfilterimplementations.TargetSCMPrFilter;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

public class TargetWildcardSCMPrFilterTrait extends SCMPrFilterTrait {

    @NonNull
    private final String includes;

    @NonNull
    private final String excludes;

    @NonNull
    private final String tagIncludes;

    @NonNull
    private final String tagExcludes;

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
    public TargetWildcardSCMPrFilterTrait(
            @NonNull String includes,
            @NonNull String excludes,
            @NonNull String tagIncludes,
            @NonNull String tagExcludes) {
        super(new TargetSCMPrFilter(new WildcardSCMHeadMatcher(includes, excludes, tagIncludes, tagExcludes)));

        this.includes = includes;
        this.excludes = excludes;
        this.tagIncludes = tagIncludes;
        this.tagExcludes = tagExcludes;
    }

    public String getIncludes() {
        return includes;
    }

    public String getExcludes() {
        return excludes;
    }

    public String getTagIncludes() {
        return tagIncludes;
    }

    public String getTagExcludes() {
        return tagExcludes;
    }

    /**
     * Our descriptor.
     */
    @Symbol("targetWildcardSCMPrFilterTrait")
    @Extension
    @Selection
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.TargetWildcardSCMPrFilterTrait_DisplayName();
        }
    }
}
