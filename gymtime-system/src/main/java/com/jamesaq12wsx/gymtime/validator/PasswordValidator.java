package com.jamesaq12wsx.gymtime.validator;

import com.jamesaq12wsx.gymtime.security.PasswordConfig;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@NoArgsConstructor
public class PasswordValidator implements Predicate<String> {

    private PasswordConfig passwordConfig;

    public static Predicate VALID_PASSWORD = null;

    public PasswordValidator(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;

        this.buildValidator(
                passwordConfig.isSpecialChar(),
                passwordConfig.isCapitalLetter(),
                passwordConfig.isNumber(),
                passwordConfig.getMin(),
                passwordConfig.getMax());
    }

    private void buildValidator(
            boolean forceSpecialChar,
            boolean forceCapitalLetter,
            boolean forceNumber,
            int minLength,
            int maxLength) {
        StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])");

        if (forceSpecialChar) {
            patternBuilder.append("(?=.*[@#$%!])");
        }

        if (forceCapitalLetter) {
            patternBuilder.append("(?=.*[A-Z])");
        }

        if (forceNumber) {
            patternBuilder.append("(?=.*d)");
        }

        patternBuilder.append(".{" + minLength + "," + maxLength + "})");
//        pattern = patternBuilder.toString();

        VALID_PASSWORD = Pattern.compile(patternBuilder.toString(), Pattern.CASE_INSENSITIVE).asPredicate();

//        return INSTANCE;
    }

    @Override
    public boolean test(String s) {
        return VALID_PASSWORD.test(s);
    }
}
