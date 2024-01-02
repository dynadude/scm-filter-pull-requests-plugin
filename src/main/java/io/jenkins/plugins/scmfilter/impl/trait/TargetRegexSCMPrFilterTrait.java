package io.jenkins.plugins.scmfilter.impl.trait;

import hudson.Extension;
import hudson.util.FormValidation;
import io.jenkins.plugins.scmfilter.impl.RegexSCMHeadMatcher;
import io.jenkins.plugins.scmfilter.impl.TargetSCMPrFilter;
import io.jenkins.plugins.scmfilter.trait.SCMPrFilterTrait;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jenkins.scm.api.trait.SCMSourceTraitDescriptor;
import jenkins.scm.impl.trait.Selection;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.QueryParameter;

public class RegexSCMPrFilterTrait extends SCMPrFilterTrait {
    /**
     * Stapler constructor.
     *
     * @param regex    the regex for filtering PRs to branches.
     * @param tagRegex the regex for filtering PRs to tags.
     */
    public RegexSCMPrFilterTrait(String regex, String tagRegex) {
        super(new TargetSCMPrFilter(new RegexSCMHeadMatcher(
                StringUtils.defaultIfBlank(regex, ""), StringUtils.defaultIfBlank(tagRegex, ""))));
    }

    @Symbol("regexSCMPrFilterTrait")
    @Extension
    @Selection
    public static class DescriptorImpl extends SCMSourceTraitDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDisplayName() {
            return Messages.RegexSCMPrFilterTrait_DisplayName();
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
