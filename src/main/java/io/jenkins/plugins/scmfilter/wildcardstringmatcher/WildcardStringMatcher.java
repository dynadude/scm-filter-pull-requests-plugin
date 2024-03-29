package io.jenkins.plugins.scmfilter.wildcardstringmatcher;

import io.jenkins.plugins.scmfilter.abstractclasses.StringMatcher;
import java.util.regex.Pattern;

public class WildcardStringMatcher implements StringMatcher {

    public WildcardStringMatcher() {}

    public boolean matches(String pattern, String testedString) {
        return Pattern.matches(toRegex(pattern), testedString);
    }

    private String toRegex(String wildcardString) {
        StringBuilder quotedBranches = new StringBuilder();
        for (String wildcard : wildcardString.split(" ")) {
            StringBuilder quotedBranch = new StringBuilder();
            for (String branch : wildcard.split("(?=[*])|(?<=[*])")) {
                if (branch.equals("*")) {
                    quotedBranch.append(".*");
                } else if (!branch.isEmpty()) {
                    quotedBranch.append(Pattern.quote(branch));
                }
            }
            if (quotedBranches.length() > 0) {
                quotedBranches.append("|");
            }
            quotedBranches.append(quotedBranch);
        }
        return quotedBranches.toString();
    }
}
