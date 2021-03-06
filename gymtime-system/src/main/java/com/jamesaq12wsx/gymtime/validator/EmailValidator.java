package com.jamesaq12wsx.gymtime.validator;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Predicate<String> {

    public final Predicate VALID_EMAIL_ADDRESS =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).asPredicate();

    @Override
    public boolean test(String email) {
        return VALID_EMAIL_ADDRESS.test(email);
    }
}
