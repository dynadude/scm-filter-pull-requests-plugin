package io.jenkins.plugins.scmfilter.impl.trait;

import hudson.Extension;
import hudson.util.FormValidation;
import io.jenkins.plugins.scmfilter.impl.RegexSCMHeadMatcher;
import io.jenkins.plugins.scmfilter.impl.SourceSCMPrFilter;
import io.jenkins.plugins.scmfilter.trait.SCMPrFilterTrait;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class SourceRegexSCMPrFilterTrait extends SCMPrFilterTrait {
    /**
     * Stapler constructor.
     *
     * @param regex the regex for filtering PRs to branches.
     */
    @DataBoundConstructor
    public SourceRegexSCMPrFilterTrait(String regex) {
        super(new SourceSCMPrFilter(new RegexSCMHeadMatcher(StringUtils.defaultIfBlank(regex, ""), "")));
    }

    @Symbol("sourceRegexSCMPrFilterTrait")
    @Extension
    @Selection
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.SourceRegexSCMPrFilterTrait_DisplayName();
        }

        /**
         * Form validation for the regular expression.
         *
         * @param value the regular expression.
         * @return the validation results.
         */
        @Restricted(NoExternalUse.class) // stapler
        public FormValidation doCheckRegex(@QueryParameter String value) {
            try {
                Pattern.compile(value);
                return FormValidation.ok();
            } catch (PatternSyntaxException e) {
                return FormValidation.error(e.getMessage());
            }
        }
    }
}
