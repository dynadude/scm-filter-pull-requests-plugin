package io.jenkins.plugins.scmfilter.impl;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.jenkins.plugins.scmfilter.abstractclasses.SCMHeadMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.mixin.TagSCMHead;
import org.kohsuke.stapler.DataBoundConstructor;

public class RegexSCMHeadMatcher extends SCMHeadMatcher {
    @NonNull
    private final String regex;

    private final String tagRegex;

    /**
     * Stapler constructor.
     *
     * @param regex the regex for filtering PRs to branches.
     */
    public RegexSCMHeadMatcher(@NonNull String regex) {
        this(regex, null);
    }

    /**
     * Stapler constructor.
     *
     * @param regex    the regex for filtering PRs to branches.
     * @param tagRegex the regex for filtering PRs to tags.
     */
    @DataBoundConstructor
    public RegexSCMHeadMatcher(@NonNull String regex, String tagRegex) {
        super(new RegexStringMatcher());
        this.regex = regex;
        this.tagRegex = tagRegex;
    }

    public boolean matches(SCMHead head) {
        if (head instanceof TagSCMHead && tagRegex != null) {
            return matcher.matches(tagRegex, head.getName());
        } else {
            return matcher.matches(regex, head.getName());
        }
    }
}
