package io.jenkins.plugins.scmfilter;

import java.util.regex.Pattern;

public class WildcardString {
    private String regexString;

    public WildcardString(String str) {
        regexString = toRegex(str);
    }

    public boolean matches(String testedString) {
        return Pattern.matches(regexString, testedString);
    }

    public String toRegex(String wildcardString) {
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
