package io.jenkins.plugins.scmfilter.impl;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.jenkins.plugins.scmfilter.SCMHeadMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.TagSCMHead;
import org.kohsuke.stapler.DataBoundConstructor;

public class RegexSCMHeadMatcher extends SCMHeadMatcher {
    @NonNull
    private final String regex;

    @NonNull
    private final String tagRegex;

    /**
     * Stapler constructor.
     *
     * @param regex    the regex for filtering PRs to branches.
     * @param tagRegex the regex for filtering PRs to tags.
     */
    @DataBoundConstructor
    public RegexSCMHeadMatcher(@NonNull String regex, @NonNull String tagRegex) {
        super(new RegexStringMatcher());
        this.regex = regex;
        this.tagRegex = tagRegex;
    }

    public boolean doesMatch(SCMHead head) {
        if (head instanceof TagSCMHead) {
            return matcher.matches(tagRegex, head.getName());
        } else {
            return matcher.matches(regex, head.getName());
        }
    }
}
