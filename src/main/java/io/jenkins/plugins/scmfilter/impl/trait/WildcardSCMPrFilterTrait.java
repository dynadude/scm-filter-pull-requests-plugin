/*
 * The MIT License
 *
 * Copyright (c) 2017, CloudBees, Inc.
 * Copyright (c) 2017-2018, Sam Gleske - https://github.com/samrocketman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.jenkins.plugins.scmfilter.impl.trait;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import io.jenkins.plugins.scmfilter.Matcher;
import io.jenkins.plugins.scmfilter.WildcardMatcher;
import jenkins.scm.api.SCMHead;
import jenkins.scm.api.SCMSource;
import jenkins.scm.api.mixin.ChangeRequestSCMHead;
import jenkins.scm.api.mixin.TagSCMHead;
import jenkins.scm.api.trait.SCMHeadPrefilter;
import jenkins.scm.api.trait.SCMSourceContext;
import jenkins.scm.api.trait.SCMSourceTrait;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Decorates a {@link SCMSource} with a {@link SCMHeadPrefilter} that filters
 * {@link SCMHead} instances based on
 * matching wildcard include/exclude rules.
 *
 * @since 0.1
 */
public class WildcardSCMPrFilterTrait extends SCMSourceTrait {

    private Matcher matcher = new WildcardMatcher();

    /**
     * The branch include rules.
     */
    @NonNull
    private final String includes;

    /**
     * The branch exclude rules.
     */
    @NonNull
    private final String excludes;

    /**
     * The tag include rules.
     */
    @NonNull
    private final String tagIncludes;

    /**
     * The tag exclude rules.
     */
    @NonNull
    private final String tagExcludes;

    /**
     * Stapler constructor.
     *
     * @param includes    the branch include rules.
     * @param excludes    the branch exclude rules.
     * @param tagIncludes the tag include rules.
     * @param tagExcludes the tag exclude rules.
     */
    @DataBoundConstructor
    public WildcardSCMPrFilterTrait(
            @CheckForNull String includes, String excludes, String tagIncludes, String tagExcludes) {
        this.includes = StringUtils.defaultIfBlank(includes, "");
        this.excludes = StringUtils.defaultIfBlank(excludes, "");
        this.tagIncludes = StringUtils.defaultIfBlank(tagIncludes, "");
        this.tagExcludes = StringUtils.defaultIfBlank(tagExcludes, "");
    }

    /**
     * Deprecated constructor kept around for compatibility and migration.
     *
     * @param includes the include rules.
     * @param excludes the exclude rules.
     */
    @Deprecated
    public WildcardSCMPrFilterTrait(@CheckForNull String includes, String excludes) {
        this.includes = StringUtils.defaultIfBlank(includes, "");
        this.excludes = StringUtils.defaultIfBlank(excludes, "");
        this.tagIncludes = "";
        this.tagExcludes = "*";
    }

    /**
     * Returns the branch include rules.
     *
     * @return the branch include rules.
     */
    public String getIncludes() {
        return includes;
    }

    /**
     * Returns the branch exclude rules.
     *
     * @return the branch exclude rules.
     */
    public String getExcludes() {
        return excludes;
    }

    /**
     * Returns the tag include rules.
     *
     * @return the tag include rules.
     */
    public String getTagIncludes() {
        return tagIncludes;
    }

    /**
     * Returns the tag exclude rules.
     *
     * @return the tag exclude rules.
     */
    public String getTagExcludes() {
        return tagExcludes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateContext(SCMSourceContext<?, ?> context) {
        context.withPrefilter(new SCMHeadPrefilter() {
            @Override
            public boolean isExcluded(@NonNull SCMSource request, @NonNull SCMHead head) {
                if (!(head instanceof ChangeRequestSCMHead)) {
                    return false;
                }

                head = ((ChangeRequestSCMHead) head).getTarget();

                if (head instanceof TagSCMHead) {
                    return isTagExcludedWithIncludeAndExcludes(head.getName());
                } else {
                    return isBranchExcludedWithIncludeAndExcludes(head.getName());
                }
            }
        });
    }

    private boolean isBranchExcludedWithIncludeAndExcludes(String name) {
        return isExcluded(name, includes, excludes);
    }

    private boolean isTagExcludedWithIncludeAndExcludes(String name) {
        return isExcluded(name, includes, excludes);
    }

    private boolean isExcluded(String name, String includes, String excludes) {
        return !(matcher.matches(includes, name) && !matcher.matches(excludes, name));
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
            return Messages.WildcardSCMPrFilterTrait_DescriptorImpl_DisplayName();
        }
    }
}
