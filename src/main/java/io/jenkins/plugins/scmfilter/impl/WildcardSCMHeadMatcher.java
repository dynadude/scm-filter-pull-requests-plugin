package io.jenkins.plugins.scmfilter.impl;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.jenkins.plugins.scmfilter.SCMHeadMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.TagSCMHead;
import org.kohsuke.stapler.DataBoundConstructor;

public class WildcardSCMHeadMatcher extends SCMHeadMatcher {
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
    public WildcardSCMHeadMatcher(
            @NonNull String includes,
            @NonNull String excludes,
            @NonNull String tagIncludes,
            @NonNull String tagExcludes) {
        super(new WildcardMatcher());
        this.includes = includes;
        this.excludes = excludes;
        this.tagIncludes = tagIncludes;
        this.tagExcludes = tagExcludes;
    }

    public boolean doesMatch(SCMHead head) {
        if (head instanceof TagSCMHead) {
            return doesTagMatchWithIncludeAndExcludes(head.getName());
        }

        return doesBranchMatchWithIncludeAndExcludes(head.getName());
    }

    private boolean doesTagMatchWithIncludeAndExcludes(String name) {
        return doesMatchWithIncludeAndExcludes(name, tagIncludes, tagExcludes);
    }

    private boolean doesBranchMatchWithIncludeAndExcludes(String name) {
        return doesMatchWithIncludeAndExcludes(name, includes, excludes);
    }

    private boolean doesMatchWithIncludeAndExcludes(String name, String includes, String excludes) {
        return matcher.matches(includes, name) && !matcher.matches(excludes, name);
    }
}
